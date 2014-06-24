package glass.room.edu;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.widget.TextView;

public class QuestionActivity extends Activity {

	//TODO: general cleanup
	StudentDatabaseHandler db;
	TextView txtview_speechresults;
	String subject;
	String name;
	int correct;
	
	public static final String[] positiveWords = {
		"good",
		"great",
		"awesome",
		"excellent",
		"perfect",
		"wonderful",
		"yes",
		"you got it"
	};
	public static final String[] negativeWords = {
		"not quite",
		"try",
		"almost",
		"no",
		"nope"
	};
	
	private static final int SPEECH_REQUEST = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_question);
		Intent intent = getIntent();
		subject = (String) intent.getStringExtra("subject");
		Log.d("TAG","subject is :"+subject);
		displaySpeechRecognizer();
	}

	//TODO: update to allow for more fluid speech
	private void displaySpeechRecognizer() {
	    Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
	    intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Tell the student how it is.");
	    startActivityForResult(intent, SPEECH_REQUEST);
	}

	//TODO: update to allow for more fluid speech
	@Override
	protected void onActivityResult(int requestCode, int resultCode,
	        Intent data) {
	    if (requestCode == SPEECH_REQUEST && resultCode == RESULT_OK) {
	        List<String> results = data.getStringArrayListExtra(
	                RecognizerIntent.EXTRA_RESULTS);
	        String teacherResponse = results.get(0);
	        int quality = parseTeacherResponseForQuality(teacherResponse);
	        db = new StudentDatabaseHandler(this);
	        Student student = parseTeacherResponseForName(teacherResponse);
	        updateStudent(student, subject, quality);
	        db.close();
	    }
	    super.onActivityResult(requestCode, resultCode, data);
	    this.finish();
	}

	public void inputData() {

	}

	//quality of 1 means correct question
	public void updateStudent(Student student, String subject, int quality) {
	    student.getPerformance(subject).incrementCorrect(quality);
	    student.getPerformance(subject).incrementTotal(1);
		db.updateContact(student);
	}
	
	public int parseTeacherResponseForQuality(String input) {
		int quality = 0;
		for(String pos : positiveWords) {
			if(input.contains(pos)) {
				quality++;
			}
		}
		for(String neg: negativeWords) {
			if(input.contains(neg)) {
				quality--;
			}
		}
		if(quality>0) {
			return PerformanceRating.CORRECT_ANSWER;
		}
		else {
			return PerformanceRating.INCORRECT_ANSWER;
		}
	}
	
	public Student parseTeacherResponseForName(String input) {
		Student studentSelected = null;
		List<Student> students = db.getAllContacts();
		db.close();
		for(Student cs : students) {
			if(input.contains(cs.getName())) {
				studentSelected = cs;
			}
		}
		if(studentSelected == null) {
			return null;
		}
		return studentSelected;
	}

}
