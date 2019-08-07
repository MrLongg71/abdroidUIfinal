package vn.mrlongg71.assignment.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v7.app.ActionBarDrawerToggle;
import android.view.MenuItem;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;

import android.support.v7.app.AppCompatActivity;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;
import android.widget.Toast;

import vn.mrlongg71.assignment.Adapter.TabLayoutAdapter;
import vn.mrlongg71.assignment.Database.Database;
import vn.mrlongg71.assignment.Fragment.HomeFragment;
import vn.mrlongg71.assignment.Fragment.IntroduceFragment;
import vn.mrlongg71.assignment.Fragment.RevenueFragment;
import vn.mrlongg71.assignment.Fragment.StatisticsFragment;
import vn.mrlongg71.assignment.R;

public class MainActivity extends AppCompatActivity


        implements NavigationView.OnNavigationItemSelectedListener {


    Toolbar toolbar;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    TextView txtName;
    public  static Database database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setSupportActionBar(toolbar);
        anhxa();
        creatDatabase();
        HomeFragment homeFragment = new HomeFragment();
        fragmentTransaction.replace(R.id.content_main, homeFragment, homeFragment.getTag()).commit();
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
    }

    private void creatDatabase() {
        //this.deleteDatabase("assignment.sqlite");
        database = new Database(this, "assignment.sqlite", null, 1 );
        //create table LoaiThu
        database.QueryData("CREATE TABLE IF NOT EXISTS LoaiThu(id INTEGER PRIMARY KEY , tenloai VARCHAR(20), deleteflag INTEGER,imgIcon BLOB,iduser VARCHAR(50))");
        database.QueryData("CREATE TABLE IF NOT EXISTS LoaiChi(id INTEGER PRIMARY KEY , tenloai VARCHAR(20), deleteflag INTEGER,imgIcon BLOB,iduser VARCHAR(50))");
        database.QueryData("CREATE TABLE IF NOT EXISTS DoanhThu(id INTEGER PRIMARY KEY , nameCV VARCHAR(20), money DECIMAL,donviThu VARCHAR(20),danhGia INTEGER,deleteFlag INTEGER,date DATE, ghiChu VARCHAR(50),img BLOB,idLoai INTEGER, idUser VARCHAR(20))");
        database.QueryData("CREATE TABLE IF NOT EXISTS KhoangChi(id INTEGER PRIMARY KEY , nameCV VARCHAR(20), money DECIMAL,donviThu VARCHAR(20),danhGia INTEGER,deleteFlag INTEGER,date DATE, ghiChu VARCHAR(50),img BLOB,idLoai INTEGER, idUser VARCHAR(20))");

    }

    private void anhxa() {
        toolbar = findViewById(R.id.toolbar);
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
//        txtName = findViewById(R.id.txtname);
//        txtName.setText("gdssfzg");
    }





    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        switch (id) {
            case R.id.nav_home:
                HomeFragment homeFragment = new HomeFragment();
                fragmentTransaction.replace(R.id.content_main, homeFragment, homeFragment.getTag()).commit();
                break;
            case R.id.nav_info:
                IntroduceFragment introduceFragment = new IntroduceFragment();
                fragmentTransaction.replace(R.id.content_main, introduceFragment, introduceFragment.getTag()).commit();
                break;

        }
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
