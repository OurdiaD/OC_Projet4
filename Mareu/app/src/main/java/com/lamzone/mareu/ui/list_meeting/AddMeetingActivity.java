package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.meeting.MeetingApiService;
import com.lamzone.mareu.service.user.UserApiService;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.TimeZone;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    View view;
    private MeetingApiService meetingService;
    private UserApiService userService;
    private List<Room> rooms;
    List<User> users;
    String date;
    String hour;
    MultiAutoCompleteTextView participantsTextView;
    AutoCompleteTextView dropDownRoomView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        meetingService = DI.getMeetingApiService();
        userService = DI.getUserApiService();
        initRoomDropDown();
        initDateView();
        initParticipantsView();
        binding.saveMeeting.setOnClickListener(listenerCreate);

    }

    protected void initRoomDropDown(){
        rooms = meetingService.getRooms();
        dropDownRoomView = binding.roomList;

        ArrayAdapter<Room> roomAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.item_dropdown, rooms);

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
                datePicker.show(getSupportFragmentManager(), "date");
            }
        });

        datePicker.addOnPositiveButtonClickListener(new MaterialPickerOnPositiveButtonClickListener<Long>() {
            @Override public void onPositiveButtonClick(Long selection) {
                Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
                calendar.setTimeInMillis(selection);
                date = new SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE).format(calendar.getTime());
                initHourView();
            }
        });
    }

    public void initHourView(){
        final MaterialTimePicker timePicker = new MaterialTimePicker.Builder()
                .setTitleText("Select hour")
                .setTimeFormat(TimeFormat.CLOCK_24H)
                .build();

        timePicker.show(getSupportFragmentManager(), "time");
        timePicker.addOnPositiveButtonClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int hourSelect = timePicker.getHour();
                int minuteSelect = timePicker.getMinute();

                hour = hourSelect + ":" + minuteSelect;

                String dateSelected = date + " " + hour;
                binding.date.setText(dateSelected);
            }
        });
    }

    public void initParticipantsView(){
        participantsTextView = binding.participantsSelected;
        users = userService.getUsers();

        ArrayAdapter<User> roomAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.item_dropdown, users);
        participantsTextView.setAdapter(roomAdapter);
        participantsTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        participantsTextView.setThreshold(1);
        participantsTextView.performValidation();
        participantsTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = (User) adapterView.getItemAtPosition(i);
                createRecipientChip(selectedUser);
            }
        });
    }

    private void createRecipientChip(User selectedUser) {
        ChipDrawable chip = ChipDrawable.createFromResource(this, R.xml.standalone_chip);
        ImageSpan span = new ImageSpan(chip);
        int cursorPosition = participantsTextView.getSelectionStart();
        int spanLength = selectedUser.getMail().length() + 2;
        Editable text = participantsTextView.getText();
        chip.setText(selectedUser.getMail());
        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        text.setSpan(span, cursorPosition - spanLength, cursorPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }

    private final View.OnClickListener listenerCreate = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            String subject = String.valueOf(binding.subject.getText());
            Room roomSelected = null;
            String participants = String.valueOf(participantsTextView.getText());

            for (Room room : rooms){
                if (room.getRoom().equals(String.valueOf(dropDownRoomView.getText()))){
                    roomSelected = room;
                    break;
                }
            }

            List<User> usersSelected = userService.getUsersSelected(participants);
            Meeting meeting = new Meeting(subject, roomSelected, hour, usersSelected);
            meetingService.createMeeting(meeting);
            finish();
        }
    };



}
