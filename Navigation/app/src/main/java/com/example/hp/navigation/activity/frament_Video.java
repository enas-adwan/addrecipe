package com.example.hp.navigation.activity;

import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.*;
import android.widget.MediaController;
import android.widget.Toast;

import com.navigation.drawer.activity.R;

import static android.content.Context.MODE_PRIVATE;


public class frament_Video extends Fragment {
    private static final String TAG = "VideoPlayer";
    FrameLayout videoLayout;
    public TextView noVideo;
    String myvideo;
    private VideoView videoView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_vedio, container, false);
        noVideo=(TextView) root.findViewById(R.id.noVideoText);
        videoLayout=(FrameLayout) root.findViewById(R.id.videolayout);
        videoView = (VideoView) root.findViewById(R.id.myVideo);

        return root;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences pref = getActivity().getSharedPreferences("MyPref", MODE_PRIVATE);
        myvideo=pref.getString("myvideo","defult");
        videoView.setMediaController(new MediaController(getActivity()));
        playVideo();
    }

    public void playVideo() {
        myvideo=myvideo.replaceAll("\\s","");
        if(myvideo.equals("novideo")){
            videoLayout.setVisibility(LinearLayout.GONE);
            noVideo.setText("There is no video for the Recipe");
        }
        else {

            String vidAddress = "http://10.0.2.2/upload/" + myvideo;
            Uri vidUri = Uri.parse(vidAddress);
            // Uri uri = Uri.parse("android.resource://" + getActivity().getPackageName() + "/" + R.raw.test_vid);
            Log.d(TAG, "Uri is: " + vidUri);
            setVideoLocation(vidUri);
            if (!videoView.isPlaying()) {
                videoView.start();
            }

        }

    }

    private void setVideoLocation(Uri uri) {
        try {
            videoView.setVideoURI(uri);
        } catch (Exception e) {
            Log.e(TAG, "VideoPlayer uri was invalid", e);
            Toast.makeText(getActivity(), "Not found", Toast.LENGTH_SHORT).show();
        }
    }

    public void pauseVideo() {
        videoView.pause();
    }

}