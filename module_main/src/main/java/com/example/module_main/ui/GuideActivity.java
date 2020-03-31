package com.example.module_main.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.example.module_main.R;
import com.library.library_base.activity.BaseGuideActivity;
import com.xuexiang.xui.utils.ResUtils;

import java.util.ArrayList;
import java.util.List;


public class GuideActivity extends BaseGuideActivity {

    public static void start(Context context){
        context.startActivity(new Intent(context,GuideActivity.class));
    }


    @Override
    public List<Object> getGuideSource() {
        List<Object> listRes = new ArrayList<Object>();
        listRes.add(ResUtils.getDrawable(R.drawable.main_guide_img_1));
        listRes.add(ResUtils.getDrawable(R.drawable.main_guide_img_2));
        listRes.add(ResUtils.getDrawable(R.drawable.main_guide_img_3));
        listRes.add(ResUtils.getDrawable(R.drawable.main_guide_img_4));
        return listRes;
    }

    @Override
    public void popToBack() {
        MainActivity.start(this);
    }
}
