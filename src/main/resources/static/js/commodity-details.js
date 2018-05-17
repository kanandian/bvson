var commodityId = 10

var $input_count = $('#commodity_count')
var $comment_content = $('#comment_content')

var bindAll = function () {
    commodityId = getUrlParam('commodityId')
    queryCommodityInfo()
    queryCommentsInfo()

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

    $('#btn_buy').on('click', function () {
        var count = $input_count.val()

        var ids = []
        var nums = []

        ids.push(commodityId)
        nums.push(count)

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

var queryCommentsInfo = function () {
    var url = '/get-comments'

    var data = {}
    data.commodityId = commodityId
    $.get(url, data, function (res) {
        if (res.errcode == 1) {
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $comment_content.append(createCommentItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var createCommentItem = function (data) {
    var tmp = '<div class="comment-item">\n' +
        '                    <div class="comment-user-name">'+data.userName+'</div>\n' +
        '                    <div class="div-content">\n' +
        '                        <div class="content-text">'+data.content+'</div>\n' +
        '                        <div class="content-img">\n' +
        '                            <img src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '                        </div>\n' +
        '                        <div class="content-time">'+formatTime(data.commentTime)+'</div>\n' +
        '                    </div>\n' +
        '                </div>'

    return $(tmp)
}


var main = function () {
    bindAll()
}

main()