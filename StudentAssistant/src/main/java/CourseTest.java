import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CourseTest {

	@Test
	void test() {
		Course c = new Course();
		System.out.println(c.calculateLetterGrade(80));
	}

}
