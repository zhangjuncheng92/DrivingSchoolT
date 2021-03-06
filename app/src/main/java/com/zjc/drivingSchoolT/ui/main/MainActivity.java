package com.zjc.drivingSchoolT.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.mobo.mobolibrary.util.image.ImageLoader;
import com.zjc.drivingSchoolT.R;
import com.zjc.drivingSchoolT.db.SharePreferences.SharePreferencesUtil;
import com.zjc.drivingSchoolT.db.models.UserInfo;
import com.zjc.drivingSchoolT.jpush.JPushUtil;
import com.zjc.drivingSchoolT.ui.apply.ApplyActivity;
import com.zjc.drivingSchoolT.ui.login.LoginActivity;
import com.zjc.drivingSchoolT.ui.notification.NotificationActivity;
import com.zjc.drivingSchoolT.ui.order.OrderManagerActivity;
import com.zjc.drivingSchoolT.ui.personal.PersonalActivity;
import com.zjc.drivingSchoolT.utils.Constants;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private Toolbar toolbar;
    private DrawerLayout drawer;
    private ImageView imgOne;
    private ImageView imgTwo;
    private ImageView imgThree;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initToolBar();
        initView();
        initOrder();
    }

    private void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        //设置标题
        getSupportActionBar().setDisplayShowTitleEnabled(false);
    }

    private void initView() {
        imgOne = (ImageView) findViewById(R.id.main_one);
        imgTwo = (ImageView) findViewById(R.id.main_two);
        imgThree = (ImageView) findViewById(R.id.main_three);

        imgOne.setOnClickListener(this);
        imgTwo.setOnClickListener(this);
        imgThree.setOnClickListener(this);
    }


    private void initOrder() {
        FragmentTransaction trans = getSupportFragmentManager().beginTransaction();
        StudyListFragment fragment = new StudyListFragment();
        trans.add(R.id.main_map, fragment, "StudyListFragment").show(fragment).commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        verifyIsLogin();
    }

    /**
     * 验证是否登录
     */
    private void verifyIsLogin() {
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if (SharePreferencesUtil.getInstance().isLogin()) {
            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            View headerView = navigationView.getHeaderView(0);

            navigationView.setNavigationItemSelectedListener(this);
            headerView.setOnClickListener(new HeaderViewOnClick());
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            UserInfo userInfo = SharePreferencesUtil.getInstance().readUser();
            SimpleDraweeView sdIcon = (SimpleDraweeView) headerView.findViewById(R.id.personal_main_frg_sd_icon);
            TextView tvName = (TextView) headerView.findViewById(R.id.personal_main_frg_tv_name);
            ImageLoader.getInstance().displayImage(sdIcon, Constants.BASE_IP + userInfo.getPhoto());
            tvName.setText(userInfo.getTeachername() + "");
        } else {
            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //跳转到登录
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
        toolbar.setNavigationIcon(R.drawable.icon_usercenter);
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.main_action_apply_history) {
            Intent intent = new Intent(MainActivity.this, ApplyActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_action_collect) {
//            Intent intent = new Intent(MainActivity.this, CollectManagerActivity.class);
//            startActivity(intent);
        } else if (id == R.id.main_action_history) {
            Intent intent = new Intent(MainActivity.this, OrderManagerActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_action_notice) {
            Intent intent = new Intent(MainActivity.this, NotificationActivity.class);
            startActivity(intent);
        } else if (id == R.id.main_action_more) {
        } else if (id == R.id.main_action_logout) {
            logout();
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    class HeaderViewOnClick implements View.OnClickListener {

        @Override
        public void onClick(View view) {
            Intent intent = new Intent(MainActivity.this, PersonalActivity.class);
            startActivity(intent);
        }
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.main_one) {

        }
    }

    private void logout() {
        JPushUtil.setAliasAndTags();
        SharePreferencesUtil.getInstance().setLogin(false);
        SharePreferencesUtil.getInstance().removePwd();
        verifyIsLogin();
        //跳转到登录
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
        this.finish();
    }
}
