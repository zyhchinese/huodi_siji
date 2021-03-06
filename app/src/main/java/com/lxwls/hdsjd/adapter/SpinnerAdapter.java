package com.lxwls.hdsjd.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.bean.ProvinceEntity;

import java.util.List;

/**
 * Created by Administrator on 2017/10/24.
 */

public class SpinnerAdapter extends BaseAdapter{
    private boolean isOk=true;
    private Context context;
    private List<ProvinceEntity> list;
    public SpinnerAdapter(Context context, List<ProvinceEntity> list) {
        this.context=context;
        this.list=list;

    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item,null);
        if (convertView!=null){
            TextView textView= (TextView) convertView.findViewById(R.id.tv_name);
//            if (isOk){
////                list.get(position).setAreaname("请选择省份");
//                textView.setText(list.get(position).getTishiname());
//                isOk=false;
//            }else {
////
                textView.setText(list.get(position).getAreaname());
//            }

        }
        return convertView;
    }
}
