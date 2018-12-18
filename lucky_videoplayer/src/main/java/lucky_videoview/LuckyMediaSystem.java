package lucky_videoview;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.PlaybackParams;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.view.Surface;

import java.lang.reflect.Method;
import java.util.Map;


public class LuckyMediaSystem extends LuckyMediaInterface implements MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnBufferingUpdateListener, MediaPlayer.OnSeekCompleteListener, MediaPlayer.OnErrorListener, MediaPlayer.OnInfoListener, MediaPlayer.OnVideoSizeChangedListener {

    public MediaPlayer mediaPlayer;

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public void prepare() {
        try {
            mediaPlayer = new MediaPlayer();
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(luckyDataSource.looping);
            mediaPlayer.setOnPreparedListener(LuckyMediaSystem.this);
            mediaPlayer.setOnCompletionListener(LuckyMediaSystem.this);
            mediaPlayer.setOnBufferingUpdateListener(LuckyMediaSystem.this);
            mediaPlayer.setScreenOnWhilePlaying(true);
            mediaPlayer.setOnSeekCompleteListener(LuckyMediaSystem.this);
            mediaPlayer.setOnErrorListener(LuckyMediaSystem.this);
            mediaPlayer.setOnInfoListener(LuckyMediaSystem.this);
            mediaPlayer.setOnVideoSizeChangedListener(LuckyMediaSystem.this);
            Class<MediaPlayer> clazz = MediaPlayer.class;
            Method method = clazz.getDeclaredMethod("setDataSource", String.class, Map.class);
//            if (dataSourceObjects.length > 2) {
            method.invoke(mediaPlayer, luckyDataSource.getCurrentUrl().toString(), luckyDataSource.headerMap);
//            } else {
//                method.invoke(mediaPlayer, currentDataSource.toString(), null);
//            }
            mediaPlayer.prepareAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void pause() {
        mediaPlayer.pause();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void seekTo(long time) {
        try {
            mediaPlayer.seekTo((int) time);
        } catch (IllegalStateException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void release() {
        if (mediaPlayer != null)
            mediaPlayer.release();
    }

    @Override
    public long getCurrentPosition() {
        if (mediaPlayer != null) {
            return mediaPlayer.getCurrentPosition();
        } else {
            return 0;
        }
    }

    @Override
    public long getDuration() {
        if (mediaPlayer != null) {
            return mediaPlayer.getDuration();
        } else {
            return 0;
        }
    }

    @Override
    public void setSurface(Surface surface) {
        mediaPlayer.setSurface(surface);
    }

    @Override
    public void setVolume(float leftVolume, float rightVolume) {
        mediaPlayer.setVolume(leftVolume, rightVolume);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void setSpeed(float speed) {
        PlaybackParams pp = mediaPlayer.getPlaybackParams();
        pp.setSpeed(speed);
        mediaPlayer.setPlaybackParams(pp);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        mediaPlayer.start();
        if (luckyDataSource.getCurrentUrl().toString().toLowerCase().contains("mp3") ||
                luckyDataSource.getCurrentUrl().toString().toLowerCase().contains("wav")) {
            LuckyMediaManager.instance().mainThreadHandler.post(() -> {
                if (LuckyManger.getCurrentJzvd() != null) {
                    LuckyManger.getCurrentJzvd().onPrepared();
                }
            });
        }
    }

    @Override
    public void onCompletion(MediaPlayer mediaPlayer) {
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                LuckyManger.getCurrentJzvd().onAutoCompletion();
            }
        });
    }

    @Override
    public void onBufferingUpdate(MediaPlayer mediaPlayer, final int percent) {
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                LuckyManger.getCurrentJzvd().setBufferProgress(percent);
            }
        });
    }

    @Override
    public void onSeekComplete(MediaPlayer mediaPlayer) {
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                LuckyManger.getCurrentJzvd().onSeekComplete();
            }
        });
    }

    @Override
    public boolean onError(MediaPlayer mediaPlayer, final int what, final int extra) {
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                LuckyManger.getCurrentJzvd().onError(what, extra);
            }
        });
        return true;
    }

    @Override
    public boolean onInfo(MediaPlayer mediaPlayer, final int what, final int extra) {
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    if (LuckyManger.getCurrentJzvd().currentState == LuckyFrameLayout.CURRENT_STATE_PREPARING
                            || LuckyManger.getCurrentJzvd().currentState == LuckyFrameLayout.CURRENT_STATE_PREPARING_CHANGING_URL) {
                        LuckyManger.getCurrentJzvd().onPrepared();
                    }
                } else {
                    LuckyManger.getCurrentJzvd().onInfo(what, extra);
                }
            }
        });
        return false;
    }

    @Override
    public void onVideoSizeChanged(MediaPlayer mediaPlayer, int width, int height) {
        LuckyMediaManager.instance().currentVideoWidth = width;
        LuckyMediaManager.instance().currentVideoHeight = height;
        LuckyMediaManager.instance().mainThreadHandler.post(() -> {
            if (LuckyManger.getCurrentJzvd() != null) {
                LuckyManger.getCurrentJzvd().onVideoSizeChanged();
            }
        });
    }
}
