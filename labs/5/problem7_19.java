import java.util.Scanner;

public class problem7_19{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);

		System.out.println("Please input the length of the list:");
		
		int length = input.nextInt();
		int[] list = new int[length];

		System.out.println("Please input the list:");

		for(int i=0;i<list.length;i++){
			list[i] = input.nextInt();
		}
		
		System.out.println("It is "+ isSorted(list) + " that this list is a sorted list.");	
	}
	
	public static boolean isSorted(int[] list){
		boolean isSorted = false;

		for(int i=1;i<=list.length-1;i++){
			isSorted = list[i-1] < list[i];

			if(!isSorted){break;}
		}

		return isSorted;
	}
}