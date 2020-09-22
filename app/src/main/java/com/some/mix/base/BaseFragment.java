package com.some.mix.base;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;

/**
 * @author cyl
 * @date 2020/8/20
 */
public abstract class BaseFragment extends Fragment {

    protected FragmentActivity mAttachActivity;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = null;
        int layoutId = getLayoutId();
        if (layoutId != 0){
            view = inflater.inflate(getLayoutId(), container, false);
            initView(view);
        }
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            mAttachActivity = (FragmentActivity) context;
        }
        super.onAttach(context);
    }

    @Override
    public void onAttach(@NonNull Activity activity) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M){
            mAttachActivity = (FragmentActivity) activity;
        }
        super.onAttach(activity);
    }

    protected abstract void initView(View view);

    protected abstract int getLayoutId();

    public static void toast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }
}
