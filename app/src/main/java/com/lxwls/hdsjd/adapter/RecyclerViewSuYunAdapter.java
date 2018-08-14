package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.support.v4.util.CircularArray;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.base.Constant;
import com.lxwls.hdsjd.bean.AuditOrderEntity;
import com.lxwls.hdsjd.bean.AuditOrderEntity1;
import com.lxwls.hdsjd.bean.HuoDKuaiYun_liebiao;
import com.lxwls.hdsjd.bean.TongChengBanJiaDan;
import com.lxwls.hdsjd.bean.TongChengXiaoJianDan;

import java.text.DecimalFormat;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by admin on 2018/3/12.（首页-货滴速运-适配器）
 */
public class RecyclerViewSuYunAdapter extends RecyclerView.Adapter<RecyclerViewSuYunAdapter.ViewHolder>{

    private Context context;
    private List<TongChengXiaoJianDan.RowsBean> list;
    private List<TongChengBanJiaDan.RowsBean> list1;
    private int type;
    private OnClickChild onClickChild;
    private OnCallPhone onCallPhone;
    private RequestOptions mOptions;//请求
    private TongChengXiaoJianDan.RowsBean tongChengXiaoJianDan = new TongChengXiaoJianDan.RowsBean();
    private RequestOptions mOptions1;//请求
    private TongChengBanJiaDan.RowsBean tongChengBanJiaDan = new TongChengBanJiaDan.RowsBean();

    public RecyclerViewSuYunAdapter(Context context, List<TongChengXiaoJianDan.RowsBean> list, int type) {
        this.context=context;
        this.list=list;
        this.type=type;
    }
    public RecyclerViewSuYunAdapter(Context context, List<TongChengBanJiaDan.RowsBean> list, int type, int a) {
        this.context=context;
        this.list1=list;
        this.type=type;
    }

    public void setOnClickChild(OnClickChild onClickChild) {
        this.onClickChild = onClickChild;
    }

    public void setOnCallPhone(OnCallPhone onCallPhone) {
        this.onCallPhone = onCallPhone;
    }

    public interface OnClickChild{
        void onClick(int position);
    }
    public interface OnCallPhone{
        void onClick(int position);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ViewHolder viewHolder;
        if (type==0){
            View view = LayoutInflater.from(context).inflate(R.layout.suyun_tongchengxiaojiandan1, parent, false);
            viewHolder=new ViewHolder(view);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickChild!=null){
                        onClickChild.onClick(viewHolder.getAdapterPosition());
                    }
                }
            });
        }else {
            View view = LayoutInflater.from(context).inflate(R.layout.suyun_tongchengbanjiadan1, parent, false);
            viewHolder=new ViewHolder(view);
            viewHolder.view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onClickChild!=null){
                        onClickChild.onClick(viewHolder.getAdapterPosition());
                    }
                }
            });
        }

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
//        String substring = list.get(position).getCreatetime().substring(0, 10);

        if (type==0){
            //holder.tv_dingdanhao.setText(list.get(position).getOrderno());

            mOptions = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            tongChengXiaoJianDan = list.get(position);
            String userLogo = Constant.BASE_URL + tongChengXiaoJianDan.getFolder() + "/" + tongChengXiaoJianDan.getAutoname();
            Glide.with(context)
                    .load(userLogo)
                    .apply(mOptions)
                    .into(holder.mAvatar1);

            holder.tv_fhr.setText(list.get(position).getConsigneename());
            if (list.get(position).getCreatetime().length()>10){
                holder.fh_sj.setText(list.get(position).getCreatetime().substring(5,list.get(position).getCreatetime().length()-3));
            }else {
                holder.fh_sj.setText(list.get(position).getCreatetime());
            }

//            holder.tv_address.setText(list.get(position).getStartaddress());
//            holder.tv_address1.setText(list.get(position).getEndaddress());
//            holder.tv_quhuoshijian.setText(list.get(position).getPickuptime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(Double.parseDouble(list.get(position).getSiji_money()));
            holder.tv_zongjia.setText("¥" + format);
            holder.tv_juli.setText("起点距您："+list.get(position).getLicheng()+ "公里");//距离是什么距离

            String[] split = list.get(position).getSprocitcou().split(" ");
            if (split.length==3){
                if (split[1].length()>1){
                    holder.tv_cfd_shi.setText(split[1].substring(0,split[1].length()-1));
                }else {
                    holder.tv_cfd_shi.setText(split[1]);
                }
                holder.tv_cfd_qu.setText(split[2]);
            }
            String[] split1 = list.get(position).getEprocitcou().split(" ");
            if (split1.length==3){
                if (split1[1].length()>1){
                    holder.tv_mdd_shi.setText(split1[1].substring(0,split1.length-1));
                }else {
                    holder.tv_mdd_shi.setText(split1[1]);
                }

                holder.tv_mdd_qu.setText(split1[2]);
            }
            holder.tv_jiaoyiliang.setText("交易"+list.get(position).getCust_num()+"   "+list.get(position).getBeizhu());

            if (list.get(position).getCust_star().equals("0")){
                holder.img1.setVisibility(View.GONE);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list.get(position).getCust_star().equals("1")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list.get(position).getCust_star().equals("2")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list.get(position).getCust_star().equals("3")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list.get(position).getCust_star().equals("4")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.GONE);
            }else if (list.get(position).getCust_star().equals("5")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.VISIBLE);
            }

