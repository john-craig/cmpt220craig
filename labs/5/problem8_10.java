import java.util.Scanner;

public class problem8_10{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		int[][] matrix = new int[4][4];
		
		for(int i = 0;i<4;i++){
			System.out.println();
			for(int j = 0;j<4;j++){
				matrix[i][j] = (Math.random() * 2);
				System.out.print(matrix[i][j]);
			}
		}

		System.out.println(findHighest(matrix));
		System.out.println(findHighest(rotateMatrix(matrix)));
	}

	public static int findHighest(int[][] matrix){
		int highestRow = 0;
		int sum1 = 0;
		int sum2 = 0;

		for(int i = 0;i<4;i++){
			for(int j=0;j<4;j++){sum1+=matrix[i][j];}
			if(sum1>sum2){
				sum2 = sum1;
				highestRow = i;
			}
		}
		
		return highestRow;
	}

	public static int[][] rotateMatrix(int[][] matrix){
			int[][] newMatrix = new int[matrix[0].length][matrix.length];

			for(int i=0;i<newMatrix.length;i++){
				for(int j=0;j<newMatrix[0].length;j++){
					newMatrix[i][j] = matrix[j][i];
				}
			}

			return newMatrix[]
	}
}