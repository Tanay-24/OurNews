package com.example.ournews;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class FrontPage extends AppCompatActivity {

    TabLayout tabLayout;
    TabItem mhome, mscience, mhealth, mtech, mentertainment, msports;
    PagerAdapter pagerAdapter;
    Toolbar mtoolbar;

    @SuppressWarnings("unused")
    String api = "a4d4e50a09b04eda9fc44ed56a5f6b12";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);

        mtoolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);

        mhome = findViewById(R.id.home);
        mscience = findViewById(R.id.science);
        mhealth = findViewById(R.id.health);
        mtech = findViewById(R.id.technology);
        mentertainment = findViewById(R.id.entertainment);
        msports = findViewById(R.id.sports);
        final ViewPager viewPager = findViewById(R.id.fragmentcontainer);
        tabLayout = findViewById(R.id.include);

        pagerAdapter = new com.example.ournews.PagerAdapter(getSupportFragmentManager(),6);
        viewPager.setAdapter(pagerAdapter);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if(tab.getPosition()==0 || tab.getPosition()==1 || tab.getPosition()==2 || tab.getPosition()==3 || tab.getPosition()==4 || tab.getPosition()==5)
                {
                    pagerAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.item,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int item_id = item.getItemId();
        if(item_id == R.id.Theme){
            Toast.makeText(this,"Your are select Theme option", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getApplicationContext(), Theme.class);
            startActivity(intent);
        }
        //else if (item_id == R.id.Blog){
            //Toast.makeText(this,"Your are select Blog option", Toast.LENGTH_SHORT).show();
            //Intent intent = new Intent(getApplicationContext(), Posts.class);
            //startActivity(intent);
        //}
        //else if (item_id == R.id.Chat){
            //Toast.makeText(this,"Your are select Chat option", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(getApplicationContext(), Chat.class);
            startActivity(intent);*/
        //}
        //else if (item_id == R.id.Read_List){
            //Toast.makeText(this,"Your are select ReadList option", Toast.LENGTH_SHORT).show();
            /*Intent intent = new Intent(getApplicationContext(), ReadList.class);
            startActivity(intent);*/
        //}
        return true;
    }
}