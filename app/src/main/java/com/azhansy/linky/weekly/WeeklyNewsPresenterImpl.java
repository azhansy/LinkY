package com.azhansy.linky.weekly;

import com.azhansy.linky.base.MVP.MVPBasePresenter;
import com.azhansy.linky.utils.JsoupParseUtil;
import com.azhansy.linky.utils.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by SHU on 2016/7/13.
 */
public class WeeklyNewsPresenterImpl extends MVPBasePresenter implements WeeklyNewsPresenter  {
    private int page;
    private Call<ResponseBody> call;
    private List<WeeklyModel> weeklyModelList = new ArrayList<>();
    @Override
    public void getNews() {
        this.page = 1;
        getWeekly();
    }

    private void getWeekly(){
        call = WeeklyService.getInstance().api.getWeeklyNews(page);
        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    WeeklyView view = getActualUi();
//                    view.LoadHtmlSuccess(response.body().string());
                    if (page != 1) {
                        for (WeeklyModel item : JsoupParseUtil.JsoupWeeklyParse(response.body().string())) {
                            weeklyModelList.add(item);
                        }
                    }else {
                        weeklyModelList =  JsoupParseUtil.JsoupWeeklyParse(response.body().string());
                    }
                    view.LoadHtmlSuccess(weeklyModelList);
                    Logger.d(response.body().string());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                WeeklyView view = getActualUi();
                view.LoadHtmlFailed(t.toString());
                Logger.d(t.toString());
            }
        });
    }

    @Override
    public void getNewsDetailForUrl(String url) {
        Call<ResponseBody> responseBodyCall = WeeklyService.getInstance().api.getWeeklyNewsDetail(url);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                try {
                    WeeklyView view = getActualUi();
                    WeeklyModel model = JsoupParseUtil.getWeeklyModel(response.body().string());
                    view.LoadHtmlSuccess(model);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                WeeklyView view = getActualUi();
                view.LoadHtmlFailed("请求失败");
            }
        });
    }
    @Override
    public void onRefresh() {
        if (call != null) {
            call.cancel();
        }
        page = 1;
        getWeekly();
    }

    @Override
    public void onLoad() {
        page++;
        getWeekly();
    }
}
