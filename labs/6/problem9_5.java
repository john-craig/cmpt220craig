import java.util.Scanner;
import java.util.GregorianCalendar;

public class problem9_5{
	public static void main(String[] args) {
		GregorianCalendar calendar = new GregorianCalendar();
		
		System.out.printlnt("The current date is: "+ calendar.get(GregorianCalendar.MONTH) +"/" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + calendar.get(GregorianCalendar.YEAR));
		calendar.setTimeInMillis(1234567898765L);
		System.out.printlnt("The current date is: "+ calendar.get(GregorianCalendar.MONTH) +"/" + calendar.get(GregorianCalendar.DAY_OF_MONTH) + "/" + calendar.get(GregorianCalendar.YEAR));
	}
}