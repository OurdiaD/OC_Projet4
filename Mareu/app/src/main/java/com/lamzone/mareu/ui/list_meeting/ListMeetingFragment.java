package com.lamzone.mareu.ui.list_meeting;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.chip.Chip;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.lamzone.mareu.R;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.service.meeting.MeetingApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class ListMeetingFragment extends Fragment {
    RecyclerView mRecyclerView;
    private MeetingApiService mApiService;
    private String dateFilterData;
    private String roomFilterData;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mApiService = DI.getMeetingApiService();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_meeting, container, false);
        mRecyclerView = view.findViewById(R.id.meeting_list);
        ImageButton addButton = view.findViewById(R.id.add_meeting);
        addButton.setOnClickListener(listenerAdd);
        initFilter(view);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        initList();
    }

    public void initList(){
        MeetingListAdapter mAdapter = new MeetingListAdapter(mApiService.filterMeeting(dateFilterData, roomFilterData), this);
        mRecyclerView.setAdapter(mAdapter);
    }

    private final View.OnClickListener listenerAdd = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(getContext(), AddMeetingActivity.class);
            ActivityCompat.startActivity(requireContext(), intent, null);
        }
    };

    private void initFilter(View view){
        final Chip filterRoom = view.findViewById(R.id.filter_room);
        final Chip filterDate = view.findViewById(R.id.filter_date);

        MeetingApiService meetingService = DI.getMeetingApiService();
        List<Room> rooms = meetingService.getRooms();
        final ArrayAdapter<Room> roomAdapter = new ArrayAdapter<>(requireActivity(),
                R.layout.item_dropdown, rooms);

        filterRoom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(requireActivity());
                builder.setTitle(R.string.salle)
                        .setAdapter(roomAdapter, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                getDataFilter(filterRoom, Objects.requireNonNull(roomAdapter.getItem(which)).getRoom());
                            }
                        });
                builder.create();
                builder.show();
            }
        });

        filterDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MaterialDatePicker<Long> datePicker = createDatePicker(filterDate);
                datePicker.show(requireActivity().getSupportFragmentManager(), "date");
            }
        });

        filterRoom.setOnCloseIconClickListener(chipsCloseListener(filterRoom, R.string.salle));
        filterDate.setOnCloseIconClickListener(chipsCloseListener(filterDate, R.string.date));
    }

    private View.OnClickListener chipsCloseListener(final Chip chip, final int name){
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDataFilter(chip, null);
                chip.setText(name);
                chip.setCloseIconVisible(false);
            }
        };
    }

    private void getDataFilter(Chip chip, String data){
        chip.setText(data);
        chip.setCloseIconVisible(true);
        if(chip.getId() == R.id.filter_date){
            dateFilterData = data;
        } else if (chip.getId() == R.id.filter_room){
            roomFilterData = data;
        }
        initList();
    }

    private MaterialDatePicker<Long> createDatePicker(final Chip filterDate){
        MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select date")
                .build();

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                String date = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(calendar.getTime());
                getDataFilter(filterDate, date);
            }
        });

        return datePicker;
    }

    public void deleteMeeting(Meeting meeting){
        mApiService.deleteMeeting(meeting);
        initList();
    }
}
