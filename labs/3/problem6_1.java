import java.util.Scanner;

public class problem6_1{
	public static void main(String[] args) {
		for(int i=0;i<10;i++){
			String output = "";

			for(int j = 0; j<10;j++){
				output = output + getPentagonalNumber((i * 10) + j) + " ";
			}
			System.out.println(output);
		}
	}
	
	public static int getPentagonalNumber(int n){
		return (n * (3 * n - 1) / 2);
	}
}