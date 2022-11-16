import java.sql.*;
import java.util.Scanner;

public class Assignment extends Queries {
	private String name;
	private double grade;
	private String deadline;
	private int receivedGrade;
	private String courseId;
	Scanner input = new Scanner(System.in);
	public Assignment() {
		
	}
	/**
	 * this method takes user input for assignment 
	 * and adds an assignment to the assignment table (mySQL)
	 */
	public void addAssignment() {
		System.out.println("Enter assingment name: ");
		name = input.next();
		System.out.println("Enter assingment weight (out of 100): ");
		grade = input.nextDouble();input.nextLine();
		System.out.println("Enter deadline day: ");
		int dayOfMonth = input.nextInt(); input.nextLine();
		System.out.println("Enter deadline month: ");
		int month = input.nextInt(); input.nextLine();
		deadline = "2022-"+month+"-"+ dayOfMonth;
		printList("courses");
		System.out.print("Enter assignment's course ID: ");
		courseId = input.next();
		printAssignmnetInfo();
		System.out.println("Looks good? press '1' to add the assignment or '2' to try again");
		int choice = input.nextInt();
		if (choice == 1) {
		insertIntoDatabase("assignments", name, deadline, courseId, grade);
		}else if (choice ==2) {
			addAssignment();
		}
	}
	
	/**
	 * this method takes user input to delete an assignment 
	 * from database.
	 */
	public void deleteAssignment() {
		printList("assignments");
		System.out.print("Enter Assignment ID to delete: ");
		int id = input.nextInt();
		System.out.println("Are you sure you want to delete assignment # " + id + "?? (enter '1' to delete, '2' to try again");
		int sure = input.nextInt();
		if (sure == 1) {
			deleteRecord("assignments" , id);
		}else if (sure ==2) {
			deleteAssignment();
		}
	}
	
	public void submitAssignment() {
		
		printList("courses");
		System.out.print("Enter course ID, or (0) to go back to menu: ");
		int courseid = input.nextInt();
		if (courseid == 0) {
			Test.printAssignmentMenu();
		}else {
		printCourseAssignments(courseid);
		System.out.print("Enter Assignment ID to submit, or (0) to go back to menu:  ");
		int id = input.nextInt();
		if (id == 0) {
			Test.printAssignmentMenu();
		}else {
		System.out.println("Submit assignment # " + id +"? (Enter '1' to submit, '2' to enter id again");
		int sure = input.nextInt();
		if (sure == 1) {
			submitAssignment(id);
			System.out.println("Assignment submitted!");
		}else if (sure == 2) {
			submitAssignment();}
		else {
			System.out.println("invalid input, going back to assignment menu..");
			Test.printAssignmentMenu();
		}
		System.out.print("(2) to submit another assignment, (1) back to assignment menu, or (0) to main menu: ");
		int choice = input.nextInt();
		if (choice == 2) {
			submitAssignment();
		}else if (choice == 1) {
			Test.printAssignmentMenu();
		}else if (choice == 0) {
			Test.printMenu();
		}else {
			System.out.println("invalid input, going back to main menu..");
			Test.printAssignmentMenu();
		}
		}}
	}
	
	public void changeAssignmentDeadline() {
		printList("courses");
		System.out.print("Enter course ID, or (0) to go back to menu: ");
		int courseid = input.nextInt();
		if (courseid == 0) {
			Test.printAssignmentMenu();
		}else {
		printCourseAssignments(courseid);
		System.out.print("Enter assignment ID: ");
		int id = input.nextInt(); input.nextLine();
		System.out.print("Enter new day of Month: ");
		int dayOfMonth = input.nextInt();
		System.out.print("Enter new Month: ");
		int month = input.nextInt(); input.nextLine();
		String deadline = "2022-"+month+"-"+ dayOfMonth;
		changeDeadline("assignments", id, deadline);
		}
	}
	public void updateAssignmentReceivedGrade() {
		printList("completedassignments");
		System.out.print("Enter assignment ID to update received grade or (0) to submit assignment: ");
		int id = input.nextInt(); input.nextLine();
		if (id == 0) {
			submitAssignment();
		}else {
		System.out.print("Enter received grade: ");
		double grade = input.nextDouble(); input.nextLine();
		updateReceivedGrade(id, grade);
		printGrades("completedassignments");
		}
	}
	
		
	
	public double getAssingmentGrade() {
		return grade;
	}
	public void setReceivedGrade(int receivedGrade) {
		this.receivedGrade = receivedGrade;
	}

	public int getReceivedGrade() {
		return receivedGrade;
	}
	public void printAssignmnetInfo() {
		System.out.println("assignment name: " +name);
		System.out.println("deadline : " +deadline);
		System.out.println("grade: " + grade +"%");
		System.out.println("courseID: " + courseId);
	}
	
	
}
