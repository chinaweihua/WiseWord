package com.wtrue.wiseword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.utlis.BmobUtlis;
/**
 * 修改密码
 * @author Ben
 *
 */
public class ResetActivity extends BaseActivity implements OnClickListener{
	private EditText old_et,new_et,newtwo_et;
	private Button submit_bt;
	private String oPassWord,nPassWord,ntPassWord;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reset_layout);
		initView();
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		old_et = (EditText) this.findViewById(R.id.old_et);
		new_et = (EditText) this.findViewById(R.id.new_et);
		newtwo_et = (EditText) this.findViewById(R.id.newtwo_et);
		submit_bt = (Button) this.findViewById(R.id.submit_bt);
		submit_bt.setOnClickListener(this);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.submit_bt://修改密码提交按钮
			oPassWord = old_et.getText().toString().trim();
			nPassWord = new_et.getText().toString().trim();
			ntPassWord = newtwo_et.getText().toString().trim();
			if(oPassWord.equals("")||oPassWord == null){
				Toast.makeText(ResetActivity.this, "请输入旧密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(nPassWord.equals("")||nPassWord == null){
				Toast.makeText(ResetActivity.this, "请输入新的密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(ntPassWord.equals("")||ntPassWord == null){
				Toast.makeText(ResetActivity.this, "请再次输入新的密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(!nPassWord.equals(ntPassWord)){
				Toast.makeText(ResetActivity.this, "两次输入的密码不一致,请确定后重新输入...", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobUtlis.userUpdatePassword(oPassWord, nPassWord, new UpdateListener() {
				
				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if(arg0 == null){//修改密码成功
						finish();
					}else{
						Toast.makeText(ResetActivity.this, arg0.toString(), Toast.LENGTH_SHORT).show();
					}
					
				}
			});
			break;

		default:
			break;
		}
	}

}
