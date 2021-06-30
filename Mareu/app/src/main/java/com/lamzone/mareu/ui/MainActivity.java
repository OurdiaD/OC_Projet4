package com.lamzone.mareu.ui;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.lamzone.mareu.R;
import com.lamzone.mareu.ui.list_meeting.ListMeetingFragment;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        getSupportFragmentManager().beginTransaction().replace(R.id.container,new ListMeetingFragment()).commit();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.filter) {
            FrameLayout filterLayout = findViewById(R.id.filter_layout);
            if(filterLayout.getVisibility() == View.GONE){
                filterLayout.setVisibility(View.VISIBLE);
            } else {
                filterLayout.setVisibility(View.GONE);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
