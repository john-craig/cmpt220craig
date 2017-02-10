import java.util.Scanner;

// JA: You should add comments to your code
public class problem5_1{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		System.out.print("Enter a list of single-digit integers; the list will end if it is 0: ");
		String list = input.next();

		int positives = 0;
		int negatives = 0;
		int total = 0;
		double average = 0.0;
		boolean empty = false;

		for(int i = 0; i<list.length(); i++){
			if(Character.getNumericValue(list.charAt(i)) == 0){
				if(i==0){empty=true;}
				break;
			}

			if(list.charAt(i) == '-'){
				negatives++;
				average -= Character.getNumericValue(list.charAt(i+1));
				i++;
			}
			else{
				positives++;
				average+=Character.getNumericValue(list.charAt(i));
			}
			average = total / (positives + negatives);  // JA: This will be an integer operation, which is wrong. YOu need to force it to be double.
		}
		
		if(!empty){
			System.out.println("The total number of positive numbers is " + positives + "\nThe total number of negative numbers is " + negatives + "\nThe average of the numbers is " + average);
		}
		else{System.out.println("That list contains only zero");}
	}
}