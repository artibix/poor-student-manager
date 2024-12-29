<template>
  <div ref="chartRef" style="width: 100%; height: 100%"></div>
</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import * as echarts from 'echarts'

const props = defineProps({
  chartData: {
    type: Object,
    required: true
  }
})

const chartRef = ref(null)
let chart = null

onMounted(() => {
  chart = echarts.init(chartRef.value)
  setOptions()
})

watch(() => props.chartData, () => {
  setOptions()
}, { deep: true })

const setOptions = () => {
  // 确保数据存在
  if (!props.chartData || !props.chartData.labels || !props.chartData.datasets) {
    return;
  }

  // 处理日期标签，将 "2024-12" 转换为 "12月"，只在第一个标签显示年份
  const formatLabels = props.chartData.labels.map((label, index) => {
    const [year, month] = label.split('-');
    if (index === 0) {
      return year + '年' + month + '月';
    }
    return month + '月';
  });

  const options = {
    tooltip: {
      trigger: 'axis',
      formatter: function(params) {
        const data = params[0];
        return data.name + ': ' + data.value + ' 元';
      }
    },
    grid: {
      left: '3%',
      right: '4%',
      bottom: '8%',  // 增加底部间距
      top: '8%',     // 增加顶部间距
      containLabel: true
    },
    xAxis: {
      type: 'category',
      boundaryGap: false,
      data: formatLabels,
      axisLabel: {
        interval: 0,    // 强制显示所有标签
        rotate: 0       // 不旋转标签
      }
    },
    yAxis: {
      type: 'value',
      name: '金额（元）',
      axisLabel: {
        formatter: '{value}'
      }
    },
    series: [{
      name: '资助金额',
      type: 'line',
      data: props.chartData.datasets[0]?.data || [],
      smooth: true,
      areaStyle: {
        opacity: 0.3
      },
      itemStyle: {
        color: '#409EFF'
      }
    }]
  }
  chart?.setOption(options)
}

window.addEventListener('resize', () => {
  chart?.resize()
})
</script>