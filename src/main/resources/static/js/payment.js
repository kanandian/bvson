var bindAll = function () {
    // queryPayment()

    $('#btn_payment').on('click', function () {
        var userName = $('#user_name').val()
        var mobile = $('#mobile').val()
        var address = $('#address').val()

        var url = '/payment'

        var data = {}
        data.userName = userName
        data.mobile = mobile
        data.address = address

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                window.location.href = '/personal-center'
            } else {
                alert(res.errmsg)
            }

        })
    })
}

var queryPayment = function () {
    var url = 'get-payment'
    $.get(url, function (res) {
        if (res.errcode == 1) {

        } else {
            alert(res.errmsg)
        }

    })
}

var main = function () {
    bindAll()
}

main()