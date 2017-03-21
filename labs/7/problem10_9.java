import java.util.Scanner;

public class problem10_9{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		Course course = new Course("Computer Science");
		
		course.addStudent("Jim");
		course.addStudent("Billy");
		course.addStudent("Lily");
		course.dropStudent("Billy")
		
		String[] n = course.getStudents();
		for(int i = 0;i<n.length;i++){System.out.println(n[i] + " ");}
	}
}

public class Course{
	private String courseName;
	private String[] students = new String[100];
	private int numberOfStudents;
	
	public Course(String courseName){this.courseName = courseName;}
	
	public void addStudent(String student){
		if(numberOfStudents>=students.length){
			int[] temp = new int[students.length * 2];
			System.arraycopy(students, 0, temp 0, students.length);
			students = temp;
		}
		students[numberOfStudents] = students;
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