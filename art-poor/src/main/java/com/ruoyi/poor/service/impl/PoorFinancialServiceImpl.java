package com.ruoyi.poor.service.impl;

import java.util.List;
import java.util.Map;

import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.poor.mapper.PoorFinancialMapper;
import com.ruoyi.poor.domain.PoorFinancial;
import com.ruoyi.poor.service.IPoorFinancialService;

/**
 * 贫困资助Service业务层处理
 * 
 * @author art
 * @date 2024-10-06
 */
@Service
public class PoorFinancialServiceImpl implements IPoorFinancialService 
{
    @Autowired
    private PoorFinancialMapper poorFinancialMapper;

    /**
     * 查询贫困资助
     * 
     * @param id 贫困资助主键
     * @return 贫困资助
     */
    @Override
    public PoorFinancial selectPoorFinancialById(Long id)
    {
        return poorFinancialMapper.selectPoorFinancialById(id);
    }

    /**
     * 查询贫困资助列表
     * 
     * @param poorFinancial 贫困资助
     * @return 贫困资助
     */
    @Override
    public List<PoorFinancial> selectPoorFinancialList(PoorFinancial poorFinancial)
    {
        return poorFinancialMapper.selectPoorFinancialList(poorFinancial);
    }

    /**
     * 新增贫困资助
     * 
     * @param poorFinancial 贫困资助
     * @return 结果
     */
    @Override
    public int insertPoorFinancial(PoorFinancial poorFinancial)
    {
        poorFinancial.setCreateTime(DateUtils.getNowDate());
        return poorFinancialMapper.insertPoorFinancial(poorFinancial);
    }

    /**
     * 修改贫困资助
     * 
     * @param poorFinancial 贫困资助
     * @return 结果
     */
    @Override
    public int updatePoorFinancial(PoorFinancial poorFinancial)
    {
        poorFinancial.setUpdateTime(DateUtils.getNowDate());
        return poorFinancialMapper.updatePoorFinancial(poorFinancial);
    }

    /**
     * 批量删除贫困资助
     * 
     * @param ids 需要删除的贫困资助主键
     * @return 结果
     */
    @Override
    public int deletePoorFinancialByIds(Long[] ids)
    {
        return poorFinancialMapper.deletePoorFinancialByIds(ids);
    }

    /**
     * 删除贫困资助信息
     * 
     * @param id 贫困资助主键
     * @return 结果
     */
    @Override
    public int deletePoorFinancialById(Long id)
    {
        return poorFinancialMapper.deletePoorFinancialById(id);
    }

    @Override
    public List<Map<String, Object>> getCollegeStats() {
        return poorFinancialMapper.selectCollegeStats();
    }

    @Override
    public List<Map<String, Object>> getMonthlyAmount() {
        return poorFinancialMapper.selectMonthlyAmount();
    }

    @Override
    public List<Map<String, Object>> getAidTypeStats() {
        return poorFinancialMapper.selectAidTypeStats();
    }
}
