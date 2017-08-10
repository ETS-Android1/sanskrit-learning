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
public class FamilyFragment extends Fragment {

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
            releaseMediaPlayer();        }
    };

    public FamilyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView=inflater.inflate(R.layout.word_list,container,false);

        audioManager= (AudioManager) getActivity().getSystemService(Context.AUDIO_SERVICE);
        // Create a list of words
        final ArrayList<Word> words = new ArrayList<Word>();
        words.add(new Word(R.string.family_father, R.string.sanskrit_family_father,
                R.drawable.family_father, R.raw.family_father));
        words.add(new Word(R.string.family_mother, R.string.sanskrit_family_mother,
                R.drawable.family_mother, R.raw.family_mother));
        words.add(new Word(R.string.family_son, R.string.sanskrit_family_son,
                R.drawable.family_son, R.raw.family_son));
        words.add(new Word(R.string.family_daughter, R.string.sanskrit_family_daughter,
                R.drawable.family_daughter, R.raw.family_daughter));
        words.add(new Word(R.string.family_older_brother, R.string.sanskrit_family_older_brother,
                R.drawable.family_older_brother, R.raw.family_older_brother));
        words.add(new Word(R.string.family_younger_brother, R.string.sanskrit_family_younger_brother,
                R.drawable.family_younger_brother, R.raw.family_younger_brother));
        words.add(new Word(R.string.family_older_sister, R.string.sanskrit_family_older_sister,
                R.drawable.family_older_sister, R.raw.family_older_sister));
        words.add(new Word(R.string.family_younger_sister, R.string.sanskrit_family_younger_sister,
                R.drawable.family_younger_sister, R.raw.family_younger_sister));
        words.add(new Word(R.string.family_grandmother, R.string.sanskrit_family_grandmother,
                R.drawable.family_grandmother, R.raw.family_grandmother));
        words.add(new Word(R.string.family_grandfather, R.string.sanskrit_family_grandfather,
                R.drawable.family_grandfather, R.raw.family_grandfather));

        WordAdapter adapter=new WordAdapter(getActivity(),words,R.color.category_family);

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
