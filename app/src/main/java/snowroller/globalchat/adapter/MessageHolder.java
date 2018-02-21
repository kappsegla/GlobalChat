package snowroller.globalchat.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.util.Linkify;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import snowroller.globalchat.Message;
import snowroller.globalchat.R;

/**
 * Created by Martin on 2018-02-21.
 */

public class MessageHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    private static final int MAX_WIDTH = 200;
    private static final int MAX_HEIGHT = 200;
    View mView;
    Context mContext;

    public MessageHolder(View itemView) {
        super(itemView);
        mView = itemView;
        mContext = itemView.getContext();
        itemView.setOnClickListener(this);
    }

    public void bindMessage(Message message, int position) {
        TextView view = mView.findViewById(R.id.textView);
        view.setAutoLinkMask(Linkify.ALL);
        ImageView imageView = mView.findViewById(R.id.imageView);
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


        Glide.with(mContext)
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
