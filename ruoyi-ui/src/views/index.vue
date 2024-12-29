<template>
  <div class="app-container">
    <!-- 统计卡片 -->
    <el-row :gutter="20" class="panel-group">
      <el-col :span="6">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>总申请数</span>
            </div>
          </template>
          <div class="card-panel-num">{{ statistics.applicationStats?.total || 0 }}</div>
        </el-card>
      </el-col>

      <el-col :span="6" v-for="(value, key) in statusMap" :key="key">
        <el-card class="box-card">
          <template #header>
            <div class="card-header">
              <span>{{ key }}</span>
            </div>
          </template>
          <div class="card-panel-num" :class="`text-${value.color}`">
            {{ value.count }}
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 图表区域 -->
    <el-row :gutter="20" style="margin-top: 20px;">
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>月度资助金额趋势</span>
            </div>
          </template>
          <div class="chart-container">
            <LineChart :chartData="monthlyChartData" />
          </div>
        </el-card>
      </el-col>
      <el-col :span="12">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>资助类型分布</span>
            </div>
          </template>
          <div class="chart-container">
            <PieChart :chartData="aidTypeChartData" />
          </div>
        </el-card>
      </el-col>
    </el-row>

    <el-row style="margin-top: 20px;">
      <el-col :span="24">
        <el-card>
          <template #header>
            <div class="card-header">
              <span>各学院资助情况</span>
            </div>
          </template>
          <div class="chart-container">
            <BarChart :chartData="collegeChartData" />
          </div>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import { getStatistics } from '@/api/poor/dashboard'
import LineChart from '@/components/LineChart.vue'
import PieChart from '@/components/PieChart.vue'
import BarChart from '@/components/BarChart.vue'

export default {
  name: "Dashboard",
  components: {
    LineChart,
    PieChart,
    BarChart
  },
  data() {
    return {
      // 统计数据
      statistics: {},
      // 图表数据
      monthlyChartData: {},
      aidTypeChartData: {},
      collegeChartData: {},
      loading: true,

      statusMap: {
        "待审核": { color: "info", count: 0 },
        "已通过": { color: "success", count: 0 },
        "已拒绝": { color: "danger", count: 0 }
      }
    }
  },
  created() {
    this.getList()
  },

  computed: {
    // 获取各状态数量
    statusCounts() {
      if (!this.statistics.applicationStats) return {};

      const { total, ...stats } = this.statistics.applicationStats;
      return stats;
    }
  },
  methods: {
    /** 获取统计信息 */
    getList() {
      this.loading = true
      getStatistics().then(response => {
        this.statistics = response.data
        this.formatChartData()
        this.loading = false
      })
    },
    /** 格式化图表数据 */
    formatChartData() {
      const stats = this.statistics.applicationStats || {};
      Object.keys(this.statusMap).forEach(key => {
        this.statusMap[key].count = stats[key] || 0;
      });
      // 月度趋势数据
      this.monthlyChartData = {
        labels: this.statistics.monthlyAmount?.map(item => item.month) || [],
        datasets: [{
          label: '资助金额',
          data: this.statistics.monthlyAmount?.map(item => item.amount) || [],
          borderColor: '#409EFF',
          backgroundColor: '#409EFF'
        }]
      };

      // 资助类型分布
      this.aidTypeChartData = {
        labels: this.statistics.aidTypeStats?.map(item => item.aid_type || '未分类') || [],
        datasets: [{
          label: '资助金额',
          data: this.statistics.aidTypeStats?.map(item => item.total_amount) || [],
          backgroundColor: ['#409EFF', '#67C23A', '#E6A23C', '#F56C6C']
        }]
      };

      // 学院统计数据
      this.collegeChartData = {
        labels: this.statistics.collegeStats?.map(item => item.college) || [],
        datasets: [{
          label: '资助金额',
          data: this.statistics.collegeStats?.map(item => item.total_amount) || [],
          backgroundColor: '#409EFF'
        }]
      };
    }
  }
}
</script>

<style scoped>
.panel-group {
  margin-top: 18px;
}

.card-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.card-panel-num {
  font-size: 24px;
  font-weight: bold;
  color: #409EFF;
  text-align: center;
}

.chart-container {
  height: 300px;
  width: 100%;
}

.box-card {
  height: 100%;
}
</style>