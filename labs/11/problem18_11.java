import java.util.Scanner;

public class problem18_11{
	public static int recursions;
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.prinlnt("Enter a number to be summed: ");
		int x = input.nextInt();
		
		System.out.println("The sum of " + x + " is " + sumDigits(x));
	}
	
	public static int sumDigits(long n){
		if(n < 10){return n;}
		else{return sumDigits(n / 10) + sumDigits(n % 10);}
	}
}