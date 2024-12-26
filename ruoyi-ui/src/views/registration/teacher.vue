<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>{{ title }}</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="教师编号" prop="number">
          <el-input v-model="form.number" placeholder="请输入教师编号"/>
        </el-form-item>
        <el-form-item label="家庭住址" prop="position">
          <el-input v-model="form.position" placeholder="请输入家庭住址"/>
        </el-form-item>
        <el-form-item label="入职日期" prop="hireDate">
          <el-date-picker
            v-model="form.hireDate"
            type="date"
            value-format="YYYY-MM-DD"
            placeholder="请选择入职日期"
            :clearable="true"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="职称" prop="level">
          <el-input v-model="form.level" placeholder="请输入职称" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="submitForm">提交</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
        <!-- 隐藏是否激活的表单项 -->
        <el-form-item v-show="false">
          <el-input v-model="form.isActivated"/>
        </el-form-item>
      </el-form>
    </el-card>
  </div>
</template>

<script>
import { getTeacher, updateTeacher } from "@/api/poor/teacher";
import usePermissionStore from "@/store/modules/permission.js";
import {isHttp} from "@/utils/validate.js";
import router from "@/router/index.js";
import {getUserProfile} from "@/api/system/user";

export default {
  name: "registration-teacher",
  data() {
    return {
      title: "老师激活表单",
      loading: false,
      form: {
        userId: null,
        number: null,
        hireDate: null,
        position: null,
        level: null,
        // 默认设置为已激活状态，不显示在表单中
        isActivated: null
      },
      rules: {
        hireDate: [
          { required: true, message: '请选择入职日期', trigger: 'change' },
          {
            validator: (rule, value, callback) => {
              if (value && !/^\d{4}-\d{2}-\d{2}$/.test(value)) {
                callback(new Error('无效的日期格式'))
              } else {
                callback()
              }
            },
            trigger: 'change'
          }
        ]
      }
    };
  },
  async created() {
    const userInfo = await getUserProfile();
    const userId = userInfo.data.userId;
    this.form.userId = userId;
    this.loading = true;
    try {
      const response = await getTeacher(userId);
      this.form = response.data;
      this.form.isActivated = 1;
      this.title = "激活老师信息";
    } finally {
      this.loading = false;
    }
  },
  methods: {
    reset() {
      this.$refs["form"].resetFields();
    },
    submitForm() {
      this.$refs["form"].validate(async (valid) => {
        if (valid) {
          try {
            if (this.form.userId != null) {
              await updateTeacher(this.form);
              this.$modal.msgSuccess("激活成功");
            }

            const permissionStore = usePermissionStore();
            const accessRoutes = await permissionStore.generateRoutes();

            accessRoutes.forEach(route => {
              if (!isHttp(route.path)) {
                router.addRoute(route);
              }
            });

            usePermissionStore().generateRoutes().then(accessRoutes => {
              // 根据roles权限生成可访问的路由表
              accessRoutes.forEach(route => {
                if (!isHttp(route.path)) {
                  router.addRoute(route) // 动态添加可访问路由表
                }
              })
            })
            this.$router.push({path: '/index'});
          } catch (error) {
            console.error("提交表单失败", error);
            this.$modal.msgError("操作失败");
          }
        }
      });
    },
  },
};
</script>

<style scoped lang="scss">
.app-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh; // 确保容器至少有视口的高度
  padding: 20px;
  box-sizing: border-box; // 确保padding不会增加元素的总大小
}

.box-card {
  width: 100%;
  max-width: 600px;
}

// 响应式设计
@media (max-width: 768px) {
  .box-card {
    max-width: 90%;
  }
}
</style>