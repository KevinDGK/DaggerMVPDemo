package com.dgk.mvpdemo.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.dgk.mvpdemo.MyApplication;
import com.dgk.mvpdemo.model.IModel;
import com.dgk.mvpdemo.model.Model;
import com.dgk.mvpdemo.view.IView;

import javax.inject.Inject;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:45
 * @des ${TODO}
 */
public class PresenterA implements IPresenter {

    IView view;     // 持有view的接口，用于显示数据
    private IModel model;   // 持有model的接口，用于获取数据

    public PresenterA(){
        model = new Model();
    }

    @Override
    public void onCreate(IView view) {
        Log.i(this.getClass().getSimpleName(), "===== onCreate =====");
        this.view = view;
//        MyApplication.getPresenterComponent().inject();
    }

    @Override
    public void performOnClick() {

        // 3.响应按钮点击事件，开启子线程加载数据

        //     将分配线程的职责全部交给Presenter，便于维护管理
        //      -开启子线程通过model下载数据
        //      -通过runOnUiThread或者handler，调用view的方法修改界面

        new AsyncTask<Void,Integer,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showDialog();
                Log.i("开始下载数据...", "onPreExecute");
            }

            @Override
            protected String doInBackground(Void... params) {
                String result;
                for (int i = 1; i < 11; i++) {
                    publishProgress(i);
                    model.getDataFromNet();
                    Log.i("模拟下载数据...", "doInBackground: "+i);
                }
                result = "我是从服务器千辛万苦下载下来的数据~~~";
                return result;
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                view.setDialogProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);

                // 5.将数据返回给presenter，然后将数据传递给view显示数据
                Log.i("下载数据完毕...", "onPostExecute");
                String dataFromNet = System.getProperty("line.separator1","\r\n")
                        +data +System.getProperty("line.separator1","\r\n")
                        +"我是presenterA，将数据加工了一下，呵呵哒~";
                view.cancelDialog();
                view.setData(dataFromNet);
            }
        }.execute();

    }

}
