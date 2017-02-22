import java.util.Scanner;

public class problem8_1{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		double[][] numbers = new double[3][4];

		for(int i=0;i<3;i++){
			System.out.println("Enter the next row of numbers.");
			for(int j=0;j<4;j++){
				numbers[i][j] = input.nextDouble();
			}
		}

		System.out.println("The sum of numbers in column one is " + sumColumn(numbers,0));
		System.out.println("The sum of numbers in column two is " + sumColumn(numbers,1));
		System.out.println("The sum of numbers in column three is " + sumColumn(numbers,2));
		System.out.println("The sum of numbers in column four is " + sumColumn(numbers,3));
	}
	
	public static double sumColumn(double[][] m, int columnIndex){
		double sum = 0;

		for(int i=0;i<m.length;i++){sum+=m[i][columnIndex];}

		return sum;
	}
}