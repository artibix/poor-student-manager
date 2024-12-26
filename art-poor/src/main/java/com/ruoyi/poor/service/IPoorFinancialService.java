package com.ruoyi.poor.service;

import java.util.List;
import com.ruoyi.poor.domain.PoorFinancial;

/**
 * 贫困资助Service接口
 * 
 * @author art
 * @date 2024-10-06
 */
public interface IPoorFinancialService 
{
    /**
     * 查询贫困资助
     * 
     * @param id 贫困资助主键
     * @return 贫困资助
     */
    public PoorFinancial selectPoorFinancialById(Long id);

    /**
     * 查询贫困资助列表
     * 
     * @param poorFinancial 贫困资助
     * @return 贫困资助集合
     */
    public List<PoorFinancial> selectPoorFinancialList(PoorFinancial poorFinancial);

    /**
     * 新增贫困资助
     * 
     * @param poorFinancial 贫困资助
     * @return 结果
     */
    public int insertPoorFinancial(PoorFinancial poorFinancial);

    /**
     * 修改贫困资助
     * 
     * @param poorFinancial 贫困资助
     * @return 结果
     */
    public int updatePoorFinancial(PoorFinancial poorFinancial);

    /**
     * 批量删除贫困资助
     * 
     * @param ids 需要删除的贫困资助主键集合
     * @return 结果
     */
    public int deletePoorFinancialByIds(Long[] ids);

    /**
     * 删除贫困资助信息
     * 
     * @param id 贫困资助主键
     * @return 结果
     */
    public int deletePoorFinancialById(Long id);
}
