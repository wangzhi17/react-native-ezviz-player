package cn.wangzhi17.ezviz.player;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;

import com.facebook.react.bridge.Arguments;
import com.facebook.react.bridge.WritableMap;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.UIManagerHelper;
import com.facebook.react.uimanager.events.Event;
import com.videogo.errorlayer.ErrorInfo;
import com.videogo.openapi.EZConstants;
import com.videogo.openapi.EZOpenSDK;
import com.videogo.openapi.EZPlayer;

import cn.wangzhi17.ezviz.player.event.OnLoadEvent;
import cn.wangzhi17.ezviz.player.event.OnPlayFailedEvent;

@SuppressLint("ViewConstructor")
public class EzvizRealPlayView extends FrameLayout implements SurfaceHolder.Callback, Handler.Callback {

    private final String TAG = "EzvizRealPlayView";
    private String mDeviceSerial = "";
    private int mCameraNo = 1;
    private String mVerifyCode = "";
    private final ThemedReactContext reactContext;
    private final Handler mHandler;
    private final SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private EZPlayer mEZPlayer;

    public final int STATUS_INIT = 1;
    public final int STATUS_START = 2;
    public final int STATUS_PLAY = 3;
    public final int STATUS_STOP = 4;

    public int mStatus = STATUS_INIT;

    public EzvizRealPlayView(@NonNull ThemedReactContext context) {
        super(context);
        reactContext = context;
        mHandler = new Handler(this);
        surfaceView = new SurfaceView(context);
        addView(surfaceView);
        surfaceView.getHolder().addCallback(this);
    }

    @Override
    public boolean handleMessage(@NonNull Message msg) {
        switch (msg.what) {
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_SUCCESS:// 播放成功消息
                if (mStatus != STATUS_STOP) {
                    mStatus = STATUS_PLAY;
                    mEZPlayer.openSound();
                }
                break;
            case EZConstants.EZRealPlayConstants.MSG_REALPLAY_PLAY_FAIL:
                if (mStatus != STATUS_STOP) {
                    mStatus = STATUS_STOP;
                    stopRealPlay();
                    ErrorInfo errorInfo = (ErrorInfo) msg.obj;

                    WritableMap map = Arguments.createMap();
                    map.putInt("errorCode", errorInfo.errorCode);
                    map.putString("description", errorInfo.description);
                    map.putString("sulution", errorInfo.sulution);

                    dispatchEvent(new OnPlayFailedEvent(getId(), map));
                }
                break;
        }
        return false;
    }

    @Override
    public void surfaceCreated(@NonNull SurfaceHolder holder) {
        this.surfaceHolder = holder;
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(holder);

        }
        beforeStartRealPlay();
    }

    @Override
    public void surfaceChanged(@NonNull SurfaceHolder holder, int format, int width, int height) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(surfaceHolder);
        }
    }

    @Override
    public void surfaceDestroyed(@NonNull SurfaceHolder holder) {
        if (mEZPlayer != null) {
            mEZPlayer.setSurfaceHold(null);
        }
        pause();
    }

    public void setDeviceSerial(String deviceSerial) {
        mDeviceSerial = deviceSerial;
    }

    public void setCameraNo(int cameraNo) {
        mCameraNo = cameraNo;
    }

    public void setVerifyCode(String verifyCode) {
        mVerifyCode = verifyCode;
    }

    public void setAccessToken(String accessToken) {

        if (TextUtils.isEmpty(accessToken)) {
            return;
        }
        EZOpenSDK.getInstance().setAccessToken(accessToken);
    }

    public void createPlayer() {
        if (TextUtils.isEmpty(mDeviceSerial) || mCameraNo == -1) {
            return;
        }
        mEZPlayer = EZOpenSDK.getInstance().createPlayer(mDeviceSerial, mCameraNo);

        mEZPlayer.setSurfaceHold(surfaceView.getHolder());
        mEZPlayer.setHandler(mHandler);
        beforeStartRealPlay();
    }

    private void beforeStartRealPlay() {
        if (mStatus == STATUS_START || mStatus == STATUS_PLAY || mEZPlayer == null) {
            return;
        }
        startRealPlay();
    }

    private void startRealPlay() {
        if (!TextUtils.isEmpty(mVerifyCode)) {
            mEZPlayer.setPlayVerifyCode(mVerifyCode);
        }
        mStatus = STATUS_START;
        mEZPlayer.startRealPlay();
    }

    private void stopRealPlay() {
        if (mEZPlayer != null) {
            mEZPlayer.stopRealPlay();
        }
    }

    public void openSound() {
        if (mEZPlayer != null) {
            mEZPlayer.openSound();
        }
    }

    public void closeSound() {
        if (mEZPlayer != null) {
            mEZPlayer.closeSound();
        }
    }

    public void pause() {
        mStatus = STATUS_STOP;
        stopRealPlay();
    }

    public void releasePlayer() {
        pause();
        if (mEZPlayer != null) {
            mEZPlayer.release();
            mEZPlayer = null;
        }
    }

    private void dispatchEvent(Event<OnPlayFailedEvent> event) {
        int reactTag = getId();
        UIManagerHelper.getEventDispatcherForReactTag(reactContext, reactTag).dispatchEvent(event);
    }

    public void dispatchOnLoadEvent() {
        WritableMap map = Arguments.createMap();
        int reactTag = getId();
        UIManagerHelper.getEventDispatcherForReactTag(reactContext, reactTag).dispatchEvent(new OnLoadEvent(reactTag, map));
    }
}
