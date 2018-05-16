var $btn_login_header = $('#btn_login_header')
var $btn_register_header = $('#btn_register_header')

$(document).ready(function(){
    $(".header").load("common-header");
    $(".footer").load("common-footer");


    footerPosition();
    $(window).resize(footerPosition);

    queryUserInfoHeader()


});

var queryUserInfoHeader = function () {
    var url = '/get-userinfo'

    $.get(url, function (res) {
        var data = res.data
        if (res.errcode == 1) {
            $('#btn_login_header').hide()
            $('#btn_register_header').hide()


            $('.ele_after_login').css('display', 'inline-block')
            $('#header_user_info').css('display', 'block')
            $('#header_user_name').html(data.userName)
        } else {
            // alert(res.errmsg)
        }
    })
}

function getUrlParam(name) {
    //构造一个含有目标参数的正则表达式对象
    var reg = new RegExp("(^|&)" + name + "=([^&]*)(&|$)");
    //匹配目标参数
    var r = window.location.search.substr(1).match(reg);
    //返回参数值
    if(r != null) {
        return decodeURI(r[2]);
    }
    return null;
}

function formatTime(timestamp) {
    var date = new Date(parseInt(timestamp))

    var nowYear = date.getFullYear()
    var nowMonth = date.getMonth()
    var nowDate = date.getDate()

    nowMonth = doHandleMonth(nowMonth + 1);
    nowDate = doHandleMonth(nowDate);
    return nowYear + "年" + nowMonth + "月" + nowDate;
}

function doHandleMonth(month) {
    if (month.toString().length == 1) {
        month = "0" + month;
    }
    return month;
}

function footerPosition(){
    // $("bottom").css({"position":"","bottom":"","width":"","height":"","background-color":""});
    // var contentHeight = document.body.scrollHeight;//网页正文全文高度
    var contentHeight = 216.8 + 30 + $('.mbody').height() + 70
    var winHeight = window.innerHeight;//可视窗口高度，不包括浏览器顶部工具栏
    if(contentHeight < winHeight-150){
        //当网页正文高度小于可视窗口高度时，为footer添加类fixed-bottom
        //position:absolute;bottom:0;width:100%;height:50px;background-color: red;
        $(".bottom").addClass("fixed-bottom");
    } else {
        $(".bottom").removeClass("fixed-bottom");
    }
}