package t3.henu.neteasymusic.appMain_drawerlayout_start;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import t3.henu.neteasymusic.MainActivity;
import t3.henu.neteasymusic.R;


public class LogInactivity extends AppCompatActivity implements View.OnClickListener {

    Intent intent = null;
    private EditText edit_userName,edit_passWord;
    private Button btn_denglu,btn_forget,btn_newUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sigin);
        initview();
    }


    private void initview() {
        edit_passWord= (EditText) findViewById(R.id.id_main_edittext_password);
        edit_userName= (EditText) findViewById(R.id.id_main_edittext_userName);
        btn_denglu = (Button)findViewById(R.id.id_main_btn_denglu);
        btn_denglu.setOnClickListener(this);
        btn_forget = (Button)findViewById(R.id.id_main_btn_forgetPassword);
        btn_forget.setOnClickListener(this);
        btn_newUser = (Button)findViewById(R.id.id_main_btn_newUser);
        btn_newUser.setOnClickListener(this);
        findViewById(R.id.id_login_btn_return1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogInactivity.this.finish();
            }
        });
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.id_main_btn_newUser:
                Intent intent_newuser=new Intent(LogInactivity.this, RegistActivity.class);
                startActivity(intent_newuser);
                break;
            case R.id.id_main_btn_denglu:
                SolveDenglu();
                break;
        }

    }

    private void SolveDenglu() {
       if(TextUtils.isEmpty(edit_userName.getText().toString())){
            toast( "用户名不能为空");
            return;
        }
        if(TextUtils.isEmpty(edit_passWord.getText().toString())){
            toast( "密码不能为空");
            return ;
        }

        final UserPerson user = new UserPerson();
        user.setUsername(edit_userName.getText().toString());
        user.setPassword(edit_passWord.getText().toString());
        user.login(new SaveListener<UserPerson>(){
            @Override
            public void done(UserPerson userPerson, BmobException e) {
                if(e==null){
                    toast("登录成功");
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //MainActivity1.is_log=true;
                            //Intent intent = new Intent();
                            //intent.putExtra("user",edit_userName.getText().toString());
                            // setResult(2, intent);
                            MainActivity.log_btn.setText(user.getUsername());
                            MainActivity.is_log = true;
                            finish();
                        }
                    }, 1000);

                }else{
                    toast("登录失败！！\n"+e.getMessage());
                }
            }
        });

    }

    private void toast(String s) {
        Toast.makeText(LogInactivity.this, s, Toast.LENGTH_SHORT).show();
    }
}