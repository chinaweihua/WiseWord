package com.wtrue.wiseword;

import okhttp3.Call;

import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.okhttp.Callback;
import com.wtrue.okhttp.OkHttpUtils;
import com.wtrue.okhttp.StringCallback;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 主页
 * 
 * @author Ben
 * 
 */
public class MainActivity extends BaseActivity {
	private TextView main_tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main_tv = (TextView) this.findViewById(R.id.main_tv);
		OkHttpUtils
				.get()
				.url("http://apis.baidu.com/showapi_open_bus/showapi_joke/joke_text")
				.tag(this).build().connTimeOut(20000).readTimeOut(20000)
				.writeTimeOut(20000).execute(new MyStringCallback());
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		if (main_tv != null) {
			main_tv.setText("网络连接。。。");
		}
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		if (main_tv != null) {
			main_tv.setText("网络断开。。。");
		}
	}
	
	public class MyStringCallback extends StringCallback{

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
}
