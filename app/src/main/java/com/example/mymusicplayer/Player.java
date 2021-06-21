package com.example.mymusicplayer;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public class Player extends AppCompatActivity {
    Button pause , fskip , pskip , ffskip , ppskip;
    SeekBar seekBar;
    TextView textView , time , time2;
    static MediaPlayer mediaPlayer;
    int position;
    // some changes
    ArrayList<File> mysongs;
    Thread updateseekbar , updatseekbar;
    String sname;
    Boolean b=true;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        getSupportActionBar().setTitle("Now Playing");
        textView = (TextView)findViewById(R.id.textview3);
        pause = (Button) findViewById(R.id.button);
        fskip = (Button) findViewById(R.id.button2);
        pskip = (Button) findViewById(R.id.button3);
        seekBar = (SeekBar) findViewById(R.id.seekbar);
        ffskip = (Button)findViewById(R.id.button4);
        ppskip = (Button)findViewById(R.id.button5);
        time2 = (TextView) findViewById(R.id.textview5);
        time = (TextView)findViewById(R.id.textview4);
        updateseekbar = new Thread(){
            @Override
            public void run() {
                int totalduration = mediaPlayer.getDuration();
                int currentposition = 0;
                while (currentposition<totalduration){
                    try {
                        sleep(500);
                        currentposition = mediaPlayer.getCurrentPosition();
                        seekBar.setProgress(currentposition);
                    }catch (InterruptedException e){
                        e.printStackTrace();
                    }
                }
            }
        };

        if(mediaPlayer!=null){
            mediaPlayer.stop();
            mediaPlayer.release();
        }
        Intent i = getIntent();
        Bundle bundle = i.getExtras();
        mysongs = (ArrayList)bundle.getParcelableArrayList("songs");
        sname = mysongs.get(position).getName().toString();
        String songname = i.getStringExtra("songname");
        textView.setText(songname);
        textView.setSelected(true);
        for(int o=0;o<mysongs.size();o++){
            String r = mysongs.get(o).getName().toString();
            if(songname.equals(r)){
                position = o;
                break;
            }
        }
        Uri u = Uri.parse(mysongs.get(position).toString());
        mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
        mediaPlayer.start();
        int totalduration = mediaPlayer.getDuration();
        String h = convert(totalduration);
        time2.setText(h);
        time2.setSelected(true);
        seekBar.setMax(mediaPlayer.getDuration());
        updateseekbar.start();
        seekBar.getProgressDrawable().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.MULTIPLY);
        seekBar.getThumb().setColorFilter(getResources().getColor(R.color.teal_200), PorterDuff.Mode.SRC_IN);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                time.setText(convert(mediaPlayer.getCurrentPosition()));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(seekBar.getProgress());
                time.setText(convert(mediaPlayer.getCurrentPosition()));
            }
        });
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                mediaPlayer.seekTo(0);

            }
        });
        pause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seekBar.setMax(mediaPlayer.getDuration());
                if(mediaPlayer.isPlaying()){
                    pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                    mediaPlayer.pause();
                }
                else{
                    pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                    mediaPlayer.start();
                }
            }
        });
        fskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mediaPlayer.stop();
              mediaPlayer.release();
                pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                updatseekbar = new Thread(){
                    @Override
                    public void run() {
                        int totalduration = mediaPlayer.getDuration();
                        int currentposition = 0;
                        while (currentposition<totalduration){
                            try {
                                sleep(500);
                                currentposition = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(currentposition);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                };
                 position = ((position+1)%mysongs.size());
                 Uri u = Uri.parse(mysongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                sname = mysongs.get(position).getName().toString();
                textView.setText(sname);
                textView.setSelected(true);
                int totalduration = mediaPlayer.getDuration();
                String h = convert(totalduration);
                time2.setText(h);
                time2.setSelected(true);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                updatseekbar.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                        mediaPlayer.seekTo(0);

                    }
                });
            }
        });
        pskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mediaPlayer.stop();
                 mediaPlayer.release();
                pause.setBackgroundResource(R.drawable.ic_baseline_pause_24);
                updatseekbar = new Thread(){
                    @Override
                    public void run() {
                        int totalduration = mediaPlayer.getDuration();
                        int currentposition = 0;
                        while (currentposition<totalduration){
                            try {
                                sleep(500);
                                currentposition = mediaPlayer.getCurrentPosition();
                                seekBar.setProgress(currentposition);
                            }catch (InterruptedException e){
                                e.printStackTrace();
                            }
                        }
                    }
                };
                if(position-1<0){
                    position = mysongs.size();
                }
                position = ((position-1)%mysongs.size());
                Uri u = Uri.parse(mysongs.get(position).toString());
                mediaPlayer = MediaPlayer.create(getApplicationContext(),u);
                sname = mysongs.get(position).getName().toString();
                textView.setText(sname);
                textView.setSelected(true);
                int totalduration = mediaPlayer.getDuration();
                String h = convert(totalduration);
                time2.setText(h);
                time2.setSelected(true);
                mediaPlayer.start();
                seekBar.setMax(mediaPlayer.getDuration());
                updatseekbar.start();
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        pause.setBackgroundResource(R.drawable.ic_baseline_play_arrow_24);
                        mediaPlayer.seekTo(0);

                    }
                });
            }
        });
        ffskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int cp = mediaPlayer.getCurrentPosition();
                    int d = mediaPlayer.getDuration();
                    if (mediaPlayer.isPlaying() && d > cp + 5000) {
                        cp = cp + 5000;
                        time.setText(convert(cp));
                        mediaPlayer.seekTo(cp);
                    }
                }
        });
        ppskip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    int cp = mediaPlayer.getCurrentPosition();
                    if (mediaPlayer.isPlaying() && cp > 5000) {
                        cp = cp - 5000;
                        time.setText(convert(cp));
                        mediaPlayer.seekTo(cp);
                    }
            }
        });
    }
    private String convert(int duration){
        return String.format("%02d:%02d" ,(duration/1000)/60 , (duration/1000)%60);
    }
}