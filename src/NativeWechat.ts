import type { TurboModule } from 'react-native'
import { TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
  // 常量方法
  getConstants(): {
    WXSceneSession: number;
    WXSceneTimeline: number;
    WXSceneFavorite: number;
    WXMiniProgramTypeRelease: number;
    WXMiniProgramTypeTest: number;
    WXMiniProgramTypePreview: number;
  }
  
  // iOS 特有方法 (Android 端可以选择性实现)
  checkUniversalLinkReady?(callback: (error: boolean, result: Object) => void): void
  
  // 核心方法
  sendAuthRequest(request: Object, callback: (result: Object) => void): void
  registerApp(request: Object): void
  shareText(request: Object, callback: (result: Object) => void): void
  shareImage(request: Object, callback: (result: Object) => void): void
  shareVideo(request: Object, callback: (result: Object) => void): void
  shareWebpage(request: Object, callback: (result: Object) => void): void
  shareMiniProgram(request: Object, callback: (result: Object) => void): void
  isWechatInstalled(callback: (result: boolean) => void): void
  requestPayment(request: Object, callback: (result: Object) => void): void
  requestSubscribeMessage(request: Object, callback: (result: Object) => void): void
  launchMiniProgram(request: Object, callback: (result: Object) => void): void
  openCustomerService(request: Object, callback: (result: Object) => void): void

  addListener: (eventType: string) => void
  removeListeners: (count: number) => void
}

export default TurboModuleRegistry.get<Spec>('Wechat')