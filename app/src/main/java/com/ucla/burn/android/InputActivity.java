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
import com.ucla.burn.android.model.Message;
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
                Conversation topush = new Conversation();
                topush.setTitle(title.getText().toString());
                topush.setOwner(current);
                topush.setCompleted(false);
                topush.setLastActive(today);
                // TODO: Get names
                topush.setPrimaryName("A");
                topush.setSecondaryName("B");
                BurnDAO.pushConversation(topush, new Callback<Conversation>() {
                    @Override
                    public void onResponse(final Conversation conversation) {
                        // Push initial context message
                        final Message message = new Message();
                        message.setContext(true);
                        message.setPrimary(true);
                        message.setConversation(conversation);
                        message.setText(messageField.getText().toString());
                        BurnDAO.pushMessage(message, new Callback<Message>() {
                            @Override
                            public void onResponse(Message response) {
                                // Push blank message for suggestions
                                Message blank = new Message();
                                blank.setContext(false);
                                blank.setPrimary(true);
                                blank.setConversation(conversation);
                                BurnDAO.pushMessage(blank, new Callback<Message>() {
                                    @Override
                                    public void onResponse(Message response) {
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

                            @Override
                            public void onFailed(Exception e) {
                                e.printStackTrace();
                            }
                        });
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
