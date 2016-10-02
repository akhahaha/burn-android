package com.ucla.burn.android;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.FacebookSdk;
import com.facebook.Profile;
import com.facebook.login.LoginManager;
import com.ucla.burn.android.adapter.ConversationListAdapter;
import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;


public class ProfileFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private static final String EXTRA_TITLE = "EXTRA_TITLE";

    private RoundedImageView mProfilePicture;
    private TextView mProfileName;
//    private OnFragmentInteractionListener mListener;

    public ProfileFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ProfileFragment newInstance(int position) {
        ProfileFragment fragment = new ProfileFragment();
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
        FacebookSdk.sdkInitialize(getActivity().getApplicationContext());
        View rootView = inflater.inflate(R.layout.fragment_profile, container, false);
        mProfilePicture = (RoundedImageView) rootView.findViewById(R.id.user_profile_picture_image_view);
        mProfileName = (TextView) rootView.findViewById(R.id.user_name_text_view);
        if(Profile.getCurrentProfile()!=null) {
            new ProfilePicFetcherTask().execute(Profile.getCurrentProfile().getProfilePictureUri(300, 300).toString());
            mProfileName.setText(Profile.getCurrentProfile().getName());
        }
        return rootView;
    }

    private class ProfilePicFetcherTask extends AsyncTask<String,Void,Bitmap> {
        @Override
        protected Bitmap doInBackground(String... params) {
            HttpURLConnection urlConnection;
            try {
                urlConnection = (HttpURLConnection) new URL(params[0]).openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                Bitmap b = BitmapFactory.decodeStream(inputStream);
                urlConnection.disconnect();
                return b;
            }
            catch (MalformedURLException m){

            }
            catch (IOException i){

            }
            return null;
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            mProfilePicture.setImageBitmap(bitmap);
        }
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