//            holder.tv_chakan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickChild!=null){
//                        onClickChild.onClick(holder.getAdapterPosition());
//                    }
//                }
//            });

            holder.iv_iphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCallPhone!=null){
                        onCallPhone.onClick(holder.getAdapterPosition());
                    }
                }
            });
           // if (list.get(position).getCust_orderstatus()==-1){
           //     holder.tv_zhuangtai.setText("已取消");
          //  }else if (list.get(position).getCust_orderstatus()==0){
           //     holder.tv_zhuangtai.setText("等待接货");
          //  }else if (list.get(position).getCust_orderstatus()==1){
          //      holder.tv_zhuangtai.setText("服务开始");
        //    }else if (list.get(position).getCust_orderstatus()==2){
          //      holder.tv_zhuangtai.setText("已完成");
         //   }
        //    holder.tv_name.setText(list.get(position).getWeightofgoods());
            //holder.tv_phone.setText("¥"+list.get(position).getOrdertotalprice());
            //holder.tv_number.setText(list.get(position).getCreatetime());
        }else {

            mOptions1 = new RequestOptions()
                    .placeholder(R.mipmap.touxiang)
                    .error(R.mipmap.touxiang)
                    .fitCenter();

            tongChengBanJiaDan = list1.get(position);
            String userLogo = Constant.BASE_URL + tongChengBanJiaDan.getFolder() + "/" + tongChengBanJiaDan.getAutoname();
            Glide.with(context)
                    .load(userLogo)
                    .apply(mOptions1)
                    .into(holder.mAvatar1);

            holder.tv_fhr.setText(list1.get(position).getOwner_link_name());
            if (list1.get(position).getOwner_createtime().length()>10){
                holder.fh_sj.setText(list1.get(position).getOwner_createtime().substring(5,list1.get(position).getOwner_createtime().length()-3));
            }else {
                holder.fh_sj.setText(list1.get(position).getOwner_createtime());
            }

//            holder.tv_address.setText(list1.get(position).getOwner_address());
//            holder.tv_address1.setText(list1.get(position).getOwner_send_address());
//            holder.tv_chexing.setText(list1.get(position).getCar_type());
//            holder.tv_quhuoshijian.setText(list1.get(position).getOwner_sendtime());
            DecimalFormat decimalFormat=new DecimalFormat("###0.00");
            String format = decimalFormat.format(Double.parseDouble(list1.get(position).getSiji_money()));
            holder.tv_zongjia.setText("¥" + format);
            holder.tv_juli.setText("起点距您："+list1.get(position).getLicheng() + "公里");//距离是什么距离

            if (list1.get(position).getOwner_address().length()>6){
                holder.tv_cfd_qu.setText(list1.get(position).getOwner_address().substring(0,6));
            }else {
                holder.tv_cfd_qu.setText(list1.get(position).getOwner_address());
            }
            if (list1.get(position).getOwner_send_address().length()>6){
                holder.tv_mdd_shi.setText(list1.get(position).getOwner_send_address().substring(0,6));
            }else {
                holder.tv_mdd_shi.setText(list1.get(position).getOwner_send_address());
            }

            holder.tv_jiaoyiliang.setText("交易"+list1.get(position).getCust_num()+"   "+list1.get(position).getBeizhu());

            if (list1.get(position).getCust_star().equals("0")){
                holder.img1.setVisibility(View.GONE);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list1.get(position).getCust_star().equals("1")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.GONE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list1.get(position).getCust_star().equals("2")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.GONE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list1.get(position).getCust_star().equals("3")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.GONE);
                holder.img5.setVisibility(View.GONE);
            }else if (list1.get(position).getCust_star().equals("4")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.GONE);
            }else if (list1.get(position).getCust_star().equals("5")){
                holder.img1.setVisibility(View.VISIBLE);
                holder.img2.setVisibility(View.VISIBLE);
                holder.img3.setVisibility(View.VISIBLE);
                holder.img4.setVisibility(View.VISIBLE);
                holder.img5.setVisibility(View.VISIBLE);
            }

