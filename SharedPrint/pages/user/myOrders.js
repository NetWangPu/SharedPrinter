// pages/user/myOrders.js
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
   * http://localhost:8090/order?id=0
   * @param {id} id 
   */
  getOrderList(id) {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl +'/order?id=' + id,
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
    //获取订单列表
    // 获取id
    var id = wx.getStorageSync('userInfo');
    console.log(id);
    this.getOrderList(id);
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