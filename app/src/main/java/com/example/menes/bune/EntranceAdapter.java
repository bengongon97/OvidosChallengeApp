package com.example.menes.bune;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

public class EntranceAdapter extends RecyclerView.Adapter<EntranceAdapter.EntranceView> {

    public interface OnItemClickListener {
        void onItemClick (int position);
    }

    private EntranceAdapter.OnItemClickListener onItemClickListener;
    private List<RetroAlbum> myAlbum;
    private Context context;

    public EntranceAdapter(Context context, List<RetroAlbum> myAlbum){
        this.context = context;
        this.myAlbum = myAlbum;
    }

    public void setOnItemClickListener(EntranceAdapter.OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    class EntranceView extends RecyclerView.ViewHolder {
        TextView EntranceText;
        LinearLayout lnrLayout;

        public EntranceView(View itemView) {
            super(itemView);

            lnrLayout =  itemView.findViewById(R.id.lnrLayout);
            EntranceText =  itemView.findViewById(R.id.entranceText);
        }
    }

    @Override
    public EntranceView onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.entrance_row, parent, false);
        EntranceView entranceView = new EntranceView(layoutView);
        return entranceView;
    }

    @Override
    public void onBindViewHolder(EntranceView holder, final int position) {
        RetroAlbum row = myAlbum.get(position);
        holder.EntranceText.setText(row.getTitle());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(onItemClickListener != null) {
                    onItemClickListener.onItemClick(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return myAlbum.size();
    }
}
