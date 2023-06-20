<template>
	<view>
		<!--顶部基本信息-->
		<view class="zan-panel" v-if="size > 0">
			<view class="zan-cell s-height">
				<view class="zan-cell__bd">为你推荐附近的{{size}}个垃圾回收</view>
			</view>
		</view>

		<!-- <cells>
			<block v-for="(item,index) in list" v-if="controlShow">
				<cell link url="item.id" cotent="item.briefAddr" value="item.name" footer="相距item.distance米"
					@click="navigate_top()">
				</cell>
			</block>
		</cells> -->
		<view class="cu-list menu card-menu margin-top">
			<view class="cu-item arrow" v-for="(item,index) in list" v-if="controlShow">
				<view class="content">
					<text class="text-grey">{{item.name}}</text>
					<view class="text-gray text-sm flex"> <view class="text-cut">{{item.briefAddr}}</view></view>
				</view>
				<view class="action" @click="navigate_top(item)">
					<view class="text-grey text-xs">相距{{item.distance}}米</view>
				</view>
			</view>
		</view>
		
		<!--没有登录显示-->
		
	</view>
</template>


<script>
	var util = require('../../util/map.js');
	export default {
		data() {
			return {
				longitude: 0,
				latitude: 0,
				size: 10,
				list: [],
				controlShow: false
			}
		},
		onShow() {
			let app = getApp()
			let isLogin = getApp().globalData.isLogin
			if(!isLogin){
				uni.switchTab({
					url:'/pages/user/user'
				})
			}else{
				uni.showLoading({
				  title: "获取数据中!"
				});
				this.getData();
			}
		},
		methods: {
			getData() {
				var _this = this
				util.getLocation()
					.then(res => {
						console.log(res)
						_this.longitude = res.longitude
						_this.latitude = res.latitude
						var location = {longitude:_this.longitude,latitude:_this.latitude}
						util.mapSearch('垃圾', location).then(res => {
							console.log('mapSearch')
							console.log(res)
							var data = res.data;
							this.setList(data);
						})
					})
					.catch((e) => {
			
					})
			},
			//组装数据信息
			setList: function(data) {
				console.log('进入setList')
				var that = this;
				var result = [];
				//循环遍历数据， 其实不做这一步也行
				data.forEach(function(item, index) {
					//替换一些不必要的大信息
					var reg = new RegExp(item.ad_info.province + item.ad_info.city + item.ad_info.district);
					var briefAddr = item.address.replace(reg, "");
					//组装数据
					result.push({
						distance: item["_distance"],
						briefAddr: briefAddr,
						address: item.address,
						category: item.category,
						id: item.id,
						latitude: item.location.lat,
						longitude: item.location.lng,
						name: item.title
					});
				});
				uni.hideLoading()
				//设置data
				
				that.list = result
				console.log("list")
				console.log(that.list)
				that.size = result.length
				that.controlShow = true
			},
			doRefresh() {
				this.onShow()
			},
			//点击列表显示本地导航信息
			navigate_top: function(item) {
				console.log(item)
				var that = this;
				//跳转传输的值
				var param = {
					//基本的信息
					latitude: item.latitude,
					longitude: item.longitude,
					list: that.list,
					briefAddr: item.briefAddr,
					name: item.name
				}
			
				uni.showActionSheet({
					itemList: ['本地小程序导航'],
					success: function(res) {
						//打开本地应用进行导航
						uni.openLocation({
							latitude: param.latitude,
							longitude: param.longitude,
							name: param.name,
							address: param.briefAddr,
							scale: 28
						});
					},
					fail: function(res) {
						uni.showToast({
							title: '你可以选择一个看看效果',
							icon: 'none',
							duration: 2000
						})
					}
				});
			},
			findMarkerById: function (id) {
			  var that = this,
			    result = {};
			  var list = that.data.list;
			  for (var i = 0; i < list.length; i++) {
			    if (id === list[i].id) {
			      result = list[i];
			      break;
			    }
			  }
			  return result;
			}
		}
	}
</script>

<style>
	@import url("gps.css");
	@import url("../../static/colorui/main.css");
	@import url("../../static/colorui/icon.css");
</style>
