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
import com.ruoyi.poor.domain.PoorTeacher;
import com.ruoyi.poor.service.IPoorTeacherService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 教师信息Controller
 * 
 * @author art
 * @date 2024-10-06
 */
@RestController
@RequestMapping("/poor/teacher")
public class PoorTeacherController extends BaseController
{
    @Autowired
    private IPoorTeacherService poorTeacherService;

    /**
     * 查询教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:list')")
    @GetMapping("/list")
    public TableDataInfo list(PoorTeacher poorTeacher)
    {
        startPage();
        List<PoorTeacher> list = poorTeacherService.selectPoorTeacherList(poorTeacher);
        return getDataTable(list);
    }

    /**
     * 导出教师信息列表
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:export')")
    @Log(title = "教师信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PoorTeacher poorTeacher)
    {
        List<PoorTeacher> list = poorTeacherService.selectPoorTeacherList(poorTeacher);
        ExcelUtil<PoorTeacher> util = new ExcelUtil<PoorTeacher>(PoorTeacher.class);
        util.exportExcel(response, list, "教师信息数据");
    }

    /**
     * 获取教师信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(poorTeacherService.selectPoorTeacherByUserId(userId));
    }

    /**
     * 新增教师信息
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:add')")
    @Log(title = "教师信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PoorTeacher poorTeacher)
    {
        return toAjax(poorTeacherService.insertPoorTeacher(poorTeacher));
    }

    /**
     * 修改教师信息
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:edit')")
    @Log(title = "教师信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PoorTeacher poorTeacher)
    {
        return toAjax(poorTeacherService.updatePoorTeacher(poorTeacher));
    }

    /**
     * 删除教师信息
     */
    @PreAuthorize("@ss.hasPermi('poor:teacher:remove')")
    @Log(title = "教师信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(poorTeacherService.deletePoorTeacherByUserIds(userIds));
    }
}
