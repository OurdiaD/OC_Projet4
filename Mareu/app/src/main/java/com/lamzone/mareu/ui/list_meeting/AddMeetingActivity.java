package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;

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

import static android.view.KeyEvent.KEYCODE_COMMA;
import static android.view.KeyEvent.KEYCODE_SEMICOLON;
import static android.view.KeyEvent.KEYCODE_SPACE;
import static android.view.inputmethod.EditorInfo.IME_NULL;

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
        //participantsTextView.onEditorAction(IME_NULL);
        //participantsTextView.setOnEditorActionListener(userEditionListener);
        participantsTextView.addTextChangedListener(watcherMail);
        /*participantsTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = (User) adapterView.getItemAtPosition(i);
                createRecipientChip(selectedUser.getMail());
            }
        });*/
    }

    private void createRecipientChip(String selectedUser) {
        ChipDrawable chip = ChipDrawable.createFromResource(this, R.xml.standalone_chip);
        ImageSpan span = new ImageSpan(chip);
        int cursorPosition = participantsTextView.getSelectionStart();
        Log.d("lol selected user",selectedUser.length()+" "+ cursorPosition);
        int spanLength = selectedUser.length() + 1;
        Editable text = participantsTextView.getText();
        chip.setText(selectedUser);

        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        text.setSpan(span, cursorPosition - spanLength, cursorPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //text.setSpan(span, 0, text.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
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


    private final TextWatcher watcherMail = new TextWatcher() {
        boolean change;
        CharSequence newtext;
        int sizeLast=0;
        int sizeCurrent=0;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            Log.d("lol", "before " + start + " "+ after + " "+ count + " " +s);

            if (after == 0){
                sizeCurrent = count;
                change = true;
            }

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            String check = String.valueOf(s.subSequence(s.length() -1, s.length()));
            //String check = String.valueOf(s.charAt(before));
            Log.d("lol", "change " + start + " "+ before + " "+ count + " " +s+ "-" +check+"-" +check.equals(" ") );
            if (check.equals(" ") || check.equals(",") || check.equals(";")){
                change = true;
                //if (count >sizeCurrent+1){
                    sizeCurrent = count;
               // }

            } else {
                sizeCurrent = count;
            }
            /*if (before == 0 && count > 1){
                if (change){
                    change = false;
                } else {
                    change = true;
                }
            } else {
                sizeCurrent = count;
            }*/
        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.d("lol", "after " + s.toString() + " "+change);
            if (change){
                String list = String.valueOf(s);
                String[] arrList = list.split(" ");
                String user = arrList[arrList.length - 1];
                /*
                int lastIndex = list.lastIndexOf(" ",);
                String user = list.substring(lastIndex);*/

                /*Log.d("lol chip", sizeLast +"-"+sizeCurrent);
                Log.d("lol chip", ""+s.subSequence(sizeLast, sizeLast+sizeCurrent));
                String user = String.valueOf(s.subSequence(sizeLast, sizeLast+sizeCurrent));
                */
                user = user.replace(",","");
                createRecipientChip(user);
                change = false;
                sizeLast = s.length();
            }
        }
    };

  /*  private TextWatcher textWather = new TextWatcher() {
        int noOfCharAdded=0;int noOfCharDeleted=0;
        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            startIdx=start;
        }
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,int after) {
            noOfCharAdded=after;
            noOfCharDeleted=count;
        }
        @Override
        public void afterTextChanged(Editable s) {
            Editable buffer = s;
            int start = multiContentText.getSelectionStart()<0?0:multiContentText.getSelectionStart();
            int end = multiContentText.getSelectionEnd()<0?0:multiContentText.getSelectionEnd();
            if(noOfCharAdded==0 && noOfCharDeleted==1){ //if space is deleted
                if (start == end && delPrevText) {
                    ImageSpan link[] = buffer.getSpans(start, end,ImageSpan.class);
                    if (link.length > 0) {
                        buffer.replace(buffer.getSpanStart(link[0]),buffer.getSpanEnd(link[0]),"");
                        buffer.removeSpan(link[0]);
                    }
                }
                delPrevText=true;
                multiContentText.setSelection(multiContentText.getText().length());
            }
            else if(noOfCharAdded==0 && noOfCharDeleted>1){//if the whole word is deleted
                if(buffer.length()>0){
                    if(start<buffer.length()){
                        delPrevText=false;
                        if(buffer.charAt(start)==' '){
                            buffer.replace(start,start+1,"");
                        }
                    }
                }
            }

        }
    };*/

    private final MultiAutoCompleteTextView.OnEditorActionListener userEditionListener = new MultiAutoCompleteTextView.OnEditorActionListener() {
        @Override
        public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
            Log.d("lol editor", actionId + " " + KEYCODE_SPACE + " " + event.getAction() + " "+ event.getKeyCode());
            if (actionId == KEYCODE_SPACE ||
                    actionId == KEYCODE_COMMA ||
                    actionId == KEYCODE_SEMICOLON){
                createRecipientChip(v.toString());
                return true;
            }
            return false;
        }
    };

}
