package cn.scauaie.model.ao;

import cn.scauaie.model.ao.group.GroupFormAO;
import cn.scauaie.model.ao.group.GroupFormAOPOST;
import cn.scauaie.model.vo.WorkVO;
import cn.scauaie.validator.annotation.Dep;
import cn.scauaie.validator.annotation.Gender;
import cn.scauaie.validator.annotation.Phone;

import javax.validation.constraints.*;
import java.util.List;

public class FormAO {

    @Min(message = "INVALID_PARAMETER_VALUE_BELOW: The name of id below, min: 0.",
            value = 0,
            groups = {GroupFormAO.class})
    private Integer id;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The name must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of name must be from 1 to 10.",
            min = 1, max = 10,
            groups = {GroupFormAO.class})
    private String name;


    @Size(message = "INVALID_PARAMETER_SIZE: The size of avatar must be from 0 to 150.",
            min = 1, max = 150,
            groups = {GroupFormAO.class})
    private String avatar;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The gender must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Gender(groups = {GroupFormAO.class})
    private String gender;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The college must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of college must be from 0 to 20.",
            min = 1, max = 20,
            groups = {GroupFormAO.class})
    private String college;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The major must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of major must be from 0 to 20.",
            min = 1, max = 20,
            groups = {GroupFormAO.class})
    private String major;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The phone must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Phone(groups = {GroupFormAO.class})
    private String phone;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The firstDep must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Dep(groups = {GroupFormAO.class})
    private String firstDep;


    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The secondDep must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Dep(groups = {GroupFormAO.class})
    private String secondDep;

    @NotBlank(message = "INVALID_PARAMETER_IS_BLANK: The introduction must be not blank.",
            groups = {GroupFormAOPOST.class})
    @Size(message = "INVALID_PARAMETER_SIZE: The size of introduction must be from 1 to 1000.",
            min = 1, max = 1000,
            groups = {GroupFormAO.class})
    private String introduction;

    private List<WorkAO> works;

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

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
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

    public List<WorkAO> getWorks() {
        return works;
    }

    public void setWorks(List<WorkAO> works) {
        this.works = works;
    }

    @Override
    public String toString() {
        return "FormAO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", avatar='" + avatar + '\'' +
                ", gender='" + gender + '\'' +
                ", college='" + college + '\'' +
                ", major='" + major + '\'' +
                ", phone='" + phone + '\'' +
                ", firstDep='" + firstDep + '\'' +
                ", secondDep='" + secondDep + '\'' +
                ", introduction='" + introduction + '\'' +
                ", works=" + works +
                '}';
    }
}