package com.fenda.onn.http;

import com.fenda.onn.bean.WeatherBean;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author David-lvfujiang
 * @time 2019/12/26 15:18
 * desc
 */
public interface ApiService {
    @GET("data/sk/{cityId}.html")
    Observable<WeatherBean> getWeatherInfo(@Path("cityId") String cityId);
}
