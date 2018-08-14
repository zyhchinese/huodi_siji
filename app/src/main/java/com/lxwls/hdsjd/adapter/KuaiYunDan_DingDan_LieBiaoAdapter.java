package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.HuoDKuaiYun_liebiao;
import com.lxwls.hdsjd.bean.KuaiYunEntity;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/3/14.（快运订单列表 适配器）
 */

public class KuaiYunDan_DingDan_LieBiaoAdapter extends RecyclerView.Adapter<KuaiYunDan_DingDan_LieBiaoAdapter.ViewHolder>{

    private Context context;
    private List<KuaiYunEntity> list;
    private OnClickItem onClickItem;
    private OnClickItemHang onClickItemHang;
    private RequestOptions mOptions;//请求
    private KuaiYunEntity huoDKuaiYun_liebiao = new KuaiYunEntity();

    public void setOnClickItemHang(OnClickItemHang onClickItemHang) {
        this.onClickItemHang = onClickItemHang;
    }

    public KuaiYunDan_DingDan_LieBiaoAdapter(Context context, List<KuaiYunEntity> list) {
        this.context=context;
        this.list=list;
    }

    public void setOnClickItem(OnClickItem onClickItem) {
        this.onClickItem = onClickItem;
    }

    public interface OnClickItem{
        void onClick(int position);
    }
    public interface OnClickItemHang{
        void onClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.kuaiyun_dingdanleibiao1, parent, false);
        final ViewHolder viewHolder=new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemHang!=null){
                    onClickItemHang.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        //String userLogo = Constant.BASE_URL + user.getFolder() + "/" + user.getAutoname();
        //Glide.with(context)
        // .load(userLogo)
        // .apply(mOptions)
        // .into(img_avatar);
        //holder.tv_car_name.setText(list.get(position).getCar_name());
        //Glide.with(context).load(Constant.BASE_URL+list.get(position).getFolder()+list.get(position).getAutoname()).into(holder.img_car);

        mOptions = new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .fitCenter();

        huoDKuaiYun_liebiao = list.get(position);
        String userLogo = Constant.BASE_URL + huoDKuaiYun_liebiao.getFolder() + "/" + huoDKuaiYun_liebiao.getAutoname();
        Glide.with(context)
                .load(userLogo)
                .apply(mOptions)
                .into(holder.mAvatar);

        if (list.get(position).getDriver_orderstatus().equals("-1")){
            holder.tv_zhuangtai.setText("已取消");
        }else if (list.get(position).getDriver_orderstatus().equals("0")){
            holder.tv_zhuangtai.setText("前往中");
        }else if (list.get(position).getDriver_orderstatus().equals("1")){
            holder.tv_zhuangtai.setText("开始订单");
        }else if (list.get(position).getDriver_orderstatus().equals("2")){
            holder.tv_zhuangtai.setText("已完成");
        }
        holder.tv_dingdanhao.setText(list.get(position).getOrderno());
        holder.tv_fhr.setText(list.get(position).getContactname());
        holder.fh_sj.setText(list.get(position).getCreatetime());
        holder.tv_cfd_shi.setText(list.get(position).getScity());
        holder.tv_cfd_qu.setText(list.get(position).getScounty());
        holder.tv_mdd_shi.setText(list.get(position).getEcity());
        holder.tv_mdd_qu.setText(list.get(position).getEcounty());
        holder.tv_huowuleixing.setText(list.get(position).getCargotypenames());
        holder.tv_cheliangleixing.setText(list.get(position).getCartypenames());
        if (list.get(position).getWeight().equals("0")){
            holder.tv_zhongliang.setText(list.get(position).getVolume() + "件");
        }else if (list.get(position).getVolume().equals("0")){
            holder.tv_zhongliang.setText(list.get(position).getWeight() + "kg");
        }else {
            holder.tv_zhongliang.setText(list.get(position).getWeight() + "kg/"+list.get(position).getVolume() + "件");
        }

        holder.tv_cheliangleixing.setText(list.get(position).getCartypenames());
        DecimalFormat decimalFormat=new DecimalFormat("###0.00");
        String format = decimalFormat.format(Double.parseDouble(list.get(position).getSiji_money()));
        holder.tv_zongjia.setText("¥" + format);
        holder.tv_zhuangcheshijian.setText(list.get(position).getShipmenttime());

        holder.ll_dingdan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItem != null) {
                    onClickItem.onClick(holder.getAdapterPosition());
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_dingdanhao,tv_zhuangtai,tv_cfd_shi,tv_cfd_qu,tv_mdd_shi,tv_mdd_qu,tv_fhr,fh_sj,tv_huowuleixing,
                tv_cheliangleixing,tv_zhongliang,tv_zongjia,tv_zhuangcheshijian;
        private ImageView iv_iphone;
        private CircleImageView mAvatar;
        private LinearLayout ll_dingdan;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            tv_zhuangcheshijian = (TextView) itemView.findViewById(R.id.tv_zhuangcheshijian);//装车时间
            ll_dingdan = (LinearLayout) itemView.findViewById(R.id.ll_dingdan);//
            mAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);//头像
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);//发货人的名字
            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);//订单号
            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);//状态
            fh_sj= (TextView) itemView.findViewById(R.id.fh_sj);//发货时间
            iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);//电话
            tv_cfd_shi= (TextView) itemView.findViewById(R.id.tv_cfd_shi);//出发地市
            tv_cfd_qu= (TextView) itemView.findViewById(R.id.tv_cfd_qu);//出发地区
            tv_mdd_shi= (TextView) itemView.findViewById(R.id.tv_mdd_shi);//目的地市
            tv_mdd_qu= (TextView) itemView.findViewById(R.id.tv_mdd_qu);//目的地区
            tv_huowuleixing= (TextView) itemView.findViewById(R.id.tv_huowuleixing);//货物类型
            tv_cheliangleixing= (TextView) itemView.findViewById(R.id.tv_cheliangleixing);//车辆类型
            tv_zhongliang= (TextView) itemView.findViewById(R.id.tv_zhongliang);//重量
            tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);//总价
        }
    }
}
