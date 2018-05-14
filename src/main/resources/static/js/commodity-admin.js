var bindAll = function () {
    queryCommodities()
}

var queryCommodities = function () {
    var url = '/get-commodities-bussiness'

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