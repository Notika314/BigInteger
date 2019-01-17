package bigint;

/**
 * This class encapsulates a BigInteger, i.e. a positive or negative integer with 
 * any number of digits, which overcomes the computer storage length limitation of 
 * an integer.
 * 
 */

public class BigInteger {

	/**
	 * True if this is a negative integer
	 */
	boolean negative;
	
	/**
	 * Number of digits in this integer
	 */
	int numDigits;
	
	/**
	 * Reference to the first node of this integer's linked list representation
	 * NOTE: The linked list stores the Least Significant Digit in the FIRST node.
	 * For instance, the integer 235 would be stored as:
	 *    5 --> 3  --> 2
	 *    
	 * Insignificant digits are not stored. So the integer 00235 will be stored as:
	 *    5 --> 3 --> 2  (No zeros after the last 2)        
	 */
	DigitNode front;
	
	/**
	 * Initializes this integer to a positive number with zero digits, in other
	 * words this is the 0 (zero) valued integer.
	 */
	public BigInteger() {
		negative = false;
		numDigits = 0;
		front = null;
	}
	
	
	//--------------------PRIVATE HELPER METHODS----------------------------
	
	private void appendAt(int position,int digit) {
		DigitNode digitToPush = new DigitNode(digit,null);
		DigitNode target =front;
		if (position == 0) {
			front = digitToPush;
		} else {
			for (int i=0;i<position-1;i++) {
				target=target.next;
			}
			target.next = digitToPush;
		}
		numDigits ++;
	}
	private int firstDigit(int digit) {
		int firstDigit = digit/10;
		return firstDigit;
	}
	private int secondDigit(int digit) {
		if (digit<=9) {
			return digit;
		} else {
			return Math.abs(digit % 10);
		}
	}
	private void printData() {
		String sign="+";
		if (negative) {
			sign = "-";
		} 	
		System.out.println("The integer value is: "+this+", number of digits: "+numDigits+" and sign is "+sign+".");
	}
	private void trimZeroes() {
		int zeroesCount = 0;
		DigitNode current = this.front;
		while (current!=null) {
			if (current.digit==0) {
				zeroesCount ++;
			} else {
				zeroesCount = 0;
			}
			current= current.next;
		}
		int meaningfulDigits = this.numDigits - zeroesCount;
		DigitNode cursor = this.front;
		for (int i=0;i<meaningfulDigits-1;i++) {
			cursor = cursor.next;
		}
		cursor.next =null;
		numDigits-=zeroesCount;
		//cut the zeroes off the list s
	}
	private boolean isZero() {
		return (numDigits==0);
	}
	private void addZeroes(int amount) {     //adding zeroes at the end
		for (int i=0;i<amount;i++) {
			DigitNode newZero = new DigitNode(0,this.front);
			this.front = newZero;
		}
	}
	
	//---------------------END OF PRIVATE METHODS--------------------------
	/**
	 * Parses an input integer string into a corresponding BigInteger instance.
	 * A correctly formatted integer would have an optional sign as the first 
	 * character (no sign means positive), and at least one digit character
	 * (including zero). 
	 * Examples of correct format, with corresponding values
	 *      Format     Value
	 *       +0            0
	 *       -0            0
	 *       +123        123
	 *       1023       1023
	 *       0012         12  
	 *       0             0
	 *       -123       -123
	 *       -001         -1
	 *       +000          0
	 *       
	 * Leading and trailing spaces are ignored. So "  +123  " will still parse 
	 * correctly, as +123, after ignoring leading and trailing spaces in the input
	 * string.
	 * 
	 * Spaces between digits are not ignored. So "12  345" will not parse as
	 * an integer - the input is incorrectly formatted.
	 * 
	 * An integer with value 0 will correspond to a null (empty) list - see the BigInteger
	 * constructor
	 * 
	 * @param integer Integer string that is to be parsed
	 * @return BigInteger instance that stores the input integer.
	 * @throws IllegalArgumentException If input is incorrectly formatted
	 */
	public static BigInteger parse(String integer) 
	throws IllegalArgumentException {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		integer = integer.trim();
		boolean hasMinus = false;
		if (integer.charAt(0)=='+' || integer.charAt(0)=='-') {
			if (integer.charAt(0)=='-') {
				hasMinus = true;
			}
			integer = integer.substring(1,integer.length());
		}
		integer = integer.replaceFirst ("^0*", "");
		BigInteger newNumber = new BigInteger();
		if (hasMinus) {
			newNumber.negative = true;
		} else { 		//possibly don't need else part
			newNumber.negative = false;
		}
		for (int i = 0; i<=integer.length()-1; i++) {
			if (Character.isDigit(integer.charAt(i))) {
				DigitNode t = new DigitNode(Character.getNumericValue(integer.charAt(i)),null);
				t.next = newNumber.front;
				newNumber.front = t;	
				newNumber.numDigits++;
			} else {
				throw new IllegalArgumentException();
			}
		}
		if (newNumber.numDigits==0) {
			newNumber.negative = false;
		}
		
		newNumber.printData();
		return newNumber;
	}
	
