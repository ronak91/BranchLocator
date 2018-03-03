package com.branchlocator.branchlocator.Activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;;
import com.branchlocator.branchlocator.Fragment.BankListFragment;
import com.branchlocator.branchlocator.Fragment.MapFragment;
import com.branchlocator.branchlocator.R;


public class MainDrawerActivity extends AppCompatActivity {

    Toolbar toolbar;
    BottomNavigationView bottomNavigationView;
    Fragment selectedFragment = null;
    public ImageView iv_toolbar_back;
    public TextView tv_toolbar_backtxt, tv_toolbar_title;
    MenuItem selectedItem;
    private int mSelectedItem;
    private static final String SELECTED_ITEM = "arg_selected_item";

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarGradiant(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = activity.getWindow();
            Drawable background = activity.getResources().getDrawable(R.color.color_light_white);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(activity.getResources().getColor(R.color.transparent));
            window.setNavigationBarColor(activity.getResources().getColor(R.color.transparent));
            window.setBackgroundDrawable(background);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusBarGradiant(this);
        setContentView(R.layout.activity_main);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");

        tv_toolbar_backtxt = (TextView) findViewById(R.id.tv_toolbar_backtxt);
        tv_toolbar_title = (TextView) findViewById(R.id.tv_toolbar_title);

        iv_toolbar_back = (ImageView) findViewById(R.id.iv_toolbar_back);
        iv_toolbar_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation);

        if (savedInstanceState != null) {
            mSelectedItem = savedInstanceState.getInt(SELECTED_ITEM, 0);
            selectedItem = bottomNavigationView.getMenu().findItem(mSelectedItem);
        } else {
            selectedItem = bottomNavigationView.getMenu().getItem(0);
        }



        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                selectFragment(item);
                return true;
            }
        });
    }


    public void switchContent(int id, Fragment fragment) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.content_frame, fragment, fragment.toString());
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void onBackPressed() {
        MenuItem homeItem = bottomNavigationView.getMenu().getItem(0);
        if (mSelectedItem != homeItem.getItemId()) {
            // select home item
            selectFragment(homeItem);
        } else {
            super.onBackPressed();
        }
    }



    @TargetApi(Build.VERSION_CODES.CUPCAKE)
    private void selectFragment(MenuItem item) {
        Fragment frag = null;
        FragmentTransaction transaction;
        // init corresponding fragment
        switch (item.getItemId()) {
            case R.id.action_banks1:
                // The tab with id R.id.action_banks was selected,
                // change your content according
                iv_toolbar_back.setVisibility(View.GONE);
                selectedFragment = BankListFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, selectedFragment);
                transaction.commit();
                break;

            case R.id.action_map1:
                // The tab with id R.id.action_map was selected,
                // change your content accordingly.
                iv_toolbar_back.setVisibility(View.GONE);
                selectedFragment = MapFragment.newInstance();
                transaction = getSupportFragmentManager().beginTransaction();
                transaction.replace(R.id.content_frame, selectedFragment);
                transaction.commit();
                break;

        }

        // update selected item
        mSelectedItem = item.getItemId();

        // uncheck the other items.
        for (int i = 0; i< bottomNavigationView.getMenu().size(); i++) {
            MenuItem menuItem = bottomNavigationView.getMenu().getItem(i);
            menuItem.setChecked(menuItem.getItemId() == item.getItemId());
        }


        if (frag != null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.add(R.id.container, frag, frag.getTag());
            ft.commit();
        }

    }

}

