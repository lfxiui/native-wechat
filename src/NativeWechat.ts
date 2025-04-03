import type { TurboModule } from 'react-native'
import { TurboModuleRegistry } from 'react-native'

export interface Spec extends TurboModule {
  getScene(): Object
  // 添加与 Java 模块中相同的所有方法
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

export default TurboModuleRegistry.getEnforcing<Spec>('Wechat')