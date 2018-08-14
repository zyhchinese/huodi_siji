package com.lxwls.hdsjd.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.lxwls.hdsjd.ui.fragment.HuoDSuYun1Fragment;

import java.util.List;

/**
 * Created by admin on 2018/3/12.
 */

public class ViewPageKuaiYunAdapter extends FragmentStatePagerAdapter {

    private List<HuoDSuYun1Fragment> list;
    private String[] title;
    public ViewPageKuaiYunAdapter(FragmentManager fm, List<HuoDSuYun1Fragment> list, String[] title) {
        super(fm);
        this.list=list;
        this.title=title;
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];

    }
}
