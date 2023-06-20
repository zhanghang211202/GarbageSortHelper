<template>
	
	<view class="content" v-if="controlShow">
		<scroll-view scroll-x class="bg-white padding response cu-steps steps-bottom" :scroll-into-view="'scroll-' + (current-1)"
		 scroll-with-animation>
			<view v-if="index!=0" class="cu-item padding-lr-xl" 
			:class="{'text-green':res[index]==1&&index<=current,'text-red':res[index]==2&&index<=current}" 
			v-for="(item,index) in 11" :key="index" :id="'scroll-' + index">
				<text class="num" :class="res[index]==2?'err':''" :data-index="index"></text>
			</view>
		</scroll-view>
		<view :animation="animationData" class="main-panel">
			<view class="quesion-panel">
				<view class="quesion-panel-item1">
					<view class="quesion-panel-text"> {{questionBanks.length==0?"干电池":questionBanks[current].garbageName}}</view>
				</view>
				<view class="quesion-panel-item2">
					<view class="">属于哪种垃圾分类？</view>
				</view>
				<view class="quesion-panel-item3">
					<view class="gary">（点击下方图标选择）</view>
				</view>
				<!-- <view class="quesion-panel-item2">
					<view class="">{{current+1}}/10</view>
				</view> -->
			</view>
			<view class="main-show-classify">
				<view class="main-classify">
					<view @click="switchTabToClassify(1)">
						<image class="main-img" src="../../static/icos/ico-1.jpg"></image>
					</view>
					<view @click="switchTabToClassify(2)">
						<image class="main-img" src="../../static/icos/ico-2.jpg"></image>
					</view>
				</view>
				<view class="main-classify">
					<view @click="switchTabToClassify(3)">
						<image class="main-img" src="../../static/icos/ico-3.jpg"></image>
					</view>
					<view @click="switchTabToClassify(4)">
						<image class="main-img" src="../../static/icos/ico-4.jpg"></image>
					</view>
				</view>
			</view>
			<view class="top-text">
				<view class="">
					{{current+1}}
				</view>
				<view class="gary">
					/ {{questionLength}}
				</view>
			</view>
		</view>

		<!-- <share /> -->
	</view>
</template>

<script>
	import share from "@/components/share.vue"

	export default {
		components: {
			share,
		},
		data() {
			return {
				res: [],
				questionBanks: [],
				score: 0,
				current: 0,
				controlShow: false,
				animationData: {},
				questionLength:10
			}
		},
		onload(){
			for(var i = 0; i < this.questionLength; i++){
				this.res[i] = 0;
			}
		},
		onShow() {
			let isLogin = getApp().globalData.isLogin
			if(!isLogin){
				uni.switchTab({
					url:'/pages/user/user'
				})
			}else{
				
				this.controlShow=true;
				uni.showLoading({
					title: "加载中..."
				})
				console.log("size:"+this.questionBanks.length)
				this.randTen();
			}
		},
		methods: {
			donghua() {
				var animation = uni.createAnimation({
					duration: 1000,
					timingFunction: 'ease',
				})
				this.animation = animation
				animation.opacity(0).translate(-300).step()
				this.animationData = animation.export()
				console.log("1000")
				setTimeout(function() {
					animation.translate(10).opacity(0.5).step({
						duration: 0
					})
					this.animationData = animation.export()
				}.bind(this), 400);
				setTimeout(function() {
					animation.translate(0).opacity(1).step({
						duration: 800
					})
					this.animationData = animation.export()
					console.log("1000")
				}.bind(this), 500);
				console.log("动画呢")
			},

			randTen() {
				console.log("初始化")
				if (this.questionBanks.length > 0 && this.current < 9) {
					uni.hideLoading();
					return false;
				}
				this.current = 0;
				this.score = 0;
				this.controlShow = true;
				uni.request({
					url: this.serverUrl + '/qb/randOne?num=10',
					success: (res) => {
						console.log('随机是个'+res)
						console.log(res.data.data);
						this.questionBanks = res.data.data;
						
					},
					complete() {
						uni.hideLoading();
					}
				});
			},
			switchTabToClassify(index) {
				if (this.current == 9) this.controlShow = false;
				console.log(index)
				console.log(this.questionBanks[this.current])
				this.questionBanks[this.current]['selectedType'] = index;

				console.log("当前的需要：" + this.current)
				if (this.questionBanks[this.current].garbageType == index) {
					this.donghua();
					let me = this;
					setTimeout(function() {
						me.score++;
						me.current++;
						me.res[me.current] = 1;
						me.gotoNav();
					}, 400);

				} else {
					let obj = this.questionBanks[this.current];
					let type = obj.garbageType == 1 ? '干垃圾' : obj.garbageType == 2 ? '湿垃圾' : obj.garbageType == 3 ? '可回收物' : '有害垃圾';
					let temp = obj.garbageName + " 属于 " + type;
					let me = this;
					uni.showModal({
						title: "选错啦",
						content: temp,
						confirmText: "知道了",
						showCancel: false,
						success: function(res) {
							console.log(res);
							console.log("点击了确认:me.current:" + me.current);
							me.donghua();
							setTimeout(function() {
								me.current++;
								me.res[me.current] = 2;
								me.gotoNav();
							}, 400);
						}
					})
				}
				console.log("res: "+this.res);
			},
			gotoNav() {
				if (this.current >= 10) {
					console.log("要进行跳转拉")
					let list = JSON.stringify(this.questionBanks);
					let score = this.score;
					let userId = getApp().globalData.userId;
					uni.request({
						url: this.serverUrl + '/challenge', 
						data: {
							"score": score,
							"userId": userId,
							"list": this.questionBanks
						},
						header: {
							'content-type': "application/json"
						},
						method: "POST",
						success: (res) => {
							console.log(res)
							console.log(res.data.data);
						}
					});
					uni.navigateTo({
						url: `/pages/challenge/challengeResult?score=${score}&list=${list}`
					});
				}
			}
		}
	}
</script>

<style>
	@import url("challenge.css");
	@import url("../../static/colorui/main.css");
	@import url("../../static/colorui/icon.css");
	@import url("../../static/colorui/animation.css");
</style>
