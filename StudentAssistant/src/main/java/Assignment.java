import java.sql.*;
import java.util.Scanner;
/**
 * Class assignment contains methods to create/view/modify assignments
 * @author Said
 *
 */
public class Assignment implements Print  {
	private int id;
	private String name;
	private double grade;
	private String deadline;
	private double receivedGrade;
	private int courseId;
	private String courseName;
	Scanner input = new Scanner(System.in);
	Queries queries = new Queries();
	public Assignment() {
	}
	public Assignment(int id, String name, double grade, String deadline, double receivedGrade,String courseName, int courseId) {
		this.id = id;
		this.name = name;this.courseName = courseName;
		this.grade = grade; this.deadline = deadline; this.receivedGrade=receivedGrade;this.courseId=courseId;
	}public int getId() {
		return id;
	}public String getName() {
		return name;
	}public double getGrade() {
		return grade;
	}public String getDeadline() {
		return deadline;
	}public double getReceivedGrade(){
		return receivedGrade;
	}public int getCourseId() {
		return courseId;
	}public void setReceivedGrade(int receivedGrade) {
		this.receivedGrade = receivedGrade;
	}public void setId(int id) {
		this.id= id;
	}
	
	/**
	 * This method takes user input for assignment 
	 * and adds an assignment to the assignment table (mySQL)
	 * Calls Queries.printList("courses"), Queries.InsertIntoDatabase(), 
	 * 		 Queries.createTasksFromAssignments
	 */
	public void addAssignment() {
		System.out.print(" Enter assingment name: ");
		name = input.nextLine();
		System.out.print(" Enter assingment weight (out of 100): ");
		grade = input.nextDouble();input.nextLine();
		System.out.print(" Enter deadline day: ");
		int dayOfMonth = input.nextInt(); input.nextLine();
		System.out.print(" Enter deadline month: ");
		int month = input.nextInt(); input.nextLine();
		deadline = ""+Test.YEAR+"-"+month+"-"+ dayOfMonth;
		queries.printList("courses");
		System.out.print("Enter assignment's course ID: ");
		courseId = input.nextInt();
		System.out.println();
		printAssignmnetInfo();
		System.out.println("\nLooks good? \n(1) to add the assignment \n(2) to try again \n(0) to assignments menu");
		int choice = input.nextInt();
		if (choice == 1) {
			queries.insertIntoDatabase("assignments", name, deadline, courseId, grade);
			queries.createTasksFromAssignments();
		}else if (choice ==2) {
			addAssignment();
		}else if (choice == 0) {
			Test.printAssignmentMenu();
		}else {
			System.out.println("invalid input!");
			addAssignment();
		}
	}
	
