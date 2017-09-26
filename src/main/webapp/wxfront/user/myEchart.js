//echarts饼图
	// 基于准备好的dom，初始化echarts实例
	var myChart = echarts.init(document.getElementById('dayEcharts'));
	// 指定图表的配置项和数据
	option = {
		title: {
			text: '某站点用户访问来源',
			subtext: '纯属虚构',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['日访', '首访', '复访', '成交']
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: '55%',
			center: ['50%', '40%'],
			data: [{
					value: 335,
					name: '日访'
				},
				{
					value: 310,
					name: '首访'
				},
				{
					value: 234,
					name: '复访'
				},
				{
					value: 135,
					name: '成交'
				}
			],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};

	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option);	
	
	//周访图饼
	var myChart = echarts.init(document.getElementById('weekEcharts'));
	// 指定图表的配置项和数据
	option1 = {
		title: {
			text: '某站点用户访问来源',
			subtext: '纯属虚构',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['日访','周访', '首访', '复访', '成交']
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: '55%',
			center: ['50%', '40%'],
			data: [{
					value: 335,
					name: '日访'
				},
				{
					value: 365,
					name: '周访'
				},
				{
					value: 310,
					name: '首访'
				},
				{
					value: 234,
					name: '复访'
				},
				{
					value: 135,
					name: '成交'
				}
			],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option1);	
	
	//月访图饼
	var myChart = echarts.init(document.getElementById('monthEcharts'));
	// 指定图表的配置项和数据
	option1 = {
		title: {
			text: '某站点用户访问来源',
			subtext: '纯属虚构',
			x: 'center'
		},
		tooltip: {
			trigger: 'item',
			formatter: "{a} <br/>{b} : {c} ({d}%)"
		},
		legend: {
			orient: 'vertical',
			left: 'left',
			data: ['日访','月访', '首访', '复访', '成交']
		},
		series: [{
			name: '访问来源',
			type: 'pie',
			radius: '55%',
			center: ['50%', '40%'],
			data: [{
					value: 335,
					name: '日访'
				},
				{
					value: 365,
					name: '月访'
				},
				{
					value: 310,
					name: '首访'
				},
				{
					value: 234,
					name: '复访'
				},
				{
					value: 135,
					name: '成交'
				}
			],
			itemStyle: {
				emphasis: {
					shadowBlur: 10,
					shadowOffsetX: 0,
					shadowColor: 'rgba(0, 0, 0, 0.5)'
				}
			}
		}]
	};
	// 使用刚指定的配置项和数据显示图表。
	myChart.setOption(option1);	