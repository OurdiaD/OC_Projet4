package com.lamzone.mareu.ui.list_meeting;

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

import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.MeetingListViewHolder> {

    private final List<Meeting> mMeeting;
    private final ListMeetingFragment fragment;
    private MeetingListViewHolder lastHolder;


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
    public void onBindViewHolder(@NonNull final MeetingListViewHolder holder, final int position) {
        Meeting meeting = mMeeting.get(position);
        //String dateShow = new SimpleDateFormat("dd/MM/yyyy", Locale.FRANCE).format(meeting.getDate());
        String textMeeting = meeting.getLocation().getRoom() + " - "+ meeting.getDate() +" - " + meeting.getHour() + " - " + meeting.getSubject();

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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expendable(holder);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mMeeting.size();
    }

    public void expendable(MeetingListViewHolder holder){
        if (lastHolder != null){
            lastHolder.meetingName.setSingleLine(true);
            lastHolder.participantsName.setSingleLine(true);
        }

        holder.meetingName.setSingleLine(false);
        holder.participantsName.setSingleLine(false);
        lastHolder = holder;
    }
}
