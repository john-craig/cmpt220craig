import java.util.Scanner;

public class problem6_3{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Input a 3-digit number");
		int num = input.nextInt();

		if(isPalindrome(num)){System.out.println("That number is a palindrome!");}
		else{System.out.println("That number is not a palindrome!");}
	}
	
	public static int reverse(int number){
		int a = number / 100;
		int b = (number / 10) % 10;
		int c = number % 10;
		
		return (c * 100) + (b * 10) + a;
	}
	public static boolean isPalindrome(int number)
	{
		return reverse(number) == number;
	}
}