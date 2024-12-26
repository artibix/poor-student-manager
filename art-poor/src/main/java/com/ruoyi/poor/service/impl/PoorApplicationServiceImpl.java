package com.ruoyi.poor.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.poor.mapper.PoorApplicationMapper;
import com.ruoyi.poor.domain.PoorApplication;
import com.ruoyi.poor.service.IPoorApplicationService;

/**
 * 贫困申请Service业务层处理
 * 
 * @author art
 * @date 2024-10-06
 */
@Service
public class PoorApplicationServiceImpl implements IPoorApplicationService 
{
    @Autowired
    private PoorApplicationMapper poorApplicationMapper;

    /**
     * 查询贫困申请
     * 
     * @param id 贫困申请主键
     * @return 贫困申请
     */
    @Override
    public PoorApplication selectPoorApplicationById(Long id)
    {
        return poorApplicationMapper.selectPoorApplicationById(id);
    }

    /**
     * 查询贫困申请列表
     * 
     * @param poorApplication 贫困申请
     * @return 贫困申请
     */
    @Override
    public List<PoorApplication> selectPoorApplicationList(PoorApplication poorApplication)
    {
        return poorApplicationMapper.selectPoorApplicationList(poorApplication);
    }

    /**
     * 新增贫困申请
     * 
     * @param poorApplication 贫困申请
     * @return 结果
     */
    @Override
    public int insertPoorApplication(PoorApplication poorApplication)
    {
        poorApplication.setCreateTime(DateUtils.getNowDate());
        return poorApplicationMapper.insertPoorApplication(poorApplication);
    }

    /**
     * 修改贫困申请
     * 
     * @param poorApplication 贫困申请
     * @return 结果
     */
    @Override
    public int updatePoorApplication(PoorApplication poorApplication)
    {
        poorApplication.setUpdateTime(DateUtils.getNowDate());
        return poorApplicationMapper.updatePoorApplication(poorApplication);
    }

    /**
     * 批量删除贫困申请
     * 
     * @param ids 需要删除的贫困申请主键
     * @return 结果
     */
    @Override
    public int deletePoorApplicationByIds(Long[] ids)
    {
        return poorApplicationMapper.deletePoorApplicationByIds(ids);
    }

    /**
     * 删除贫困申请信息
     * 
     * @param id 贫困申请主键
     * @return 结果
     */
    @Override
    public int deletePoorApplicationById(Long id)
    {
        return poorApplicationMapper.deletePoorApplicationById(id);
    }
}
