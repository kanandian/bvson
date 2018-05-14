var $activity_content = $('#activity_content')

var bindAll = function () {
    queryCommodities()

    $('body').on('click', '.btn-delete', function () {
        var url = '/remove-activity'
        var activityId = parseInt($(this).attr('activity_id'))

        var $btn = $(this)

        var data = {}
        data.activityId = activityId

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                $btn.parent().parent().remove()
            } else {
                alert(res.errmsg)
            }
        })

    })
}

var createItem = function (data) {
    var tmp = '<div class="commodity-item">\n' +
        '        <div class="div-img">\n' +
        '            <img class="commodity-img" width="100" height="64" src="'+data.imageURL+'" />\n' +
        '        </div>\n' +
        '        <div class="commodity-name">'+data.activityName+'</div>\n' +
        '        <div class="create-time"><span>'+formatTime(data.createTime)+'</span></div>\n' +
        '        <div class="btn-admin"><a class="btn-delete" href="javascript:;" activity_id="'+data.activityId+'">删除</a></div>\n' +
        '        <div class="clr"></div>\n' +
        '    </div>'

    return $(tmp)
}

var queryCommodities = function () {
    var url = '/get-activities'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            for (var i=0;i<data.length;i++) {
                $activity_content.append(createItem(data[i]))
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