package com.wtrue.wiseword;

import okhttp3.Call;

import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.okhttp.Callback;
import com.wtrue.okhttp.OkHttpUtils;
import com.wtrue.okhttp.StringCallback;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主页
 * 
 * @author Ben
 * 
 */
public class MainActivity extends BaseActivity implements OnClickListener{
	private TextView main_tv;
	private boolean isConn;// 是否有网络
	private MyStringCallback callback;
	private Button register_bt;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main_tv = (TextView) this.findViewById(R.id.main_tv);
		register_bt = (Button) this.findViewById(R.id.register_bt);
		callback = new MyStringCallback();
		OkHttpUtils.get().url("http://route.showapi.com/341-3")
				.addParams("showapi_appid", "100")
				.addParams("showapi_sign", "698d51a19d8a121ce581499d7b701668")
				.addParams("page", "1").addParams("maxResult", "20").tag(this)
				.build().connTimeOut(20000).readTimeOut(20000)
				.writeTimeOut(20000).execute(callback);
		register_bt.setOnClickListener(this);
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		isConn = true;
		if (main_tv != null) {
			main_tv.setText("网络连接。。。");
		}
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		isConn = false;
		if (main_tv != null) {
			main_tv.setText("网络断开。。。");
		}
	}

	public class MyStringCallback extends StringCallback {

		@Override
		public void onError(Call call, Exception e, int id) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onResponse(String response, int id) {
			// TODO Auto-generated method stub
			main_tv.setText(response);
		}

	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		OkHttpUtils.getInstance().cancelTag(this);//当activity退出的时候取消请求
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.register_bt://跳转注册界面
			Intent intent = new Intent(MainActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;

		default:
			break;
		}
	}

}
