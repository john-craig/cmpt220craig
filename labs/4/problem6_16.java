// JA: Always add comments to your code
public class problem6_16{
	public static void main(String[] args) {
		int year = 2000;
		System.out.println("Year | Days");

		for(int i=0;i<=20;i++){
			System.out.println("" + (year+i) + " | " + numberOfDays(year+i));
		}
	}
	
	public static int numberOfDays(int year){
		if(((year % 4) == 0) && ((year % 100) == 0) && ((year % 400) == 0)){return 366;} // JA: This function is wrong
		// JA: The correct formula is (year % 400 == 0 ) || (year % 4 == 0 && year % 100 != 0) 
		else{return 365;}
	}
}