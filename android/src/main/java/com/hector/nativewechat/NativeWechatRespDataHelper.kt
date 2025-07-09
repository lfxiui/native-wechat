package com.hector.nativewechat

import com.facebook.react.bridge.Arguments
import com.facebook.react.bridge.WritableMap
import com.tencent.mm.opensdk.constants.ConstantsAPI
import com.tencent.mm.opensdk.modelbase.BaseResp
import com.tencent.mm.opensdk.modelbiz.WXLaunchMiniProgram
import com.tencent.mm.opensdk.modelmsg.SendAuth

class NativeWechatRespDataHelper {
    companion object {
        private var type = ""

        fun downcastResp(baseResp: BaseResp): WritableMap {
            val argument = Arguments.createMap()

            // 首先根据响应类型设置事件类型，无论成功还是失败
            when {
                baseResp is SendAuth.Resp -> {
                    type = "SendAuthResp"
                }
                baseResp.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM -> {
                    type = "WXLaunchMiniProgramResp"
                }
                baseResp.type == ConstantsAPI.COMMAND_PAY_BY_WX -> {
                    type = "PayResp"
                }
            }

            // 只有成功时才填充详细数据
            if (baseResp.errCode == 0) {
                when {
                    baseResp is SendAuth.Resp -> {
                        val resp = baseResp

                        argument.putString("code", resp.code)
                        argument.putString("state", resp.state)
                        argument.putString("lang", resp.lang)
                        argument.putString("country", resp.country)
                    }

                    baseResp.type == ConstantsAPI.COMMAND_LAUNCH_WX_MINIPROGRAM -> {
                        val resp = baseResp as WXLaunchMiniProgram.Resp

                        argument.putString("extMsg", resp.extMsg)
                    }
                }
            }

            return wrapResponse(baseResp, argument)
        }

        private fun wrapResponse(baseResp: BaseResp, data: WritableMap): WritableMap {
            val argument = Arguments.createMap()

            argument.putString("type", type)
            argument.putInt("errorCode", baseResp.errCode)
            argument.putString("errorStr", baseResp.errStr)
            argument.putString("transaction", baseResp.transaction)
            argument.putMap("data", data)

            return argument
        }
    }
} 