package com.ruoyi.web.controller.poor;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.poor.service.IPoorApplicationService;
import com.ruoyi.poor.service.IPoorFinancialService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/poor/dashboard")
public class PoorDashboardController extends BaseController {
    @Autowired
    private IPoorApplicationService applicationService;

    @Autowired
    private IPoorFinancialService financialService;

    @GetMapping("/statistics")
    public AjaxResult getStatistics() {
        Map<String, Object> data = new HashMap<>();

        // 获取申请统计
        Map<String, Long> applicationStats = applicationService.getApplicationStats();
        data.put("applicationStats", applicationStats);

        // 获取各学院资助统计
        List<Map<String, Object>> collegeStats = financialService.getCollegeStats();
        data.put("collegeStats", collegeStats);

        // 获取月度资助金额
        List<Map<String, Object>> monthlyAmount = financialService.getMonthlyAmount();
        data.put("monthlyAmount", monthlyAmount);

        // 获取资助类型分布
        List<Map<String, Object>> aidTypeStats = financialService.getAidTypeStats();
        data.put("aidTypeStats", aidTypeStats);

        return success(data);
    }
}