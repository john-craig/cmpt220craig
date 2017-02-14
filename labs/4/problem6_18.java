import java.util.Scanner;

public class problem6_18{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.print("Please input your password: ");
		String pass = input.next();

		if(checkPassword(pass)){System.out.print("Password Valid");}
		else{System.out.print("Password Invalid");}
	}
	
	public static boolean checkPassword(String pass){
		if(pass.length() < 8){return false;}
		
		String nums = pass.replaceAll("[^0-9]","");
		String letters = pass.replaceAll("[^a-zA-Z]","");
		
		if(nums.length()<2){return false;}
		if((nums.length() + letters.length()) < pass.length()){return false;}

		return true;
	}
}