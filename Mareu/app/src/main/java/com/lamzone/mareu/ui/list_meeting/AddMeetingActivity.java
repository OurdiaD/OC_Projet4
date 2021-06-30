package com.lamzone.mareu.ui.list_meeting;

import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TimePicker;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
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

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    View view;
    private MeetingApiService meetingService;
    private UserApiService userService;
    private List<Room> rooms;
    List<User> users;
    String dateShow;
    long dateMilli;
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
        getSupportActionBar().setDisplayShowHomeEnabled(true);

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
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(selection);
                Log.d("lol date",""+ calendar.getTimeInMillis());
                dateMilli = calendar.getTimeInMillis();
                dateShow = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(dateMilli);
                initHourView();
            }
        });
    }

    public void initHourView(){
        TimePickerDialog timePicker = new TimePickerDialog(this,listenerTime,10, 10, true);
        timePicker.setTitle("select hour");
        timePicker.create();
        timePicker.show();
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
        //participantsTextView.addTextChangedListener(watcherMail);
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

        /*Chip chip = new Chip(this);
        chip.setText(selectedUser);
        binding.participantsMeeting.addView(chip);*/

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
            Meeting meeting = new Meeting(subject, roomSelected, dateShow, hour, usersSelected);
            meetingService.createMeeting(meeting);
            finish();
        }
    };


    /*private final TextWatcher watcherMail = new TextWatcher() {
        boolean change;
        CharSequence newtext;
        int sizeLast=0;
        int sizeCurrent=0;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (s.length() > 0 ){
                String check = String.valueOf(s.subSequence(s.length() -1, s.length()));
                //String check = String.valueOf(s.subSequence(0, count));
                if (check.equals(" ") || check.equals(",") || check.equals(";")){
                    change = true;
                }
            }
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (change){
                *//*String list = String.valueOf(s);
                String[] arrList = list.split(" ");
                String user = arrList[arrList.length - 1];
                user = user.replace(",","");
                createRecipientChip(user);*//*
                createRecipientChip(String.valueOf(s));
                change = false;
                //sizeLast = s.length();
                s.clear();
            }
        }
    };*/

    TimePickerDialog.OnTimeSetListener listenerTime = new TimePickerDialog.OnTimeSetListener() {
        @Override
        public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
            hour = hourOfDay + ":" + minute;

            String dateSelected = dateShow + " " + hour;
            binding.date.setText(dateSelected);
        }
    };

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }
}
