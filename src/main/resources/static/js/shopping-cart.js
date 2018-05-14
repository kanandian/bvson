var $item_content = $('#item_content')
var $cart_count = $('#cart_count')
var $cart_price = $('#cart_price')

var bindAll = function () {
    queryShopCart()


    $('body').on('click', '.btn_add', function () {
        addCount($(this))
    })

    $('body').on('click', '.btn_sub', function () {
        subCount($(this))
        // alert(1)
    })

    $('body').on('click', '.check-item', function () {
        var totalPrice = 0.0
        var cnt = 0
        $item_content.children().each(function () {
            if (checked($(this))) {
                totalPrice += getCartPrice($(this))
                cnt++
            }
        })
        $cart_count.html(cnt)
        $cart_price.html(totalPrice)
    })

    $('body').on('click', '.btn-remove', function () {
        var url = '/remove-shop-cart'

        var cartId = $(this).attr('cart_id')
        var data = {}
        data.cartId = cartId

        $btn_remove = $(this)

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                $btn_remove.parent().parent().remove()
            } else {
                alert(res.errmsg)
            }
        })
    })


        $('#btn_account').on('click', function () {
            var ids = []
            var nums = []
            $item_content.children().each(function () {
                if (checked($(this))) {
                    ids.push($(this).attr('commodity_id'))
                    nums.push(getCount($(this)))
                }
            })

            var data = {}
            data.ids = ids.join(',')
            data.nums = nums.join(',')

            var url = '/buy-commodities'
            $.post(url, data, function (res) {
                if (res.errcode == 1) {
                    window.location.href = '/payment'
                } else {
                    alert(res.errmsg)
                }
            })
        })

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

    return tmp
}

var queryShopCart = function () {
    var url = '/get-shop-cart'
    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $item_content.append(createItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var addCount = function ($btn) {
    var $div = $btn.parent()
    var $count = $div.children(':eq(1)')

    var count = parseInt($count.val())
    count++
    $count.val(count)

    var totalPrice = parseFloat($count.attr('price'))*count
    $btn.parent().parent().next().children(':eq(1)').html(totalPrice)
}

var checked = function ($div) {
    var $check = $div.children('.div-check').children('.check-item')
    return $check.prop('checked')
}

var getCartPrice = function ($div) {
    var $price = $div.children('.cart-commodity-totalprice').children('.price-num')
    return parseFloat($price.html())
}

var getCount = function ($div) {
    var $count = $div.children('.cart-commodity-count').children('.position-count').children('.commodity-count')
    return parseInt($count.val())
}

var subCount = function ($btn) {
    var $count = $btn.next()
    var count = parseInt($count.val())

    if (count > 1) {
        count--
    }
    $count.val(count)

    var totalPrice = parseFloat($count.attr('price'))*count
    $btn.parent().parent().next().children(':eq(1)').html(totalPrice)
}

var main = function () {
    bindAll()
}

main()