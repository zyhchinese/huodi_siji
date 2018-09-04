package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.ShuFengCheXiangQingEntity;
import com.lxwls.hdsjd.ui.activity.ShuFengCheXiangQingActivity;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 赵英辉 on 2018/8/24.
 */

public class HuoZhuLieBiaoAdapter  extends RecyclerView.Adapter<HuoZhuLieBiaoAdapter.ViewHolder>{
    private Context context;
    private ShuFengCheXiangQingEntity entity;
    private OnClickCall onClickCall;

    public HuoZhuLieBiaoAdapter(Context context, ShuFengCheXiangQingEntity entity) {
        this.context=context;
        this.entity=entity;
    }

    public void setOnClickCall(OnClickCall onClickCall) {
        this.onClickCall = onClickCall;
    }

    public interface OnClickCall{
        void onClick(int position);
    }

    @Override
    public HuoZhuLieBiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.huozhuliebiao_item, parent, false);
        ViewHolder viewHolder=new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(HuoZhuLieBiaoAdapter.ViewHolder holder, final int position) {
        RequestOptions options=new RequestOptions()
                .placeholder(R.mipmap.user_header_defult)
                .error(R.mipmap.user_header_defult)
                .fitCenter();

        Glide.with(context).load(Constant.BASE_URL+entity.getResponse().get(0).getFreerideJoin().get(position).getFolder()+
                entity.getResponse().get(0).getFreerideJoin().get(position).getAutoname()).apply(options).into(holder.cir_touxiang);
        holder.tv_name.setText(entity.getResponse().get(0).getFreerideJoin().get(position).getCustname());
        holder.tv_phone.setText(entity.getResponse().get(0).getFreerideJoin().get(position).getConsignorphone());
        holder.tv_phone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (onClickCall!=null){
                    onClickCall.onClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return entity.getResponse().get(0).getFreerideJoin().size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CircleImageView cir_touxiang;
        private TextView tv_name,tv_phone;
        public ViewHolder(View itemView) {
            super(itemView);
            cir_touxiang= (CircleImageView) itemView.findViewById(R.id.cir_touxiang);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_phone= (TextView) itemView.findViewById(R.id.tv_phone);
        }
    }
}
