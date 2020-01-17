package t3.henu.neteasymusic;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import cn.bmob.v3.Bmob;


public class WelcomeActivity extends AppCompatActivity {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_welcome);
        Bmob.initialize(this,"ad4cc4c34893bb9438df9793297f5893");
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent=new Intent(WelcomeActivity.this,MainActivity.class);
                startActivity(intent);finish();
            }
        },1000);

    }
}
