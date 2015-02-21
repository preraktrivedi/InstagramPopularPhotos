package com.mad.demo.instafamous.adapter;

import android.content.Context;
import android.graphics.Point;
import android.text.Html;
import android.text.format.DateUtils;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.demo.instafamous.R;
import com.mad.demo.instafamous.model.InstagramPhoto;
import com.mad.demo.instafamous.utilities.CircleTransform;
import com.squareup.picasso.Picasso;

import java.util.List;

public class InstagramPhotoAdapter extends ArrayAdapter<InstagramPhoto> {

    private static class ViewHolder {
        TextView tvUsername;
        TextView tvCaption;
        TextView tvLikes;
        TextView tvCreatedAt;
        ImageView ivProfilePic;
        ImageView ivPhoto;
    }

    // Constructor - what data do we need from activity - context, data source
    public InstagramPhotoAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, R.layout.item_photo, objects);
    }


    // Use the template to display each photo
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) { // If we are not reusing a view
            viewHolder = new ViewHolder();

            // Create a new view from template
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo,
                    parent, false);

            viewHolder.ivProfilePic = (ImageView) convertView.findViewById(R.id.ivProfilePic);
            viewHolder.ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.tvUsername = (TextView) convertView.findViewById(R.id.tvUsername);
            viewHolder.tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.tvLikes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.tvCreatedAt = (TextView) convertView.findViewById(R.id.tvCreatedAt);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String fontHtmlBeg = "<font color=\"" + getContext().getResources().
                getColor(R.color.instagram_blue) + "\">";
        String fontHtmlEnd = "</font>";

        viewHolder.tvUsername.setText(photo.getUsername());

        viewHolder.tvCreatedAt.setText(DateUtils.getRelativeTimeSpanString(photo.getCreatedAt() * 1000,
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS));

        String photoCaption = photo.getCaption();
        StringBuilder formattedCaption = new StringBuilder();
        formattedCaption.append("");
        if (photoCaption != null) {
            formattedCaption.append(fontHtmlBeg + "<b>" + photo.getUsername() + "</b>" +
                    fontHtmlEnd + " ");
            formattedCaption.append(photoCaption);
        }
        viewHolder.tvCaption.setText(Html.fromHtml(formattedCaption.toString()));

        // This piece allows us to get , in the like count - just like the real Instagram client
        viewHolder.tvLikes.setText(String.format("%,d", photo.getLikesCount()) + " " +
                getContext().getResources().getString(R.string.like_str));

        // Insert into ImageView using Picasso - but this takes time so meanwhile we can clear out
        // imageView
        viewHolder.ivPhoto.setImageResource(0); // clears out current image, if any (for recycling)
        viewHolder.ivProfilePic.setImageResource(0);

        // The idea here is to make the image square like Instagram does.
        // This first piece calculates the aspect ratio of the Instagram image, which is, ofc, 1,
        // but just to make things generic.
        int aspectRatio = photo.getImgWidth() / photo.getImgHeight();

        // This piece of code gets the display width, which is sufficient because we know we've
        // set ivPhoto's width to match_parent.
        WindowManager wm = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int targetWidth = size.x;

        // This resizes the image before loading it so that the aspect ratio is maintained.
        // (giving us a square image in this case)
        Picasso.with(getContext())
                .load(photo.getImgUrl())
                .resize(targetWidth, targetWidth / aspectRatio)
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .into(viewHolder.ivPhoto);

        // This uses the circle transform so the profile picture is loaded into a circle
        Picasso.with(getContext())
                .load(photo.getProfilePicUrl())
                .placeholder(R.drawable.loading)
                .error(R.drawable.error)
                .transform(new CircleTransform())
                .into(viewHolder.ivProfilePic);

        return convertView;
    }
}
