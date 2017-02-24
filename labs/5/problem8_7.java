import java.util.Scanner;

// JA: Always add comments to your code
public class problem8_7{
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		
		double[][] points = {{-1,0,3},{-1,-1,-1},{4,1,1},{2,.5,9},{3.5,2,-1},{3,1.5,3},{-1.5,4,2},{5.5,4,-.5}};

		System.out.println(closestDistance(points));
	}
	
	public static double closestDistance(double[][] points){
		double distance = 100;
		double[] pointA = new double[3];
		double[] pointB = new double[3];
		
		for(int i = 0;i<points.length;i++){
			for(int j = 0;j<points[i].length;j++){pointA[j] = points[i][j];}

			for(int j = 0;j<points[i].length;j++){
				for(int k = 0;k<points[i].length;j++){pointB[k] = points[i][k];}

				if(distanceBetweenPoints(pointA, pointB)<distance){distance = distanceBetweenPoints(pointA, pointB);}
			}
		}
		if(distance < Double.100){return distance;} // JA: Were you trying to cast it to a double?
		else{return -1;}
	}
	
	public static double distanceBetweenPoints(double[] pointA, double[] pointB){
		double x = Math.pow(pointB[0] - pointA[0],2);
		double y = Math.pow(pointB[1] - pointA[1],2);
		double z = Math.pow(pointB[2] - pointA[2],2);

		return Math.sqrt(x + y + z);
	}
}