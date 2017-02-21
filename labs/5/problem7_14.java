import java.util.Scanner;

public class problem7_14{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int[] numbers = new int[5];

		System.out.println("Please input five numbers:");
		for(int i = 0;i<5;i++){numbers[i]=input.nextInt();}

		System.out.println("The greatest common divisor of those numbers is " + gcd(numbers));
	}
	
	public static int gcd(int... numbers){
		int gcd = 1;
		int highest = 0;

		for(int i=0;i<numbers.length;i++){
			if(numbers[i]>highest){highest = numbers[i];}
		}

		for(int i=1;i<=highest;i++){
			boolean isDenominator = false;

			for(int j=0;j<numbers.length;j++){
				isDenominator = numbers[j] % i == 0;

				if(!isDenominator){break;}
			}

			if(isDenominator){gcd = i;}
		}

		return gcd;
	}
}