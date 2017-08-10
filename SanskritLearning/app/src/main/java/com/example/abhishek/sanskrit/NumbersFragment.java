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
public class NumbersFragment extends Fragment {


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

    public NumbersFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.word_list,container,false);

        audioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);

        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.number_one, R.string.sanskrit_number_one,
                R.drawable.number_one, R.raw.number_one));
        words.add(new Word(R.string.number_two, R.string.sanskrit_number_two,
                R.drawable.number_two, R.raw.number_two));
        words.add(new Word(R.string.number_three, R.string.sanskrit_number_three,
                R.drawable.number_three, R.raw.number_three));
        words.add(new Word(R.string.number_four, R.string.sanskrit_number_four,
                R.drawable.number_four, R.raw.number_four));
        words.add(new Word(R.string.number_five, R.string.sanskrit_number_five,
                R.drawable.number_five, R.raw.number_five));
        words.add(new Word(R.string.number_six, R.string.sanskrit_number_six,
                R.drawable.number_six, R.raw.number_six));
        words.add(new Word(R.string.number_seven, R.string.sanskrit_number_seven,
                R.drawable.number_seven, R.raw.number_seven));
        words.add(new Word(R.string.number_eight, R.string.sanskrit_number_eight,
                R.drawable.number_eight, R.raw.number_eight));
        words.add(new Word(R.string.number_nine, R.string.sanskrit_number_nine,
                R.drawable.number_nine, R.raw.number_nine));
        words.add(new Word(R.string.number_ten, R.string.sanskrit_number_ten,
                R.drawable.number_ten, R.raw.number_ten));

        WordAdapter adapter=new WordAdapter(getActivity(),words,R.color.category_numbers);

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

        return rootView;

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
