package com.libs.utils.systemUtils.exceptionUtil;


import com.libs.utils.logUtils.LogUtil;

/**
 * @ author：mo
 * @ data：2018/1/19 0019
 * @ 功能： 异常工具类
 */

public class ExceptionUtil {
    private ExceptionUtil() {
        throw getUnsupportedOperationException();
    }

    public static UnsupportedOperationException getUnsupportedOperationException() {
        return new UnsupportedOperationException("实例化失败");
    }

    /**
     * 通过exceptionCode 判断错误类型 （只限于网络异常，且不保准，这个只是通用的，后台不一定按照这个反回）
     *
     * @param code
     */
    public static void tips(int code) {
        switch (code) {
            case 400:
                LogUtil.i("(错误请求)服务器不理解请求的语法");
                break;
            case 401:
                LogUtil.i("(未授权)请求要求身份验证。对于登录后请求的网页，服务器可能返回此响应");
                break;
            case 402:
                LogUtil.i("保留有效ChargeTo头响应、或者session过期");
                break;
            case 403:
                LogUtil.i("禁止访问，服务器收到请求，但是拒绝提供服务，可能是没有权限等原因");
                break;
            case 404:
                LogUtil.i("可连接服务器，但服务器无法取得所请求的网页，请求资源不存在。eg：输入了错误的URL");
                break;
            case 405:
                LogUtil.i("(方法禁用)禁用请求中指定的方法，切换POST或GET请求");
                break;
            case 406:
                LogUtil.i("(不接受)无法使用请求的内容特性响应请求的网页");
                break;
            case 407:
                LogUtil.i("类似401，用户必须首先在代理服务器上得到授权");
                break;
            case 408:
                LogUtil.i("(请求超时)服务器等候请求时发生超时");
                break;
            case 409:
                LogUtil.i("对当前资源状态，请求不能完成、(冲突)服务器在完成请求时发生冲突");
                break;
            case 410:
                LogUtil.i("(已删除)如果请求的资源已永久删除，服务器就会返回此响应");
                break;
            case 411:
                LogUtil.i("服务器拒绝用户定义的Content-Length属性请求or(需要有效长度)服务器不接受不含有效内容长度标头字段的请求");
                break;
            case 412:
                LogUtil.i("(未满足前提条件)服务器未满足请求者在请求中设置的其中一个前提条件 or一个或多个请求头字段在当前请求中错误");
                break;
            case 413:
                LogUtil.i("请求的资源大于服务器允许的大小");
                break;
            case 414:
                LogUtil.i("(请求的 URI 过长)请求的 URI(通常为网址)过长，服务器无法处理");
                break;
            case 415:
                LogUtil.i("请求资源不支持请求项目格式");
                break;
            case 416:
                LogUtil.i("请求范围不符合要求)页面无法提供请求的范围");
                break;
            case 417:
                LogUtil.i("(未满足期望值)服务器未满足”期望”请求标头字段的要求");
                break;
            case 500:
                LogUtil.i("(服务器内部错误)服务器遇到错误，无法完成请求");
                break;
            case 501:
                LogUtil.i("(尚未实施，接口没写)服务器不具备完成请求的功能。例如，服务器无法识别请求方法时可能会返回此代码");
                break;
            case 502:
                LogUtil.i("(错误网关)服务器作为网关或代理，从上游服务器收到无效响应");
                break;
            case 503:
                LogUtil.i("(服务不可用)服务器目前无法使用(由于超载或停机维护)。通常，这只是暂时状态");
                break;
            case 504:
                LogUtil.i("(网关超时)服务器作为网关或代理，但是没有及时从上游服务器收到请求");
                break;
            case 505:
                LogUtil.i("(HTTP 版本不受支持)服务器不支持请求中所用的 HTTP 协议版本");
                break;
            default:
                break;
        }
    }
}
