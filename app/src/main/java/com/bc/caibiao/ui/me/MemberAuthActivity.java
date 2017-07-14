package com.bc.caibiao.ui.me;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import com.bc.caibiao.R;
import com.bc.caibiao.model.FieldError;
import com.bc.caibiao.model.Member;
import com.bc.caibiao.model.MemberAuthApply;
import com.bc.caibiao.request.BCHttpRequest;
import com.bc.caibiao.request.HttpResponseHelper;
import com.bc.caibiao.request.subscribe.ResolveFailSubscribe;
import com.bc.caibiao.ui.BasePresenter;
import com.bc.caibiao.utils.SP;

/**
 * Created  on 2017/4/19.
 * 关于我们
 */

public class MemberAuthActivity extends Activity {
    private Member member;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            member = SP.getInstance().getMemberSP();
        } catch (Exception e) {
        }
        getMemberAuthInfo();
    }


    private void getMemberAuthInfo() {
        BCHttpRequest.getMemberInterface().viewMemberAuthApply(
                member.getMemberId()
        ).compose(HttpResponseHelper.<MemberAuthApply>getAllData())
                .subscribe(new ResolveFailSubscribe<MemberAuthApply>(this, "加载中") {
                    @Override
                    protected void _onFail(FieldError fieldError) {
                        //提交认证
                        Intent intent = new Intent();
                        intent.setClass(MemberAuthActivity.this, AddNewPeopleAuthActivity.class);
                        startActivity(intent);
                        finish();
                    }

                    @Override
                    protected void _onNext(MemberAuthApply member) {
                        Intent intent = new Intent();
                        if (member == null || member.getCheckResult() == MemberAuthApply.CHECKRESULT_DISAPPROVED
                                || member.getCheckResult() == MemberAuthApply.AUTHTYPE_IDENTITY) {
                            //提交认证
                            intent.setClass(MemberAuthActivity.this, AddNewPeopleAuthActivity.class);
                            startActivity(intent);
                        } else {
                            //认证详情
                            intent.setClass(MemberAuthActivity.this, PeopleAuthActivity.class);
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("MemberAuthApply", member);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                        finish();
                    }
                });
    }

}
