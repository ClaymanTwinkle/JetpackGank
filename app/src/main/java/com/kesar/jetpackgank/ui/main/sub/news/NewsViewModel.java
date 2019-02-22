package com.kesar.jetpackgank.ui.main.sub.news;

import android.content.Context;
import android.widget.Toast;

import com.kesar.jetpackgank.Global;
import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.base.SubscriptionViewModel;
import com.kesar.jetpackgank.data.AppDatabase;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.http.ApiCallBack;
import com.kesar.jetpackgank.http.ApiClient;
import com.kesar.jetpackgank.http.ApiResponse;

import java.util.HashMap;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class NewsViewModel extends SubscriptionViewModel {
    private MutableLiveData<HashMap<String, List<Gank>>> mDataList;

    public LiveData<HashMap<String, List<Gank>>> getDataList() {
        if(mDataList == null) {
            mDataList = new MutableLiveData<>();
        }

        return mDataList;
    }

    public void refresh() {
        final Context context = Global.getInstance().getContext();
        addSubscription(ApiClient.retrofit().getToday(), new ApiCallBack<ApiResponse<HashMap<String, List<Gank>>>>() {
            @Override
            public void onSuccess(ApiResponse<HashMap<String, List<Gank>>> data) {
                if (data.isError()) {
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                } else {
                    mDataList.setValue(data.getResults());
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                mDataList.setValue(new HashMap<String, List<Gank>>(0));
            }

            @Override
            public void onFinish() {

            }
        });
    }

    public void saveHistory(final Gank gank) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                final Context context = Global.getInstance().getContext();
                AppDatabase.getInstance(context).gankDao().insert(gank);
            }
        }).start();
    }
}
