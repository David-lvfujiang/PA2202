package com.fenda.onn.http.exception;

import android.net.ParseException;
import com.google.gson.JsonParseException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import javax.net.ssl.SSLHandshakeException;
import org.json.JSONException;
import retrofit2.adapter.rxjava2.HttpException;

/**
 * Date : 2020/3/4
 * Author : Davaid.lvfujiang
 * Desc : 异常统一处理类
 */
public class RetrofitException {
    private static final int UNAUTHORIZED = 401;
    private static final int FORBIDDEN = 403;
    private static final int NOT_FOUND = 404;
    private static final int REQUEST_TIMEOUT = 408;
    private static final int INTERNAL_SERVER_ERROR = 500;
    private static final int BAD_GATEWAY = 502;
    private static final int SERVICE_UNAVAILABLE = 503;
    private static final int GATEWAY_TIMEOUT = 504;

    public static ResponeThrowable retrofitException(Throwable e) {
        ResponeThrowable ex;
        if (e instanceof HttpException) {
            HttpException httpException = (HttpException) e;
            ex = new ResponeThrowable(e, ERROR.HTTP_ERROR);
            switch (httpException.code()) {
                case UNAUTHORIZED:
                case FORBIDDEN:
                case NOT_FOUND:
                case REQUEST_TIMEOUT:
                case GATEWAY_TIMEOUT:
                case INTERNAL_SERVER_ERROR:
                case BAD_GATEWAY:
                case SERVICE_UNAVAILABLE:
                default:
                    ex.message = "网络错误";
                    break;
            }
            return ex;
        } else if ((e instanceof JsonParseException)
                || (e instanceof JSONException)
                || (e instanceof ParseException)) {
            ex = new ResponeThrowable(e, ERROR.PARSE_ERROR);
            ex.message = "解析错误";
            return ex;
        } else if (e instanceof ConnectException
                || e instanceof SocketTimeoutException
                || e instanceof UnknownHostException) {
            ex = new ResponeThrowable(e, ERROR.NETWORD_ERROR);
            ex.message = "请求失败";
            return ex;
        } else if (e instanceof SSLHandshakeException) {
            ex = new ResponeThrowable(e, ERROR.SSL_ERROR);
            ex.message = "证书验证失败";
            return ex;
        } else {
            ex = new ResponeThrowable(e, ERROR.UNKNOWN);
            ex.message = "未知错误";
            return ex;
        }
    }

    /**
     * 约定异常
     */
    public interface ERROR {
        /**
         * 未知错误
         */
         int UNKNOWN = 1000;
        /**
         * 解析错误
         */
         int PARSE_ERROR = 1001;
        /**
         * 网络错误
         */
         int NETWORD_ERROR = 1002;
        /**
         * 协议出错
         */
         int HTTP_ERROR = 1003;
        /**
         * 证书出错
         */
         int SSL_ERROR = 1005;
    }

    public static class ResponeThrowable extends Exception {
        public int code;
        public String message;

        public ResponeThrowable(Throwable throwable, int code) {
            super(throwable);
            this.code = code;
        }

        @Override public String toString() {
            return "ResponeThrowable{" +
                    "code=" + code +
                    ", message='" + message + '\'' +
                    '}';
        }
    }
}
