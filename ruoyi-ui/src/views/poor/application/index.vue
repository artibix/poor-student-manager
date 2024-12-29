<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
          v-model="queryParams.studentName"
          placeholder="请输入学生姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="申请日期" prop="applicationDate">
        <el-date-picker clearable
          v-model="queryParams.applicationDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择申请日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item label="审核人姓名" prop="reviewerName">
        <el-input
          v-model="queryParams.reviewerId"
          placeholder="请输入审核人姓名"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="审核日期" prop="reviewDate">
        <el-date-picker clearable
          v-model="queryParams.reviewDate"
          type="date"
          value-format="YYYY-MM-DD"
          placeholder="请选择审核日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="search" size="default" @click="handleQuery">搜索</el-button>
        <el-button icon="refresh" size="default" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="plus"
          size="default"
          @click="handleAdd"
          v-hasPermi="['poor:application:add']"
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="edit"
          size="default"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['poor:application:edit']"
        >修改</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="delete"
          size="default"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['poor:application:remove']"
        >删除</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="download"
          size="default"
          @click="handleExport"
          v-hasPermi="['poor:application:export']"
        >导出</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="applicationList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="学生姓名" align="center" prop="studentName" />
      <el-table-column label="申请日期" align="center" prop="applicationDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.applicationDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="贫困原因" align="center" prop="povertyReason" />
      <el-table-column label="证明材料" align="center" prop="supportingDocuments" />
      <el-table-column label="审核状态" align="center" prop="status" />
      <el-table-column label="审核人" align="center" prop="reviewerName" />
      <el-table-column label="审核日期" align="center" prop="reviewDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.reviewDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核意见" align="center" prop="reviewComments" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
            size="default"
            type="text"
            icon="edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['poor:application:edit']"
          >修改</el-button>
          <el-button
            size="default"
            type="text"
            icon="delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['poor:application:remove']"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改贫困申请对话框 -->
    <el-dialog :title="title" v-model="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="学生" prop="studentId">
          <el-select v-model="form.studentId" placeholder="请选择学生" clearable>
            <el-option
                v-for="student in studentOptions"
                :key="student.userId"
                :label="student.userName"
                :value="student.userId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="申请日期" prop="applicationDate">
          <el-date-picker clearable
            v-model="form.applicationDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择申请日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="贫困原因" prop="povertyReason">
          <el-input v-model="form.povertyReason" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="证明材料" prop="supportingDocuments">
          <el-input v-model="form.supportingDocuments" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="审核日期" prop="reviewDate">
          <el-date-picker clearable
            v-model="form.reviewDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择审核日期">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="审核意见" prop="reviewComments">
          <el-input v-model="form.reviewComments" type="textarea" placeholder="请输入内容" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listApplication, getApplication, delApplication, addApplication, updateApplication } from "@/api/poor/application";
import {listStudent} from "@/api/poor/student.js";
import useUserStore from "@/store/modules/user.js";

const userStore = useUserStore();

export default {
  name: "Application",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 贫困申请表格数据
      applicationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        studentName: null,
        applicationDate: null,
        povertyReason: null,
        supportingDocuments: null,
        status: null,
        reviewerName: null,
        reviewDate: null,
        reviewComments: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      studentOptions: [],
      rules: {
        studentId: [
          {required: true, message: "请选择学生", trigger: "change"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getStudentOptions();
  },
  methods: {
    getStudentOptions() {
      listStudent().then(response => {
        this.studentOptions = response.rows;
      });
    },
    /** 查询贫困申请列表 */
    getList() {
      this.loading = true;
      listApplication(this.queryParams).then(response => {
        this.applicationList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        studentId: null,
        applicationDate: null,
        povertyReason: null,
        supportingDocuments: null,
        status: null,
        reviewerId: null,
        reviewDate: null,
        reviewComments: null,
        createTime: null,
        updateTime: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加贫困申请";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getApplication(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改贫困申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.reviewerId = userStore.id;

          if (this.form.id != null) {
            updateApplication(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addApplication(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal.confirm('是否确认删除贫困申请编号为"' + ids + '"的数据项？').then(function() {
        return delApplication(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('poor/application/export', {
        ...this.queryParams
      }, `application_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
