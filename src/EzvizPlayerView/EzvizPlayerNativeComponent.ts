import type { HostComponent, ViewProps } from "react-native";
import { DirectEventHandler, Int32 } from "react-native/Libraries/Types/CodegenTypes";
import codegenNativeComponent from "react-native/Libraries/Utilities/codegenNativeComponent";
import codegenNativeCommands from "react-native/Libraries/Utilities/codegenNativeCommands";
import React from "react";

export type onPlayFailedEvent = {
  errorCode: Int32;
  description: string;
  solution: string
};
export type onLoad = {

};

export interface EzvizPlayerProps extends ViewProps {
  accessToken: string;
  deviceSerial: string;
  cameraNo: Int32;
  verifyCode: string;
  onPlayFailed?: DirectEventHandler<onPlayFailedEvent>;
  onLoad?: DirectEventHandler<onLoad>;

}

export type EzvizPlayerType = HostComponent<EzvizPlayerProps>;

export interface EzvizPlayerCommands {
  createPlayer: (viewRef: React.ElementRef<EzvizPlayerType>) => void;
  openSound: (viewRef: React.ElementRef<EzvizPlayerType>) => void;
  closeSound: (viewRef: React.ElementRef<EzvizPlayerType>) => void;
  releasePlayer: (viewRef: React.ElementRef<EzvizPlayerType>) => void;
}

export const Commands: EzvizPlayerCommands = codegenNativeCommands<EzvizPlayerCommands>({
  supportedCommands: ["createPlayer", "openSound", "closeSound", "releasePlayer"]
});
export default codegenNativeComponent<EzvizPlayerProps>("EzvizPlayer") as HostComponent<EzvizPlayerProps>;
