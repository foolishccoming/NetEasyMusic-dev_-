package t3.henu.neteasymusic.appMain;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import t3.henu.left_library.FZ_solve.fz_fragment;
import t3.henu.neteasymusic.R;
import t3.henu.neteasymusic.hkq_fragment1.hkq_fragment;

public class TabLayout_Mid extends Fragment {

    private View rootView;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments=new ArrayList<Fragment>();
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView=inflater.inflate(R.layout.activity_main_appmain_tablayout_mid,container,false);
        initTabLayout();
        initViewPager();
        return rootView;
    }

    private void initTabLayout() {
        tabLayout= (TabLayout) rootView.findViewById(R.id.id_appmain_toolbar_tabLayout_mid_tablayout);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tabLayout.getSelectedTabPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void initViewPager() {
        viewPager= (ViewPager) rootView.findViewById(R.id.id_appmain_toolbar_tabLayout_mid_viewPager);
        if(fragments.size()<=0){
            fragments.add(new hkq_fragment());
            fragments.add(new fz_fragment());
            fragments.add(new t3.henu.neteasymusic.FragmentZhubo());
            fragments.add(new t3.henu.left_library.YHQ_solve.BillboardFragment());
        }
        MyViewPagerAdapter myAdapter=new MyViewPagerAdapter(getActivity().getSupportFragmentManager(),fragments);
        viewPager.setAdapter(myAdapter);
        viewPager.setCurrentItem(0);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tabLayout.getTabAt(position).select();

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
}

