var $item_content = $('#item_content')
var $commodity_content = $('#commodity_content')
var $item_content = $('#item_content')


var $commodity_admin_content = $('#commodity_admin_content')

var $user_admin_content = $('#user_admin_content')

var myChart = echarts.init(document.getElementById('statistic_chat'))

var bindAll = function () {
    queryUserInfo()

    $('#btn_update_info').on('click', function () {
        updateUserInfo()
    })

    $('#btn_update_password').on('click', function () {
        updateUserPassword()
    })

    $('#shop_cart').on('click', function () {
        window.location.href='/shopping-cart'
    })
    $('#attend_activity').on('click', function () {
        window.location.href='/attend-activity'
    })

    $('#my_activity').on('click', function () {
        window.location.href = '/my-activity'
    })

    $('#buy_records').on('click', function () {
        queryBuyRecords()

        // $('.btn-receipt').each(function () {
        //     var status = $(this).attr('status')
        //     if (status == 1) {
                $(this).show()
                // $(this).css('display', 'inline-block')
            // }
        // })
    })

    $('#statistic').on('click', function () {
        showChat()
    })

    $('body').on('click', '.layui-form-item', function () {
        footerPosition()
    })

    $('body').on('click', '.btn-receipt', function () {
        var status = parseInt($(this).attr('status'))
        var id = ($(this).attr('sid'))
        var $btn = $(this)
        if (status == 1) {
            var url = '/comfirm-receipt'
            var data = {}
            data.id = id
            $.post(url, data, function (res) {
                if (res.errcode == 1) {
                    $btn.attr('status', 2)
                    $btn.html('添加评论')
                    $btn.parent().children(':eq(0)').html('已完成')
                } else {
                    alert(res.errmsg)
                }
            })

        } else if (status == 2) {
            var id = ($(this).attr('commodity_id'))
            sessionStorage.setItem('commodityId', id)
            window.location.href = '/add-comment'
        } else if (status == 0) {
            var url = '/distribution'
            var data = {}
            data.id = id

            $.post(url, data, function (res) {
                if (res.errcode == 1) {
                    $btn.attr('status', 1)
                    $btn.hide()
                    $btn.parent().children(':eq(0)').html('已发货')
                } else {
                    alert(res.errmsg)
                }
            })



        }
    })

    $('#bussiness_order').on('click', function () {
        queryBussinessUserCommodity()
    })

    $('#user_admin').on('click', function () {
        queryUserAdmin()
    })

    $('#commodity_admin').on('click', function () {
        queryCommodityAdmin()
    })

    $('body').on('click', '.admin-btn-delete-user', function () {
        var url = '/remove-user'
        var $btn = $(this)
        var userId = $btn.attr('user_id')

        var data = {}
        data.userId = userId
        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                $btn.parent().remove()
            } else {
                alert(res.errmsg)
            }
        })
    })

    $('body').on('click', '.btn-admin-delete-commodity', function () {
        var url = '/remove-commodity'

        var $btn = $(this)
        var commodityId = $btn.attr('commodity_id')
        var data = {}
        data.commodityId = commodityId

        $.post(url, data, function (res) {
            if (res.errcode == 1) {
                $btn.parent().remove()
            } else {
                alert(res.errmsg)
            }
        })
    })


    // $('body').on('click', '.status-admin', function () {
    //     var status = $(this).attr('status')
    //
    //     if (status == 0) {
    //         $(this).html('已下单')
    //     } else if (status == 1) {
    //         $(this).html('正在配送')
    //     } else if (status == 2) {
    //         $(this).html('已完成')
    //     }
    // })
}

var queryUserInfo = function () {
    var url = '/get-userinfo'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data

            $('#phone_number').val(data.phoneNumber)
            $('#email').val(data.email)

            if (data.userType == 1) {
                $('.showif_bussiness').show()
            } else if (data.userType == 2) {
                $('.showif_admin').show()
            } else if (data.userType == 0) {
                $('.showif_user').show()
            }

        } else {
            alert(res.errmsg)
        }
    })
}

