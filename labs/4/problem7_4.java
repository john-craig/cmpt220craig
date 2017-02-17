import java.util.Scanner;

// JA: Always add comments to your code
public class problem7_4{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int[] scores = new int[100];
		int sum = 0;
		int amt = 0;

		for(int i=0;i<100;i++){
			System.out.print("Input a score: ");
			int score = input.nextInt();
			if(score < 0){break;}
			else{
				scores[i] = score;
				sum = sum+=score;
				amt++;
			}
		}

		int average = sum / amt; // JA: This has to be a non-integer calculation
		int aboveAve = 0;
		int belowAve = 0;
		
		for(int i=0;i<amt;i++){
			if(scores[i]>=average){aboveAve++;}
			else{belowAve++;}
		}
		
		System.out.println("The number of scores above the average is " + aboveAve + ", the number below the average is " + belowAve);
	}
}