package com.wtrue.interfaces;
/**
 * Bmob后端云扩展接口
 * @author Ben
 *
 */
public interface BmobExtendListener {
	/**
	 * 退出成功回调
	 */
	void logOut();
	/**
	 * 退出失败回调
	 */
	void errorLogOut();
}	
