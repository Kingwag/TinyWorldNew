package com.kingwag.tinyworld.view.view.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kingwag.tinyworld.R;
import com.kingwag.tinyworld.view.bean.Goods;
import com.kingwag.tinyworld.view.helper.GoodsManager;
import com.kingwag.tinyworld.view.helper.UserManager;
import com.kingwag.tinyworld.view.utils.DataCleanManagerUtils;
import com.kingwag.tinyworld.view.utils.ShopLoginSharedpreferencesUtil;
import com.kingwag.tinyworld.view.view.activity.MainActivity;
import com.kingwag.tinyworld.view.view.activity.MineCollect;
import com.kingwag.tinyworld.view.view.activity.MineFragment_OpinionActivity;
import com.kingwag.tinyworld.view.view.activity.MineFragment_RegisterActivity;
import com.kingwag.tinyworld.view.view.activity.MineRecord;
import com.kingwag.tinyworld.view.view.activity.MyFragment_LoginActivity;
import com.kingwag.tinyworld.view.view.activity.Setting;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * A simple {@link Fragment} subclass.
 */

/**
 * 我的Fragment
 */
public class MineMainFragment extends Fragment implements View.OnClickListener{
//    public static final boolean SWITCH_DATA =false;
    private DataCleanManagerUtils dataCleanManagerUtils;//清除缓存工具类
    private String cacheStr;//获取缓存大小
    private TextView tvCache;//缓存大小显示的TextView
    private ImageView ivSwitchData;
    private int count_data,count_push;//省流量模式的计数器
    private ImageView ivSwitchPush;//推送通知的开关
    private Button btlogin;
    private Button btRegist;
    private TextView tvUser;
    private UserManager manager;
    private ImageView userIcon;
    public MineMainFragment() {

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        manager = new UserManager(getActivity());
        return inflater.inflate(R.layout.fragment_mine_main, container, false);

    }



    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //缓存显示的TextView实例化
        tvCache = (TextView) view.findViewById(R.id.tv_cache);
        //清除缓存的方法
        initCache();
        //登录按钮
        btlogin = (Button) view.findViewById(R.id.btn_login);
        btlogin.setOnClickListener(this);
        //用户头像
        userIcon = (ImageView) view.findViewById(R.id.iv_login_icon);
        //注册按钮
        btRegist = (Button) view.findViewById(R.id.btn_register);
        btRegist.setOnClickListener(this);
        //用户名
        tvUser = (TextView) view.findViewById(R.id.mine_login_name);
        //意见反馈
        view.findViewById(R.id.relativelayout_suggestion).setOnClickListener(this);
        //收藏
        view.findViewById(R.id.relativelayout_like).setOnClickListener(this);
        //浏览记录
        view.findViewById(R.id.relativelayout_histroy).setOnClickListener(this);
        //清除缓存
        view.findViewById(R.id.relativelayout_clear_cache).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dataCleanManagerUtils.clearAllCache(getContext());
                try {
                    cacheStr = dataCleanManagerUtils.getTotalCacheSize(getContext());
                    tvCache.setText(cacheStr);
                    Toast.makeText(getContext(),"缓存清除成功",Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                }
                dataCleanManagerUtils.cleanInternalCache(getContext());
            }
        });
        //省流量模式
        ivSwitchData = (ImageView) view.findViewById(R.id.iv_switch_data);
        ivSwitchData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_data++;
                if(count_data%2==1)
                {
                    ivSwitchData.setImageResource(R.mipmap.switch_on);
                    //

                }
                else
                {
                    ivSwitchData.setImageResource(R.mipmap.switch_off);
                }








            }
        });

        //推送通知开关
        ivSwitchPush= (ImageView) view.findViewById(R.id.iv_switch_push);

        ivSwitchPush.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count_push++;
                if(count_push%2==1)
                {
                    ivSwitchPush.setImageResource(R.mipmap.switch_off);
                    //弹Dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    AlertDialog dialog = builder.create();
                    builder.setMessage("取消后不再接收每日最热门时尚潮流资讯，确认取消？")
                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    ivSwitchPush.setImageResource(R.mipmap.switch_on);
                                }
                            })
                            .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {

                                }
                            })
                            .show();




                }
                else
                {
                    ivSwitchPush.setImageResource(R.mipmap.switch_on);
                }


            }
        });


    }

    private void initCache() {
        try {
            cacheStr =   dataCleanManagerUtils.getTotalCacheSize(getContext());
            tvCache.setText(cacheStr);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }




    @Override
    public void onClick(View view) {
        Intent intent = new Intent();
        switch (view.getId()) {
            //登录按钮
            case R.id.btn_login:
                intent.setClass(getContext(), MyFragment_LoginActivity.class);
                break;
            //注册按钮跳转
            case R.id.btn_register:
                intent.setClass(getContext(), MineFragment_RegisterActivity.class);
                break;
            //意见反馈
            case R.id.relativelayout_suggestion:
                intent.setClass(getContext(), MineFragment_OpinionActivity.class);
                break;
            case R.id.relativelayout_setting:
                intent.setClass(getContext(), Setting.class);
                break;
            case R.id.relativelayout_like:
                intent.setClass(getContext(), MineCollect.class);
                break;
            case R.id.relativelayout_histroy:
                intent.setClass(getContext(), MineRecord.class);
                break;

        }
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        boolean state = ShopLoginSharedpreferencesUtil.getLoginState(getActivity());
        if (state){
            btlogin.setVisibility(View.GONE);
            btRegist.setVisibility(View.GONE);
            tvUser.setVisibility(View.VISIBLE);
            tvUser.setText("User");
            userIcon.setImageResource(R.mipmap.user_icon);
        }
    }


}