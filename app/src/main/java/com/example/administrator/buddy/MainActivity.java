package com.example.administrator.buddy;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bingoogolapple.refreshlayout.BGARefreshLayout;
import com.example.administrator.buddy.ui.device.GuardFragment;

public class MainActivity extends FragmentActivity {
    LinearLayout mLayoutMain;
    View v = null;
    MainFragmentHabit mMainFragmentHabit;
    MainFragmentSetup mMainFragmentSetup;
    MainFragmentMap mMainFragmentMap;
    Fragment mMainFragmentBaby;
    Fragment mFragment;
    BGARefreshLayout mLayout;
    //TextView tvmap;
    //TextView tvhabit;
    //TextView tvsetup;
    //TextView tvbaby;
    @BindView(R.id.tv_habit) TextView mTvHabit;
    @BindView(R.id.tv_map) TextView mTvMap;
    @BindView(R.id.tv_baby) TextView mTvBaby;
    @BindView(R.id.tv_setup) TextView mTvSetup;

    @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLayoutMain = (LinearLayout) findViewById(R.id.layout_main);
        //cki(R.id.tv_habit);
        //select();
        onViewClicked(mTvHabit);
    }

    //protected void select() {
    //    View.OnClickListener listener = new View.OnClickListener() {
    //        @Override public void onClick(View v) {
    //            cki(v.getId());
    //        }
    //    };
    //    tvhabit.setOnClickListener(listener);
    //    tvmap.setOnClickListener(listener);
    //    tvsetup.setOnClickListener(listener);
    //    tvbaby.setOnClickListener(listener);
    //}
    //
    //protected void cki(int id) {
    //    FragmentManager manager = getSupportFragmentManager();
    //    FragmentTransaction ft = manager.beginTransaction();
    //    if (mFragment != null) {
    //        ft.hide(mFragment);
    //    }
    //    switch (id) {
    //        case R.id.tv_habit:
    //            tvhabit.setSelected(true);
    //            tvmap.setSelected(false);
    //            tvsetup.setSelected(false);
    //            tvbaby.setSelected(false);
    //            if (mMainFragmentHabit == null) {
    //                mMainFragmentHabit = new MainFragmentHabit();
    //                ft.add(R.id.layout_main, mMainFragmentHabit);
    //            } else {
    //                ft.show(mMainFragmentHabit);
    //            }
    //            mFragment = mMainFragmentHabit;
    //            ft.commitAllowingStateLoss();
    //            break;
    //        case R.id.tv_map:
    //            tvhabit.setSelected(false);
    //            tvmap.setSelected(true);
    //            tvsetup.setSelected(false);
    //            tvbaby.setSelected(false);
    //            if (mMainFragmentMap == null) {
    //                mMainFragmentMap = new MainFragmentMap();
    //                ft.add(R.id.layout_main, mMainFragmentMap);
    //            } else {
    //                ft.show(mMainFragmentMap);
    //            }
    //            mFragment = mMainFragmentMap;
    //            ft.commitAllowingStateLoss();
    //            break;
    //        case R.id.tv_setup:
    //            tvhabit.setSelected(false);
    //            tvmap.setSelected(false);
    //            tvsetup.setSelected(true);
    //            tvbaby.setSelected(false);
    //            if (mMainFragmentSetup == null) {
    //                mMainFragmentSetup = new MainFragmentSetup();
    //                ft.add(R.id.layout_main, mMainFragmentSetup);
    //            } else {
    //                ft.show(mMainFragmentSetup);
    //            }
    //            mFragment = mMainFragmentSetup;
    //            ft.commitAllowingStateLoss();
    //            break;
    //        case R.id.tv_baby:
    //            tvhabit.setSelected(false);
    //            tvmap.setSelected(false);
    //            tvsetup.setSelected(false);
    //            tvbaby.setSelected(true);
    //            if (mMainFragmentBaby == null) {
    //                mMainFragmentBaby = new MainFragmentBaby();
    //                ft.add(R.id.layout_main, mMainFragmentBaby);
    //            } else {
    //                ft.show(mMainFragmentBaby);
    //            }
    //            mFragment = mMainFragmentBaby;
    //            ft.commitAllowingStateLoss();
    //            break;
    //    }
    //}

    @OnClick({ R.id.tv_habit, R.id.tv_map, R.id.tv_baby, R.id.tv_setup })
    public void onViewClicked(View view) {
        //当点击自身
        if (v == view) {
            Log.e("view", " 0");
            return;
        }
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction ft = manager.beginTransaction();
        if (v != null) {
            v.setSelected(false);
        }
        v = view;
        view.setSelected(true);
        if (mFragment != null) {
            ft.hide(mFragment);
        }
        switch (view.getId()) {
            case R.id.tv_habit:
                if (mMainFragmentHabit == null) {
                    mMainFragmentHabit = new MainFragmentHabit();
                    ft.add(R.id.layout_main, mMainFragmentHabit);
                } else {
                    ft.show(mMainFragmentHabit);
                    Log.e("view", "1");
                }
                mFragment = mMainFragmentHabit;
                ft.commitAllowingStateLoss();
                break;
            case R.id.tv_map:
                if (mMainFragmentMap == null) {
                    mMainFragmentMap = new MainFragmentMap();
                    ft.add(R.id.layout_main, mMainFragmentMap);
                } else {
                    ft.show(mMainFragmentMap);
                }
                mFragment = mMainFragmentMap;
                ft.commitAllowingStateLoss();
                break;
            case R.id.tv_setup:
                if (mMainFragmentSetup == null) {
                    mMainFragmentSetup = new MainFragmentSetup();
                    ft.add(R.id.layout_main, mMainFragmentSetup);
                } else {
                    ft.show(mMainFragmentSetup);
                }
                mFragment = mMainFragmentSetup;
                ft.commitAllowingStateLoss();
                break;
            case R.id.tv_baby:
                if (mMainFragmentBaby == null) {
                    mMainFragmentBaby = new GuardFragment();
                    ft.add(R.id.layout_main, mMainFragmentBaby);
                } else {
                    ft.show(mMainFragmentBaby);
                }
                mFragment = mMainFragmentBaby;
                ft.commitAllowingStateLoss();
                break;
        }
    }

}
