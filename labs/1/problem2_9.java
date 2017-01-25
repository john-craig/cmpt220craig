import java.util.Scanner;

public class problem2_9{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input the starting velocity in meters/second: ");
		double startV = input.nextDouble();
		System.out.print("Please input the ending velocity in meters/second: ");
		double endV = input.nextDouble();
		System.out.print("Please input time span in seconds: ");
		double time = input.nextDouble();

		double accel = (endV - startV) / time;

		System.out.println("The average acceleration is " + accel);
	}
}