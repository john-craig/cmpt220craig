import java.util.Scanner;

public class problem9_11{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		double[] nums = new double[6];
		for(int i=0;i<6;i++){
			System.out.println("Enter the next number: ");
			nums[i] = input.nextDouble();
		}

		LinearEquation tester = new LinearEquation(nums[0], nums[1], nums[2], nums[3], nums[4], nums[5]);
		if(tester.isSolvable()){
			System.out.println("The X value is " + tester.getX() + ", and the Y value is " + tester.getY());
		}
		else{System.out.println("The equation has no solution.");}
	}
}

class LinearEquation{
	private double a;
	private double b;
	private double c;
	private double d;
	private double e;
	private double f;
	
	LinearEquation(double a, double b, double c, double d, double e, double f){
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
		this.e = e;
		this.f = f;
	}

	double getA(){return a;}
	double getB(){return b;}
	double getC(){return c;}
	double getD(){return d;}
	double getE(){return e;}
	double getF(){return f;}

	boolean isSolvable(){return (a * d) - (b *c) != 0;}
	double getX(){return ((e * d) - (b * f)) / ((a * d) - (b * c));}
	double getY(){return ((a * f) - (e * c)) / ((a * d) - (b * c));}
}