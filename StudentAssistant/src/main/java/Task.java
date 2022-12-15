import java.util.ArrayList;

public class Task extends Course {
		public int id,time,length, assignmentid;
		public String name, day;
		public double grade;
		public Task() {
		}
		public Task(String day,String name, int time,int length) {
			this.time = time;
			this.day = day;
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
		/**public void assignmentsToTasks() {
			ArrayList<Task> tasks = getSchoolTasks(8);
			System.out.println(padString(""));
			for(int i =0; i<tasks.size();i++) {
				Task t = tasks.get(i);
				System.out.println(padString());
			}
				
			
		}**/
		
	
	}