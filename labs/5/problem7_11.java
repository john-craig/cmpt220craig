import java.util.Scanner;


// JA: Always add comments to your code
public class problem7_11{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		double[] numbers = new double[10];
		
		System.out.println("Please input ten numbers:");

		for(int i=0;i<10;i++){
			numbers[i] = input.nextDouble();
		}
		
		System.out.println("The mean of those numbers is " + mean(numbers) +", and the standard deviations is " + deviation(numbers));	
	}
	
	public static double mean(double[] x){
		double sum = 0;

		for(int i=0;i<x.length;i++){sum+=x[i];}
		
		return sum/x.length;
	}

	public static double deviation(double[] x){
		double ave = mean(x);

		for(int i=0;i<x.length;i++){
			x[i] = Math.pow(x[i] - ave, 2);
		}

		ave = 0;

		for(int i=0;i<x.length;i++){ave+=x[i];} 
		ave = ave / x.length - 1; // JA: You need to add parenthesis to the lower part of the equation (x.length - 1)

		return Math.sqrt(ave);
	}
}