package glass.room.edu;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.google.android.glass.app.Card;
import com.google.android.glass.widget.CardScrollAdapter;
import com.google.android.glass.widget.CardScrollView;

public class MainActivity extends Activity implements OnItemClickListener {

	private List<Card> cards;
	private CardScrollView cardscrollview;
	//TODO: lookup how to properly handling databasing in android. do it.
	private DatabaseHandler db;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    
    private void createCards() {
        cards = new ArrayList<Card>();

        //creates 3 example subject cards
        //TODO: Make this dynamic. A teacher can set up and change her subjects.
        Card card;
        card = new Card(this);
        card.setText("Biology");
        card.setFootnote("Chapter 13, Biospheres");
        cards.add(card);

        Card card2;
        card2 = new Card(this);
        card2.setText("Literature");
        card2.setFootnote("19th Century English");
        cards.add(card2);

        Card card3;
        card3 = new Card(this);
        card3.setText("Mathematics");
        card3.setFootnote("Elementary differentiation");
        cards.add(card3);
    }
    
    @Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		Intent suggestIntent = new Intent(this, SuggestStudentActivity.class);
		suggestIntent.putExtra("subject", cards.get(position).getText());
		startActivity(suggestIntent);
	}
    
    private class ExampleCardScrollAdapter extends CardScrollAdapter {

        @Override
        public int getPosition(Object item) {
            return cards.indexOf(item);
        }

        @Override
        public int getCount() {
            return cards.size();
        }

        @Override
        public Object getItem(int position) {
            return cards.get(position);
        }

        @Override
        public int getViewTypeCount() {
            return Card.getViewTypeCount();
        }

        @Override
        public int getItemViewType(int position){
            return cards.get(position).getItemViewType();
        }

        @Override
        public View getView(int position, View convertView,
                ViewGroup parent) {
            return  cards.get(position).getView(convertView, parent);
        }
    }
    
    //creates some basic student data as an example
    //TODO: provide basic setup funtionality to add students to program
    public void fakeData() {
    	Student student2 = new Student();
    	student2.setName("Matthew");
    	Student student4 = new Student();
    	student4.setName("Alex");
    	Student student5 = new Student();
    	student5.setName("David");
    	db.addContact(student2);
    	db.addContact(student4);
    	db.addContact(student5);
    	db.close();
    }


}