	/**
	 * Adds the first and second big integers, and returns the result in a NEW BigInteger object. 
	 * DOES NOT MODIFY the input big integers.
	 * 
	 * NOTE that either or both of the input big integers could be negative.
	 * (Which means this method can effectively subtract as well.)
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return Result big integer
	 */
	public static BigInteger add(BigInteger first, BigInteger second) {
		// following line is a placeholder - compiler needs a return
		// modify it according to need
//		if ()
		BigInteger result = new BigInteger();
		if (first.front==null) {
			result = second;
			result.printData();
			return second;
		} else if (second.front == null){
			result = first;
			result.printData();
			return first;
		} 
		if (first.negative == second.negative) {
			result = result.sumUp(first,second);
			result.negative = first.negative;
			
		} else {
			result = first.subtract(second);
			result.trimZeroes();
		}	
		result.printData();
		return result; 
	}
	
	
	private BigInteger subtract(BigInteger other) {
		BigInteger result = new BigInteger();
		result.negative = this.negative;
		if (other.numDigits>numDigits) {
			result = other.subtract(this);
			result.negative = other.negative;
			result.printData();
			return result;
		} else {
			DigitNode first = this.front;
			DigitNode second = other.front;
			boolean keepSubtracting = true;
			boolean secondNumberFinished = false;
			int moveDown = 0;
			int position = 0;
			while (keepSubtracting) {
				int diff;
				if (secondNumberFinished) {
					diff = first.digit - moveDown;
				} else {
					 diff = first.digit - second.digit-moveDown;
				}
				if (diff<0) {	//if need to borrow 1 from next level 
					if (first.next==null) {  //if next level is empty, reverse subtracting
						result = other.subtract(this);
						result.negative = other.negative;
						break;
					} else {
						diff =10+diff;	//otherwise, borrow 1 and increase debt 
						moveDown = 1;
					}
				} else {
					moveDown = 0;
				}
				result.appendAt(position, diff);	//append diff to result
				if (first.next==null && second.next==null) {
					//check moveDown?
					keepSubtracting = false;
				} else if (second.next==null) {
					first = first.next;
					secondNumberFinished = true;
				} else {
					first = first.next;
					second = second.next;
				}
				position++;
			}
		}
		return result;
	}
	
	
	private BigInteger sumUp(BigInteger first, BigInteger second) {
		BigInteger result = new BigInteger();
		int moveUp = 0;
		boolean keepAdding = true;
		boolean justAddFirst = false;
		boolean justAddSecond = false;
		BigInteger currentFirst = new BigInteger();
		BigInteger currentSecond = new BigInteger();
		currentSecond.front = second.front;
		currentFirst.front = first.front;
		int sum;
		int position = 0;
		while (keepAdding) {
			if (justAddFirst) {
				sum = currentFirst.front.digit+moveUp;
			} else if (justAddSecond) {
				sum=currentSecond.front.digit+moveUp;
			} else {
				sum = currentFirst.front.digit+currentSecond.front.digit+moveUp; 
			}
			int digitToPush = result.secondDigit(sum);
			result.appendAt(position,digitToPush);
			position++;
			moveUp = result.firstDigit(sum);
			if (currentSecond.front.next == null && currentFirst.front.next!=null) {
				justAddFirst = true;
				currentFirst.front = currentFirst.front.next;
				continue;
			} else if (currentFirst.front.next==null && currentSecond.front.next!=null){
				justAddSecond = true;
				currentSecond.front = currentSecond.front.next;
				continue;
			}
			currentFirst.front = currentFirst.front.next;
			currentSecond.front = currentSecond.front.next;
			if (currentSecond.front==null && currentFirst.front==null) {
				keepAdding=false;
				if (moveUp!=0) {
					result.appendAt(position,moveUp);
					position++;
					break;
				}
			} 
			
		}
		result.printData();
		return result;
	}
	/**
	 * Returns the BigInteger obtained by multiplying the first big integer
	 * with the second big integer
	 * 
	 * This method DOES NOT MODIFY either of the input big integers
	 * 
	 * @param first First big integer
	 * @param second Second big integer
	 * @return A new BigInteger which is the product of the first and second big integers
	 */
	
	public static BigInteger multiply(BigInteger first, BigInteger second) {
		BigInteger result = new BigInteger();
		System.out.println("Digits in first number: "+first.numDigits+", in second: "+second.numDigits);
		if (first.isZero() || second.isZero()) {
			result.front = new DigitNode(0,null);
			result.printData();
			return result;
		}
		DigitNode currentMultiplier = second.front; //update it as you go
		boolean outerKeepGoing = true;
		int addingZeroes = 0;
		int outerPosition = 0;
		while (outerKeepGoing) {
			BigInteger innerResult = new BigInteger();
			int moveUp= 0;
			BigInteger currentFirst = new BigInteger();
			boolean keepGoing = true;
			currentFirst.front = first.front;
			int product ;
			int position = 0;
			while (keepGoing) {
				System.out.println(currentMultiplier+", this is current multiplier");
				product = currentFirst.front.digit*currentMultiplier.digit+moveUp;
				int digitToPush = innerResult.secondDigit(product);
				innerResult.appendAt(position,digitToPush);
				moveUp = innerResult.firstDigit(product);
				position++;
				if (currentFirst.front.next==null) {
					keepGoing = false;
					if (moveUp>0) {
						innerResult.appendAt(position, moveUp);
					}
				} else {
					currentFirst.front = currentFirst.front.next;
				}
			}
			System.out.println(innerResult+"");
			innerResult.addZeroes(addingZeroes);
			result = add(result,innerResult);
			if (currentMultiplier.next!=null) {
				currentMultiplier = currentMultiplier.next;
			} else {
				outerKeepGoing = false;
			}
//			update multiplier
//			check if need to terminate
			addingZeroes++;
		}
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		if (first.negative!=second.negative) {
			result.negative = true;
		}
		result.printData();
//		System.out.println("The sign is " + !result.negative);
		return result; 
		
	}

	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		if (front == null) {
			return "0";
		}
		String retval = front.digit + "";
		for (DigitNode curr = front.next; curr != null; curr = curr.next) {
				retval = curr.digit + retval;
		}
		
		if (negative) {
			retval = '-' + retval;
		}
		return retval;
	}
	
}
