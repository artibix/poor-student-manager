package com.ruoyi.poor.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 学生信息对象 poor_student
 * 
 * @author art
 * @date 2024-10-06
 */
public class PoorStudent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 用户id */
    private Long userId;

    /** 学号 */
    @Excel(name = "学号")
    private String number;

    /** 学院 */
    @Excel(name = "学院")
    private String college;

    /** 专业 */
    @Excel(name = "专业")
    private String major;

    /** 年级 */
    @Excel(name = "年级")
    private String grade;

    /** 家庭住址 */
    @Excel(name = "家庭住址")
    private String familyAddress;

    /** 家庭年收入 */
    @Excel(name = "家庭年收入")
    private BigDecimal familyIncome;

    /** 是否激活 */
    @Excel(name = "是否激活")
    private Integer isActivated;

    public void setUserId(Long userId) 
    {
        this.userId = userId;
    }

    public Long getUserId() 
    {
        return userId;
    }
    public void setNumber(String number) 
    {
        this.number = number;
    }

    public String getNumber() 
    {
        return number;
    }
    public void setCollege(String college) 
    {
        this.college = college;
    }

    public String getCollege() 
    {
        return college;
    }
    public void setMajor(String major) 
    {
        this.major = major;
    }

    public String getMajor() 
    {
        return major;
    }
    public void setGrade(String grade) 
    {
        this.grade = grade;
    }

    public String getGrade() 
    {
        return grade;
    }
    public void setFamilyAddress(String familyAddress) 
    {
        this.familyAddress = familyAddress;
    }

    public String getFamilyAddress() 
    {
        return familyAddress;
    }
    public void setFamilyIncome(BigDecimal familyIncome) 
    {
        this.familyIncome = familyIncome;
    }

    public BigDecimal getFamilyIncome() 
    {
        return familyIncome;
    }
    public void setIsActivated(Integer isActivated) 
    {
        this.isActivated = isActivated;
    }

    public Integer getIsActivated() 
    {
        return isActivated;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("userId", getUserId())
            .append("number", getNumber())
            .append("college", getCollege())
            .append("major", getMajor())
            .append("grade", getGrade())
            .append("familyAddress", getFamilyAddress())
            .append("familyIncome", getFamilyIncome())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("isActivated", getIsActivated())
            .toString();
    }
}
