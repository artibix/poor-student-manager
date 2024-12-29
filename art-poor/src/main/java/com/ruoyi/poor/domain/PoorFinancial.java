package com.ruoyi.poor.domain;

import java.math.BigDecimal;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 贫困资助对象 poor_financial
 *
 * @author art
 * @date 2024-10-06
 */
public class PoorFinancial extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 资助ID
     */
    private Long id;

    /**
     * 学生id
     */
    @Excel(name = "学生id")
    private Long studentId;

    /**
     * 操作人id
     */
    @Excel(name = "操作人id")
    private Long userId;

    @Excel(name = "学生姓名")
    private String studentName;

    @Excel(name = "操作人姓名")
    private String userName;

    /**
     * 资助类型
     */
    @Excel(name = "资助类型")
    private String aidType;

    /**
     * 资助金额
     */
    @Excel(name = "资助金额")
    private BigDecimal aidAmount;

    /**
     * 发放日期
     */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "发放日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date grantDate;

    /**
     * 发放状态
     */
    @Excel(name = "发放状态")
    private String grantStatus;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    public Long getStudentId() {
        return studentId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getUserId() {
        return userId;
    }

    public void setAidType(String aidType) {
        this.aidType = aidType;
    }

    public String getAidType() {
        return aidType;
    }

    public void setAidAmount(BigDecimal aidAmount) {
        this.aidAmount = aidAmount;
    }

    public BigDecimal getAidAmount() {
        return aidAmount;
    }

    public void setGrantDate(Date grantDate) {
        this.grantDate = grantDate;
    }

    public Date getGrantDate() {
        return grantDate;
    }

    public void setGrantStatus(String grantStatus) {
        this.grantStatus = grantStatus;
    }

    public String getGrantStatus() {
        return grantStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this, ToStringStyle.MULTI_LINE_STYLE)
                .append("id", getId())
                .append("studentId", getStudentId())
                .append("studentName", getStudentName())
                .append("userId", getUserId())
                .append("userName", getUserName())
                .append("aidType", getAidType())
                .append("aidAmount", getAidAmount())
                .append("grantDate", getGrantDate())
                .append("grantStatus", getGrantStatus())
                .append("createTime", getCreateTime())
                .append("updateTime", getUpdateTime())
                .toString();
    }

    public String getStudentName() {
        return studentName;
    }

    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
