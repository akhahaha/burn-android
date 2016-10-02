package com.ucla.burn.android;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.LinearLayout;

import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.Message;
import com.ucla.burn.android.model.Suggestion;

import java.util.List;


public class ConversationActivity extends Activity {
    private String conversationId;
    LinearLayout mConvoHolder;
    WrapContentViewPager mMsgSelectionPager;

    public static final String EXTRA_ID = "EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mConvoHolder = (LinearLayout) findViewById(R.id.convo_holder_linear_layout);
        mMsgSelectionPager = (WrapContentViewPager) findViewById(R.id.msg_selection_view_pager);
        conversationId = getIntent().getStringExtra(EXTRA_ID);

    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        BurnDAO.getConversation(conversationId, new Callback<Conversation>() {
            @Override
            public void onResponse(final Conversation conversation) {
                for (int i=0;i<conversation.getMessages().size()-1;i++) {
                    BurnDAO.getMessage(conversation.getMessages().get(i).getId(), new Callback<Message>() {
                        @Override
                        public void onResponse(Message response) {
                            mConvoHolder.addView(newMessage(response));

                        }

                        @Override
                        public void onFailed(Exception e) {

                        }
                    });
                }

            }

            @Override
            public void onFailed(Exception e) {

            }
        });
        BurnDAO.getConversationSuggestions(conversationId, new Callback<List<Suggestion>>() {
            @Override
            public void onResponse(final List<Suggestion> response) {
                mMsgSelectionPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
                    @Override
                    public Fragment getItem(int position) {
                        return MessageFragment.newInstance(MessageFragment.SIDE_RIGHT,response.get(position).getText());
                    }

                    @Override
                    public int getCount() {
                        return response.size();
                    }
                });
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private WrapContentViewPager newMessage(final Message message) {
        final WrapContentViewPager viewPager;
        final int side = message.isPrimary()?MessageFragment.SIDE_RIGHT:MessageFragment.SIDE_LEFT;


        if (side == MessageFragment.SIDE_LEFT) {
            viewPager = (WrapContentViewPager) getLayoutInflater().inflate(
                    R.layout.viewpager_message_left, mConvoHolder, false);
        } else {
            viewPager = (WrapContentViewPager) getLayoutInflater().inflate(
                    R.layout.viewpager_message_right, mConvoHolder, false);
        }
        viewPager.setId(View.generateViewId());
        viewPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MessageFragment.newInstance(side,
                        message.isContext()?message.getText():message.getSuggestions().get(position).getText());
            }

            @Override
            public int getCount() {
                return message.isContext()?1:message.getSuggestions().size();
            }
        });

        return viewPager;
    }
}
