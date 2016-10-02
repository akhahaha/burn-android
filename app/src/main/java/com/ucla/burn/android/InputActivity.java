package com.ucla.burn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.User;

import java.util.Date;

public class InputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Button newconvo = (Button) findViewById(R.id.itm_submit);
        EditText title = (EditText) findViewById(R.id.lbl_title);
        final String title_topush = title.getText().toString();
        EditText message = (EditText) findViewById(R.id.lbl_content_submitted);
        final String message_topush = message.getText().toString();
        final User current = Session.getInstance().getCurrentUser();
        final String currId = current.getId();
        final Date today = new Date();

        newconvo.setOnClickListener((new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ShellActivity.class);
                Conversation topush = new Conversation();
                topush.setTitle(title_topush);
                topush.setOwner(current);
                topush.setCompleted(false);
                topush.setLastActive(today);
                topush.setPrimaryName("A");
                topush.setSecondaryName("B");
                BurnDAO.pushConversation(topush, null);

                startActivity(intent);
            }
        }));


    }
}
