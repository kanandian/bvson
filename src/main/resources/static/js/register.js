var $div_username = $('#user_name')
var $div_password = $('#password')
var $div_cpassword = $('#check_password')

var $btn_register = $('#btn_register')

function bindAll() {
    $btn_register.on('click', function () {
        register()
    })
}

function register() {
    var username = $div_username.val()
    var password = $div_password.val()
    var cpassword = $div_cpassword.val()

    var usertype = $('input:radio:checked').val()

    if (password == cpassword) {
        var url = '/register'
        var data = {}
        data.userName = username
        data.password = password
        data.userType = usertype

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                window.location.href = '/'
            }
        })
    } else {
        $.alert('两次密码输入不一致')
    }

}


function main() {
    bindAll()
}

main()