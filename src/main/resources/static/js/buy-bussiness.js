var $bussinesses_content = $('#bussinesses_content')

var bindAll = function () {
    queryBussinessesInfo()

    $('body').on('click', '.bussiness-item', function () {
        var commodityId = $(this).attr('commodity_id')
        window.location.href = '/commodity-details?commodityId='+commodityId
    })
}

var queryBussinessesInfo = function () {
    var url = '/get-commodities'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            for (var i=0;i<data.length;i++) {
                var commodityId = data[i].commodityId
                var tmp = '<div class="bussiness-item" commodity_id="'+commodityId+'">\n' +
                    '            <img src="'+data[i].imageURL+'" class="bussiness-image" width="100px" height="64px" />\n' +
                    '            <div class="bussiness-name" style="margin-top: 6px;margin-left: 18px;">'+data[i].commodityName+'</div>\n' +
                    '            <div class="bussiness-price" style="margin-left: 18px;"><span>￥</span><span class="bussiness-price-number">'+data[i].price+'</span></div>\n' +
                    '        </div>'

                var $item = $(tmp)
                // $item.on('click', function () {
                //     window.location.href = '/commodity-details?commodityId='+commodityId
                // })
                $bussinesses_content.append($item)
            }

        } else {
            alert(res.errmsg)
        }
    })
}

var main = function () {
    bindAll()
}

main()