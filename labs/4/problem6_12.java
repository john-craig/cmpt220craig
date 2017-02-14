public class problem6_12{
	public static void main(String[] args) {
			printChars('1','Z',10);
	}
	
	public static void printChars(char ch1, char ch2, int numberPerLine){
		for(int i = (int)ch1; i < (int)ch2; i++){
			for(int j=0;j<numberPerLine;j++){
				System.out.print((char)i + " ");
				i++;
				if(i>(int)ch2){break;}
			}
			System.out.println();
		}
	}
}