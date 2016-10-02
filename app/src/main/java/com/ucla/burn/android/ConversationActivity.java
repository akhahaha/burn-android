package com.ucla.burn.android;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.v13.app.FragmentStatePagerAdapter;
import android.view.View;
import android.widget.LinearLayout;

import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.Message;


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

        mMsgSelectionPager.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MessageFragment.newInstance(MessageFragment.SIDE_RIGHT);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        BurnDAO.getConversation(conversationId, new Callback<Conversation>() {
            @Override
            public void onResponse(Conversation conversation) {
                for (Message message : conversation.getMessages()) {
                    mConvoHolder.addView(newMessage(message.getId()));
                }
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

    private WrapContentViewPager newMessage(String messageId) {
        // TODO: Use BurnDAO.getMessage()
        WrapContentViewPager message;

        final int side = 0;
        if (side == MessageFragment.SIDE_LEFT) {
            message = (WrapContentViewPager) getLayoutInflater().inflate(
                    R.layout.viewpager_message_left, mConvoHolder, false);
        } else {
            message = (WrapContentViewPager) getLayoutInflater().inflate(
                    R.layout.viewpager_message_right, mConvoHolder, false);
        }
        message.setId(View.generateViewId());
        message.setAdapter(new FragmentStatePagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return MessageFragment.newInstance(side);
            }

            @Override
            public int getCount() {
                return 3;
            }
        });

        return message;
    }
}
