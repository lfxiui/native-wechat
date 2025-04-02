import { TurboModuleRegistry as v, NativeEventEmitter as y } from "react-native";
import { useState as P, useEffect as b } from "react";
function w(e, t, n, r) {
  function s(a) {
    return a instanceof n ? a : new n(function(h) {
      h(a);
    });
  }
  return new (n || (n = Promise))(function(a, h) {
    function m(u) {
      try {
        l(r.next(u));
      } catch (d) {
        h(d);
      }
    }
    function g(u) {
      try {
        l(r.throw(u));
      } catch (d) {
        h(d);
      }
    }
    function l(u) {
      u.done ? a(u.value) : s(u.value).then(m, g);
    }
    l((r = r.apply(e, t || [])).next());
  });
}
class E {
  constructor() {
    Object.defineProperty(this, "handlers", {
      enumerable: !0,
      configurable: !0,
      writable: !0,
      value: void 0
    }), this.handlers = /* @__PURE__ */ new Map();
  }
  getQueue(t) {
    const n = this.handlers.get(t);
    return n || (this.handlers.set(t, []), []);
  }
  listen(t, n) {
    const r = this.getQueue(t);
    this.handlers.set(t, r.concat(n));
  }
  once(t, n) {
    this.handlers.set(t, [n]);
  }
  clear(t) {
    this.handlers.set(t, []);
  }
  dispatch(t, ...n) {
    this.getQueue(t).forEach((s) => s(...n)), this.clear(t);
  }
}
const i = (e) => (...t) => new Promise((n, r) => {
  e(...t, (s, a) => {
    s ? r(a) : n(a);
  });
}), o = v.getEnforcing("Wechat"), I = () => {
  const [e, t] = P(!1);
  return b(() => {
    S().then(() => t(!0)).catch(() => t(!1));
  }, []), e;
}, f = new E();
let p = !1;
const M = (e) => new Error(`[Native Wechat]: (${e.errorCode}) ${e.errorStr}`), c = (e) => {
  if (!p)
    throw new Error(`Please register SDK before invoking ${e}`);
}, N = () => i(o.checkUniversalLinkReady)(), q = (e) => {
  p || (o.registerApp(e), p = !0);
  const n = new y(o).addListener("NativeWechat_Response", (r) => {
    const s = r.errorCode ? M(r) : null;
    f.dispatch(r.type, s, r);
  });
  return () => n.remove();
}, S = () => i(o.isWechatInstalled)(), C = (e = {
  scope: "snsapi_userinfo",
  state: ""
}) => {
  c("sendAuthRequest");
  const t = i(o.sendAuthRequest);
  return new Promise((n, r) => {
    t(e).catch(r), f.once("SendAuthResp", (s, a) => s ? r(s) : n(a));
  });
}, k = (e) => (c("shareText"), i(o.shareText)(e)), x = (e) => (c("shareImage"), i(o.shareImage)(e)), A = (e) => (c("shareVideo"), i(o.shareVideo)(e)), T = (e) => (c("shareWebpage"), i(o.shareWebpage)(e)), _ = (e) => (c("shareMiniProgram"), i(o.shareMiniProgram)(e)), L = (e) => {
  c("requestPayment");
  const t = i(o.requestPayment);
  return new Promise((n, r) => w(void 0, void 0, void 0, function* () {
    t(e).catch(r), f.once("PayResp", (s, a) => s ? r(s) : n(a));
  }));
}, Q = (e) => {
  c("requestSubscribeMessage");
  const t = i(o.requestSubscribeMessage);
  return e.scene = +e.scene, t(e);
}, V = (e) => (c("openCustomerService"), i(o.openCustomerService)(e)), $ = (e) => {
  c("launchMiniProgram"), e.miniProgramType = +e.miniProgramType;
  const t = i(o.launchMiniProgram);
  return f.once("WXLaunchMiniProgramResp", (n, r) => {
    var s;
    if (!n)
      return (s = e.onNavBack) === null || s === void 0 ? void 0 : s.call(e, r);
  }), t(e);
}, U = o.getConstants();
export {
  U as NativeWechatConstants,
  N as checkUniversalLinkReady,
  S as isWechatInstalled,
  $ as launchMiniProgram,
  V as openCustomerService,
  q as registerApp,
  L as requestPayment,
  Q as requestSubscribeMessage,
  C as sendAuthRequest,
  x as shareImage,
  _ as shareMiniProgram,
  k as shareText,
  A as shareVideo,
  T as shareWebpage,
  I as useWechatInstalled
};
