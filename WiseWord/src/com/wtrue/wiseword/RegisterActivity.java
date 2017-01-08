package com.wtrue.wiseword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.wtrue.bean.WWUser;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.utils.BmobUtils;

/**
 * 注册界面
 * @author Ben
 *
 */
public class RegisterActivity extends BaseActivity implements OnClickListener{
	/**
	 * 账号和密码输入框
	 */
	private EditText username_et,password_et,smscode_et;
	private Button register_bt,sms_bt,smssend_bt;
	private String rUserName;
	private String rPassWord;
	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initView();
	}
	private void initView(){
		username_et = (EditText) this.findViewById(R.id.username_et);
		password_et = (EditText) this.findViewById(R.id.password_et);
		smscode_et = (EditText) this.findViewById(R.id.smscode_et);
		register_bt = (Button) this.findViewById(R.id.register_bt);
		smssend_bt = (Button) this.findViewById(R.id.smssend_bt);
		sms_bt = (Button) this.findViewById(R.id.sms_bt);
		register_bt.setOnClickListener(this);
		sms_bt.setOnClickListener(this);
		smssend_bt.setOnClickListener(this);
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
		case R.id.register_bt://注册按钮
			rUserName = username_et.getText().toString().trim();
			rPassWord = password_et.getText().toString().trim();
			if(rUserName.equals("")||rUserName==null){
				Toast.makeText(RegisterActivity.this, "请正确输入账号...", Toast.LENGTH_SHORT).show();
				return;
			}
			if(rPassWord.equals("")||rPassWord.trim()==null){
				Toast.makeText(RegisterActivity.this, "请正确输入密码...", Toast.LENGTH_SHORT).show();
				return;
			}
			BmobUtils.userRegister(rUserName, rPassWord, new SaveListener<WWUser>() {
				
				@Override
				public void done(WWUser arg0, BmobException arg1) {
					// TODO Auto-generated method stub
					if(arg1 == null){
						Toast.makeText(RegisterActivity.this, "注册成功...:"+arg0.toString(), Toast.LENGTH_SHORT).show();
						Log.e("注册成功", arg0.toString()+"");
					}else{
						if(arg1.getErrorCode() == 202){//错误code如果是202的话，那么就是用户输入的账号已经注册过了。
							Toast.makeText(RegisterActivity.this, "此账号已经注册，请重新输入账号...", Toast.LENGTH_SHORT).show();
							return;
						}
					}
				}
			});
			break;
		case R.id.sms_bt://验证验证码
			rUserName = username_et.getText().toString().trim();
			BmobSMS.verifySmsCode(rUserName, smscode_et.getText().toString().trim(), new UpdateListener() {
				
				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if(arg0 == null){//验证码成功
						
					}else{
						Log.e("验证验证码", false+"");
					}
				}
			});
			break;
		case R.id.smssend_bt://发送验证码
			rUserName = username_et.getText().toString().trim();
			BmobSMS.requestSMSCode(rUserName, "笑语", new QueryListener<Integer>() {
				  
				@Override
				public void done(Integer smsId, BmobException ex) {
					// TODO Auto-generated method stub
					if(ex==null){//验证码发送成功
			            Log.i("bmob", "短信id："+smsId);//用于查询本次短信发送详情
			        }else{
			        	Log.e("发送验证码", ex.toString()+"");
			        }

				}
			});
			break;
		}
		
	}

}
