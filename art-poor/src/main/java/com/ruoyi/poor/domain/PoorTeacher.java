package com.ruoyi.poor.domain;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 教师信息对象 poor_teacher
 *
 * @author art
 * @date 2024-10-06
 */
public class PoorTeacher extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    private Long userId;

    @Excel(name = "姓名")
    private String userName;

    /**
     * 教师编号
     */
    @Excel(name = "教师编号")
    private String number;

    /**
     * 家庭住址
     */
    @Excel(name = "家庭住址")
    private String position;

    /**
     * 入职日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "入职日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date hireDate;

    /**
     * 职称
     */
    @Excel(name = "职称")
    private String level;

    /**
     * 是否激活
     */
    @Excel(name = "是否激活")
    private Integer isActivated;

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getPosition() {
        return position;
    }

    public void setHireDate(Date hireDate) {
        this.hireDate = hireDate;
    }

    public Date getHireDate() {
        return hireDate;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public void setIsActivated(Integer isActivated) {
        this.isActivated = isActivated;
    }

    public Integer getIsActivated() {
        return isActivated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("number", getNumber())
                .append("position", getPosition())
                .append("hireDate", getHireDate())
                .append("level", getLevel())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .append("isActivated", getIsActivated())
                .toString();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
