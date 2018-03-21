package com.zhh.dto.wx;

public class UserInfoEncrypted {

    private String openId;
    private String nickname;
    private String gender;
    private String city;
    private String province;
    private String country;
    private String avatarUrl;
    private String unionId;

    public String getOpenId() {
        return openId;
    }

    public UserInfoEncrypted setOpenId(String openId) {
        this.openId = openId;
        return this;
    }

    public String getNickname() {
        return nickname;
    }

    public UserInfoEncrypted setNickname(String nickname) {
        this.nickname = nickname;
        return this;
    }

    public String getGender() {
        return gender;
    }

    public UserInfoEncrypted setGender(String gender) {
        this.gender = gender;
        return this;
    }

    public String getCity() {
        return city;
    }

    public UserInfoEncrypted setCity(String city) {
        this.city = city;
        return this;
    }

    public String getProvince() {
        return province;
    }

    public UserInfoEncrypted setProvince(String province) {
        this.province = province;
        return this;
    }

    public String getCountry() {
        return country;
    }

    public UserInfoEncrypted setCountry(String country) {
        this.country = country;
        return this;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public UserInfoEncrypted setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
        return this;
    }

    public String getUnionId() {
        return unionId;
    }

    public UserInfoEncrypted setUnionId(String unionId) {
        this.unionId = unionId;
        return this;
    }
}
