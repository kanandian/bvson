var commodityId = 10

var $input_count = $('#commodity_count')

var bindAll = function () {
    commodityId = getUrlParam('commodityId')
    queryCommodityInfo()

    $('#btn_add').on('click', function () {
        var count = parseInt($input_count.val())

        $input_count.val(count+1)
    })

    $('#btn_sub').on('click', function () {
        var count = parseInt($input_count.val())

        if (count > 1) {
            $input_count.val(count-1)
        }
    })

    $('#btn_add_cart').on('click', function () {
        var count = parseInt($input_count.val())

        var url = '/add-shop-cart'
        var data = {}
        data.commodityId = commodityId
        data.num = count

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                alert('添加成功')
            } else {
                alert(res.errmsg)
            }
        })
    })
}

var queryCommodityInfo = function () {
    var url = '/commodity-info'

    var data = {}
    data.commodityId = commodityId

    $.get(url, data, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            $('#img_commodity').attr('src', data.imageURL)
            $('#commodity_name').html(data.commodityName)
            $('#commodity_price').html(data.price)
            $('#commodity_des').html(data.des)
        } else {
            alert(res.errmsg)
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

var main = function () {
    bindAll()
}

main()