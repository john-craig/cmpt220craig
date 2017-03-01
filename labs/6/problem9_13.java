import java.util.Scanner;

public class problem9_13{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.println("What are the dimensions of the array?");
		int a = input.nextInt();
		int b = input.nextInt();

		double[][] matrix = new double[a][b];
		
		for(int i = 0;i<a;i++){
			for(int j = 0;j<b;j++){
				System.out.println("Enter the next number: ");
				matrix[i][j] = input.nextDouble();
			}
		}

		Location loc = Location.locateLargest(matrix);
		System.out.println("The largest number in that array is " + loc.maxValue + ", and the coordinates are (" + loc.row + "," + loc.column +")");
	}
}

class Location{
	public int row;
	public int column;
	public double maxValue;

	public static Location locateLargest(double[][] a){
		Location b = new Location();
		b.maxValue = 0;

		for(int i = 0;i<a.length;i++){
			for(int j = 0;j<a[i].length;j++){
				if(a[i][j]>b.maxValue){
					b.maxValue = a[i][j];
					b.row = i;
					b.column = j;
				}
			}
		}
		return b;
	}
}