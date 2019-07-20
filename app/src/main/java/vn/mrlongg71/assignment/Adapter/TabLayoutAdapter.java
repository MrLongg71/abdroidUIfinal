package vn.mrlongg71.assignment.Adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.widget.AdapterView;

import vn.mrlongg71.assignment.Fragment.ExpenditureFragment;
import vn.mrlongg71.assignment.Fragment.RevenueFragment;
import vn.mrlongg71.assignment.Fragment.StatisticsFragment;

public class TabLayoutAdapter extends FragmentPagerAdapter {
    private Context context;
    int totalTabs;

    public TabLayoutAdapter(FragmentManager fm, Context context, int totalTabs) {
        super(fm);
        this.context = context;
        this.totalTabs = totalTabs;
    }

    @Override
    public Fragment getItem(int i) {

        switch (i) {
            case 0:
                RevenueFragment revenueFragment = new RevenueFragment();
                return revenueFragment;
            case 1:
                ExpenditureFragment expenditureFragment = new ExpenditureFragment();
                return expenditureFragment;
            case 2:
                StatisticsFragment statisticsFragment = new StatisticsFragment();
                return statisticsFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }
}
