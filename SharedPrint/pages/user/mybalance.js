// pages/user/mybalance.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    //我的余额
    myBalance: 0,
    show: false,
    actions: [
      {
        name: '充值5元',
        
      },
      { 
        name: '充值20元',
        // subname: '选择响应的金额进行充值',
        // openType: 'share',
      },
    ],

  },
  onClose() {
    this.setData({ show: false });
  },
   /**
  *充值
  *ttp://localhost:8090/recharge?balance=5&id=+id
  */
  toRecharge(money) {
    var id = wx.getStorageSync('userInfo');
    console.log(id)
    console.log(money)
    wx.request({
      url: getApp().globalData.serverUrl + '/recharge?balance=' + money + '&id=' + id,
      method: 'GET',
      success: (res) => {
        console.log(res)
        if (res.data == 0) {
          wx.showToast({
            title: '充值成功',
            icon: 'success',
            duration: 2000
          })
          this.getMyBalance(id);
        }
      }
    }
    )
  },

  onSelect(event) {
    console.log(event.detail);
    if (event.detail.name == "充值5元") {
      this.toRecharge(5);
      console.log("充值5元")
    }
    else if (event.detail.name == "充值20元") {
      this.toRecharge(20);
      console.log("充值20元")
    }
    this.onClose();
  },
  /**
   * 获取我的余额
   * http://localhost:8090/user?id=1
   */
  getMyBalance(id) {
    wx.request({
      url: getApp().globalData.serverUrl + '/user?id=' + id,
      method: 'GET',
      success: (res) => {
        console.log(res)
        this.setData({
          myBalance: res.data.balance
        })
      }
    }
    )
  },

 
  
  /**
   * 充值
   */
  recharge() {
    this.setData({ show: true });
  },
    

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    //从缓存中获取用户id
    var id = wx.getStorageSync('userInfo');
    this.getMyBalance(id);

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {

  },

  /**
   * 生命周期函数--监听页面隐藏
   */
  onHide() {

  },

  /**
   * 生命周期函数--监听页面卸载
   */
  onUnload() {

  },

  /**
   * 页面相关事件处理函数--监听用户下拉动作
   */
  onPullDownRefresh() {

  },

  /**
   * 页面上拉触底事件的处理函数
   */
  onReachBottom() {

  },

  /**
   * 用户点击右上角分享
   */
  onShareAppMessage() {

  }
})