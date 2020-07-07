package com.github.musevarg.remar.Questions.QuestionExpanders;

import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.musevarg.remar.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.util.Date;

/**
 * Created by will on 13/05/16.
 */
public class CommitExpander extends Expander {

    private static final int REQUIRED_ANSWERS = 1;

    Button choiceOneButton;
    Button choiceTwoButton;
    boolean allowMultiple = false;

    public CommitExpander(AppCompatActivity activity, JSONObject questionJson) {
        super(activity, questionJson, REQUIRED_ANSWERS);
    }

    @Override
    public void expandLayout() throws JSONException {
        activity.setContentView(R.layout.expander_commit);
        //ImageView imageView = (ImageView) activity.findViewById(R.id.image);
        //TextView titleView = (TextView) activity.findViewById(R.id.title);
        TextView description = (TextView) activity.findViewById(R.id.description);
        choiceOneButton = (Button) activity.findViewById(R.id.choice_one);
        choiceTwoButton = (Button) activity.findViewById(R.id.choice_two);

        //imageView.setImageDrawable(getDrawable(getQuestionString("questionPicture")));
        //titleView.setText(getRichTextQuestionString("questionTitle"));
        description.setText(getRichTextQuestionString("description"));
        choiceOneButton.setText(getRichTextQuestionString("choiceOneText"));
        choiceTwoButton.setText(getRichTextQuestionString("choiceTwoText"));

        try {
            Boolean addImage = Boolean.parseBoolean(getQuestionString("hasImage"));

            if(addImage){
                try {
                    //imageView.setImageDrawable(getDrawable(getQuestionString("imageFile")));
                } catch (Exception e) {
                    Log.d("TwoChoice", "Failed to load image from imageFile, or could not find imagefile" + e.getStackTrace());
                }
            }
        } catch (Exception e) {
            Log.d("TwoChoice", "Could not find hasImage" + e.getStackTrace());
        }

        try {
            allowMultiple = questionJson.getBoolean("allowMultiple");
        } catch (JSONException e) {}

        choiceOneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            nextQuestion(0);
            }
        });

        choiceTwoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            previousQuestion();
            }
        });
    }

    @Override
    protected void setPreviousAnswer(JSONArray answer) {

    }

    @Override
    public JSONArray getSelectedAnswer() {
        JSONArray array = new JSONArray();
        array.put(DateFormat.getDateTimeInstance().format(new Date()).toString());
        return array;
    }
}
