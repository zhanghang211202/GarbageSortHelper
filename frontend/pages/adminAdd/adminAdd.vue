<template>
	<form @submit="addType">
		<view class="cu-form-group margin-top">
			<view class="title">垃圾名称</view>
			<input placeholder="请输入垃圾名称" v-model="name" name="name"></input>
		</view>
		<view class="cu-form-group margin-top">
			<view class="title">垃圾类型</view>
			<picker @change="bindPickerChange" :value="typeIndex" :range="typePicker">
				<view class="picker">
					{{typePicker[typeIndex]}}
				</view>
			</picker>
		</view>
		<button class="cu-btn bg-grey lg button-hover add" form-type="submit">添加数据</button>
	</form>
</template>

<script>
	export default {
		data() {
			return {
				typePicker: [
					'干垃圾', '湿垃圾', '可回收物', '有害垃圾'
				],
				typeIndex: 0,
				name: ''
			}
		},
		methods: {
			addType() {
				if (!this.name) {
					uni.showModal({
						title: '提示',
						content: '请输入垃圾名称',
						showCancel: false
					})
				}
				console.log(this.name)
				console.log(this.typePicker[this.typeIndex])
				uni.request({
					method:'POST',
					url: this.serverUrl + "/admin/addQuestion",
					data: {
						garbageName: this.name,
						garbageType: parseInt(this.typeIndex)+1
					},
					success(res) {
						uni.showModal({
							title: '提示',
							content: '新增成功',
							showCancel: false
						})
						console.log(res)
					},
				})
			},
			bindPickerChange(e){
				this.typeIndex = e.detail.value;
			}

		}
	}
</script>

<style>
	@import url("../../common/icon.css");
	@import url("../../common/commons.css");
	@import url("adminAdd.css");
</style>
