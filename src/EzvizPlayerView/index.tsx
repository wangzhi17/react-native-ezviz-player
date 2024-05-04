import React, { ComponentRef, forwardRef, useImperativeHandle, useRef } from "react";
import NativeEzvizPlayerView, { Commands, EzvizPlayerProps, EzvizPlayerType } from "./EzvizPlayerNativeComponent";

const EzvizPlayerView = forwardRef<{}, EzvizPlayerProps>(
  (
    {
      accessToken,
      deviceSerial,
      cameraNo,
      verifyCode,
      onPlayFailed,
      style
    },
    ref) => {
    const ezvizPlayerViewRef = useRef<ComponentRef<EzvizPlayerType> | null>(null);

    useImperativeHandle(ref, () => ({
      createPlayer: () => ezvizPlayerViewRef.current && Commands.createPlayer(ezvizPlayerViewRef.current),
      openSound: () => ezvizPlayerViewRef.current && Commands.openSound(ezvizPlayerViewRef.current),
      closeSound: () => ezvizPlayerViewRef.current && Commands.closeSound(ezvizPlayerViewRef.current),
      releasePlayer: () => ezvizPlayerViewRef.current && Commands.releasePlayer(ezvizPlayerViewRef.current)
    }), [ezvizPlayerViewRef]);

    return (
      <NativeEzvizPlayerView ref={ezvizPlayerViewRef}
                             style={style}
                             accessToken={accessToken}
                             deviceSerial={deviceSerial}
                             cameraNo={cameraNo}
                             verifyCode={verifyCode}
                             onPlayFailed={onPlayFailed}
      />
    );
  }
);

export default EzvizPlayerView;
