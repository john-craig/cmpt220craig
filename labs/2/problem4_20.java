import java.util.Scanner;

public class problem4_20{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a string: ");

		String x = input.next();

		System.out.println("The length of that string is " + x.length() + " characters, and the first letter is " + x.charAt(0));		
	}
}