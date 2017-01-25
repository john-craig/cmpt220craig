import java.util.Scanner;

public class problem2_1 {
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.print("Please input a temperature in Celsius: ");
		double tempC = input.nextDouble();
		double tempF = (9.0 / 5) * tempC + 32;
		System.out.println("That temperature in Farenheit is: " + tempF);
	}
}