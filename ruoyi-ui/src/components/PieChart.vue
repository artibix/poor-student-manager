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
  // 数据验证
  if (!props.chartData || !props.chartData.labels || !props.chartData.datasets) {
    return;
  }

  // 处理数据
  const data = props.chartData.labels.map((label, index) => ({
    name: label || '其他',  // 如果标签为空则显示"其他"
    value: props.chartData.datasets[0]?.data[index] || 0  // 如果数据为空则显示0
  }));

  const options = {
    tooltip: {
      trigger: 'item',
      formatter: '{b}: {c} 元 ({d}%)'
    },
    legend: {
      orient: 'vertical',
      right: '5%',
      top: 'middle',
      formatter: function(name) {
        const item = data.find(item => item.name === name);
        return `${name}: ${item ? item.value : 0}元`;
      }
    },
    series: [{
      name: '资助类型',
      type: 'pie',
      radius: ['40%', '70%'],
      center: ['40%', '50%'],
      avoidLabelOverlap: true,
      itemStyle: {
        borderColor: '#fff',
        borderWidth: 2
      },
      label: {
        show: false,
        position: 'center'
      },
      emphasis: {
        label: {
          show: true,
          fontSize: '18',
          fontWeight: 'bold'
        }
      },
      labelLine: {
        show: false
      },
      data: data || []
    }]
  }

  chart?.setOption(options)
}

window.addEventListener('resize', () => {
  chart?.resize()
})
</script>