import java.util.Scanner;

public class problem18_13{
	public static int recursions;
	
	public static void main(String[] args){
		Scanner input = new Scanner(System.in);
		int[] x = new int[8];
		for(int i=0;i<8;i++){
			System.out.prinlnt("Enter the next integer: ");
			x[i] = input.nextInt();
		}
		
		System.out.println("The highest number is " + findLargest(x));
	}
	
	public static int findLargest(int[] n){
		if(n.length == 1){return n[0];}
		else if(n.length == 2){
			if(n[0] > n[1]){return n[0];}
			else{return n[1];}
		}
		else{
			int[] a = new int[n.length / 2];
			int[] b = new int[(n.length / 2) + (n.length % 2)]
			
			for(int i=0;i<n.length;i++){
				if(i <= n.length / 2){a[i] = n[i];}
				else{b[i] = n[i + (n.length / 2) + 1];}
			}
			
			int x = findLargest(a);
			int y = findLargest(b);
			
			if(x > y){return x;}
			else{return y;}
		}
	}
}