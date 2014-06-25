package glass.room.edu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class ChooseSubjectActivity extends Activity implements OnItemClickListener {
	
	private List<Card> subjectCards;
	private List<String> subjects;
	private CardScrollView subjectCardScroll;
	//TODO: lookup how to properly handling databasing in android. do it.
	private StudentDatabaseHandler db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.deleteDatabase("studentsDatabase");
        Log.d(Constants.TAG, "Deleted database");
        fakeData();
        createCards();
        subjectCardScroll = new CardScrollView(this);
    	subjectCardScroll.setOnItemClickListener(this);
    	SubjectCardScrollAdapter adapter = new SubjectCardScrollAdapter();
    	subjectCardScroll.setAdapter(adapter);
    	subjectCardScroll.activate();
    	setContentView(subjectCardScroll);
    	Log.d(Constants.TAG, "app started");
    }
    
    private void createCards() {
    	subjectCards = new ArrayList<Card>();
        subjects = Student.defaultSubjects;
        for (String subj : subjects) {
            Card subjCard = new Card(this);
        	subjCard.setText(subj);
        	// Currently we don't have any extra information alongside subjects,
        	// but in the future populating the footnote/image would be nice.
        	// subCard.setFootnote("MORE INFO");
        	// subCard.setImageLayout(Card.ImageLayout.LEFT); // This will probably be moved to top.
        	// subCard.addImage("CORRESPONDING IMAGE");
        	subjectCards.add(subjCard);
        }
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent suggestIntent = new Intent(this, SuggestStudentActivity.class);
		suggestIntent.putExtra("subject", subjectCards.get(position).getText());
		startActivity(suggestIntent);
	}
    
    private class SubjectCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return subjectCards.indexOf(item);
        }

        @Override
        public int getCount() {
            return subjectCards.size();
        }

        @Override
        public Object getItem(int position) {
            return subjectCards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return Card.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position){
            return subjectCards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView,
                ViewGroup parent) {
            return  subjectCards.get(position).getView(convertView, parent);
        }
    }
    
    public void fakeData() {
    	Student student1 = new Student("Matthew");
    	Student student2 = new Student("Alex");
    	Student student3 = new Student("Jason");
    	db = new StudentDatabaseHandler(this);
    	db.addStudent(student1);
    	db.addStudent(student2);
    	db.addStudent(student3);
    }
}
