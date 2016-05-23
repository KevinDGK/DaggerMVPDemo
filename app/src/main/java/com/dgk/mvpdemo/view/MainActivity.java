package com.dgk.mvpdemo.view;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.dgk.mvpdemo.MyApplication;
import com.dgk.mvpdemo.R;
import com.dgk.mvpdemo.dagger.bean.Apple;
import com.dgk.mvpdemo.dagger.bean.Banana;
import com.dgk.mvpdemo.dagger.bean.Fruit;
import com.dgk.mvpdemo.dagger.bean.Orange;
import com.dgk.mvpdemo.dagger.component.DaggerPresenterComponent;
import com.dgk.mvpdemo.presenter.IPresenter;
import com.dgk.mvpdemo.presenter.PresenterA;

import javax.inject.Inject;
import javax.inject.Named;

// View层
public class MainActivity extends AppCompatActivity implements IView{

    private TextView tv_content;
    private Button btn;
    private ProgressDialog pd;

    @Inject
    public IPresenter presenter;        // 1.使用dagger注入IPresenter，初始化并不在view层，实现了view层和presenter层的解耦
                                        // 如果复用View，即改变presenter，并不用修改view的代码，仅仅注入的时候将PresentA换成PresentB即可。

    @Inject
    public Fruit fruit;

    @Inject
    public Apple apple;

    @Named("Orange")
    public Fruit mOrange;

    @Named("Banana")
    public Fruit mBanana;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 方法一：在application层初始化注入器的实例，可重复调用注入器来对不同的容器完成注入
        //((MyApplication)getApplication()).getPresenterComponent().inject(this); // 注入依赖 - IPresenter

        // 方法二：每次创建注入器实例，来完成对容器的注入
        DaggerPresenterComponent.create().inject(this);

        initView();
        initListener();
    }

    private void initView() {

        tv_content = (TextView) findViewById(R.id.tv_content);
        btn = (Button) findViewById(R.id.btn);

        //presenter = new PresenterA(this);    // 1.创建presenter并初始化
                                            // 若未使用dagger依赖注入，那么view和presenter层之间仍然有较大耦合，
                                            // 修改presenter的话，view层也会变动
                                            // 而dagger的使用，可以极大的降低这种耦合。

        presenter.onCreate(this);
    }

    private void initListener() {
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.performOnClick(); // 2.点击响应事件传递给presenter处理
            }
        });
    }

    @Override
    public void setData(String data) {
        // 6.将presenter传递过来的数据显示到界面上
        Log.i("View", "setData: 设置数据 "+data);
        tv_content.setText(tv_content.getText()+data);

        if (fruit instanceof Apple) {
            if (((Apple) fruit).color == Color.RED) {
                Toast.makeText(this,"水果：红果果~~~",Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this,"水果：绿果果~~~",Toast.LENGTH_SHORT).show();
            }
        }

        if (apple.type == "HFS") {
            Toast.makeText(this,"苹果：红富士！",Toast.LENGTH_SHORT).show();
        }

        if (mOrange instanceof Orange) {
            Log.i("View", "mApple：注入的水果是苹果！！！");
        }

        if (mBanana instanceof Banana) {
            Log.i("View", "mBanana：注入的水果是香蕉！！！");
        }
    }

    @Override
    public void showDialog() {
        // 创建并显示进度对话框
        Log.i("View", "showDialog: 创建对话框");
        pd = new ProgressDialog(this);
        pd.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);//设置进度条样式为水平
        pd.setMax(10);//设置进度条最大值
        pd.setCancelable(false);//设置进度条不可以被Back键取消
        pd.setTitle("正在联网下载数据");//设置标题
        pd.setMessage("请耐心等一下，好不好嘛 ~~~");//设置进度条内容部分的文字
        pd.setIndeterminate(false);//设置是否显示进度条里的进度
        pd.show();
    }

    @Override
    public void setDialogProgress(Integer value) {
        // 更改加载进度
        pd.setProgress(value);
    }

    @Override
    public void cancelDialog() {
        pd.dismiss();
    }
}
