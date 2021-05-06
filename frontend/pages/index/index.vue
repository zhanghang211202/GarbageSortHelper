<template>
	<view>
		<swiper class="card-swiper square-dot" :indicator-dots="true" :circular="true"
		 :autoplay="true" interval="5000" duration="500" @change="cardSwiper" indicator-color="#8799a3"
		 indicator-active-color="#3baa00">
			<swiper-item v-for="(item,index) in swiper" :key="index" :class="cardCur==index?'cur':''">
				<view class="swiper-item">
					<image :src="item.imageUrl" mode="aspectFill"></image>
				</view>
			</swiper-item>
		</swiper>

		<!-- <view class="search-box"> -->
		<view class="form-view">
			<view class="tip-text">你知道 [{{randOneObj.garbageName}}] 属于哪种类型的垃圾吗? </view>
			<form @submit="formSubmit" @reset="formReset" class="form-form">
				<view class="input-view">
					<view @click="moveToPhoto" class="input-view-item input-view-camera">
						<image class="search-img" src="../../static/icos/camera.png"></image>
					</view>
					<view @click="moveToRecord" class="input-view-item input-view-speech">
						<image class="search-img" src="../../static/icos/record.png"></image>
					</view>
					<view class="input-view-item input-view-search">
						<input confirm-type="search" @confirm="searchKeyword" @search="searchKeyword" v-model="keyword" id="inputid"
						 class="input-search" name="input" placeholder="输入搜索关键词" />
					</view>
					<view @tap="searchKeyword" class=" font-search">查询</view>
				</view>
			</form>
		</view>
		<view v-show="true">
			<view class="">
				<uni-notice-bar show-icon="false" color="#999" backgroundColor="rgb(242,242,242)" text="提示:本查询系统就供参考,具体分类要求以属地专业管理部门为准">
				</uni-notice-bar>
			</view>
		</view>
		<view class="">
			<view class="simpleTips">
				{{randomTip}}
			</view>
		</view>
		<view class="main-show-classify">
			<view class="main-classify">
				<view @click="moveToClassify(1)">
					<image class="main-img" src="../../static/icos/ico-1.jpg"></image>
				</view>
				<view @click="moveToClassify(2)">
					<image class="main-img" src="../../static/icos/ico-2.jpg"></image>
				</view>
			</view>
			<view class="main-classify">
				<view @click="moveToClassify(3)">
					<image class="main-img" src="../../static/icos/ico-3.jpg"></image>
				</view>
				<view @click="moveToClassify(4)">
					<image class="main-img" src="../../static/icos/ico-4.jpg"></image>
				</view>
			</view>
		</view>
		<share/>
	</view>
</template>

<script>
	import uniNoticeBar from "../../components/uni-notice-bar/uni-notice-bar.vue"
	import uniIcon from "../../components/uni-icon/uni-icon.vue"
	import uniPopup from "@/components/uni-popup/uni-popup.vue"
	import share from "@/components/share.vue"

	export default {
		components: {
			uniNoticeBar,
			uniIcon,
			uniPopup,
			share
		},
		data() {
			return {
				cardCur: 0,
				title: '这是我的小程序，开始了',
				defaultKeyword: "111",
				keyword: "干电池",
				popupShow: false,
				voicePath: '',
				startTiming: false,
				drawTiming: false,
				timeoutTiming: false,
				maxTime: 5000,
				frame: 50,
				swiper: [],
				isShowKeywordList: false,
				keywordList: [],
				randOneObj: {
					garbageName: "干电池"
				},
				randomTip: "",
				simpleTips: [
					"让垃圾找到自己的归属",
					"请给垃圾找个合适的家",
					"希望有一天，垃圾桶也会下岗",
					"垃圾分类放，环境有保障",
					"垃圾要分类，资源要利用",
					"今天分一分，明天美十分",
					"积极参与垃圾分类，共创优美社区环境",
					"分类收集人人有责，男女老幼齐来参与",
					"提高社区的品味，从垃圾分类开始",
					"垃圾分类，举手之劳",
					"配合垃圾分类，争做文明市民",
					"垃圾分类益处多，环境保护靠你我",
					"要是垃圾变为宝，分类回收不可少",
					"请给垃圾找个合适的家",
					"垃圾儿女要分家，安居乐业靠大家",
					"垃圾分类人人做，做好分类为人人",
					"让垃圾找到自己的归属",
					"垃圾要分类，生活变美好",
					"你我虽渺小，但意义重大",
					"让垃圾找到自己的归属",
					"请给垃圾找个合适的家",
				],
			}
		},
		onShow() {
			this.randOne();
			this.randomTip = this.simpleTips[Math.round(Math.random() * 20)];
		},
		onLoad() {
			let me = this;
			uni.showLoading({
				title: "加载中..."
			});

			//  获取幻灯片 swiper start
			uni.request({
				url: me.serverUrl + '/slideShow',
				success(res) {
					me.swiper = res.data.data;
				},
				complete() {
					uni.hideLoading();
				}
			});
		},
			//  获取幻灯片 swiper end
		methods: {
			// cardSwiper
			cardSwiper(e) {
				this.cardCur = e.detail.current
			},
			
			randOne() {
				uni.request({
					url: this.serverUrl + '/qb/randOne',
					success: (res) => {
						this.randOneObj = res.data.data[0];
						//搜索框同步替换
						this.keyword = res.data.data[0].garbageName;
					}
				});
			},
			// 幻灯片 的点击跳转事件 start
			navigateTo(skipUrl) {
				uni.navigateTo({
					url: '/pages/webview/webview?url=' + skipUrl
				});
			},
			// 幻灯片 的点击跳转事件 end
			searchKeyword() {
				let me = this;
				if (!me.keyword) return;
				uni.request({
					url: me.serverUrl + "/search/uniname/" + me.keyword, 
					success: (res) => {
						console.log(res);
						let data = res.data.data;
						let uniOne = JSON.stringify(data.uni);
						let results = JSON.stringify(data.results);
						console.log(res.data.data)
						uni.navigateTo({
							url: `/pages/search/search?type=3&keyword=${me.keyword}&keywordList=${results}&uni=${uniOne}`
						});
					}
				});
			},
			moveToRecord() {
				uni.navigateTo({
					url: `/pages/search/search?type=2`
				});
			},
			
			moveToPhoto() {
				uni.navigateTo({
					url: `/pages/search/search?type=1`
				});
			},
			
			moveToClassify(index) {
				console.log(index)
				getApp().globalData.typeid = index
				uni.switchTab({
					url: '/pages/type/type',
				});
			},
			
		}
	}
</script>

<style>
	@import url("index.css");
	@import url("../../static/colorui/main.css");
	@import url("../../static/colorui/icon.css");
</style>
