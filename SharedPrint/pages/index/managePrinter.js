// pages/index/managePrinter.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    printerid: '',
    printerInfo: {},

  },
  /**
   * 查询打印机信息
   * http://localhost:8090/printer?id=1
   * @param {*} id
   * @returns 无
   */
  getPrinterInfo(id) {
    var that = this;
    wx.request({
      url: getApp().globalData.serverUrl + '/printer?id=' + id,
      method: 'GET',
      success: function (res) {
        console.log(res.data);
        that.setData({
          printerInfo: res.data,
        })
      }
    })
  },

  /**
   * 删除打印机 
   * http://localhost:8090/deleteprinter?id=3
   */
  deletePrinter() {
    //提示 
    wx.showModal({
      title: '提示',
      content: '确定要删除该打印机吗？',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: getApp().globalData.serverUrl + '/deleteprinter?id=' + this.data.printerid,
            method: 'GET',
            success: (res) => {
              console.log(res)
              if (res.data == 1) {
                wx.showToast({
                  title: '删除成功',
                  icon: 'success',
                  duration: 1000
                })
                wx.navigateBack({
                  delta: 1
                })
              }
            }
          })
        }
      }
    })
  },
  /**
   * 加纸
   * http://localhost:8090/addpaper?id=3&numberofpaper=100
   */
  addPaper() {
    wx.request({
      url: getApp().globalData.serverUrl + '/addpaper?id=' + this.data.printerid + '&numberofpaper=100',
      method: 'GET',
      success: (res) => {
        console.log(res)
        if (res.data == 1) {
          wx.showToast({
            title: '加纸成功',
            icon: 'success'
          })
          this.getPrinterInfo(this.data.printerid);
        }
      }
    })
  },
  updateState1() {
    console.log(1)
    this.updateState(1);
  },
  updateState0() {
    console.log(0)
    this.updateState(0);
  },

  /**
   * 更改打印机状态为在线或者离线
   * http://localhost:8090/updatestate?id=1&state=%E5%9C%A8%E7%BA%BF
   * @param {*} e 1 在线 0 离线
   */
  updateState(e) {
    console.log("updateState" + e)
    wx.request({
      url: getApp().globalData.serverUrl + '/updatestate?id=' + this.data.printerid + '&state=' + e ,
      method: 'GET',
      success: (res) => {
        console.log(res)
        if (res.data == 1) {
          wx.showToast({
            title: '修改成功',
            icon: 'success'
          })
          this.getPrinterInfo(this.data.printerid);
        }
      }
    })
  },
  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    console.log(options)
    //http://localhost:8090/printer?id=1
    this.setData({
      printerid: options.id
    })
    this.getPrinterInfo(options.id);
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