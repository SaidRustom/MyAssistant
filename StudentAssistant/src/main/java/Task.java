
import java.time.LocalDate;
import java.time.Year;
import java.util.ArrayList;
import java.util.Scanner;
/**
 * Class Task is mainly used to create, modify, delete tasks.
 * Tasks are used to generate Schedule.
 * Tasks can be added manually by user or created from assignments
 * existing in DB that are due less than 2 weeks.
 * @author Said
 *
 */
public class Task {
		int id,time,length, assignmentid;
		String name, day, room, date;
		double grade;
		Queries queries = new Queries();
		Scanner input = new Scanner(System.in);
		public Task() {
		}
		public Task(String day,String name, int time,int length, String room) {
			this.time = time;
			this.day = day;
			this.name = name;
			this.length = length;
			this.room = room;
		}
		public Task (String day, String name, int time, int length) {
			this.time = time;
			this.day= day;
			this.name = name;
			this.length = length;
		}
		public Task(String day, String name, int time, int length, int assignmentid, int grade) {
			this.time = time;
			this.day = day;
			this.name = name;
			this.length = length;
			this.assignmentid = assignmentid;
			this.grade = grade;
		}
		public Task(int id,int assignmentid, String name, int length, double grade) {
			this.assignmentid = assignmentid;
			this.name = name;
			this.length = length;
			this.id = id;
			this.grade= grade;
		}
		public String getDay() {
			return day;
		}
		public String getName() {
			return name;
		
		}
		public int getTime() {
			return time;
		}
		public int getLength() {
			return length;
		}
		public double getGrade() {
			return grade;
		}
		public int getAssignmentId() {
			return assignmentid;
		}
		public String getRoom() {
			return room;
		}
		/**
		 * Takes user input to add a task into DB.
		 * Calls Queries.checkNoCLassAtTimeSlot(), Queries.checkNoTaskAtTimeSlot(), Queries.addTask()
		 */
		public void addTask() {
			System.out.print(" Enter task name: ");
			name = input.next();
			System.out.print(" Enter number of hours needed to complete the task: ");
			length = input.nextInt();
			System.out.println("\n (1) to enter a task with specific time\n"
					+ " (2) to enter a task deadline (helpful when task's time currently unknown)"
					+ "\n (0) to go back to menu");
			System.out.print("     choice: ");
			int choice = input.nextInt();
			if(choice == 1) {
				System.out.print(" Enter day of month: ");
				int day = input.nextInt(); input.nextLine();
				if(day > 31 || day < 1) {
					System.out.println(Print.printRed(" Invalid input.\n Please try again\n"));
					addTask();
				}
				System.out.print(" Enter month (number): ");
				int month = input.nextInt(); input.nextLine();
				while(month > 12 || month < 1 ) {
					System.out.println(Print.printRed(" Invalid input.\n Please try again\n"));
					month = input.nextInt();
				}
				System.out.print(" Enter task time: ");
				time = input.nextInt(); 
				while(time > 24 || time < 6) {
					System.out.println(Print.printRed("Invalid input. Please enter time between 7-24"));
					time = input.nextInt();
				}
				LocalDate date = LocalDate.of(Test.YEAR, month, day);
				System.out.println(Print.printBlue("\n Task name: ") + name);
				System.out.println(Print.printBlue(" Task day: ") + date.getDayOfWeek());
				System.out.println(Print.printBlue(" Task time: ") + time);
				System.out.println(Print.printBlue(" Task length: ") + length+" hours\n");
				System.out.print(" (1)- add task\n (2)- try again\n (0)- back to main menu"
						+ "\n    choice: ");
				choice = input.nextInt();
				if (choice == 1) {
					boolean noClass = queries.checkNoClassAtTimeSlot(date.getDayOfWeek().toString(), time);
					boolean noTask = queries.checkNoTasksAtTimeSlot(date.toString(), time, length);
					if(!noClass) {
						System.out.println(Print.printRed(" A class is scheduled for "+date.getDayOfWeek()+" at: "+time));
						System.out.print(" Would you like to overrite it?\n(1)Yes overrite (2)No\n   choice: ");
						int choice1 = input.nextInt();
						if (choice1 != 1) {
							addTask(); 
						}
					}if(!noTask) {
						System.out.println(Print.printRed(" A task is scheduled for "+date.getDayOfWeek()+" at: "+time));
						System.out.println(" Would you like to overrite it?\n(1)Yes overrite (2)No");
						int choice2 = input.nextInt();
						if (choice2 != 1) {
							addTask();
					}}
					queries.addTask(name, date.getDayOfWeek().toString(), time, date.toString(), length);
					Test.printScheduleMenu();
				}else if(choice == 2) {
					System.out.println(" OKAY!\n Going back to menu..");
					Test.printScheduleMenu();
				}else {
					Test.printScheduleMenu();
				}
			}else if (choice == 2) {
				System.out.print(" Enter day of month: ");
				int day = input.nextInt();
				System.out.print(" Enter month (number): ");
				int month = input.nextInt();	
				LocalDate date = LocalDate.of(Test.YEAR, month, day);
				queries.addTask(name, date.toString(), length);
				Test.printScheduleMenu();
			}else if (choice == 0) {
				Test.printScheduleMenu();
			}else {
				System.out.println("Invalid input"); addTask();
			}
		}
		/**
		 * Changes a tasks's time 
		 * Calls Schedule.printSchedule(), Queries.checkNoClassAtTimeSlot(), 
		 * 		Queries.checkNoTaskAtTimeSlot(), Queries.changeTaskDateTime(), 
		 * 			Task.findWeekDayDate().
		 */
		public void changeTaskDateTime() {
			Schedule s = new Schedule();
			s.printSchedule("schedule");
			queries.printTasks();
			System.out.print("\n Enter taskID: "); id = input.nextInt();
			System.out.print("\n (1)-Add task to this week's schedule"
					+ "\n (2)-Add a specific date (helpful for tasks scheduled further than a week from now)"
					+ "\n  Choice: "); int choice = input.nextInt();
			if (choice == 1) {
			System.out.println("\n (1)-Monday\n (2)-Tuesday\n (3)-Wednesday\n (4)-Thursday"
					+ "\n (5)-Friday\n (6)-Saturday\n (7)-Sunday");
			System.out.print(Print.printBlue("\n  Enter day number: "));
			int d = input.nextInt(); // user input for day.
			switch(d){
			case 1: day = "Monday";
				break;
			case 2: day = "Tuesday";
				break;
			case 3: day = "Wednesday";
				break;
			case 4: day = "Thursday";
				break;
			case 5: day = "Friday";
				break;
			case 6: day = "Saturday";
				break;
			case 7: day = "Sunday";
				break;
			}System.out.print(Print.printBlue(" Enter new task time: ")); time= input.nextInt();
			this.date = findWeekDayDate(day);
			}else if (choice == 2) {
				System.out.print(" Enter day of month: ");
				int day = input.nextInt();
				System.out.print(" Enter month (number): ");
				int month = input.nextInt();
				System.out.print(" Enter task time: ");
				time = input.nextInt();
				LocalDate date = LocalDate.of(Test.YEAR, month, day);
				this.date = date.toString();
				boolean noTask = queries.checkNoTasksAtTimeSlot(date.toString(), time, 2);
				if (!noTask) {
					System.out.println(Print.printRed(" Another task is scheduled at this time."
							+ "\n Task date NOT changed")); Test.printScheduleMenu();
				}
			}
			boolean noClass = queries.checkNoClassAtTimeSlot(day, time);
			if(!noClass) {
				System.out.println(Print.printRed("\n A class is scheduled for "+day+" at: "+time));
				System.out.println(" Would you like to continue?\n  (1)Yes\n (2)No");
				int choice1 = input.nextInt();
				if (choice1 != 1) {
					System.out.println(Print.printRed(" OKAY!"));
					Test.printScheduleMenu();}}
				queries.changeTaskDateTime(id, date, time);
			Test.printMenu();
		}
		/**
		 * Changes a task's length
		 * Calls Queries.printTasks(), Queries.changeTaskLength()
		 */
		public void changeTaskLength() {
			Schedule s = new Schedule();
			s.printSchedule("schedule");
			queries.printTasks();
			System.out.print("\n Enter taskID: "); id = input.nextInt();
			System.out.print("\n Enter new length: "); length = input.nextInt();
			queries.changeTaskLength(id, length);
		}
		public void deleteTask() {
			queries.printTasks();
			System.out.print("\n Enter taskID: "); id = input.nextInt();
			System.out.println(" Deleting task# " + id +" ...\n(1)-To confirm \n(2)-To menu");
			int choice = input.nextInt();
			if (choice == 1 ) {
				queries.deleteTask(id);
				Test.printScheduleMenu();
			}else {
				Test.printScheduleMenu();
			}
		}
		/**
		 * finds the date (yyyy-mm-dd) of a specific day of current week.
		 */
		public static String findWeekDayDate(String day) { 
		int taskDay;
		String date = "";
		LocalDate today = LocalDate.now();
		Year y = Year.of(today.getYear()); //current year
		//System.out.println(taskDate);
		for (int i = 0; i<7; i++) {
			if (today.getDayOfYear() + i > 365) {
				taskDay = today.getDayOfYear() + i - 365;
				y= Year.of(today.getYear()+1);
			}else {
				taskDay = today.getDayOfYear()+i; //dayOfYear+i to find dif in days between today and 'day' 
			}
			String tDay = y.atDay(taskDay).getDayOfWeek().toString(); //TaskDay dayofWeek (ex:MONDAY)
			if (tDay.equals(day.toUpperCase())) {
				date = y.atDay(taskDay).toString();
				System.out.println(y.atDay(taskDay));
				}
			}return date;
		}
		

}