package glass.room.edu;

import java.util.Comparator;

public class StudentComparator implements Comparator<Student> {

	String subject;

	public StudentComparator(String subject) {
		this.subject = subject;
	}

	@Override
	public int compare(Student lhs, Student rhs) {
		return Integer.compare(lhs.getPerformance(subject).getCorrect(), rhs
				.getPerformance(subject).getCorrect());
	}

}
