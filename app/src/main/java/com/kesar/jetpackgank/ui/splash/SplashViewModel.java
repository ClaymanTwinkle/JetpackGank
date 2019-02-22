package com.kesar.jetpackgank.ui.splash;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.widget.Toast;

import com.kesar.jetpackgank.Global;
import com.kesar.jetpackgank.R;
import com.kesar.jetpackgank.http.ApiCallBack;
import com.kesar.jetpackgank.http.ApiClient;
import com.kesar.jetpackgank.http.ApiResponse;
import com.kesar.jetpackgank.data.Gank;
import com.kesar.jetpackgank.base.SubscriptionViewModel;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import static com.kesar.jetpackgank.Constant.WELFARE_TYPE;

public class SplashViewModel extends SubscriptionViewModel {

    private final static String KEY_SPLASH_CACHE_IMAGE_URL = "splash_cache_image_url";

    private MutableLiveData<String> mImageUrl;

    public LiveData<String> getImageUrl() {
        if (mImageUrl == null) {
            mImageUrl = new MutableLiveData<>();
            loadData();
        }
        return mImageUrl;
    }

    private void loadData() {
        final Context context = Global.getInstance().getContext();
        final SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        String cacheImageUrl = sharedPreferences.getString(KEY_SPLASH_CACHE_IMAGE_URL, "");
        if (TextUtils.isEmpty(cacheImageUrl)) {
            // 没有，读网络
            addSubscription(ApiClient.retrofit().loadRandomData(WELFARE_TYPE, 1), new ApiCallBack<ApiResponse<List<Gank>>>() {
                @Override
                public void onSuccess(ApiResponse<List<Gank>> data) {
                    if (data.isError()) {
                        Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                    } else {
                        if (data.getResults() == null || data.getResults().isEmpty()) {
                            mImageUrl.postValue("");
                        } else {
                            String cacheImageUrl = data.getResults().get(0).getUrl();
                            if (TextUtils.isEmpty(cacheImageUrl)) {
                                mImageUrl.postValue("");
                            } else {
                                sharedPreferences.edit().putString(KEY_SPLASH_CACHE_IMAGE_URL, cacheImageUrl).apply();
                                mImageUrl.postValue(cacheImageUrl);
                            }
                        }
                    }
                }

                @Override
                public void onFailure(int code, String msg) {
                    mImageUrl.postValue("");
                    Toast.makeText(context, R.string.server_error, Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFinish() {

                }
            });
        } else {
            sharedPreferences.edit().remove(KEY_SPLASH_CACHE_IMAGE_URL).apply();
            // 有，直接读
            mImageUrl.postValue(cacheImageUrl);
        }
    }
}
