var $item_content = $('#item_content')

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

var queryBussiness = function () {

}

var createItem = function (data) {
    var tmp = '<div class="shop-cart-item" commodity_id="'+data.commodityId+'">\n' +
        '        <div class="div-check">\n' +
        '            <input type="checkbox" class="check-item" />\n' +
        '        </div>\n' +
        '        <div class="div-img">\n' +
        '            <img class="cart-commodity-img" src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-name">'+data.commodityName+'</div>\n' +
        '        <div class="cart-commodity-price"><span>￥</span>'+data.price+'</div>\n' +
        '        <div class="cart-commodity-count">\n' +
        '            <div class="position-count">\n' +
        '                <input class="btn_num_admin btn_sub" type="button" value="-">\n' +
        '                <input type="text" class="commodity-count" value="'+data.count+'" price="'+data.price+'" style="text-align: center;" />\n' +
        '                <input class="btn_num_admin btn_add" type="button" value="+" />\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-totalprice"><span>￥</span><span class="price-num">'+(data.count*data.price)+'</span></div>\n' +
        '        <div class="cart-commodity-admin"><span class="btn-remove" cart_id="'+data.cartId+'">移除</span></div>\n'+
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