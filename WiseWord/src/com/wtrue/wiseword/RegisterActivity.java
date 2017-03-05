package com.wtrue.wiseword;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.OvershootInterpolator;
import android.widget.Button;
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
import com.wtrue.widget.progressbutton.IndeterminateProgressButton;
import com.wtrue.widget.progressbutton.MorphingButton;

import dym.unique.com.springinglayoutlibrary.handler.SpringTouchRippleHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingNotificationJumpHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchDragHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchPointHandler;
import dym.unique.com.springinglayoutlibrary.handler.SpringingTouchScaleHandler;
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
	private SpringingEditText username_et, password_et, smscode_et,
			verification_et;
	private Button register_bt, sms_bt, smssend_bt;
	private String rUserName;
	private String rPassWord;
	private String rVeriFication;
	private SpringingImageView simg_back;
	private SpringingRelativeLayout srl_actionBar = null;
	private SpringingLinearLayout sll_mainContainer;
	private SpringingImageView simg_avatarMan = null;
	private IndeterminateProgressButton btnMorph1, btnMorph2;
	/**
	 * 是否发送过验证码
	 */
	private boolean isSendVer = false;
	/**
	 * 验证码按钮状态
	 */
	private int mMorphCounter1 = 1;
	/**
	 * 是否有网络连接
	 */
	private boolean isNetworkConnected = true;

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
		verification_et = (SpringingEditText) this
				.findViewById(R.id.verification_et);
		sms_bt = (Button) this.findViewById(R.id.sms_bt);
		sll_mainContainer = (SpringingLinearLayout) this
				.findViewById(R.id.sll_mainContainer);
		simg_avatarMan = (SpringingImageView) this
				.findViewById(R.id.simg_avatarMan);
		simg_back = (SpringingImageView) this.findViewById(R.id.simg_back);
		srl_actionBar = (SpringingRelativeLayout) this
				.findViewById(R.id.srl_actionBar);
		btnMorph1 = (IndeterminateProgressButton) this
				.findViewById(R.id.btnMorph1);
		btnMorph2 = (IndeterminateProgressButton) this
				.findViewById(R.id.btnMorph2);
		btnMorph1.setOnClickListener(this);
		btnMorph2.setOnClickListener(this);
		register_bt.setOnClickListener(this);
		sms_bt.setOnClickListener(this);
		smssend_bt.setOnClickListener(this);
		simg_back.setOnClickListener(this);
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
						.setOnlyOnChildren(true, username_et, password_et,
								verification_et));
		srl_actionBar.getSpringingHandlerController().addSpringingHandler(
				new SpringTouchRippleHandler(this, srl_actionBar)
						.setOnlyOnChildren(true, simg_back));
		simg_back.getSpringingHandlerController().addSpringingHandler(
				new SpringingTouchPointHandler(this, simg_back)
						.setAngle(SpringingTouchPointHandler.ANGLE_LEFT));
		simg_avatarMan.getSpringingHandlerController().addSpringingHandler(
				new SpringingTouchScaleHandler(this, simg_avatarMan));
		morphToSquare(btnMorph1, 0);
		morphToSquare(btnMorph2, 0);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
	}

	@Override
	protected void onNetworkConnected(NetType type) {
		// TODO Auto-generated method stub
		isNetworkConnected = true;// 有网络连接
	}

	@Override
	protected void onNetworkDisConnected() {
		// TODO Auto-generated method stub
		isNetworkConnected = false;
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.register_bt:// 注册按钮
			rUserName = username_et.getText().toString().trim();
			rPassWord = password_et.getText().toString().trim();
			if (rUserName.equals("") || rUserName == null
					|| !RegexUtils.isMobileExact(rUserName)) {
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

			break;
		case R.id.btnMorph1:// 验证验证码
			onMorphButton1Clicked(btnMorph1);
			break;
		case R.id.btnMorph2:// 发送验证码
			if (isNetworkConnected == false) {
				Toast.makeText(RegisterActivity.this, "没有网络连接...",
						Toast.LENGTH_SHORT).show();
				return;
			}
			rUserName = username_et.getText().toString().trim();
			if (!RegexUtils.isMobileExact(rUserName) || isSendVer == true) {
				new SpringingNotificationJumpHandler(this, username_et)
						.start(1);
				Toast.makeText(RegisterActivity.this, "请确认输入的手机号码是否正确...",
						Toast.LENGTH_SHORT).show();
				return;
			}
			onMorphButton1Clicked(btnMorph2);
			BmobSMS.requestSMSCode(rUserName, "笑语",
					new QueryListener<Integer>() {

						@Override
						public void done(Integer smsId, BmobException ex) {
							// TODO Auto-generated method stub
							if (ex == null) {// 验证码发送成功
								isSendVer = true;
								Log.i("bmob", "短信id：" + smsId);// 用于查询本次短信发送详情
							} else {
								Log.e("发送验证码", ex.toString() + "");
								isSendVer = false;
							}

						}
					});

			break;
		case R.id.simg_back:
			finish();
			break;
		}

	}

	/**
	 * 切换验证码按钮
	 * 
	 * @param btnMorph
	 */
	private void onMorphButton1Clicked(
			final IndeterminateProgressButton btnMorph) {
		if (mMorphCounter1 == 0) {
			mMorphCounter1++;
			morphToSquare(btnMorph, 500);
		} else if (mMorphCounter1 == 1) {
			mMorphCounter1 = 0;
			simulateProgress1(btnMorph);
		}
	}

	/**
	 * 验证验证码按钮设置
	 * 
	 * @param btnMorph
	 * @param duration
	 */
	private void morphToSquare(final IndeterminateProgressButton btnMorph,
			int duration) {
		MorphingButton.Params square = MorphingButton.Params.create()
				.duration(duration)
				.cornerRadius(dimen(R.dimen.mb_corner_radius_2))
				.color(color(R.color.mb_blue))
				.colorPressed(color(R.color.mb_blue_dark));
		btnMorph.morph(square);
	}

	private void simulateProgress1(
			@NonNull final IndeterminateProgressButton button) {
		int progressColor1 = color(R.color.holo_blue_bright);
		int progressColor2 = color(R.color.holo_green_light);
		int progressColor3 = color(R.color.holo_orange_light);
		int progressColor4 = color(R.color.holo_red_light);
		int color = color(R.color.mb_gray);
		int progressCornerRadius = dimen(R.dimen.mb_corner_radius_4);
		int width = dimen(R.dimen.mb_width_200);
		int height = dimen(R.dimen.mb_height_8);
		int duration = 500;

		Handler handler = new Handler();
		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				Log.e("完成======？", true + "");
				morphToSuccess(button);
				button.unblockTouch();// 恢复点击
			}
		}, 4000);

		button.blockTouch(); // prevent user from clicking while button is in
								// progress
		button.morphToProgress(color, progressCornerRadius, width, height,
				duration, progressColor1, progressColor2, progressColor3,
				progressColor4);
	}

	private void morphToSuccess(final IndeterminateProgressButton btnMorph) {
		MorphingButton.Params circle = MorphingButton.Params.create()
				.duration(500).cornerRadius(dimen(R.dimen.mb_height_56))
				.width(dimen(R.dimen.mb_height_56))
				.height(dimen(R.dimen.mb_height_56))
				.color(color(R.color.mb_green))
				.colorPressed(color(R.color.mb_green_dark))
				.icon(R.drawable.ic_done);
		btnMorph.morph(circle);
	}
}
