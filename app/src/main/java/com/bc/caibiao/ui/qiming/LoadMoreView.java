package com.bc.caibiao.ui.qiming;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.bc.caibiao.R;

public class LoadMoreView {

	private Context mContext;
	private View mView;
	private TextView mTextView;

	@SuppressLint("InflateParams")
	public LoadMoreView(Context context) {
		mContext = context;
		mView = LayoutInflater.from(mContext).inflate(
				R.layout.bbs_loadmore_layout, null);
		mTextView = (TextView) mView.findViewById(R.id.loadmore_text);
		mTextView.setText("加载中...");
	}

	public View getView() {
		return mView;
	}

}
