package com.example.abhishek.sanskrit;

/**
 * Created by Abhishek on 02-Aug-17.
 */

public class Word {

    private int miwokTranslationResourceId;
    private int defaultTranslationResourceId;
    private int imgResourceId=NO_IMAGE_PROVIDED;
    private int audioResourceId;

    private static final int NO_IMAGE_PROVIDED=-1;

    public Word(int defaultTranslationResourceId,int miwokTranslationResourceId,int audioResourceId){
        this.defaultTranslationResourceId=defaultTranslationResourceId;
        this.miwokTranslationResourceId=miwokTranslationResourceId;
        this.audioResourceId=audioResourceId;
    }

    public Word(int defaultTranslationResourceId,int miwokTranslationResourceId,int imgResourceId,int audioResourceId){
        this.defaultTranslationResourceId=defaultTranslationResourceId;
        this.miwokTranslationResourceId=miwokTranslationResourceId;
        this.audioResourceId=audioResourceId;
        this.imgResourceId=imgResourceId;
    }

    public int getMiwokTranslationResourceId() {
        return miwokTranslationResourceId;
    }

    public int getDefaultTranslationResourceId() {
        return defaultTranslationResourceId;
    }

    public int getImgResourceId() {
        return imgResourceId;
    }

    public int getAudioResourceId() {
        return audioResourceId;
    }

    public boolean hasImage(){
        return imgResourceId!=NO_IMAGE_PROVIDED;
    }

}
