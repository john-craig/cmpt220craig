import java.util.Scanner;

public class problem10_21{
	public static void main(String[] args) {
		double n = (double)Long.MAX_VALUE;
		double[] m = new double[5];
		int o = 0;
		
		while(o < 5){//This is supposed to repeatedly increase n and then check if it's divisible by 5 or 6; for some reason, it doesn't increase n each iteration
			n++;
			
			if((n % 5 == 0) || (n % 6 == 0)){
				m[o] = n;
				o += 1;
				
			}
		}
		
		for(int i = 0;i<5;i++){
			System.out.println(m[i]);
		}
	}
}
