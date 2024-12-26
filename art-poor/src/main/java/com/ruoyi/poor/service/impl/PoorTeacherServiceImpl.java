package com.ruoyi.poor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.poor.mapper.PoorTeacherMapper;
import com.ruoyi.poor.domain.PoorTeacher;
import com.ruoyi.poor.service.IPoorTeacherService;

/**
 * 教师信息Service业务层处理
 * 
 * @author art
 * @date 2024-10-06
 */
@Service
public class PoorTeacherServiceImpl implements IPoorTeacherService 
{
    @Autowired
    private PoorTeacherMapper poorTeacherMapper;

    /**
     * 查询教师信息
     * 
     * @param userId 教师信息主键
     * @return 教师信息
     */
    @Override
    public PoorTeacher selectPoorTeacherByUserId(Long userId)
    {
        return poorTeacherMapper.selectPoorTeacherByUserId(userId);
    }

    /**
     * 查询教师信息列表
     * 
     * @param poorTeacher 教师信息
     * @return 教师信息
     */
    @Override
    public List<PoorTeacher> selectPoorTeacherList(PoorTeacher poorTeacher)
    {
        return poorTeacherMapper.selectPoorTeacherList(poorTeacher);
    }

    /**
     * 新增教师信息
     * 
     * @param poorTeacher 教师信息
     * @return 结果
     */
    @Override
    public int insertPoorTeacher(PoorTeacher poorTeacher)
    {
        poorTeacher.setCreateTime(DateUtils.getNowDate());
        return poorTeacherMapper.insertPoorTeacher(poorTeacher);
    }

    /**
     * 修改教师信息
     * 
     * @param poorTeacher 教师信息
     * @return 结果
     */
    @Override
    public int updatePoorTeacher(PoorTeacher poorTeacher)
    {
        poorTeacher.setUpdateTime(DateUtils.getNowDate());
        return poorTeacherMapper.updatePoorTeacher(poorTeacher);
    }

    /**
     * 批量删除教师信息
     * 
     * @param userIds 需要删除的教师信息主键
     * @return 结果
     */
    @Override
    public int deletePoorTeacherByUserIds(Long[] userIds)
    {
        return poorTeacherMapper.deletePoorTeacherByUserIds(userIds);
    }

    /**
     * 删除教师信息信息
     * 
     * @param userId 教师信息主键
     * @return 结果
     */
    @Override
    public int deletePoorTeacherByUserId(Long userId)
    {
        return poorTeacherMapper.deletePoorTeacherByUserId(userId);
    }
}