var queryCommodityAdmin = function () {
    var url = '/get-commodities-admin'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            $commodity_admin_content.children().remove()
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $commodity_admin_content.append(createCommodityAdminItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var queryUserAdmin = function () {
    var url = '/get-users-admin'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            $user_admin_content.children().remove()
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $user_admin_content.append(createUserInfoItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var createUserInfoItem = function (data) {
    var tmp = '<div class="user-info-item">\n' +
        '            <div class="admin-user-name">'+data.userName+'</div>\n' +
        '            <div class="admin-btn-delete-user" user_id="'+data.userId+'">删除</div>\n' +
        '      </div>'

    return $(tmp)
}

var queryBuyRecords = function () {
    var url = '/buy-records'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            $commodity_content.children().remove()
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $commodity_content.append(createCommodityItem(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var queryBussinessUserCommodity = function () {
    var url = '/sold-records'

    $.get(url, function (res) {
        if (res.errcode == 1) {
            var data = res.data
            for (var i=0;i<data.length;i++) {
                $item_content.append(createCommodityItemByBussiness(data[i]))
            }
        } else {
            alert(res.errmsg)
        }
    })
}

var createCommodityAdminItem = function (data) {
    var tmp = '<div class="shop-cart-item" commodity_id="'+data.commodityId+'">\n' +
        '        <div class="div-img">\n' +
        '            <img class="cart-commodity-img" src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-name">'+data.commodityName+'</div>\n' +
        '        <div class="cart-commodity-price"><span>￥</span>'+data.price+'</div>\n' +
        '           <span style="display: inline-block;float:right;margin-right: 12px;" commodity_id="'+data.commodityId+'" class="btn-admin-delete-commodity" sid="'+data.id+'" status="'+data.orderStatis+'">删除</span>\n' +
        '       </div>\n'+
        '        <div class="clr"></div>\n' +
        '    </div>'

    return $(tmp)
}

var createCommodityItem = function (data) {
    var tmp = '<div class="shop-cart-item" commodity_id="'+data.commodityId+'">\n' +
        '        <div class="div-img">\n' +
        '            <img class="cart-commodity-img" src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-name">'+data.commodityName+'</div>\n' +
        '        <div class="cart-commodity-price" style="margin-left: -54px;"><span>￥</span>'+data.price+'</div>\n' +
        '        <div class="cart-commodity-count">\n' +
        '            <div class="position-count">\n' +
        // '                <input class="btn_num_admin btn_sub" type="button" value="-">\n' +
        '                <span class="commodity-count" style="text-align: center;">'+data.num+'</span>\n' +
        // '                <input class="btn_num_admin btn_add" type="button" value="+" />\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-totalprice"><span>￥</span><span class="price-num">'+(data.num*data.price)+'</span></div>\n' +
        '        <div class="cart-commodity-admin" style="margin-left: 32px;"><span class="status-admin" ua-id="'+data.id+'" status="'+data.orderStatis+'">'+formatOrderStatus(data.orderStatis)+'</span>\n'

    if (data.orderStatis == 1 || data.orderStatis == 2) {
        tmp +=  '           <span style="display: inline-block;margin-left: 12px;" commodity_id="'+data.commodityId+'" class="btn-receipt" sid="'+data.id+'" status="'+data.orderStatis+'">'+formatAdminText(data.orderStatis)+'</span>\n'
    }
    tmp +=
        '       </div>\n'+
        '        <div class="clr"></div>\n' +
        '    </div>'

    return $(tmp)
}

var createCommodityItemByBussiness = function (data) {
    var tmp = '<div class="shop-cart-item" commodity_id="'+data.commodityId+'">\n' +
        '        <div class="div-img">\n' +
        '            <img class="cart-commodity-img" src="'+data.imageURL+'" width="88px" height="80px" />\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-name">'+data.commodityName+'</div>\n' +
        '        <div class="cart-commodity-price" style="margin-left: -54px;"><span>￥</span>'+data.price+'</div>\n' +
        '        <div class="cart-commodity-count">\n' +
        '            <div class="position-count" style="width: 100%;text-align: right;">\n' +
        // '                <input class="btn_num_admin btn_sub" type="button" value="-">\n' +
        '                <span class="commodity-count" style="text-align: center;">'+data.num+'</span>\n' +
        // '                <input class="btn_num_admin btn_add" type="button" value="+" />\n' +
        '            </div>\n' +
        '        </div>\n' +
        '        <div class="cart-commodity-totalprice"><span>￥</span><span class="price-num">'+(data.num*data.price)+'</span></div>\n' +
        '        <div class="cart-commodity-admin" style="margin-left: 32px;"><span class="status-admin" ua-id="'+data.id+'" status="'+data.orderStatus+'">'+formatOrderStatus(data.orderStatus)+'</span>\n'

    if (data.orderStatus == 0) {
        tmp +=  '           <span style="display: inline-block;margin-left: 12px;" commodity_id="'+data.commodityId+'" class="btn-receipt" sid="'+data.id+'" status="'+data.orderStatus+'">发货</span>\n'
    }
    tmp +=
        '       </div>\n'+
        '        <div class="clr"></div>\n' +
        '    </div>'

    return $(tmp)
}

var showChat = function () {
    var url = '/sold-statistics'
    $.get(url, function (res) {
        if (res.errcode == 1) {
            var statistic = res.data

            var dataAxis = []
            var data = []
            var yMax = 0
            var dataShadow = []

            Object.keys(statistic).forEach(function (key) {
                dataAxis.push(key)
                data.push(statistic[key])
                if (statistic[key] > yMax) {
                    yMax = statistic[key]
                }
            })



            for (var i = 0; i < data.length; i++) {
                dataShadow.push(yMax);
            }

            var option = {
                title: {
                    text: '商品出售统计',
                    // subtext: 'Feature Sample: Gradient Color, Shadow, Click Zoom'
                },
                xAxis: {
                    data: dataAxis,
                    axisLabel: {
                        inside: false,
                        textStyle: {
                            color: '#000'
                        }
                    },
                    axisTick: {
                        show: false
                    },
                    axisLine: {
                        show: false
                    },
                    z: 10
                },
                yAxis: {
                    axisLine: {
                        show: false
                    },
                    axisTick: {
                        show: false
                    },
                    axisLabel: {
                        textStyle: {
                            color: '#999'
                        }
                    }
                },
                dataZoom: [
                    {
                        type: 'inside'
                    }
                ],
                series: [
                    { // For shadow
                        type: 'bar',
                        itemStyle: {
                            normal: {color: 'rgba(0,0,0,0.05)'}
                        },
                        barGap:'-100%',
                        barCategoryGap:'40%',
                        data: dataShadow,
                        animation: false
                    },
                    {
                        type: 'bar',
                        itemStyle: {
                            normal: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        {offset: 0, color: '#83bff6'},
                                        {offset: 0.5, color: '#188df0'},
                                        {offset: 1, color: '#188df0'}
                                    ]
                                )
                            },
                            emphasis: {
                                color: new echarts.graphic.LinearGradient(
                                    0, 0, 0, 1,
                                    [
                                        {offset: 0, color: '#2378f7'},
                                        {offset: 0.7, color: '#2378f7'},
                                        {offset: 1, color: '#83bff6'}
                                    ]
                                )
                            }
                        },
                        data: data
                    }
                ]
            };

            myChart.setOption(option)

// Enable data zoom when user click bar.
            var zoomSize = 6;
            myChart.on('click', function (params) {
                console.log(dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)]);
                myChart.dispatchAction({
                    type: 'dataZoom',
                    startValue: dataAxis[Math.max(params.dataIndex - zoomSize / 2, 0)],
                    endValue: dataAxis[Math.min(params.dataIndex + zoomSize / 2, data.length - 1)]
                });
            });

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

var formatOrderStatus = function (status) {
    var orderStatus = ''
    if (status == 0) {
        orderStatus = '已下单'
    } else if (status == 1) {
        orderStatus = '正在配送'
    } else if (status == 2) {
        orderStatus = '已完成'
    }

    return orderStatus
}

var formatAdminText = function (status) {
    if (status == 1) {
        return '确认收货'
    } else if (status == 2) {
        return '添加评论'
    }
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