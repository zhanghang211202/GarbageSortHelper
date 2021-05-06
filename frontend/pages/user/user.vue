<template>
	<view v-if="!isLogin">
		<view class="empower">
			<view class='header'>
				<view class="logo-img">
					<open-data type="userAvatarUrl" style="width: 100%;height: 100%;display: inline-block;"></open-data>
				</view>
			</view>
			<view class='content_h'>
				<view>申请获取以下权限</view>
				<text>获得你的公开信息(昵称，头像、地区等)</text>
			</view>
		</view>
		<button class='bottom' type='primary' @click="getUserInfo">
			授权登录
		</button>
	</view>

	<view v-else>
		<view>
			<view class="logo">
				<image class="logo-img" :src="userInfo.avatarUrl"></image>
				<view class="logo-title">
					<text class="user-name">Hi，{{userInfo.nickName}}</text>
				</view>
			</view>

			<view class="cu-list menu sm-border" @click="moveToIncorrectList">
				<view class="cu-item arrow">
					<view class="content">
						<text class="cuIcon-roundclose  text-grey"></text>
						<text class="text-grey">我的错题</text>
					</view>
				</view>
			</view>


			<view class="cu-list menu sm-border" @click="moveToAdmin">
				<view class="cu-item arrow">
					<view class="content">
						<text class="cuIcon-friendfamous  text-grey"></text>
						<text class="text-grey">后台管理</text>
					</view>
				</view>
			</view>
		</view>
	</view>
</template>


<script>
	export default {
		data() {
			return {
				isLogin: getApp().globalData.isLogin,
				userInfo: getApp().globalData.userInfo
			}
		},
		methods: {
			getUserInfo() {
				var _this = this
				console.log("进入getUserInfo")
				
				uni.getUserProfile({
					desc: '测试',
					success(res) {
						console.log(res)
						_this.isLogin = true
						_this.userInfo = res.userInfo
						getApp().globalData.isLogin = true
						getApp().globalData.userInfo = res.userInfo
						uni.login({
							desc: '测试',
							success(res) {
								let code = res.code
								uni.request({
									url: _this.serverUrl + '/getOpenId/'+code, //请求路径 
									success(res) {
										console.info(res.data)
										getApp().globalData.userId = res.data
									}
								});
							}
						})
					}
				})
			},
			moveToIncorrectList() {
				uni.navigateTo({
					url: '/pages/incorrectList/incorrectList'
				})
			},
			moveToAdmin() {
				uni.navigateTo({
					url: '/pages/admin/admin'
				})
			}
		},
	}
</script>

<style>
	@import url("user.css");
	@import url("../../common/icon.css");
	@import url("../../common/commons.css");
</style>
