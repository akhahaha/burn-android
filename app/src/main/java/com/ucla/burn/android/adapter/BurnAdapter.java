package com.ucla.burn.android.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucla.burn.android.R;
import com.ucla.burn.android.model.ListItem;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Jonathan on 10/1/2016.
 */

public class BurnAdapter extends RecyclerView.Adapter<BurnAdapter.BurnHolder> {

    private List<ListItem> listData;
    private LayoutInflater inflater;

    private ItemClickCallBack itemClickCallBack;

    public interface ItemClickCallBack {
        void onItemClick(int p);
    }

    public void setItemClickCallBack(final ItemClickCallBack itemClickCallBack) {
        this.itemClickCallBack = itemClickCallBack;
    }


    public BurnAdapter(List<ListItem> listData, Context c) {
        this.inflater = LayoutInflater.from(c);
        this.listData = listData;
    }

    @Override
    public BurnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new BurnHolder(view);
    }

    @Override
    public void onBindViewHolder(BurnHolder holder, final int position) {
        ListItem item = listData.get(position);
        holder.title.setText(item.getTitle());
        holder.subTitle.setText(item.getSubTitle());

        holder.burn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listData.get(position).setFavourite(!listData.get(position).isFavourite());
                notifyDataSetChanged();
            }
        });

        holder.container.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemClickCallBack.onItemClick(position);
            }
        });

        if (item.isFavourite()) {
            holder.burn.setImageResource(R.drawable.campfire_red);
        } else {
            holder.burn.setImageResource(R.drawable.campfire);
        }

    }

    @Override
    public int getItemCount() {
        return listData.size();
    }

    //adapter needs a viewholder object
    class BurnHolder extends RecyclerView.ViewHolder {

        TextView title;
        TextView subTitle;
        ImageView icon;
        ImageView burn;
        private View container;
        private TextView numvotes;

        public BurnHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            subTitle = (TextView) itemView.findViewById(R.id.lbl_item_sub_title);
            icon = (ImageView) itemView.findViewById(R.id.im_item_icon);
            burn = (ImageView) itemView.findViewById(R.id.im_item_icon_burn);
            //numvotes = (TextView) itemView.findViewById(R.id.lbl_numvotes);
            container = (View) itemView.findViewById(R.id.cont_item_root);
        }
    }

    public void setListData(ArrayList<ListItem> exerciseList) {
        this.listData.clear();
        this.listData.addAll(exerciseList);
    }

}
