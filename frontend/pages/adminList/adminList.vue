<template>
	<view class="cu-list grid col-3">
		<block v-for="item in iconList" :key="item.id">
			<view class="cu-item" @tap="ItemClick" :data-url="item.url">
				<view :class="`cuIcon-`+item.icon+` `+`text-`+item.color"></view>
				<text>{{item.name}}</text>
			</view>
		</block>
		<view class="cu-item" @click="uploadExcel">
			<view class="cuIcon-upload text-green"></view>
			<text>excel导入</text>
		</view>
	</view>
</template>

<script>
	export default {
		data() {
			return {
				iconList: [{
					icon: 'add',
					color: 'yellow',
					name: '新增数据',
					url: "/pages/adminAdd/adminAdd"
				}, {
					icon: 'edit',
					color: 'orange',
					badge: 0,
					name: '数据总览与更新',
					url: "/pages/adminShow/adminShow"
				}]
			}
		},
		methods: {
			ItemClick(event){
				let url = event.currentTarget.dataset.url
				uni.navigateTo({
				  url: url
				})
			},
			
			uploadExcel(){
				uni.showLoading({
					title: "努力导入中..."
				});
				let me = this
				wx.chooseMessageFile({
					type: 'file',
					success(res){
						const tempFilePaths = res.tempFiles
						console.log(res)
						uni.uploadFile({
							url: me.serverUrl+"/upload/excel",
							filePath: tempFilePaths[0].path,
							name: 'file',
							success: (res) => {
								console.log(res);
								uni.showModal({
									title: '提示',
									content: '导入成功',
									showCancel: false
								})
							},
							fail: (res) => {
								console.log(res)
							},
							complete: () => {
								uni.hideLoading();
							}
						})
					}
				})
			}
		}
	}
</script>

<style>
	@import url("adminList.css");
	@import url("./../../common/icon.css");
	@import url("./../../common/commons.css");
</style>
