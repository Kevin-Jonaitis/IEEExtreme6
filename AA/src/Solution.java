import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;
import java.util.Vector;


public class Solution {

	
	public static final int UP = 1;
	public static final int DOWN = -1;
	public static final int RIGHT = 1;
	public static final int LEFT = -1;
	public static final int NEUTRAL = 0;
	
	public static int horizontal;
	public static int vertical;
	
	public static Point startPoint;
	public static Point endPoint;
	public static Point currentPoint;
	
	public static int totalMovement = 0;
	
	static int board[][] = new int[30][30];

	
	
	public static class Point {
		int x;
		int y;
		
		public Point() {
			x = -999;
			y = -999;
		}
		
		public Point realMiddle(Point endPoint) {
			Point testPoint = new Point();
			testPoint.x = endPoint.x - 1;
			testPoint.y = endPoint.y;
			return testPoint;
		}
	}
	
	public static void main(String[] args) throws IOException {
		

		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String line = br.readLine();
        String parseThis = line;
        Scanner scan1 = new Scanner(parseThis).useDelimiter(",");
        String first = scan1.next();
        String second = scan1.next();
        
//        System.out.println(first + "     " + second);

        
        Scanner scan2 = new Scanner(first).useDelimiter("\\.");
//
        int firstX = Integer.parseInt(scan2.next());
        int firstY = Integer.parseInt(scan2.next());
        Scanner scan3 = new Scanner(second).useDelimiter("\\.");
        int secondX = Integer.parseInt(scan3.next());
        int secondY = Integer.parseInt(scan3.next());
//        System.out.println(first + "     " + second);
        
//        int N = Integer.parseInt(line);
//        for (int i = 0; i < N; i++) {
//            System.out.println("hello world");
//        }
        
        
		//set all values to 0e
		for(int i = 0; i < 30; i++)
		{
			for (int j = 0; j < 30; j++) {
			board[i][j] = 0;
			}
		}

		for (int i = 2; i<30; i+=4) {
			for(int j = 1; j < 30; j+=2) {
			board[i][j] = 1;
			board[i+1][j] = 1;
			}
		}
		 
		 
		 for (int i = 1; i<30; i+=4) {
			for(int j = 2; j < 30; j+=2) {
			board[i][j] = 1;
			board[i-1][j] = 1;
			}
		}
		 
		 
		startPoint = new Point();
		endPoint = new Point();
		currentPoint = new Point();
		
		Point startHex = new Point();
		Point endHex = new Point();
		
//		startHex.x = firstX;
//		startHex.y = firstY;
//		
//		endHex.x = secondX;
//		endHex.y = secondY;
		
		startHex.x = 3;
		startHex.y = 1;
		
		endHex.x = 4;
		endHex.y = 5;

		
		if (startHex.x == endHex.x && startHex.y == endHex.y) {
			System.out.println(0);
			return;
			}
		
		if(startHex.x > 12 || startHex.x < 1 || startHex.y > 12 || startHex.y < 1 || endHex.x < 1 || endHex.x > 12 || endHex.y > 12 || endHex.y < 1) {
			System.out.println(0);
			return;
		}
		startPoint = hexToCartesian(startHex);
		endPoint = hexToCartesian(endHex);
		
		currentPoint.x = startPoint.x;
		currentPoint.y = startPoint.y;
		
		calculateDirection();
		findAndMoveToStartPoint();
		//increment by 5, since moving from center to edge costs 5
		
//		System.out.println(currentPoint.x + "  " + currentPoint.y);
		
		while (true) {
//			System.out.println("X and Y " + currentPoint.x + " " + currentPoint.y);
//			System.out.println("Total so far" + totalMovement);
			if (isOnHex()) {
				totalMovement += 5;
				System.out.println(totalMovement);
				return;
			}
			else
				nextMove();
		}
	}
	
	public static double findMin(double a, double b, double c){
		double min = 9999999;
		
		if (a < min)
			min = a;
		if (b < min)
			min = b;
		if (c < min)
			min = c;
		return min;
	}

	
	public static double calculateDistance(Point a, Point b) {
	//	b = b.realMiddle(b);
		double xs = Math.pow(a.x - b.x,2);
		double ys = Math.pow(a.y - b.y,2);
		double total = Math.sqrt(xs + ys);
//		System.out.println("Xs " + xs);
//		System.out.println("Ys " + ys);
//		System.out.println("Total " + total);
		return total;
	}
	
