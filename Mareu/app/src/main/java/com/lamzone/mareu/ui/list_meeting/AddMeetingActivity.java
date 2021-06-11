package com.lamzone.mareu.ui.list_meeting;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputLayout;
import com.lamzone.mareu.R;
import com.lamzone.mareu.databinding.ActivityAddMeetingBinding;
import com.lamzone.mareu.di.DI;
import com.lamzone.mareu.model.Room;
import com.lamzone.mareu.model.User;
import com.lamzone.mareu.service.ApiService;
import com.lamzone.mareu.service.MeetingApiService;

import java.util.ArrayList;
import java.util.List;

public class AddMeetingActivity extends AppCompatActivity {

    ActivityAddMeetingBinding binding;
    private ApiService mApiService;
    private List<Room> rooms;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_meeting);

        /*binding = ActivityAddMeetingBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);*/


        //getActionBar().setTitle("Add new meeting");
        //getActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        /*val items = listOf("Material", "Design", "Components", "Android")
        val adapter = ArrayAdapter(requireContext(), R.layout.list_item, items)
        (textField.editText as? AutoCompleteTextView)?.setAdapter(adapter)*/

        mApiService = DI.getNeighbourApiService();
        initDropDown();
    }

    protected void initDropDown(){
        rooms = mApiService.getRooms();
        AutoCompleteTextView dropDownRoomView = findViewById(R.id.room_list);

        List<String> dropDownRoomData = new ArrayList<>();
        for (Room room : rooms){
            dropDownRoomData.add(room.getRoom());
        }

        ArrayAdapter<String> roomAdapter = new ArrayAdapter<>(getBaseContext(),
                R.layout.item_dropdown, dropDownRoomData);

        dropDownRoomView.setAdapter(roomAdapter);
    }

}
