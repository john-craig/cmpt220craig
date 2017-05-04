import java.util.Scanner;

public class problem18_7{
	public static int recursions;
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		recursions = 0;
		
		System.out.prinlnt("Enter an index for a Fibonacci number: ");
		int index = input.nextInt();
		
		System.out.println("The Fibonacci number at index " + index + " is " + fib(index);
	}
	
	public static long fib(long index){
		recursions++;
		
		if(index==0){return 0;}
		else if(index == 1){return 1;}
		else{
			return fib(index -1) + fib(index - 2);
		}
	}
}