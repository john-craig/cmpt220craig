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
		ArrayList<Integer> temp = new ArrayList<Integer>();
		
		temp.add(list.get(0));
		
		for(int i = 1;i<list.size();i++){
			Integer n = list.get(i);
			int position = 0;
			
			for(int j=0;j<temp.size();j++){
				if(n > temp.get(j)){position++;}
			}
			
			list.add(n, position);
		}
		
		list = temp;
	}
}