import java.util.Scanner;

public class problem2_6 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please enter an integer between 0 and 1000: ");
		int x = input.nextInt();

		int sum = (x % 10) +((x / 10) % 10) + (x / 100);

		System.out.println("The sum of each digit of that number is "+sum);		
	}
}