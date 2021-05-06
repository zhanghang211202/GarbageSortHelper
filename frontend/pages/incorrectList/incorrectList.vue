<template>
	
	<view>
		<view class="cu-timeline" v-for="(item,index) in incorrectList">
			<view class="cu-time">{{item.date}}</view>
			<view class="cu-item">
				<view class="content shadow-blur"  v-for="(res,idx) in item.list">
					<text>{{res.garbageName}}</text>属于<text>{{garbageType[res.garbageType-1]}}</text>,我错误地选择成了 <text>{{garbageType[res.selectedType-1]}}</text>。
				</view>
			</view>
		</view>
	</view>
	<!-- <view>
		<view class="items">
			<view class="item bold">垃圾名称</view>
			<view class="item bold">正确种类</view>
			<view class="item bold">你的回答</view>
		</view>
		<view class="items" v-for="(item,index) in incorrectList">
			<view class="item grey">{{item.garbageName}}</view>
			<view class="item green">{{garbageType[item.garbageType-1]}}</view>
			<view class="item red">{{garbageType[item.selectedType-1]}}</view>
		</view>
	</view> -->
</template>

<script>
	export default {
		data() {
			return {
				incorrectList:[],
				garbageType:["干垃圾","湿垃圾","可回收物","有害垃圾"]
			}
		},
		onLoad(){
			this.getIncorrectList()
		},
		methods: {
			getIncorrectList(){
				let _this = this
				let userId = getApp().globalData.userId
				console.log(userId)
				uni.showLoading({
					title: '正在努力加载中...'
				});
				uni.request({
					url: this.serverUrl + "/challenge/incorrectList/"+userId,
					success(res){
						console.log(res)
						_this.incorrectList = res.data.data
						console.log(_this.incorrectList)
					},
					complete() {
						
						uni.hideLoading()
					}
				})
			}
		}
	}
</script>

<style>
	@import url("../../static/colorui/main.css");
	@import url("../../static/colorui/icon.css");
</style>
