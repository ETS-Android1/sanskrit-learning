package com.example.abhishek.sanskrit;


import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class ColorsFragment extends Fragment {

    private MediaPlayer mediaPlayer;
    private AudioManager audioManager;

    private AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener=new AudioManager.OnAudioFocusChangeListener() {
        @Override
        public void onAudioFocusChange(int focusChange) {

            if(focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT || focusChange== AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK){
                mediaPlayer.pause();
                mediaPlayer.seekTo(0);
            }else if(focusChange==AudioManager.AUDIOFOCUS_GAIN){
                mediaPlayer.start();
            }else{
                releaseMediaPlayer();
            }

        }
    };


    private MediaPlayer.OnCompletionListener completionListener=new MediaPlayer.OnCompletionListener() {
        @Override
        public void onCompletion(MediaPlayer mp) {
            releaseMediaPlayer();
        }
    };
    public ColorsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView=inflater.inflate(R.layout.word_list,container,false);
        audioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(R.string.color_red, R.string.sanskrit_color_red,
                R.drawable.color_red, R.raw.color_red));
        words.add(new Word(R.string.color_yellow, R.string.sanskrit_color_yellow,
                R.drawable.color_yellow, R.raw.color_yellow));
        words.add(new Word(R.string.color_green, R.string.sanskrit_color_green,
                R.drawable.color_green, R.raw.color_green));
        words.add(new Word(R.string.color_brown, R.string.sanskrit_color_brown,
                R.drawable.color_brown, R.raw.color_brown));
        words.add(new Word(R.string.color_gray, R.string.sanskrit_color_gray,
                R.drawable.color_gray, R.raw.color_gray));
        words.add(new Word(R.string.color_black, R.string.sanskrit_color_black,
                R.drawable.color_black, R.raw.color_black));
        words.add(new Word(R.string.color_white, R.string.sanskrit_color_white,
                R.drawable.color_white, R.raw.color_white));

        WordAdapter adapter=new WordAdapter(getActivity(),words,R.color.category_colors);

        ListView listView= (ListView) rootView.findViewById(R.id.numbersView);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                releaseMediaPlayer();

                int status= audioManager.requestAudioFocus(onAudioFocusChangeListener,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                if(status==AudioManager.AUDIOFOCUS_REQUEST_GRANTED){

                    mediaPlayer=MediaPlayer.create(getActivity(),words.get(position).getAudioResourceId());
                    mediaPlayer.start();

                    mediaPlayer.setOnCompletionListener(completionListener);

                }

            }
        });


        return  rootView;
    }
    private  void releaseMediaPlayer(){

        if(mediaPlayer!=null){
            mediaPlayer.release();
            mediaPlayer=null;

            audioManager.abandonAudioFocus(onAudioFocusChangeListener);

        }

    }

    @Override
    public void onStop() {
        super.onStop();
        releaseMediaPlayer();
    }
}
