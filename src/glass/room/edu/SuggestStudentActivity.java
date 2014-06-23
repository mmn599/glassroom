package glass.room.edu;

import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;

import com.google.android.glass.app.Card;

public class SuggestStudentActivity extends Activity {

	//TODO: improve ordering logic
	
	DatabaseHandler db;
	private List<Student> students;
	private String subject; 
	private Student currLowest;
	private Student secondLowest;
	Intent suggestIntent;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		db = new DatabaseHandler(this);
		new Intent(this, QuestionActivity.class);
		subject = getIntent().getExtras().getString("subject");
		
		students = db.getAllContacts();
		db.close();
		currLowest = students.get(0);
		secondLowest = students.get(2);
		switch (subject) {
		case "Mathematics":
			for(Student cn : students) {
				if (currLowest.getMath() > cn.getMath()) {
					if (currLowest.getMath() < secondLowest.getMath()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getMath() < secondLowest.getMath()) {
					secondLowest = cn;
				}
			}
			break;
		case "Literature":
			for(Student cn : students) {
				if (currLowest.getEnglish() > cn.getEnglish()) {
					if (currLowest.getEnglish() < secondLowest.getEnglish()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getEnglish() < secondLowest.getEnglish()) {
					secondLowest = cn;
				}
			}
			break;
		case "Biology": default:
			for(Student cn : students) {
				if (currLowest.getScience() > cn.getScience()) {
					if (currLowest.getScience() < secondLowest.getScience()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getScience() < secondLowest.getScience()) {
					secondLowest = cn;
				}
			}
			break;
		}
		Card card = new Card(this);
		View vCard = card.getView();
		card.setImageLayout(Card.ImageLayout.LEFT);
		switch (currLowest.getName()) {
		case "Matthew":
		//	card.addImage(R.drawable.mattprof);
			break;
		case "Saleh":
		//	card.addImage(R.drawable.salehprof);
			break;
		case "Sanat":
		//	card.addImage(R.drawable.sanatprof);
			break;
		case "David":
		//	card.addImage(R.drawable.davidprof);
			break;
		case "Alex":
		//	card.addImage(R.drawable.alexprof);
			break;
		}
		
		card.setText(currLowest.getName());
		card.setFootnote(secondLowest.getName());
		vCard.setFocusable(true);
//    	Card card2 = new Card(this);
//    	card2.setText("FUCK");
    	//view2.setFocusable(true);
    	//view2.setOnClickListener(this);
		setContentView(card.getView());
    	
	}

	@Override
	public void onResume() {
		super.onResume();
		db = new DatabaseHandler(this);
		students = db.getAllContacts();
		db.close();
		currLowest = students.get(0);
		secondLowest = students.get(2);
		switch (subject) {
		case "Mathematics":
			for(Student cn : students) {
				if (currLowest.getMath() > cn.getMath()) {
					if (currLowest.getMath() < secondLowest.getMath()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getMath() < secondLowest.getMath()) {
					secondLowest = cn;
				}
			}
			break;
		case "Literature":
			for(Student cn : students) {
				if (currLowest.getEnglish() > cn.getEnglish()) {
					if (currLowest.getEnglish() < secondLowest.getEnglish()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getEnglish() < secondLowest.getEnglish()) {
					secondLowest = cn;
				}
			}
			break;
		case "Biology": default:
			for(Student cn : students) {
				if (currLowest.getScience() > cn.getScience()) {
					if (currLowest.getScience() < secondLowest.getScience()) {
						secondLowest = currLowest;
					}
					currLowest = cn;
				} else if (cn.getScience() < secondLowest.getScience()) {
					secondLowest = cn;
				}
			}
			break;
		}
		Card card = new Card(this);
		View vCard = card.getView();
		card.setImageLayout(Card.ImageLayout.LEFT);
		switch (currLowest.getName()) {
		case "Matthew":
//			card.addImage(R.drawable.mattprof);
			break;
		case "Saleh":
//			card.addImage(R.drawable.salehprof);
			break;
		case "Sanat":
//			card.addImage(R.drawable.sanatprof);
			break;
		case "David":
//			card.addImage(R.drawable.davidprof);
			break;
		case "Alex":
//			card.addImage(R.drawable.alexprof);
			break;
		}
		
		card.setText(currLowest.getName());
		card.setFootnote(secondLowest.getName());
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

}
