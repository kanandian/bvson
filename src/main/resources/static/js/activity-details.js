var activityId = 10

var $input_count = $('#commodity_count')

var bindAll = function () {
    activityId = getUrlParam('activityId')
    queryActivityInfo()

    $('#btn_attend_activity').on('click', function () {
        var url = '/signup-activity'

        var data = {}
        data.activityId = activityId

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                alert('报名成功')
            } else {
                alert(res.errmsg)
            }
        })
    })

}

var queryActivityInfo = function () {
    var url = '/get-activity-info'

    var data = {}
    data.activityId = activityId

    $.get(url, data, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            $('#img_activity').attr('src', data.imageURL)
            $('#activity_name').html(data.activityName)
            $('#activity_time').html(formatTime(data.createTime))
            $('#activity_des').html(data.des)
            $('#current_num').html(data.currentNum)
            $('#limit_num').html(data.numLimit)
            $('#start_time').html(formatTime(data.startTime))
        } else {
            alert(res.errmsg)
        }
    })
}


var main = function () {
    bindAll()
}

main()