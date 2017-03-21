import java.util.Scanner;

public class problem10_11{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		Circle2D cl = new Circle2D(2, 2, 5.5);
		System.out.println("Area: " + cl.getArea() + " Perimeter: " + cl.getPerimeter());
		System.out.println("" + cl.contains(new Circle2D(4,5,10.5)) + " " + cl.overlaps(new Circle2D(3,5,2.3)));
	}
}

class Circle2D{
	double x;
	double y;
	double radius;
	
	public Circle2D(double x, double y, double radius){
		this.x = x;
		this.y = y;
		this.radius = radius;
	}
	
	public double getArea(){
		return Math.PI * Math.pow(radius, 2);
	}
	
	public double getPerimeter(){
		return 2 * Math.PI * radius;
	}
	
	public boolean contains(double x, double y){ //Determines if a point is within the circle by making certain it's within the diameter of the diameter of the circle
		boolean inside = true;
		
		for(int i = 0;i<360;i++){
			
			double pointA = (this.x + this.radius) * Math.cos(Math.toRadians(i));
			double pointB = (this.y + this.radius) * Math.sin(Math.toRadians(i));
			
			if((i <= 90 && i > 0) && (x > pointA || y > pointB)){inside = false;}
			if((i <= 180 && i > 90) && (x < pointA || y > pointB)){inside = false;}
			if((i <= 270 && i > 180) && (x < pointA || y < pointB)){inside = false;}
			if((i <= 360 && i > 270) && (x > pointA || y < pointB)){inside = false;}
		
			if(!inside){break;}
		}
	
		return inside;
	}
	
	public boolean contains(Circle2D circle){ //Determines if a circle is within the circle by making certain it's origin is within the circle, and its diameter is less
		return contains(circle.getX(),circle.getY()) && (circle.getRadius() < radius);
	}
	
	public boolean overlaps(Circle2D circle){
		boolean overlaps = false;
		
		for(int i = 0;i<360;i++){
			double pointA = (circle.getX() + circle.getRadius()) * Math.cos(Math.toRadians(i));
			double pointB = (circle.getX() + circle.getRadius()) * Math.sin(Math.toRadians(i));
			
			overlaps = this.contains(pointA,pointB);
			
			if(overlaps){break;}
		}
		
		return overlaps;
	}
	
	public double getX(){return x;}
	public double getY(){return y;}
	public double getRadius(){return radius;}
}