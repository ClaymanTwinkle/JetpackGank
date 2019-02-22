package com.kesar.jetpackgank.ui.main.sub.welfare;

import android.content.Context;
import android.widget.Toast;

import com.kesar.jetpackgank.Constant;
import com.kesar.jetpackgank.Global;
import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.http.ApiCallBack;
import com.kesar.jetpackgank.http.ApiClient;
import com.kesar.jetpackgank.http.ApiResponse;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.base.SubscriptionViewModel;

import java.util.ArrayList;
import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

public class WelfareViewModel extends SubscriptionViewModel {

    private int page = 1;
    private int count = 20;
    private boolean hasMore = true;

    private MutableLiveData<List<Gank>> dataList;

    public LiveData<List<Gank>> getData() {
        if(dataList == null) {
            dataList = new MutableLiveData<>();
        }

        return dataList;
    }

    public void refresh() {
        page = 1;
        hasMore = true;
        dataList.setValue(new ArrayList<Gank>(0));
        loadMore();
    }

    public void loadMore() {
        final Context context = Global.getInstance().getContext();

        addSubscription(ApiClient.retrofit().loadData(Constant.WELFARE_TYPE, count, ++page), new ApiCallBack<ApiResponse<List<Gank>>>() {
            @Override
            public void onSuccess(ApiResponse<List<Gank>> data) {
                if (data.isError()) {
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                } else {
                    if (data.getResults() == null || data.getResults().isEmpty()) {
                        dataList.setValue(new ArrayList<Gank>(0));
                    } else {
                        List<Gank> preList = dataList.getValue();
                        if(preList == null) {
                            dataList.setValue(data.getResults());
                        } else {
                            preList.addAll(data.getResults());
                            dataList.setValue(preList);
                        }
                    }
                }
            }

            @Override
            public void onFailure(int code, String msg) {
                dataList.setValue(new ArrayList<Gank>(0));
                Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {

            }
        });
    }
}