//            holder.tv_chakan.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (onClickChild!=null){
//                        onClickChild.onClick(holder.getAdapterPosition());
//                    }
//                }
//            });
            holder.iv_iphone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (onCallPhone!=null){
                        onCallPhone.onClick(holder.getAdapterPosition());
                    }
                }
            });
           // holder.tv_dingdanhao.setText(list1.get(position).getOwner_orderno());
           // if (list1.get(position).getCust_orderstatus()==-1){
          //      holder.tv_zhuangtai.setText("已取消");
          //  }else if (list1.get(position).getCust_orderstatus()==0){
          //      holder.tv_zhuangtai.setText("等待接货");
         //   }else if (list1.get(position).getCust_orderstatus()==1){
        //        holder.tv_zhuangtai.setText("服务开始");
        //    }else if (list1.get(position).getCust_orderstatus()==2){
        //        holder.tv_zhuangtai.setText("已完成");
       //
      //  }

//        holder.tv_time.setText(substring);
//        holder.tv_money.setText((int)(list.get(position).getOrder_money())+"元");
//        if (type==0){
//            holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao);
//        }else{
//            if (list.get(position).getOrderstatus()==1){
//                holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao1);
//            }else {
//                holder.img_zuo.setImageResource(R.mipmap.img_zuoshangjiao_f1);
//            }
//
//        }
      //  holder.tv_dingdanhao.setOnClickListener(new View.OnClickListener() {
     //       @Override
      //      public void onClick(View v) {
    //            if (onClickChild!=null){
    //                onClickChild.onClick(holder.getAdapterPosition());
   //             }
    //        }
        };
    }
    @Override
    public int getItemCount() {
        if (type==0){
            return list.size();
        }else {
            return list1.size();
        }
    }
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_fhr,fh_sj,tv_address,tv_address1,tv_chexing,
                tv_quhuoshijian,tv_zongjia,tv_juli,tv_chakan;
        private ImageView iv_iphone,jiantou,img_zuo;
        private CircleImageView mAvatar;
        private ImageView mAvatar1;
        private TextView tv_cfd_shi,tv_cfd_qu,tv_mdd_shi,tv_mdd_qu,tv_jiaoyiliang;
        private View view;
        private ImageView img1,img2,img3,img4,img5;
        public ViewHolder(View itemView) {
            super(itemView);
            view=itemView;
            if (type==0){
                mAvatar1 = (ImageView) itemView.findViewById(R.id.img_avatar);//头像
                tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);//发货人的名字
                fh_sj= (TextView) itemView.findViewById(R.id.fh_sj1);//发货时间
                iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);//电话
