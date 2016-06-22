package com.azhansy.linky.joke.presenter;

import com.azhansy.linky.base.LinkApplication;
import com.azhansy.linky.base.RxBasePresenter;
import com.azhansy.linky.joke.JokeAdapter;
import com.azhansy.linky.joke.JokeView;
import com.azhansy.linky.joke.bean.JokeBean;
import com.azhansy.linky.joke.bean.JokeBeanHead;
import com.azhansy.linky.joke.business.JokeBusiness;
import com.loopj.android.http.RequestParams;

/**
 * Created by SHU on 2016/6/22.
 */
public class JokePresenterImpl extends RxBasePresenter implements JokePresenter {
    JokeBusiness jokeBusiness;
    JokeAdapter adapter;

    public JokePresenterImpl(){
        jokeBusiness = new JokeBusiness();
        adapter = new JokeAdapter(getContext());
    }

    @Override
    public void getData() {
        mSubscriptions.add(LinkApplication.getInstance().getRxBus().toObserverable()
                            .filter(event -> event instanceof JokeBeanHead)
                            .subscribe(jokeBHead ->{
                                JokeView view = getActualUi();
                                JokeBeanHead jokeBeanHead = (JokeBeanHead) jokeBHead;
                                if (view != null && jokeBeanHead != null) {
                                    adapter.replaceAll(jokeBeanHead.getJokeBeanList());
                                    view.setAdapter(adapter);
                                }
                            })
        );
        RequestParams params = new RequestParams();
        params.put("page","1");
        jokeBusiness.getJokeData(params);
    }
}
