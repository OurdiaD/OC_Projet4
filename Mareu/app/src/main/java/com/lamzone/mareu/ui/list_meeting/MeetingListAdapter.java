package com.lamzone.mareu.ui.list_meeting;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.User;

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingListViewHolder> {

    private final List<Meeting> mMeeting;
    private Context context;


    public MeetingListAdapter(List<Meeting> meeting) {
        this.mMeeting = meeting;
    }

    static class MeetingListViewHolder extends RecyclerView.ViewHolder {
        private final TextView meetingName;
        private final TextView participantsName;

        private MeetingListViewHolder(View itemView) {
            super(itemView);
            meetingName = itemView.findViewById(R.id.meeting);
            participantsName = itemView.findViewById(R.id.participants);
        }
    }


    @NonNull
    @Override
    public MeetingListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_meeting, parent, false);
        return new MeetingListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, int position) {
        Meeting meeting = mMeeting.get(position);
        String textMeeting = meeting.getSubject() + " - " + meeting.getHour() + " - " + meeting.getLocation();
        StringBuilder textParticipants = new StringBuilder();
        for (User user : meeting.getParticipants()){
            textParticipants.append(user.getName()).append("@lamzone.com").append(", ");
        }
        holder.meetingName.setText(textMeeting);
        holder.participantsName.setText(textParticipants);
    }

    @Override
    public int getItemCount() {
        return mMeeting.size();
    }
}
