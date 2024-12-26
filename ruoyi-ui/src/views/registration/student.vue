<template>
  <div class="app-container">
    <el-card class="box-card">
      <div slot="header" class="clearfix">
        <span>{{ title }}</span>
      </div>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="学号" prop="number">
          <el-input v-model="form.number" placeholder="请输入学号"/>
        </el-form-item>
        <el-form-item label="学院" prop="college">
          <el-input v-model="form.college" placeholder="请输入学院"/>
        </el-form-item>
        <el-form-item label="专业" prop="major">
          <el-input v-model="form.major" placeholder="请输入专业"/>
        </el-form-item>
        <el-form-item label="年级" prop="grade">
          <el-input v-model="form.grade" placeholder="请输入年级"/>
        </el-form-item>
        <el-form-item label="家庭住址" prop="familyAddress">
          <el-input v-model="form.familyAddress" placeholder="请输入家庭住址"/>
        </el-form-item>
        <el-form-item label="家庭年收入" prop="familyIncome">
          <el-input v-model="form.familyIncome" placeholder="请输入家庭年收入"/>
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
import {getStudent, updateStudent} from "@/api/poor/student";
import usePermissionStore from "@/store/modules/permission.js";
import {isHttp} from "@/utils/validate.js";
import router from "@/router/index.js";
import {getUserProfile} from "@/api/system/user";

export default {
  name: "registration-student",
  data() {
    return {
      title: "学生激活表单",
      loading: false,
      form: {
        userId: null,
        number: null,
        college: null,
        major: null,
        grade: null,
        familyAddress: null,
        familyIncome: null,
        // 默认设置为已激活状态，不显示在表单中
        isActivated: null
      },
      rules: {
        // 在这里定义您的表单验证规则
      }
    };
  },
  async created() {
    const userInfo = await getUserProfile();
    const userId = userInfo.data.userId;
    this.form.userId = userId;
    this.loading = true;
    try {
      const response = await getStudent(userId);
      this.form = response.data;
      this.form.isActivated = 1;
      this.title = "激活学生信息";
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
              await updateStudent(this.form);
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
  }
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