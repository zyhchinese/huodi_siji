package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.bean.SuYun_XiaoJianDan_EWaiXuQiu;

import java.util.List;

/**
 * Created by admin on 2018/3/15.（速运-搬家单-额外需求）
 */

public class SuYun_BanJiaDan_EWaiXuQiuAdapter extends RecyclerView.Adapter<SuYun_BanJiaDan_EWaiXuQiuAdapter.ViewHolder>{

    private Context context;
    private List<SuYun_XiaoJianDan_EWaiXuQiu> eWaiEntityList;
    private int a;
    public SuYun_BanJiaDan_EWaiXuQiuAdapter(Context context, List<SuYun_XiaoJianDan_EWaiXuQiu> eWaiEntityList, int a) {
        this.context=context;
        this.eWaiEntityList=eWaiEntityList;
        this.a=a;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.suyun_xiaojiandan_ewaixuqiu, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
//        if (a == -1){
//            holder.tv_fangshi.setTextColor(Color.parseColor("#B9B9B9"));
//            holder.tv_feiyong.setTextColor(Color.parseColor("#B9B9B9"));
//        }
        holder.tv_fangshi.setText(eWaiEntityList.get(position).getService_name());
        if (eWaiEntityList.get(position).getOwner_service_price() == 0.00){
            holder.tv_feiyong.setText("免费");
        }else {
            holder.tv_feiyong.setText(eWaiEntityList.get(position).getOwner_service_price()+"元");
        }
    }

    @Override
    public int getItemCount() {
        return eWaiEntityList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fangshi,tv_feiyong;
        public ViewHolder(View itemView) {
            super(itemView);
            tv_fangshi = (TextView) itemView.findViewById(R.id.tv_fangshi);
            tv_feiyong = (TextView) itemView.findViewById(R.id.tv_feiyong);
        }
    }
}
