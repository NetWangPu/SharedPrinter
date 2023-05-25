// app.js
App({
  onLaunch() {
    //判断用户是否登录
    let isLogin = wx.getStorageSync('isLogin')
    if (isLogin) {
      console.log("用户已登录")
      console.log(wx.getStorageSync('isLogin'))
      this.globalData.isLogin = true
      //判断用户是否是管理员
      let isAdmin = wx.getStorageSync('isAdmin')
      if (isAdmin) {
        console.log("用户是管理员")
        console.log(wx.getStorageSync('isAdmin'))
        this.globalData.isAdmin = true
      }
      //跳转到首页
      wx.switchTab({
        url: '/pages/index/index',
      })
    } else {
      console.log("用户未登录")
      console.log(wx.getStorageSync('isLogin'))
      this.globalData.isLogin = false
      //跳转到登录页面
      wx.redirectTo({
        url: '/pages/login/login',
      })
    }
  },
  //全局变量
  globalData: {
    isAdmin: false,
    isLogin: false,
    userInfo: "",
    //服务器地址  
    // serverUrl: 'http://localhost:8091'
  }
})
