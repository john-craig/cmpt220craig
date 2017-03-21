import java.util.Scanner;

public class problem10_21{
	public static void main(String[] args) {
		
	}
}

class MyString2{
	private String str;
	
	public MyString2(String s){
		str = s;
	}
	
	public int compare(String s){ //Converts both to chars arrays and then adds together their lexographical values, and checks them against one another
		int output = 0;
		MyString2 other = new MyString2(s);
		
		char[] charsA = this.toChars();
		char[] charsB = other.toChars();
		int a = 0;
		int b = 0;
		
		for(int i = 0;i<charsA.length;i++){a += charsA[i];}
		for(int i = 0;i<charsB.length;i++){b += charsB[i];}
		
		(a > b) ? output = 1 : output = -1;
		return output;
		
	}
	
	public MyString2 substring(int begin){ //Converts the String to a char array and then cuts off the portion before the begin integer
		char[] temp = this.toChars();
		String output = "";
		
		for(int i = 0;i < temp.length - begin;i++){
			output = output + temp[begin + i];
		}
		
		return new MyString(output);
	}
	
	public MyString2 toUpperCase(){
		char[] temp = this.toChars();
		String output = "";
		
		for(int i = 0;i<temp.length;i++){
			(temp[i] > 59) ? output += (char)(temp[i] + 32) : output += temp[i];
		}
		
		return new MyString2(output);
	}
	
	public char[] toChars(){ //Iterates through the String and gets the charAt each position
		char[] output = new char[str.length()]
		
		for(int i = 0;i<str.length;i++){
			output[i] = str.charAt(i);
		}
		
		return output;
	}
	
	public static MyString2 valueOf(boolean b){
		String m = "";
		
		b ? m = "true" : m = "false";
		
		return new MyString(m);
	}