	/**
	 * Takes user input to delete an assignment from DB
	 * Calls Queries.printGrades("completedassignments"), Queries.printGrades("assignments"), 
	 * 		 Queries.deleteRecord() 
	 */
	public void deleteAssignment() {
		System.out.println("\n Submitted");
		queries.printGrades("completedassignments");
		System.out.println();
		System.out.println("\n Not submitted");
		System.out.println();
		queries.printGrades("assignments");
		System.out.print("Enter Assignment ID to delete: ");
		int id = input.nextInt();
		System.out.println("Are you sure you want to delete assignment # " + id + "??\n (1)- to delete, (2)- to try again");
		int sure = input.nextInt();
		if (sure == 1) {
			queries.deleteRecord("assignments" , id);
		}else if (sure ==2) {
			deleteAssignment();
		}
	}
	/**
	 * Moves a record from assignment to completedassignments in DB
	 * Calls Queries.printList("courses"), Queries.printCourseAssignments(), Queries.submitAssignment() 
	 */
	public void submitAssignment() {
		queries.printList("courses");
		System.out.print("Enter course ID, or (0) to go back to menu: ");
		int courseid = input.nextInt();
		if (courseid == 0) {
			Test.printAssignmentMenu();
		}else {
			queries.printCourseAssignments(courseid);
		System.out.print("Enter Assignment ID to submit, or (0) to go back to menu:  ");
		int id = input.nextInt();input.nextLine();
		System.out.println();
		if (id == 0) {
			Test.printAssignmentMenu();
		}else {
		System.out.println("Submit assignment # " + id + "?");
		System.out.print("(1) to submit, (2) to enter id again: ");
		
		int sure = input.nextInt();
		if (sure == 1) {
			queries.submitAssignment(id);
			System.out.println();
			System.out.println("Assignment submitted!");
			System.out.println();
		}else if (sure == 2) {
			submitAssignment();}
		else {
			System.out.println("invalid input, going back to assignment menu..");
			Test.printAssignmentMenu();
		}
		System.out.println("(1) To enter assignment grade");
		System.out.println("(2) to submit another assignment" );
		System.out.println("(3) back to assignment menu");
		System.out.print("(0) to main menu: ");
		int choice = input.nextInt();
		if (choice == 2) {
			submitAssignment();
		}else if (choice == 3) {
			Test.printAssignmentMenu();
		}else if (choice == 0) {
			Test.printMenu();
		}else if (choice == 1) {
			System.out.print("Enter received grade: ");
			double grade = input.nextDouble();
			queries.updateReceivedGrade(id, grade);
			Test.printAssignmentMenu();
		}
		else {
			System.out.println("invalid input, going back to main menu..");
			Test.printAssignmentMenu();
		}
		}
		}
	}
	/**
	 * Changes the deadline of an assignment record in DB
	 * Calls Queries.PrintList("courses"), Queries.changeDeadline()
	 */
	public void changeAssignmentDeadline() {
		queries.printList("courses");
		System.out.print("Enter course ID, or (0) to go back to menu: ");
		int courseid = input.nextInt();
		if (courseid == 0) {
			Test.printAssignmentMenu();
		}else {
			queries.printCourseNotSubmittedAssignments(courseid);
		System.out.println("\n (0)- To assignments menu");
		System.out.print("   Enter assignment ID: ");
		int id = input.nextInt();
		if (id == 0) {
			Test.printAssignmentMenu();
		}
		System.out.print("Enter new day of Month: ");
		int dayOfMonth = input.nextInt();
		System.out.print("Enter new Month: ");
		int month = input.nextInt();
		String deadline = Test.YEAR+"-"+month+"-"+ dayOfMonth;
		queries.changeDeadline("assignments", id, deadline);
		}
	}
	/**
	 * Updates a completedassignment record's received grade in DB
	 * Calls Queries.printList("courses"), Queries.printCourseAssignments(), Queries.updateReceivedGrade()
	 */
	public void updateAssignmentReceivedGrade() {
		queries.printList("courses");
		System.out.print("Enter course ID, or (0) to go back to menu: ");
		int courseid = input.nextInt();
		if (courseid == 0) {
			Test.printAssignmentMenu();
		}else {
			queries.printCourseAssignments(courseid);
		}
		System.out.println();
		System.out.println( "(1) to submit an assignment"); 
		System.out.println("(0) to go back to menu:  ");
		System.out.print("Enter Assignment ID: "); 
		int id = input.nextInt();
		if (id == 0) {
			Test.printAssignmentMenu();
		}else if (id == 1) {
			submitAssignment();
		}
		System.out.print("Enter received grade: ");
		double grade = input.nextDouble(); input.nextLine();
		queries.updateReceivedGrade(id, grade);
		queries.printCourseAssignments(courseid);
	}
	/**
	 * Prints assignment info to console.
	 */
	public void printAssignmnetInfo() {
		System.out.println("assignment name: " +name);
		System.out.println("deadline : " +deadline);
		System.out.println("grade: " + grade +"%");
		System.out.println("courseID: " + courseId);
	}
	
	
}
