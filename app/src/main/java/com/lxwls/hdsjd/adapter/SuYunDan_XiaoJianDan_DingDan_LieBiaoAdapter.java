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
import com.lxwls.hdsjd.bean.AuditOrderEntity;
import com.lxwls.hdsjd.bean.HuoDKuaiYun_liebiao;
import com.lxwls.hdsjd.bean.TongChengBanJiaDan;
import com.lxwls.hdsjd.bean.TongChengXiaoJianDan;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/3/15.（速运-订单-列表 适配器）
 */

public class SuYunDan_XiaoJianDan_DingDan_LieBiaoAdapter extends RecyclerView.Adapter<SuYunDan_XiaoJianDan_DingDan_LieBiaoAdapter.ViewHolder>{

    private Context context;
    private List<AuditOrderEntity> list;
    private List<TongChengBanJiaDan> list1;
    private OnClickItem onClickItem;
    private OnClickItemHang onClickItemHang;
    private RequestOptions mOptions;//请求
    private AuditOrderEntity tongChengXiaoJianDan = new AuditOrderEntity();
    private RequestOptions mOptions1;//请求
    private TongChengBanJiaDan tongChengBanJiaDan = new TongChengBanJiaDan();

    public void setOnClickItemHang(OnClickItemHang onClickItemHang) {
        this.onClickItemHang = onClickItemHang;
    }

    public SuYunDan_XiaoJianDan_DingDan_LieBiaoAdapter(Context context, List<AuditOrderEntity> list) {
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

        View view = LayoutInflater.from(context).inflate(R.layout.suyun_xiaojiandan_dingdanliebiao1, parent, false);
        final ViewHolder viewHolder = new ViewHolder(view);
        viewHolder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onClickItemHang != null) {
                    onClickItemHang.onClick(viewHolder.getAdapterPosition());
                }
            }
        });
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        mOptions = new RequestOptions()
                .placeholder(R.mipmap.touxiang)
                .error(R.mipmap.touxiang)
                .fitCenter();

        tongChengXiaoJianDan = list.get(position);
        String userLogo = Constant.BASE_URL + tongChengXiaoJianDan.getFolder() + "/" + tongChengXiaoJianDan.getAutoname();
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
        holder.tv_fhr.setText(list.get(position).getConsigneename());
        holder.fh_sj.setText(list.get(position).getCreatetime());
        holder.tv_address.setText(list.get(position).getStartaddress());
        holder.tv_address1.setText(list.get(position).getEndaddress());
        holder.tv_quhuoshijian.setText(list.get(position).getPickuptime());
        DecimalFormat decimalFormat=new DecimalFormat("###0.00");
        String format = decimalFormat.format(Double.parseDouble(list.get(position).getSiji_money()));
        holder.tv_zongjia.setText("¥" + format);
        holder.tv_dingdanhao.setText(list.get(position).getOrderno());

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
        private TextView tv_fhr,fh_sj,tv_address,tv_address1,
                tv_quhuoshijian,tv_zongjia,tv_dingdanhao,tv_zhuangtai;
        private ImageView iv_iphone,jiantou,img_zuo;
        private LinearLayout ll_dingdan;
        private CircleImageView mAvatar;
        private View view;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            ll_dingdan = (LinearLayout) itemView.findViewById(R.id.ll_dingdan);//
            mAvatar = (CircleImageView) itemView.findViewById(R.id.img_avatar);//头像
            tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);//发货人的名字
            fh_sj= (TextView) itemView.findViewById(R.id.fh_sj);//发货时间
            iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);//电话
            tv_address= (TextView) itemView.findViewById(R.id.tv_address);//出发地
            tv_address1= (TextView) itemView.findViewById(R.id.tv_address1);//目的地
            tv_dingdanhao= (TextView) itemView.findViewById(R.id.tv_dingdanhao);//订单号
            tv_quhuoshijian= (TextView) itemView.findViewById(R.id.tv_quhuoshijian);//取货时间
            tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);//总价
            tv_zhuangtai= (TextView) itemView.findViewById(R.id.tv_zhuangtai);
        }
    }
}

