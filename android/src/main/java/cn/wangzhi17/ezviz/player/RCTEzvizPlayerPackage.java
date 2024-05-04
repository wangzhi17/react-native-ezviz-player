package cn.wangzhi17.ezviz.player;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.facebook.react.TurboReactPackage;
import com.facebook.react.bridge.NativeModule;
import com.facebook.react.bridge.ReactApplicationContext;
import com.facebook.react.module.model.ReactModuleInfo;
import com.facebook.react.module.model.ReactModuleInfoProvider;
import com.facebook.react.uimanager.ViewManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RCTEzvizPlayerPackage extends TurboReactPackage {
    @Nullable
    @Override
    public NativeModule getModule(@NonNull String name, @NonNull ReactApplicationContext reactApplicationContext) {
        if ("EzvizPlayer".equals(name)) {
            return new RCTEzvizPlayerModule(reactApplicationContext);
        }
        return null;
    }

    @Override
    public ReactModuleInfoProvider getReactModuleInfoProvider() {
        return new ReactModuleInfoProvider() {
            @Override
            public Map<String, ReactModuleInfo> getReactModuleInfos() {
                final Map<String, ReactModuleInfo> moduleInfo = new HashMap<>();
                boolean isTurboModule = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
                moduleInfo.put(
                        "EzvizPlayer",
                        new ReactModuleInfo(
                                "EzvizPlayer",
                                "EzvizPlayer",
                                false,
                                false,
                                false,
                                isTurboModule
                        )
                );
                return moduleInfo;
            }
        };
    }

    @NonNull
    @Override
    public List<ViewManager> createViewManagers(@NonNull ReactApplicationContext reactContext) {
        List<ViewManager> list = new ArrayList<>();
        list.add(new EzvizViewManager(reactContext));
        return list;
    }
}
