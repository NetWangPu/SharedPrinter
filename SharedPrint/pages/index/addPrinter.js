// pages/index/addPrinter.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },

  /**
   * 提交打印机信息 并添加
   * http://localhost:8090/addprinter?position=位置
   * get请求
   */
  formSubmit(e) {
    console.log(e.detail.value);
    //判断打印机名称是否为空 position
    if (e.detail.value.position == "") {
      wx.showToast({
        title: '打印机名称不能为空',
        icon: 'none'
      })
      return;
    }
    //提交打印机信息
    wx.request({
      url:  getApp().globalData.serverUrl +'/addprinter?position=' + e.detail.value.position,
      method: 'GET',
      success: (res) => {
        console.log(res)
        if (res.data == 1) {
          wx.showToast({
            title: '添加成功',
            icon: 'success'
          })
          wx.navigateBack({
            delta: 1
          })
        }
      }
    })
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