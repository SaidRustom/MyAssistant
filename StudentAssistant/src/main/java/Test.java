import java.sql.*;
import java.util.Scanner;
/**
 * This class contains method main to run the program.
 * @author Said
 *
 */
public class Test {
	
	static Scanner userInput = new Scanner(System.in);
	public static void main(String[] args) {
		Queries q = new Queries();
		q.createTasksFromAssignments(); // a STOREDPROCEDURE in DB to run everytime the program starts.
		System.out.println(Print.printGreen("\n     Welcome to the student assistant program! "));
			Test.printMenu();
		}
	/**
	 * Prints program's main menu to console.
	 */
	public static void printMenu() {
		
		final int SEMESTER = 1;
		final int COURSE = 2;
		final int ASSIGNMENT = 3;
		final int SCHEDULE = 4;
		final int WEEK_ASSIGNMENTS = 6;
		final int VIEW_SCHEDULE = 5;
		final int QUIT = 8;
		
		System.out.println("      ________________________________________");
		System.out.println("     |"+printBlue("      Select an option from the menu   ")+"|");
		System.out.println("     |__ ____________________________________|");
		System.out.println("     |  |                                    |");
		System.out.println("     |1-|-----     Semesters menu    -----   |");
		System.out.println("     |  |                                    |");
		System.out.println("     |2-|-----     Courses menu      -----   |");
		System.out.println("     |  |                                    |");
		System.out.println("     |3-|-----     Assignments menu  -----   |");
		System.out.println("     |  |                                    |");
		System.out.println("     |4-|-----     Schedule menu     -----   |");
		System.out.println("     |  |                                    |");
		System.out.println("     |5-|--------  View Schedule    -------  |");
		System.out.println("     |  |                                    |");
		System.out.println("     |6-|----- View this week due assignments|");
		System.out.println("     |  |                                    |");
		System.out.println("     |  |                                    |");
		System.out.println("     |8-|-----      close program            |");
		System.out.println("     |__|____________________________________|");
		System.out.print("\n                 your choice: ");
		int choice = userInput.nextInt();
		System.out.println();
		if (choice == SEMESTER) {
			printSemesterMenu();
		}else if (choice == COURSE) {
			printCourseMenu();
		}else if (choice == ASSIGNMENT) {
			printAssignmentMenu();
		}else if(choice == SCHEDULE) {
			printScheduleMenu();
		}else if (choice == VIEW_SCHEDULE) {
			Schedule s = new Schedule();
			s.printSchedule("schedule");
			System.out.println("(0) to go back to menu");
			if (userInput.nextInt() == 0) {
				printMenu();
			}else {
				printMenu();
		}	}
		else if (choice == QUIT) {
			System.out.println("Closing program..");
			System.out.println("See you later! :)");
			System.exit(0);
		}else if ( choice == WEEK_ASSIGNMENTS) {
			Queries queries = new Queries();
			queries.printWeekAssignments();
			menuChoiceAssignment();
		}else {
			System.out.println("invalid input...");
			printMenu();
		}
	}
	/**
	 * Prints semester menu to console.
	 */
	public static void printSemesterMenu() {
		
		final int ADD_SEMESTER = 1;
		final int VIEW_ALL_SEMESTERS = 5;
		final int SEMESTER_DETAILS = 2;
		final int SEMESTER_PROGRESS = 3;
		final int GPA_CALCULATOR = 4;
		final int ENTER_SEMESTERID = 6;
		
		System.out.println();
		System.out.println(printBlue("     Semesters Menu:    "));
		System.out.println(printBlue("_______________________"));
		System.out.println();
		System.out.println(" (1)- Add a new semester.");
		System.out.println(" (2)- View semester details.");
		System.out.println(" (3)- View semester progress.");
		System.out.println(" (4)- GPA Calculator.");
		System.out.println(" (5)- View all semesters.");
		System.out.println(" (6)- Enter a static semester ID  (useful when adding courses)" );
		System.out.println();
		System.out.println(" (0)- Main menu");
		System.out.println();
		System.out.print("    your choice: ");
		int choice = userInput.nextInt();
		System.out.println();
		
		if (choice == ADD_SEMESTER) {
			Semester s = new Semester();
			s.addSemester();
			System.out.println();
			menuChoiceSemester();
		}else if (choice == VIEW_ALL_SEMESTERS) {
			Queries queries = new Queries();
			queries.printList("semesters");
			System.out.println();
			menuChoiceSemester();
		}else if (choice == SEMESTER_DETAILS) {
			Semester s = new Semester();
			s.getSemesterDetails();
			menuChoiceSemester();
		}else if (choice == SEMESTER_PROGRESS) {
			Semester s = new Semester();
			s.printSemesterProgress();
			menuChoiceSemester();
		}else if(choice == GPA_CALCULATOR) {
			Semester s = new Semester();
			s.gpaCalculator();
			menuChoiceSemester();
		}else if (choice == ENTER_SEMESTERID) {
			Queries queries = new Queries();
			queries.printList("semesters");
			System.out.print("Enter semester ID: ");
			semesterId = userInput.nextInt();
			System.out.println("Semester ID added!");
			System.out.println();
			printMenu();
		}
		else if ( choice == 0 ) {
			printMenu();
		}else {
			System.out.println("Invalid input..");
			printSemesterMenu();
		}
	}
	/**
	 * Prints course menu to console.
	 */
	public static void printCourseMenu() {
		final int ADD_COURSE = 1;
		final int COURSE_ASSIGNMENTS = 2;
		final int VIEW_ALL_COURSES = 3;
		final int ADD_CLASS = 4;
		final int DELETE_COURSE = 5;
		System.out.println();
		System.out.println(printBlue("     Courses Menu: "));
		System.out.println(printBlue("_______________________"));
		System.out.println();
		System.out.println(" (1)- Add a course.");
		System.out.println(" (2)- View assignments of a course.");
		System.out.println(" (3)- View all courses.");
		System.out.println(" (4)- Add class time for a course.");
		System.out.println(" (5)- Delete a course");
		System.out.println();
		System.out.println(" (0)- Main menu");
		System.out.println();
		System.out.print("    your choice: ");
		int choice = userInput.nextInt();
		System.out.println();

		if ( choice == ADD_COURSE ) {
			Course c = new Course();
			c.addCourse();
			System.out.println();
			menuChoiceCourse();
		}else if (choice == COURSE_ASSIGNMENTS ) {
			Course c = new Course();
			c.printCourseAssignments();
			System.out.println();
			menuChoiceCourse();
		}else if (choice == VIEW_ALL_COURSES) {
			Semester s = new Semester();
			s.printSemesterProgress();
			System.out.println();
			menuChoiceCourse();
		}else if (choice == DELETE_COURSE) {
			Course c = new Course();
			c.deleteCourse();
		}else if (choice == ADD_CLASS) {
			Course c = new Course();
			c.addClass();
		}else if (choice == 0 ) {
			printMenu();
		}else {
			System.out.println("Invalid input..");
			printCourseMenu();
		}
	}
	/**
	 * Prints assignments menu to console.
	 */
	public static void printAssignmentMenu() {
		final int ADD_ASSIGNMENT =1;
		final int DELETE_ASSIGNMENT = 2;
		final int SUBMIT_ASSIGNMENT = 3;
		final int CHANGE_DEADLINE = 4;
		final int ADD_RECEIVED_GRADE = 5;
		final int VIEW_NOTSUBMITTED_ASSIGNMENTS = 6;
		final int VIEW_ALL_COMPLETED_ASSIGNMENTS = 7;
		final int VIEW_COURSE_ASSIGNMENTS = 8;
		System.out.println();
		System.out.println(printBlue("     Assignments Menu: "));
		System.out.println(printBlue("__________________________"));
		System.out.println();
		System.out.println(" (1)- Add assignment.");
		System.out.println(" (2)- Delete assignment.");
		System.out.println(" (3)- Submit assignment.");
		System.out.println(" (4)- Change assignment deadline.");
		System.out.println(" (5)- Add received grade to an assignment.");
		System.out.println(" (6)- View all not submitted assignments.");
		System.out.println(" (7)- View all submitted assignments.");
		System.out.println(" (8)- View a course's assignments");
		System.out.println();
		System.out.println(" (0)- Main menu");
		System.out.println();
		System.out.print("      your choice: ");
		
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
		}else if (choice == VIEW_NOTSUBMITTED_ASSIGNMENTS) {
			Queries queries = new Queries();
			queries.printNotSubmittedAssignments("assignments");
			System.out.println();
			menuChoiceAssignment();
		}else if (choice == VIEW_ALL_COMPLETED_ASSIGNMENTS) {
			Queries queries = new Queries();
			queries.printGrades("completedassignments");
			menuChoiceAssignment();
		}else if (choice == VIEW_COURSE_ASSIGNMENTS) {
			Course c = new Course();
			c.printCourseAssignments();
			menuChoiceAssignment();
		}
		else if (choice == 0) {
			printMenu();
		}else {
			System.out.println(" Invalid input, press enter to try again..");
			printAssignmentMenu();
		}
	}
	/**
	 * Prints schedule menu to console.
	 */
	public static void printScheduleMenu() {
		final int ADD_TASK = 1;
		final int MODIFY_TASK = 2;
		final int DELETE_TASK = 3;
		final int VIEW_TASKS = 4;
		final int VIEW_CLASS = 5;
		final int VIEW = 6;
		System.out.println(printBlue("\n Schedule Menu: \n_____________________\n "));
		System.out.println(" (1)- Add a new task");
		System.out.println(" (2)- Edit a task");
		System.out.println(" (3)- Delete a task");
		System.out.println(" (4)- View all tasks");
		System.out.println(" (5)- View classes schedule");
		System.out.println(" (6)- View schedule\n \n (0)- Back to menu\n");
		System.out.print("      Choice: "); int choice = userInput.nextInt();
		Task t = new Task();
		System.out.println();
		switch(choice){
		case ADD_TASK :
			t.addTask(); break;
		case MODIFY_TASK:
				System.out.println("(1)-Change a task's length(given number of hours)\n"
						+ "(2)-Give/change a task's date/time");
				 int choice1 = userInput.nextInt();
				if(choice1 == 1) {
					t.changeTaskLength();
					menuChoiceSchedule();break;
					
				}else if (choice1 == 2) {
					t.changeTaskDateTime();
					menuChoiceSchedule();break;
				}else {
					System.out.println("Invalid input.");
					printScheduleMenu();break; }
		case DELETE_TASK :
			t.deleteTask(); break;
		case VIEW_TASKS:
			Queries q = new Queries();
			q.printTasks();
			menuChoiceSchedule(); break;
		case VIEW_CLASS:
			Schedule s = new Schedule();
			s.printSchedule("class");
			menuChoiceSchedule();break;
		case VIEW:
			Schedule s1 = new Schedule();
			s1.printSchedule("schedule");
			menuChoiceSchedule();break;
		case 0:
			printMenu();break;
		default :
			System.out.println(printRed("  Invalid input!"));
			printScheduleMenu();
		}
	}
	/**
	 * MENU_CHOICE methods prints 2 choices to the user to choose from
	 * Are called to keep program running.
	 */
	public static void menuChoiceSchedule() {
		Task t = new Task();
		System.out.print(printBlue("\n(1)-Edit a task\n(0)-To menu\n")+"   choice: ");
		int choice= userInput.nextInt();
		if (choice == 1) {
			System.out.println("(1)-Change a task's length(given number of hours)\n"
					+ "(2)-Give/change a task's date/time");
			 choice = userInput.nextInt();
			if(choice == 1) {
				t.changeTaskLength();
			}else if (choice == 2) {
				t.changeTaskDateTime();
			}else {
				System.out.println("invalid input!"); menuChoiceSchedule();
			}
		}else if (choice == 0) {
			printScheduleMenu();
		}else {
			System.out.println("Invalid input.");
			printScheduleMenu(); 
			}
	}
	
	public static void menuChoiceSemester() {
		System.out.println(printBlue(printBlue("\n (0) to main menu\n (1) to Semesters menu ")));
		System.out.print("   choice: ");
		int choice = userInput.nextInt();
		if (choice == 1) {
			printSemesterMenu();
		}else if (choice == 0) {
			printMenu();
		}else {
			System.out.println(" invalid input, press enter to try again");
			menuChoiceSemester();
		}
	}
		public static void menuChoiceCourse() {
			System.out.println(printBlue("\n (0) to main menu\n (1) to Courses menu "));
			System.out.print("   choice: ");
			int choice = userInput.nextInt();
			if (choice == 1) {
				printCourseMenu();
			}else if (choice == 0) {
				printMenu();
			}else {
				System.out.println(" invalid input, press enter to try again");
				menuChoiceCourse();
			}
	}
		public static void menuChoiceAssignment() {
			System.out.println(printBlue("\n (1) Assignments menu \n (0) Main menu"));
			System.out.print("      choice: ");
			int choice = userInput.nextInt();
			if (choice == 1) {
				printAssignmentMenu();
			}else if (choice == 0) {
				printMenu();
			}else {
				System.out.println(" invalid input, press enter to try again");
				menuChoiceAssignment();
			}
		}
		/**
		 * Sets a static semesterID value.
		 * @return
		 */
		public static int setSemesterId() {
			Queries queries = new Queries();
			queries.printList("semesters");
			System.out.print("Enter a static semester ID: ");
			int semesterId = userInput.nextInt();
			return semesterId;
		}
		public static String printBlue(String str) {
			final String ANSI_BLUE = "\u001b[36m";
			final String ANSI_RESET = "\u001B[0m";
			str = (ANSI_BLUE + str + ANSI_RESET);
			return str;
		}
		public static String printRed(String str) {
			final String ANSI_RESET = "\u001B[0m";
			final String ANSI_RED = "\u001B[31;1m";
			 str = (ANSI_RED + str + ANSI_RESET );
			return str;
		}
		
		public static final int YEAR = 2022;//the year program uses to manipulate tasks.
		
		static int semesterId = 0;// static semester id to be used when adding courses
								//to the program to avoid entering it multiple times.
}