package com.mad.demo.instafamous.model;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramPhoto {

    private String type;
    private String username;
    private String caption;
    private String imgUrl;
    private String profilePicUrl;
    private long createdAt;
    private JSONArray tags;
    private JSONArray allComments;

    public JSONArray getAllComments() {
        return allComments;
    }

    public void setAllComments(JSONArray allComments) {
        this.allComments = allComments;
    }

    public int getNumComments() {
        return numComments;
    }

    public void setNumComments(int numComments) {
        this.numComments = numComments;
    }

    private int numComments;

    public JSONArray getTags() {
        return tags;
    }

    public void setTags(JSONArray tags) {
        this.tags = tags;
    }

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

    public int imgHeight;
    public int imgWidth;
    public int likesCount;


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
        photo.setTags(photoJSON.getJSONArray("tags"));

        if (photoJSON.optJSONObject("caption") != null) {
            photo.setCaption(photoJSON.getJSONObject("caption").getString("text"));
        }

        if (photoJSON.optJSONObject("comments") != null) {
            JSONObject commentsObj = photoJSON.getJSONObject("comments");

            photo.setNumComments(commentsObj.getInt("count"));
            JSONArray commentsArr = commentsObj.getJSONArray("data");
            photo.setAllComments(commentsArr);
        }
        return photo;
    }
}