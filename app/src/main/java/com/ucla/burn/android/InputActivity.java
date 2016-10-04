package com.ucla.burn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.User;

import java.util.Date;

public class InputActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input);

        Button newconvo = (Button) findViewById(R.id.itm_submit);
        final EditText title = (EditText) findViewById(R.id.lbl_title);
        final EditText messageField = (EditText) findViewById(R.id.lbl_content_submitted);
        final User current = Session.getInstance().getCurrentUser();
        final String currId = current.getId();
        final Date today = new Date();

        newconvo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                BurnDAO.createConversation(current, title.getText().toString(),
                        messageField.getText().toString(), new Callback<Conversation>() {
                    @Override
                    public void onResponse(Conversation conversation) {
                        Intent intent = new Intent(getApplicationContext(),
                                ConversationActivity.class);
                        intent.putExtra(ConversationActivity.EXTRA_ID,
                                conversation.getId());
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    public void onFailed(Exception e) {
                        e.printStackTrace();
                    }
                });
            }
        });
    }
}
