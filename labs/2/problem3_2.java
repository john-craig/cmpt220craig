import java.util.Scanner;

public class problem3_2{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int a = (int)(10 * Math.random());
		int b = (int)(10 * Math.random());
		int c = (int)(10 * Math.random());

		System.out.println("What is the sum of "+ a + ", " + b + ", and " + c + "?");
		int reply = input.nextInt();

		if(reply == (a+b+c)){System.out.println("That is correct!");}
		else{System.out.println("That is incorrect!");}
	}
}