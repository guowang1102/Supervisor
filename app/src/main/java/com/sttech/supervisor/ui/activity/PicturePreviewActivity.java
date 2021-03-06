package com.sttech.supervisor.ui.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.sttech.supervisor.Constant;
import com.sttech.supervisor.R;
import com.sttech.supervisor.dto.ImageDto;
import com.sttech.supervisor.ui.fragment.PictureImagePreviewFragment;
import com.sttech.supervisor.ui.widget.PreviewViewPager;

import java.util.ArrayList;
import java.util.List;

/**
 * function :
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class PicturePreviewActivity extends TActivity {
    private ImageButton left_back;
    private TextView tv_title;
    private PreviewViewPager viewPager;
    private List<ImageDto> images = new ArrayList<>();
    private int position = 0;
    private SimpleFragmentAdapter adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.picture_activity_external_preview);
        tv_title = (TextView) findViewById(R.id.tv_title);
        left_back = (ImageButton) findViewById(R.id.left_back);
        viewPager = (PreviewViewPager) findViewById(R.id.preview_pager);
        position = getIntent().getIntExtra(Constant.EXTRA_PREVIEW_POSITION, 0);
        images = (List<ImageDto>) getIntent().getSerializableExtra(Constant.EXTRA_PREVIEW_SELECT_LIST);
        left_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        initViewPageAdapterData();
    }

    private void initViewPageAdapterData() {
        tv_title.setText(position + 1 + "/" + images.size());
        adapter = new SimpleFragmentAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(position);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                tv_title.setText(position + 1 + "/" + images.size());
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    public class SimpleFragmentAdapter extends FragmentPagerAdapter {

        public SimpleFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            LocalMedia media = images.get(position);
//            String path = "";
//            if (media.isCut() && !media.isCompressed()) {
//                // 裁剪过
//                path = media.getCutPath();
//            } else if (media.isCompressed() || (media.isCut() && media.isCompressed())) {
//                // 压缩过,或者裁剪同时压缩过,以最终压缩过图片为准
//                path = media.getCompressPath();
//            } else {
//                path = media.getPath();
//            }
//            PictureImagePreviewFragment fragment = PictureImagePreviewFragment.getInstance(path, images);


            ImageDto imageDto = images.get(position);
            PictureImagePreviewFragment fragment = PictureImagePreviewFragment.getInstance(imageDto.getPath(), images);

            return fragment;
        }

        @Override
        public int getCount() {
            return images.size();
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


}
