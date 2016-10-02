package com.ucla.burn.android;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by Allan on 10/1/2016.
 */

public class MessageFragment extends Fragment {
    private static final String BUNDLE_SIDE_PARAM = "side";
    public static final int SIDE_LEFT=0;
    public static final int SIDE_RIGHT=1;
    TextView mMessage;

    public static MessageFragment newInstance(int side){
        MessageFragment m = new MessageFragment();
        Bundle b = new Bundle();
        b.putInt(BUNDLE_SIDE_PARAM,side);
        m.setArguments(b);
        return m;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        int color = getArguments().getInt(BUNDLE_SIDE_PARAM);

        int layoutRes;
        if(color==SIDE_LEFT)
            layoutRes=R.layout.textview_message_left;
        else layoutRes=R.layout.textview_message_right;

        View v = inflater.inflate(layoutRes,container,false);
        mMessage= (TextView) v;
        return v;
    }
}
