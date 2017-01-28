import java.util.Scanner;

public class problem4_9{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a single character:");

		String x1 = input.next();
		int x2 = (int)x1.charAt(0);

		System.out.println("The Unicode of that character is " + x2);
	}
}