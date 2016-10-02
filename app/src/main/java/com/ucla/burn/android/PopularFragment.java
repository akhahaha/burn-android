package com.ucla.burn.android;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ucla.burn.android.adapter.ConversationListAdapter;
import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link PopularFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link PopularFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PopularFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_TITLE = "EXTRA_TITLE";

    private RecyclerView conversationListView;
    private ConversationListAdapter conversationListAdapter;
//    private OnFragmentInteractionListener mListener;

    public PopularFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PopularFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PopularFragment newInstance(int position) {
        PopularFragment fragment = new PopularFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.content_main, container, false);

        conversationListView = (RecyclerView) rootView.findViewById(R.id.conversation_list_view);
        conversationListView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return rootView;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        BurnDAO.getConversations(new Callback<List<Conversation>>() {
            @Override
            public void onResponse(List<Conversation> conversations) {
                conversationListAdapter = new ConversationListAdapter(conversations);
                conversationListAdapter.setOnItemClickListener(
                        new ConversationListAdapter.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, Conversation conversation, int position) {
                                Intent intent = new Intent(getActivity(), ConversationActivity.class);
                                Bundle extras = new Bundle();
                                extras.putString(EXTRA_TITLE, conversation.getTitle());
                                startActivity(intent);
                            }
                        });
                conversationListView.setAdapter(conversationListAdapter);
            }

            @Override
            public void onFailed(Exception e) {

            }
        });
    }

//
//    // TODO: Rename method, update argument and hook method into UI event
//    public void onButtonPressed(Uri uri) {
//        if (mListener != null) {
//            mListener.onFragmentInteraction(uri);
//        }
//    }
//
//    @Override
//    public void onAttach(Context context) {
//        super.onAttach(context);
//        if (context instanceof OnFragmentInteractionListener) {
//            mListener = (OnFragmentInteractionListener) context;
//        } else {
//            throw new RuntimeException(context.toString()
//                    + " must implement OnFragmentInteractionListener");
//        }
//    }
//
//    @Override
//    public void onDetach() {
//        super.onDetach();
//        mListener = null;
//    }
//
//    /**
//     * This interface must be implemented by activities that contain this
//     * fragment to allow an interaction in this fragment to be communicated
//     * to the activity and potentially other fragments contained in that
//     * activity.
//     * <p>
//     * See the Android Training lesson <a href=
//     * "http://developer.android.com/training/basics/fragments/communicating.html"
//     * >Communicating with Other Fragments</a> for more information.
//     */
//    public interface OnFragmentInteractionListener {
//        // TODO: Update argument type and name
//        void onFragmentInteraction(Uri uri);
//    }
}