	public static void nextMove() {
		calculateDirection();
		
		if (horizontal == LEFT && vertical == NEUTRAL) {
			
			double min = findMin(tryLeft(),tryLeftUp(),tryLeftDown());
			if (tryLeft() == min)
				doLeft();
			else if (tryLeftUp() == min)
				doLeftUp();
			else if (tryLeftDown() == min)
				doLeftDown();
			
				
		}
		
		if (horizontal == LEFT && vertical == UP) {
			
			
			double min = findMin(tryLeft(),tryLeftUp(),tryUp());
			if (tryLeft() == min)
				doLeft();
			else if (tryLeftUp() == min)
				doLeftUp();
			else if (tryUp() == min)
				doUp();
			
		}
		
		if (horizontal == NEUTRAL && vertical == UP) {
			
			double min = findMin(tryRightUp(),tryLeftUp(),tryUp());
			if (tryRightUp() == min)
				doRightUp();
			else if (tryLeftUp() == min)
				doLeftUp();
			else if (tryUp() == min)
				doUp();
		}
		
		if (horizontal == RIGHT && vertical == UP) {
			
			double min = findMin(tryRightUp(),tryRight(),tryUp());
			if (tryRightUp() == min)
				doRightUp();
			else if (tryRight() == min)
				doRight();
			else if (tryUp() == min)
				doUp();
		}
		
		if (horizontal == RIGHT && vertical == NEUTRAL) {
			
			double min = findMin(tryRightUp(),tryRight(),tryRightDown());
			if (tryRightUp() == min)
				doRightUp();
			else if (tryRight() == min)
				doRight();
			else if (tryRightDown() == min)
				doRightDown();
		}
		
		if (horizontal == RIGHT && vertical == DOWN) {
			
			double min = findMin(tryDown(),tryRight(),tryRightDown());
			if (tryDown() == min)
				doDown();
			else if (tryRight() == min)
				doRight();
			else if (tryRightDown() == min)
				doRightDown();
		}
		
		
		if (horizontal == NEUTRAL && vertical == DOWN) {
			double min = findMin(tryDown(),tryRight(),tryLeftDown());
			if (tryDown() == min)
				doDown();
			else if (tryRight() == min)
				doRight();
			else if (tryLeftDown() == min)
				doLeftDown();
		}
		
		if (horizontal == LEFT && vertical == DOWN) {
			
			double min = findMin(tryDown(),tryLeft(),tryLeftDown());
			if (tryDown() == min)
				doDown();
			else if (tryLeft() == min)
				doLeft();
			else if (tryLeftDown() == min)
				doLeftDown();
		}

		totalMovement += 5;
		
	}


	public static void findAndMoveToStartPoint() {
		//System.out.println(horizontal + "  " + vertical);
		if(horizontal == RIGHT)
		{
			if(vertical == UP)
			{
				currentPoint.x = startPoint.x;
				currentPoint.y = startPoint.y - 1;
				double temp = calculateDistance(currentPoint, endPoint);
				currentPoint.x = startPoint.x + 1;
				currentPoint.y = startPoint.y;
				double temp2 = calculateDistance(currentPoint, endPoint);
				if(temp < temp2){

					currentPoint.x = startPoint.x;
					currentPoint.y = startPoint.y - 1;
				}
			}
			else if(vertical == DOWN)
			{
				currentPoint.x = startPoint.x;
				currentPoint.y = startPoint.y + 1;
				double temp = calculateDistance(currentPoint, endPoint);
//				System.out.println("Temp1: " + temp);
				currentPoint.x = startPoint.x + 1;
				currentPoint.y = startPoint.y;
				double temp2 = calculateDistance(currentPoint, endPoint);
//				System.out.println("Temp2:" + temp2);
				if(temp < temp2){

					currentPoint.x = startPoint.x;
					currentPoint.y = startPoint.y + 1;
				}
			}
			else
			{
				currentPoint.x = startPoint.x + 1;
				currentPoint.y = startPoint.y;
			}
		}
		else if(horizontal == LEFT)
		{
			if(vertical == UP)
			{
				currentPoint.x = startPoint.x - 1;
				currentPoint.y = startPoint.y - 1;

				double temp = calculateDistance(currentPoint, endPoint);
				currentPoint.x = startPoint.x - 2;
				currentPoint.y = startPoint.y;
				double temp2 = calculateDistance(currentPoint, endPoint);
				if(temp < temp2){

					currentPoint.x = startPoint.x - 1;
					currentPoint.y = startPoint.y - 1;
				}
			}
			else if(vertical == DOWN)
			{
				currentPoint.x = startPoint.x - 1;
				currentPoint.y = startPoint.y + 1;
				double temp = calculateDistance(currentPoint, endPoint);
				currentPoint.x = startPoint.x - 2;
				currentPoint.y = startPoint.y;
				double temp2 = calculateDistance(currentPoint, endPoint);
				if(temp < temp2){
					currentPoint.x = startPoint.x - 1;
					currentPoint.y = startPoint.y + 1;
				}
			}
			else
			{
				currentPoint.x = startPoint.x - 2;
				currentPoint.y = startPoint.y ;
			}
		}
		else
		{
			//if horizontal is neutral
			
			
			if(vertical == UP)
			{
				currentPoint.x = startPoint.x;
				currentPoint.y = startPoint.y - 1;
			}
			if(vertical == DOWN)
			{
				currentPoint.x = startPoint.x;
				currentPoint.y = startPoint.y + 1;
			}	
		}
		totalMovement += 5;
	}


	
	public static boolean isOnHex()
	{
		int currentX = currentPoint.x;
		int currentY = currentPoint.y;
		
		if((currentX == endPoint.x && currentY == (endPoint.y - 1))
				|| (currentX == (endPoint.x - 1) && currentY == (endPoint.y - 1))
				|| (currentX == endPoint.x && currentY == (endPoint.y + 1))
				|| (currentX == (endPoint.x + 1) && currentY == (endPoint.y))
				|| (currentX == (endPoint.x - 1) && currentY == (endPoint.y + 1))
				|| (currentX == (endPoint.x - 2) && currentY == (endPoint.y)))
		{
			return true;
		}
		return false;
	}

