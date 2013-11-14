import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Solution {

	public static class Student {
		int one;
		int two;
		int three;

		public Student(int one, int two, int three) {
			this.one = one;
			this.two = two;
			this.three = three;
		}
	}

	private static BufferedReader br;
	public static final int VALIDNUMBER = 198;

	public static void main(String[] args) throws IOException {
		ArrayList<Student> validStudents = new ArrayList<Student>();

		br = new BufferedReader(new InputStreamReader(System.in));
		String nextLine = br.readLine();
		nextLine = nextLine.toUpperCase();
		for (int i = 0; i < nextLine.length(); i++) {
			for (int j = i + 1; j < nextLine.length(); j++) {
				for (int k = j + 1; k < nextLine.length(); k++) {
					if (nextLine.charAt(i) + nextLine.charAt(j) + nextLine.charAt(k) != VALIDNUMBER)
						validStudents.add(new Student(i,j,k));
				}
			}
		}
		//System.out.println("The first runthrough size is " + validStudents.size());
		String currentLine = br.readLine();
		currentLine = currentLine.toUpperCase();

		for(int j = 0; j < 13; j ++) {
			//System.out.println(currentLine);
			for (int i = validStudents.size() - 1; i >= 0; i--) {
				if (currentLine.charAt(validStudents.get(i).one) + currentLine.charAt(validStudents.get(i).two) 
						+ currentLine.charAt(validStudents.get(i).three) == VALIDNUMBER)
					validStudents.remove(i);
			}
		//	System.out.println("Current runthrough size is " + validStudents.size());
			currentLine =  br.readLine();
		}
		
		if (validStudents.size() > 0) {
			System.out.println("Students " + (validStudents.get(0).one + 1 ) + "," + (validStudents.get(0).two + 1 ) + " and " + (validStudents.get(0).three + 1 ) + " do not have any day with different deserts.");
		}
		else
			System.out.println("Solution OK.");
		
	}
}
