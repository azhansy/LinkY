package com.azhansy.linky.joke.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.joke.JokeAdapter;
import com.azhansy.linky.joke.JokeView;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.joke.bean.JokeBeanHead;
import com.azhansy.linky.joke.business.JokeBusiness;
import com.azhansy.linky.utils.ToastUtil;
import com.loopj.android.http.RequestParams;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokePresenterImpl extends RxBasePresenter implements JokePresenter {
    JokeBusiness jokeBusiness;

    public JokePresenterImpl() {
        jokeBusiness = new JokeBusiness();
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                .filter(event -> event instanceof JokeBeanHead)
                .subscribe(jokeBHead -> {
                    JokeView view = getActualUi();
                    JokeBeanHead jokeBeanHead = (JokeBeanHead) jokeBHead;
                    if (view != null/* && jokeBeanHead != null*/) {
                        if (!jokeBeanHead.isState()) {
                            view.getDataFailed("请求出错");
                            return;
                        }
                        view.addListData(jokeBeanHead);
                    }
                })
        );
    }

    @Override
    public void getData(int page) {
        RequestParams params = new RequestParams();
        params.put("page", String.valueOf(page));
        jokeBusiness.getJokeData(params);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
