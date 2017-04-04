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
		){
			while(input.hasNext()){
				Strng s1 = input.nextLine();
				String s2 = s1.replaceall(ars[0], "");
				output.println(s2);
			}
		}
	}
}