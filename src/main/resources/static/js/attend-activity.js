var $activity_content = $('#activity_content')

var bindAll = function () {
    queryActivitiesInfo()
}


var queryActivitiesInfo = function() {
    var url = '/get-activities'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            for (var i=0;i<data.length;i++) {
                var tmp = '<div class="activity-item" activity-id="'+data[i].activityId+'">\n' +
                    '            <div class="activity-name">'+data[i].activityName+'</div>\n' +
                    '            <div class="create-time">'+formatTime(data[i].createTime)+'</div>\n' +
                    '        </div>'

                var $item = $(tmp)
                $item.on('click', function () {
                    window.location.href = '/activity-details?activityId='+$(this).attr('activity-id')
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