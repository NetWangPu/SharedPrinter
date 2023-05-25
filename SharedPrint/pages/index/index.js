// pages/index/index.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    printerList: [],
    printerListLen: 0,
    isAdmin: false,
  },
  /**
   * 获取获得打印机列表
   */
  getDeviceList() {
    //请求服务器获取获得授权的设备列表
    wx.request({
      url: getApp().globalData.serverUrl + '/allprinter',
      method: 'GET',
      success: (res) => {
        console.log(res)
        this.setData({
          printerList: res.data,
          printerListLen: res.data.length
        })
      }
    }
    )
  },

  /**
  * 打印文件 printFile
  * @param {e} options 
  */
  printFile(e) {
    console.log(e.currentTarget.dataset.id)
    wx.navigateTo({
      url: '/pages/index/print?id=' + e.currentTarget.dataset.id,
    })
  },
  /**
  * 管理打印机
  * @param {e} options 
  */
  managePrinter(e) {
    console.log(e.currentTarget.dataset.id)
    wx.navigateTo({
      url: '/pages/index/managePrinter?id=' + e.currentTarget.dataset.id,
    })
  },

  /**
   * 添加打印机 addPrinters
   */
  addPrinters() {
    wx.navigateTo({
      url: '/pages/index/addPrinter',
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getDeviceList();
    //从缓存中获取用户身份isAdmin 赋值给data中的isAdmin
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
    this.getDeviceList();

  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
    this.getDeviceList();

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
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })

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