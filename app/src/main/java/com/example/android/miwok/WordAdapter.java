package com.example.android.miwok;

import android.app.Activity;
import android.media.MediaPlayer;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class WordAdapter extends ArrayAdapter<Word> {
    private int mBackgroundcolor;
    MediaPlayer mMediaPlayer;

    /**
     *
     * @param context get context so can access click in spesific view in list
     * @param words
     * @param backgroundColor add backgound to specify color each activity
     */
    public WordAdapter(Activity context, ArrayList<Word> words,int backgroundColor) {
        super(context, 0, words);
        mBackgroundcolor = backgroundColor;
    }

    /**
     * release media player
     */
    private void releseMedia(){
        if(mMediaPlayer != null){
            mMediaPlayer.release();
            mMediaPlayer = null;
        }
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;

        /**
         * set view to specify layout
         */
        if(listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_item, parent, false);
        }

        /**
         * get object value
         */
        final Word currentAndroidMiwok =(Word) getItem(position);

        /**
         * set click listener to list
         * release media, sound will stop when play new sound
         * create media player
         * start media player
         * set complete listener and show toast
         */
        ImageView play = (ImageView) listItemView.findViewById(R.id.img_play);
        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                releseMedia();
                mMediaPlayer = MediaPlayer.create(WordAdapter.super.getContext(),currentAndroidMiwok.getAudioResourceId());
                mMediaPlayer.start();
                mMediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        Toast.makeText(WordAdapter.super.getContext(), currentAndroidMiwok.getMiwokTranslation(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        /**
         * set background color to specific row list view
         */
        LinearLayout linearLayout = (LinearLayout) listItemView.findViewById(R.id.text_container);
        int color = ContextCompat.getColor(getContext(), mBackgroundcolor);
        linearLayout.setBackgroundColor(color);

        /**
         * set text miwok
         */
        TextView miwokTranslate = (TextView) listItemView.findViewById(R.id.miwok_name);
        miwokTranslate.setText(currentAndroidMiwok.getMiwokTranslation());

        /**
         * set text translation
         */
        TextView defaultName = (TextView) listItemView.findViewById(R.id.default_name);
        defaultName.setText(currentAndroidMiwok.getDefaultTranslation());

        /**
         * set image
         */
        ImageView imageView = (ImageView) listItemView.findViewById(R.id.item_icon);

        /**
         * control view to hide image when no image on input
         */
        if(currentAndroidMiwok.hasImage()){
            imageView.setImageResource(currentAndroidMiwok.getImageResourceId());
            imageView.setVisibility(View.VISIBLE);
        }else{
            imageView.setVisibility(View.GONE);
        }

        return listItemView;
    }
}
