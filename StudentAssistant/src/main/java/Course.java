
import java.util.Scanner;

public class Course extends Queries {
	 
	
	Scanner input = new Scanner(System.in);
	private String name;
	private int hours;
	private int grade;
	private String letterGrade;
	private int semesterId;
	private String classDay;
	private int classTime;
	
	
	public Course() {
		
	}
	public void addCourse() {
		System.out.print("Enter course name: ");
		name = input.nextLine();
	
		System.out.print("Enter course total hours: ");
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
		System.out.println(printRed("\nCourse details: \n"));
		System.out.println("Course name: " + printRed(name));
		System.out.println("Class: " + printRed(classDay + ", at: " + classTime));
		System.out.println("Course hours: " + printRed(String.valueOf(hours)));
		System.out.println("Semester ID = " + printRed(String.valueOf(Test.semesterId)));
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
	
	public void addClass() {
		printList("courses");
		System.out.print("Which course do you want to add a class to? ");
		int id = input.nextInt();
		System.out.println("\nDay of class?");
		System.out.println("1-Monday\n2-Tuesday\n3-Wednesday\n4-Thursday\n5-Friday");
		String day = input.next();
		switch(day){
		case "1": classDay = "Monday";
			break;
		case "2": classDay = "Tuesday";
			break;
		case "3": classDay = "Wednesday";
			break;
		case "4": classDay = "Thursday";
			break;
		case "5": classDay = "Friday";
		break;
		}
		System.out.print("\ntime of class?  ");
		int time = input.nextInt();
		System.out.print("\nhow many hours? ");
		int length = input.nextInt();
		System.out.println("Courseid: "+id+"\nclass day: " +classDay+"\nclass time: "+time);
		System.out.print("\n (1) to add class\n (0) to go back to menu: ");
		int choice = input.nextInt();
		if (choice == 1) {
			//adding classes record * length to the database.
			for(int i=0;i<length;i++) {
			insertClassToDatabase("classes",id, classDay, time+i, length);
			}try {
				Thread.sleep(2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			Test.printCourseMenu();
		}else {
			Test.printCourseMenu();
		}
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
	public String calculateLetterGrade(double grade) {
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
	
	

