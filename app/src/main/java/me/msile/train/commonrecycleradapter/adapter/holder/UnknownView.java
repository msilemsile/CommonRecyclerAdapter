package me.msile.train.commonrecycleradapter.adapter.holder;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UnknownView extends TextView {
    public UnknownView(Context context) {
        this(context, null);
    }

    public UnknownView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public UnknownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText("--未知类型--");
        setTextColor(android.graphics.Color.RED);
        setTextSize(16);
        setGravity(Gravity.CENTER);
        int padding = 20;
        setPadding(padding, padding, padding, padding);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

}
