package com.github.hintofbasil.crabbler.Questions.QuestionExpanders;

import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.hintofbasil.crabbler.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by will on 07/06/16.
 */
public class UserDetailsExpander extends Expander {

    private static final int REQUIRED_ANSWERS = 2;

    TextView nameView;
    TextView emailView;

    public UserDetailsExpander(AppCompatActivity activity, JSONObject questionJson) {
        super(activity, questionJson, REQUIRED_ANSWERS);
    }

    @Override
    public void expandLayout() throws JSONException {
        activity.setContentView(R.layout.expander_user_details);

        ImageView imageView = (ImageView) activity.findViewById(R.id.image);
        TextView titleView = (TextView) activity.findViewById(R.id.title);
        TextView descriptionView = (TextView) activity.findViewById(R.id.description);

        nameView = (TextView) activity.findViewById(R.id.name);
        emailView = (TextView) activity.findViewById(R.id.email);

        imageView.setImageDrawable(getDrawable(getQuestionString("questionPicture")));
        titleView.setText(getQuestionString("questionTitle"));
        descriptionView.setText(getQuestionString("description"));

        nameView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableDisableNext();
            }
        });

        emailView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                enableDisableNext();
            }
        });
    }

    @Override
    protected void setPreviousAnswer(JSONArray answer) {
        try {
            nameView.setText(answer.getString(0));
            emailView.setText(answer.getString(1));
        } catch (JSONException|NullPointerException e) {
            Log.d("UserDetailsExpander", "Unable to parse answer");
        }
    }

    @Override
    public JSONArray getSelectedAnswer() {
        JSONArray array = new JSONArray();
        array.put(nameView.getText());
        array.put(emailView.getText());
        return array;
    }
}
