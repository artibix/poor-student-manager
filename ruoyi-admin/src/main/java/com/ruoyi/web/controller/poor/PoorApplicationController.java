package com.ruoyi.web.controller.poor;

import java.util.Date;
import java.util.List;
import javax.servlet.http.HttpServletResponse;

import com.ruoyi.poor.domain.PoorApplication;
import com.ruoyi.poor.service.IPoorApplicationService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 贫困申请Controller
 * 
 * @author art
 * @date 2024-09-29
 */
@RestController
@RequestMapping("/poor/application")
public class PoorApplicationController extends BaseController
{
    @Autowired
    private IPoorApplicationService poorApplicationService;

    /**
     * 查询贫困申请列表
     */
    @PreAuthorize("@ss.hasPermi('poor:application:list')")
    @GetMapping("/list")
    public TableDataInfo list(PoorApplication poorApplication)
    {
        startPage();
        List<PoorApplication> list = poorApplicationService.selectPoorApplicationList(poorApplication);
        return getDataTable(list);
    }

    /**
     * 导出贫困申请列表
     */
    @PreAuthorize("@ss.hasPermi('poor:application:export')")
    @Log(title = "贫困申请", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PoorApplication poorApplication)
    {
        List<PoorApplication> list = poorApplicationService.selectPoorApplicationList(poorApplication);
        ExcelUtil<PoorApplication> util = new ExcelUtil<PoorApplication>(PoorApplication.class);
        util.exportExcel(response, list, "贫困申请数据");
    }

    /**
     * 获取贫困申请详细信息
     */
    @PreAuthorize("@ss.hasPermi('poor:application:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(poorApplicationService.selectPoorApplicationById(id));
    }

    /**
     * 新增贫困申请
     */
    @PreAuthorize("@ss.hasPermi('poor:application:add')")
    @Log(title = "贫困申请", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PoorApplication poorApplication)
    {
        return toAjax(poorApplicationService.insertPoorApplication(poorApplication));
    }

    /**
     * 修改贫困申请
     */
    @PreAuthorize("@ss.hasPermi('poor:application:edit')")
    @Log(title = "贫困申请", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PoorApplication poorApplication)
    {
        return toAjax(poorApplicationService.updatePoorApplication(poorApplication));
    }

    /**
     * 删除贫困申请
     */
    @PreAuthorize("@ss.hasPermi('poor:application:remove')")
    @Log(title = "贫困申请", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(poorApplicationService.deletePoorApplicationByIds(ids));
    }

    /**
     * 审核贫困申请
     */
    @PreAuthorize("@ss.hasPermi('poor:application:review')")
    @Log(title = "贫困申请审核", businessType = BusinessType.UPDATE)
    @PutMapping("/review")
    public AjaxResult review(@RequestBody PoorApplication poorApplication)
    {
        // 设置当前用户为审核人
        poorApplication.setReviewerId(getUserId());
        // 设置审核日期为当前日期
        poorApplication.setReviewDate(new Date());
        return toAjax(poorApplicationService.reviewPoorApplication(poorApplication));
    }
}
