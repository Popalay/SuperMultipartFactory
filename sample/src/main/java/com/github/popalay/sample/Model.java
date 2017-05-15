package com.github.popalay.sample;

import com.github.popalay.supermultipartfactory.Partable;
import com.google.gson.annotations.SerializedName;

@Partable
public class Model {

    @SerializedName("id")
    private int id;

    @SerializedName("first_name")
    private String firstName;

    @SerializedName("last_name")
    private String lastName;

    @SerializedName("avatar")
    private String avatar;

    @Partable
    @SerializedName("order")
    private NestedModel order;

    public Model() {
    }

    public Model(int id, String firstName, String lastName, String avatar, NestedModel order) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.avatar = avatar;
        this.order = order;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public NestedModel getOrder() {
        return order;
    }

    public void setOrder(NestedModel order) {
        this.order = order;
    }
}
