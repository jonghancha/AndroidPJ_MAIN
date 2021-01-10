package com.android.androidpj_main.Make_Youtube;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.android.androidpj_main.R;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

// 21.01.08 지은 추가 ****************************************************
public class YoutubeViewActivity extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {

    //사용자가 얻은 API Key을 입력하면 된다.(개발자 콘솔에 얻은 것.)
    public static final String API_KEY = "AIzaSyCZV9d_FQ4hL5Cdt2SnZcdizR6FhKhIJOk";

    //http://youtu.be/<VIDEO_ID>

    private final String TAG = "MainViewActivity";

    String VIDEO_ID;
    private static final int RQS_ErrorDialog = 1;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        /** attaching layout xml **/
        setContentView(R.layout.activity_youtubeview);

        Intent intent = getIntent();
        String ytUrl = intent.getStringExtra("ytUrl");
        Log.v(TAG, ytUrl);
        VIDEO_ID = ytUrl;
        TextView youtubeUrl = findViewById(R.id.youtubeUrl);
        Log.v(TAG, String.valueOf(findViewById(R.id.youtubeUrl)));
        youtubeUrl.setText("출처 : https://www.youtube.com/watch?v="+ytUrl);

        String ytContent = intent.getStringExtra("ytContent");
        TextView ytCon = findViewById(R.id.ytContent);
        ytCon.setText(ytContent);


        /** Initializing YouTube player view **/
        YouTubePlayerView youTubePlayerView = findViewById(R.id.youtube_player);
        youTubePlayerView.initialize(API_KEY, this);


    }
    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult result) {
        if (result.isUserRecoverableError()) {
            result.getErrorDialog(this, RQS_ErrorDialog).show();
        } else {
            Toast.makeText(this,
                    "YouTubePlayer.onInitializationFailure(): " + result.toString(),
                    Toast.LENGTH_LONG).show();
        }
    }
    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {
        /** add listeners to YouTubePlayer instance **/
        player.setPlayerStateChangeListener(playerStateChangeListener);
        player.setPlaybackEventListener(playbackEventListener);
        /** Start buffering **/
        if (!wasRestored) {
            player.cueVideo(VIDEO_ID);
        }
    }
    private YouTubePlayer.PlaybackEventListener playbackEventListener = new YouTubePlayer.PlaybackEventListener() {
        @Override
        public void onBuffering(boolean arg0) {
        }
        @Override
        public void onPaused() {
        }
        @Override
        public void onPlaying() {
        }
        @Override
        public void onSeekTo(int arg0) {
        }
        @Override
        public void onStopped() {
        }
    };
    private YouTubePlayer.PlayerStateChangeListener playerStateChangeListener = new YouTubePlayer.PlayerStateChangeListener() {
        @Override
        public void onAdStarted() {
        }
        @Override
        public void onError(YouTubePlayer.ErrorReason arg0) {
        }
        @Override
        public void onLoaded(String arg0) {
        }
        @Override
        public void onLoading() {
        }
        @Override
        public void onVideoEnded() {
        }
        @Override
        public void onVideoStarted() {
        }
    };
}
