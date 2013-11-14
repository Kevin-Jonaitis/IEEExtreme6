import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solution {

	public static final int Y = 0;
	public static final int R = 1;
	public static final int B = 2;
	public static final int O = 3;
	public static final int G = 4;
	public static final int W = 5;

	static Face front;
	static Face back;
	static Face left;
	static Face right;
	static Face bottom;
	static Face top;

	private static char[] colorSequence = new char[6];
	private static ArrayList<String> rotations = new ArrayList<String>();
	Face[] cube = new Face[6];

	public static void performRotate(String s) {
		if(s.length() == 1) {
			if (s.equalsIgnoreCase("L")) {
				moveLeftToFront();
				rotateClockwise();
				moveRightToFront();
			}
			if (s.equalsIgnoreCase("R")) {
				moveRightToFront();
				rotateClockwise();
				moveLeftToFront();

			}
			if (s.equalsIgnoreCase("B")) {
				moveBackToFront();
				rotateClockwise();
				moveBackToFront();
			}
			if (s.equalsIgnoreCase("U")) {
				moveTopToFront();
				rotateClockwise();
				moveBottomToFront();
			}
			if (s.equalsIgnoreCase("D")) {
				moveBottomToFront();
				rotateClockwise();
				moveTopToFront();

			}
			if (s.equalsIgnoreCase("F")) {
				rotateClockwise();
			}
		}
		if(s.length() == 2){
			if (s.equalsIgnoreCase("L'")) {
				moveLeftToFront();
				rotateCounterClockwise();
				moveRightToFront();
			}
			if (s.equalsIgnoreCase("R'")) {
				moveRightToFront();
				rotateCounterClockwise();
				moveLeftToFront();

			}
			if (s.equalsIgnoreCase("B'")) {
				moveBackToFront();
				rotateCounterClockwise();
				moveBackToFront();
			}
			if (s.equalsIgnoreCase("U'")) {
				moveTopToFront();
				rotateCounterClockwise();
				moveBottomToFront();

			}
			if (s.equalsIgnoreCase("D'")) {
				moveBottomToFront();
				rotateCounterClockwise();
				moveTopToFront();
			}
			if (s.equalsIgnoreCase("F'")) {
				rotateCounterClockwise();

			}
			if (s.equalsIgnoreCase("L2")) {
				moveLeftToFront();
				rotateClockwise();
				rotateClockwise();
				moveRightToFront();
			}
			if (s.equalsIgnoreCase("R2")) {
				moveRightToFront();
				rotateClockwise();
				rotateClockwise();
				moveLeftToFront();

			}
			if (s.equalsIgnoreCase("B2")) {
				moveBackToFront();
				rotateClockwise();
				rotateClockwise();
				moveBackToFront();
			}
			if (s.equalsIgnoreCase("U2")) {
				moveTopToFront();
				rotateClockwise();
				rotateClockwise();
				moveBottomToFront();

			}
			if (s.equalsIgnoreCase("D2")) {
				moveBottomToFront();
				rotateClockwise();
				rotateClockwise();
				moveTopToFront();

			}
			if (s.equalsIgnoreCase("F2")) {
				rotateClockwise();
				rotateClockwise();
			}
		}
		
	}
	
	public static String lookUpColor(int i){
		switch(i) {
		case 0:
			return "Y";
		case 1: 
			return "R";
		case 2:
			return "B";
		case 3:
			return "O";
		case 4: 
			return "G";
		case 5:
			return "W";
		}
		return null;
	}
	public static void printOutColorFrontFace() {
		for(int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				System.out.print(lookUpColor(front.face[j][i]) + " ");
			}
			System.out.print("\n");
		}
	}
	public static void main(String[] args) {

		BufferedReader br = new BufferedReader(new InputStreamReader(
				System.in));
		String line = null;
		String line2 = null;
		try {
			line = br.readLine();
			line2 = br.readLine();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		String line = "YORBWG";
//		String line2 = "RR";
		// parse out the lines into the arrays
		colorSequence = line.toCharArray();
		top = new Face(colorSequence[0]);
		left = new Face(colorSequence[1]);
		front = new Face(colorSequence[2]);
		right = new Face(colorSequence[3]);
		back = new Face(colorSequence[4]);
		bottom = new Face(colorSequence[5]);


		int currLength = 0;
		while (currLength < line2.length()) {
			String temp = line2.substring(currLength, currLength + 1);
			if (currLength + 2 <= line2.length()) {
				String temp2 = line2.substring(currLength + 1,
						currLength + 2);
				if (temp2.equals("\'") || temp2.equals("2")) {
					rotations.add(temp.concat(temp2));
				} else {
					rotations.add(temp);
				}
			} else{
				if(temp.equals("\'") || temp.equals("2")){
					
				} else{
				rotations.add(temp);
				}
			}
			currLength++;
		}
		
		for (String s: rotations){
			performRotate(s);
		}
		printOutColorFrontFace();
	}
	
	public static void moveTopToFront() {
		//System.out.println("Before change, the top is " + top.face[1][1]);
		Face tempFront = new Face(front);
		Face tempBack =  new Face(back);
		Face tempRight = new Face(right);
		Face tempLeft = new Face(left);
		Face tempBottom = new Face(bottom);
		Face tempTop =  new Face(top);
		
		front = tempTop;
		bottom = tempFront;
		back = tempBottom;
		top = tempBack;
		
		
		matrixRotateClockwise(right);
		matrixRotateClockwise(left);

		
	
//		System.out.println("The front is " + front.face[1][1]);
//		System.out.println("The bottom is " + bottom.face[1][1]);
	}
	
	public static boolean chcekEquals(Face a, Face b) {
		for(int i = 1; i < 4; i++) {
			for (int j = 1; j < 4; j++) {
				if (!(a.face[i][j] == b.face[i][j]))
					return false;
			}
		}
		return true;
		
	}
	public static void moveRightToFront() {
		Face tempFront = new Face(front);
		Face tempBack =  new Face(back);
		Face tempRight = new Face(right);
		Face tempLeft = new Face(left);
		
		matrixRotateClockwise(top);
		matrixRotateClockwise(bottom);

		

		front = tempRight;
		back = tempLeft;
		right = tempBack;
		left = tempFront;
		
	}
	
	public static void moveBottomToFront() {
		moveTopToFront();
		moveTopToFront();
		moveTopToFront();
		
		
		matrixRotateCounterClockwise(left);
		matrixRotateCounterClockwise(right);
	}
	
	public static void moveBackToFront() {
		moveTopToFront();
		moveTopToFront();
		
		matrixRotateCounterClockwise(left);
		matrixRotateCounterClockwise(left);
		matrixRotateCounterClockwise(right);
		matrixRotateCounterClockwise(right);
	}
	
	public static void moveLeftToFront() {
		
		Face tempFront = new Face(front);
		Face tempBack = new Face(back);
		Face tempRight = new Face(right);
		Face tempLeft = new Face(left);
		
		matrixRotateCounterClockwise(top);
		matrixRotateCounterClockwise(bottom);
		

		front = tempLeft;
		back = tempRight;
		right = tempFront;
		left = tempBack;
	}
	
	public static void matrixRotateClockwise(Face f) {
		Face tempFace = new Face(f);
		
		f.face[1][1] = tempFace.face[1][3];
		f.face[2][1] = tempFace.face[1][2];
		f.face[3][1] = tempFace.face[1][1];
		f.face[1][2] = tempFace.face[2][3];
		f.face[1][3] = tempFace.face[3][3];
		f.face[2][3] = tempFace.face[3][2];
		f.face[3][3] = tempFace.face[3][1];
		f.face[3][2] = tempFace.face[2][1];
	}
	
	public static void matrixRotateCounterClockwise(Face f) {
		matrixRotateClockwise(f);
		matrixRotateClockwise(f);
		matrixRotateClockwise(f);
	}
	
	public static void rotateClockwise() {
		Face tempFront = new Face(front);
		Face tempLeft = new Face(left);
		Face tempRight = new Face(right);
		Face tempTop = new Face(top);
		Face tempBottom = new Face(bottom);
		
		//front face
		front.face[1][1] = tempFront.face[1][3];
		front.face[2][1] = tempFront.face[1][2];
		front.face[3][1] = tempFront.face[1][1];
		front.face[1][2] = tempFront.face[2][3];
		front.face[1][3] = tempFront.face[3][3];
		front.face[2][3] = tempFront.face[3][2];
		front.face[3][3] = tempFront.face[3][1];
		front.face[3][2] = tempFront.face[2][1];
		
		left.face[3][1] = tempBottom.face[1][1];
		left.face[3][2] = tempBottom.face[2][1];
		left.face[3][3] = tempBottom.face[1][1];
		
		right.face[1][1] = tempTop.face[1][3];
		right.face[1][2] = tempTop.face[2][3];
		right.face[1][3] = tempTop.face[3][3];
		
		bottom.face[1][1] = tempRight.face[1][3];
		bottom.face[2][1] = tempRight.face[1][2];
		bottom.face[3][1] = tempRight.face[1][1];
		
		top.face[1][3] = tempLeft.face[3][3];
		top.face[2][3] = tempLeft.face[3][2];
		top.face[3][3] = tempLeft.face[3][1];

	}
	
	public static void rotateCounterClockwise() {
		rotateClockwise();
		rotateClockwise();
		rotateClockwise();
	}
	
	
	static class Face {
		int[][] face = new int[4][4];

		public Face() {
			
		}
		
		public boolean chcekEquals(Face left) {
			return false;
		}

		//replaces the array with a face; aka creates a copy
		public Face(Face f) {
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
				//	int value = f.face[i][j];
					this.face[i][j] = f.face[i][j];
				}
			}
			//this.face = f.face.clone();
		}
		public Face(char colorSequence) {
			int toFill = -1;
			switch (colorSequence) {
			case 'Y':
				toFill = Y;
				break;
			case 'R':
				toFill = R;
				break;
			case 'B':
				toFill = B;
				break;
			case 'O':
				toFill = O;
				break;
			case 'G':
				toFill = G;
				break;
			case 'W':
				toFill = W;
				break;
			}
			for (int i = 0; i < 4; i++) {
				for (int j = 0; j < 4; j++) {
					if (toFill == -1) {
						System.out.println("you lose");
					}
					face[i][j] = toFill;
				}
			}
		}
	}
}
