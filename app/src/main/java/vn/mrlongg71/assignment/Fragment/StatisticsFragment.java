package vn.mrlongg71.assignment.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import vn.mrlongg71.assignment.Adapter.TabLayoutAdapter;
import vn.mrlongg71.assignment.Adapter.TablayoutStatisticsAdapter;
import vn.mrlongg71.assignment.R;


public class StatisticsFragment extends Fragment {
    TabLayout tabLayout;
    ViewPager viewPager;
    FragmentManager fragmentManager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_statistics, container, false);
        anhxa(view);
        tabLayout(view);
        return view;
    }
    //fragment thống kê//
    private void tabLayout(View view) {
        TablayoutStatisticsAdapter tablayoutStatisticsAdapter = new TablayoutStatisticsAdapter(getChildFragmentManager(), view.getContext(), tabLayout.getTabCount());
        viewPager.setAdapter(tablayoutStatisticsAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.ic_here).setText("Ngày");
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_30);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_year);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {


            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setText("Ngày");
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_here);


                        break;
                    case 1:
                        tabLayout.getTabAt(1).setText("Tháng");
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_30);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setText("Năm");
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_year);
                        break;
                }

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

                switch (tab.getPosition()) {
                    case 0:
                        tabLayout.getTabAt(0).setText("");
                        tabLayout.getTabAt(0).setIcon(R.drawable.ic_here);
                        break;
                    case 1:
                        tabLayout.getTabAt(1).setText("");
                        tabLayout.getTabAt(1).setIcon(R.drawable.ic_30);
                        break;
                    case 2:
                        tabLayout.getTabAt(2).setText("");
                        tabLayout.getTabAt(2).setIcon(R.drawable.ic_year);
                        break;
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    private void anhxa(View view) {
        tabLayout = view.findViewById(R.id.tabLayout_sta);
        viewPager = view.findViewById(R.id.viewPager_sta);
        fragmentManager = getActivity().getSupportFragmentManager();

    }


}