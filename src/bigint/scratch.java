public static BigInteger multiply(BigInteger first, BigInteger second) {
		BigInteger result = new BigInteger();
		System.out.println("Digits in first number: "+first.numDigits+", in second: "+second.numDigits);
		if (first.isZero() || second.isZero()) {
			result.front = new DigitNode(0,null);
			return result;
		}
		if (first.negative!=second.negative) {
			result.negative = true;
		}
		
		int moveUp= 0;
		BigInteger currentFirst = new BigInteger();
		BigInteger currentSecond = new BigInteger();
		boolean keepGoing = true;
		currentFirst.front = first.front;
		currentSecond.front = second.front;
		int product ;
		int position = 0;
		while (keepGoing) {
			product = currentFirst.front.digit*currentSecond.front.digit+moveUp;
			int digitToPush = result.secondDigit(product);
			result.appendAt(position,digitToPush);
			moveUp = result.firstDigit(product);
			position++;
			if (currentFirst.front.next==null) {
				keepGoing = false;
				if (moveUp>0) {
					result.appendAt(position, moveUp);
				}
			} else {
				currentFirst.front = currentFirst.front.next;
			}
		}
		
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return result; 
		
	}






		public static BigInteger multiply(BigInteger first, BigInteger second) {
		BigInteger result = new BigInteger();
		System.out.println("Digits in first number: "+first.numDigits+", in second: "+second.numDigits);
		if (first.isZero() || second.isZero()) {
			result.front = new DigitNode(0,null);
			return result;
		}
		if (first.negative!=second.negative) {
			result.negative = true;
		}
		BigInteger currentSecond = new BigInteger();
		currentSecond.front = second.front;
		boolean outerKeepgoing = true;
		int sumOfProducts = 0;
		int 
		While (outerKeepGoing){
			int moveUp= 0;
			BigInteger currentFirst = new BigInteger();
			boolean keepGoing = true;
			currentFirst.front = first.front;
			int product ;
			int position = 0;
			while (keepGoing) {
				product = currentFirst.front.digit*currentSecond.front.digit+moveUp;
				int digitToPush = result.secondDigit(product);
				result.appendAt(position,digitToPush);
				moveUp = result.firstDigit(product);
				position++;
				if (currentFirst.front.next==null) {
					keepGoing = false;
					if (moveUp>0) {
						result.appendAt(position, moveUp);
					}
				} else {
					currentFirst.front = currentFirst.front.next;
				}
			}
			}
		// following line is a placeholder - compiler needs a return
		// modify it according to need
		return result; 
		
	}