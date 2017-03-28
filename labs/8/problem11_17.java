import java.util.Scanner;
import java.util.ArrayList;

public class problem11_17{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean perfectSquare = false;
		int m;
		int n = 0;
		
		System.out.println("Enter an integer: ");
		m = input.nextInt();
		
		while(!perfectSquare){
			n++;
			perfectSquare = checkPerfectSquare(m * n);
		}
		
		System.out.println("The smallest integer n such that m * n is a perfect square is: " + n);
	}
	
	public static boolean checkPerfectSquare(int test){
		double a = Math.sqrt(test); //Gets the square root of the number being
		int b = (int)a; //Truncates the square root of the number
		a = a - (double)b; //By subtracted b from a, we can find whether or not there are any decimals on the end of the square root
		
		return (a == 0);
	}
}