package com.mmb.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

public class MainActivity extends AppCompatActivity {
    private ViewPager viewpager1;
    private ViewPager viewpager2;

    private int mViewPagerIndex = -1;
    private View selectPage = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewpager1 = findViewById(R.id.viewpager1);
        viewpager2 = findViewById(R.id.viewpager2);
        viewpager1.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewpager2.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        viewpager1.setOffscreenPageLimit(100);
        viewpager2.setOffscreenPageLimit(100);

        viewpager1.addOnPageChangeListener(new BaseLinkPageChangeListener(viewpager1, viewpager2) {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
            }
        });

//        viewpager2.setPageTransformer(true, new LeftFadePageTransformer());
    }

    class BaseLinkPageChangeListener implements ViewPager.OnPageChangeListener {

        private ViewPager linkViewPager;
        private ViewPager selfViewPager;

        private int pos;

        public BaseLinkPageChangeListener(ViewPager selfViewPager, ViewPager linkViewPager) {
            this.linkViewPager = linkViewPager;
            this.selfViewPager = selfViewPager;
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            int marginX = ((selfViewPager.getWidth() + selfViewPager.getPageMargin()) * position
                    + positionOffsetPixels) * (linkViewPager.getWidth() + linkViewPager.getPageMargin()) / (
                    selfViewPager.getWidth()
                            + selfViewPager.getPageMargin());

            if (linkViewPager.getScrollX() != marginX) {
                linkViewPager.scrollTo(marginX, 0);

                if (selectPage == null) selectPage = linkViewPager.getChildAt(0); //第一次进入时
                Log.e("mmb", "positionOffset------->" + positionOffset);

                if (selectPage != null && 0.0 != positionOffset && positionOffset < 1) {

                    if (mViewPagerIndex == position) { //左滑
                        selectPage.setAlpha(1 - positionOffset);
                    } else {
                        selectPage.setAlpha(positionOffset);
                    }
                }
            }
        }

        @Override
        public void onPageSelected(int position) {
            this.pos = position;
            Log.e("mmb", "onPageSelected-->" + selectPage);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.e("mmb", "onPageScrollStateChanged------->" + pos + "----->" + state);
            if (state == ViewPager.SCROLL_STATE_IDLE) {
                linkViewPager.setCurrentItem(pos, true);
            }
            if (state == ViewPager.SCROLL_STATE_DRAGGING) {//当手指刚触碰屏幕时state的值为1.
                mViewPagerIndex = viewpager2.getCurrentItem();
            }
            if (state != ViewPager.SCROLL_STATE_DRAGGING) {
                //赋值选中的那一个
                selectPage = linkViewPager.getChildAt(pos);
                //左右两边的页重置
                View pageLeft = linkViewPager.getChildAt(pos - 1);
                if (pageLeft != null) pageLeft.setAlpha(1.0f);
                View pageRight = linkViewPager.getChildAt(pos + 1);
                if (pageRight != null) pageRight.setAlpha(1.0f);
            }
        }
    }

//
//    public class LeftFadePageTransformer implements ViewPager.PageTransformer {
//
//        private static final float MIN_ALPHA = 0.3f;
//
//        @Override
//        public void transformPage(@NonNull View page, float position) {
//
//            if (position < -1 || position > 1) {
//                page.setAlpha(0f);
//            } else {
//                if (-1 <= position && position < 0) {
//                    if (isToLeft) {
//                        page.setAlpha(1 + position - MIN_ALPHA * position);
//                    } else {
//                        page.setAlpha(1f);
//                    }
//                } else if (0 < position && position <= 1) {
//                    if (isToLeft) {
//                        page.setAlpha(1f);
//                    } else {
//                        page.setAlpha(1 - position + MIN_ALPHA * position);
//                    }
//                } else {
//                    page.setAlpha(1f);
//                }
//            }
//        }
//    }

    public class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position == 0) {
                return new MyFragment(R.drawable.bg_home_page);
            } else if (position == 1) {
                return new MyFragment(R.drawable.bg_home_page_lowcarbon);
            } else {
                return new MyFragment(R.drawable.bg_home_page_security);
            }
        }

        @Override
        public int getCount() {
            // 返回页面数量
            return 5;
        }
    }

}