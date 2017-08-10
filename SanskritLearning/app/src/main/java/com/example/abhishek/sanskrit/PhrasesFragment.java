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
public class PhrasesFragment extends Fragment {

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
    public PhrasesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.word_list,container,false);

        audioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();

        words.add(new Word(R.string.phrase_hello,R.string.sanskrit_phrase_hello,R.raw.phrase_hello));
        words.add(new Word(R.string.phrase_how_are_you,R.string.sanskrit_phrase_how_are_you,R.raw.phrase_how_are_you));
        words.add(new Word(R.string.phrase_good_morning,R.string.sanskrit_phrase_good_morning,R.raw.phrase_good_morning));
        words.add(new Word(R.string.phrase_good_afternoon,R.string.sanskrit_phrase_good_afternoon,R.raw.phrase_good_afternoon));
        words.add(new Word(R.string.phrase_good_evening,R.string.sanskrit_phrase_good_evening,R.raw.phrase_good_evening));
        words.add(new Word(R.string.phrase_good_night,R.string.sanskrit_phrase_good_night,R.raw.phrase_good_night));
        words.add(new Word(R.string.phrase_happy_birthday,R.string.sanskrit_phrase_happy_birthday,R.raw.phrase_happy_birthday));
        words.add(new Word(R.string.phrase_happy_new_year,R.string.sanskrit_phrase_happy_new_year,R.raw.phrase_happy_new_year));
        words.add(new Word(R.string.phrase_good_luck,R.string.sanskrit_phrase_good_luck,R.raw.phrase_good_luck));
        words.add(new Word(R.string.phrase_see_you_soon,R.string.sanskrit_phrase_see_you_soon,R.raw.phrase_see_you_soon));
        words.add(new Word(R.string.phrase_i_love_you,R.string.sanskrit_phrase_i_love_you,R.raw.phrase_i_love_you));
        words.add(new Word(R.string.phrase_i_miss_you,R.string.sanskrit_phrase_i_miss_you,R.raw.phrase_i_miss_you));
        words.add(new Word(R.string.phrase_i_am_sorry,R.string.sanskrit_phrase_i_am_sorry,R.raw.phrase_i_am_sorry));
        words.add(new Word(R.string.phrase_yes,R.string.sanskrit_phrase_yes,R.raw.phrase_yes));
        words.add(new Word(R.string.phrase_no,R.string.sanskrit_phrase_no,R.raw.phrase_no));
        words.add(new Word(R.string.phrase_thank_you,R.string.sanskrit_phrase_thank_you,R.raw.phrase_thank_you));
        words.add(new Word(R.string.phrase_you_are_welcome,R.string.sanskrit_phrase_you_are_welcome,R.raw.phrase_you_are_welcome));
        words.add(new Word(R.string.phrase_have_good_day,R.string.sanskrit_phrase_have_good_day,R.raw.phrase_have_good_day));

        WordAdapter adapter=new WordAdapter(getActivity(),words,R.color.category_phrases);

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
