import java.util.Scanner;

public class problem12_7{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		System.out.println(bin2Dec("111")); // JA
		System.out.println(bin2Dec("345")); // JA
	}

	// JA: This is a method, not a class
	public static int bin2Dec(String binaryString){
		for(int i = 0;i<binaryString.length()-1;i++){ // JA
			int test = Integer.parseInt(binaryString.substring(i, i+1)); // JA
			if(test != 0 || test != 1){throw new NumberFormatException();} // JA
		}
		
		return Integer.parseInt(binaryString, 2);
	}
}

