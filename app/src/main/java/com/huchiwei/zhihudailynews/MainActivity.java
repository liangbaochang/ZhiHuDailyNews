package com.huchiwei.zhihudailynews;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.huchiwei.zhihudailynews.modules.news.api.NewsService;
import com.huchiwei.zhihudailynews.core.retrofit.RetrofitHelper;

import com.huchiwei.zhihudailynews.modules.news.entity.News4List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        NewsService newsService = RetrofitHelper.createApi(this, NewsService.class);
        newsService.findLastNews().enqueue(new Callback<News4List>() {
            @Override
            public void onResponse(Call<News4List> call, Response<News4List> response) {
                if(response.isSuccessful()){
                    News4List result = response.body();
                    Log.d(TAG, "最新日期: " + result.getDate());
                    Log.d(TAG, "当天新闻" + response.body().getStories().size() + "条");

                    if(null != result.getTop_stories())
                        Log.d(TAG, "推荐新闻" + result.getTop_stories().size() + "条");
                }

            }

            @Override
            public void onFailure(Call<News4List> call, Throwable t) {
                Log.e(TAG, "onFailure: 获取最新的消息出错了", t);
            }
        });
    }
}