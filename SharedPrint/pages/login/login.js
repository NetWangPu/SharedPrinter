// pages/login/login.js
Page({

  /**
   * 页面的初始数据
   */
  data: {

    //绑定表单数据
    username: '',
    password: '',
    type: ''
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {

  },
  formSubmit(e) {
    console.log('form发生了submit事件，携带数据为：', e.detail.value)
    //效验表单数据 e.detail.value.username e.detail.value.password 是否为空
    //校验数据
    if (!e.detail.value.username.trim()) {
      wx.showToast({
        title: '用户名不能为空',
        icon: 'none'
      })
      return
    }
    if (!e.detail.value.password.trim()) {
      wx.showToast({
        title: '密码不能为空',
        icon: 'none'
      })
      return
    }

    //将表单数据存储到data中
    this.setData({
      username: e.detail.value.username,
      password: e.detail.value.password,
    })

    //获取表单数据中的radio-group 用于判断身份
    console.log(e.detail.value.radiogroup)
    console.log("开始判断 并登录")
    //判断身份
    if (e.detail.value.radiogroup == 'user') {
      //用户登录
      wx.request({
        url: getApp().globalData.serverUrl+'/login?username=' + this.data.username + '&password=' + this.data.password,
        method: 'POST',
        success: res => {
          console.log(res)
          if (res.data != -1) {
            wx.showToast({
              title: '登录成功',
              icon: 'success'
            })
            wx.setStorageSync('userInfo', res.data)
            wx.setStorageSync('isLogin', true)
            wx.setStorageSync('isAdmin', false)
            wx.switchTab({
              url: '/pages/index/index',
            })
          } else {
            wx.showToast({
              title: "账号或密码错误",
              icon: 'none'
            })
          }
        }
      })
    }
    if (e.detail.value.radiogroup == 'admin') {
      wx.request({
        url: getApp().globalData.serverUrl+'/adminLogin?username=' + this.data.username + '&password=' + this.data.password,
        method: 'POST',
        success: res => {
          console.log(res)
          if (res.data == 1) {
            wx.showToast({
              title: '登录成功',
              icon: 'success'
            })
            wx.setStorageSync('userInfo', res.data)
            wx.setStorageSync('isLogin', true)
            wx.setStorageSync('isAdmin', true)
            wx.switchTab({
              url: '/pages/index/index',
            })
          } else {
            wx.showToast({
              title: "账号或密码错误",
              icon: 'none'
            })
          }
        }
      })
    }
    if (e.detail.value.radiogroup == 'register') {
      //注册
      wx.request({
        url: getApp().globalData.serverUrl+'/adduser?password=' + this.data.password + '&username=' + this.data.username,
        method: 'POST',
        success: res => {
          console.log(res)
          if (res.data != 0) {
            wx.showToast({
              title: '注册成功',
              icon: 'success'
            })
            wx.setStorageSync('userInfo', res.data)
            wx.setStorageSync('isLogin', true)
            wx.setStorageSync('isAdmin', false)
            wx.switchTab({
              url: '/pages/index/index',
            })
          } else {
            wx.showToast({
              title: "用户已存在",
              icon: 'none'
            })
          }
        }
      })
    }
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