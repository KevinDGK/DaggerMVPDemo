package com.dgk.mvpdemo.presenter;

import android.os.AsyncTask;
import android.util.Log;

import com.dgk.mvpdemo.model.IModel;
import com.dgk.mvpdemo.model.Model;
import com.dgk.mvpdemo.view.IView;

/**
 * @author dgk
 * @time 2016/5/22 0022 下午 10:45
 * @des ${TODO}
 */
public class PresenterB implements IPresenter {

    private IView view;     // 持有view的接口，用于显示数据
    private IModel model;   // 持有model的接口，用于获取数据

    public PresenterB(){
        model = new Model();
    }

    @Override
    public void onCreate(IView view) {
        this.view = view;
    }

    @Override
    public void performOnClick() {

        // 将分配线程的职责全部交给Presenter，便于维护管理
        //  -1.开启子线程通过model下载数据
        //  -2.通过runOnUiThread或者handler，调用view的方法修改界面
        new AsyncTask<Void,Integer,String>(){
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                view.showDialog();
            }

            @Override
            protected String doInBackground(Void... params) {
                for (int i = 1; i < 11; i++) {
                    publishProgress(i);
                    model.getDataFromNet();
                    Log.i("模拟下载数据...", "doInBackground: "+i);
                }
                return "我是从服务器千辛万苦下载下来的数据~~~";
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
                view.setDialogProgress(values[0]);
            }

            @Override
            protected void onPostExecute(String data) {
                super.onPostExecute(data);
                // 5.将数据传递给presenter的callBack
                String dataFromNet = System.getProperty("line.separator1","\r\n")
                        +"我是从服务器千辛万...(后面的数据被吃掉了〒_〒)" +System.getProperty("line.separator1","\r\n")
                        +"我是presenterB，将数据吃了一半，呵呵哒~";
                view.cancelDialog();
                view.setData(dataFromNet);
            }
        }.execute();

    }

}
