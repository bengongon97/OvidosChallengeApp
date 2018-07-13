package com.example.menes.bune;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MasonryAdapter extends RecyclerView.Adapter<MasonryAdapter.MasonryView> {

    private List<RetroPhoto> pList;
    private  Context context;

    public MasonryAdapter(Context context, List<RetroPhoto> pList){
        this.context = context;
        this.pList = pList;
    }

    class MasonryView extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public MasonryView(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.img);
            textView = itemView.findViewById(R.id.img_name);

        }
    }

        @Override
        @NonNull
        public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.grid_item, parent, false);
            return new MasonryView(layoutView);
        }

        @Override
        public void onBindViewHolder(MasonryView holder, int position) {
            holder.textView.setText(pList.get(position).getTitle());

            Picasso.Builder builder = new Picasso.Builder(context);
            builder.downloader(new OkHttp3Downloader(context));
            builder.build().load(pList.get(position).getThumbnailUrl())
                    .placeholder((R.drawable.ic_launcher_background))
                    .error(R.drawable.ic_launcher_background)
                    .into(holder.imageView);
        }

        @Override
        public int getItemCount() {
            return pList.size();
        }
}
