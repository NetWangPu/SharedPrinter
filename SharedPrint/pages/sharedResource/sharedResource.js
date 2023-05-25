// pages/sharedResource/sharedResource.js
Page({

  /**
   * 页面的初始数据
   */
  data: {
    fileList: [],
    fileListLen: 0 ,
    isAdmin: false,

  },

  /**
   * 删除文件
   * post
   * http://localhost:8090/deleteFile?fileName=%E7%94%B5%E5%AD%90%E5%85%83%E4%BB%B6%E6%B8%85%E7%82%B9.xlsx
    * @param {*} e 文件名称
   */
  DeleteFile(e) {
    console.log(e.currentTarget.dataset.name)
    var name = e.currentTarget.dataset.name
    wx.showModal({
      title: '提示',
      content: '确定要删除该文件吗？',
      success: (res) => {
        if (res.confirm) {
          wx.request({
            url: getApp().globalData.serverUrl + '/deleteFile?fileName=' + name,
            method: 'POST',
            success: (res) => {
              console.log(res)
              if (res.data == 1) {
                wx.showToast({
                  title: '删除成功',
                  icon: 'success',
                  duration: 1000
                })
                this.getAllFiles();
              }
            }
          })
        }
      }
    })
  },

  /**
   * 生命周期函数--监听页面加载
   */
  onLoad(options) {
    this.getAllFiles();
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
    this.getAllFiles();

  },

  /**
   * 请求服务器获取资源列表 http://localhost:8090/getAllFiles
   */
  getAllFiles() {
    //请求服务器获取获得列表
    wx.request({
      url: getApp().globalData.serverUrl + '/getAllFiles',
      method: 'GET',
      success: (res) => {
        console.log(res)
        this.setData({
          fileList: res.data,
          fileListLen: res.data.length
        })
      }
    }
    )
  },

  /**
   * 上传文件
   * curl -X POST http://localhost:8090/uploadsharedfile?filename=%E8%B4%B7%E6%AC%BE
   * 上传时不改变文件名
  */
  uploadFile() {
    var that = this;
    wx.chooseMessageFile({
      count: 1,
      type: 'file',
      success: (res) => {
        console.log(res)
        //获取文件名称
        var filename = res.tempFiles[0].name
        console.log(filename)
        const tempFilePaths = res.tempFiles[0].path
        console.log(tempFilePaths)
        wx.uploadFile({
          url: getApp().globalData.serverUrl + '/uploadsharedfile?filename=' + filename,
          filePath: tempFilePaths,
          name: 'file',
          formData: {
          },
          success: (res) => {
            console.log(res)
            wx.showToast({
              title: '上传成功',
              icon: 'success',
              duration: 2000,
              success: () => {
                that.getAllFiles();
              }
            })
          }
        })
      }
    })
  },

  /**
   * DownFile 下载文件
   * @param {*} e
   */
  DownFile(e) {
    console.log(e.currentTarget.dataset.name)
    var name = e.currentTarget.dataset.name
    wx.showLoading({
      title: '加载中',
    })
    wx.request({
      url: getApp().globalData.serverUrl + '/downloadFile?fileName='+name,
      method: "POST",
      responseType: 'arraybuffer', //此处是请求文件流，必须带入的属性
      success: res => {
        if (res.statusCode === 200) {
          const fs = wx.getFileSystemManager(); //获取全局唯一的文件管理器
          fs.writeFile({
            filePath: wx.env.USER_DATA_PATH + "/"+name, // wx.env.USER_DATA_PATH 指定临时文件存入的路径，后面字符串自定义
            data: res.data,
            encoding: "binary", //二进制流文件必须是 binary
            success (res){
              wx.openDocument({ // 打开文档
                filePath: wx.env.USER_DATA_PATH + "/"+name,   //拿上面存入的文件路径
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
   * 生命周期函数--监听页面显示
   */
  onShow() {
    this.setData({
      isAdmin: wx.getStorageSync('isAdmin')
    })
    this.getAllFiles();

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