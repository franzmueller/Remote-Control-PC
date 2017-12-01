package me.varunon9.remotecontrolpc.mediacontrol;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import me.varunon9.remotecontrolpc.MainActivity;
import me.varunon9.remotecontrolpc.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class MediaControlFragment extends Fragment implements View.OnClickListener, View.OnTouchListener {

    private Button volDownButton, volUpButton, playButton, previousButton, nextButton, muteButton, spaceButton, stopButton;

    private int lastPressedButton;

    public MediaControlFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_mediacontrol, container, false);
        volDownButton = (Button) rootView.findViewById(R.id.volDownButton);
        volUpButton = (Button) rootView.findViewById(R.id.volUpButton);
        previousButton = (Button) rootView.findViewById(R.id.previousButton);
        nextButton = (Button) rootView.findViewById(R.id.nextButton);
        playButton = (Button) rootView.findViewById(R.id.playButton);
        spaceButton = (Button) rootView.findViewById(R.id.spaceButton);
        stopButton = (Button) rootView.findViewById(R.id.stopButton);
        muteButton = (Button) rootView.findViewById(R.id.muteButton);
        volDownButton.setOnTouchListener(this);
        previousButton.setOnClickListener(this);
        volUpButton.setOnTouchListener(this);
        nextButton.setOnClickListener(this);
        playButton.setOnClickListener(this);
        spaceButton.setOnClickListener(this);
        stopButton.setOnClickListener(this);
        muteButton.setOnClickListener(this);
        lastPressedButton = 0;
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getActivity().setTitle(getResources().getString(R.string.mediacontrol));
    }

    @Override
    public void onClick(View v) {
        String message = "";
        switch (v.getId()) {
            case R.id.previousButton:
                MainActivity.sendMessageToServer("PREVIOUS_KEY");
                break;
            case R.id.nextButton:
                MainActivity.sendMessageToServer("NEXT_KEY");
                break;
            case R.id.playButton:
                MainActivity.sendMessageToServer("PLAY_KEY");
                break;
            case R.id.muteButton:
                MainActivity.sendMessageToServer("MUTE_KEY");
                break;
            case R.id.stopButton:
                MainActivity.sendMessageToServer("STOP_KEY");
                break;
            case R.id.spaceButton:
                MainActivity.sendMessageToServer("TYPE_KEY");
                MainActivity.sendMessageToServer(32);
                break;
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        switch(event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                if(lastPressedButton!=0) return false;
                switch (v.getId()) {
                    case R.id.volDownButton:
                        MainActivity.sendMessageToServer("PRESS_VOL_DOWN");
                        lastPressedButton = 1;
                        return true;
                    case R.id.volUpButton:
                        MainActivity.sendMessageToServer("PRESS_VOL_UP");
                        lastPressedButton = 2;
                        return true;
                }
                break;
            case MotionEvent.ACTION_UP:
                if(lastPressedButton==1){
                    MainActivity.sendMessageToServer("RELEASE_VOL_DOWN");
                    lastPressedButton = 0;
                    return true;
                }
                else if(lastPressedButton==2){
                    MainActivity.sendMessageToServer("RELEASE_VOL_UP");
                    lastPressedButton = 0;
                    return true;
                }
                break;
        }
        return false;
    }
}
