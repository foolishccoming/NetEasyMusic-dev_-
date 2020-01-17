package t3.henu.neteasymusic;


import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import t3.henu.left_library.GYB_solve.Activities.NetWork.SearchActivity;
import t3.henu.left_library.GYB_solve.Collect;
import t3.henu.left_library.GYB_solve.Fragments.LeftRecyclerView;
import t3.henu.left_library.GYB_solve.PlayActivity;
import t3.henu.left_library.GYB_solve.RobotFragment.ChatFragment;
import t3.henu.left_library.GYB_solve.Services.PlayService;
import t3.henu.left_library.XPD_solve.MainActivity_XPD;
import t3.henu.neteasymusic.appMain.MyViewPagerAdapter;
import t3.henu.neteasymusic.appMain.TabLayout_Mid;
import t3.henu.neteasymusic.appMain_drawerlayout_start.LogInactivity;
import t3.henu.neteasymusic.appMain_drawerlayout_start.RecyclerViewData;
import t3.henu.neteasymusic.appMain_drawerlayout_start.RecyclerviewAdapter;

public class MainActivity extends AppCompatActivity {//
    public static TextView text_singer, text_song;
    public static ImageButton btn_play;
    public static ImageView play_imageView;
    public static RoundedImageView user_image;
    public static boolean is_log = false;
    public static Button log_btn;
    final private int REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS = 124;
    final private int WRITE_EXTERNAL_STORAGE_REQUEST_CODE = 122;
    final private int READ_SMS_REQUES_CODE = 122;
    final private int READ_EXTERNAL_STORAGE_REQUEST_CODE = 123;
    Fragment fragment_appmain_left = null,chatFragment=null;
    List<RecyclerViewData> lists = new ArrayList<RecyclerViewData>();
    Intent intent_setting;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private ConstraintLayout constraintLayout_left;
    private DrawerLayout drawerLayout;
    private RecyclerView recyclerView;
    private RelativeLayout play_layout;
    private Button btn_finish, btn_setting;
    private Intent search_intent;
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main_drawerlayout);
        initView();
        Intent in=new Intent(this, PlayService.class);
        bindService(in, t3.henu.left_library.GYB_solve.MainActivity.con, BIND_AUTO_CREATE);
        play_layout= (RelativeLayout) findViewById(R.id.id_base_play);
        play_layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, PlayActivity.class));
            }
        });
        intent_setting = new Intent(MainActivity.this, SettingActivity.class);
    }

    private void initView() {

        initToolbar();
        initViewPager();
        initDrawerlayout();
        if(isMarshmallow()){
            solvePermisson();
        }

        drawerLayout = (DrawerLayout) findViewById(R.id.id_appmain_drawelayout);
        initflow();
    }

    private boolean     isMarshmallow() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    private void solvePermisson() {
        List<String> permissionsNeeded = new ArrayList<String>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    WRITE_EXTERNAL_STORAGE_REQUEST_CODE);
        }
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            //申请WRITE_EXTERNAL_STORAGE权限
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    READ_EXTERNAL_STORAGE_REQUEST_CODE);
        }

        final List<String> permissionsList = new ArrayList<String>();
        if (!addPermission(permissionsList, Manifest.permission.READ_CONTACTS))
            permissionsNeeded.add("Read Contacts");
        if (!addPermission(permissionsList, Manifest.permission.WRITE_CONTACTS))
            permissionsNeeded.add("Write Contacts");

        if (permissionsList.size() > 0) {
            if (permissionsNeeded.size() > 0) {
                // Need Rationale
                String message = "You need to grant access to " + permissionsNeeded.get(0);
                for (int i = 1; i < permissionsNeeded.size(); i++)
                    message = message + ", " + permissionsNeeded.get(i);

                return;
            }
            requestPermissions(permissionsList.toArray(new String[permissionsList.size()]),
                    REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS);
            return;
        }
    }

    private boolean addPermission(List<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
            permissionsList.add(permission);
            // Check for Rationale Option
            if (!shouldShowRequestPermissionRationale(permission))
                return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CODE_ASK_MULTIPLE_PERMISSIONS: {
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.ACCESS_FINE_LOCATION, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.READ_CONTACTS, PackageManager.PERMISSION_GRANTED);
                perms.put(Manifest.permission.WRITE_CONTACTS, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);
                // Check for ACCESS_FINE_LOCATION
                if (perms.get(Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED
                        && perms.get(Manifest.permission.WRITE_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
                    // All Permissions Granted
                } else {
                    // Permission Denied
                    Toast.makeText(MainActivity.this, "Some Permission is Denied", Toast.LENGTH_SHORT)
                            .show();
                }
            }
            break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    private void initDrawerlayout() {
        user_image = (RoundedImageView) findViewById(R.id.id_user_roundedImageView);
        constraintLayout_left = (ConstraintLayout) findViewById(R.id.id_appmain_drawelayout_left);
        ImageButton btn = (ImageButton) findViewById(R.id.id_appmain_btn_showleft);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!drawerLayout.isDrawerOpen(constraintLayout_left)) {
                    drawerLayout.openDrawer(constraintLayout_left);
                }
            }
        });
        btn_setting = (Button) findViewById(R.id.id_setting);
        btn_setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(intent_setting);
            }
        });
        btn_finish = (Button) findViewById(R.id.id_finish);
        /*Toolbar toolbar= (Toolbar) findViewById(R.id.id_appmain_toobar);
        setSupportActionBar(toolbar);
        ActionBar actionBar=getSupportActionBar();
        if(actionBar!=null){
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeAsUpIndicator(R.drawable.icon_menu);
        }*/
        final RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.id_base_play);
        initNavigationView();
        btn_finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });
    }

    private void initNavigationView() {
        recyclerView= (RecyclerView) findViewById(R.id.id_left_recyclerview);
        if(lists.size()<=0){
            String []texs=getResources().getStringArray(R.array.appmain_drawerlayout_left_menu_itemNames);
            int []images={R.mipmap.ic_launcher,R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02,R.drawable.icon_appmain_left_menu_03,
                    R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02,R.drawable.icon_appmain_left_menu_03,
                    R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02,R.drawable.icon_appmain_left_menu_03,
                    R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02,R.drawable.icon_appmain_left_menu_03,
                    R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02,R.drawable.icon_appmain_left_menu_03,
                    R.drawable.icon_appmain_left_menu_01,R.drawable.icon_appmain_left_menu_02};
            for(int i=0;i<texs.length;i++){
                RecyclerViewData r=new RecyclerViewData(images[i],texs[i]);
                lists.add(r);
            }
        }


        RecyclerviewAdapter adapter=new RecyclerviewAdapter(lists);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int i) {
                if(i==11){
                    AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final String[] array = new String[]{"不关闭了","10s","20s","30s","60s"};
                    builder.setTitle("请选择时间")
                            .setIcon(R.mipmap.ic_search)
                            .setItems(array, new DialogInterface.OnClickListener(){
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    Intent intent = new Intent(MainActivity.this, AlarmService.class);
                                    int time=0;
                                    switch(i){
                                        case 0:
                                            break;
                                        case 1:

                                            time=AlarmService.timelong = 10;
                                            break;
                                        case 2:
                                            time=AlarmService.timelong = 20;
                                            break;
                                        case 3:
                                            time=AlarmService.timelong = 30;
                                            break;
                                        case 4:
                                            time=AlarmService.timelong = 60;
                                            break;
                                    }//startService(intent);
                                    if(time!=0){
                                        new Handler().postDelayed(new Runnable() {
                                            @Override
                                            public void run() {
                                                finish();
                                            }
                                        },time*1000);
                                    }

                                }
                            })
                            .setNeutralButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            });
                    AlertDialog ad = builder.create();
                    ad.show();
                }
                else if(i!=0&&i!=5&&i!=8){
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            if(!is_log){
                                Intent in=new Intent(MainActivity.this, LogInactivity.class);
                                startActivity(in);
                            }else {
                                toast("已经登录！！！");
                            }

                        }
                    },500);

                }
            }
        });
        adapter.setOnRecyclerViewItemChildClickListener(new BaseQuickAdapter.OnRecyclerViewItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter baseQuickAdapter, View view, int i) {
                switch (view.getId()){


                    case R.id.id_appmain_drawelayout_left_btn_signin:
                        Intent in;
                        if (!is_log) {
                            if (view.getId() == R.id.id_appmain_drawelayout_left_btn_signin) {
                                log_btn = (Button) view;
                            }
                            in = new Intent(MainActivity.this, LogInactivity.class);
                        } else {
                            in = new Intent(MainActivity.this, MainActivity_XPD.class);
                        }

                        startActivity(in);
                        break;
                }
            }
        });
        recyclerView.setAdapter(adapter);
    }

    private void initToolbar() {
        ImageButton button= (ImageButton) findViewById(R.id.id_btn_search);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(search_intent==null){
                    search_intent=new Intent(MainActivity.this, SearchActivity.class);
                }
                startActivity(search_intent);
            }
        });

        tabLayout = (TabLayout) findViewById(R.id.id_appmain_toolbar_tabLayout);
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
        viewPager = (ViewPager) findViewById(R.id.id_appmain_viewpager);
        if (fragments.size() <= 0) {
            if (fragment_appmain_left != null) {
                fragment_appmain_left = new BlankFragment("第三个");
            }
            if(chatFragment==null){
                chatFragment=new ChatFragment();
            }
            fragments.add(new LeftRecyclerView());
            fragments.add(new TabLayout_Mid());
            fragments.add(chatFragment);
        }
        MyViewPagerAdapter myAdapter = new MyViewPagerAdapter(getSupportFragmentManager(), fragments);
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

    private void toast(String s) {
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }

    private void initflow() {
        text_singer= (TextView) findViewById(R.id.id_textview_songsinger);
        text_song= (TextView) findViewById(R.id.id_textview_songName);
        btn_play= (ImageButton) findViewById(R.id.id_flow_play);
        play_imageView= (ImageView) findViewById(R.id.id_imageview_album);
        btn_play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PlayService.play_list.size()>0){
                    t3.henu.left_library.GYB_solve.MainActivity.playBinder.setIsPlay();
                }else{
                    toast("播放列表为空！！！");
                }

                // toast("播放");
            }
        });
    }

    @Override
    protected void onDestroy() {
        unbindService(t3.henu.left_library.GYB_solve.MainActivity.con);
        Collect.removeAll();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK
                && event.getAction() == KeyEvent.ACTION_DOWN) {
           /* if ((System.currentTimeMillis() - exitTime) > 2000) {
                //弹出提示，可以有多种方式
                Toast.makeText(getApplicationContext(), "再按一次退出程序", Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            } else {
                System.exit(0);
            }*/
            Intent home = new Intent(Intent.ACTION_MAIN);
            home.addCategory(Intent.CATEGORY_HOME);
            startActivity(home);
            return true;
        }

        return super.onKeyDown(keyCode, event);
    }


}
