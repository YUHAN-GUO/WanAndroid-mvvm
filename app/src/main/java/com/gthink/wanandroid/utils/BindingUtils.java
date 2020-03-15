package com.gthink.wanandroid.utils;

import androidx.databinding.BindingAdapter;
import androidx.core.content.ContextCompat;
import android.text.TextUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.base.gyh.baselib.data.https.GlideApp;
import com.gthink.wanandroid.R;

/**
 * Created by GUOYH on 2019/6/1.
 */
public class BindingUtils {
    @BindingAdapter({"imageUrl"})
    public static void loadWelfareImage(ImageView imgView, String url) {
        if(!TextUtils.isEmpty(url)) {
//            int columnWidth = AppUtil.getColumnWidth(imgView.getContext(), 2, 16);
//            url = AppUtil.buildRequestImageParam(imgView.getContext(), url, columnWidth);
//            imgView.setLayoutParams(new FrameLayout.LayoutParams(columnWidth, columnWidth));
            GlideApp.with(imgView.getContext()).load(url).centerCrop()
//                    .placeholder(R.color.c_fafafa).error(R.color.c_fafafa)
                    .into(imgView);
        }
    }

    @BindingAdapter({"randomColor"})
    public static void randomColorTv(TextView tv, boolean randomColor) {
        if (randomColor){
            tv.setTextColor(RandomColorUtils.randomColor());
        }else{
            tv.setTextColor(ContextCompat.getColor(tv.getContext(),R.color.color_black));
        }
    }
}
