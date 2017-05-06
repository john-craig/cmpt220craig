import java.util.Scanner;

public class problem18_15{
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		
		System.out.println("Enter a string: ");
		String s = scanner.next();
		
		System.out.println("Enter a character: ");
		Char a = scanner.next().charAt(0);
		
		System.out.println("The number of " + a + "'s in the string " + s + " is " + count(s, a));
	}
	
	public static int count(String str, char a){
		if(str.length() == 1 && str.charAt(0) == a){return 1;}
		else if(str.length() == 1){return 0;}
		else{
			return count(str, a, str.length / 2) + count(str.substring(str.length / 2), a);
		}
	}
	
	public static int count(String str, char a, int high){
		str = str.substring(0, high);
	
		if(str.length() == 1 && str.charAt(0) == a){return 1;}
		else if(str.length() == 1){return 0;}
		else{
			return count(str, a, str.length / 2) + count(str.substring(str.length / 2), a);
		}
	}
}