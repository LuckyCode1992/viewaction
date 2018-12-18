package lucky_videoview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.AudioManager;
import android.provider.Settings;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Constructor;
import java.util.Timer;
import java.util.TimerTask;


public abstract class LuckyFrameLayout extends FrameLayout implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, View.OnTouchListener {

    public static final String TAG = "JZVD";
    public static final int THRESHOLD = 80;
    public static final int FULL_SCREEN_NORMAL_DELAY = 300;

    public static final int SCREEN_WINDOW_NORMAL = 0;
    public static final int SCREEN_WINDOW_LIST = 1;
    public static final int SCREEN_WINDOW_FULLSCREEN = 2;
    public static final int SCREEN_WINDOW_TINY = 3;

    public static final int CURRENT_STATE_IDLE = -1;
    public static final int CURRENT_STATE_NORMAL = 0;
    public static final int CURRENT_STATE_PREPARING = 1;
    public static final int CURRENT_STATE_PREPARING_CHANGING_URL = 2;
    public static final int CURRENT_STATE_PLAYING = 3;
    public static final int CURRENT_STATE_PAUSE = 5;
    public static final int CURRENT_STATE_AUTO_COMPLETE = 6;
    public static final int CURRENT_STATE_ERROR = 7;

    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ADAPTER = 0;//default
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_PARENT = 1;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_FILL_SCROP = 2;
    public static final int VIDEO_IMAGE_DISPLAY_TYPE_ORIGINAL = 3;
    public static boolean ACTION_BAR_EXIST = true;
    public static boolean TOOL_BAR_EXIST = true;
    public static int FULLSCREEN_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE;
    public static int NORMAL_ORIENTATION = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT;
    public static boolean SAVE_PROGRESS = true;
    public static boolean WIFI_TIP_DIALOG_SHOWED = false;
    public static int VIDEO_IMAGE_DISPLAY_TYPE = 0;
    public static long CLICK_QUIT_FULLSCREEN_TIME = 0;
    public static long lastAutoFullscreenTime = 0;
    public static AudioManager.OnAudioFocusChangeListener onAudioFocusChangeListener = new AudioManager.OnAudioFocusChangeListener() {//是否新建个class，代码更规矩，并且变量的位置也很尴尬
        @Override
        public void onAudioFocusChange(int focusChange) {
            switch (focusChange) {
                case AudioManager.AUDIOFOCUS_GAIN:
                    break;
                case AudioManager.AUDIOFOCUS_LOSS:
                    releaseAllVideos();
                    Log.d(TAG, "AUDIOFOCUS_LOSS [" + this.hashCode() + "]");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT:
                    try {
                        LuckyFrameLayout player = LuckyManger.getCurrentJzvd();
                        if (player != null && player.currentState == LuckyFrameLayout.CURRENT_STATE_PLAYING) {
                            player.startButton.performClick();
                        }
                    } catch (IllegalStateException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, "AUDIOFOCUS_LOSS_TRANSIENT [" + this.hashCode() + "]");
                    break;
                case AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK:
                    break;
            }
        }
    };
    protected static LuckyUserAction JZ_USER_EVENT;
    protected Timer UPDATE_PROGRESS_TIMER;
    public int currentState = -1;
    public int currentScreen = -1;
    public long seekToInAdvance = 0;
    public ImageView startButton;
    public SeekBar progressBar;
    public ImageView fullscreenButton;
    public TextView currentTimeTextView, totalTimeTextView;
    public ViewGroup textureViewContainer;
    public ViewGroup topContainer, bottomContainer;
    public int widthRatio = 0;
    public int heightRatio = 0;
    public LuckyDataSource luckyDataSource;
    public int positionInList = -1;
    public int videoRotation = 0;
    protected int mScreenWidth;
    protected int mScreenHeight;
    protected AudioManager mAudioManager;
    protected ProgressTimerTask mProgressTimerTask;
    protected boolean mTouchingProgressBar;
    protected float mDownX;
    protected float mDownY;
    protected boolean mChangeVolume;
    protected boolean mChangePosition;
    protected boolean mChangeBrightness;
    protected long mGestureDownPosition;
    protected int mGestureDownVolume;
    protected float mGestureDownBrightness;
    protected long mSeekTimePosition;
    boolean tmp_test_back = false;

    public LuckyFrameLayout(Context context) {
        super(context);
        init(context);
    }

    public LuckyFrameLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public static void releaseAllVideos() {
        if ((System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) > FULL_SCREEN_NORMAL_DELAY) {
            Log.d(TAG, "releaseAllVideos");
            LuckyManger.completeAll();
            LuckyMediaManager.instance().positionInList = -1;
            LuckyMediaManager.instance().releaseMediaPlayer();
        }
    }

    public static void startFullscreen(Context context, Class _class, String url, String title) {
        startFullscreen(context, _class, new LuckyDataSource(url, title));
    }

