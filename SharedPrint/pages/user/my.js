// pages/user/my.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    nameID: '就是我',
    myOrderNum: 1,
    show: false,
    isAdmin: false,

  },

  onClose() {
    this.setData({ show: false });
  },

  /**
   * 修改密码 确认修改
   */
  ChangePasswordConfirm(e) {
    console.log(e)
    const newPassword = e.detail.value.newPassword
    const confirmPassword = e.detail.value.confirmPassword
    if (newPassword != confirmPassword) {
      wx.showToast({
        title: '两次密码不一致',
        icon: 'none'
      })
      return
    }
    // if (newPassword.length < 6) {
    //   wx.showToast({
    //     title: '密码长度不能小于6位',
    //     icon: 'none'
    //   })
    //   return
    // }
    //修改密码
    wx.request({
      url: getApp().globalData.serverUrl+'/updateuser?id=' + this.data.userInfo + '&password=' + newPassword,
      method: 'GET',
      success: (res) => {
        console.log("修改密码成功")
        console.log(res)
        if (res.data == 1) {
          wx.showToast({
            title: '修改成功',
            icon: 'success'
          })
          this.setData({
            ChangePasswordvisibleView: false
          })
        }
       
      }
    })
  },

  logout() {
    wx.showModal({
      title: '提示',
      content: '确定要退出登录吗？',
      success(res) {
        if (res.confirm) {
          wx.removeStorageSync('userInfo')
          wx.removeStorageSync('isLogin')
          wx.removeStorageSync('isAdmin')
          //跳转到登录页面
          wx.redirectTo({
            url: '/pages/login/login',
          })
          //清空所有缓存
          wx.clearStorage()
          //清空所有数据
          wx.clearStorageSync()
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
  },
  /**
   * exportExcal 导出excel
   */
  exportExcal() {
    wx.showLoading({
      title: '加载中',
    })
    wx.request({
      url: getApp().globalData.serverUrl + '/exportgoods',
      method: "POST",
      responseType: 'arraybuffer', //此处是请求文件流，必须带入的属性
      success: res => {
        if (res.statusCode === 200) {
          const fs = wx.getFileSystemManager(); //获取全局唯一的文件管理器
          fs.writeFile({
            filePath: wx.env.USER_DATA_PATH + "/订单数据.xlsx", // wx.env.USER_DATA_PATH 指定临时文件存入的路径，后面字符串自定义
            data: res.data,
            encoding: "binary", //二进制流文件必须是 binary
            success (res){
              wx.openDocument({ // 打开文档
                filePath: wx.env.USER_DATA_PATH + "/订单数据.xlsx",  //拿上面存入的文件路径
                showMenu: true, // 显示右上角菜单
                success: function (res) {
                  setTimeout(()=>{wx.hideLoading()},500)
                }
              })
            }
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面初次渲染完成
   */
  onReady() {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
  },

  /**
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
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