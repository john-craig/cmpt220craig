import java.util.Scanner;

public class problem5_16{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Please input an integer: ");
		int num = input.nextInt();
		String factors = "";

		// JA: When finding the smallest factors, you need to decompose the numbers
		// JA: For example, if the input is 340, you need to find the smallest number that is a factor 
		// JA: and divide by the number, i.e., 2 is a factor and now your number is 170. You keep doing that 
		// JA: until the number is 1. So the factors of 340 are 2,2,5,17.
		for(int i=1;i<=num;i++){
			if((num % i) == 0){
				factors = factors + i + ", ";
			}
		}
		System.out.println("The factors of the integer are: " + factors);
	}
}