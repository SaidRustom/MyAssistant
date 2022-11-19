import java.util.ArrayList;
import java.util.Scanner;

public class Course extends Queries {
	 
	
	Scanner input = new Scanner(System.in);
	private String name;
	private int hours;
	private int grade;
	private String letterGrade;
	private int semesterId;
	
	
	public Course() {
		
	}
	public void addCourse() {
		System.out.print("Enter course name: ");
		name = input.nextLine();
		System.out.print("Enter course hours: ");
		hours = input.nextInt();
		if ( Test.semesterId == 0) {
			printList("semesters");
			System.out.println("Enter semester ID: ");
			Test.semesterId = input.nextInt();
		}
		printCourseDetails();
		System.out.print("Add course? (enter (1) to add course, (2) to enter course info again (0) to go back to courses menu): ");
		int sure = input.nextInt();input.nextLine();
		if (sure == 1) {
			insertIntoDatabase("courses", name, hours, Test.semesterId);
		}else if (sure == 2) {
			addCourse();
		}else if (sure == 0) {
			Test.printCourseMenu();
		}else {
			System.out.println("invalid input, going back to courses menu...");
			System.out.println("");
		}
	}
	
	
	public void getCourseAssignments() {
		printList("courses");
		System.out.println();
		System.out.print("Enter course ID: ");
		int id = input.nextInt();
		printCourseAssignments(id);
		
	}

	public void printCourseDetails() {
		System.out.println("Course details: ");
		System.out.println("Course name: " + name);
		System.out.println("Course hours: " + hours);
		System.out.println("Semester ID = " + Test.semesterId);
	}
	
	public void deleteCourse() {
		printList("courses");
		System.out.println("Enter course ID to remove");
		int id = input.nextInt();
		System.out.println("Are you sure you want to delete course # " + id + "? ");
		System.out.println("Enter (1) to delete (2) to enter course id again (3) to go back to courses menu");
		int sure = input.nextInt();
		if (sure == 1) {
			deleteRecord("courses", id);
		}else if (sure == 2) {
			deleteCourse();
		}else if( sure == 3) {
			Test.printCourseMenu();
		}else {
			System.out.println("invalid input...");
			deleteCourse();
		}
	}
	public void setGrades() {
		
	}
	
	public int getCourseHours() {
		return hours;
	}
	public int getCourseGrade() {
		return grade;
	}
	public void setCourseGrade(int grade) {
		this.grade =+ grade;
	}
	public String calculateLetterGrade(int grade) {
		if (grade > 89 && grade < 101) {
			letterGrade = "A+";
		}else if (grade >= 85 && grade < 90) {
			letterGrade = "A";
		}else if (grade >= 80 && grade < 85) {
			letterGrade = "A-";
		}else if (grade >= 77 && grade < 80) {
			letterGrade = "B+";
		}else if (grade >= 73 && grade < 77) {
			letterGrade = "B";
		}else if (grade >= 70 && grade < 73) {
			letterGrade = "B-";
		}else if (grade >= 67 && grade < 70) {
			letterGrade = "C+";
		}else if (grade >= 63 && grade < 67) {
			letterGrade = "C";
		}else if (grade >= 60 && grade < 63) {
			letterGrade = "C-";
		}else if (grade >= 57 && grade < 60) {
			letterGrade = "D+";
		}else if (grade >= 53 && grade < 57) {
			letterGrade = "D";
		}else if (grade >= 50 && grade < 53) {
			letterGrade = "D-";
		}else if (grade >= 00 && grade < 50) {
			letterGrade = "F";
		}else {
			letterGrade = "undefined";
		}
		return letterGrade;
	}
	
}
	
	

