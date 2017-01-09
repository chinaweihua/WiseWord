package com.wtrue.wiseword;


import com.wtrue.constants.ProjectConstants;
import com.wtrue.netmonitor.NetUtils.NetType;
import com.wtrue.utils.SpUtil;
import com.wtrue.views.timelyview.TimelyView;
import com.wtrue.widget.CirclePageIndicator;
import com.wtrue.widget.ColorShades;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
/**
 * 启动欢迎界面 
 * @author Ben
 */
public class WelcomeActivity extends BaseActivity implements OnClickListener{
	/**
	 * 计时View
	 */
	private TimelyView launcher_time;
	private final int MSG_FINISH_LAUNCHERACTIVITY = 10001;
	private boolean isSliderAnimation = false;
	private boolean isIntent = false;//是否已经跳转
	private static final String SAVING_STATE_SLIDER_ANIMATION = "SliderAnimationSavingState";
	/**
	 * 启动页面跳转时间
	 */ 
	private int RECIPROCALTIME = 6;
	private Button reciprocalBt;
	private volatile ObjectAnimator objectAnimator = null;
	private boolean isFirst;//是不是第一次开启app 
	private ViewPager viewPager;
	private CirclePageIndicator mIndicator;//ViewPager指示View
	private RelativeLayout first_rl;//ViewPager的父layout
	private ImageView advertisement_iv;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		isFirst = (Boolean) SpUtil.get(WelcomeActivity.this, ProjectConstants.SPFIRST, false);
		Window window = getWindow();
	    window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		setContentView(R.layout.launcher_layout);
		initView();
	}
	private void initView(){
		launcher_time = (TimelyView) this.findViewById(R.id.launcher_time);
		reciprocalBt = (Button) this.findViewById(R.id.reciprocalBt);
		viewPager = (ViewPager) this.findViewById(R.id.wa_pager);
		first_rl = (RelativeLayout) this.findViewById(R.id.first_rl);
		mIndicator = (CirclePageIndicator) this.findViewById(R.id.indicator);
		advertisement_iv = (ImageView) this.findViewById(R.id.advertisement_iv);
		if(isFirst){//如果不是第一次打开app的话，不需要加载和显示ViewPager
			first_rl.setVisibility(View.GONE);//直接不显示ViewPager的父layout
			advertisement_iv.setVisibility(View.VISIBLE);
		}else{//如果是第一次打开app，那么需要隐藏广告ImageView
			advertisement_iv.setVisibility(View.GONE);
			first_rl.setVisibility(View.VISIBLE);
			initFirst();
		}
		reciprocalBt.setOnClickListener(this);
		if(objectAnimator == null){
			objectAnimator = launcher_time.animate(RECIPROCALTIME, RECIPROCALTIME-1);//设置开始数字和结束数字
			objectAnimator.setDuration(1000);//设置动画时间
			objectAnimator.start();//开始执行动画
		}
		jumpHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 1000);
	}
	/**
	 * 计时and跳转
	 */
	private Handler jumpHandler = new Handler(){

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case MSG_FINISH_LAUNCHERACTIVITY:
				RECIPROCALTIME--;
				if(RECIPROCALTIME == 1||RECIPROCALTIME == 0){//跳转到主页
					if(isIntent){
						return;
					}
					isIntent = true;
					Intent intent = new Intent(WelcomeActivity.this,MainActivity.class);
					startActivity(intent);
			 		finish();
				}else{
					objectAnimator = launcher_time.animate(RECIPROCALTIME, RECIPROCALTIME-1);
					objectAnimator.setDuration(1000);
					objectAnimator.start();
					jumpHandler.sendEmptyMessageDelayed(MSG_FINISH_LAUNCHERACTIVITY, 1000);
				}
				break;

			default:
				break;
			}
		}
	};
	
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		switch (arg0.getId()) {
		case R.id.reciprocalBt://跳过计时
			if(RECIPROCALTIME == 1||RECIPROCALTIME == 0){
				return;
			}
			RECIPROCALTIME = 2;
			jumpHandler.sendEmptyMessage(MSG_FINISH_LAUNCHERACTIVITY);
			break;
		}
	}

	@Override
	protected void onNetworkConnected(NetType type) { //当程序运行时网络连接时回调
		// TODO Auto-generated method stub
	}

	@Override
	protected void onNetworkDisConnected() {//当程序运行时网络断开时回调
		// TODO Auto-generated method stub
	}
	/**
	 * 加载第一次启动app的所有设置
	 */
	private void initFirst(){
		viewPager.setAdapter(new ViewPagerAdapter(R.array.icon,R.array.titles, R.array.hints));
		mIndicator.setViewPager(viewPager);
		viewPager.setPageTransformer(true, new CustomPageTransformer());
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int arg0) {
				// TODO Auto-generated method stub
				mIndicator.setCurrentItem(arg0);
			}
			
			@Override
			public void onPageScrolled(int arg0, float positionOffset, int arg2) {
				// TODO Auto-generated method stub
				 View landingBGView = findViewById(R.id.landing_backgrond);
	                int colorBg[] = getResources().getIntArray(R.array.landing_bg);
	                ColorShades shades = new ColorShades();
	                shades.setFromColor(colorBg[arg0 % colorBg.length])
	                        .setToColor(colorBg[(arg0 + 1) % colorBg.length])
	                        .setShade(positionOffset);
	                landingBGView.setBackgroundColor(shades.generate());
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	  public class ViewPagerAdapter extends PagerAdapter {

	        private int iconResId, titleArrayResId, hintArrayResId;

	        public ViewPagerAdapter(int iconResId, int titleArrayResId, int hintArrayResId) {
	            this.iconResId = iconResId;
	            this.titleArrayResId = titleArrayResId;
	            this.hintArrayResId = hintArrayResId;
	        }

	        @Override
	        public int getCount() {
	            return getResources().getIntArray(iconResId).length;
	        }

	        @Override
	        public boolean isViewFromObject(View view, Object object) {
	            return view == object;
	        }

	        @Override
	        public Object instantiateItem(ViewGroup container, int position) {

	            String title = getResources().getStringArray(titleArrayResId)[position];
	            String hint = getResources().getStringArray(hintArrayResId)[position];
	            String icon = getResources().getStringArray(iconResId)[position];

	            View itemView = getLayoutInflater().inflate(R.layout.viewpager_item, container, false);

	            TextView iconView = (TextView) itemView.findViewById(R.id.landing_img_slide);
	            TextView titleView = (TextView)itemView.findViewById(R.id.landing_txt_title);
	            TextView hintView = (TextView)itemView.findViewById(R.id.landing_txt_hint);

	            iconView.setText(icon);
	            titleView.setText(title);
	            hintView.setText(hint);

	            container.addView(itemView);

	            return itemView;
	        }

	        @Override
	        public void destroyItem(ViewGroup container, int position, Object object) {
	            container.removeView((RelativeLayout) object);

	        }
	    }

	    public class CustomPageTransformer implements ViewPager.PageTransformer {


	        public void transformPage(View view, float position) {
	            int pageWidth = view.getWidth();

	            View imageView = view.findViewById(R.id.landing_img_slide);
	            View contentView = view.findViewById(R.id.landing_txt_hint);
	            View txt_title = view.findViewById(R.id.landing_txt_title);

	            if (position < -1) { // [-Infinity,-1)
	                // This page is way off-screen to the left
	            } else if (position <= 0) { // [-1,0]
	                // This page is moving out to the left

	                // Counteract the default swipe
	                setTranslationX(view,pageWidth * -position);
	                if (contentView != null) {
	                    // But swipe the contentView
	                    setTranslationX(contentView,pageWidth * position);
	                    setTranslationX(txt_title,pageWidth * position);

	                    setAlpha(contentView,1 + position);
	                    setAlpha(txt_title,1 + position);
	                }

	                if (imageView != null) {
	                    // Fade the image in
	                    setAlpha(imageView,1 + position);
	                }

	            } else if (position <= 1) { // (0,1]
	                // This page is moving in from the right

	                // Counteract the default swipe
	                setTranslationX(view, pageWidth * -position);
	                if (contentView != null) {
	                    // But swipe the contentView
	                    setTranslationX(contentView,pageWidth * position);
	                    setTranslationX(txt_title,pageWidth * position);

	                    setAlpha(contentView, 1 - position);
	                    setAlpha(txt_title, 1 - position);

	                }
	                if (imageView != null) {
	                    // Fade the image out
	                    setAlpha(imageView,1 - position);
	                }

	            }
	        }
	    }

	    /**
	     * Sets the alpha for the view. The alpha will be applied only if the running android device OS is greater than honeycomb.
	     * @param view - view to which alpha to be applied.
	     * @param alpha - alpha value.
	     */
	    private void setAlpha(View view, float alpha) {

	        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
	            view.setAlpha(alpha);
	        }
	    }

	    /**
	     * Sets the translationX for the view. The translation value will be applied only if the running android device OS is greater than honeycomb.
	     * @param view - view to which alpha to be applied.
	     * @param translationX - translationX value.
	     */
	    private void setTranslationX(View view, float translationX) {
	        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB && ! isSliderAnimation) {
	            view.setTranslationX(translationX);
	        }
	    }

	    public void onSaveInstanceState(Bundle outstate) {

	        if(outstate != null) {
	            outstate.putBoolean(SAVING_STATE_SLIDER_ANIMATION,isSliderAnimation);
	        }

	        super.onSaveInstanceState(outstate);
	    }

	    public void onRestoreInstanceState(Bundle inState) {

	        if(inState != null) {
	            isSliderAnimation = inState.getBoolean(SAVING_STATE_SLIDER_ANIMATION,false);
	        }
	        super.onRestoreInstanceState(inState);

	    }
}
