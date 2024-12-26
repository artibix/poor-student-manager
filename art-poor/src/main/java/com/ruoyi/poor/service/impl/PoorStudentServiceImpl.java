package com.ruoyi.poor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.poor.mapper.PoorStudentMapper;
import com.ruoyi.poor.domain.PoorStudent;
import com.ruoyi.poor.service.IPoorStudentService;

/**
 * 学生信息Service业务层处理
 * 
 * @author art
 * @date 2024-10-06
 */
@Service
public class PoorStudentServiceImpl implements IPoorStudentService 
{
    @Autowired
    private PoorStudentMapper poorStudentMapper;

    /**
     * 查询学生信息
     * 
     * @param userId 学生信息主键
     * @return 学生信息
     */
    @Override
    public PoorStudent selectPoorStudentByUserId(Long userId)
    {
        return poorStudentMapper.selectPoorStudentByUserId(userId);
    }

    /**
     * 查询学生信息列表
     * 
     * @param poorStudent 学生信息
     * @return 学生信息
     */
    @Override
    public List<PoorStudent> selectPoorStudentList(PoorStudent poorStudent)
    {
        return poorStudentMapper.selectPoorStudentList(poorStudent);
    }

    /**
     * 新增学生信息
     * 
     * @param poorStudent 学生信息
     * @return 结果
     */
    @Override
    public int insertPoorStudent(PoorStudent poorStudent)
    {
        poorStudent.setCreateTime(DateUtils.getNowDate());
        return poorStudentMapper.insertPoorStudent(poorStudent);
    }

    /**
     * 修改学生信息
     * 
     * @param poorStudent 学生信息
     * @return 结果
     */
    @Override
    public int updatePoorStudent(PoorStudent poorStudent)
    {
        poorStudent.setUpdateTime(DateUtils.getNowDate());
        return poorStudentMapper.updatePoorStudent(poorStudent);
    }

    /**
     * 批量删除学生信息
     * 
     * @param userIds 需要删除的学生信息主键
     * @return 结果
     */
    @Override
    public int deletePoorStudentByUserIds(Long[] userIds)
    {
        return poorStudentMapper.deletePoorStudentByUserIds(userIds);
    }

    /**
     * 删除学生信息信息
     * 
     * @param userId 学生信息主键
     * @return 结果
     */
    @Override
    public int deletePoorStudentByUserId(Long userId)
    {
        return poorStudentMapper.deletePoorStudentByUserId(userId);
    }
}
