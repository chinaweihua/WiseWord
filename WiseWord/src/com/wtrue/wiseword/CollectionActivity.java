package com.wtrue.wiseword;

import java.util.List;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.wtrue.bean.Collection;
import com.wtrue.netmonitor.NetUtils.NetType;

public class CollectionActivity extends BaseActivity{
	/**
	 * 收藏数据显示ListView
	 */
	private ListView list;
	private List<Collection> data;
	private listAdapter adapter;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.collection_layout);
		list = (ListView) this.findViewById(R.id.list);
		data = (List<Collection>) getIntent().getBundleExtra("data").getSerializable("CollectionData");
		adapter = new listAdapter();
		list.setAdapter(adapter);
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		
	}
	private class listAdapter extends BaseAdapter{
		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return data == null?0:data.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}
		private class ViewHolder{
			private TextView text;
		}
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			ViewHolder vh = null;
			if(convertView == null){
				convertView = View.inflate(CollectionActivity.this, R.layout.conllection_item_layout, null);
				vh = new ViewHolder();
				vh.text = (TextView) convertView.findViewById(R.id.collection_text);
				convertView.setTag(vh);
			}else{
				vh = (ViewHolder) convertView.getTag();
			}
			vh.text.setText(data == null?"没有收藏":data.get(position).getTitle());
			return convertView;
		}
		
	}
}