    public static void startFullscreen(Context context, Class _class, LuckyDataSource luckyDataSource) {
        hideSupportActionBar(context);
        LuckyUtils.setRequestedOrientation(context, FULLSCREEN_ORIENTATION);
        ViewGroup vp = (LuckyUtils.scanForActivity(context))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
        }
        try {
            Constructor<LuckyFrameLayout> constructor = _class.getConstructor(Context.class);
            final LuckyFrameLayout luckyFrameLayout = constructor.newInstance(context);
            luckyFrameLayout.setId(R.id.jz_fullscreen_id);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(luckyFrameLayout, lp);
//            final Animation ra = AnimationUtils.loadAnimation(context, R.anim.start_fullscreen);
//            jzVideoPlayer.setAnimation(ra);
            luckyFrameLayout.setUp(luckyDataSource, LuckyFrameLayoutControl.SCREEN_WINDOW_FULLSCREEN);
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            luckyFrameLayout.startButton.performClick();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean backPress() {
        Log.i(TAG, "backPress");
        if ((System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) < FULL_SCREEN_NORMAL_DELAY)
            return false;

        if (LuckyManger.getSecondFloor() != null) {
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            if (LuckyManger.getFirstFloor().luckyDataSource.containsTheUrl(LuckyMediaManager.getDataSource().getCurrentUrl())) {
                LuckyFrameLayout luckyFrameLayout = LuckyManger.getSecondFloor();
                luckyFrameLayout.onEvent(luckyFrameLayout.currentScreen == LuckyFrameLayoutControl.SCREEN_WINDOW_FULLSCREEN ?
                        LuckyUserAction.ON_QUIT_FULLSCREEN :
                        LuckyUserAction.ON_QUIT_TINYSCREEN);
                LuckyManger.getFirstFloor().playOnThisJzvd();
            } else {
                quitFullscreenOrTinyWindow();
            }
            return true;
        } else if (LuckyManger.getFirstFloor() != null &&
                (LuckyManger.getFirstFloor().currentScreen == SCREEN_WINDOW_FULLSCREEN ||
                        LuckyManger.getFirstFloor().currentScreen == SCREEN_WINDOW_TINY)) {//以前我总想把这两个判断写到一起，这分明是两个独立是逻辑
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
            quitFullscreenOrTinyWindow();
            return true;
        }
        return false;
    }

    public static void quitFullscreenOrTinyWindow() {
        //直接退出全屏和小窗
        LuckyManger.getFirstFloor().clearFloatScreen();
        LuckyMediaManager.instance().releaseMediaPlayer();
        LuckyManger.completeAll();
    }

    @SuppressLint("RestrictedApi")
    public static void showSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && LuckyUtils.getAppCompActivity(context) != null) {
            ActionBar ab = LuckyUtils.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.show();
            }
        }
        if (TOOL_BAR_EXIST) {
            LuckyUtils.getWindow(context).clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    @SuppressLint("RestrictedApi")
    public static void hideSupportActionBar(Context context) {
        if (ACTION_BAR_EXIST && LuckyUtils.getAppCompActivity(context) != null) {
            ActionBar ab = LuckyUtils.getAppCompActivity(context).getSupportActionBar();
            if (ab != null) {
                ab.setShowHideAnimationEnabled(false);
                ab.hide();
            }
        }
        if (TOOL_BAR_EXIST) {
            LuckyUtils.getWindow(context).setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                    WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }

    public static void clearSavedProgress(Context context, String url) {
        LuckyUtils.clearSavedProgress(context, url);
    }

    public static void setJzUserAction(LuckyUserAction jzUserEvent) {
        JZ_USER_EVENT = jzUserEvent;
    }

    public static void goOnPlayOnResume() {
        if (LuckyManger.getCurrentJzvd() != null) {
            LuckyFrameLayout luckyFrameLayout = LuckyManger.getCurrentJzvd();
            if (luckyFrameLayout.currentState == LuckyFrameLayout.CURRENT_STATE_PAUSE) {
                if (ON_PLAY_PAUSE_TMP_STATE == CURRENT_STATE_PAUSE) {
                    luckyFrameLayout.onStatePause();
                    LuckyMediaManager.pause();
                } else {
                    luckyFrameLayout.onStatePlaying();
                    LuckyMediaManager.start();
                }
                ON_PLAY_PAUSE_TMP_STATE = 0;
            }
        }
    }

    public static int ON_PLAY_PAUSE_TMP_STATE = 0;

    public static void goOnPlayOnPause() {
        if (LuckyManger.getCurrentJzvd() != null) {
            LuckyFrameLayout luckyFrameLayout = LuckyManger.getCurrentJzvd();
            if (luckyFrameLayout.currentState == LuckyFrameLayout.CURRENT_STATE_AUTO_COMPLETE ||
                    luckyFrameLayout.currentState == LuckyFrameLayout.CURRENT_STATE_NORMAL ||
                    luckyFrameLayout.currentState == LuckyFrameLayout.CURRENT_STATE_ERROR) {
//                JZVideoPlayer.releaseAllVideos();
            } else {
                ON_PLAY_PAUSE_TMP_STATE = luckyFrameLayout.currentState;
                luckyFrameLayout.onStatePause();
                LuckyMediaManager.pause();
            }
        }
    }

    public static void onScrollAutoTiny(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        int currentPlayPosition = LuckyMediaManager.instance().positionInList;
        if (currentPlayPosition >= 0) {
            if ((currentPlayPosition < firstVisibleItem || currentPlayPosition > (lastVisibleItem - 1))) {
                if (LuckyManger.getCurrentJzvd() != null &&
                        LuckyManger.getCurrentJzvd().currentScreen != LuckyFrameLayout.SCREEN_WINDOW_TINY &&
                        LuckyManger.getCurrentJzvd().currentScreen != LuckyFrameLayout.SCREEN_WINDOW_FULLSCREEN) {
                    if (LuckyManger.getCurrentJzvd().currentState == LuckyFrameLayout.CURRENT_STATE_PAUSE) {
                        LuckyFrameLayout.releaseAllVideos();
                    } else {
                        Log.e(TAG, "onScroll: out screen");
                        LuckyManger.getCurrentJzvd().startWindowTiny();
                    }
                }
            } else {
                if (LuckyManger.getCurrentJzvd() != null &&
                        LuckyManger.getCurrentJzvd().currentScreen == LuckyFrameLayout.SCREEN_WINDOW_TINY) {
                    Log.e(TAG, "onScroll: into screen");
                    LuckyFrameLayout.backPress();
                }
            }
        }
    }

    public static void onScrollReleaseAllVideos(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        int lastVisibleItem = firstVisibleItem + visibleItemCount;
        int currentPlayPosition = LuckyMediaManager.instance().positionInList;
        Log.e(TAG, "onScrollReleaseAllVideos: " +
                currentPlayPosition + " " + firstVisibleItem + " " + currentPlayPosition + " " + lastVisibleItem);
        if (currentPlayPosition >= 0) {
            if ((currentPlayPosition < firstVisibleItem || currentPlayPosition > (lastVisibleItem - 1))) {
                if (LuckyManger.getCurrentJzvd().currentScreen != LuckyFrameLayout.SCREEN_WINDOW_FULLSCREEN) {
                    LuckyFrameLayout.releaseAllVideos();//为什么最后一个视频横屏会调用这个，其他地方不会
                }
            }
        }
    }

    public static void onChildViewAttachedToWindow(View view, int jzvdId) {
        if (LuckyManger.getCurrentJzvd() != null && LuckyManger.getCurrentJzvd().currentScreen == LuckyFrameLayout.SCREEN_WINDOW_TINY) {
            LuckyFrameLayout luckyFrameLayout = view.findViewById(jzvdId);
            if (luckyFrameLayout != null && luckyFrameLayout.luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl())) {
                LuckyFrameLayout.backPress();
            }
        }
    }

    public static void onChildViewDetachedFromWindow(View view) {
        if (LuckyManger.getCurrentJzvd() != null && LuckyManger.getCurrentJzvd().currentScreen != LuckyFrameLayout.SCREEN_WINDOW_TINY) {
            LuckyFrameLayout luckyFrameLayout = LuckyManger.getCurrentJzvd();
            if (((ViewGroup) view).indexOfChild(luckyFrameLayout) != -1) {
                if (luckyFrameLayout.currentState == LuckyFrameLayout.CURRENT_STATE_PAUSE) {
                    LuckyFrameLayout.releaseAllVideos();
                } else {
                    luckyFrameLayout.startWindowTiny();
                }
            }
        }
    }

    public static void setTextureViewRotation(int rotation) {
        if (LuckyMediaManager.textureView != null) {
            LuckyMediaManager.textureView.setRotation(rotation);
        }
    }

    public static void setVideoImageDisplayType(int type) {
        LuckyFrameLayout.VIDEO_IMAGE_DISPLAY_TYPE = type;
        if (LuckyMediaManager.textureView != null) {
            LuckyMediaManager.textureView.requestLayout();
        }
    }

    public Object getCurrentUrl() {
        return luckyDataSource.getCurrentUrl();
    }

    public abstract int getLayoutId();

    public void init(Context context) {
        View.inflate(context, getLayoutId(), this);
        startButton = findViewById(R.id.start);
        fullscreenButton = findViewById(R.id.fullscreen);
        progressBar = findViewById(R.id.bottom_seek_progress);
        currentTimeTextView = findViewById(R.id.current);
        totalTimeTextView = findViewById(R.id.total);
        bottomContainer = findViewById(R.id.layout_bottom);
        textureViewContainer = findViewById(R.id.surface_container);
        topContainer = findViewById(R.id.layout_top);

        startButton.setOnClickListener(this);
        fullscreenButton.setOnClickListener(this);
        progressBar.setOnSeekBarChangeListener(this);
        bottomContainer.setOnClickListener(this);
        textureViewContainer.setOnClickListener(this);
        textureViewContainer.setOnTouchListener(this);

        mScreenWidth = getContext().getResources().getDisplayMetrics().widthPixels;
        mScreenHeight = getContext().getResources().getDisplayMetrics().heightPixels;
        mAudioManager = (AudioManager) getContext().getSystemService(Context.AUDIO_SERVICE);

        try {
            if (isCurrentPlay()) {
                NORMAL_ORIENTATION = ((AppCompatActivity) context).getRequestedOrientation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setUp(String url, String title, int screen) {
        setUp(new LuckyDataSource(url, title), screen);
    }

    public void setUp(LuckyDataSource luckyDataSource, int screen) {
        if (this.luckyDataSource != null && luckyDataSource.getCurrentUrl() != null &&
                this.luckyDataSource.containsTheUrl(luckyDataSource.getCurrentUrl())) {
            return;
        }
        if (isCurrentJZVD() && luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl())) {
            long position = 0;
            try {
                position = LuckyMediaManager.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
            if (position != 0) {
                LuckyUtils.saveProgress(getContext(), LuckyMediaManager.getCurrentUrl(), position);
            }
            LuckyMediaManager.instance().releaseMediaPlayer();
        } else if (isCurrentJZVD() && !luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl())) {
            startWindowTiny();
        } else if (!isCurrentJZVD() && luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl())) {
            if (LuckyManger.getCurrentJzvd() != null &&
                    LuckyManger.getCurrentJzvd().currentScreen == LuckyFrameLayout.SCREEN_WINDOW_TINY) {
                //需要退出小窗退到我这里，我这里是第一层级
                tmp_test_back = true;
            }
        } else if (!isCurrentJZVD() && !luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl())) {
        }
        this.luckyDataSource = luckyDataSource;
        this.currentScreen = screen;
        onStateNormal();

    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.start) {
            Log.i(TAG, "onClick start [" + this.hashCode() + "] ");
            if (luckyDataSource == null || luckyDataSource.urlsMap.isEmpty() || luckyDataSource.getCurrentUrl() == null) {
                Toast.makeText(getContext(), getResources().getString(R.string.no_url), Toast.LENGTH_SHORT).show();
                return;
            }
            if (currentState == CURRENT_STATE_NORMAL) {
                if (!luckyDataSource.getCurrentUrl().toString().startsWith("file") && !
                        luckyDataSource.getCurrentUrl().toString().startsWith("/") &&
                        !LuckyUtils.isWifiConnected(getContext()) && !WIFI_TIP_DIALOG_SHOWED) {
                    showWifiDialog();
                    return;
                }
                startVideo();
                onEvent(LuckyUserAction.ON_CLICK_START_ICON);//开始的事件应该在播放之后，此处特殊
            } else if (currentState == CURRENT_STATE_PLAYING) {
                onEvent(LuckyUserAction.ON_CLICK_PAUSE);
                Log.d(TAG, "pauseVideo [" + this.hashCode() + "] ");
                LuckyMediaManager.pause();
                onStatePause();
            } else if (currentState == CURRENT_STATE_PAUSE) {
                onEvent(LuckyUserAction.ON_CLICK_RESUME);
                LuckyMediaManager.start();
                onStatePlaying();
            } else if (currentState == CURRENT_STATE_AUTO_COMPLETE) {
                onEvent(LuckyUserAction.ON_CLICK_START_AUTO_COMPLETE);
                startVideo();
            }
        } else if (i == R.id.fullscreen) {
            Log.i(TAG, "onClick fullscreen [" + this.hashCode() + "] ");
            if (currentState == CURRENT_STATE_AUTO_COMPLETE) return;
            if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                //quit fullscreen
                backPress();
            } else {
                Log.d(TAG, "toFullscreenActivity [" + this.hashCode() + "] ");
                onEvent(LuckyUserAction.ON_ENTER_FULLSCREEN);
                startWindowFullscreen();
            }
        }
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        float x = event.getX();
        float y = event.getY();
        int id = v.getId();
        if (id == R.id.surface_container) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    Log.i(TAG, "onTouch surfaceContainer actionDown [" + this.hashCode() + "] ");
                    mTouchingProgressBar = true;

                    mDownX = x;
                    mDownY = y;
                    mChangeVolume = false;
                    mChangePosition = false;
                    mChangeBrightness = false;
                    break;
                case MotionEvent.ACTION_MOVE:
                    Log.i(TAG, "onTouch surfaceContainer actionMove [" + this.hashCode() + "] ");
                    float deltaX = x - mDownX;
                    float deltaY = y - mDownY;
                    float absDeltaX = Math.abs(deltaX);
                    float absDeltaY = Math.abs(deltaY);
                    if (currentScreen == SCREEN_WINDOW_FULLSCREEN) {
                        if (!mChangePosition && !mChangeVolume && !mChangeBrightness) {
                            if (absDeltaX > THRESHOLD || absDeltaY > THRESHOLD) {
                                cancelProgressTimer();
                                if (absDeltaX >= THRESHOLD) {
                                    // 全屏模式下的CURRENT_STATE_ERROR状态下,不响应进度拖动事件.
                                    // 否则会因为mediaplayer的状态非法导致App Crash
                                    if (currentState != CURRENT_STATE_ERROR) {
                                        mChangePosition = true;
                                        mGestureDownPosition = getCurrentPositionWhenPlaying();
                                    }
                                } else {
                                    //如果y轴滑动距离超过设置的处理范围，那么进行滑动事件处理
                                    if (mDownX < mScreenWidth * 0.5f) {//左侧改变亮度
                                        mChangeBrightness = true;
                                        WindowManager.LayoutParams lp = LuckyUtils.getWindow(getContext()).getAttributes();
                                        if (lp.screenBrightness < 0) {
                                            try {
                                                mGestureDownBrightness = Settings.System.getInt(getContext().getContentResolver(), Settings.System.SCREEN_BRIGHTNESS);
                                                Log.i(TAG, "current system brightness: " + mGestureDownBrightness);
                                            } catch (Settings.SettingNotFoundException e) {
                                                e.printStackTrace();
                                            }
                                        } else {
                                            mGestureDownBrightness = lp.screenBrightness * 255;
                                            Log.i(TAG, "current activity brightness: " + mGestureDownBrightness);
                                        }
                                    } else {//右侧改变声音
                                        mChangeVolume = true;
                                        mGestureDownVolume = mAudioManager.getStreamVolume(AudioManager.STREAM_MUSIC);
                                    }
                                }
                            }
                        }
                    }
                    if (mChangePosition) {
                        long totalTimeDuration = getDuration();
                        mSeekTimePosition = (int) (mGestureDownPosition + deltaX * totalTimeDuration / mScreenWidth);
                        if (mSeekTimePosition > totalTimeDuration)
                            mSeekTimePosition = totalTimeDuration;
                        String seekTime = LuckyUtils.stringForTime(mSeekTimePosition);
                        String totalTime = LuckyUtils.stringForTime(totalTimeDuration);

                        showProgressDialog(deltaX, seekTime, mSeekTimePosition, totalTime, totalTimeDuration);
                    }
                    if (mChangeVolume) {
                        deltaY = -deltaY;
                        int max = mAudioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
                        int deltaV = (int) (max * deltaY * 3 / mScreenHeight);
                        mAudioManager.setStreamVolume(AudioManager.STREAM_MUSIC, mGestureDownVolume + deltaV, 0);
                        //dialog中显示百分比
                        int volumePercent = (int) (mGestureDownVolume * 100 / max + deltaY * 3 * 100 / mScreenHeight);
                        showVolumeDialog(-deltaY, volumePercent);
                    }

                    if (mChangeBrightness) {
                        deltaY = -deltaY;
                        int deltaV = (int) (255 * deltaY * 3 / mScreenHeight);
                        WindowManager.LayoutParams params = LuckyUtils.getWindow(getContext()).getAttributes();
                        if (((mGestureDownBrightness + deltaV) / 255) >= 1) {//这和声音有区别，必须自己过滤一下负值
                            params.screenBrightness = 1;
                        } else if (((mGestureDownBrightness + deltaV) / 255) <= 0) {
                            params.screenBrightness = 0.01f;
                        } else {
                            params.screenBrightness = (mGestureDownBrightness + deltaV) / 255;
                        }
                        LuckyUtils.getWindow(getContext()).setAttributes(params);
                        //dialog中显示百分比
                        int brightnessPercent = (int) (mGestureDownBrightness * 100 / 255 + deltaY * 3 * 100 / mScreenHeight);
                        showBrightnessDialog(brightnessPercent);
//                        mDownY = y;
                    }
                    break;
                case MotionEvent.ACTION_UP:
                    Log.i(TAG, "onTouch surfaceContainer actionUp [" + this.hashCode() + "] ");
                    mTouchingProgressBar = false;
                    dismissProgressDialog();
                    dismissVolumeDialog();
                    dismissBrightnessDialog();
                    if (mChangePosition) {
                        onEvent(LuckyUserAction.ON_TOUCH_SCREEN_SEEK_POSITION);
                        LuckyMediaManager.seekTo(mSeekTimePosition);
                        long duration = getDuration();
                        int progress = (int) (mSeekTimePosition * 100 / (duration == 0 ? 1 : duration));
                        progressBar.setProgress(progress);
                    }
                    if (mChangeVolume) {
                        onEvent(LuckyUserAction.ON_TOUCH_SCREEN_SEEK_VOLUME);
                    }
                    startProgressTimer();
                    break;
            }
        }
        return false;
    }

    public void startVideo() {
        LuckyManger.completeAll();
        Log.d(TAG, "startVideo [" + this.hashCode() + "] ");
        initTextureView();
        addTextureView();
        AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.requestAudioFocus(onAudioFocusChangeListener, AudioManager.STREAM_MUSIC, AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);
        LuckyUtils.scanForActivity(getContext()).getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        LuckyMediaManager.setDataSource(luckyDataSource);
        LuckyMediaManager.instance().positionInList = positionInList;
        onStatePreparing();
        LuckyManger.setFirstFloor(this);
    }

    public void onPrepared() {
        Log.i(TAG, "onPrepared " + " [" + this.hashCode() + "] ");
        onStatePrepared();
        onStatePlaying();
    }

    public void setState(int state) {
        setState(state, 0, 0);
    }

    public void setState(int state, int urlMapIndex, int seekToInAdvance) {
        switch (state) {
            case CURRENT_STATE_NORMAL:
                onStateNormal();
                break;
            case CURRENT_STATE_PREPARING:
                onStatePreparing();
                break;
            case CURRENT_STATE_PREPARING_CHANGING_URL:
                changeUrl(urlMapIndex, seekToInAdvance);
                break;
            case CURRENT_STATE_PLAYING:
                onStatePlaying();
                break;
            case CURRENT_STATE_PAUSE:
                onStatePause();
                break;
            case CURRENT_STATE_ERROR:
                onStateError();
                break;
            case CURRENT_STATE_AUTO_COMPLETE:
                onStateAutoComplete();
                break;
        }
    }

    public void onStateNormal() {
        Log.i(TAG, "onStateNormal " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_NORMAL;
        cancelProgressTimer();
    }

    public void onStatePreparing() {
        Log.i(TAG, "onStatePreparing " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_PREPARING;
        resetProgressAndTime();
    }

    public void changeUrl(int urlMapIndex, long seekToInAdvance) {
        currentState = CURRENT_STATE_PREPARING_CHANGING_URL;
        this.seekToInAdvance = seekToInAdvance;
        luckyDataSource.currentUrlIndex = urlMapIndex;
        LuckyMediaManager.setDataSource(luckyDataSource);
        LuckyMediaManager.instance().prepare();
    }

    public void changeUrl(LuckyDataSource luckyDataSource, long seekToInAdvance) {
        currentState = CURRENT_STATE_PREPARING_CHANGING_URL;
        this.seekToInAdvance = seekToInAdvance;
        this.luckyDataSource = luckyDataSource;
        if (LuckyManger.getSecondFloor() != null && LuckyManger.getFirstFloor() != null) {
            LuckyManger.getFirstFloor().luckyDataSource = luckyDataSource;
        }
        LuckyMediaManager.setDataSource(luckyDataSource);
        LuckyMediaManager.instance().prepare();
    }

    public void changeUrl(String url, String title, long seekToInAdvance) {
        changeUrl(new LuckyDataSource(url, title), seekToInAdvance);
    }

    public void onStatePrepared() {//因为这个紧接着就会进入播放状态，所以不设置state
        if (seekToInAdvance != 0) {
            LuckyMediaManager.seekTo(seekToInAdvance);
            seekToInAdvance = 0;
        } else {
            long position = LuckyUtils.getSavedProgress(getContext(), luckyDataSource.getCurrentUrl());
            if (position != 0) {
                LuckyMediaManager.seekTo(position);
            }
        }
    }

    public void onStatePlaying() {
        Log.i(TAG, "onStatePlaying " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_PLAYING;
        startProgressTimer();
    }

    public void onStatePause() {
        Log.i(TAG, "onStatePause " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_PAUSE;
        startProgressTimer();
    }

    public void onStateError() {
        Log.i(TAG, "onStateError " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_ERROR;
        cancelProgressTimer();
    }

    public void onStateAutoComplete() {
        Log.i(TAG, "onStateAutoComplete " + " [" + this.hashCode() + "] ");
        currentState = CURRENT_STATE_AUTO_COMPLETE;
        cancelProgressTimer();
        progressBar.setProgress(100);
        currentTimeTextView.setText(totalTimeTextView.getText());
    }

    public void onInfo(int what, int extra) {
        Log.d(TAG, "onInfo what - " + what + " extra - " + extra);
    }

    public void onError(int what, int extra) {
        Log.e(TAG, "onError " + what + " - " + extra + " [" + this.hashCode() + "] ");
        if (what != 38 && extra != -38 && what != -38 && extra != 38 && extra != -19) {
            onStateError();
            if (isCurrentPlay()) {
                LuckyMediaManager.instance().releaseMediaPlayer();
            }
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (currentScreen == SCREEN_WINDOW_FULLSCREEN || currentScreen == SCREEN_WINDOW_TINY) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
            return;
        }
        if (widthRatio != 0 && heightRatio != 0) {
            int specWidth = MeasureSpec.getSize(widthMeasureSpec);
            int specHeight = (int) ((specWidth * (float) heightRatio) / widthRatio);
            setMeasuredDimension(specWidth, specHeight);

            int childWidthMeasureSpec = MeasureSpec.makeMeasureSpec(specWidth, MeasureSpec.EXACTLY);
            int childHeightMeasureSpec = MeasureSpec.makeMeasureSpec(specHeight, MeasureSpec.EXACTLY);
            getChildAt(0).measure(childWidthMeasureSpec, childHeightMeasureSpec);
        } else {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        }

    }

    public void onAutoCompletion() {
        Runtime.getRuntime().gc();
        Log.i(TAG, "onAutoCompletion " + " [" + this.hashCode() + "] ");
        onEvent(LuckyUserAction.ON_AUTO_COMPLETE);
        dismissVolumeDialog();
        dismissProgressDialog();
        dismissBrightnessDialog();
        onStateAutoComplete();

        if (currentScreen == SCREEN_WINDOW_FULLSCREEN || currentScreen == SCREEN_WINDOW_TINY) {
            backPress();
        }
        LuckyMediaManager.instance().releaseMediaPlayer();
        LuckyUtils.scanForActivity(getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        LuckyUtils.saveProgress(getContext(), luckyDataSource.getCurrentUrl(), 0);
    }

    public void onCompletion() {
        Log.i(TAG, "onCompletion " + " [" + this.hashCode() + "] ");
        if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE) {
            long position = getCurrentPositionWhenPlaying();
            LuckyUtils.saveProgress(getContext(), luckyDataSource.getCurrentUrl(), position);
        }
        cancelProgressTimer();
        dismissBrightnessDialog();
        dismissProgressDialog();
        dismissVolumeDialog();
        onStateNormal();
        textureViewContainer.removeView(LuckyMediaManager.textureView);
        LuckyMediaManager.instance().currentVideoWidth = 0;
        LuckyMediaManager.instance().currentVideoHeight = 0;

        AudioManager mAudioManager = (AudioManager) getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
        mAudioManager.abandonAudioFocus(onAudioFocusChangeListener);
        LuckyUtils.scanForActivity(getContext()).getWindow().clearFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        clearFullscreenLayout();
        LuckyUtils.setRequestedOrientation(getContext(), NORMAL_ORIENTATION);

        if (LuckyMediaManager.surface != null) LuckyMediaManager.surface.release();
        if (LuckyMediaManager.savedSurfaceTexture != null)
            LuckyMediaManager.savedSurfaceTexture.release();
        LuckyMediaManager.textureView = null;
        LuckyMediaManager.savedSurfaceTexture = null;
    }

    public void release() {
        if (luckyDataSource.getCurrentUrl().equals(LuckyMediaManager.getCurrentUrl()) &&
                (System.currentTimeMillis() - CLICK_QUIT_FULLSCREEN_TIME) > FULL_SCREEN_NORMAL_DELAY) {
            //在非全屏的情况下只能backPress()
            if (LuckyManger.getSecondFloor() != null &&
                    LuckyManger.getSecondFloor().currentScreen == SCREEN_WINDOW_FULLSCREEN) {//点击全屏
            } else if (LuckyManger.getSecondFloor() == null && LuckyManger.getFirstFloor() != null &&
                    LuckyManger.getFirstFloor().currentScreen == SCREEN_WINDOW_FULLSCREEN) {//直接全屏
            } else {
                Log.d(TAG, "releaseMediaPlayer [" + this.hashCode() + "]");
                releaseAllVideos();
            }
        }
    }

    public void initTextureView() {
        removeTextureView();
        LuckyMediaManager.textureView = new LuckyTextureView(getContext().getApplicationContext());
        LuckyMediaManager.textureView.setSurfaceTextureListener(LuckyMediaManager.instance());
    }

    public void addTextureView() {
        Log.d(TAG, "addTextureView [" + this.hashCode() + "] ");
        FrameLayout.LayoutParams layoutParams =
                new FrameLayout.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        Gravity.CENTER);
        textureViewContainer.addView(LuckyMediaManager.textureView, layoutParams);
    }

    public void removeTextureView() {
        LuckyMediaManager.savedSurfaceTexture = null;
        if (LuckyMediaManager.textureView != null && LuckyMediaManager.textureView.getParent() != null) {
            ((ViewGroup) LuckyMediaManager.textureView.getParent()).removeView(LuckyMediaManager.textureView);
        }
    }

    public void clearFullscreenLayout() {
        ViewGroup vp = (LuckyUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View oldF = vp.findViewById(R.id.jz_fullscreen_id);
        View oldT = vp.findViewById(R.id.jz_tiny_id);
        if (oldF != null) {
            vp.removeView(oldF);
        }
        if (oldT != null) {
            vp.removeView(oldT);
        }
        showSupportActionBar(getContext());
    }

    public void clearFloatScreen() {
        LuckyUtils.setRequestedOrientation(getContext(), NORMAL_ORIENTATION);
        showSupportActionBar(getContext());
        ViewGroup vp = (LuckyUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        LuckyFrameLayout fullLuckyFrameLayout = vp.findViewById(R.id.jz_fullscreen_id);
        LuckyFrameLayout tinyLuckyFrameLayout = vp.findViewById(R.id.jz_tiny_id);

        if (fullLuckyFrameLayout != null) {
            vp.removeView(fullLuckyFrameLayout);
            if (fullLuckyFrameLayout.textureViewContainer != null)
                fullLuckyFrameLayout.textureViewContainer.removeView(LuckyMediaManager.textureView);
        }
        if (tinyLuckyFrameLayout != null) {
            vp.removeView(tinyLuckyFrameLayout);
            if (tinyLuckyFrameLayout.textureViewContainer != null)
                tinyLuckyFrameLayout.textureViewContainer.removeView(LuckyMediaManager.textureView);
        }
        LuckyManger.setSecondFloor(null);
    }

    public void onVideoSizeChanged() {
        Log.i(TAG, "onVideoSizeChanged " + " [" + this.hashCode() + "] ");
        if (LuckyMediaManager.textureView != null) {
            if (videoRotation != 0) {
                LuckyMediaManager.textureView.setRotation(videoRotation);
            }
            LuckyMediaManager.textureView.setVideoSize(LuckyMediaManager.instance().currentVideoWidth, LuckyMediaManager.instance().currentVideoHeight);
        }
    }

    public void startProgressTimer() {
        Log.i(TAG, "startProgressTimer: " + " [" + this.hashCode() + "] ");
        cancelProgressTimer();
        UPDATE_PROGRESS_TIMER = new Timer();
        mProgressTimerTask = new ProgressTimerTask();
        UPDATE_PROGRESS_TIMER.schedule(mProgressTimerTask, 0, 300);
    }

    public void cancelProgressTimer() {
        if (UPDATE_PROGRESS_TIMER != null) {
            UPDATE_PROGRESS_TIMER.cancel();
        }
        if (mProgressTimerTask != null) {
            mProgressTimerTask.cancel();
        }
    }

    public void onProgress(int progress, long position, long duration) {
//        Log.d(TAG, "onProgress: progress=" + progress + " position=" + position + " duration=" + duration);
        if (!mTouchingProgressBar) {
            if (seekToManulPosition != -1) {
                if (seekToManulPosition > progress) {
                    return;
                } else {
                    seekToManulPosition = -1;
                }
            } else {
                if (progress != 0) progressBar.setProgress(progress);
            }
        }
        if (position != 0) currentTimeTextView.setText(LuckyUtils.stringForTime(position));
        totalTimeTextView.setText(LuckyUtils.stringForTime(duration));
    }

    public void setBufferProgress(int bufferProgress) {
        if (bufferProgress != 0) progressBar.setSecondaryProgress(bufferProgress);
    }

    public void resetProgressAndTime() {
        progressBar.setProgress(0);
        progressBar.setSecondaryProgress(0);
        currentTimeTextView.setText(LuckyUtils.stringForTime(0));
        totalTimeTextView.setText(LuckyUtils.stringForTime(0));
    }

    public long getCurrentPositionWhenPlaying() {
        long position = 0;
        //TODO 这块的判断应该根据MediaPlayer来
        if (currentState == CURRENT_STATE_PLAYING ||
                currentState == CURRENT_STATE_PAUSE) {
            try {
                position = LuckyMediaManager.getCurrentPosition();
            } catch (IllegalStateException e) {
                e.printStackTrace();
                return position;
            }
        }
        return position;
    }

    public long getDuration() {
        long duration = 0;
        //TODO MediaPlayer 判空的问题
//        if (LuckyMediaManager.instance().mediaPlayer == null) return duration;
        try {
            duration = LuckyMediaManager.getDuration();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            return duration;
        }
        return duration;
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStartTrackingTouch [" + this.hashCode() + "] ");
        cancelProgressTimer();
        ViewParent vpdown = getParent();
        while (vpdown != null) {
            vpdown.requestDisallowInterceptTouchEvent(true);
            vpdown = vpdown.getParent();
        }
    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        Log.i(TAG, "bottomProgress onStopTrackingTouch [" + this.hashCode() + "] ");
        onEvent(LuckyUserAction.ON_SEEK_POSITION);
        startProgressTimer();
        ViewParent vpup = getParent();
        while (vpup != null) {
            vpup.requestDisallowInterceptTouchEvent(false);
            vpup = vpup.getParent();
        }
        if (currentState != CURRENT_STATE_PLAYING &&
                currentState != CURRENT_STATE_PAUSE) return;
        long time = seekBar.getProgress() * getDuration() / 100;
        seekToManulPosition = seekBar.getProgress();
        LuckyMediaManager.seekTo(time);
        Log.i(TAG, "seekTo " + time + " [" + this.hashCode() + "] ");
    }

    public int seekToManulPosition = -1;

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if (fromUser) {
            //设置这个progres对应的时间，给textview
            long duration = getDuration();
            currentTimeTextView.setText(LuckyUtils.stringForTime(progress * duration / 100));
        }
    }

    public void startWindowFullscreen() {
        Log.i(TAG, "startWindowFullscreen " + " [" + this.hashCode() + "] ");
        hideSupportActionBar(getContext());

        ViewGroup vp = (LuckyUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_fullscreen_id);
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(LuckyMediaManager.textureView);
        try {
            Constructor<LuckyFrameLayout> constructor = (Constructor<LuckyFrameLayout>) LuckyFrameLayout.this.getClass().getConstructor(Context.class);
            LuckyFrameLayout luckyFrameLayout = constructor.newInstance(getContext());
            luckyFrameLayout.setId(R.id.jz_fullscreen_id);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            vp.addView(luckyFrameLayout, lp);
            luckyFrameLayout.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY | View.SYSTEM_UI_FLAG_FULLSCREEN);
            luckyFrameLayout.setUp(luckyDataSource, LuckyFrameLayoutControl.SCREEN_WINDOW_FULLSCREEN);
            luckyFrameLayout.setState(currentState);
            luckyFrameLayout.addTextureView();
            LuckyManger.setSecondFloor(luckyFrameLayout);
//            final Animation ra = AnimationUtils.loadAnimation(getContext(), R.anim.start_fullscreen);
//            jzVideoPlayer.setAnimation(ra);
            LuckyUtils.setRequestedOrientation(getContext(), FULLSCREEN_ORIENTATION);

            onStateNormal();
            luckyFrameLayout.progressBar.setSecondaryProgress(progressBar.getSecondaryProgress());
            luckyFrameLayout.startProgressTimer();
            CLICK_QUIT_FULLSCREEN_TIME = System.currentTimeMillis();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void startWindowTiny() {
        Log.i(TAG, "startWindowTiny " + " [" + this.hashCode() + "] ");
        onEvent(LuckyUserAction.ON_ENTER_TINYSCREEN);
        if (currentState == CURRENT_STATE_NORMAL || currentState == CURRENT_STATE_ERROR || currentState == CURRENT_STATE_AUTO_COMPLETE)
            return;
        ViewGroup vp = (LuckyUtils.scanForActivity(getContext()))//.getWindow().getDecorView();
                .findViewById(Window.ID_ANDROID_CONTENT);
        View old = vp.findViewById(R.id.jz_tiny_id);
        if (old != null) {
            vp.removeView(old);
        }
        textureViewContainer.removeView(LuckyMediaManager.textureView);

        try {
            Constructor<LuckyFrameLayout> constructor = (Constructor<LuckyFrameLayout>) LuckyFrameLayout.this.getClass().getConstructor(Context.class);
            LuckyFrameLayout luckyFrameLayout = constructor.newInstance(getContext());
            luckyFrameLayout.setId(R.id.jz_tiny_id);
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(400, 400);
            lp.gravity = Gravity.RIGHT | Gravity.BOTTOM;
            vp.addView(luckyFrameLayout, lp);
            luckyFrameLayout.setUp(luckyDataSource, LuckyFrameLayoutControl.SCREEN_WINDOW_TINY);
            luckyFrameLayout.setState(currentState);
            luckyFrameLayout.addTextureView();
            LuckyManger.setSecondFloor(luckyFrameLayout);
            onStateNormal();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isCurrentPlay() {
        return isCurrentJZVD()
                && luckyDataSource.containsTheUrl(LuckyMediaManager.getCurrentUrl());//不仅正在播放的url不能一样，并且各个清晰度也不能一样
    }

    public boolean isCurrentJZVD() {
        return LuckyManger.getCurrentJzvd() != null
                && LuckyManger.getCurrentJzvd() == this;
    }

    //退出全屏和小窗的方法
    public void playOnThisJzvd() {
        Log.i(TAG, "playOnThisJzvd " + " [" + this.hashCode() + "] ");
        //1.清空全屏和小窗的jzvd
        currentState = LuckyManger.getSecondFloor().currentState;
        clearFloatScreen();
        //2.在本jzvd上播放
        setState(currentState);
//        removeTextureView();
        addTextureView();
    }

    //重力感应的时候调用的函数，
    public void autoFullscreen(float x) {
        if (isCurrentPlay()
                && (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE)
                && currentScreen != SCREEN_WINDOW_FULLSCREEN
                && currentScreen != SCREEN_WINDOW_TINY) {
            if (x > 0) {
                LuckyUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                LuckyUtils.setRequestedOrientation(getContext(), ActivityInfo.SCREEN_ORIENTATION_REVERSE_LANDSCAPE);
            }
            onEvent(LuckyUserAction.ON_ENTER_FULLSCREEN);
            startWindowFullscreen();
        }
    }

    public void autoQuitFullscreen() {
        if ((System.currentTimeMillis() - lastAutoFullscreenTime) > 2000
                && isCurrentPlay()
                && currentState == CURRENT_STATE_PLAYING
                && currentScreen == SCREEN_WINDOW_FULLSCREEN) {
            lastAutoFullscreenTime = System.currentTimeMillis();
            backPress();
        }
    }

    public void onEvent(int type) {
        if (JZ_USER_EVENT != null && isCurrentPlay() && !luckyDataSource.urlsMap.isEmpty()) {
            JZ_USER_EVENT.onEvent(type, luckyDataSource.getCurrentUrl(), currentScreen);
        }
    }

    public static void setMediaInterface(LuckyMediaInterface mediaInterface) {
        LuckyMediaManager.instance().luckyMediaInterface = mediaInterface;
    }

    //TODO 是否有用
    public void onSeekComplete() {

    }

    public void showWifiDialog() {
    }

    public void showProgressDialog(float deltaX,
                                   String seekTime, long seekTimePosition,
                                   String totalTime, long totalTimeDuration) {
    }

    public void dismissProgressDialog() {

    }

    public void showVolumeDialog(float deltaY, int volumePercent) {

    }

    public void dismissVolumeDialog() {

    }

    public void showBrightnessDialog(int brightnessPercent) {

    }

    public void dismissBrightnessDialog() {

    }

    public static class JZAutoFullscreenListener implements SensorEventListener {
        @Override
        public void onSensorChanged(SensorEvent event) {//可以得到传感器实时测量出来的变化值
            final float x = event.values[SensorManager.DATA_X];
            float y = event.values[SensorManager.DATA_Y];
            float z = event.values[SensorManager.DATA_Z];
            //过滤掉用力过猛会有一个反向的大数值
            if (x < -12 || x > 12) {
                if ((System.currentTimeMillis() - lastAutoFullscreenTime) > 2000) {
                    if (LuckyManger.getCurrentJzvd() != null) {
                        LuckyManger.getCurrentJzvd().autoFullscreen(x);
                    }
                    lastAutoFullscreenTime = System.currentTimeMillis();
                }
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    }

    public class ProgressTimerTask extends TimerTask {
        @Override
        public void run() {
            if (currentState == CURRENT_STATE_PLAYING || currentState == CURRENT_STATE_PAUSE) {
//                Log.v(TAG, "onProgressUpdate " + "[" + this.hashCode() + "] ");
                post(() -> {
                    long position = getCurrentPositionWhenPlaying();
                    long duration = getDuration();
                    int progress = (int) (position * 100 / (duration == 0 ? 1 : duration));
                    onProgress(progress, position, duration);
                });
            }
        }
    }

    public Context getApplicationContext() {
        Context context = getContext();
        if (context != null) {
            Context applicationContext = context.getApplicationContext();
            if (applicationContext != null) {
                return applicationContext;
            }
        }
        return context;
    }
}
