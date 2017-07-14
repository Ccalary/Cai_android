package com.bc.caibiao.widget.ConvenientBanner;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;

/**
 * Created by Sai on 15/8/4. 网络图片加载例子
 */
public class NetworkImageHolderView implements CBPageAdapter.Holder<String> {
	private ImageView imageView;

	@Override
	public View createView(Context context) {
		// 你可以通过layout文件来创建，也可以像我一样用代码创建，不一定是Image，任何控件都可以进行翻页
		LinearLayout linearLayout = new LinearLayout(context);
		linearLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
		imageView = new ImageView(context);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,LinearLayout.LayoutParams.MATCH_PARENT));
		imageView.setScaleType(ImageView.ScaleType.FIT_XY);
		linearLayout.addView(imageView);
		return linearLayout;
	}

	@Override
	public void UpdateUI(final Context context, final int position, String data, final String skipUrl, final String title) {

		Glide.with(context).load(data).into(imageView);

		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				// 点击事件

				if (mItemClickListener != null) {
					mItemClickListener.onItemClick();
				}
			}
		});
	}

	public interface ItemClickListener {
		public void onItemClick();
	}

	ItemClickListener mItemClickListener;

	public void setmItemClickListener(ItemClickListener mItemClickListener) {
		this.mItemClickListener = mItemClickListener;
	}

}
