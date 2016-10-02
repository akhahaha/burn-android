package com.ucla.burn.android.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ucla.burn.android.R;
import com.ucla.burn.android.Session;
import com.ucla.burn.android.data.BurnDAO;
import com.ucla.burn.android.data.Callback;
import com.ucla.burn.android.model.Conversation;
import com.ucla.burn.android.model.User;

import java.util.ArrayList;
import java.util.List;

public class ConversationListAdapter
        extends RecyclerView.Adapter<ConversationListAdapter.BurnHolder> {
    private OnItemClickListener onItemClickListener;

    public interface OnItemClickListener {
        void onItemClick(View view, Conversation conversation, int position);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void removeOnItemClickListener() {
        onItemClickListener = null;
    }

    private List<Conversation> conversations;

    public ConversationListAdapter() {
        this.conversations = new ArrayList<>();
    }

    public ConversationListAdapter(List<Conversation> listData) {
        this.conversations = listData;
    }

    public List<Conversation> getConversations() {
        return conversations;
    }

    public void setConversations(List<Conversation> conversations) {
        this.conversations = conversations;
        notifyDataSetChanged();
    }

    @Override
    public BurnHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new BurnHolder(view);
    }

    @Override
    public void onBindViewHolder(final BurnHolder holder, int position) {
        final Conversation item = conversations.get(position);
        holder.title.setText(item.getTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = holder.getAdapterPosition();
                if (onItemClickListener != null) {
                    onItemClickListener.onItemClick(view, item, pos);
                }
            }
        });

        final User currUser = Session.getInstance().getCurrentUser();
        holder.burn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Toggle vote
                item.setVote(currUser,
                        !conversations.get(holder.getAdapterPosition()).hasVote(currUser));
                BurnDAO.updateConversation(item, new Callback<Conversation>() {
                    @Override
                    public void onResponse(Conversation response) {
                        System.out.println(response);
                    }

                    @Override
                    public void onFailed(Exception e) {
                        e.printStackTrace();
                    }
                });
                notifyDataSetChanged();
            }
        });

        if (item.hasVote(currUser)) {
            holder.burn.setImageResource(R.drawable.campfire_red);
        } else {
            holder.burn.setImageResource(R.drawable.campfire);
        }

        holder.numvotes.setText(String.valueOf(item.getScore()));
    }

    @Override
    public int getItemCount() {
        return conversations.size();
    }

    public static class BurnHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView subTitle;
        ImageView icon;
        ImageView burn;
        TextView numvotes;

        public BurnHolder(View itemView) {
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.lbl_item_text);
            subTitle = (TextView) itemView.findViewById(R.id.lbl_item_sub_title);
            icon = (ImageView) itemView.findViewById(R.id.im_item_icon);
            burn = (ImageView) itemView.findViewById(R.id.im_item_icon_burn);
            numvotes = (TextView) itemView.findViewById(R.id.lbl_numvotes);
        }
    }
}
