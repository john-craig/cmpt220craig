public class problem9_1{
	public static void main(String[] args) {
		Rectangle rectangle1 = new Rectangle(4, 40);
		Rectangle rectangle2 = new Rectangle(3.5, 35.9);

		System.out.println("The width of the first rectangle is " + rectangle1.width + ", its height is " + rectangle1.height + ", its area is " + rectangle1.getArea() + ", and its perimeter is " + rectangle1.getPerimeter());
		System.out.println("The width of the first rectangle is " + rectangle2.width + ", its height is " + rectangle2.height + ", its area is " + rectangle2.getArea() + ", and its perimeter is " + rectangle2.getPerimeter());
	}
}

class Rectangle{
	double width;
	double height;

	Rectangle(){
		width = 1.0;
		height = 1.0;
	}
	Rectangle(double width, double height){
		this.width = width;
		this.height = height;
	}

	double getArea(){
		return width*height;
	}
	double getPerimeter(){
		return (width*2) + (height*2);
	}

}