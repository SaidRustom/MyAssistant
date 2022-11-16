import java.sql.*;
import java.util.Scanner;

public class Test {

	static Scanner userInput = new Scanner(System.in);
	public static void main(String[] args) {
		
		System.out.println("Welcome to the student assistant program! ");
			Test.printMenu();
		
	}
	static int semesterId = 0;
	public static void printMenu() {
		
		final int SEMESTER = 1;
		final int COURSE = 2;
		final int ASSIGNMENT = 3;
		final int ENTER_SEMESTERID = 4;
		final int QUIT = 5;
		
		System.out.println();
		System.out.println("________________________________________");
		System.out.println("|      Select an option from the menu   |");
		System.out.println("|__ ____________________________________|");
		System.out.println("|  |                                    |");
		System.out.println("|1-|-----Go to Semesters menu.          |");
		System.out.println("|2-|-----Go to Courses menu.            |");
		System.out.println("|3-|-----Go to Assignments menu.        |");
		System.out.println("|4-|-----Enter a static semester ID     |");
		System.out.println("|5-|-----To close program.              |");
		System.out.println("|__|____________________________________|");
		System.out.print("            your choice: ");
		int choice = userInput.nextInt();
		System.out.println();
		if (choice == SEMESTER) {
			printSemesterMenu();
		}else if (choice == COURSE) {
			printCourseMenu();
		}else if (choice == ASSIGNMENT) {
			printAssignmentMenu();
		}else if (choice == ENTER_SEMESTERID) {
			Semester s = new Semester();
			s.printList("semesters");
			System.out.print("Enter semester ID: ");
			semesterId = userInput.nextInt();
			System.out.println("Semester ID added!");
			System.out.println();
			printMenu();
		}
		else if (choice == QUIT) {
			System.out.println("Closing program..");
			System.out.println("See you later! :)");
		}
		else {
			System.out.println("invalid input...");
			printMenu();
		}
		
	}
	public static void printSemesterMenu() {
		final int ADD_SEMESTER = 1;
		final int SEMESTER_COURSES = 2;
		final int VIEW_ALL_SEMESTERS = 3;
		final int SEMESTER_DETAILS = 4;
		final int SEMESTER_PROGRESS = 5;
		
		System.out.println();
		System.out.println("     Semesters Menu:    ");
		System.out.println("_______________________");
		System.out.println();
		System.out.println("1- Add a new semester.");
		System.out.println("2- View courses of a semester.");
		System.out.println("3- View all semesters.");
		System.out.println("4- View semester details.");
		System.out.println("5- View semester progress.");
		System.out.println();
		System.out.println("Press 0 at any time to go back to main menu.");
		System.out.println();
		System.out.print("your choice: ");
		int choice = userInput.nextInt();
		System.out.println();
		
		if (choice == ADD_SEMESTER) {
			Semester s = new Semester();
			s.addSemester();
			System.out.println();
			menuChoiceSemester();
		}else if (choice == SEMESTER_COURSES) {
			Semester s = new Semester();
			s.getCoursesOfSemester();
			System.out.println();
			menuChoiceSemester();
		}else if (choice == VIEW_ALL_SEMESTERS) {
			Assignment a = new Assignment();
			a.printList("semesters");
			System.out.println();
			menuChoiceSemester();
		}else if (choice == SEMESTER_DETAILS) {
			Semester s = new Semester();
			s.getSemesterDetails();
			menuChoiceSemester();
		}else if (choice == SEMESTER_PROGRESS) {
			Semester s = new Semester();
			s.getSemesterProgress();
		}
		else if ( choice == 0 ) {
			printMenu();
		}else {
			System.out.println("Invalid input..");
			printSemesterMenu();
		}
	}
	public static void printCourseMenu() {
		final int ADD_COURSE = 1;
		final int COURSE_ASSIGNMENTS = 2;
		final int VIEW_ALL_COURSES = 3;
		final int DELETE_COURSE = 4;
		System.out.println();
		System.out.println("     Courses Menu: ");
		System.out.println("_______________________");
		System.out.println();
		System.out.println("1- Add a course.");
		System.out.println("2- View assignments of a course.");
		System.out.println("3- View all courses.");
		System.out.println();
		System.out.println("Press 0 at any time to go back to main menu.");
		System.out.println();
		System.out.print("your choice: ");
		int choice = userInput.nextInt();
		System.out.println();

		if ( choice == ADD_COURSE ) {
			Course c = new Course();
			c.addCourse();
			System.out.println();
			menuChoiceCourse();
		}else if (choice == COURSE_ASSIGNMENTS ) {
			Course c = new Course();
			c.getCourseAssignments();
			System.out.println();
			menuChoiceCourse();
		}else if (choice == VIEW_ALL_COURSES) {
			Course c = new Course();
			c.printList("courses");
			System.out.println();
			menuChoiceCourse();
		}else if (choice == DELETE_COURSE) {
			Course c = new Course();
			c.deleteCourse();
		}else if (choice == 0 ) {
			printMenu();
		}
		
		else {
			System.out.println("Invalid input..");
			printCourseMenu();
		}
	}
	public static void printAssignmentMenu() {
		final int ADD_ASSIGNMENT =1;
		final int DELETE_ASSIGNMENT = 2;
		final int SUBMIT_ASSIGNMENT = 3;
		final int CHANGE_DEADLINE = 4;
		final int ADD_RECEIVED_GRADE = 5;
		final int VIEW_ALL_ASSIGNMENTS = 6;
		final int VIEW_ALL_COMPLETED_ASSIGNMENTS = 7;
		final int VIEW_COURSE_ASSIGNMENTS = 8;
		System.out.println();
		System.out.println("     Assignments Menu: ");
		System.out.println("__________________________");
		System.out.println();
		System.out.println("1- Add assignment.");
		System.out.println("2- Delete assignment.");
		System.out.println("3- Submit assignment.");
		System.out.println("4- Change assignment deadline.");
		System.out.println("5- Add received grade to an assignment.");
		System.out.println("6- View all not submitted assignments.");
		System.out.println("7- View all submitted assignments.");
		System.out.println("8- View a course's assignments");
		System.out.println();
		System.out.println("Press 0 at any time to go back to main menu.");
		System.out.println();
		System.out.print("your choice: ");
		
		int choice = userInput.nextInt();
		System.out.println();

		if (choice == ADD_ASSIGNMENT) {
			Assignment a = new Assignment();
			a.addAssignment();
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == DELETE_ASSIGNMENT) {
			Assignment a = new Assignment();
			a.deleteAssignment();
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == SUBMIT_ASSIGNMENT) {
			Assignment a = new Assignment();
			a.submitAssignment();
			System.out.println();
			
		}else if (choice == CHANGE_DEADLINE) {
			Assignment a = new Assignment();
			a.changeAssignmentDeadline();
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == ADD_RECEIVED_GRADE) {
			Assignment a = new Assignment();
			a.updateAssignmentReceivedGrade();
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == VIEW_ALL_ASSIGNMENTS) {
			Assignment a = new Assignment();
			a.printGradesDeadline("assignments");
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == VIEW_ALL_COMPLETED_ASSIGNMENTS) {
			Assignment a = new Assignment();
			a.printGrades("completedassignments");
			menuChoiceAssignment();
		}else if (choice == VIEW_COURSE_ASSIGNMENTS) {
			Course c = new Course();
			c.getCourseAssignments();
			menuChoiceAssignment();
		}
		else if (choice == 0) {
			printMenu();
		}else {
			System.out.println("Invalid input, press enter to try again..");
			printAssignmentMenu();
		}
		
	}
	public static void menuChoiceSemester() {
		System.out.println("(0) to main menu, (1) to Semesters menu ");
		int choice = userInput.nextInt();
		if (choice == 1) {
			printSemesterMenu();
		}else if (choice == 0) {
			printMenu();
		}else {
			System.out.println("invalid input, press enter to try again");
			menuChoiceSemester();
		}
	}
		public static void menuChoiceCourse() {
			System.out.println("(0) to main menu, (1) to Courses menu ");
			int choice = userInput.nextInt();
			if (choice == 1) {
				printCourseMenu();
			}else if (choice == 0) {
				printMenu();
			}else {
				System.out.println("invalid input, press enter to try again");
				menuChoiceCourse();
			}
	}
		public static void menuChoiceAssignment() {
			System.out.println("(0) to main menu, (1) to Assignments menu ");
			int choice = userInput.nextInt();
			if (choice == 1) {
				printAssignmentMenu();
			}else if (choice == 0) {
				printMenu();
			}else {
				System.out.println("invalid input, press enter to try again");
				menuChoiceAssignment();
			}
			
		}
		public static int setSemesterId() {
			Semester s = new Semester();
			s.printList("semesters");
			System.out.print("Enter a static semester ID: ");
			int semesterId = userInput.nextInt();
			return semesterId;
		}
}