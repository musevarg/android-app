package com.github.musevarg.remar.Questions.QuestionExpanders;

import android.provider.CalendarContract;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.github.musevarg.remar.ColorListAdapter;
import com.github.musevarg.remar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Calendar;
import java.util.List;

public class MonthChoiceExpander extends Expander {

    private static final int REQUIRED_ANSWERS = 1;
    public static final int MIN_YEAR = 2016;

    ListView monthListView;

    int monthNo = -1;

    ColorListAdapter<String> monthsAdapter;

    int currentYear;
    int currentMonth;
    int year; //from previous question

    public MonthChoiceExpander(AppCompatActivity activity, JSONObject questionJson) {
        super(activity, questionJson, REQUIRED_ANSWERS);
    }

    @Override
    public void expandLayout() throws JSONException {
        activity.setContentView(R.layout.expander_month_choice);

        final ImageView imageView = (ImageView) activity.findViewById(R.id.image);
        TextView titleView = (TextView) activity.findViewById(R.id.title);
        TextView questionView = (TextView) activity.findViewById(R.id.question_text);
        monthListView = (ListView) activity.findViewById(R.id.month_list_view);

        imageView.setImageDrawable(getDrawable(getQuestionString("questionPicture")));
        titleView.setText(getRichTextQuestionString("questionTitle"));
        questionView.setText(getRichTextQuestionString("questionText"));

        currentMonth = Calendar.getInstance().get(Calendar.MONTH);
        currentYear = Calendar.getInstance().get(Calendar.YEAR);

        year = Integer.parseInt(getQuestionString("year"));


        monthListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(year == currentYear-2016 && (position) > currentMonth)
                {
                    disableDisableNext();
                    return;
                }
                monthNo = position;
                //month2ListView.setItemChecked(monthNo, true);
                // Colour is set as background on first selected.  Must override
                if(monthsAdapter != null) {
                    monthsAdapter.removeDefault();
                }
                enableDisableNext();
                //monthListView.setSelection(monthNo);
            }
        });

        // Fix scrolling
        monthListView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }

    @Override
    protected void setPreviousAnswer(JSONArray answer) {
        /*try {
            switch (answer.getInt(0)) {
                case -1: // Indicates not filled in yet
                    break;
                default:
                    Log.d("TwoChoiceDate", "Invalid previous answer");
                    break;
            }
        } catch (JSONException e) {
            Log.i("TwoChoiceDateExpander", "Unable to parse answer (0)");
        }*/

        try {
            monthNo = answer.getInt(0);
        } catch (NumberFormatException|ArrayIndexOutOfBoundsException|JSONException e) {
            Log.d("TwoChoiceDate", "No previous month");
        }

        if(year == currentYear - 2016)
        {
            monthsAdapter = new ColorListAdapter<String>(
                    activity.getBaseContext(),
                    R.layout.list_background,
                    activity.getResources().getStringArray(R.array.months),
                    monthNo,
                    currentMonth);
            monthListView.setAdapter(monthsAdapter);
        }
        else
        {
            monthsAdapter = new ColorListAdapter<String>(
                    activity.getBaseContext(),
                    R.layout.list_background,
                    activity.getResources().getStringArray(R.array.months),
                    monthNo,
                    -1);
            monthListView.setAdapter(monthsAdapter);
        }
    }

    @Override
    public JSONArray getSelectedAnswer() {
        JSONArray array = new JSONArray();
        array.put(monthNo);
        return array;
    }
}
