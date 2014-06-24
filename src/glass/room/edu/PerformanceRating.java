package glass.room.edu;

public class PerformanceRating {
	public int total;
	public int correct;
	
	public PerformanceRating() {
		total = 0;
		correct = 0;
	}
	
	public int getTotal() {
		return total;
	}
	
	public int getCorrect() {
		return correct;
	}
	
	public int getWrong() {
		return (total-correct);
	}
	
	public void setTotal(int total) {
		this.total = total;
	}
	
	public void setCorrect(int correct) {
		this.correct = correct;
	}
	
	public void incrementCorrect(int inp) {
		correct+=inp;
	}
	
	public void incrementTotal(int inp) {
		total+=inp;
	}
}
