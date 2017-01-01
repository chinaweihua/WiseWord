package com.wtrue.application;



import com.wtrue.netmonitor.NetStateReceiver;

import android.app.Application;

public class WtApplication extends Application{

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		super.onCreate();
        /*开启网络广播监听*/
        NetStateReceiver.registerNetworkStateReceiver(this);
	}

	@Override
	public void onLowMemory() {
		// TODO Auto-generated method stub
		super.onLowMemory();
		NetStateReceiver.unRegisterNetworkStateReceiver(this);
        android.os.Process.killProcess(android.os.Process.myPid());
	}
	
}
