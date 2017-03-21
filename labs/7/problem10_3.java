import java.util.Scanner;

public class problem10_3{
	public static void main(String[] args) {
		MyInteger a = new MyInteger(23);
		MyInteger b = new MyInteger(10);
		int c = MyInteger.parseInt("253");
		
		System.out.println("The value of A is " + a.getValue() + ", and the value of B is " + b.getValue());
		System.out.println("It is " + a.equals(b) + " that A and B are equal");
		System.out.println("It is " + MyInteger.isEven(5) + " that 5 is an even number, and " + MyInteger.isOdd(5) + " that 5 is an odd number. It is " + MyInteger.isPrime(5) + " that 5 is a prime number.");
		System.out.println(c);
	}
}

class MyInteger{
	private int value;
	
	public MyInteger(int value){this.value = value;}
	
	public static boolean isEven(int n){return (n % 2) == 0;} //Checks if the number is divisible by two; if so, returns true, otherwise, returns false
	public static boolean isOdd(int n){return !isEven(n);} //Returns the opposite of whether or not the number is even
	public static boolean isPrime(int n){
		for(int i = 2;i<n;i++){ //Starts the search for factors at 2, because all numbers have 1 as a factor
			if(n % i == 0){return false;} //Checks to see if any integers divide evenly into n
		}
		return true;
	}
	
	public boolean equals(int n){return value == n;} //Returns the veracity of the integer being equal to this integer's value
	public boolean equals(MyInteger n){return value == n.getValue();} //Gets the value of the MyInteger being passed and checks it against this value
	
	
	public static int parseInt(String n){ //This method simply converts the String parameter into a char array and passes it through to the char array version of the method
		char[] m = new char[n.length()];
		for(int i=0;i<n.length();i++){m[i] = n.charAt(i);}
		return parseInt(m);
	}
	public static int parseInt(char[] n){
		int output = 0;
		for(int i = 0;i<n.length;i++){
			output += ((n[i] - 48) * (int)Math.pow(10.0,n.length - (i+2))); //This code is meant to multiply the integer of the char array by a power of ten corresponding to its location in the array
		}
		return output;
	}
	
	public int getValue(){return value;}
}