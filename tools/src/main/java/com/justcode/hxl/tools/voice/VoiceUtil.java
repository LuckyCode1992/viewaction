package com.justcode.hxl.tools.voice;

import android.content.Context;
import android.media.AudioManager;

public class VoiceUtil {
    private static final VoiceUtil instance = new VoiceUtil();

    private VoiceUtil() {

    }

    public static VoiceUtil getInstance() {
        return instance;
    }

    AudioManager mAudioManager;

    public VoiceUtil init(Context context) {
        if (mAudioManager == null)
            mAudioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
        return this;
    }

    public int getMaxVolume(AudioMangerTag audioManagerTag) {
        int max = mAudioManager.getStreamMaxVolume(audioManagerTag.getValue());
        return max;
    }

    public int getCurrentVolume(AudioMangerTag audioManagerTag) {
        int currentVolume = mAudioManager.getStreamVolume(audioManagerTag.getValue());
        return currentVolume;
    }

    public enum AudioMangerTag {
        VOICE_CALL(AudioManager.STREAM_VOICE_CALL),
        VOICE_SYSTEML(AudioManager.STREAM_SYSTEM),
        VOICE_RING(AudioManager.STREAM_RING),
        VOICE_MEDIA(AudioManager.STREAM_MUSIC),
        VOICE_ALARM(AudioManager.STREAM_ALARM);
        private int voiceTAG;

        AudioMangerTag(int voice) {
            voiceTAG = voice;
        }

        public int getValue() {
            return voiceTAG;
        }
    }
}


