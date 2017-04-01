import java.util.Scanner;
import java.util.ArrayList;

public class problem11_13{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> tester = new ArrayList<Integer>();
		
		for(int i=0;i<10;i++){
			System.out.print("Enter the next number: ");
			tester.add(input.nextInt());
		}
		
		for(int i=0;i<tester.size();i++){System.out.print(tester.get(i) + " ");}
	}

	// JA: A better approach is to recreate the list without duplications
	public static void removeDuplicate(ArrayList<Integer> list){
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		temp.add(list.get(0));
		
		for(int i=1;i<list.size();i++){
			boolean alreadyPresent = false;
			
			for(int j=0;j<temp.size();j++){
				if(temp.get(j) == list.get(i)){alreadyPresent = true; break;}
			}
			
			if(!alreadyPresent){temp.add(list.get(i));}
		}
	}
}