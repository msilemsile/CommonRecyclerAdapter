package me.msile.lib.commonrecycleradapter.holder;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class UnknownView extends TextView {

    public UnknownView(Context context) {
        super(context);
        init();
    }

    public UnknownView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public UnknownView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setText("--未知类型--");
        setTextColor(Color.RED);
        setTextSize(16);
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
    }

}
