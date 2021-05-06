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
			}
		}
	}
</script>

<style>
	@import url("adminList.css");
	@import url("./../../common/icon.css");
	@import url("./../../common/commons.css");
</style>
