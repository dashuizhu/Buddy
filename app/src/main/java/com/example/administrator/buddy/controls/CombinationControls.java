package com.example.administrator.buddy.controls;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.administrator.buddy.R;

/**
 * Created by zhuj on 2017/8/23 14:42.
 */
public class CombinationControls extends RelativeLayout {
    protected TextView title;
    protected TextView content;
    protected ImageView detailed;

    public CombinationControls(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.merge_setup,this,true);
        title = (TextView) findViewById(R.id.tv_setup1);
        content = (TextView) findViewById(R.id.tv_setup2);
        detailed = (ImageView) findViewById(R.id.btn_setup1);
        TypedArray attributes =context.obtainStyledAttributes(attrs,R.styleable.CombinationControls);
        if (attributes!=null){
            String  tvtext = attributes.getString(R.styleable.CombinationControls_merge_setup_text);
            String  tvrighttext =attributes.getString(R.styleable.CombinationControls_merge_setup_right_text);
            int  tvcolor = attributes.getInt(R.styleable.CombinationControls_merge_setup_text_color,
                    R.color.colorblack);
             int imageView =attributes.getResourceId(R.styleable.CombinationControls_right_button,R.mipmap.list_right_arrow);
            title.setText(tvtext);
            title.setTextColor(tvcolor);
            content.setText(tvrighttext);
            content.setTextColor(tvcolor);
            detailed.setBackgroundResource(imageView);
        }
        attributes.recycle();
    }

    public void setButtonClickListener(OnClickListener onClickListener  ){
        if(onClickListener !=null ){
            detailed.setOnClickListener(onClickListener);
        }

    }
    public TextView gettitle(){
        return title;
    }
    public TextView getcontent(){
        return content;
    }
    public ImageView getdetailed(){
        return detailed;
    }
}
