package t3.henu.neteasymusic.appMain_drawerlayout_start;

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
import t3.henu.neteasymusic.R;

public class RegistActivity extends AppCompatActivity {


    private EditText et_userName,et_passWord,et_re_passWord,et_email,et_phoneNumber;
    private Button btn_return,btn_login;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initview();
        initclick();
    }

    private void initclick() {
        btn_return.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RegistActivity.this.finish();
            }
        });

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPerson user=new UserPerson();
                if(TextUtils.isEmpty(et_userName.getText())){
                    Toast.makeText(RegistActivity.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(!et_passWord.getText().toString().equals(et_re_passWord.getText().toString())){
                    Toast.makeText(RegistActivity.this,"两次密码输入不一致！",Toast.LENGTH_SHORT).show();
                    return ;
                }
                if(!TextUtils.isEmpty(et_email.getText())){
                    user.setEmail(et_email.getText().toString());
                }
                if (!TextUtils.isEmpty(et_phoneNumber.getText())) {
                    et_phoneNumber.setText(et_phoneNumber.getText().toString());
                }
                user.setUsername(et_userName.getText().toString());
                user.setPassword(et_passWord.getText().toString());
                user.signUp(new SaveListener<UserPerson>() {
                    @Override
                    public void done(UserPerson userPerson, BmobException e) {
                        if(e == null){
                            Toast.makeText(RegistActivity.this,"注册成功！",Toast.LENGTH_SHORT).show();
                            et_userName.setText("");
                            et_passWord.setText("");
                            et_re_passWord.setText("");
                            et_email.setText("");
                            new Handler().postDelayed(new Runnable() {
                                @Override
                                public void run() {
                                    finish();
                                }
                            }, 1000);

                        }else{
                            Toast.makeText(RegistActivity.this,"注册失败,"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }

    private void initview() {
        et_userName= (EditText) findViewById(R.id.id_login_etxt_username);
        et_passWord= (EditText) findViewById(R.id.id_login_etxt_passWord1);
        et_re_passWord= (EditText) findViewById(R.id.id_login_etxt_passWord_repeat);
        et_email= (EditText) findViewById(R.id.id_login_etxt_email);
        et_phoneNumber= (EditText) findViewById(R.id.id_login_etxt_phoneNumber);
        btn_return= (Button) findViewById(R.id.id_login_btn_return);
        btn_login= (Button) findViewById(R.id.id_login_btn_confirmLogin);
    }
}
