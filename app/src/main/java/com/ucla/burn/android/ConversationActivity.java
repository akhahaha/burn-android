package com.ucla.burn.android;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;


public class ConversationActivity extends AppCompatActivity {

    LinearLayout mConvoHolder;
    WrapContentViewPager mMsgSelectionPager;

    public static final String EXTRA_ID = "EXTRA_ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conversation);
        mConvoHolder = (LinearLayout) findViewById(R.id.convo_holder_linear_layout);
        mMsgSelectionPager = (WrapContentViewPager) findViewById(R.id.msg_selection_view_pager);

        String ID = getIntent().getStringExtra(EXTRA_ID);
        

        mConvoHolder.addView(newMessage(MessageFragment.SIDE_LEFT));
        mConvoHolder.addView(newMessage(MessageFragment.SIDE_RIGHT));
        mConvoHolder.addView(newMessage(MessageFragment.SIDE_RIGHT));
        mConvoHolder.addView(newMessage(MessageFragment.SIDE_LEFT));
        mConvoHolder.addView(newMessage(MessageFragment.SIDE_RIGHT));
        mConvoHolder.addView(newMessage(MessageFragment.SIDE_RIGHT));
        mMsgSelectionPager.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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

    private WrapContentViewPager newMessage(final int side){
        WrapContentViewPager message;

        if(side==MessageFragment.SIDE_LEFT)
            message = (WrapContentViewPager) getLayoutInflater().inflate(R.layout.viewpager_message_left,mConvoHolder,false);
        else message = (WrapContentViewPager) getLayoutInflater().inflate(R.layout.viewpager_message_right,mConvoHolder,false);
        message.setId(View.generateViewId());


        ViewPager.LayoutParams layoutParams = new ViewPager.LayoutParams();

        message.setAdapter(new FragmentStatePagerAdapter(getSupportFragmentManager()) {
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
