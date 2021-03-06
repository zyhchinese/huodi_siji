package com.lxwls.hdsjd.ui.activity;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.ShangPinFenLeiViewPagerAdapter;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.ui.fragment.ShangPinFenLeiFragment;

import java.util.ArrayList;
import java.util.List;

public class ShangPinFenLei11Activity extends BackCommonActivity {
    private TabLayout tab_layout;
    private ViewPager viewpage;
    private EditText ed_content;
    private TextView tv_sousuo;
    private ImageView img_saoma;
    private String [] tab=new String[]{"综合","销量","价格"};
    private List<ShangPinFenLeiFragment> list=new ArrayList<>();
    private int currentItem=0;
    private boolean isok1=true;
    private boolean isok2=true;
    private boolean isok3=true;
    private String name="";


    @Override
    protected int getContentView() {
        return R.layout.activity_shang_pin_fen_lei;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        tab_layout= (TabLayout) findViewById(R.id.tab_layout);
        viewpage= (ViewPager) findViewById(R.id.viewpage);
        ed_content= (EditText) findViewById(R.id.ed_content);
        img_saoma= (ImageView) findViewById(R.id.img_saoma);
        img_saoma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        tv_sousuo= (TextView) findViewById(R.id.tv_sousuo);
        tv_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name=ed_content.getText().toString().trim();
                if (!ed_content.getText().toString().trim().equals("")){
                    if (currentItem==0){
                        list.get(currentItem).initShuJu("3","0","",ed_content.getText().toString().trim());
                    }else if (currentItem==1){
                        list.get(currentItem).initShuJu("2","0","",ed_content.getText().toString().trim());
                    }else if (currentItem==2){
                        list.get(currentItem).initShuJu("0","0","",ed_content.getText().toString().trim());
                    }
                }else {
                    showToast("请输入商品名称");
                }

            }
        });

        name=getIntent().getStringExtra("name");
        if (!name.equals("")){
            ed_content.setHint(name);
        }



        for (int i = 0; i < 3; i++) {
            ShangPinFenLeiFragment fenLeiFragment=new ShangPinFenLeiFragment();
            list.add(fenLeiFragment);

        }

        tab_layout.addTab(tab_layout.newTab().setText("综合"));
        tab_layout.addTab(tab_layout.newTab().setText("销量"));
        tab_layout.addTab(tab_layout.newTab().setText("价格"));

        ShangPinFenLeiViewPagerAdapter adapter=new ShangPinFenLeiViewPagerAdapter(getSupportFragmentManager(),list,tab);
        viewpage.setAdapter(adapter);
        tab_layout.setupWithViewPager(viewpage);

        currentItem = viewpage.getCurrentItem();

        list.get(0).initShuJu("3","0","",name);


        viewpage.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==0){
                    list.get(position).initShuJu("3","0","",name);
                }else if (position==1){
                    list.get(position).initShuJu("2","0","",name);
                }else if (position==2){
                    list.get(position).initShuJu("0","0","",name);
                }

                currentItem = viewpage.getCurrentItem();
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        tab_layout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                if (tab.getPosition()==0){
                    if (isok1){
                        isok1=false;
                        list.get(tab.getPosition()).initShuJu("3","1","",name);
                    }else {
                        isok1=true;
                        list.get(tab.getPosition()).initShuJu("3","0","",name);
                    }
                }else if (tab.getPosition()==1){
                    if (isok2){
                        isok2=false;
                        list.get(tab.getPosition()).initShuJu("2","1","",name);
                    }else {
                        isok2=true;
                        list.get(tab.getPosition()).initShuJu("2","0","",name);
                    }
                }else if (tab.getPosition()==2){
                    if (isok3){
                        isok3=false;
                        list.get(tab.getPosition()).initShuJu("0","1","",name);
                    }else {
                        isok3=true;
                        list.get(tab.getPosition()).initShuJu("0","0","",name);
                    }
                }

            }
        });


    }

}
