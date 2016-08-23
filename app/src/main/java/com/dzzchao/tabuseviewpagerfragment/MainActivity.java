package com.dzzchao.tabuseviewpagerfragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.dzzchao.tabuseviewpagerfragment.fragment.Tab1Fragment;
import com.dzzchao.tabuseviewpagerfragment.fragment.Tab2Fragment;
import com.dzzchao.tabuseviewpagerfragment.fragment.Tab3Fragment;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private ViewPager mViewPager;
    private List<Fragment> mList;
    private FragmentPagerAdapter mAdapter;
    private TextView tvTab1;
    private TextView tvTab2;
    private TextView tvTab3;
    private ImageView imvTabLine;
    private int mScreen1_3;
    private int mCurrentPageIndex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initData();
        initView();
        initTabLine();
    }

    private void initTabLine() {
        //????
        Display display = getWindow().getWindowManager().getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);
        mScreen1_3 = outMetrics.widthPixels / 3;
        ViewGroup.LayoutParams lp = imvTabLine.getLayoutParams();
        lp.width = outMetrics.widthPixels / 3;
        imvTabLine.setLayoutParams(lp);
    }

    private void initData() {
        mList = new ArrayList<>();

        Tab1Fragment tab1Fragment = new Tab1Fragment();
        Tab2Fragment tab2Fragment = new Tab2Fragment();
        Tab3Fragment tab3Fragment = new Tab3Fragment();

        mList.add(tab1Fragment);
        mList.add(tab2Fragment);
        mList.add(tab3Fragment);
    }

    private void initView() {

        imvTabLine = (ImageView) findViewById(R.id.imv_tabLine);

        tvTab1 = (TextView) findViewById(R.id.tv_tab1);
        tvTab1.setTextColor(Color.parseColor("#008000"));
        tvTab2 = (TextView) findViewById(R.id.tv_tab2);
        tvTab3 = (TextView) findViewById(R.id.tv_tab3);

        mViewPager = (ViewPager) findViewById(R.id.viewPager);
        mAdapter = new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return mList.get(position);
            }

            @Override
            public int getCount() {
                return mList.size();
            }
        };
        mViewPager.setAdapter(mAdapter);

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                Log.d(TAG, "position = " + position + " : PositionOffset = " + positionOffset + " : positionOffsetPixels = " + positionOffsetPixels);
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) imvTabLine.getLayoutParams();
                if (mCurrentPageIndex == 0 && position == 0) { //0-->1
                    lp.leftMargin = (int) (positionOffset * mScreen1_3 + mCurrentPageIndex * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 0) { // 1-->0
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
                } else if (mCurrentPageIndex == 1 && position == 1) { // 1-->2
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + positionOffset * mScreen1_3);
                } else if (mCurrentPageIndex == 2 && position == 1) {
                    lp.leftMargin = (int) (mCurrentPageIndex * mScreen1_3 + (positionOffset - 1) * mScreen1_3);
                }
                imvTabLine.setLayoutParams(lp);
            }

            @Override
            public void onPageSelected(int position) {
                //设置当前的tab文字为绿色
                resetTextView();
                switch (position) {
                    case 0:
                        tvTab1.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 1:
                        tvTab2.setTextColor(Color.parseColor("#008000"));
                        break;
                    case 2:
                        tvTab3.setTextColor(Color.parseColor("#008000"));
                        break;
                }

                mCurrentPageIndex = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void resetTextView() {
        tvTab1.setTextColor(Color.BLACK);
        tvTab2.setTextColor(Color.BLACK);
        tvTab3.setTextColor(Color.BLACK);
    }
}
