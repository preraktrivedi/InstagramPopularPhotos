package com.mad.demo.instafamous.model;

import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {

    public static InstagramPhoto sampleInstagramOfflineObject(int i) {
        InstagramPhoto photo = new InstagramPhoto();

        photo.setUsername("sampleusername" + i);
        photo.setImgUrl("http://sampleurlfordemo.xyzzzz");
        photo.setImgHeight(480);
        photo.setImgWidth(480);
        photo.setType("photo");
        photo.setLikesCount(i * 239);
        long sampleCreatedAt = (System.currentTimeMillis()/1000) - (i*3600*36); //Intervals of 36 hours
        photo.setCreatedAt(sampleCreatedAt);
        photo.setProfilePicUrl("http://sampleurlfordemo.xyzzzz");
        photo.setCaption("Sample caption for photo " + i);

        return photo;
    }

    public int imgHeight, imgWidth, likesCount;
    private String type, username, caption, imgUrl, profilePicUrl;
    private long createdAt;

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public String getProfilePicUrl() {
        return profilePicUrl;
    }

    public void setProfilePicUrl(String profilePicUrl) {
        this.profilePicUrl = profilePicUrl;
    }

    public String getCaption() {
        return caption;
    }

    public void setCaption(String caption) {
        this.caption = caption;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public int getImgHeight() {
        return imgHeight;
    }

    public void setImgHeight(int imgHeight) {
        this.imgHeight = imgHeight;
    }

    public int getImgWidth() {
        return imgWidth;
    }

    public void setImgWidth(int imgWidth) {
        this.imgWidth = imgWidth;
    }

    public int getLikesCount() {
        return likesCount;
    }

    public void setLikesCount(int likesCount) {
        this.likesCount = likesCount;
    }

    public static InstagramPhoto fromJson(JSONObject photoJSON) throws JSONException {
        InstagramPhoto photo = new InstagramPhoto();

        // These keys should not be null
        photo.setUsername(photoJSON.getJSONObject("user").getString("username"));
        photo.setImgUrl(photoJSON.getJSONObject("images").
                getJSONObject("standard_resolution").getString("url"));
        photo.setImgHeight(photoJSON.getJSONObject("images").
                getJSONObject("standard_resolution").getInt("height"));
        photo.setImgWidth(photoJSON.getJSONObject("images").
                getJSONObject("standard_resolution").getInt("width"));
        photo.setType(photoJSON.getString("type"));
        photo.setLikesCount(photoJSON.getJSONObject("likes").getInt("count"));
        photo.setProfilePicUrl(photoJSON.getJSONObject("user").
                getString("profile_picture"));
        photo.setCreatedAt(photoJSON.getLong("created_time"));

        if (photoJSON.optJSONObject("caption") != null) {
            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
        }
        return photo;
    }
}