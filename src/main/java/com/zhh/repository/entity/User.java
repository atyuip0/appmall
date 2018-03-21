package com.zhh.repository.entity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name="t_users")
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String openId;

    private String nickname;

    private String email;

    private String phone;

    private String password;

    private Integer gender;

    private Integer status;

    private String lastip;

    private String city;

    private String province;

    private String country;

    private String avatarurl;

    public Long getId() {
        return id;
    }

    public User setId(Long id) {
        this.id = id;
        return this;
    }

    public String getOpenId() {
        return openId;
    }

    public User setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public User setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getEmail() {
        return email;
    }

    public User setEmail(String email) {
        this.email = email;
        return this;
    }

    public String getPhone() {
        return phone;
    }

    public User setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public User setPassword(String password) {
        this.password = password;
        return this;
    }

    public Integer getGender() {
        return gender;
    }

    public User setGender(Integer gender) {
        this.gender = gender;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public User setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public String getLastip() {
        return lastip;
    }

    public User setLastip(String lastip) {
        this.lastip = lastip;
        return this;
    }

    public String getCity() {
        return city;
    }

    public User setCity(String city) {
        this.city = city;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public User setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public User setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAvatarurl() {
        return avatarurl;
    }

    public User setAvatarurl(String avatarurl) {
        this.avatarurl = avatarurl;
        return this;
    }

}