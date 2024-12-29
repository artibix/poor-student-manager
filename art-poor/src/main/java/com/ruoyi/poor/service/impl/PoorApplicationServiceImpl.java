package com.ruoyi.poor.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
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

    /**
     * 审核状态枚举
     */
    public enum ReviewStatus {
        PENDING("待审核", "待审核"),
        APPROVED("已通过", "通过"),
        REJECTED("已拒绝", "不通过");

        private final String code;
        private final String info;

        ReviewStatus(String code, String info) {
            this.code = code;
            this.info = info;
        }

        public String getCode() {
            return code;
        }

        public String getInfo() {
            return info;
        }
    }

    @Override
    public int reviewPoorApplication(PoorApplication poorApplication)
    {
        // 参数校验
        if (poorApplication.getId() == null) {
            throw new ServiceException("申请ID不能为空");
        }
        if (StringUtils.isEmpty(poorApplication.getStatus())) {
            throw new ServiceException("审核状态不能为空");
        }
        if (StringUtils.isEmpty(poorApplication.getReviewComments())) {
            throw new ServiceException("审核意见不能为空");
        }

        // 查询原申请记录
        PoorApplication original = poorApplicationMapper.selectPoorApplicationById(poorApplication.getId());
        if (original == null) {
            throw new ServiceException("申请记录不存在");
        }

        // 检查是否可以审核
//        if (!ReviewStatus.PENDING.getCode().equals(original.getStatus())) {
//            throw new ServiceException("该申请已审核，不能重复审核");
//        }

        // 更新审核信息
        PoorApplication updateApplication = new PoorApplication();
        updateApplication.setId(poorApplication.getId());
        updateApplication.setStatus(poorApplication.getStatus());
        updateApplication.setReviewerId(poorApplication.getReviewerId());
        updateApplication.setReviewDate(poorApplication.getReviewDate());
        updateApplication.setReviewComments(poorApplication.getReviewComments());
        updateApplication.setUpdateTime(DateUtils.getNowDate());

        return poorApplicationMapper.updatePoorApplication(updateApplication);
    }

    @Override
    public Map<String, Long> getApplicationStats() {
        Map<String, Long> stats = new HashMap<>();
        List<Map<String, Object>> rawStats = poorApplicationMapper.selectApplicationStats();

        // 初始化所有状态的计数
        stats.put("total", 0L);
        stats.put("待审核", 0L);
        stats.put("已通过", 0L);
        stats.put("已拒绝", 0L);

        // 处理查询结果
        long total = 0;
        for (Map<String, Object> stat : rawStats) {
            String status = (String) stat.get("status");
            Long count = ((Number) stat.get("count")).longValue();
            stats.put(status, count);
            total += count;
        }
        stats.put("total", total);

        return stats;
    }
}
