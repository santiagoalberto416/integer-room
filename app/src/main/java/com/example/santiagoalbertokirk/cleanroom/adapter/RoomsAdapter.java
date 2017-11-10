package com.example.santiagoalbertokirk.cleanroom.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.santiagoalbertokirk.cleanroom.R;
import com.example.santiagoalbertokirk.cleanroom.SingleRoomActivity;
import com.example.santiagoalbertokirk.cleanroom.data.Room;

/**
 * Created by santiagoalbertokirk on 28/10/17.
 */

public class RoomsAdapter extends RecyclerView.Adapter<RoomsAdapter.ViewHolder> {
    private Room[] mDataset;
    private Context mContext;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView mNameRoom;
        public TextView mSubtitleText;
        public ViewHolder(View v) {
            super(v);
            mSubtitleText = (TextView)v.findViewById(R.id.subtitle_text);
            mNameRoom = (TextView)v.findViewById(R.id.name_room);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public RoomsAdapter(Room[] myDataset, Context context) {
        mDataset = myDataset;
        this.mContext = context;
    }

    // Create new views (invoked by the layout manager)
    @Override
    public RoomsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                      int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.room_layout, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        holder.mNameRoom.setText(mDataset[position].getName());
        holder.mSubtitleText.setText(mDataset[position].getDescription());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SingleRoomActivity.class);
                intent.putExtra(SingleRoomActivity.ID_ROOM, mDataset[position].getId());
                mContext.startActivity(intent);
            }
        });

    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mDataset.length;
    }
}


