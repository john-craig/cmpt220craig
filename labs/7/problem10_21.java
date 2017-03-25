import java.util.Scanner;
import java.math.BigInteger;

public class problem10_21{
	public static void main(String[] args) {
		//double n = (double)Long.MAX_VALUE;
		BigInteger n = new BigInteger(Long.MAX_VALUE + "");
		double[] m = new double[5];
		int o = 0;
		
		while(o < 10){//This is supposed to repeatedly increase n and then check if it's divisible by 5 or 6; for some reason, it doesn't increase n each iteration
//			n++;
			n = n.add(BigInteger.ONE);
			
		//	if((n % 5 == 0) || (n % 6 == 0)){ // JA: These numbers are too big, you cannot use the regular operators
		      if (n.remainder(new BigInteger("5")).equals(BigInteger.ZERO) ||
				  n.remainder(new BigInteger("6")).equals(BigInteger.ZERO)) {
			//	m[o] = n;
				System.out.println(n);
				o += 1;
				
			}
		}
		
//		for(int i = 0;i<5;i++){
//			System.out.println(m[i]);
//		}
	}
}
