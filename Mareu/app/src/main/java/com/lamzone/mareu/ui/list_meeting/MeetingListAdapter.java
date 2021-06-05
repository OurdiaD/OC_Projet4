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
        holder.meetingName.setText(meeting.getSubject());
        holder.participantsName.setText(meeting.getParticipants().toString());

        /*context = holder.itemView.getContext();
        Neighbour neighbour = mNeighbours.get(position);
        holder.mNeighbourName.setText(neighbour.getName());

        Glide.with(holder.mNeighbourAvatar.getContext())
                .load(neighbour.getAvatarUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(holder.mNeighbourAvatar);

        holder.mDeleteButton.setOnClickListener(v -> EventBus.getDefault().post(new DeleteNeighbourEvent(neighbour)));

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ShowNeighbourActivity.class);
            intent.putExtra("neighbour", neighbour);
            ActivityCompat.startActivity(context, intent, null);
        });*/
    }

    @Override
    public int getItemCount() {
        return mMeeting.size();
    }
}
