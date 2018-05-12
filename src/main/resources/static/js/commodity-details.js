var commodityId = 10

var $input_count = $('#commodity_count')

var bindAll = function () {
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

var main = function () {
    bindAll()
}

main()