package cn.wangzhi17.ezviz.player;

import android.app.Application;
import android.os.Handler;
import android.os.Message;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.Promise;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.modules.core.DeviceEventManagerModule;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

public class RCTEzvizPlayerModule extends NativeEzvizVoiceTalkSpec implements Handler.Callback {
    private final String Name = "EzvizPlayer";
    private final String voiceTalkEvent = "voiceTalk";
    private final ReactApplicationContext reactContext;

    private EZPlayer mEZPlayer;

    private Handler mHandler;

    @NonNull
    @Override
    public String getName() {
        return Name;
    }

    @Override
    public void initSDK(String appKey, Promise promise) {
        Application application = (Application) reactContext.getBaseContext();
        EZOpenSDK.initLib(application, appKey);
        promise.resolve(true);
    }

    @Override
    public void destroySDK(Promise promise) {
        EZOpenSDK.finiLib();
        promise.resolve(true);
    }

    public RCTEzvizPlayerModule(ReactApplicationContext reactContext) {
        super(reactContext);
        this.reactContext = reactContext;
    }

    @Override
    public void createVoicePlayer(String deviceSerial, int cameraNo, String verifyCode, Promise promise) {
        mEZPlayer = EZOpenSDK.getInstance().createPlayer(deviceSerial, cameraNo);
        mHandler = new Handler(this);
        mEZPlayer.setPlayVerifyCode(verifyCode);
        mEZPlayer.setHandler(mHandler);
        promise.resolve(true);
    }

    @Override
    public void startVoiceTalk(Promise promise) {
        if (mEZPlayer == null) {
            promise.reject("error", "对讲失败");
            return;
        }
        mEZPlayer.closeSound();
        mEZPlayer.startVoiceTalk();
        promise.resolve(true);
    }

    @Override
    public void stopVoiceTalk(Promise promise) {
        if (mEZPlayer == null) {
            promise.reject("error", "对讲失败");
            return;
        }
        mEZPlayer.stopVoiceTalk();
        promise.resolve(true);
    }

    @Override
    public void destroyVoiceTalk(Promise promise) {
        promise.resolve(true);
        if (mEZPlayer == null) {
            return;
        }
        mEZPlayer.release();
        mEZPlayer = null;
        mHandler = null;
    }

    @Override
    public void addListener(String eventName) {

    }

    @Override
    public void removeListeners(int count) {

    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_SUCCESS:
                if (mEZPlayer != null)
                    mEZPlayer.openSound();
                WritableMap successMap = Arguments.createMap();
                successMap.putString("description", "对讲成功");
                successMap.putString("sulution", "");
                successMap.putInt("errorCode", 0);
                sendEvent(voiceTalkEvent, successMap);
                break;

            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_STOP:
                WritableMap stopMap = Arguments.createMap();
                stopMap.putString("description", "对讲结束");
                stopMap.putString("sulution", "");
                stopMap.putInt("errorCode", -1);
                sendEvent(voiceTalkEvent, stopMap);
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_VOICETALK_FAIL:
                ErrorInfo errorInfo = (ErrorInfo) msg.obj;
                WritableMap failMap = Arguments.createMap();
                failMap.putString("description", errorInfo.description);
                failMap.putString("sulution", errorInfo.sulution);
                failMap.putInt("errorCode", errorInfo.errorCode);
                sendEvent(voiceTalkEvent, failMap);
                break;
        }
        return false;
    }

    private void sendEvent(String eventName, @Nullable WritableMap params) {
        reactContext.getJSModule(DeviceEventManagerModule.RCTDeviceEventEmitter.class).emit(eventName, params);
    }
}
