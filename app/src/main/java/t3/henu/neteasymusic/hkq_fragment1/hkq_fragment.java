package t3.henu.neteasymusic.hkq_fragment1;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;

import java.util.ArrayList;
import java.util.List;

import t3.henu.neteasymusic.R;

/**
 * Created by 117 on 2017/6/7.
 */

public class hkq_fragment extends Fragment {
    public static String[] imgs = {
            "http://t1.niutuku.com/190/14/14-117639.jpg",
            "http://pic.qiantucdn.com/58pic/11/31/58/97p58PICV26.jpg",
            "http://img2.3lian.com/img2007/19/51/025.jpg",
            "http://pic.58pic.com/58pic/12/81/91/94858PICWxT.jpg",
            "http://img.taopic.com/uploads/allimg/140326/235113-1403260U22059.jpg",
            "http://pic38.nipic.com/20140228/3822951_135521683000_2.jpg"
    };
    public static LinearLayout dotLayout;  //底部小圆点布局
    //音乐信息
    private String[] title = {"推荐歌单 >", "独家放送 >", "最新音乐 >", "我的最爱 >"};
    private int[] img = {R.mipmap.p1, R.mipmap.p2, R.mipmap.p3
            , R.mipmap.p4, R.mipmap.p5, R.mipmap.p6
            , R.mipmap.p7, R.mipmap.p8, R.mipmap.p9
            , R.mipmap.p10, R.mipmap.p11, R.mipmap.p12};
    private String[] describe = {"音乐1", "音乐2", "音乐3", "音乐4", "音乐5", "音乐6", "音乐7", "音乐8", "音乐9", "音乐10", "音乐11", "音乐12"};
    private List<SelectItems> selectItemsList = new ArrayList<>();
    private DisplayImageOptions options = new DisplayImageOptions.Builder().cacheInMemory(true)
            .cacheOnDisk(true).imageScaleType(ImageScaleType.IN_SAMPLE_INT).build();
    private Toast toast;
    private View view1;

    public hkq_fragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view1 = inflater.inflate(R.layout.fragment_blank, container, false);
        dotLayout = (LinearLayout) view1.findViewById(R.id.dot_layout);
        selectItemsList.clear();
        addDotImage();
        initSelect();  //初始化数据
        RecyclerView recyclerView = (RecyclerView) view1.findViewById(R.id.select_type);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        SongListViewAdapter adapter = new SongListViewAdapter(selectItemsList);
        recyclerView.setAdapter(adapter);
        return view1;
    }

    private void initSelect() {
        for (int i = 1; i <= title.length; i++) {
            SelectItems selectItems = new SelectItems(title[i - 1], img[3 * i - 3], img[3 * i - 2], img[3 * i - 1], describe[3 * i - 3], describe[3 * i - 2], describe[3 * i - 1]);
            selectItemsList.add(selectItems);
        }
    }

    //添加小圆点
    private void addDotImage() {
        for (int i = 0; i < imgs.length; i++) {
            ImageView iv = new ImageView(getActivity());
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            lp.setMargins(5, 5, 5, 5);
            iv.setLayoutParams(lp);
            iv.setImageResource(R.drawable.dot_normal);
            dotLayout.addView(iv);
        }
        ImageView iv = (ImageView) hkq_fragment.dotLayout.getChildAt(0);
        iv.setImageResource(R.drawable.dot_select);
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        AutoScrollViewPager pager = (AutoScrollViewPager) view1.findViewById(R.id.scroll_pager);

        pager.setAdapter(new PagerAdapter() {
            @Override
            public int getCount() {
                return imgs.length;
            }

            @Override
            public boolean isViewFromObject(View view, Object o) {
                return view == o;
            }

            @Override
            public Object instantiateItem(ViewGroup container, int position) {
                ImageView view = new ImageView(container.getContext());
                view.setScaleType(ImageView.ScaleType.CENTER_CROP);
                ImageLoader imageLoader = ImageLoader.getInstance();
                imageLoader.init(ImageLoaderConfiguration.createDefault(getActivity()));
                imageLoader.displayImage(imgs[position], view, options);
                container.addView(view);
                return view;
            }

            @Override
            public void destroyItem(ViewGroup container, int position, Object object) {
                container.removeView((View) object);
            }
        });

        pager.setScrollFactgor(5);
        pager.setOffscreenPageLimit(4);  //进行页面缓存，避免滑动显示的时候多次调用onCreate()方法
        pager.startAutoScroll(4000);  //设置自动播放时间
        pager.setOnPageClickListener(new AutoScrollViewPager.OnPageClickListener() {
            @Override
            public void onPageClick(AutoScrollViewPager autoScrollPager, int position) {
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(getActivity(), "You clicked page: " + (position + 1),
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        });
    }

}
