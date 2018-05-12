var bindAll = function () {
    queryUserInfo()

    $('#btn_update_info').on('click', function () {
        updateUserInfo()
    })

    $('#btn_update_password').on('click', function () {
        updateUserPassword()
    })
}

var queryUserInfo = function () {
    var url = '/get-userinfo'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            $('#phone_number').val(data.phoneNumber)
            $('#email').val(data.email)
        } else {
            alert(res.errmsg)
        }
    })
}

var updateUserInfo = function () {
    var url = '/update_user_info'

    var phoneNumber = $('#phone_number').val()
    var email = $('#email').val()

    var data = {}
    data.phoneNumber = phoneNumber
    data.email = email

    $.post(url, data, function (res) {
        if (res.errcode == 1) {
            alert('修改成功')
        } else {
            alert(res.errmsg)
        }
    })
}

var updateUserPassword = function () {
    var url = '/update_user_password'

    var opassword = $('#opassword').val()
    var npassword = $('#npassword').val()
    var cpassword = $('#cpassword').val()

    if (npassword == cpassword) {
        var data = {}
        data.opassword = opassword
        data.npassword = npassword

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                alert('修改成功')
                $('#opassword').val('')
                $('#npassword').val('')
                $('#cpassword').val('')
            } else {
                alert(res.errmsg)
            }
        })
    } else {
        alert('两次密码输入不一致')
    }

}

var main = function () {
    bindAll()
}

main()