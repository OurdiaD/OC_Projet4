package com.lamzone.mareu.ui.list_meeting;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.service.ApiService;

import java.util.Objects;

public class ListMeetingFragment extends Fragment {
    private MeetingListAdapter mAdapter;
    RecyclerView mRecyclerView;
    private ApiService mApiService;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getNeighbourApiService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        mRecyclerView = view.findViewById(R.id.meeting_list);
        ImageButton addButton = view.findViewById(R.id.add_meeting);
        addButton.setOnClickListener(listenerAdd);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        mAdapter = new MeetingListAdapter(mApiService.getMeeting());
        mRecyclerView.setAdapter(mAdapter);
    }

    private final View.OnClickListener listenerAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddMeetingActivity.class);
            ActivityCompat.startActivity(requireContext(), intent, null);
        }
    };

}
