package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiService;
import com.lamzone.mareu.service.MeetingApiService;
import com.lamzone.mareu.ui.MainActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    View view;
    private ApiService mApiService;
    private List<Room> rooms;
    String date;
    String hour;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_add_meeting);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mApiService = DI.getNeighbourApiService();
        initDropDown();
        initDateView();
    }

    protected void initDropDown(){
        rooms = mApiService.getRooms();
        //AutoCompleteTextView dropDownRoomView = findViewById(R.id.room_list);
        AutoCompleteTextView dropDownRoomView = binding.roomList;

        List<String> dropDownRoomData = new ArrayList<>();
        for (Room room : rooms){
            dropDownRoomData.add(room.getRoom());
        }

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.item_dropdown, dropDownRoomData);

        dropDownRoomView.setAdapter(roomAdapter);
    }

    public void initDateView(){
        CalendarConstraints.Builder constraintsBuilder = new CalendarConstraints.Builder()
                .setValidator(DateValidatorPointForward.now());

        final MaterialDatePicker<Long> datePicker = MaterialDatePicker.Builder.datePicker()
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .setTitleText("Select date")
                .setCalendarConstraints(constraintsBuilder.build())
                .build();

        binding.date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                datePicker.show(getSupportFragmentManager(), "tag");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).format(calendar.getTime());
            }
        });
    }

}
