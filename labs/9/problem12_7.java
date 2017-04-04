import java.util.Scanner;

public class problem12_7{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
	}
}

class bin2Dec(String binaryString){
	for(int i = 0;i<binaryString.length-1;i++){
		int test = Interger.parseInt(binaryString.substring(i, i+1))'
		if(test != 0 || test != 1){throw NumberFormatException;}
	}
	
	return Integer.parseInt(binaryString, 2);
}