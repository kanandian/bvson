var $item_content = $('#item_content')
var $commodity_content = $('#commodity_content')

var bindAll = function () {
    queryUserInfo()

    $('#btn_update_info').on('click', function () {
        updateUserInfo()
    })

    $('#btn_update_password').on('click', function () {
        updateUserPassword()
    })

    $('#shop_cart').on('click', function () {
        window.location.href='/shopping-cart'
    })
    $('#attend_activity').on('click', function () {
        window.location.href='/attend-activity'
    })

    $('#my_activity').on('click', function () {
        window.location.href = '/my-activity'
    })

    $('#buy_records').on('click', function () {
        queryBuyRecords()

        // $('.btn-receipt').each(function () {
        //     var status = $(this).attr('status')
        //     if (status == 1) {
                $(this).show()
                // $(this).css('display', 'inline-block')
            // }
        // })
    })

    // $('body').on('click', '.status-admin', function () {
    //     var status = $(this).attr('status')
    //
    //     if (status == 0) {
    //         $(this).html('已下单')
    //     } else if (status == 1) {
    //         $(this).html('正在配送')
    //     } else if (status == 2) {
    //         $(this).html('已完成')
    //     }
    // })
}

var queryUserInfo = function () {
    var url = '/get-userinfo'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            $('#phone_number').val(data.phoneNumber)
            $('#email').val(data.email)

            if (data.userType == 1) {
                $('.showif_bussiness').show()
            } else if (data.userType == 2) {
                // $('.')
            }

        } else {
            alert(res.errmsg)
        }
    })
}

var queryBuyRecords = function () {
    var url = '/buy-records'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            // $commodity_content.clear()
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $commodity_content.append(createCommodityItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var queryBussiness = function () {

}

var createCommodityItem = function (data) {
    var tmp = '<div class="shop-cart-item" commodity_id="'+data.commodityId+'">\n' +
        '        <div class="div-img">\n' +
        '            <img class="cart-commodity-img" src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-name">'+data.commodityName+'</div>\n' +
        '        <div class="cart-commodity-price" style="margin-left: -54px;"><span>￥</span>'+data.price+'</div>\n' +
        '        <div class="cart-commodity-count">\n' +
        '            <div class="position-count">\n' +
        // '                <input class="btn_num_admin btn_sub" type="button" value="-">\n' +
        '                <span class="commodity-count" style="text-align: center;">'+data.num+'</span>\n' +
        // '                <input class="btn_num_admin btn_add" type="button" value="+" />\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-totalprice"><span>￥</span><span class="price-num">'+(data.num*data.price)+'</span></div>\n' +
        '        <div class="cart-commodity-admin" style="margin-left: 32px;"><span class="status-admin" ua-id="'+data.id+'" status="'+data.orderStatis+'">'+formatOrderStatus(data.orderStatis)+'</span>\n'

    if (data.orderStatis == 1 || data.orderStatis == 2) {
        tmp +=  '           <span style="display: inline-block;margin-left: 12px;" class="btn-receipt" status="'+data.orderStatis+'">'+formatAdminText(data.orderStatis)+'</span>\n'
    }
    tmp +=
        '       </div>\n'+
        '        <div class="clr"></div>\n' +
        '    </div>'

    return $(tmp)
}

var updateUserInfo = function () {
    var url = '/update_user_info'

    var phoneNumber = $('#phone_number').val()
    var email = $('#email').val()

    var data = {}
    data.phoneNumber = phoneNumber
    data.email = email

    $.post(url, data, function (res) {
        if (res.errcode == 1) {
            alert('修改成功')
        } else {
            alert(res.errmsg)
        }
    })
}

var formatOrderStatus = function (status) {
    var orderStatus = ''
    if (status == 0) {
        orderStatus = '已下单'
    } else if (status == 1) {
        orderStatus = '正在配送'
    } else if (status == 2) {
        orderStatus = '已完成'
    }

    return orderStatus
}

var formatAdminText = function (status) {
    if (status == 1) {
        return '确认收货'
    } else if (status == 2) {
        return '添加评论'
    }
}

var updateUserPassword = function () {
    var url = '/update_user_password'

    var opassword = $('#opassword').val()
    var npassword = $('#npassword').val()
    var cpassword = $('#cpassword').val()

    if (npassword == cpassword) {
        var data = {}
        data.opassword = opassword
        data.npassword = npassword

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                alert('修改成功')
                $('#opassword').val('')
                $('#npassword').val('')
                $('#cpassword').val('')
            } else {
                alert(res.errmsg)
            }
        })
    } else {
        alert('两次密码输入不一致')
    }

}

var main = function () {
    bindAll()
}

main()