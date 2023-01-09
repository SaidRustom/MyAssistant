import java.time.LocalDate;
import java.util.ArrayList;
import java.util.LinkedList;
/**
 * Class Schedule is used to create and manipulate schedule. 
 * Schedule is created from Tasks, that are created from tasks, classes tables in DB.
 * @author Said
 *
 */
public class Schedule extends Task {
	Queries queries = new Queries();
	/**
	 * 7 for 7 days per week
	 * 21 for the number of hours. for now,
	 * the program will function between 9am-9pm
	 * (22 = 12 + 10) because the index of the array refers to time on schedule.
	 * therefore using indexes between 7-24 
	 * 2ndD index = 30 only to array exception out of bound.
	 */
	public String[][] schedule = new String[7][30];
	
	 
	/**
	 * populates schedule[][] with tasks and classes of the week.
	 */
	public String[][] getSchedule() {
		String[][] schedule = new String[7][30];
		ArrayList<Task> tasks = queries.getWeekTasks();
		ArrayList<Task> classes = queries.getWeekClasses();
		classes.addAll(tasks); tasks = classes; // to allow tasks to overwrite classes.
		Task t = new Task();
		for (int i = 0; i<tasks.size(); i++) {
			t = tasks.get(i);
			String day = t.getDay().toUpperCase();
			switch (day) {
			case "MONDAY" : schedule[0][t.getTime()] = t.getName();
				break;
			case "TUESDAY" : schedule[1][t.getTime()] = t.getName();				 
				break;
			case "WEDNESDAY" : schedule[2][t.getTime()] = t.getName();				   
				break;
			case "THURSDAY" : schedule[3][t.getTime()] = t.getName();				  
				break;
			case "FRIDAY" : schedule[4][t.getTime()] = t.getName();
				break;
			case "SATURDAY" : schedule[5][t.getTime()] = t.getName();
				break;
			case "SUNDAY" : schedule[6][t.getTime()] = t.getName();
				break;
			}
		}return schedule;
	}
	/**
	 * populates classSchedule[][] with classes of the week.
	 */
	public String[][] getClassesSchedule() {
		String[][] classSchedule = new String[7][30];
		ArrayList<Task> classes = queries.getWeekClasses();
		Task t = new Task();
		for (int i = 0; i<classes.size(); i++) {
			t = classes.get(i);
			String day = t.getDay().toUpperCase();
			switch (day) {
			case "MONDAY" : classSchedule[0][t.getTime()] = t.getName();
				break;
			case "TUESDAY" : classSchedule[1][t.getTime()] = t.getName();				 
				break;
			case "WEDNESDAY" : classSchedule[2][t.getTime()] = t.getName();				   
				break;
			case "THURSDAY" : classSchedule[3][t.getTime()] = t.getName();				  
				break;
			case "FRIDAY" : classSchedule[4][t.getTime()] = t.getName();
				break;
			case "SATURDAY" : classSchedule[5][t.getTime()] = t.getName();
				break;
			case "SUNDAY" : classSchedule[6][t.getTime()] = t.getName();
				break;
			}
		}return classSchedule;
	}
	/**
	 * Prints out the schedule to console.
	 */
	public void printSchedule(String scheduleName) {
		if(scheduleName.equals("class")) {
			schedule = getClassesSchedule();
		}else if (scheduleName.equals("schedule")) {
			schedule = getSchedule();
		}
		// since the schedule starts at 7
		//index 6 will be used to store day of the week.
		schedule[0][6] = "MONDAY";
		schedule[1][6] = "TUESDAY";
		schedule[2][6] = "WEDNESDAY";
		schedule[3][6] = "THURSDAY";
		schedule[4][6] = "FRIDAY";
		schedule[5][6] = "SATURDAY";
		schedule[6][6] = "SUNDAY";
		
		System.out.print("     ");
		for(int j=6;j<25;j++) {
			if(j>9) {
				System.out.print(Print.printBlue(Print.padString(""+j+"|", 5)));
				//fixing the spaces on schedule (for visual purposes)
			}if(j==7 || j==8 || j==9) {
				System.out.print(Print.printBlue(Print.padString(" "+j+"|", 5)));
			}
			for (int i=0; i<7;i++) {
				if(j==6) { // To change the color (for visual purposes)
				System.out.print("\u001b[36m");
				}
				System.out.print(Print.padString(schedule[i][j], 21)+Print.padString("|",3));
				}
		System.out.println();
		System.out.print("\u001b[36m");System.out.println(Print.padString("__|",3)
			+Print.padString("\u001B[0m_______________________|",24) +Print.padString("_______________________|",24)
			+Print.padString("_______________________|",24)+Print.padString("_______________________|",24)
			+Print.padString("_______________________|",24)+Print.padString("_______________________|",24)
			+Print.padString("_______________________|",24));
		}
	}
	public void printTodaySchedule() {
		schedule = getSchedule();
		LocalDate date = LocalDate.now();
		int day=0 ;
		String today = date.getDayOfWeek().toString().toUpperCase();
		switch(today) {
		case "MONDAY"   : day = 0; break;
		case "TUESDAY"  : day = 1; break;
		case "WEDNESDAY": day = 2; break;
		case "THURSDAY" : day = 3; break;
		case "FRIDAY"   : day = 4; break;
		case "SATURDAY" : day = 5; break;
		case "SUNDAY"   : day = 6; break;
			}
		System.out.println("\n--"+Print.printBlue(today)+"--\n");
		for (int i=6;i<25;i++) {
			while(schedule[day][i] != null) {
			System.out.println(Print.padString(" "+Print.printBlue(i+""), 6)+": "+(schedule[day][i]));
			break;
			}
		}
		}
		
	/**
	 * Finds available time slots on schedule.
	 * @param day
	 * @return int[] where value 0 => available slot, value 1 =>unavailable
	 * index number refers to the hour on schedule. (NOT USED ANYWHERE YET)
	 */
	public int[] getAvailabeSlots(int day){
		getSchedule();
		int[] availableSlots = new int [30];
		for(int i = 7; i <25; i++) {
			if (schedule[day][i] == null ) {
				availableSlots[i] = 0;
			}else {
				availableSlots[i] = 1;
			}System.out.println(i +" " + availableSlots[i]);
			}return availableSlots;
	}
}

	
	
	
	
	
	
	


