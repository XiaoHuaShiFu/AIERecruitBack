package cn.scauaie.model.vo;

import java.util.List;

/**
 * 描述:
 *
 * @author xhsf
 * @email 827032783@qq.com
 * @create 2019-08-09 23:07
 */
public class FormVO {
    private Integer id;

    private String name;

    private String gender;

    private String college;

    private String major;

    private String phone;

    private String avatar;

    private String firstDep;

    private String secondDep;

    private String introduction;

    private List<WorkVO> works;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getFirstDep() {
        return firstDep;
    }

    public void setFirstDep(String firstDep) {
        this.firstDep = firstDep;
    }

    public String getSecondDep() {
        return secondDep;
    }

    public void setSecondDep(String secondDep) {
        this.secondDep = secondDep;
    }

    public String getIntroduction() {
        return introduction;
    }

    public void setIntroduction(String introduction) {
        this.introduction = introduction;
    }

    public List<WorkVO> getWorks() {
        return works;
    }

    public void setWorks(List<WorkVO> works) {
        this.works = works;
    }
}
