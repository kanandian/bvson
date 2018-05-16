var ucid = 1

var bindAll = function () {
    ucid = sessionStorage.getItem('commodityId')
    // alert(ucid)
    $('#commodityId').val(ucid)
}

var main = function () {
    bindAll()
}

main()