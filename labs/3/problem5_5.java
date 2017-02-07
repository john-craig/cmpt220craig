import java.util.Scanner;

public class problem5_5{
	public static void main(String[] args) {
		
		System.out.println("Kilograms   Pounds | Pounds     Kilograms");
		for(int i=0; i<100; i++){
			int kg = (i * 2) + 1;
			double conLbs = kg * 2.2;
			int lbs = (i * 5) + 20;
			double conKg = lbs * .45;
			
			if(kg<10){
				System.out.print(kg + "           " + conLbs);
			}
			else if(kg<100){
				System.out.print(kg + "          " + conLbs);
			}
			else{
				System.out.print(kg + "          " + conLbs);
			}
			
			System.out.print(" | ");
			
			if(lbs<10){
				System.out.println(lbs + "             " + conKg);
			}
			else if(kg<100){
				System.out.println(lbs + "            " + conKg);
			}
			else{
				System.out.println(lbs + "            " + conKg);
			}
		}
	}
}