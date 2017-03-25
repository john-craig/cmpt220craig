import java.util.Scanner;

public class problem10_9{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		Course course = new Course("Computer Science");
		
		course.addStudent("Jim");
		course.addStudent("Billy");
		course.addStudent("Lily");
		course.dropStudent("Billy"); // JA: Missing ;
		
		String[] n = course.getStudents();
		for(int i = 0;i<n.length;i++){System.out.println(n[i] + " ");}
	}
}

class Course{ // JA: cannot be public because it's in the same file
	private String courseName;
	private String[] students = new String[100];
	private int numberOfStudents;
	
	public Course(String courseName){this.courseName = courseName;}
	
	public void addStudent(String student){
		if(numberOfStudents>=students.length){
			String[] temp = new String[students.length * 2]; // JA: Should be String[] type
			System.arraycopy(students, 0, temp, 0, students.length); // JA: Missing comma
			students = temp; 
		}
		students[numberOfStudents] = student; // JA: student, not students
		numberOfStudents++;
	}
	
	public String[] getStudents(){return students;}
	
	public int getNumberOfStudents(){return numberOfStudents;}
	
	public String getCourseName(){return courseName;}
	
	public void dropStudent(String student){
		for(int i = 0;i<numberOfStudents;i++){
			if(students[i] == student){
				students[i] = null;
				break;
			}
		}
	}
	
	public void clear(){
		students = new String[students.length];
	}
}