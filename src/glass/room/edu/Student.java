package glass.room.edu;

public class Student {
	private String name;
	private int _id;
	private int correctEnglish;
	private int totalEnglish;
	private int correctMath;
	private int totalMath;
	private int correctScience;
	private int totalScience;
	
	public Student() {
		name = "Fake";
		_id = 0;
		correctEnglish = 0;
		totalEnglish = 0;
		correctMath = 0;
		totalMath = 0;
		correctScience = 0;
		totalScience = 0;
	}
	
	public Student(String name, int id){
		this.setName(name);
		this.setId(id);
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

	public int getCorrectEnglish() {
		return correctEnglish;
	}

	public void setCorrectEnglish(int correctEnglish) {
		this.correctEnglish = correctEnglish;
	}

	public int getEnglish() {
		return totalEnglish;
	}

	public void setEnglish(int totalEnglish) {
		this.totalEnglish = totalEnglish;
	}

	public int getCorrectMath() {
		return correctMath;
	}

	public void setCorrectMath(int correctMath) {
		this.correctMath = correctMath;
	}

	public int getMath() {
		return totalMath;
	}

	public void setMath(int totalMath) {
		this.totalMath = totalMath;
	}

	public int getCorrectScience() {
		return correctScience;
	}

	public void setCorrectScience(int correctScience) {
		this.correctScience = correctScience;
	}

	public int getScience() {
		return totalScience;
	}

	public void setScience(int totalScience) {
		this.totalScience = totalScience;
	}
}
