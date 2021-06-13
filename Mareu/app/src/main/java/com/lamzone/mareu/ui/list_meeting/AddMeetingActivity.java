package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.text.Editable;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.MultiAutoCompleteTextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.android.material.chip.ChipDrawable;
import com.google.android.material.datepicker.CalendarConstraints;
import com.google.android.material.datepicker.DateValidatorPointForward;
import com.google.android.material.datepicker.MaterialDatePicker;
import com.google.android.material.datepicker.MaterialPickerOnPositiveButtonClickListener;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.timepicker.MaterialTimePicker;
import com.google.android.material.timepicker.TimeFormat;
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
    MultiAutoCompleteTextView participantsTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        view = binding.getRoot();
        setContentView(view);
        Objects.requireNonNull(getSupportActionBar()).setDisplayHomeAsUpEnabled(true);

        mApiService = DI.getNeighbourApiService();
        initDropDown();
        initDateView();
        initParticipantsView();
    }

    protected void initDropDown(){
        rooms = mApiService.getRooms();
        AutoCompleteTextView dropDownRoomView = binding.roomList;

        /*List<String> dropDownRoomData = new ArrayList<>();
        for (Room room : rooms){
            dropDownRoomData.add(room.getRoom());
        }*/

        List<String> dropDownRoomData = MeetingApiService.getListString(rooms);

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

                hour = hourSelect + " : " + minuteSelect;

                String dateSelected = date + " " + hour;
                binding.date.setText(dateSelected);
            }
        });
    }

    public void initParticipantsView(){
        participantsTextView = binding.participantsSelected;
        List<User> users = mApiService.getUsers();


        List<String> UserData = new ArrayList<>();
        for (User user : users){
            UserData.add(user.getMail());
        }
        ArrayAdapter<User> roomAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.item_dropdown, users);
        participantsTextView.setAdapter(roomAdapter);
        participantsTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
// Minimum number of characters the user has to type before the drop-down list is shown
        participantsTextView.setThreshold(1);
        participantsTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                User selectedUser = (User) adapterView.getItemAtPosition(i);
                createRecipientChip(selectedUser);
            }
        });

       /* MultiAutoCompleteTextView contactAutoCompleteTextView = findViewById(R.id.recipient_auto_complete_text_view);
        List<Contact> contacts = new ArrayList<Contact>() {{
            add(new Contact("Adam Ford", R.drawable.adam_ford));
            add(new Contact("Adele McCormick", R.drawable.adele_mccormick));
            add(new Contact("Alexandra Hollander", R.drawable.alexandra_hollander));
            add(new Contact("Alice Paul", R.drawable.alice_paul));
            add(new Contact("Arthur Roch", R.drawable.arthur_roch));
        }};

        contactAutoCompleteTextView.setAdapter(new ContactAdapter(this,
                R.layout.contact_layout, contacts));
        contactAutoCompleteTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
// Minimum number of characters the user has to type before the drop-down list is shown
        contactAutoCompleteTextView.setThreshold(1);
        contactAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Contact selectedContact = (Contact) adapterView.getItemAtPosition(i);
                createRecipientChip(selectedContact);
            }
        });*/
    }

    private void createRecipientChip(User selectedUser) {
        ChipDrawable chip = ChipDrawable.createFromResource(this, R.xml.standalone_chip);
        //CenteredImageSpan span = new CenteredImageSpan(chip, 40f, 40f);
        ImageSpan span = new ImageSpan(chip);
        int cursorPosition = participantsTextView.getSelectionStart();
        int spanLength = selectedUser.getMail().length() + 2;
        Editable text = participantsTextView.getText();
        /*chip.setChipIcon(ContextCompat.getDrawable(AddMeetingActivity.this,
                selectedContact.getAvatarResource()));*/
        chip.setText(selectedUser.getMail());
        chip.setBounds(0, 0, chip.getIntrinsicWidth(), chip.getIntrinsicHeight());
        text.setSpan(span, cursorPosition - spanLength, cursorPosition, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
    }
}
