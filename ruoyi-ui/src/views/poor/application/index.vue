<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="学生姓名" prop="studentName" v-if="!isStudent">
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
      <el-table-column label="证明材料" align="center" prop="supportingDocuments">
        <template #default="scope">
          <div v-if="scope.row.supportingDocuments">
            <el-link
                v-for="(file, index) in scope.row.supportingDocuments.split(',')"
                :key="index"
                type="primary"
                :href="baseUrl + file"
                target="_blank"
                style="margin-right: 10px"
            >
              附件{{ index + 1 }}
            </el-link>
          </div>
          <span v-else>无</span>
        </template>
      </el-table-column>
      <el-table-column label="审核状态" align="center" prop="status">
        <template #default="scope">
          <el-tag :type="scope.row.status === '已通过' ? 'success' : scope.row.status === '已拒绝' ? 'danger' : 'info'">
            {{ scope.row.status }}
          </el-tag>
        </template>
      </el-table-column>
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
              icon="view"
              @click="handleReview(scope.row)"
              v-hasPermi="['poor:application:review']"
          >审核</el-button>
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
        <el-form-item label="学生" prop="studentId" v-if="!isStudent">
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
          <!-- 文件上传组件 -->
          <el-upload
              :file-list="fileList"
              :action="uploadUrl"
              :before-upload="handleBeforeUpload"
              :on-success="handleUploadSuccess"
              :on-remove="handleRemove"
              multiple
              :headers="headers"
              :limit="5"
          >
            <el-button type="primary">选择文件</el-button>
            <template #tip>
              <div class="el-upload__tip">只能上传 jpg/png/pdf 文件，且不超过 10MB</div>
            </template>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 审核对话框 -->
    <el-dialog :title="'申请审核'" v-model="reviewOpen" width="500px" append-to-body>
      <el-form ref="reviewForm" :model="reviewForm" :rules="reviewRules" label-width="100px">
        <el-form-item label="审核状态" prop="status">
          <el-select v-model="reviewForm.status" placeholder="请选择审核状态">
            <el-option
                v-for="item in statusOptions.filter(item => item.value !== '0')"
                :key="item.value"
                :label="item.label"
                :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="审核意见" prop="reviewComments">
          <el-input
              v-model="reviewForm.reviewComments"
              type="textarea"
              placeholder="请输入审核意见"
              maxlength="500"
              show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitReview">确 定</el-button>
        <el-button @click="cancelReview">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listApplication,
  getApplication,
  delApplication,
  addApplication,
  updateApplication,
  reviewApplication
} from "@/api/poor/application";
import {listStudent} from "@/api/poor/student.js";
import useUserStore from "@/store/modules/user.js";
import {getToken} from "@/utils/auth.js";

const userStore = useUserStore();

export default {
  name: "Application",
  data() {
    return {
      isStudent: false,
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
      uploadUrl: import.meta.env.VITE_APP_BASE_API + "/common/upload", // 上传文件服务器地址
      headers: {
        Authorization: 'Bearer ' + getToken()
      },
      fileList: [],
      // 修改表单规则
      rules: {
        studentId: [
          { required: true, message: "请选择学生", trigger: "change" }
        ],
        supportingDocuments: [
          { required: true, message: "请上传证明材料", trigger: "change" }
        ]
      },
      baseUrl: import.meta.env.VITE_APP_BASE_API,

      reviewOpen: false,
      reviewForm: {
        id: null,
        status: '',
        reviewComments: '',
        reviewerId: null,
        reviewDate: null
      },
      reviewRules: {
        status: [
          { required: true, message: "请选择审核状态", trigger: "change" }
        ],
        reviewComments: [
          { required: true, message: "请输入审核意见", trigger: "blur" }
        ]
      },
      statusOptions: [
        { value: "待审核", label: "待审核" },
        { value: "已通过", label: "通过" },
        { value: "已拒绝", label: "不通过" }
      ]
    };
  },
  created() {
    this.form.status = "待审核"; // 设置为待审核状态

    this.initUserRole();
    this.getList();
    // 只有非学生角色才需要获取学生列表
    if (!this.isStudent) {
      this.getStudentOptions();
    }
  },

  methods: {

    initUserRole() {
      // 判断用户是否为学生角色
      this.isStudent = userStore.roles.includes('student');
      if (this.isStudent) {
        // 如果是学生，直接设置studentId为当前用户ID
        this.form.studentId = userStore.id;
      }
    },
    getStatusLabel(status) {
      return this.statusOptions.find(option => option.value === status)?.label || '';
    },
    handleReview(row) {
      this.reviewForm = {
        id: row.id,
        status: '',
        reviewComments: '',
        reviewerId: userStore.id,
        reviewDate: new Date().toISOString().slice(0, 10)
      };
      this.reviewOpen = true;
    },

    // 取消审核
    cancelReview() {
      this.reviewOpen = false;
      this.reviewForm = {};
    },

    // 提交审核
    submitReview() {
      this.$refs["reviewForm"].validate(valid => {
        if (valid) {
          const reviewData = {
            id: this.reviewForm.id,
            status: this.reviewForm.status,
            reviewComments: this.reviewForm.reviewComments,
            reviewerId: userStore.id,
            reviewDate: new Date().toISOString().slice(0, 10)
          };

          reviewApplication(reviewData).then(response => {
            this.$modal.msgSuccess("审核成功");
            this.reviewOpen = false;
            this.getList();
          });
        }
      });
    },
    handleBeforeUpload(file) {
      const isJPG = file.type === 'image/jpeg';
      const isPNG = file.type === 'image/png';
      const isPDF = file.type === 'application/pdf';
      const isLt10M = file.size / 1024 / 1024 < 10;

      if (!isJPG && !isPNG && !isPDF) {
        this.$modal.msgError('上传文件只能是 jpg/png/pdf 格式!');
        return false;
      }
      if (!isLt10M) {
        this.$modal.msgError('上传文件大小不能超过 10MB!');
        return false;
      }
      return true;
    },

    // 文件上传成功的回调
    handleUploadSuccess(response, file, fileList) {
      if (response.code === 200) {
        this.fileList = fileList;
        this.form.supportingDocuments = fileList.map(item => item.response?.fileName || item.name).join(',');
        this.$modal.msgSuccess("上传成功");
      } else {
        this.$modal.msgError(response.msg);
      }
    },

    // 移除文件的回调
    handleRemove(file, fileList) {
      this.fileList = fileList;
      this.form.supportingDocuments = fileList.map(item => item.response?.fileName || item.name).join(',');
    },
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
      this.fileList = [];
      this.form = {
        id: null,
        studentId: this.isStudent ? userStore.id : null, // 如果是学生角色，设置为当前用户ID
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
        const responseData = response.data;  // 修正变量名
        this.form = {
          id: responseData.id,
          studentId: responseData.studentId,
          applicationDate: responseData.applicationDate,
          povertyReason: responseData.povertyReason,
          supportingDocuments: responseData.supportingDocuments
        };
        if (this.form.supportingDocuments) {
          this.fileList = this.form.supportingDocuments.split(',').map(file => ({
            name: file,
            url: import.meta.env.VITE_APP_BASE_API + file
          }));
        }
        this.open = true;
        this.title = "修改贫困申请";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (!this.form.id) {
            this.form.status = "待审核";
          }

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
