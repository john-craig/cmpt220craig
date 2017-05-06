import java.util.Scanner;

public class problem18_17{
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a list of characters: ");
		String s = scanner.next();
		s.replaceAll("\\s","");
		
		char[] c = new char[s.length()];
		for(int i=0;i<s.length();i++){c[i] = s.charAt(i);}
		
		System.out.println("Enter a character: ");
		Char a = scanner.next().charAt(0);
		
		System.out.println("The number of " + a + "'s in the array is " + count(s, a));
	}
	
	public static int count(char[] chars, char c){
		if(chars.length == 1){
			if(chars[0] == c){return 1;}
			else{return 0;}
		}
		else{
			return count(chars, c, chars.length / 2) + count(chars, c, chars.length);
		}
	}
	
	public static int count(char[] chars, char c, int high){
		int index = high;
		if(high < chars.length / 2){index = (chars.length / 2) + high;}
		
		if(chars.length - index == 1 || index == 1 || index == 0){
			if(chars[0] == c){return 1;}
			else{return 0;}
		}
		else{
			return count(chars, c, index - (high / 2)) + count(chars, c, index + (high / 2));
		}
	}
}