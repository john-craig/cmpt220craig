import java.util.Scanner;

public class problem7_10{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		double[] numbers = new double[10];

		System.out.println("Please input ten numbers:");
		for(int i=0;i<10;i++){
			numbers[i] = input.nextDouble();
		}

		System.out.println("The smallest number is " + numbers[indexOfSmallestElement(numbers)]);
	}
	
	public static int indexOfSmallestElement(double[] array){
		int n = 0;

		for(int i=0;i<array.length;i++){
			if(array[i]<array[n]){n = i;}
		}

		return n;
	}
}