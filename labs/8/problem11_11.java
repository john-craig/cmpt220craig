import java.util.Scanner;
import java.util.ArrayList;

public class problem11_11{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		ArrayList<Integer> tester = new ArrayList<Integer>();
		
		for(int i=0;i<5;i++){
			System.out.print("Enter the next number: ");
			tester.add(input.nextInt());
		}
		
		sort(tester);
		
		for(int i = 0;i<tester.size();i++){System.out.print(tester.get(i) + " ");}
	}

	
	public static void sort(ArrayList<Integer> list){
		//ArrayList<Integer> temp = new ArrayList<Integer>();
		
		//temp.add(list.get(0));
		
		for(int i = 0;i<list.size() - 1;i++){ // JA
			Integer n = list.get(i);
			int position = 0;
			
			for(int j=i+1;j<list.size();j++){ // JA
				if( list.get(i) > list.get(j)){position++; // JA
					Integer temp = list.get(i); // JA
					list.set(i, list.get(j)); // JA
					list.set(j, temp); // JA
					//list.add(n, position); // JA
				}
			}
		}
		
//		list = temp; // JA
	}
}