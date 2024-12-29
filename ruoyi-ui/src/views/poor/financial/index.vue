<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="studentName">
        <el-input
            v-model="queryParams.studentName"
            placeholder="请输入学姓名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="操作人姓名" prop="userName">
        <el-input
            v-model="queryParams.userName"
            placeholder="请输入操作人姓名"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资助金额" prop="aidAmount">
        <el-input
            v-model="queryParams.aidAmount"
            placeholder="请输入资助金额"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="资助类型" prop="aidType">
        <el-input
            v-model="queryParams.aidType"
            placeholder="请输入资助类型"
            clearable
            @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="发放日期" prop="grantDate">
        <el-date-picker clearable
                        v-model="queryParams.grantDate"
                        type="date"
                        value-format="YYYY-MM-DD"
                        placeholder="请选择发放日期">
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
            v-hasPermi="['poor:financial:add']"
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="success"
            plain
            icon="edit"
            size="default"
            :disabled="single"
            @click="handleUpdate"
            v-hasPermi="['poor:financial:edit']"
        >修改
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="danger"
            plain
            icon="delete"
            size="default"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['poor:financial:remove']"
        >删除
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
            type="warning"
            plain
            icon="download"
            size="default"
            @click="handleExport"
            v-hasPermi="['poor:financial:export']"
        >导出
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="financialList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="学生姓名" align="center" prop="studentName"/>
      <el-table-column label="操作人姓名" align="center" prop="userName"/>
      <el-table-column label="资助类型" align="center" prop="aidType"/>
      <el-table-column label="资助金额" align="center" prop="aidAmount"/>
      <el-table-column label="发放日期" align="center" prop="grantDate" width="180">
        <template #default="scope">
          <span>{{ parseTime(scope.row.grantDate, '{y}-{m}-{d}') }}</span>
        </template>
      </el-table-column>
      <el-table-column label="发放状态" align="center" prop="grantStatus"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template #default="scope">
          <el-button
              size="default"
              type="text"
              icon="edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['poor:financial:edit']"
          >修改
          </el-button>
          <el-button
              size="default"
              type="text"
              icon="delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['poor:financial:remove']"
          >删除
          </el-button>
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

    <!-- 添加或修改贫困资助对话框 -->
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
        <el-form-item label="资助金额" prop="aidAmount">
          <el-input v-model="form.aidAmount" placeholder="请输入资助金额"/>
        </el-form-item>
        <el-form-item label="资助类型" prop="aidType">
          <el-input v-model="form.aidType" placeholder="请输入资助类型"/>
        </el-form-item>
        <el-form-item label="发放日期" prop="grantDate">
          <el-date-picker clearable
                          v-model="form.grantDate"
                          type="date"
                          value-format="YYYY-MM-DD"
                          placeholder="请选择发放日期">
          </el-date-picker>
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
import {listFinancial, getFinancial, delFinancial, addFinancial, updateFinancial} from "@/api/poor/financial";
import {listStudent} from "@/api/poor/student.js";
import useUserStore from "@/store/modules/user.js";

const userStore = useUserStore();

export default {
  name: "Financial",
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
      // 贫困资助表格数据
      financialList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        studentName: null,
        studentId: null,
        userId: null,
        aidType: null,
        aidAmount: null,
        grantDate: null,
        grantStatus: null,
      },
      // 表单参数
      form: {},
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
    /** 查询贫困资助列表 */
    getList() {
      this.loading = true;
      listFinancial(this.queryParams).then(response => {
        this.financialList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    getStudentOptions() {
      listStudent().then(response => {
        this.studentOptions = response.rows;
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
        userId: userStore.id,
        aidType: null,
        aidAmount: null,
        grantDate: null,
        grantStatus: null,
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
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加贫困资助";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids
      getFinancial(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改贫困资助";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.userId = userStore.id;
          if (this.form.id != null) {
            updateFinancial(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addFinancial(this.form).then(response => {
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
      this.$modal.confirm('是否确认删除贫困资助编号为"' + ids + '"的数据项？').then(function () {
        return delFinancial(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('poor/financial/export', {
        ...this.queryParams
      }, `financial_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>
