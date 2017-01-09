package com.wtrue.wiseword;

import cn.bmob.v3.Bmob;

import com.wtrue.constants.BmobConstants;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.views.timelyview.TimelyView;

import android.animation.ObjectAnimator;
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
	private TimelyView launcher_time;
	private final int MSG_FINISH_LAUNCHERACTIVITY = 10001;
	/**
	 * 启动页面跳转时间
	 */
	private int RECIPROCALTIME = 6;
	private Button reciprocalBt;
	private volatile    ObjectAnimator objectAnimator = null;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.launcher_layout);
		launcher_time = (TimelyView) this.findViewById(R.id.launcher_time);
		reciprocalBt = (Button) this.findViewById(R.id.reciprocalBt);
		reciprocalBt.setOnClickListener(this);
		if(objectAnimator == null){
			objectAnimator = launcher_time.animate(RECIPROCALTIME, RECIPROCALTIME-1);
			objectAnimator.setDuration(1000);
			objectAnimator.start();
		}
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
				if(RECIPROCALTIME == 1){//跳转到主页
					Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
					finish();
				}else{
					objectAnimator = launcher_time.animate(RECIPROCALTIME, RECIPROCALTIME-1);
					objectAnimator.setDuration(1000);
					objectAnimator.start();
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
	protected void onNetworkConnected(NetType type) { //当程序运行时网络连接时回调
		// TODO Auto-generated method stub
	}

	@Override
	protected void onNetworkDisConnected() {//当程序运行时网络断开时回调
		// TODO Auto-generated method stub
	}
}
