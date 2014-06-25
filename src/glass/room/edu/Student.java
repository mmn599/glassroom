package glass.room.edu;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Student {
	
	private String name;
	private int _id;
	private Map<String, PerformanceRating> performance;
	public static final ArrayList<String> defaultSubjects = new ArrayList<String>() {{
		this.add("Mathematics");
		this.add("Literature");
		this.add("Biology");
	}};
	
	//default constructor
	public Student() {
		this("defaultstudent", defaultSubjects);
	}
	
	public Student(String name) {
		this(name, defaultSubjects);
	}
	
	public Student(String name, ArrayList<String> subjects) {
		performance = new HashMap<String, PerformanceRating>();
		this.name = name;
		_id = 0;
		for(String sub : subjects) {
			performance.put(sub, new PerformanceRating());
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getId() {
		return _id;
	}

	public void setId(int id) {
		this._id = id;
	}

	public PerformanceRating getPerformance(String subject) {
		return performance.get(subject);
	}
	
}