//                tv_address= (TextView) itemView.findViewById(R.id.tv_address);//出发地
//                tv_address1= (TextView) itemView.findViewById(R.id.tv_address1);//目的地
//                tv_chexing= (TextView) itemView.findViewById(R.id.tv_chexing);//车型
//                tv_quhuoshijian= (TextView) itemView.findViewById(R.id.tv_quhuoshijian);//取货时间
                tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);//总价
                tv_juli= (TextView) itemView.findViewById(R.id.tv_juli);//距离
//                tv_chakan= (TextView) itemView.findViewById(R.id.tv_chakan);//查看详情
//            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
//            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
//            jiantou= (ImageView) itemView.findViewById(R.id.jiantou);
//            img_zuo= (ImageView) itemView.findViewById(R.id.img_zuo);

                tv_cfd_shi= (TextView) itemView.findViewById(R.id.tv_cfd_shi);
                tv_cfd_qu= (TextView) itemView.findViewById(R.id.tv_cfd_qu);
                tv_mdd_shi= (TextView) itemView.findViewById(R.id.tv_mdd_shi);
                tv_mdd_qu= (TextView) itemView.findViewById(R.id.tv_mdd_qu);
                tv_jiaoyiliang= (TextView) itemView.findViewById(R.id.tv_jiaoyiliang);
                img1= (ImageView) itemView.findViewById(R.id.img1);
                img2= (ImageView) itemView.findViewById(R.id.img2);
                img3= (ImageView) itemView.findViewById(R.id.img3);
                img4= (ImageView) itemView.findViewById(R.id.img4);
                img5= (ImageView) itemView.findViewById(R.id.img5);
            }else {
                mAvatar1 = (ImageView) itemView.findViewById(R.id.img_avatar);//头像
                tv_fhr= (TextView) itemView.findViewById(R.id.tv_fhr);//发货人的名字
                fh_sj= (TextView) itemView.findViewById(R.id.fh_sj1);//发货时间
                iv_iphone= (ImageView) itemView.findViewById(R.id.iv_iphone);//电话
//                tv_address= (TextView) itemView.findViewById(R.id.tv_address);//出发地
//                tv_address1= (TextView) itemView.findViewById(R.id.tv_address1);//目的地
//                tv_chexing= (TextView) itemView.findViewById(R.id.tv_chexing);//车型
//                tv_quhuoshijian= (TextView) itemView.findViewById(R.id.tv_quhuoshijian);//取货时间
                tv_zongjia= (TextView) itemView.findViewById(R.id.tv_zongjia);//总价
                tv_juli= (TextView) itemView.findViewById(R.id.tv_juli);//距离
//                tv_chakan= (TextView) itemView.findViewById(R.id.tv_chakan);//查看详情
//            tv_time= (TextView) itemView.findViewById(R.id.tv_time);
//            tv_money= (TextView) itemView.findViewById(R.id.tv_money);
//            jiantou= (ImageView) itemView.findViewById(R.id.jiantou);
//            img_zuo= (ImageView) itemView.findViewById(R.id.img_zuo);

                tv_cfd_shi= (TextView) itemView.findViewById(R.id.tv_cfd_shi);
                tv_cfd_qu= (TextView) itemView.findViewById(R.id.tv_cfd_qu);
                tv_mdd_shi= (TextView) itemView.findViewById(R.id.tv_mdd_shi);
                tv_mdd_qu= (TextView) itemView.findViewById(R.id.tv_mdd_qu);
                tv_jiaoyiliang= (TextView) itemView.findViewById(R.id.tv_jiaoyiliang);
                img1= (ImageView) itemView.findViewById(R.id.img1);
                img2= (ImageView) itemView.findViewById(R.id.img2);
                img3= (ImageView) itemView.findViewById(R.id.img3);
                img4= (ImageView) itemView.findViewById(R.id.img4);
                img5= (ImageView) itemView.findViewById(R.id.img5);
            }

        }
    }
}
