package glass.room.edu;

import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.glass.app.Card;

public class SuggestStudentActivity extends Activity {

	StudentDatabaseHandler db;
	String subject;
	private List<Student> students;
	Intent suggestIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		subject = getIntent().getExtras().getString("subject");
	}

	@Override
	public void onResume() {
		super.onResume();	
		db = new StudentDatabaseHandler(this);
		students = db.getAllContacts();
		
		db.close();

		String toreport = "";
		for(Student student : students) {
			toreport += (student.getName() + " ");
			for(String sub : Student.defaultSubjects) {
				toreport += (sub + ": "+student.getPerformance(sub).getCorrect() + " "+ student.getPerformance(sub).getTotal()+" ");
			}
			Log.d(Constants.TAG,toreport);
			toreport = "";
		}
		
		//TEST
		toreport = "";
		for(Student student : students) {
			toreport += (student.getName() + " ");
		}
		Log.d(Constants.TAG, toreport);
		
		sortStudentsBySubjectPerformace(students);
	
		toreport = "";
		for(Student student : students) {
			toreport += (student.getName() + " ");
		}
		Log.d(Constants.TAG, toreport);
		
		Student suggestion = students.get(0);
		Student alternateSuggestion = students.get(1);
		
		Card card = new Card(this);
		View vCard = card.getView();
		card.setImageLayout(Card.ImageLayout.LEFT);
		card.setText(suggestion.getName());
		card.setFootnote(alternateSuggestion.getName());
		vCard.setFocusable(true);
		setContentView(card.getView());
	}
	    
	@Override
	public boolean onKeyDown(int keycode, KeyEvent event) {
	   if (keycode == KeyEvent.KEYCODE_DPAD_CENTER) {
	        // user tapped touchpad, do something
	        Intent suggestIntent = new Intent(this, QuestionActivity.class);
	        suggestIntent.putExtra("subject", subject);
	        startActivity(suggestIntent);
	        return true;
	   }
	       
	   return super.onKeyDown(keycode, event);
     }
	
	public void sortStudentsBySubjectPerformace(List<Student> students) {
		Collections.sort(students, new StudentComparator(subject));
	}

}
