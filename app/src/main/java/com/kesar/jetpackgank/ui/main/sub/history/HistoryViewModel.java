package com.kesar.jetpackgank.ui.main.sub.history;

import android.content.Context;

import com.kesar.jetpackgank.Global;
import com.kesar.jetpackgank.data.AppDatabase;
import com.kesar.jetpackgank.data.Gank;

import java.util.List;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

public class HistoryViewModel extends ViewModel {

    public LiveData<List<Gank>> getData() {
        Context context = Global.getInstance().getContext();
        return AppDatabase.getInstance(context).gankDao().getGanks();
    }

    public void refresh() {
    }
}
