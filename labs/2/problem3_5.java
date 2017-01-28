import java.util.Scanner;

public class problem3_5{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		String[] daysOfTheWeek = {"Sunday","Monday","Tuesday","Wednesday","Thursday","Friday","Saturday"};

		System.out.println("What is the number of this day of the week?");
		int day = input.nextInt();

		System.out.println("How many days in the future?");
		int future = input.nextInt() % 7;

		System.out.println("Today is " + daysOfTheWeek[day] + ", and the future day is " + daysOfTheWeek[day+future]);
	}
}