package com.ruoyi.web.controller.poor;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
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
import com.ruoyi.poor.domain.PoorFinancial;
import com.ruoyi.poor.service.IPoorFinancialService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 贫困资助Controller
 * 
 * @author art
 * @date 2024-09-29
 */
@RestController
@RequestMapping("/poor/financial")
public class PoorFinancialController extends BaseController
{
    @Autowired
    private IPoorFinancialService poorFinancialService;

    /**
     * 查询贫困资助列表
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:list')")
    @GetMapping("/list")
    public TableDataInfo list(PoorFinancial poorFinancial)
    {
        startPage();
        List<PoorFinancial> list = poorFinancialService.selectPoorFinancialList(poorFinancial);
        return getDataTable(list);
    }

    /**
     * 导出贫困资助列表
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:export')")
    @Log(title = "贫困资助", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PoorFinancial poorFinancial)
    {
        List<PoorFinancial> list = poorFinancialService.selectPoorFinancialList(poorFinancial);
        ExcelUtil<PoorFinancial> util = new ExcelUtil<PoorFinancial>(PoorFinancial.class);
        util.exportExcel(response, list, "贫困资助数据");
    }

    /**
     * 获取贫困资助详细信息
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return success(poorFinancialService.selectPoorFinancialById(id));
    }

    /**
     * 新增贫困资助
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:add')")
    @Log(title = "贫困资助", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PoorFinancial poorFinancial)
    {
        return toAjax(poorFinancialService.insertPoorFinancial(poorFinancial));
    }

    /**
     * 修改贫困资助
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:edit')")
    @Log(title = "贫困资助", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PoorFinancial poorFinancial)
    {
        return toAjax(poorFinancialService.updatePoorFinancial(poorFinancial));
    }

    /**
     * 删除贫困资助
     */
    @PreAuthorize("@ss.hasPermi('poor:financial:remove')")
    @Log(title = "贫困资助", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(poorFinancialService.deletePoorFinancialByIds(ids));
    }
}
