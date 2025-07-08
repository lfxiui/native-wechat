import {NativeEventEmitter} from 'react-native';
import Notification from './notification';
import {promisifyNativeFunction} from './utils';
import {
  NativeWechatModuleConstants,
  NativeWechatResponse,
  SendAuthRequestResponse,
  LaunchMiniProgramResponse,
  UniversalLinkCheckingResponse,
} from './typing';
import NativeWechat from './NativeWechat';
export * from './hooks';

const notification = new Notification();

let registered = false;

const generateError = (response: NativeWechatResponse) =>
  new Error(`[Native Wechat]: (${response.errorCode}) ${response.errorStr}`);

const assertRegisteration = (name: string) => {
  if (!registered) {
    throw new Error(`Please register SDK before invoking ${name}`);
  }
};

export const checkUniversalLinkReady = () => {
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }
  
  if (!NativeWechat.checkUniversalLinkReady) {
    // Android 端不支持此方法，返回一个假的成功响应
    return Promise.resolve({
      suggestion: '',
      errorInfo: '',
    } as UniversalLinkCheckingResponse);
  }

  const fn = promisifyNativeFunction<UniversalLinkCheckingResponse>(
    NativeWechat.checkUniversalLinkReady,
  );

  return fn();
};

export const registerApp = (request: {
  appid: string;
  universalLink?: string;
  log?: boolean;
  logPrefix?: string;
}) => {
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }
  
  if (!registered) {
    NativeWechat.registerApp(request);
    registered = true;
  }

  const nativeEmitter = new NativeEventEmitter(NativeWechat);

  const listener = nativeEmitter.addListener(
    'NativeWechat_Response',
    (response: NativeWechatResponse) => {
      const error = response.errorCode ? generateError(response) : null;

      notification.dispatch(response.type, error, response);
    },
  );

  return () => listener.remove();
};

export const isWechatInstalled = () => {
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }
  return promisifyNativeFunction<boolean>(NativeWechat.isWechatInstalled)();
};

export const sendAuthRequest = (
  request: {scope: string; state?: string} = {
    scope: 'snsapi_userinfo',
    state: '',
  },
) => {
  assertRegisteration('sendAuthRequest');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.sendAuthRequest,
  );

  return new Promise<SendAuthRequestResponse>((resolve, reject) => {
    fn(request).catch(reject);

    notification.once('SendAuthResp', (error, response) => {
      if (error) {
        return reject(error);
      }

      return resolve(response);
    });
  });
};

export const shareText = (request: {text: string; scene: number}) => {
  assertRegisteration('shareText');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(NativeWechat.shareText);

  return fn(request);
};

export const shareImage = (request: {src: string; scene: number}) => {
  assertRegisteration('shareImage');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(NativeWechat.shareImage);

  return fn(request);
};

export const shareVideo = (request: {
  title?: string;
  description?: string;
  scene: number;
  videoUrl: string;
  videoLowBandUrl?: string;
  coverUrl?: string;
}) => {
  assertRegisteration('shareVideo');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(NativeWechat.shareVideo);

  return fn(request);
};

export const shareWebpage = (request: {
  title?: string;
  description?: string;
  scene: number;
  webpageUrl: string;
  coverUrl?: string;
}) => {
  assertRegisteration('shareWebpage');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.shareWebpage,
  );

  return fn(request);
};

export const shareMiniProgram = (request: {
  userName: string;
  path: string;
  miniProgramType: number;
  webpageUrl: string;
  withShareTicket?: boolean;
  title?: string;
  description?: string;
  coverUrl?: string;
}) => {
  assertRegisteration('shareMiniProgram');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.shareMiniProgram,
  );

  return fn(request);
};

export const requestPayment = (request: {
  partnerId: string;
  prepayId: string;
  nonceStr: string;
  timeStamp: string;
  sign: string;
}) => {
  assertRegisteration('requestPayment');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.requestPayment,
  );

  return new Promise<NativeWechatResponse>(async (resolve, reject) => {
    fn(request).catch(reject);

    notification.once('PayResp', (error, response) => {
      if (error) {
        return reject(error);
      }

      return resolve(response);
    });
  });
};

export const requestSubscribeMessage = (request: {
  scene: number;
  templateId: string;
  reserved?: string;
}) => {
  assertRegisteration('requestSubscribeMessage');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.requestSubscribeMessage,
  );

  request.scene = +request.scene;

  return fn(request);
};

export const openCustomerService = (request: {corpid: string; url: string}) => {
  assertRegisteration('openCustomerService');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.openCustomerService,
  );

  return fn(request);
};

export const launchMiniProgram = (request: {
  userName: string;
  path: string;
  miniProgramType: number;
  onNavBack?: (res: LaunchMiniProgramResponse) => void;
}) => {
  assertRegisteration('launchMiniProgram');
  if (!NativeWechat) {
    throw new Error('Native Wechat module is not available');
  }

  request.miniProgramType = +request.miniProgramType;

  const fn = promisifyNativeFunction<Promise<boolean>>(
    NativeWechat.launchMiniProgram,
  );

  notification.once('WXLaunchMiniProgramResp', (error, response) => {
    if (!error) {
      return request.onNavBack?.(response);
    }
  });

  return fn(request);
};

export const NativeWechatConstants: NativeWechatModuleConstants = 
  NativeWechat?.getConstants?.() || {
    WXSceneSession: 0,
    WXSceneTimeline: 1,
    WXSceneFavorite: 2,
    WXMiniProgramTypeRelease: 0,
    WXMiniProgramTypeTest: 1,
    WXMiniProgramTypePreview: 2,
  };
