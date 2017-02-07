import java.util.Scanner;

public class problem5_16{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input an integer: ");
		int num = input.nextInt();
		String factors = "";

		for(int i=1;i<=num;i++){
			if((num % i) == 0){
				factors = factors + i + ", ";
			}
		}
		System.out.println("The factors of the integer are: " + factors);
	}
}