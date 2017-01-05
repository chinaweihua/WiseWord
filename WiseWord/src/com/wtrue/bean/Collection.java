package com.wtrue.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * 用户“收藏”实体类
 * @author Ben
 *
 */
public class Collection extends BmobObject{
	
	private static final long serialVersionUID = 1L;
	
	private String title;//笑话（可能不一定是笑话）标题

	private String content;// 笑话内容
	
	private WWUser author;//收藏的用户

	private BmobFile image;//笑话的图片  如果是null那么就是没有图片只有文字的笑话

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public BmobFile getImage() {
		return image;
	}

	public void setImage(BmobFile image) {
		this.image = image;
	}

	public WWUser getAuthor() {
		return author;
	}

	public void setAuthor(WWUser author) {
		this.author = author;
	}
	
}
