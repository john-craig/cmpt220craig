import java.util.Scanner;

public class problem7_8{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		double[] values = new double[10];

		System.out.println("Please input ten values");

		for(int i=0;i<10;i++){
			values[i] = input.nextDouble();
		}

		System.out.println("The average of those values is " + average(values));
	}

	public static int average(int[] array){
		int sum = 0;
		for(int i=0;i<array.length;i++){
			sum += array[i];
		}
		return sum / array.length;
	}
	public static double average(double[] array){
		double sum = 0;
		for(int i=0;i<array.length;i++){
			sum += array[i];
		}
		return sum / array.length;
	}
}