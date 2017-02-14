public class problem6_9{
	public static void main(String[] args) {
		System.out.println("Feet   Meters | Meters     Feet");
		for(int i=1; i<=10; i++){
			double foot = i * 1.0;
			double meter = (i * 5.0) + 15;
			
			System.out.print(foot + "     " + footToMeter(foot));			
			System.out.print(" | ");
			System.out.println(meter + "     " + meterToFoot(meter));
		}
	}
	
	public static double footToMeter(double foot){return (foot * .305);}
	public static double meterToFoot(double meter){return (meter * 3.279);}
}