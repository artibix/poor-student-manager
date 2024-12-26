package com.ruoyi.poor.domain;

import java.util.Date;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 贫困申请对象 poor_application
 * 
 * @author art
 * @date 2024-10-06
 */
public class PoorApplication extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 申请ID */
    private Long id;

    /** 学生id */
    @Excel(name = "学生id")
    private Long studentId;

    /** 申请日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "申请日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date applicationDate;

    /** 贫困原因 */
    @Excel(name = "贫困原因")
    private String povertyReason;

    /** 证明材料 */
    @Excel(name = "证明材料")
    private String supportingDocuments;

    /** 审核状态 */
    @Excel(name = "审核状态")
    private String status;

    /** 审核人ID */
    @Excel(name = "审核人ID")
    private Long reviewerId;

    /** 审核日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "审核日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date reviewDate;

    /** 审核意见 */
    @Excel(name = "审核意见")
    private String reviewComments;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setStudentId(Long studentId) 
    {
        this.studentId = studentId;
    }

    public Long getStudentId() 
    {
        return studentId;
    }
    public void setApplicationDate(Date applicationDate) 
    {
        this.applicationDate = applicationDate;
    }

    public Date getApplicationDate() 
    {
        return applicationDate;
    }
    public void setPovertyReason(String povertyReason) 
    {
        this.povertyReason = povertyReason;
    }

    public String getPovertyReason() 
    {
        return povertyReason;
    }
    public void setSupportingDocuments(String supportingDocuments) 
    {
        this.supportingDocuments = supportingDocuments;
    }

    public String getSupportingDocuments() 
    {
        return supportingDocuments;
    }
    public void setStatus(String status) 
    {
        this.status = status;
    }

    public String getStatus() 
    {
        return status;
    }
    public void setReviewerId(Long reviewerId) 
    {
        this.reviewerId = reviewerId;
    }

    public Long getReviewerId() 
    {
        return reviewerId;
    }
    public void setReviewDate(Date reviewDate) 
    {
        this.reviewDate = reviewDate;
    }

    public Date getReviewDate() 
    {
        return reviewDate;
    }
    public void setReviewComments(String reviewComments) 
    {
        this.reviewComments = reviewComments;
    }

    public String getReviewComments() 
    {
        return reviewComments;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("studentId", getStudentId())
            .append("applicationDate", getApplicationDate())
            .append("povertyReason", getPovertyReason())
            .append("supportingDocuments", getSupportingDocuments())
            .append("status", getStatus())
            .append("reviewerId", getReviewerId())
            .append("reviewDate", getReviewDate())
            .append("reviewComments", getReviewComments())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .toString();
    }
}