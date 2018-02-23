package snowroller.globalchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import snowroller.globalchat.Message;
import snowroller.globalchat.R;

/**
 * Created by Martin on 2018-02-21.
 */

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 128;
    private static final int MAX_HEIGHT = 128;
    View mView;
    Context mContext;
    public TextView view;
    public ImageView imageView;

    public MessageHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        view = mView.findViewById(R.id.textView);
        imageView = mView.findViewById(R.id.imageView);
        itemView.setOnClickListener(this);
    }

    public void bindMessage(Message message, int position, RequestManager glide) {

        view.setText(message.getText());

//        if (isImagePosition(position)) {
//            String url = urls.get(position);
//            Glide.with(fragment)
//                    .load(url)
//                    .into(holder.imageView);
//        } else {
//            Glide.with(fragment).clear(holder.imageView);
//            holder.imageView.setImageDrawable(specialDrawable);
//        }

        //Glide.with(mContext).clear(imageView);

            glide
                .load(message.getPhotoUrl())
                .apply(RequestOptions
                        .circleCropTransform()
                        .placeholder(R.drawable.ic_launcher_foreground))
                .into(imageView);
    }

    @Override
    public void onClick(View view) {

    }
}
