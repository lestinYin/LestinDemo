package com.lestin.yin.entity;

import java.io.Serializable;

/**
 * @ProjectName:
 * @Package:
 * @ClassName: EUser
 * @Description: 用户信息类
 * @Author: Lestin.Yin
 * @CreateDate: 2019/7/23 10:58
 * @Version: 1.0
 */


public class EUser implements Serializable {
    private String token;
    private String uid;
    private String username;
    private String nickname;
    private String phone;
    public Boolean isGuest;
    private int sex;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Boolean getGuest() {
        return isGuest;
    }

    public void setGuest(Boolean guest) {
        isGuest = guest;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    @Override
    public String toString() {
        return "EUser{" +
                "token='" + token + '\'' +
                ", uid='" + uid + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", phone='" + phone + '\'' +
                ", isGuest=" + isGuest +
                ", sex=" + sex +
                '}';
    }
}
