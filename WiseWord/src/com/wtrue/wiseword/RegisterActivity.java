package com.wtrue.wiseword;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import cn.bmob.v3.BmobSMS;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.QueryListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bencao.utils.RegexUtils;
import com.wtrue.bean.WWUser;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.utils.BmobUtils;

import dym.unique.com.springinglayoutlibrary.handler.SpringTouchRippleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingNotificationJumpHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchDragHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchPointHandler;
import dym.unique.com.springinglayoutlibrary.view.SpringingEditText;
import dym.unique.com.springinglayoutlibrary.view.SpringingImageView;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingLinearLayout;
import dym.unique.com.springinglayoutlibrary.viewgroup.SpringingRelativeLayout;

/**
 * 注册界面
 * 
 * @author Ben
 * 
 */
public class RegisterActivity extends BaseActivity implements OnClickListener {
	/**
	 * 账号和密码输入框
	 */
	private SpringingEditText username_et, password_et, smscode_et;
	private Button register_bt, sms_bt, smssend_bt;
	private String rUserName;
	private String rPassWord;
	private SpringingImageView simg_back;
	private SpringingRelativeLayout srl_actionBar = null;
	private SpringingLinearLayout sll_mainContainer;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.register_layout);
		initView();
	}

	private void initView() {
		username_et = (SpringingEditText) this.findViewById(R.id.username_et);
		password_et = (SpringingEditText) this.findViewById(R.id.password_et);
		smscode_et = (SpringingEditText) this.findViewById(R.id.smscode_et);
		register_bt = (Button) this.findViewById(R.id.register_bt);
		smssend_bt = (Button) this.findViewById(R.id.smssend_bt);
		sms_bt = (Button) this.findViewById(R.id.sms_bt);
		sll_mainContainer = (SpringingLinearLayout) this
				.findViewById(R.id.sll_mainContainer);
		simg_back = (SpringingImageView) this.findViewById(R.id.simg_back);
		srl_actionBar = (SpringingRelativeLayout) this
				.findViewById(R.id.srl_actionBar);
		register_bt.setOnClickListener(this);
		sms_bt.setOnClickListener(this);
		smssend_bt.setOnClickListener(this);
		sll_mainContainer
				.getSpringingHandlerController()
				.addSpringingHandler(
						new SpringingTouchDragHandler(this, sll_mainContainer)
								.setBackInterpolator(
										new OvershootInterpolator())
								.setBackDuration(
										SpringingTouchDragHandler.DURATION_LONG)
								.setDirection(
										SpringingTouchDragHandler.DIRECTOR_BOTTOM
												| SpringingTouchDragHandler.DIRECTOR_TOP)
								.setMinDistance(
										0,
										(int) TypedValue.applyDimension(
												TypedValue.COMPLEX_UNIT_DIP,
												16, getResources()
														.getDisplayMetrics())));
		sll_mainContainer.getSpringingHandlerController().addSpringingHandler(
				new SpringTouchRippleHandler(this, sll_mainContainer)
						.setOnlyOnChildren(true, username_et, password_et));
		srl_actionBar.getSpringingHandlerController().addSpringingHandler(
				new SpringTouchRippleHandler(this, srl_actionBar)
						.setOnlyOnChildren(true, simg_back));
		simg_back.getSpringingHandlerController().addSpringingHandler(
				new SpringingTouchPointHandler(this, simg_back)
						.setAngle(SpringingTouchPointHandler.ANGLE_LEFT));
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
		case R.id.register_bt:// 注册按钮
			rUserName = username_et.getText().toString().trim();
			rPassWord = password_et.getText().toString().trim();
			if (rUserName.equals("") || rUserName == null || !RegexUtils.isMobileExact(rUserName)) {
				new SpringingNotificationJumpHandler(this, username_et)
						.start(1);
				Toast.makeText(RegisterActivity.this, "请正确输入手机号码...",
						Toast.LENGTH_SHORT).show();
				return;
			}
			if (rPassWord.equals("") || rPassWord.trim() == null) {
				new SpringingNotificationJumpHandler(this, password_et)
				.start(1);
				Toast.makeText(RegisterActivity.this, "请正确输入密码...",
						Toast.LENGTH_SHORT).show();
				return;
			}
			BmobUtils.userRegister(rUserName, rPassWord,
					new SaveListener<WWUser>() {

						@Override
						public void done(WWUser arg0, BmobException arg1) {
							// TODO Auto-generated method stub
							if (arg1 == null) {
								Toast.makeText(RegisterActivity.this,
										"注册成功...:" + arg0.toString(),
										Toast.LENGTH_SHORT).show();
								Log.e("注册成功", arg0.toString() + "");
							} else {
								if (arg1.getErrorCode() == 202) {// 错误code如果是202的话，那么就是用户输入的账号已经注册过了。
									Toast.makeText(RegisterActivity.this,
											"此账号已经注册，请重新输入账号...",
											Toast.LENGTH_SHORT).show();
									return;
								}
							}
						}
					});
			break;
		case R.id.sms_bt:// 验证验证码
			rUserName = username_et.getText().toString().trim();
			BmobSMS.verifySmsCode(rUserName, smscode_et.getText().toString()
					.trim(), new UpdateListener() {

				@Override
				public void done(BmobException arg0) {
					// TODO Auto-generated method stub
					if (arg0 == null) {// 验证码成功
						Log.d("验证验证码", "通过");

					} else {
						Log.e("验证验证码", false + "");
					}
				}
			});
			break;
		case R.id.smssend_bt:// 发送验证码
			rUserName = username_et.getText().toString().trim();
			BmobSMS.requestSMSCode(rUserName, "笑语",
					new QueryListener<Integer>() {

						@Override
						public void done(Integer smsId, BmobException ex) {
							// TODO Auto-generated method stub
							if (ex == null) {// 验证码发送成功
								Log.i("bmob", "短信id：" + smsId);// 用于查询本次短信发送详情
							} else {
								Log.e("发送验证码", ex.toString() + "");
							}

						}
					});
			break;
		}

	}

}
