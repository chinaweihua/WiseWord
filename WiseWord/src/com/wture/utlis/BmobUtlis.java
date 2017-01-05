package com.wture.utlis;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.wtrue.bean.Collection;
import com.wtrue.bean.WWUser;
import com.wtrue.interfaces.BmobExtendListener;

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
	/**
	 * 修改密码
	 * @param oldPwd	旧密码
	 * @param newPwd	新密码
	 * @param listener	请求回调接口
	 */
	public static void userUpdatePassword(String oldPwd,String newPwd,UpdateListener listener){
		BmobUser.updateCurrentUserPassword(oldPwd, newPwd, listener);
	}
	
	/**
	 * 更新用户资料
	 * @param headImage	头像
	 * @param listener	请求回调接口
	 * 			目前只添加了个头像的字段，如果需要其他的资料需要在实体类中添加字段，参数也需要对应添加。
	 */
	public static void userUpdate(String headImage,String nickname,UpdateListener listener){
		WWUser user = new WWUser();
		user.setNickname(nickname);
		user.setHeadImage(headImage);
		WWUser oldUser = BmobUser.getCurrentUser(WWUser.class);
		user.update(oldUser.getObjectId(), listener);
	}
	
	/**
	 * 退出登陆
	 * @param listener 请求回调接口
	 */
	public static void userLogOut(BmobExtendListener listener){
		BmobUser.logOut();
		WWUser user = BmobUser.getCurrentUser(WWUser.class);
		if(user == null){//成功退出
			listener.logOut();
		}else{//退出失败
			listener.errorLogOut();
		}
	}
	/**
	 * 收藏
	 * @param title		标题
	 * @param content	内容
	 * @param image		图片
	 */
	public static void userColllection(String title,String content,BmobFile image,SaveListener<String> listener){
		WWUser user = BmobUser.getCurrentUser(WWUser.class);
		Collection collection = new Collection();
		collection.setTitle(title);
		collection.setContent(content);
		collection.setImage(image);
		collection.setAuthor(user);
		collection.save(listener);
	}
	/**
	 * 收藏
	 * @param title		标题
	 * @param content	内容
	 */
	public static void userColllection(String title,String content,SaveListener<String> listener){
		WWUser user = BmobUser.getCurrentUser(WWUser.class);
		Collection collection = new Collection();
		collection.setTitle(title);
		collection.setContent(content);
		collection.setAuthor(user);
		collection.save(listener);
	}
	
	/**
	 * 收藏
	 * @param title		标题
	 * @param image		图片
	 */
	public static void userColllection(String title,BmobFile image,SaveListener<String> listener){
		WWUser user = BmobUser.getCurrentUser(WWUser.class);
		Collection collection = new Collection();
		collection.setTitle(title);
		collection.setImage(image);
		collection.setAuthor(user);
		collection.save(listener);
	}
}
