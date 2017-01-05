package com.wtrue.bean;

import cn.bmob.v3.BmobUser;

/**
 * 用户实体类
 * @author Ben
 *	BmobUser已经账号密码的字段了，如果需要其他用户资料需要在这个实体类中添加字段
 */
public class WWUser extends BmobUser{
	private static final long serialVersionUID = 1L;
	private String headImage;//头像

	public String getHeadImage() {
		return headImage;
	}

	public void setHeadImage(String headImage) {
		this.headImage = headImage;
	}
	
	@Override
	public String toString() {
		return getUsername()+"\n"+getObjectId()+"\n"+getSessionToken()+"\n"+getEmailVerified();
	}
}
