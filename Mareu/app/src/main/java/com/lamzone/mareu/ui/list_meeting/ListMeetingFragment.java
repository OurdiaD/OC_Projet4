package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.service.ApiService;

public class ListMeetingFragment extends Fragment {
    private ApiService mApiService;
    private MeetingListAdapter mAdapter;
    RecyclerView mRecyclerView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
        mAdapter = new MeetingListAdapter(mApiService.getMeeting());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        mRecyclerView = view.findViewById(R.id.meeting_list);
        return view;
    }

    /*@Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        mRecyclerView = findViewById(R.id.meeting_list);
    }*/

    @Override
    public void onResume() {
        super.onResume();
        mRecyclerView.setAdapter(mAdapter);
    }

}
