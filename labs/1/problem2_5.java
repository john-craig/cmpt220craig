import java.util.Scanner;

public class problem2_5 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please enter the subtotal: ");
		int subtotal = input.nextInt();
		System.out.print("Please enter the gratuity rate: ");
		int gratRate = input.nextInt();
		
		double gratuity = subtotal * (gratRate / 100.0);
		double total = subtotal + gratuity;

		System.out.println("The tip is $" + gratuity + ", and the total is $" + total);
	}
}