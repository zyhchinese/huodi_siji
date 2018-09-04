package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.ShuFengCheLieBiaoEntity;
import com.lxwls.hdsjd.ui.activity.ShuFengCheLieBiaoActivity;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by 赵英辉 on 2018/8/23.
 */

public class ShuFengCheLieBiaoAdapter extends RecyclerView.Adapter<ShuFengCheLieBiaoAdapter.ViewHolder> {

    private Context context;
    private List<ShuFengCheLieBiaoEntity.ResponseBean> list;
    private OnClickItem onClickItem;

    public ShuFengCheLieBiaoAdapter(Context context, List<ShuFengCheLieBiaoEntity.ResponseBean> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }

    @Override
    public ShuFengCheLieBiaoAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.shufengcheliebiao_item, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem!=null){
                    onClickItem.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ShuFengCheLieBiaoAdapter.ViewHolder holder, int position) {

        RequestOptions options=new RequestOptions()
                .error(R.mipmap.user_header_defult)
                .placeholder(R.mipmap.user_header_defult)
                .fitCenter();
        holder.tv_xingcheng.setText(list.get(position).getScity()+"一"+list.get(position).getEcity());
        if (list.get(position).getState()==0){
            holder.tv_status.setText("已取消");
        }else {
            holder.tv_status.setText("");
        }
        holder.tv_chechang.setText(list.get(position).getTotalvehicle()+"米/余:"+list.get(position).getAvailablevehicle()+"米");
        holder.tv_chufa_time.setText(list.get(position).getDeparturetime());
        Glide.with(context).load(Constant.BASE_URL+list.get(position).getFolder()+list.get(position).getAutoname()).apply(options).into(holder.cir_touxiang);
        holder.tv_name.setText(list.get(position).getContactname());
        holder.tv_createtime.setText(list.get(position).getCreatetime());


    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_xingcheng,tv_status,tv_chechang,tv_chufa_time,tv_name,tv_createtime;
        private CircleImageView cir_touxiang;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_xingcheng= (TextView) itemView.findViewById(R.id.tv_xingcheng);
            tv_status= (TextView) itemView.findViewById(R.id.tv_status);
            tv_chechang= (TextView) itemView.findViewById(R.id.tv_chechang);
            tv_chufa_time= (TextView) itemView.findViewById(R.id.tv_chufa_time);
            tv_name= (TextView) itemView.findViewById(R.id.tv_name);
            tv_createtime= (TextView) itemView.findViewById(R.id.tv_createtime);
            cir_touxiang= (CircleImageView) itemView.findViewById(R.id.cir_touxiang);
        }
    }
}
