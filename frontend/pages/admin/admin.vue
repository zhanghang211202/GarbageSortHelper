<template>
	<view>
		<view class="login-img">
			<view class="owl-login" :class="{ password:hideEyes }">
				<view class="hand"></view>
				<view class="hand hand-r"></view>
				<view class="arms">
					<view class="arm"></view>
					<view class="arm arm-r"></view>
				</view>
			</view>
		</view>
		<view class="login-form">
			<form>
				<view class="form-input">
					<input type="text" value="" name="admin" v-model="admin" placeholder="用户名" />
				</view>
				<view class="form-input">
					<input type="password" value="" name="password" v-model='password' placeholder="密码" @focus="passwordF_B" @blur="passwordF_B" />
				</view>
				<button type="primary" form-type="submit" @click="login">登录</button>
			</form>
		</view>
	</view>

</template>

<script>
	export default {
		data() {
			return {
				hideEyes: false,
				admin: '',
				password: ''
			}
		},
		methods: {
			passwordF_B() {
				this.hideEyes = !this.hideEyes
			},
			login(){
				uni.request({
					method: 'POST',
					data:{
						adminName: this.admin,
						adminPassword: this.password
					},
					url: this.serverUrl+"/admin/login",
					success(res) {
						console.log(res)
						if(res.data.data == 'success'){
							uni.navigateTo({
								url: '/pages/adminList/adminList',
							})
						}else{
							uni.showModal({
								title: '提示',
								content: '用户名或密码错误',
								showCancel: false
							})
						}
					}
				})
			}
		}
	}
</script>

<style>
	@import url("admin.css");
</style>
