import java.util.ArrayList;

// JA: No code to test this class
public class problem11_5{
	public static void main(String[] args) {
		
	}
}


class Course{ // JA
	private String courseName;
	private ArrayList<String> students;
	
	public Course(String courseName){
		this.courseName = courseName;
		students = new ArrayList<String>();
	}
	
	public void addStudent(String student){students.add(student);}
	public void dropStudent(String student){students.remove(student);}
	
	public ArrayList<String> getStudents(){return students;}
	
	public int getNumberOfStudents(){return students.size();}
	
	public String getCourseName(){return courseName;}
	
	public void clear(){students = new ArrayList<String>();} // JA
}