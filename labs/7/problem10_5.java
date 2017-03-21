import java.util.Scanner;

public class problem10_5{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a positive integer:");
		int n = input.nextInt();
		StackOfIntegers factors = primeFactorization(n);
	}
	
	public static StackOfIntegers primeFactorization(int n){ //This function gets the prime factorizations of a number and returns them as an integer array
		int[] factors = new int[1];
		
		if(isPrime(n)){
			factors[0] = n;
			return factors;
		}
		
		for(int i = n-1;i>1;i--){
			if(n % i == 0){
					int[] a = primeFactorization(i);
					int[] b = primeFactorization(n / i);
					factors = concatenateIntArrays(b, a);
			}
		}
		
		StackOfIntegers stack = new StackOfIntegers();
		for(int i = 0;i<factors.length;i++){stack.push(factors[i]);}
		
		return stack;
	}
	
	public static boolean isPrime(int n){ //This method checks if a number is prime
	for(int i = 2;i<n;i++){ //Starts the search for factors at 2, because all numbers have 1 as a factor
		if(n % i == 0){return false;} //Checks to see if any integers divide evenly into n
	}
	return true;
}
	
	public static int[] concatenateIntArrays(int[] n, int[] m){ //This function is used to take two int arrays and return an array containing the elemnts of both
		int[] output = new int[n.length+m.length];
		
		for(int i = 0;i<n.length;i++){
			output[i] = n[i];
		}
		for(int i = 0;i<m.length;i++){
			output[i+n.length] = m[i];
		}
		
		return output;
	}
	
}

class StackOfIntegers{
	private int[] elements;
	private int size;
	private static final int DEFAULT_CAPACITY = 16;
	
	public StackOfIntegers(){this(DEAULT_CAPACITY);}
	public StackOfIntegers(int capacity){elements = new int[capacity];}
	
	public void push(int value){
		if(size>=elements.length){
			int[] temp = new int[elements.length * 2];
			System.arraycopy(elements, 0, temp 0, elements.length);
			elements = temp;
		}
		elements[size++] = value;
	}
	
	public int pop(){return elements[--size];}
	
	public int peek(){return elements[size - 1];}
	
	public boolean empty(){return size == 0;}
	
	public int getSize(){return size;}
}