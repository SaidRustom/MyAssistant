import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class Semester contain methods to create/view/modify semesters 
 * @author Said
 *
 */
public class Semester {
	Scanner input = new Scanner(System.in);
	private String name;
	private int numberOfCourses;
	private int totalCourseHours;
	private double gpa;
	private int id;
	private String startDate;
	private String endDate;
	Queries queries = new Queries();
	public Semester() {	
	}
	/**
	 * Adds a semester to DB
	 * Calls Queries.insertIntoDatabase()
	 */
	public void addSemester() {
		final int FALL = 1;
		final int WINTER = 2;
		final int SPRING = 3;
		System.out.println(" 1- Fall Semester");
		System.out.println(" 2- Winter Semester");
		System.out.println(" 3- Spring Semester");
		System.out.println();
		System.out.print(" Enter semester number: ");
		id = input.nextInt();
		System.out.print(" Enter semester year: ");
		int year = input.nextInt();
		if ( id == FALL) {
			name = "Fall " + year;
			startDate = year + "-09-06";
			endDate = year + "-12-10";
		}else if (id == WINTER) {
			name = "Winter "+ year;
			startDate = year + "-01-09";
			endDate = year + "-04-15";
		}else if (id == SPRING) {
			name = "Spring "+year;
			startDate = year + "05-08";
			endDate = year +"08-12";
		}else {
			System.out.println("\n invalid input. press enter to try again.");
			addSemester();
		}
		
		System.out.println("\n add '" + name + "' to database? \n (1)-to add \n (2)-to enter semester info again \n (0)-back to menu) ");
		int sure = input.nextInt();
		if (sure == 1) {
			queries.insertIntoDatabase("semesters", name, startDate, endDate);
		}else if (sure == 2) {
			addSemester();
		}else if ( sure == 0) {
			Test.printSemesterMenu();
		}
		else {
			System.out.println(" invalid input, press enter to try again");
			addSemester();
		}
		
	}
	/**
	 * Gets number of weeks left, courses for a semester.
	 * Only asks for semester id if static semesterId value doesn't exist.
	 * calls Queries.getCourses(), Queries.getDayDiff()
	 */
	public void getSemesterDetails() {
		if (Test.semesterId == 0) {
			queries.printList("semesters");
			System.out.print(" Enter semester ID: ");
			int id = input.nextInt();
			System.out.println(" Courses this semester: ");
			System.out.println("________________________");
			queries.getCourses(id);
			if (queries.getDayDiff("semesters", id) < 0) {
				System.out.println(" You have already completed this semester!");
				System.out.println();
			}else {
				System.out.println(" You have " + (queries.getDayDiff("semesters", id)/7 +1) + " weeks left until the end of the semester."
						+"( " +queries.getDayDiff("semesters", id) +" days )");
				System.out.println();
		}}else {
			
			System.out.println(Test.printBlue("Courses this semester: "));
			System.out.println("_______________________");
			queries.getCourses(Test.semesterId);
			if (queries.getDayDiff("semesters", Test.semesterId) < 0) {
				System.out.println("You have already completed this semester!");
				System.out.println();
			}else {
			System.out.println("You have " + (queries.getDayDiff("semesters", Test.semesterId)/7 +1) + " weeks left until the end of the semester."
					+"( " +queries.getDayDiff("semesters", Test.semesterId) +" days )");
			System.out.println();
			}
		}
	}
	/**
	 * Calls Queries.printSemesterProgress() that prints:
	 * Projected grade ( depending on current performance in submitted assignments)
	 * Possible (%) to achieve so far and achieved(%) in all courses in a semester.
	 */
	public void printSemesterProgress() {
		if (Test.semesterId == 0) {
			queries.printList("semesters");
			System.out.print("Enter semester ID to see progression: ");
			int id = input.nextInt();
			System.out.println();
			queries.printSemesterProgress(id);
		}else {
			queries.printSemesterProgress(Test.semesterId);
		}
	}
	/**
	 * Prints courses of a semester to console.
	 */
	public void getCoursesOfSemester() {
		queries.printList("semesters");
		System.out.println();
		System.out.print("Enter semester ID to see semester  courses: ");
		int id = input.nextInt();
		System.out.println();
		queries.getCourses(id);
	}
	/**
	 * Calculates the GPA of a semester.
	 * Calls Queries.getSemesterCourses()
	 */
	public void gpaCalculator() {
		System.out.println(Print.printBlue(" GPA Calculator: \n"
				+ "  Enter LetterGrade   (Ex:A-)\n  OR    TotalGrade(%) (Ex:82)\n"));
		ArrayList<Course> courses = queries.getSemesterCourses();
		Course course = new Course();
		double gradeSum = 0; //a double to keep numericalGrade(out of 4) * courseHours
		int totalHours = 0;
		for (int i=0; i<courses.size(); i++) {
			course = courses.get(i);
			System.out.print(" Enter Grade for " + course.getName()+": ");
			course.setNumericGrade(course.getGrades());
			gradeSum += course.getNumericGrade() * course.getHours();
			totalHours += course.getHours();
		}
		gpa = gradeSum / totalHours;
		System.out.println(Print.printGreen("\n GPA = " + gpa));
	}
		
	
}
	
