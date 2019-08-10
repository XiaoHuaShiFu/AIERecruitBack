package cn.scauaie.model.dao;

import java.util.Date;

public class FormDO {
    private Integer id;

    private String openid;

    private String name;

    private String avatar;

    private String gender;

    private String college;

    private String major;

    private String phone;

    private String firstDep;

    private String secondDep;

    private String introduction;

    private Date createTime;

    private Date updateTime;

    public FormDO(Integer id, String openid, String name, String avatar, String gender, String college, String major, String phone, String firstDep, String secondDep, String introduction, Date createTime, Date updateTime) {
        this.id = id;
        this.openid = openid;
        this.name = name;
        this.avatar = avatar;
        this.gender = gender;
        this.college = college;
        this.major = major;
        this.phone = phone;
        this.firstDep = firstDep;
        this.secondDep = secondDep;
        this.introduction = introduction;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public FormDO() {
        super();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid == null ? null : openid.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar == null ? null : avatar.trim();
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender == null ? null : gender.trim();
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college == null ? null : college.trim();
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major == null ? null : major.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getFirstDep() {
        return firstDep;
    }

    public void setFirstDep(String firstDep) {
        this.firstDep = firstDep == null ? null : firstDep.trim();
    }

    public String getSecondDep() {
        return secondDep;
    }

    public void setSecondDep(String secondDep) {
        this.secondDep = secondDep == null ? null : secondDep.trim();
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}