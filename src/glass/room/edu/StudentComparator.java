package glass.room.edu;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

	String subject;
	
	public StudentComparator(String subject) {
		this.subject = subject;
	}
	
	@Override
	public int compare(Student lhs, Student rhs) {
		if(subject.equals("Mathematics")) {
			//logic
		}
		else if(subject.equals("Literature")) {
			//logic
		}
		else if(subject.equals("Biology")) {
			//logic
		}
	}

}
