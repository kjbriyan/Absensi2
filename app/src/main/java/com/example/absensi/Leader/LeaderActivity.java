package com.example.absensi.Leader;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.viewpager.widget.ViewPager;

import com.astuetz.PagerSlidingTabStrip;
import com.example.absensi.Login;
import com.example.absensi.R;
import com.example.absensi.Utils.Move;
import com.example.absensi.adapter.FragmentAdapter;
import com.example.absensi.fragment.IjinFragment;
import com.example.absensi.fragment.PerformaFragment;
import com.pixplicity.easyprefs.library.Prefs;

public class LeaderActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private PagerSlidingTabStrip tabLayout;
    private ViewPager viewPager;
    private FragmentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_leader);

        String f1 = "Ijin";
        String f2 = "Performa";
        String f3 = "Data Keluarga";
        String f4 = "Kegiatan Warga";


        tabLayout = findViewById( R.id.tb_main );
        viewPager = findViewById( R.id.vp_main );
        adapter = new FragmentAdapter( getSupportFragmentManager() );

        adapter.AddFragment(new IjinFragment(),f1);
        adapter.AddFragment(new PerformaFragment(),f2);
        viewPager.setAdapter( adapter );
        tabLayout.setViewPager( viewPager );
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.nav_drawer, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.logout:
                logout();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
    private void logout(){
        Prefs.clear();
        Move.move(this, Login.class);
    }
}
