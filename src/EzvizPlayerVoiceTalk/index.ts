import RCTEzvizVoiceTalk, { addListener, removeAllListeners, voiceTalkEvent } from "./NativeEzvizVoiceTalk";
import { Int32 } from "react-native/Libraries/Types/CodegenTypes";

const EzvizPlayerVoiceTalk = {
    async initSDK(appKey: string) {
        return await RCTEzvizVoiceTalk.initSDK(appKey);
    },
    async destroySDK() {
        return await RCTEzvizVoiceTalk.destroySDK();
    },
    async createVoicePlayer(deviceSerial: string, cameraNo: Int32, verifyCode: string) {
        return await RCTEzvizVoiceTalk.createVoicePlayer(deviceSerial, cameraNo, verifyCode);
    },
    async startVoiceTalk() {
        return await RCTEzvizVoiceTalk.startVoiceTalk();
    },
    async stopVoiceTalk() {
        return await RCTEzvizVoiceTalk.stopVoiceTalk();
    },
    async destroyVoiceTalk() {
        return await RCTEzvizVoiceTalk.destroyVoiceTalk();
    },
    addListener(callback: (event: voiceTalkEvent) => {}) {
        addListener(callback);
    },
    removeListener() {
        removeAllListeners();
    }
};

export default EzvizPlayerVoiceTalk;
