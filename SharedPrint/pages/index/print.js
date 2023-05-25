// pages/index/print.js


Page({

  /**
   * 页面的初始数据
   */
  data: {
    printer: {},
    steps: [
      {
        text: '步骤一',
        desc: '上传文件',
      },
      {
        text: '步骤二',
        desc: '选择参数',
      },
      {
        text: '步骤四',
        desc: '提交任务',
      },
    ],
    active: 0, //当前执行步骤
    radio: '2', //1事黑白，2是彩色
    radios: [], // 定义 radios 变量并将其初始值设置为空数组
    filePathName: "", //上的文件名
    valuepages: 0, //打印页数
    priace: 0, //打印价格
    //打印机id
    printerid: 0,
    userid: 100,
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
    //从缓存中获取用户id userInfo
    wx.getStorage({
      key: 'userInfo',
      success: (res) => {
        console.log(res)
        this.setData({
          userid: res.data
        })
      }
    })
  
    wx.request({
      url: getApp().globalData.serverUrl + '/printer?id=' + options.id,
      method: 'GET',
      success: (res) => {
        console.log(res)
        this.setData({
          printer: res.data
        })
      }
    })
    // this.getgoodsList();
  },

  onChange(e) {
    console.log(e)
    this.setData({
      active: 2,
      radio:e.detail
    })
  },

  changePages(e) {
    console.log(e.detail)
    var priace = e.detail * 0.5*100
    this.setData({
      priace: priace
    })
  },

  /**
   * 提交打印文件表单  http://localhost:8090/addorder?PrintNum=1&Printcolor=1&Printpaper=12&ordererid=1&printfile=349823904893291.docx&printid=1&printprice=12
   */
  onSubmit: function () {
    // 获取打印份数和打印页数的输入值
    var printCount = this.selectComponent('#print-count').data.value;
    var pageCount = this.selectComponent('#print-pages').data.value;
    // 获取黑白打印和彩色打印的选择值
    var printType = this.data.radio;

    // 获取其他所需的值
    var steps = this.data.steps;
    var active = this.data.active;
    var radios = this.data.radios;

    // 打印获取到的值，或者提交订单到后台
    console.log('打印份数：', printCount);
    console.log('打印页数：', pageCount);
    console.log('打印类型：', printType);
    console.log('当前步骤：', steps[active].text);
    console.log('打印文件名称：', this.data.filePathName);
    console.log('打印机id：', this.data.printerid);
    console.log('打印价格：', this.data.priace);
    console.log('打印用户id：', this.data.userid); 
    // 你要在这个里面写一个回调函数 下面的这个函数就是回调函 演示
    //http://localhost:8090/addorder?PrintNum=123&Printcolor=123&Printpaper=123&ordererid=123&printfile=123&printid=123&printprice=123
    // curl -X POST "http://localhost:8090/addorder?PrintNum=123&Printcolor=123&Printpaper=123&ordererid=123&printfile=123&printid=123&printprice=123" -H "accept: */*"
    //post请求
    this.data.priace = this.data.priace/100;
    //检验数据
    if (printCount == 0 || pageCount == 0  || this.data.filePathName == "" || this.data.printerid == 0 || this.data.priace == 0 || this.data.userid == 0) {
      wx.showToast({
        title: '请检查数据是否完整',
        icon: 'none'
      })
      return;
    }

    wx.request({
      url: getApp().globalData.serverUrl + '/addorder?PrintNum=' + printCount + '&Printcolor=' + printType + '&Printpaper=' + pageCount + '&ordererid=' + this.data.userid + '&printfile=' +
       this.data.filePathName + '&printid=' + this.data.printerid + '&printprice=' + this.data.priace,
      method: 'POST',
      success: (res) => {
        console.log(res)
        if (res.data == 1) {
          wx.showToast({
            title: '提交成功',
            icon: 'success'
          })
          wx.navigateBack({
            delta: 1
          })
        }
        else{
          wx.showToast({
            title: '提交失败',
            icon: 'none'
          })
        }
      }
    })
  },


  /**
   * 上传文件
   * curl -X POST "http://localhost:8090/upload" -H 
  */
  uploadFile() {
    var that = this;
    wx.chooseMessageFile({
      count: 1,
      type: 'file',
      success: (res) => {
        console.log(res)
        const tempFilePaths = res.tempFiles[0].path
        console.log(tempFilePaths)
        wx.uploadFile({
          url: getApp().globalData.serverUrl + '/upload',
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
                that.setData({
                  active: 1,
                  filePathName: res.data
                })
              }
            })
          }
        })
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