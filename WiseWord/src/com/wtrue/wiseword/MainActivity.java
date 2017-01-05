package com.wtrue.wiseword;

import okhttp3.Call;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.okhttp.Callback;
import com.wtrue.okhttp.OkHttpUtils;
import com.wtrue.okhttp.StringCallback;
import com.wtrue.utlis.BmobUtlis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
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
	/**
	 * 注册、登陆、修改密码、更新名字、收藏测试按钮
	 */
	private Button register_bt,login_bt,reset_bt,update_bt,colletion;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		main_tv = (TextView) this.findViewById(R.id.main_tv);
		register_bt = (Button) this.findViewById(R.id.register_bt);
		login_bt = (Button) this.findViewById(R.id.login_bt);
		reset_bt = (Button) this.findViewById(R.id.reset_bt);
		update_bt = (Button) this.findViewById(R.id.update_bt);
		colletion = (Button) this.findViewById(R.id.colletion);
		callback = new MyStringCallback();
		OkHttpUtils.get().url("http://route.showapi.com/341-3")
				.addParams("showapi_appid", "100")
				.addParams("showapi_sign", "698d51a19d8a121ce581499d7b701668")
				.addParams("page", "1").addParams("maxResult", "20").tag(this)
				.build().connTimeOut(20000).readTimeOut(20000)
				.writeTimeOut(20000).execute(callback);
		register_bt.setOnClickListener(this);
		login_bt.setOnClickListener(this);
		reset_bt.setOnClickListener(this);
		update_bt.setOnClickListener(this);
		colletion.setOnClickListener(this);
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
		Intent intent;
		switch (arg0.getId()) {
		case R.id.register_bt://跳转注册界面
			intent = new Intent(MainActivity.this,RegisterActivity.class);
			startActivity(intent);
			break;
		case R.id.login_bt://跳转登陆界面
			intent = new Intent(MainActivity.this,LoginActivity.class);
			startActivity(intent);
			break;
		case R.id.reset_bt://修改密码
			intent = new Intent(MainActivity.this,ResetActivity.class);
			startActivity(intent);
			break;
		case R.id.update_bt://更新名字
			BmobUtlis.userUpdate(null, "哈哈", new UpdateListener() {
				
				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if(arg0 == null){//更新成功
						Toast.makeText(MainActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
					}else{
						Toast.makeText(MainActivity.this, "更新失败了+："+arg0.toString(), Toast.LENGTH_SHORT).show();
					}
				}
			});
			break;
		case R.id.colletion://收藏测试   收藏之前需要判断是否有登陆过，如果没有登陆或者本地保存的账号密码过期需要提示用户重新登陆
			BmobUtlis.userColllection("测试", "测试aaaa", new SaveListener<String>() {
				 
				@Override
				public void done(String arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if(arg1 == null){//收藏成功
						Toast.makeText(MainActivity.this, "收藏成功了", Toast.LENGTH_SHORT).show();
					}else{//收藏失败
						Toast.makeText(MainActivity.this, "收藏失败了", Toast.LENGTH_SHORT).show();
						Log.e("失败原因", arg1.toString());
					}
				}
			});
			break;
		default:
			break;
		}
	}

}
