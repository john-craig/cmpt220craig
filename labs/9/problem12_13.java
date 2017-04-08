import java.io.*;
import java.util.*;

public class problem12_13{
	public static void main(String[] args) {
		File file = new File(args[0]);
		if(!file.exists()){
			System.out.println("File " + args[1] + " not found");
			System.exit(0);
		}
		
		int lines = 0;
		int words = 0;
		int characters = 0;
		
		// JA
		try{
			Scanner input = new Scanner(file);
			while(input.hasNext()){
				lines++;
				String currentLine = input.nextLine();
				
				for(int i = 0;i<currentLine.length();i++){ // JA
					if((int)currentLine.charAt(i) == 1){
						words++;
					}
					else{characters++;}
				}
			}
			
			System.out.println("File contains " + lines + " lines, " + words + " words, and " + characters + " characters");
		} catch(Exception ex) {
		}
	}
}