package com.example.abhishek.sanskrit;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Abhishek on 02-Aug-17.
 */

public class WordAdapter extends ArrayAdapter<Word> {

    private int bgColorResourceId;

    public WordAdapter(Context context, ArrayList<Word> words,int bgColorResourceId){
        super(context,0,words);
        this.bgColorResourceId=bgColorResourceId;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView==null){
            convertView= LayoutInflater.from(getContext()).inflate(R.layout.list_item_view,parent,false);
        }

        Word word=getItem(position);

        TextView miwokTextView= (TextView) convertView.findViewById(R.id.miwokTextView);
        TextView defaultTextView= (TextView) convertView.findViewById(R.id.defaultTextView);
        ImageView imgView= (ImageView) convertView.findViewById(R.id.img);

        miwokTextView.setText(word.getMiwokTranslationResourceId());
        defaultTextView.setText(word.getDefaultTranslationResourceId());

        Typeface sanskritFont=Typeface.createFromAsset(getContext().getAssets(), "font/Sanskrit_Bold.TTF");
        miwokTextView.setTypeface(sanskritFont);

        if(word.hasImage()){
            imgView.setImageResource(word.getImgResourceId());
            imgView.setVisibility(View.VISIBLE);
        }else{
            imgView.setVisibility(View.GONE);
        }

        View textContainerView=convertView.findViewById(R.id.textContainerView);
        textContainerView.setBackgroundColor(ContextCompat.getColor(getContext(),bgColorResourceId));

        return convertView;
    }
}
