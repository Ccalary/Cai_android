package com.bc.caibiao.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Administrator on 2017/3/22.
 */
public class SpaceItemDecoration extends RecyclerView.ItemDecoration{

    private int space;

    public SpaceItemDecoration(int space) {
        this.space = space;
    }


    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int itemCount = 3;
        int pos = parent.getChildAdapterPosition(view);

        outRect.left = 0;
        outRect.top = 0;
        outRect.bottom = 0;


        if (pos != (itemCount -1)) {
            outRect.right = space;
        } else {
            outRect.right = 0;
        }
    }
}
