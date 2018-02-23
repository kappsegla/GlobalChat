package snowroller.globalchat.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.RequestManager;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;

import snowroller.globalchat.Message;
import snowroller.globalchat.R;

/**
 * Created by Martin on 2018-02-22.
 */

public class MessageFirestoreRecyclerAdapter extends FirestoreRecyclerAdapter<Message, MessageHolder> {

    private final RequestManager glide;

    public MessageFirestoreRecyclerAdapter(RequestManager glide, FirestoreRecyclerOptions<Message> options) {
        super(options);
        this.glide = glide;
    }

    @Override
    public void onBindViewHolder(MessageHolder holder, int position, Message model) {
        // Bind the Chat object to the ChatHolder
        // ...
        holder.bindMessage(model, position, glide);
    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup group, int i) {
        // Create a new instance of the ViewHolder, in this case we are using a custom
        // layout called R.layout.message for each item
        View view = LayoutInflater.from(group.getContext())
                .inflate(R.layout.message, group, false);

        return new MessageHolder(view);
    }

    @Override
    public void onViewRecycled(MessageHolder holder) {
        super.onViewRecycled(holder);
        glide.clear(holder.imageView);
    }
}
