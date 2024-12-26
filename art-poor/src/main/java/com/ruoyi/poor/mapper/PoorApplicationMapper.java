package com.ruoyi.poor.mapper;

import java.util.List;
import com.ruoyi.poor.domain.PoorApplication;

/**
 * 贫困申请Mapper接口
 * 
 * @author art
 * @date 2024-10-06
 */
public interface PoorApplicationMapper 
{
    /**
     * 查询贫困申请
     * 
     * @param id 贫困申请主键
     * @return 贫困申请
     */
    public PoorApplication selectPoorApplicationById(Long id);

    /**
     * 查询贫困申请列表
     * 
     * @param poorApplication 贫困申请
     * @return 贫困申请集合
     */
    public List<PoorApplication> selectPoorApplicationList(PoorApplication poorApplication);

    /**
     * 新增贫困申请
     * 
     * @param poorApplication 贫困申请
     * @return 结果
     */
    public int insertPoorApplication(PoorApplication poorApplication);

    /**
     * 修改贫困申请
     * 
     * @param poorApplication 贫困申请
     * @return 结果
     */
    public int updatePoorApplication(PoorApplication poorApplication);

    /**
     * 删除贫困申请
     * 
     * @param id 贫困申请主键
     * @return 结果
     */
    public int deletePoorApplicationById(Long id);

    /**
     * 批量删除贫困申请
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deletePoorApplicationByIds(Long[] ids);
}
