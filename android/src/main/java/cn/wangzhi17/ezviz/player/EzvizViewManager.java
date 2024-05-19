package cn.wangzhi17.ezviz.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.bridge.ReadableArray;
import com.facebook.react.common.MapBuilder;
import com.facebook.react.uimanager.ThemedReactContext;
import com.facebook.react.uimanager.ViewGroupManager;
import com.facebook.react.uimanager.ViewManagerDelegate;
import com.facebook.react.viewmanagers.EzvizPlayerManagerDelegate;
import com.facebook.react.viewmanagers.EzvizPlayerManagerInterface;

import java.util.Map;

import cn.wangzhi17.ezviz.player.event.OnPlayEvent;

public class EzvizViewManager extends ViewGroupManager<EzvizRealPlayView> implements EzvizPlayerManagerInterface<EzvizRealPlayView> {
    private final String Name = "EzvizPlayer";
    private final ViewManagerDelegate<EzvizRealPlayView> delegate;

    public EzvizViewManager(ReactApplicationContext context) {
        delegate = new EzvizPlayerManagerDelegate<>(this);
    }

    protected ViewManagerDelegate<EzvizRealPlayView> getDelegate() {
        return delegate;
    }

    @NonNull
    @Override
    public String getName() {
        return Name;
    }

    @NonNull
    @Override
    protected EzvizRealPlayView createViewInstance(@NonNull ThemedReactContext themedReactContext) {
        return new EzvizRealPlayView(themedReactContext);
    }

    @Nullable
    @Override
    public Map<String, Object> getExportedCustomDirectEventTypeConstants() {
        return MapBuilder.<String, Object>builder()
                .put(OnPlayEvent.EVENT_NAME, MapBuilder.of("registrationName", OnPlayEvent.EVENT_NAME))
                .build();
    }

    @Override
    public void setAccessToken(EzvizRealPlayView view, @Nullable String value) {
        view.setAccessToken(value);
    }

    @Override
    public void setDeviceSerial(EzvizRealPlayView view, @Nullable String value) {
        view.setDeviceSerial(value);
    }

    @Override
    public void setCameraNo(EzvizRealPlayView view, int value) {
        view.setCameraNo(value);
    }

    @Override
    public void setVerifyCode(EzvizRealPlayView view, @Nullable String value) {
        view.setVerifyCode(value);
    }

    @Override
    public void createPlayer(EzvizRealPlayView view) {
        view.createPlayer();
    }

    @Override
    public void openSound(EzvizRealPlayView view) {
        view.openSound();
    }

    @Override
    public void closeSound(EzvizRealPlayView view) {
        view.closeSound();
    }

    @Override
    public void releasePlayer(EzvizRealPlayView view) {
        view.releasePlayer();
    }

    @Nullable
    @Override
    public Map<String, Integer> getCommandsMap() {
        return super.getCommandsMap();
    }

    @Override
    public void receiveCommand(@NonNull EzvizRealPlayView root, String commandId, @Nullable ReadableArray args) {
        super.receiveCommand(root, commandId, args);
    }
}
