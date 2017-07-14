package com.bc.caibiao.ui.me;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;

import com.bc.caibiao.R;
import com.bc.caibiao.adapter.MessageAdapter;
import com.bc.caibiao.base.Constant;
import com.bc.caibiao.base.SPTag;
import com.bc.caibiao.model.Message;
import com.bc.caibiao.model.MessageModel;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.ui.BaseActivity;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.ui.qiming.BaseRecyclerAdapter;
import com.bc.caibiao.ui.qiming.TaskDetailActivity;
import com.bc.caibiao.utils.SP;
import com.bc.caibiao.view.PtrFrameLayout;

import java.util.ArrayList;

import butterknife.Bind;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created  by panghuan on 2017/4/18.
 * 消息列表
 */

public class MessageActivity extends BaseActivity {
    PtrFrameLayout mPtrFrameLayout;

    @Bind(R.id.container_relativelayout)
    RelativeLayout mContainerLayout;

    private int currentPage = 1;
    private ArrayList<Message> mMessageList = new ArrayList<>();
    private MessageAdapter mMessageAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_message);

        mContainerLayout = (RelativeLayout) findViewById(R.id.container_relativelayout);
        mPtrFrameLayout = new PtrFrameLayout(this);
        mContainerLayout.addView(mPtrFrameLayout.getView());
        mPtrFrameLayout.setPtrRefreshListener(new PtrFrameLayout.PtrRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                loadMessageData();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        currentPage = 1;
        loadMessageData();
    }

    /*
    * 请求消息数据
    * */
    private void loadMessageData() {
        BCHttpRequest.getOtherInterface().getMessageListApi(Integer.valueOf(SP.getInstance().getString(SPTag.TAG_MEMBER_ID)), currentPage, Constant.PAGESIZE)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<MessageModel>() {
                    @Override
                    public void onCompleted() {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onError(Throwable e) {
                        mPtrFrameLayout.stopPtrRefresh();
                    }

                    @Override
                    public void onNext(MessageModel messageModel) {
                        mPtrFrameLayout.stopPtrRefresh();

                        if (currentPage == 1) {
                            mMessageList.clear();
                        }

                        if(messageModel.data == null) {
                            mMessageAdapter.canLoadMore(false);
                            mMessageAdapter.notifyDataSetChanged();
                        } else {
                            mMessageList.addAll(messageModel.data.data);

                            loadAdapter();

                            if (messageModel.data.data.size() < messageModel.data.pageSize) {
                                mMessageAdapter.canLoadMore(false);
                            } else {
                                mMessageAdapter.canLoadMore(true);
                            }
                        }
                    }
                });
    }

    /*
    * 加载数据
    * */
    private void loadAdapter() {
        if (mMessageAdapter == null) {
            mMessageAdapter = new MessageAdapter(MessageActivity.this, mMessageList);
            mPtrFrameLayout.setAdapter(mMessageAdapter);
            mMessageAdapter.setLoadMoreListener(new BaseRecyclerAdapter.RecyclerAdapterListener() {
                @Override
                public void OnLoadMore() {
                    currentPage++;
                    loadMessageData();
                }
            });

            mMessageAdapter.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                    int linkType = mMessageList.get(position).getLinkType();
                    Intent aIntent = new Intent();
                    switch (linkType){
                        case 1:
                            // 悬赏任务
                            aIntent.setClass(MessageActivity.this, TaskDetailActivity.class);
                            aIntent.putExtra("taskID", mMessageList.get(position).getLinkParams());
                            startActivity(aIntent);
                            break;
                        case 2:
                            //大师任务
                            aIntent.setClass(MessageActivity.this,MasterTaskDetailAct.class);
                            aIntent.putExtra("taskID", mMessageList.get(position).getLinkParams());
                            startActivity(aIntent);
                            break;
                        case 3:
                            //复查结果详情
                            aIntent.setClass(MessageActivity.this, RecheckDetailActivity.class);
                            aIntent.putExtra("recheckId", Integer.valueOf(mMessageList.get(position).getLinkParams()));
                            startActivity(aIntent);
                            break;
                        case 11:
                            //浏览器
                            aIntent.setClass(MessageActivity.this, WebViewActivity.class);
                            String url = mMessageList.get(position).getLinkParams();
                            String title = mMessageList.get(position).getLinkParamsName();
                            aIntent.putExtra("URL", url);
                            aIntent.putExtra("TITLE", title);
                            startActivity(aIntent);
                            break;
                        case 99:

                            break;
                    }
                }
            });

        } else {
            mMessageAdapter.setData(mMessageList);
            mMessageAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public BasePresenter initPresenter() {
        return null;
    }
}
