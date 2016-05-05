package com.github.hintofbasil.crabbler.Questions.QuestionExpanders;

import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

import com.github.hintofbasil.crabbler.R;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by will on 05/05/16.
 */
public class TwoPictureLayoutExpander implements Expander {

    @Override
    public void expandLayout(AppCompatActivity activity, JSONObject question) throws JSONException {
        activity.setContentView(R.layout.two_picture_choice_layout);
        TextView questionText = (TextView) activity.findViewById(R.id.question_text);
        TextView choiceOneTitle = (TextView) activity.findViewById(R.id.choice_one_title);
        TextView choiceTwoTitle = (TextView) activity.findViewById(R.id.choice_two_title);

        questionText.setText(question.getString("questionText"));
        questionText.setMovementMethod(new ScrollingMovementMethod());

        choiceOneTitle.setText(question.getString("choiceOneTitle"));
        choiceTwoTitle.setText(question.getString("choiceTwoTitle"));
    }
}
