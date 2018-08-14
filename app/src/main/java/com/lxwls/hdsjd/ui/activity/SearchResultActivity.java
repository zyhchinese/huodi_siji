package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.SearchResultAdapter;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.AuditOrderEntity;
import com.lxwls.hdsjd.bean.AuditOrderEntity1;
import com.lxwls.hdsjd.bean.HuoDiSearch;
import com.lxwls.hdsjd.bean.HuoDiSearch1;
import com.lxwls.hdsjd.bean.HuoDiSearch2;
import com.lxwls.hdsjd.bean.KuaiYunEntity;

import java.util.List;

public class SearchResultActivity extends BackCommonActivity {

    private RecyclerView act_recyclerView;
    private List<AuditOrderEntity> huoDiSearchList;
    private List<AuditOrderEntity1> huoDiSearchList1;
    private List<KuaiYunEntity> huoDiSearchList2;
    private String type;


    @Override
    protected int getContentView() {
        return R.layout.activity_search_result;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("订单搜索结果");
        huoDiSearchList= (List<AuditOrderEntity>) getIntent().getSerializableExtra("shuju");
        huoDiSearchList1= (List<AuditOrderEntity1>) getIntent().getSerializableExtra("shuju1");
        huoDiSearchList2= (List<KuaiYunEntity>) getIntent().getSerializableExtra("shuju2");
        type = getIntent().getStringExtra("type");
        act_recyclerView= (RecyclerView) findViewById(R.id.act_recyclerView);
        initRecyclerView();
    }

    private void initRecyclerView() {
        SearchResultAdapter adapter=new SearchResultAdapter(this,huoDiSearchList,huoDiSearchList1,huoDiSearchList2,type);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        act_recyclerView.setLayoutManager(manager);
        act_recyclerView.setAdapter(adapter);
        adapter.setOnClickItem(new SearchResultAdapter.OnClickItem() {
            @Override
            public void onClick(int position) {
                if (type.equals("0")){
                    Intent intent=new Intent(SearchResultActivity.this, SuYunDan_XiaoJianDan_XiangQingActivity.class);
                    intent.putExtra("orderno",huoDiSearchList.get(position).getOrderno());
                    intent.putExtra("line","1");
                    startActivity(intent);
                }else if (type.equals("1")){
                    Intent intent=new Intent(SearchResultActivity.this, SuYunDan_BanJiaDan_XiangQingActivity.class);
                    intent.putExtra("orderno",huoDiSearchList1.get(position).getOwner_orderno());
                    intent.putExtra("line","0");
                    startActivity(intent);
                }else {
                    Intent intent=new Intent(SearchResultActivity.this, KuaiYunDan_DingDan_XiangQingActivity.class);
                    intent.putExtra("orderno",huoDiSearchList2.get(position).getOrderno());
                    intent.putExtra("line","2");
                    startActivity(intent);
                }
            }
        });
    }
}
