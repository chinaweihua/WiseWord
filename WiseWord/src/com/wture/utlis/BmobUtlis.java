package com.wture.utlis;

import cn.bmob.v3.listener.SaveListener;

import com.wtrue.bean.WWUser;

public class BmobUtlis {
	/**
	 * 注册
	 * @param userName	用户名
	 * @param passWord	密码
	 * @param listener	请求回调接口
	 */
	public static void userRegister(String userName,String passWord,SaveListener<WWUser> listener){
		WWUser user = new WWUser();
		user.setUsername(userName);
		user.setPassword(passWord);
		user.signUp(listener);
	}
	/**
	 * 登陆
	 * @param userName	用户名
	 * @param passWord	密码
	 * @param listener	请求回调接口
	 */
	public static void userLogin(String userName,String passWord,SaveListener<WWUser> listener){
		WWUser user = new WWUser();
		user.setUsername(userName);
		user.setPassword(passWord);
		user.login(listener);
	}
}
