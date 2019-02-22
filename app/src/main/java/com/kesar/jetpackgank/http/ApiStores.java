package com.kesar.jetpackgank.http;

import com.kesar.jetpackgank.data.Gank;

import java.util.HashMap;
import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * ApiStores
 *
 * @author andy <br/>
 * create time: 2019/2/20 11:54
 */
public interface ApiStores {
    String API_SERVER_URL = "http://gank.io/api/";

    // 今天
    @GET("today")
    Observable<ApiResponse<HashMap<String, List<Gank>>>> getToday();

    // 随机数据
    @GET("random/data/{type}/{count}")
    Observable<ApiResponse<List<Gank>>> loadRandomData(@Path("type") String type, @Path("count") int count);

    // 某一天的数据
    @GET("day/{year}/{month}/{day}")
    Observable<ApiResponse<HashMap<String, List<Gank>>>> loadDateData(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    // 获取所有历史日期
    @GET("day/history")
    Observable<ApiResponse<List<String>>> loadAllHistoryDayList();

    // 获取某类数据
    @GET("data/{type}/{count}/{page}")
    Observable<ApiResponse<List<Gank>>> loadData(@Path("type") String type, @Path("count") int count, @Path("page") int page);
}
