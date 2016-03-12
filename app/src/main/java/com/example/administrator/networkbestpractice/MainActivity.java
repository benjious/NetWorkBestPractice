package com.example.administrator.networkbestpractice;
/***
 * 需求是发出HTTP请求,希望从服务器那里返回信息
 */
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

    private Button show;
    private TextView show_text;
    public static final int SHOW_DATA=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        show=(Button)findViewById(R.id.show);
        show_text=(TextView)findViewById(R.id.show_text);
        final Handler  handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case SHOW_DATA:
                        show_text.setText(msg.obj.toString());
                        Log.d("bbbbbbbbbb", "HANDLE");
                        break;
                    default:
                        break;

                }

            }
        };



        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String address="http://www.baidu.com";
                HttpUri.sendHttpRequest(address, new HttpBackListener() {
                    @Override
                    public void onFinish(String response) {

                        Message msg=new Message();
                        msg.what=SHOW_DATA;
                        msg.obj=response.toString();
                        handler.sendMessage(msg);
                            Log.d("AAAAAAAAAAAA","HANDLE");





                    }

                    @Override
                    public void onError(Exception e) {
                        Log.d("---MainActivity---","返回信息错误");

                    }
                });



            }
        });




    }
}
