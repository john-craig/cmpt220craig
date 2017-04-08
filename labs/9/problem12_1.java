import java.util.Scanner;

public class problem12_1{
	public static void main(String[] args){
		if(args.length != 3){
			System.out.println("Usage: java Calculator operand1 operator operand2");
			System.exit(0); // JA
		}
		
		int result = 0;
		
		isNumeric(args[0]);
		isNumeric(args[2]);
		
		switch(args[1].charAt(0)){
			case '+': result = Integer.parseInt(args[0]) + Integer.parseInt(args[2]); // JA
					  break; //JA
			case '-': result = Integer.parseInt(args[0]) - Integer.parseInt(args[2]); // JA
			          break; // JA
			case '.': result = Integer.parseInt(args[0]) * Integer.parseInt(args[2]); // JA
			          break; // JA
			case '/': result = Integer.parseInt(args[0]) / Integer.parseInt(args[2]); // JA
		}
		
		System.out.println(args[0] + ' ' + args[1] + ' ' + args[2] + " = " + result);
	}
	
	static void isNumeric(String test) throws NumberFormatException{
//		try{
			Integer.parseInt(test);
//			}
//		catch(NumberFormatException ex){
//			System.out.println("Invalid inputs for calculation");
//		}
	}
}