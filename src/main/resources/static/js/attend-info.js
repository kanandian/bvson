var id = 0

var bindAll = function () {
    id = getUrlParam('id')
    queryInfo()

    $('#btn_update').on('click', function () {
        var url = '/update-useractivity-info'

        var userName = $('#user_name').val()
        var mobile = $('#mobile').val()

        var data = {}

        data.id = id
        data.userName = userName
        data.mobile = mobile

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                alert('修改成功')
            }  else {
                alert(res.errmsg)
            }
        })

    })
}

var queryInfo = function () {
    var url = '/get-useractivity-info'

    var data = {}
    data.id = id

    $.get(url, data, function (res) {
        if (res.errcode == 1) {
            var data = res.data
            $('#user_name').val(data.userName)
            $('#mobile').val(data.mobile)

            $('#img_activity').attr('src', data.imageURL)
            $('#activity_name').html(data.activityName)
            $('#activity_time').html(formatTime(data.createTime))
            $('#activity_des').html(data.des)
            $('#current_num').html(data.currentNum)
            $('#limit_num').html(data.numLimit)
            $('#start_time').html(formatTime(data.startTime))
        }  else {
            alert(res.errmsg)
        }
    })
}

var main = function () {
    bindAll()
}

main()