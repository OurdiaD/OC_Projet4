package com.lamzone.mareu.ui.list_meeting;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.service.ApiService;

public class ListMeetingActivity extends AppCompatActivity {

    private ApiService mApiService;
    private MeetingListAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_meeting);
        mApiService = DI.getNeighbourApiService();


        //mRecyclerView = findViewById(R.id.meeting_list);
        mRecyclerView = findViewById(R.id.meeting_list);
        mAdapter = new MeetingListAdapter(mApiService.getMeeting());

        //mRecyclerView.setLayoutManager(new LinearLayoutManager(getBaseContext()));


        /*View view = inflater.inflate(R.layout.fragment_neighbour_list, container, false);
        Context context = view.getContext();
        mRecyclerView = (RecyclerView) view;
        mRecyclerView.setLayoutManager(new LinearLayoutManager(context));
        if (getContext() != null)
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));*/
    }

    @Override
    protected void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(mAdapter);
    }
}
