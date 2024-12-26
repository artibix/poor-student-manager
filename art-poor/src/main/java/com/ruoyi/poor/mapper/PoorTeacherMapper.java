package com.ruoyi.poor.mapper;

import java.util.List;
import com.ruoyi.poor.domain.PoorTeacher;

/**
 * 教师信息Mapper接口
 * 
 * @author art
 * @date 2024-10-06
 */
public interface PoorTeacherMapper 
{
    /**
     * 查询教师信息
     * 
     * @param userId 教师信息主键
     * @return 教师信息
     */
    public PoorTeacher selectPoorTeacherByUserId(Long userId);

    /**
     * 查询教师信息列表
     * 
     * @param poorTeacher 教师信息
     * @return 教师信息集合
     */
    public List<PoorTeacher> selectPoorTeacherList(PoorTeacher poorTeacher);

    /**
     * 新增教师信息
     * 
     * @param poorTeacher 教师信息
     * @return 结果
     */
    public int insertPoorTeacher(PoorTeacher poorTeacher);

    /**
     * 修改教师信息
     * 
     * @param poorTeacher 教师信息
     * @return 结果
     */
    public int updatePoorTeacher(PoorTeacher poorTeacher);

    /**
     * 删除教师信息
     * 
     * @param userId 教师信息主键
     * @return 结果
     */
    public int deletePoorTeacherByUserId(Long userId);

    /**
     * 批量删除教师信息
     * 
     * @param userIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePoorTeacherByUserIds(Long[] userIds);
}
