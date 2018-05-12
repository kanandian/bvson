var $input_username = $('#user_name')
var $input_password = $('#password')

var $btn_login = $('#btn_login')

var bindAll = function () {
    $btn_login.on('click', function () {
        login()
    })

    $('#btn_register').on('click', function () {
        window.location.href = 'register'
    })
}

var login = function () {
    var userName = $input_username.val()
    var password = $input_password.val()

    var url = '/login'

    var data = {}
    data.userName = userName
    data.password = password

    $.post(url, data, function (res) {
        if(res.errcode == 1) {

            window.location.href = '/'
        } else {
            // $.alert(res.errmsg)
            alert(res.errmsg)
        }
    })
}

var main = function () {
    bindAll()
}

main()