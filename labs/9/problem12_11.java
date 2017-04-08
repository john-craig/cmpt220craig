import java.io.*;
import java.util.*;

public class problem12_11{
	public static void main(String[] args) {
		File file = new File(args[1]);
		if(!file.exists()){
			System.out.println("File " + args[1] + " not found");
			System.exit(1);
		}
		
		try{
			Scanner input = new Scanner(file);
			PrintWriter output = new PrintWriter(file);
	// JA	{
			while(input.hasNext()){
				String s1 = input.nextLine(); // JA
				String s2 = s1.replaceAll(args[0], ""); // JA
				output.println(s2);
			}
		}
		catch(Exception ex) {
		}
	}
}