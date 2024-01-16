package com.jhems.mygpt;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MyviewHolder> {

    List<Message> messageList;
    public MessageAdapter(List<Message> messageList, MainActivity mainActivity) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MyviewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View chatView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_message, null);
        MyviewHolder myViewHolder = new MyviewHolder(chatView);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MyviewHolder holder, int position) {
        Message message = messageList.get(position);
        if(message.getSendby().equals(Message.SENT_BY_ME)){
            holder.leftChatView.setVisibility(View.GONE);
            holder.rightChatView.setVisibility(View.VISIBLE);
            holder.rightTextView.setText(message.getMessage());
        } else {
            holder.rightChatView.setVisibility(View.GONE);
            holder.leftChatView.setVisibility(View.VISIBLE);
            holder.leftTextView.setText(message.getMessage());
        }
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }


    public class MyviewHolder extends RecyclerView.ViewHolder{

        LinearLayout leftChatView, rightChatView;
        TextView leftTextView, rightTextView;

        public MyviewHolder(@NonNull View itemView) {
            super(itemView);

            leftChatView = itemView.findViewById(R.id.left_chat_view);
            rightChatView = itemView.findViewById(R.id.right_chat_view);
            leftTextView = itemView.findViewById(R.id.left_chat_text_view);
            rightTextView = itemView.findViewById(R.id.right_chat_text_view);
        }
    }
}