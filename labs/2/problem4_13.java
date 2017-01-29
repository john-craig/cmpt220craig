import java.util.Scanner;

public class problem4_13{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a single letter:");

		String x1 = input.next();
		int x2 = (int)x1.charAt(0);
		boolean isVowel;

		if((x2>=65 && x2<=90)||(x2>=97 && x2<=122)){
			isVowel = (x2 == 65)||(x2 == 69)||(x2 == 73)||(x2 == 79)||(x2 == 87)||(x2 == 97)||(x2==101)||(x2 == 105)||(x2==111)||(x2 == 117);
			
			if(isVowel){System.out.println("That letter is a vowel");}
			else{System.out.println("That letter is a consonant");}
		}
		else{System.out.println("That letter is invalid");}
	}
}