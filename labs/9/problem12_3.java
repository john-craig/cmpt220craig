import java.util.Scanner;

public class problem12_3{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int[] array = new int[100];
		
		for(int i = 0;i<100;i++){
			array[i] = (int)(Math.random() * 1000);
		}
		
		System.out.println("Input an index digit:");
		int index = input.nextInt();
		
		try{System.out.println(array[index]);}
		catch(ArrayIndexOutOfBoundsException ex){
			System.out.println("Out of Bounds");
		}
	}
}