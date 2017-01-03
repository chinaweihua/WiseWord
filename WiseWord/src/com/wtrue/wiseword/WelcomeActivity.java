package com.wtrue.wiseword;

import com.wtrue.netmonitor.NetUtils.NetType;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
/**
 * 启动欢迎界面 
 * @author Ben
 */
public class WelcomeActivity extends BaseActivity implements OnClickListener{
	/**
	 * 计时View
	 */
	private TextView launcher_time;
	private final int MSG_FINISH_LAUNCHERACTIVITY = 10001;
	/**
	 * 启动页面跳转时间
	 */
	private int RECIPROCALTIME = 5;
	private Button reciprocalBt;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_layout);
		launcher_time = (TextView) this.findViewById(R.id.launcher_time);
		reciprocalBt = (Button) this.findViewById(R.id.reciprocalBt);
		reciprocalBt.setOnClickListener(this);
		launcher_time.setText(RECIPROCALTIME+"");
		jumpHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 1000);
	}
	
	/**
	 * 计时and跳转
	 */
	private Handler jumpHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_FINISH_LAUNCHERACTIVITY:
				RECIPROCALTIME--;
				if(RECIPROCALTIME == 0){//跳转到主页
					Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else{
					launcher_time.setText(RECIPROCALTIME+"");
					jumpHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 1000);
				}
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.reciprocalBt://跳过计时
			RECIPROCALTIME = 1;
			jumpHandler.sendEmptyMessage(MSG_FINISH_LAUNCHERACTIVITY);
			break;
		}
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
	}
}
