public class problem1_4 {
	public static void main(String[] args) {
		String x = "     ";
		System.out.println("a     a^2   a^3");
		for(int i=1;i<5;i++){
			int a = i*i;
			int b = i*i*i;
			System.out.println(i + x + a + x + b);
		}
	}
}