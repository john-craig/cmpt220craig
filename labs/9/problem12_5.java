import java.util.Scanner;

public class problem12_5{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		try
		{
			new Triangle(3, 4, 8); // JA
			
		}
		catch (IllegalTriangleException ex)
		{
			System.out.println(ex.getMessage()); //JA
		}
	}
}

class GeometricObject{
	private String color = "white";
	private boolean filled;
	private java.util.Date dateCreated;
	
	public GeometricObject(){
		dateCreated = new java.util.Date();
	}
	
	public GeometricObject(String color, boolean filled){
		dateCreated = new java.util.Date();
		this.color = color;
		this.filled = filled;
	}
	
	public String getColor(){return color;}
	public void setColor(String color){this.color = color;}
	
	public boolean isFilled(){return filled;}
	public void setFilled(boolean filled){this.filled = filled;}
	
	public java.util.Date getDateCreated(){return dateCreated;}
	
	public String toString(){return "created on " + dateCreated + "\ncolor: " + color + " and filled: " +filled;}
}

class Triangle extends GeometricObject{
	private double side1;
	private double side2;
	private double side3;
	
	public Triangle(){
		side1 = 1.0;
		side2 = 1.0;
		side3 = 1.0;
	}
	
	public Triangle(double s1, double s2, double s3) throws IllegalTriangleException { // JA
		if((s1 + s2 < s3) || (s1 + s3 < s2) || (s2 + s3 < s1)){
			throw new IllegalTriangleException(s1, s2, s3); // JA
		}
		
		side1 = s1;
		side2 = s2;
		side3 = s3;
	}
	
	public double getArea(){
		double n = this.getPerimeter() / 2;
		
		return Math.sqrt(n * (n - side1) * (n - side2) * (n - side3));
	}
	
	public double getPerimeter(){return (side1 + side2 + side3);}
	
	public String toString(){
		return "Triangle: side1 = " + side1 + " side2 = " + side2 + " side3 = " + side3;
	}
	
	public double getSide1(){return side1;}
	public double getSide2(){return side2;}
	public double getSide3(){return side3;}
}

class IllegalTriangleException extends Exception{
	public IllegalTriangleException(double s1, double s2, double s3){
		super("Invalid triangle dimensions " + s1 + " " + s2 + " " + s3);
	}
}