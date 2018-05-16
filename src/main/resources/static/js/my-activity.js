var $activity_content = $('#activity_content')

var bindAll = function () {
    queryMyActivity()
}

var queryMyActivity = function () {
    var url = '/get-attended-activities'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            $activity_content.children().remove()
            var data = res.data

            for (var i=0;i<data.length;i++) {
                var tmp = '<div class="activity-item" activity-id="'+data[i].id+'">\n' +
                    '            <div class="activity-name">'+data[i].activityName+'</div>\n' +
                    '            <div class="create-time">'+formatTime(data[i].startTime)+'</div>\n' +
                    '        </div>'

                var $item = $(tmp)
                $item.on('click', function () {
                    window.location.href = '/attend-info?id='+$(this).attr('activity-id')
                })
                $activity_content.append($item)
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