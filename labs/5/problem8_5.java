import java.util.Scanner;

public class problem8_5{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		double[][] matrixA = new double[3][4];
		double[][] matrixB = new double[3][4];

		System.out.println("Enter first matrix:");
		for(int i = 0;i<3;i++){
			for(int j=0;j<3;j++){
				matrixA[i][j] = input.nextDouble();
			}
		}
		System.out.println("Enter second matrix:");
		for(int i = 0;i<3;i++){
			for(int j=0;j<3;j++){
				matrixB[i][j] = input.nextDouble();
			}
		}

		double[][] matrixC = addMatrix(matrixA, matrixB);

		for(int i = 0;i<3;i++){
			System.out.println();
			for(int j=0;j<3;j++){
				System.out.print(matrixC[i][j] + " ");
			}
		}
	}
	
	public static double[][] addMatrix(double[][] a, double[][] b){
		double[][] sum = new double[a.length][a[0].length];

		for(int i = 0;i<a.length;i++){
			for(int j = 0;j<a[0].length;j++){sum[i][j] = a[i][j] + b[i][j];}
		}

		return sum;
	}
}