import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;

public class Semester extends Queries {
	Scanner input = new Scanner(System.in);
	private String name;
	private int numberOfCourses;
	private int totalCourseHours;
	private double gpa;
	private int id;
	private String startDate;
	private String endDate;
	
	
	
	public Semester() {
		
	}
	public void addSemester() {
		final int FALL = 1;
		final int WINTER = 2;
		final int SPRING = 3;
		System.out.println("1- Fall Semester");
		System.out.println("2- Winter Semester");
		System.out.println("3- Spring Semester");
		System.out.println();
		System.out.print("Enter semester number: ");
		id = input.nextInt();
		System.out.print("Enter semester year: ");
		int year = input.nextInt();
		if ( id == 1) {
			name = "Fall " + year;
			startDate = year + "-09-06";
			endDate = year + "-12-10";
		}else if (id == 2) {
			name = "Winter "+ year;
			startDate = year + "-01-09";
			endDate = year + "-04-15";
		}else if (id == 3) {
			name = "Spring "+year;
			startDate = year + "05-08";
			endDate = year +"08-12";
		}else {
			System.out.println("invalid input. press enter to try again.");
			addSemester();
		}
		
		System.out.println("add '" + name + "' to database? (insert 1 to add, 2 to enter semester info again) ");
		int sure = input.nextInt();
		if (sure == 1) {
			insertIntoDatabase("semesters", name, startDate, endDate);
		}else if (sure == 2) {
			addSemester();
		}else {
			System.out.println("invalid input, press enter to try again");
			addSemester();
		}
		
	}
	/**
	 * gets weeks left, courses in a semester.
	 * asks user input for semester id if static semesterId value doesn't exist.
	 */
	public void getSemesterDetails() {
		if (Test.semesterId == 0) {
		printList("semesters");
		System.out.print("Enter semester ID: ");
			int id = input.nextInt();
			System.out.println("Courses this semester: ");
			System.out.println("_______________________");
			getCourses(id);
			if (getDayDiff("semesters", id) < 0) {
			System.out.println("You have already completed this semester!");
			System.out.println();
			}else {
				System.out.println("You have " + (getDayDiff("semesters", id)/7 +1) + " weeks left until the end of the semester."
						+"( " +getDayDiff("semesters", id) +" days )");
				System.out.println();
		}}else {
			
			System.out.println("Courses this semester: ");
			System.out.println("_______________________");
			getCourses(Test.semesterId);
			if (getDayDiff("semesters", Test.semesterId) < 0) {
				System.out.println("You have already completed this semester!");
				System.out.println();
			}else {
			System.out.println("You have " + (getDayDiff("semesters", Test.semesterId)/7 +1) + " weeks left until the end of the semester."
					+"( " +getDayDiff("semesters", Test.semesterId) +" days )");
			System.out.println();
			}
		}
	}
	
	public void getSemesterProgress() {
		if (Test.semesterId == 0) {
			printList("semesters");
			System.out.print("Enter semester ID to see progression: ");
			int id = input.nextInt();
			System.out.println();
			printSemesterProgress(id);
		}else {
			printSemesterProgress(Test.semesterId);
		}
		System.out.println();
		System.out.print("(1) to semesters menu, (0) to main menu: ");
		int choice = input.nextInt();
		if (choice == 1) {
			Test.printSemesterMenu();
		}else if (choice == 0) {
			Test.printMenu();
		}else {
			System.out.println("invalid input, going to main menu..");
			Test.printMenu();
		}
	}
	
	
	public void getCoursesOfSemester() {
		printList("semesters");
		System.out.println();
		System.out.print("Enter semester ID to see semester  courses: ");
		int id = input.nextInt();
		System.out.println();
		getCourses(id);
	}
	public int getTotalCourseHours() {
		return numberOfCourses;
	}
	public void setTotalCourseHours(int numberOfHours) {
		totalCourseHours = totalCourseHours + numberOfHours;
	}
	
	
}
	
