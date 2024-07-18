import type { TurboModule } from "react-native";
import { EmitterSubscription, NativeEventEmitter, TurboModuleRegistry } from "react-native";
import { Int32 } from "react-native/Libraries/Types/CodegenTypes";

export interface Spec extends TurboModule {
  initSDK(appKey: string, apiUrl?: string): Promise<boolean>;

  destroySDK(): Promise<boolean>;

  createVoicePlayer(deviceSerial: string, cameraNo: Int32, verifyCode: string): Promise<boolean>;

  startVoiceTalk(): Promise<boolean>;

  stopVoiceTalk(): Promise<boolean>;

  destroyVoiceTalk(): Promise<boolean>;

  addListener(eventName: string): void;

  removeListeners(count: Int32): void;
}

const RCTEzvizVoiceTalk = TurboModuleRegistry.get<Spec>("EzvizPlayerModule") as Spec;

const eventEmitter = new NativeEventEmitter(RCTEzvizVoiceTalk);
let event: EmitterSubscription;
const EVENT_NAME = "voiceTalk";

export type voiceTalkEvent = {
  errorCode: Int32
  description: string
  sulution: string
}
const addListener = (callback: (event: voiceTalkEvent) => {}) => {

  event = eventEmitter.addListener(EVENT_NAME, (event: voiceTalkEvent) => callback(event));

};

const removeAllListeners = () => {
  event && event.remove();
};

export { addListener, removeAllListeners };
export default RCTEzvizVoiceTalk;
