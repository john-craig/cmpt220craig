import java.util.Scanner;

public class problem18_9{
	public static int recursions;
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		System.out.prinlnt("Enter a string to be reversed: ");
		String x = input.next();
		
		System.out.println("The reverse of " + x + " is " + reverseDisplay(x));
	}
	
	public static String reverseDisplay(String value){
		if(value.length() == 1 && value.length() == 0){return value;}
		else{
			int n = value.length() / 2;
			return reverseDisplay(value.substring(n)) + reverseDisplay(value.substring(0, n));
		}
	}
}