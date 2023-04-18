package com.mmb.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

/**
 * Created by mmb on 2023/3/30.
 * mail:392401273@qq.com
 */
public class MyFragment extends Fragment {
   int res;
   public MyFragment(int res) {
      this.res = res;
   }

   @Override
   public View onCreateView(LayoutInflater inflater, ViewGroup container,
                            Bundle savedInstanceState) {
      // Inflate the layout for this fragment
      View view = inflater.inflate(R.layout.home_fragment_bg, container, false);
      view.findViewById(R.id.background).setBackgroundResource(res);
      // 在这里可以对 view 进行操作，例如设置按钮点击事件等

      return view;
   }
}

