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
import com.ruoyi.poor.domain.PoorStudent;
import com.ruoyi.poor.service.IPoorStudentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 学生信息Controller
 * 
 * @author art
 * @date 2024-10-06
 */
@RestController
@RequestMapping("/poor/student")
public class PoorStudentController extends BaseController
{
    @Autowired
    private IPoorStudentService poorStudentService;

    /**
     * 查询学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('poor:student:list')")
    @GetMapping("/list")
    public TableDataInfo list(PoorStudent poorStudent)
    {
        startPage();
        List<PoorStudent> list = poorStudentService.selectPoorStudentList(poorStudent);
        return getDataTable(list);
    }

    /**
     * 导出学生信息列表
     */
    @PreAuthorize("@ss.hasPermi('poor:student:export')")
    @Log(title = "学生信息", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, PoorStudent poorStudent)
    {
        List<PoorStudent> list = poorStudentService.selectPoorStudentList(poorStudent);
        ExcelUtil<PoorStudent> util = new ExcelUtil<PoorStudent>(PoorStudent.class);
        util.exportExcel(response, list, "学生信息数据");
    }

    /**
     * 获取学生信息详细信息
     */
    @PreAuthorize("@ss.hasPermi('poor:student:query')")
    @GetMapping(value = "/{userId}")
    public AjaxResult getInfo(@PathVariable("userId") Long userId)
    {
        return success(poorStudentService.selectPoorStudentByUserId(userId));
    }

    /**
     * 新增学生信息
     */
    @PreAuthorize("@ss.hasPermi('poor:student:add')")
    @Log(title = "学生信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody PoorStudent poorStudent)
    {
        return toAjax(poorStudentService.insertPoorStudent(poorStudent));
    }

    /**
     * 修改学生信息
     */
    @PreAuthorize("@ss.hasPermi('poor:student:edit')")
    @Log(title = "学生信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody PoorStudent poorStudent)
    {
        return toAjax(poorStudentService.updatePoorStudent(poorStudent));
    }

    /**
     * 删除学生信息
     */
    @PreAuthorize("@ss.hasPermi('poor:student:remove')")
    @Log(title = "学生信息", businessType = BusinessType.DELETE)
	@DeleteMapping("/{userIds}")
    public AjaxResult remove(@PathVariable Long[] userIds)
    {
        return toAjax(poorStudentService.deletePoorStudentByUserIds(userIds));
    }
}
