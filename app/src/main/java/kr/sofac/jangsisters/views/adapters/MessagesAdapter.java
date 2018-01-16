package kr.sofac.jangsisters.views.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import kr.sofac.jangsisters.R;
import kr.sofac.jangsisters.models.Message;

public class MessagesAdapter extends RecyclerView.Adapter{

    private static final int VIEW_TYPE_MESSAGE_SENT = 1;
    private static final int VIEW_TYPE_MESSAGE_RECEIVED = 2;

    private List<Message> messages;
    private int userID;

    public MessagesAdapter(List<Message> messages, int userID) {
        this.messages = messages;
        this.userID = userID;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view;
        if (viewType == VIEW_TYPE_MESSAGE_RECEIVED){
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, parent, false);
            return new ReceivedHolder(view);
        }
        else{
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message_my, parent, false);
            return new SentHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder.getItemViewType() == VIEW_TYPE_MESSAGE_SENT) {
            ((SentHolder) holder).bind(messages.get(position));
        }
        else{
            ((ReceivedHolder) holder).bind(messages.get(position));
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(messages.get(position).getUserID() == userID){
            return VIEW_TYPE_MESSAGE_SENT;
        }
        return VIEW_TYPE_MESSAGE_RECEIVED;
    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    class SentHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text) TextView text;
        @BindView(R.id.time) TextView time;

        SentHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Message message){
            text.setText(message.getMessage());
            time.setText(message.getTime());
        }
    }

    class ReceivedHolder extends RecyclerView.ViewHolder{

        @BindView(R.id.text) TextView text;
        @BindView(R.id.time) TextView time;

        ReceivedHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        void bind(Message message){
            text.setText(message.getMessage());
            time.setText(message.getTime());
        }
    }
}
