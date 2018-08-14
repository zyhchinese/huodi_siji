package com.lxwls.hdsjd.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.ViewPageKuaiYunAdapter;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.ui.fragment.HuoDSuYun1Fragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by admin on 2018/3/10.（货滴速运）
 */

public class HuoDSuyun1Activity extends BackCommonActivity{

    private TabLayout tab_layout;
    private ViewPager view_pager;
    private List<HuoDSuYun1Fragment> list=new ArrayList<>();
    private String[] title=new String[]{"同城小件单","同城搬家单"};
    private ImageView img_back;
    private int currentItem;

    @Override
    protected int getContentView() {
        return R.layout.huodisuyun1;
    }
   //
    @Override
    protected void initWidget() {
        super.initWidget();
        tab_layout= (TabLayout) findViewById(R.id.tab_layout);
        view_pager= (ViewPager) findViewById(R.id.viewpage);
        img_back= (ImageView) findViewById(R.id.go_back);

        img_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        list.clear();
        for (int i = 0; i < 2; i++) {
            HuoDSuYun1Fragment fragment=new HuoDSuYun1Fragment();
            fragment.type(i);
            list.add(fragment);
        }
        initView();
    }

    private void initView() {
        tab_layout.addTab(tab_layout.newTab().setText("同城小件单"));
        tab_layout.addTab(tab_layout.newTab().setText("同城搬家单"));
        ViewPageKuaiYunAdapter adapter = new ViewPageKuaiYunAdapter(getSupportFragmentManager(),list,title);
        view_pager.setAdapter(adapter);
        tab_layout.setupWithViewPager(view_pager);

        currentItem = view_pager.getCurrentItem();

//        list.get(0).initDingDanSearch();
        view_pager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }
            @Override
            public void onPageSelected(int position) {
                list.get(position).location();
                currentItem = view_pager.getCurrentItem();
            }
            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}