	public static void calculateDirection() {
		if (endPoint.x > (currentPoint.x))
			horizontal = RIGHT;
		else if (endPoint.x < (currentPoint.x))
			horizontal = LEFT;
		else 
			horizontal = NEUTRAL;
		if (endPoint.y > (currentPoint.y))
			vertical = DOWN;
		else if (endPoint.y < (currentPoint.y))
			vertical = UP;
		else 
			vertical = NEUTRAL;
		
		//check extra case
		if (currentPoint.x + 1 == endPoint.x)
			horizontal = NEUTRAL;
	}

	public static Point hexToCartesian(Point hex) {
		Point cartesian = new Point();
		
		cartesian.x = (hex.y * 2) + 1;
		
		if (hex.y % 2 == 0)
			cartesian.y = hex.x * 2 + 1;
		else
			cartesian.y = (hex.x * 2);
		
//		System.out.println("x:" + cartesian.x);
//		System.out.println("y:" + cartesian.y);
		return cartesian;
		
	}
	
	public static double tryRight() {
		if (board[currentPoint.x + 1][currentPoint.y] == 1) {
		Point test = new Point();
		test.x = currentPoint.x + 1;
		test.y = currentPoint.y;
		return calculateDistance(test, endPoint);
		}
		else
			return 9999;
	}
	public static void doRight() {
			currentPoint.x += 1;
	}
		
	
	public static double tryLeft() {
		if (board[currentPoint.x - 1][currentPoint.y] == 1) {
			Point test = new Point();
			test.x = currentPoint.x - 1;
			test.y = currentPoint.y;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doLeft() {
			currentPoint.x += -1;	
	}
	
	public static double tryRightUp() {
		if (board[currentPoint.x + 1][currentPoint.y - 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x + 1;
			test.y = currentPoint.y - 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doRightUp() {
			currentPoint.x += 1;
			currentPoint.y += -1;
	}

	
	public static double tryRightDown() {
		if (board[currentPoint.x + 1][currentPoint.y + 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x + 1;
			test.y = currentPoint.y + 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doRightDown() {
		currentPoint.x += 1;
		currentPoint.y += 1;
		
	}
	public static double tryUp() {
		if (board[currentPoint.x][currentPoint.y - 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x;
			test.y = currentPoint.y - 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doUp() {
			currentPoint.y += -1;
		
	}
	
	public static double tryLeftUp() {
		if (board[currentPoint.x - 1][currentPoint.y - 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x - 1;
			test.y = currentPoint.y + 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doLeftUp() {
		currentPoint.x += -1;
		currentPoint.y += -1;		
	}
	
	public static double tryLeftDown() {
		if (board[currentPoint.x - 1][currentPoint.y + 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x - 1;
			test.y = currentPoint.y + 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doLeftDown() {
		currentPoint.x = currentPoint.x - 1;
		currentPoint.y = currentPoint.y + 1;
	}
	
	public static double tryDown() {
		if (board[currentPoint.x][currentPoint.y + 1] == 1) {
			Point test = new Point();
			test.x = currentPoint.x;
			test.y = currentPoint.y + 1;
			return calculateDistance(test, endPoint);
			}
			else
				return 9999;
	}
	
	public static void doDown() {
		currentPoint.x = currentPoint.x;
		currentPoint.y = currentPoint.y + 1;
	}
}


