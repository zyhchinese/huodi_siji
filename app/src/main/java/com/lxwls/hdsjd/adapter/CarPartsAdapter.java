package com.lxwls.hdsjd.adapter;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.ShopListBean;

import java.util.List;

public class CarPartsAdapter extends BaseQuickAdapter<ShopListBean, BaseViewHolder> {

    public CarPartsAdapter(List<ShopListBean> data) {
        super(R.layout.item_car_part, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ShopListBean item) {
        helper.setText(R.id.item_tx, item.getProname());
        String sb = Constant.BASE_URL + item.getFolder() + item.getAutoname();
        Glide.with(mContext.getApplicationContext())
                .load(sb)
                .into((ImageView) helper.getView(R.id.shopimg));

    }

    /**
     * 刷新数据
     *
     * @param valueList 新的数据列表
     */
    public void refreshData(List<ShopListBean> valueList) {
        this.mData = valueList;
        notifyDataSetChanged();
    }
//    @Override
//    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
//        mContext.startActivity(new Intent(mContext, GoodsDetialActivity.class));
//    }
}
