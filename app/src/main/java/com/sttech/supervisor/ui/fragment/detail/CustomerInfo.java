package com.sttech.supervisor.ui.fragment.detail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sttech.supervisor.R;
import com.sttech.supervisor.ui.fragment.TFragment;

/**
 * function : 客户信息
 * Created by 韦国旺 on 2017/5/4.
 * Copyright (c) 2017 All Rights Reserved.
 */


public class CustomerInfo extends TFragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_customer_info, container, false);
        return view;
    }

}