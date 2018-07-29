package com.teachableapps.bakingapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.teachableapps.bakingapp.models.Step;

import java.util.List;

public class StepDetailsFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = StepDetailsFragment.class.getSimpleName();

    private Step mStep;
    private TextView tv_description;
    private ImageButton nav_prev;
    private ImageButton nav_next;

    private ImageView iv_exo;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;

    // Define a new interface that triggers a callback in the host activity
    StepDetailsFragment.OnNavClickListener mCallback;

    // OnNavClickListener interface, calls a method in the host activity named onNavigate
    public interface OnNavClickListener {
        void onNavigate(int position);
    }

    // Override onAttach to make sure that the container activity has implemented the callback
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // This makes sure that the host activity has implemented the callback interface
        // If not, it throws an exception
        try {
            mCallback = (StepDetailsFragment.OnNavClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString()
                    + " must implement OnNavClickListener");
        }
    }

    // Mandatory empty constructor
    public StepDetailsFragment() {
    }

    @SuppressLint("ValidFragment")
    public StepDetailsFragment(Step step) {
        mStep = step;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootView = inflater.inflate(R.layout.fragment_stepdetail, container, false);

        iv_exo = (ImageView) rootView.findViewById(R.id.iv_exoplayer);
        mPlayerView = (SimpleExoPlayerView) rootView.findViewById(R.id.v_exoplayer);
        tv_description = rootView.findViewById(R.id.tv_step_description);

        if(mStep.getVideoURL().length()>0){
            Log.d(TAG,mStep.getVideoURL());
            iv_exo.setVisibility(View.GONE);
            // Initialize the player.
            initializePlayer(Uri.parse(mStep.getVideoURL()));
        }

        if(rootView.findViewById(R.id.nav_next)!=null) {
            nav_prev = rootView.findViewById(R.id.nav_prev);
            nav_next = rootView.findViewById(R.id.nav_next);

            nav_prev.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Trigger the callback method and pass in the previous id
                    mCallback.onNavigate(mStep.getId() - 1);
                    Log.d(TAG, "<<<<<<< Prev " + String.valueOf(mStep.getId() - 1));
                }
            });
            nav_next.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View arg0) {
                    // Trigger the callback method and pass in the next id
                    mCallback.onNavigate(mStep.getId() + 1);
                    Log.d(TAG, "NExt >>>>>>>> " + String.valueOf(mStep.getId() + 1));
                }
            });
        }

        if(mStep != null) {
            tv_description.setText(mStep.getDescription());
        }
        return rootView;

    }

    /**
     * Initialize ExoPlayer.
     * @param mediaUri The URI of the sample to play.
     */
    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);
            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }


    /**
     * Release ExoPlayer.
     */
    private void releasePlayer() {
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    /**
     * Release the player when the fragment is stopped.
     */
    @Override
    public void onStop() {
        super.onStop();
        if(mExoPlayer!=null) {
            releasePlayer();
        }
    }

    @Override
    public void onClick(View view) {}
}
