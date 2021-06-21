package com.lamzone.mareu.ui.list_meeting;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lamzone.mareu.R;
import com.lamzone.mareu.model.Meeting;
import com.lamzone.mareu.model.User;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingListViewHolder> {

    private final List<Meeting> mMeeting;
    private ListMeetingFragment fragment;


    public MeetingListAdapter(List<Meeting> meeting, ListMeetingFragment fragment) {
        this.mMeeting = meeting;
        this.fragment = fragment;
    }

    static class MeetingListViewHolder extends RecyclerView.ViewHolder {
        private final ImageView meetingImage;
        private final TextView meetingName;
        private final TextView participantsName;
        private final ImageButton deleteButton;

        private MeetingListViewHolder(View itemView) {
            super(itemView);
            meetingImage = itemView.findViewById(R.id.imageView);
            meetingName = itemView.findViewById(R.id.meeting);
            participantsName = itemView.findViewById(R.id.participants);
            deleteButton = itemView.findViewById(R.id.delete);
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
    public void onBindViewHolder(@NonNull MeetingListViewHolder holder, final int position) {
        Meeting meeting = mMeeting.get(position);
        String dateShow = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(meeting.getDate());
        String textMeeting = meeting.getLocation().getRoom() + " - "+ dateShow +" - " + meeting.getHour() + " - " + meeting.getSubject();

        StringBuilder textParticipants = new StringBuilder();
        int i = 0;
        for (User user : meeting.getParticipants()){
            textParticipants.append(user.getMail());
            if (i < meeting.getParticipants().size() -1)
                textParticipants.append(", ");
            i++;
        }

        GradientDrawable shape = new GradientDrawable();
        shape.setShape(GradientDrawable.OVAL);
        shape.setCornerRadii(new float[]{0, 0, 0, 0, 0, 0, 0, 0});
        shape.setColor(Color.parseColor(meeting.getLocation().getColor()));

        holder.meetingName.setText(textMeeting);
        holder.participantsName.setText(textParticipants);
        holder.meetingImage.setBackground(shape);

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragment.deleteMeeting(mMeeting.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeeting.size();
    }
}
