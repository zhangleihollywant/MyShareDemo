package com.want.hotkidclub.myshareutildemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.PlatformDb;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.sina.weibo.SinaWeibo;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, PlatformActionListener {
    String name, gender, id, icon;
    private Button sinaLogin, qqLogin, wchatLogin, sinaShare, qqShare, qqFriendShare, wchatShare, wchatFriendShare;
    private TextView mTextView;
    private ImageView head_img;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ShareSDK.initSDK(this);
        initView();
    }

    private void initView() {
        sinaLogin = (Button) findViewById(R.id.sina_login);
        qqLogin = (Button) findViewById(R.id.qq_login);
        wchatLogin = (Button) findViewById(R.id.wchat_login);
        sinaShare = (Button) findViewById(R.id.sina_share);
        qqShare = (Button) findViewById(R.id.qq_share);
        qqFriendShare = (Button) findViewById(R.id.qq_friend_share);
        wchatShare = (Button) findViewById(R.id.wchat_share);
        wchatFriendShare = (Button) findViewById(R.id.wchat_friend_share);
        mTextView = (TextView) findViewById(R.id.show_msg);
        head_img = (ImageView) findViewById(R.id.head_icon);
        sinaLogin.setOnClickListener(this);
        qqLogin.setOnClickListener(this);
        wchatLogin.setOnClickListener(this);
        sinaShare.setOnClickListener(this);
        qqShare.setOnClickListener(this);
        qqFriendShare.setOnClickListener(this);
        wchatShare.setOnClickListener(this);
        wchatFriendShare.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.sina_login:
                Platform sina = ShareSDK.getPlatform(SinaWeibo.NAME);
                sina.SSOSetting(true);
                //问题  登录输入正确的账号密码后怎么处理
                authorize(sina);
                break;
            case R.id.qq_login:
                Platform qq = ShareSDK.getPlatform(QQ.NAME);
                qq.SSOSetting(true);
                authorize(qq);
                break;
            case R.id.wchat_login:
                break;
            case R.id.sina_share://微博分享弹不出编辑框的
                SinaWeibo.ShareParams sinaShare = new SinaWeibo.ShareParams();
                sinaShare.setImageUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
                sinaShare.setText("我是大神");
                Platform weibo = ShareSDK.getPlatform(SinaWeibo.NAME);
//                weibo.SSOSetting(true);
                weibo.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
                        Toast.makeText(MainActivity.this, "微博分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                weibo.share(sinaShare);
                break;
            case R.id.qq_share:

                break;
            case R.id.qq_friend_share://空间分享
                //分享图片显示不出来，分享编辑可以看到的，成功后显示不出来
                QQ.ShareParams qqshare = new QQ.ShareParams();
                qqshare.setImageUrl("http://pic6.huitu.com/res/20130116/84481_20130116142820494200_1.jpg");
                qqshare.setTitleUrl("http://www.baidu.com");
                qqshare.setText("woshidas");
                qqshare.setSite("肖谷渡");
                qqshare.setSiteUrl("http://www.jianshu.com/u/729f3bbcda5a");


                Platform qqp = ShareSDK.getPlatform(QZone.NAME);
//                qqp.SSOSetting(true);
                //分享成功，但是消息发送失败的
                qqp.setPlatformActionListener(new PlatformActionListener() {
                    @Override
                    public void onComplete(Platform platform, int i, HashMap<String, Object> map) {
                        Toast.makeText(MainActivity.this, "分享成功", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(Platform platform, int i, Throwable throwable) {

                    }

                    @Override
                    public void onCancel(Platform platform, int i) {

                    }
                });
                qqp.share(qqshare);
                break;
            case R.id.wchat_share:
                break;
            case R.id.wchat_friend_share:
                break;

            default:
                break;
        }
    }

    public void authorize(Platform platform) {
        if (platform == null) {
            return;
        }
        platform.setPlatformActionListener(this);
        platform.SSOSetting(false);//关闭授权
        platform.showUser(null);
    }

    @Override
    public void onComplete(Platform platform, int action, HashMap<String, Object> map) {
//        if (action == Platform.ACTION_USER_INFOR) {
//            getUser(platform);
//            mTextView.setText("姓名：" + name + "性别：" + gender + "id：" + id + "icon" + icon);
////            head_img.setImageBitmap(BitmapFactory.decodeFile(icon));
//            head_img.setImageURI(Uri.parse(icon));
//        }
    }

    @Override
    public void onError(Platform platform, int i, Throwable throwable) {
        Toast.makeText(this, "未知错误", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onCancel(Platform platform, int i) {
        Toast.makeText(this, "取消登录授权", Toast.LENGTH_SHORT).show();
    }


    public void getUser(Platform platform) {
        PlatformDb db = platform.getDb();
        name = db.getUserName();
        gender = db.getUserGender();
        id = db.getUserId();
        icon = db.getUserIcon();
    }
}


