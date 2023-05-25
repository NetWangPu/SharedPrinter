// pages/user/allOrders.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    OrderList: [],
    OrderListLen: 0,

  },
  /**
   * 获取订单列表
   * http://localhost:8090/allorder
   */
  getAllOrderList() {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl +'/allorder',
      method: 'GET',
      success: function (res) {
        console.log(res.data);
        that.setData({
          OrderList: res.data,
          OrderListLen: res.data.length
        })
      }
    })
  },
  

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getAllOrderList();

  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    this.getAllOrderList();
    

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.getAllOrderList();
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