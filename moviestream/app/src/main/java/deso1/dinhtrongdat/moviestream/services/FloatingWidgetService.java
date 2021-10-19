package deso1.dinhtrongdat.moviestream.services;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.net.Uri;
import android.os.Build;
import android.os.IBinder;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import deso1.dinhtrongdat.moviestream.R;
import deso1.dinhtrongdat.moviestream.VideoPlayer;

public class FloatingWidgetService extends Service {

    WindowManager windowManager;
    private View mFloatingManager;
    Uri videoUri;
    SimpleExoPlayer exoPlayer;
    PlayerView playerView;

    public FloatingWidgetService() {
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        if(intent != null){
            String uriStr = intent.getStringExtra("videoUri");
            videoUri = Uri.parse(uriStr);
            if(windowManager != null && mFloatingManager.isShown() && exoPlayer != null){
                windowManager.removeView(mFloatingManager);
                mFloatingManager = null;
                windowManager = null;
                exoPlayer.setPlayWhenReady(false);
                exoPlayer.release();
                exoPlayer = null;
            }
            final WindowManager.LayoutParams params;
            mFloatingManager = LayoutInflater.from(this).inflate(R.layout.custom_popup_window, null);
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                        PixelFormat.TRANSLUCENT
                );
            }else{
                params = new WindowManager.LayoutParams(
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT,
                        WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                        WindowManager.LayoutParams.TYPE_SYSTEM_ALERT,
                        PixelFormat.TRANSLUCENT
                );
            }
            params.gravity = Gravity.TOP | Gravity.LEFT;
            params.x = 200;
            params.y = 200;
            windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            windowManager.addView(mFloatingManager, params);
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection trackSelection= (TrackSelection) new DefaultTrackSelector(new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(this, (TrackSelector) trackSelection);
            playerView = mFloatingManager.findViewById(R.id.popupPlayerView);
            ImageView imgDissmis = mFloatingManager.findViewById(R.id.imgDissmis);
            ImageView imgMaximize = mFloatingManager.findViewById(R.id.imgMaximine);
            imgMaximize.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(windowManager != null && mFloatingManager.isShown() && exoPlayer != null){
                        windowManager.removeView(mFloatingManager);
                        mFloatingManager = null;
                        windowManager = null;
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.release();
                        exoPlayer = null;
                        stopSelf();

                        Intent openAcivityIntent = new Intent(FloatingWidgetService.this, VideoPlayer.class);
                        openAcivityIntent.putExtra("videoUri", videoUri.toString());
                        openAcivityIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(openAcivityIntent);
                    }
                }
            });

            imgDissmis.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(windowManager != null && mFloatingManager.isShown() && exoPlayer != null){
                        windowManager.removeView(mFloatingManager);
                        mFloatingManager = null;
                        windowManager = null;
                        exoPlayer.setPlayWhenReady(false);
                        exoPlayer.release();
                        exoPlayer = null;
                        stopSelf();
                    }
                }
            });

            playVideo();
            mFloatingManager.findViewById(R.id.layout_customPopup).setOnTouchListener(new View.OnTouchListener() {

                private int initialX, initialY;
                private float initialTouchX, initialTouchY;

                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    switch (motionEvent.getAction()){
                        case MotionEvent.ACTION_DOWN:
                            initialX = params.x;
                            initialY = params.y;
                            initialTouchX = motionEvent.getRawX();
                            initialTouchY = motionEvent.getRawY();
                            return true;

                        case MotionEvent.ACTION_UP:
                            return true;
                        case MotionEvent.ACTION_MOVE:
                            params.x = initialX + (int)(motionEvent.getRawX() - initialTouchX);
                            params.y = initialY + (int)(motionEvent.getRawY()-initialTouchY);
                            windowManager.updateViewLayout(mFloatingManager, params);
                            return true;
                    }
                    return false;
                }
            });
        }
        return super.onStartCommand(intent, flags, startId);
    }

    public void playVideo(){
        try{
            BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection trackSelection = (TrackSelection) new DefaultTrackSelector(
                    new AdaptiveTrackSelection.Factory(bandwidthMeter));
            exoPlayer = ExoPlayerFactory.newSimpleInstance(FloatingWidgetService.this, (TrackSelector) trackSelection);
            String palyerInfo = Util.getUserAgent(this, "VideoPlayer");
            DefaultDataSourceFactory dataSourceFactory = new DefaultDataSourceFactory(this, palyerInfo);
            ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
            MediaSource mediaSource = new ExtractorMediaSource(videoUri, dataSourceFactory, extractorsFactory, null, null);
            playerView.setPlayer(exoPlayer);
            exoPlayer.prepare(mediaSource);
            exoPlayer.setPlayWhenReady(true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if(mFloatingManager != null)
            windowManager.removeView(mFloatingManager);
    }
}
