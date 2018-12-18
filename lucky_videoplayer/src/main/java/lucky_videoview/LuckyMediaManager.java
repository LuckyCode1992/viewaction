package lucky_videoview;

import android.graphics.SurfaceTexture;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.Surface;
import android.view.TextureView;


public class LuckyMediaManager implements TextureView.SurfaceTextureListener {

    public static final String TAG = "JZVD";
    public static final int HANDLER_PREPARE = 0;
    public static final int HANDLER_RELEASE = 2;

    public static LuckyTextureView textureView;
    public static SurfaceTexture savedSurfaceTexture;
    public static Surface surface;
    public static LuckyMediaManager luckyMediaManager;
    public int positionInList = -1;
    public LuckyMediaInterface luckyMediaInterface;
    public int currentVideoWidth = 0;
    public int currentVideoHeight = 0;

    public HandlerThread mMediaHandlerThread;
    public MediaHandler mMediaHandler;
    public Handler mainThreadHandler;

    public LuckyMediaManager() {
        mMediaHandlerThread = new HandlerThread(TAG);
        mMediaHandlerThread.start();
        mMediaHandler = new MediaHandler(mMediaHandlerThread.getLooper());
        mainThreadHandler = new Handler();
        if (luckyMediaInterface == null)
            luckyMediaInterface = new LuckyMediaSystem();
    }

    public static LuckyMediaManager instance() {
        if (luckyMediaManager == null) {
            luckyMediaManager = new LuckyMediaManager();
        }
        return luckyMediaManager;
    }

    //这几个方法是不是多余了，为了不让其他地方动MediaInterface的方法
    public static void setDataSource(LuckyDataSource luckyDataSource) {
        instance().luckyMediaInterface.luckyDataSource = luckyDataSource;
    }

    public static LuckyDataSource getDataSource() {
        return instance().luckyMediaInterface.luckyDataSource;
    }


    //    //正在播放的url或者uri
    public static Object getCurrentUrl() {
        return instance().luckyMediaInterface.luckyDataSource == null ? null : instance().luckyMediaInterface.luckyDataSource.getCurrentUrl();
    }
//
//    public static void setCurrentDataSource(LuckyDataSource luckyDataSource) {
//        instance().luckyMediaInterface.luckyDataSource = luckyDataSource;
//    }

    public static long getCurrentPosition() {
        return instance().luckyMediaInterface.getCurrentPosition();
    }

    public static long getDuration() {
        return instance().luckyMediaInterface.getDuration();
    }

    public static void seekTo(long time) {
        instance().luckyMediaInterface.seekTo(time);
    }

    public static void pause() {
        instance().luckyMediaInterface.pause();
    }

    public static void start() {
        instance().luckyMediaInterface.start();
    }

    public static boolean isPlaying() {
        return instance().luckyMediaInterface.isPlaying();
    }

    public static void setSpeed(float speed) {
        instance().luckyMediaInterface.setSpeed(speed);
    }

    public void releaseMediaPlayer() {
        mMediaHandler.removeCallbacksAndMessages(null);
        Message msg = new Message();
        msg.what = HANDLER_RELEASE;
        mMediaHandler.sendMessage(msg);
    }

    public void prepare() {
        releaseMediaPlayer();
        Message msg = new Message();
        msg.what = HANDLER_PREPARE;
        mMediaHandler.sendMessage(msg);
    }

    @Override
    public void onSurfaceTextureAvailable(SurfaceTexture surfaceTexture, int i, int i1) {
        if (LuckyManger.getCurrentJzvd() == null) return;
        Log.i(TAG, "onSurfaceTextureAvailable [" + LuckyManger.getCurrentJzvd().hashCode() + "] ");
        if (savedSurfaceTexture == null) {
            savedSurfaceTexture = surfaceTexture;
            prepare();
        } else {
            textureView.setSurfaceTexture(savedSurfaceTexture);
        }
    }

    @Override
    public void onSurfaceTextureSizeChanged(SurfaceTexture surfaceTexture, int i, int i1) {

    }

    @Override
    public boolean onSurfaceTextureDestroyed(SurfaceTexture surfaceTexture) {
        return savedSurfaceTexture == null;
    }

    @Override
    public void onSurfaceTextureUpdated(SurfaceTexture surfaceTexture) {

    }


    public class MediaHandler extends Handler {
        public MediaHandler(Looper looper) {
            super(looper);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case HANDLER_PREPARE:
                    currentVideoWidth = 0;
                    currentVideoHeight = 0;
                    luckyMediaInterface.prepare();

                    if (savedSurfaceTexture != null) {
                        if (surface != null) {
                            surface.release();
                        }
                        surface = new Surface(savedSurfaceTexture);
                        luckyMediaInterface.setSurface(surface);
                    }
                    break;
                case HANDLER_RELEASE:
                    luckyMediaInterface.release();
                    break;
            }
        }
    }
}
