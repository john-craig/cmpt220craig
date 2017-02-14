import java.util.Scanner;

public class problem7_2{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		int[] nums = new int[10];
		
		for(int i = 0;i<10;i++){
			System.out.print("Please input an integer:");
			nums[i] = input.nextInt();
		}
		for (int i = 9;i>0;i--){
			System.out.print("" + nums[i] + ", ");
		}
		System.out.print(nums[0]);
	}
}