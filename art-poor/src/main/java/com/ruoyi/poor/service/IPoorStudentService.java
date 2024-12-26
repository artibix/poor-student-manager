package com.ruoyi.poor.service;

import java.util.List;
import com.ruoyi.poor.domain.PoorStudent;

/**
 * 学生信息Service接口
 * 
 * @author art
 * @date 2024-10-06
 */
public interface IPoorStudentService 
{
    /**
     * 查询学生信息
     * 
     * @param userId 学生信息主键
     * @return 学生信息
     */
    public PoorStudent selectPoorStudentByUserId(Long userId);

    /**
     * 查询学生信息列表
     * 
     * @param poorStudent 学生信息
     * @return 学生信息集合
     */
    public List<PoorStudent> selectPoorStudentList(PoorStudent poorStudent);

    /**
     * 新增学生信息
     * 
     * @param poorStudent 学生信息
     * @return 结果
     */
    public int insertPoorStudent(PoorStudent poorStudent);

    /**
     * 修改学生信息
     * 
     * @param poorStudent 学生信息
     * @return 结果
     */
    public int updatePoorStudent(PoorStudent poorStudent);

    /**
     * 批量删除学生信息
     * 
     * @param userIds 需要删除的学生信息主键集合
     * @return 结果
     */
    public int deletePoorStudentByUserIds(Long[] userIds);

    /**
     * 删除学生信息信息
     * 
     * @param userId 学生信息主键
     * @return 结果
     */
    public int deletePoorStudentByUserId(Long userId);
}
