package com.lxwls.hdsjd.ui.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.adapter.TiXIanStoryAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.fragment.BaseRecyclerAdapter;
import com.lxwls.hdsjd.base.fragment.BaseRecyclerViewFragment;
import com.lxwls.hdsjd.bean.ChargeOrder;
import com.lxwls.hdsjd.bean.TiXianStory;

import java.lang.reflect.Type;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * A simple {@link Fragment} subclass.
 */
public class TiXianStoryFragment extends BaseRecyclerViewFragment<TiXianStory> {

    @Override
    protected void initBundle(Bundle bundle) {
        super.initBundle(bundle);
    }
    @Override
    protected BaseRecyclerAdapter<TiXianStory> getRecyclerAdapter() {
        return new TiXIanStoryAdapter(getActivity(),BaseRecyclerAdapter.ONLY_FOOTER);
    }

    @Override
    protected Type getType() {
        return   new TypeToken<List<ChargeOrder>>() {
        }.getType();
    }
    @Override
    public void onRefreshing() {
        super.onRefreshing();

    }
    @Override
    protected void requestData() {
        super.requestData();
        PileApi.instance.getTiXianStory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mHandler);
    }

    @Override
    protected void onRequestFinish() {
        super.onRequestFinish();
    }

    @Override
    public void onItemClick(int position, long itemId) {
        super.onItemClick(position, itemId);
//        ChargeOrder chargeOrder = mAdapter.getItem(position);
//        if (chargeOrder == null) return;
//        OrderDetialActivity.show(getContext(),chargeOrder);

    }
    @Override
    protected boolean isNeedEmptyView() {
        return false;
    }

    @Override
    protected boolean isNeedCache() {
        return false;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

}
