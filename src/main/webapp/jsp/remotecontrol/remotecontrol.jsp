<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/static/common/tagLibs.jsp" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>远程控制</title>
    <style>
        /*body {
            margin: 0;
            padding: 0;
        }*/

        #p1, #p2 {
            height: 20px;
        }

        #move_div {
            cursor: pointer;
            display: inline-block;
            margin-left: 100px;
            background-color: gray;
            /* border: 1px solid #336699;*/
        }

        #move_div2 {
            margin-top: -200px;
            width: 360px;
            height: 600px;
            background: url(static/OUT1/sn-1.jpg) no-repeat;
        }

        /*        .video-back {
                    display: block;
                    margin-left: 192px;
                    margin-top: 21px;
                    width: 420px;
                    height: 700px;

                }*/
        .form {
            width: 200px;
            border: 1px solid #ccc;
            padding: 5px;
        }

    </style>
    <script src="http://www.jq22.com/jquery/jquery-1.10.2.js"></script>
    <script src="http://vjs.zencdn.net/5.20.1/video.js"></script>
    <link href="http://vjs.zencdn.net/5.20.1/video-js.css" rel="stylesheet">

    <%--<script src="http://vjs.zencdn.net/5.20.1/videojs-ie8.min.js"></script>--%>

    <%-- <script src="res/video/dist/jquery.vidbacking.js" type="text/javascript"></script>--%>

    <%-- <script src="https://cdnjs.cloudflare.com/ajax/libs/socket.io/2.1.1/socket.io.js"></script>--%>
    <script>
        //var socket =new WebSocket('ws://10.20.10.86:8899');
        // socket.send("123456");
        /*离开页面断开连接*/
        function checkLeave() {
            //event.returnValue="确定离开当前页面吗？";
            del();
        }
    </script>

    <script>
        var move_div;//要操作的div对象
        var m_move_x, m_move_y, m_down_x, m_down_y;

        //鼠标按下
        function down() {
            move_div = document.getElementById('move_div');
            //获取鼠标按下时坐标
            m_down_x = event.pageX - getLeft(move_div);//此处move_div只能用原生获取,因为封装的方法为原生写法,
            m_down_y = event.pageY - getTop(move_div);
            $("#p1").text('按下坐标:X:' + m_down_x + 'Y:' + m_down_y);
            $('#coordx').text(m_down_x);
            $('#coordy').text(m_down_y);
        }

        //鼠标移动
        function move() {
            move_div = document.getElementById('move_div');

            //获取鼠标移动实时坐标
            m_move_x = event.pageX - getLeft(move_div);
            m_move_y = event.pageY - getTop(move_div);

        }

        //鼠标释放
        function up() {
            $("#p2").text('弹起坐标:X:' + m_move_x + 'Y:' + m_move_y);
            $('#xx').text(m_move_x);
            $('#yy').text(m_move_y);
            end(2);
        }

        //封装页面元素到顶部的距离
        function getTop(obj) {
            if (obj.offsetParent == null) {
                //obj是body时,停止
                return 0;
            }
            return obj.offsetTop + obj.offsetParent.clientTop + getTop(obj.offsetParent);
        }

        //封装页面元素到顶部的距离
        function getLeft(obj) {
            if (obj.offsetParent == null) {
                //obj是body时,停止
                return 0;
            }
            return obj.offsetLeft + obj.offsetParent.clientLeft + getLeft(obj.offsetParent);
        }
    </script>
    <script type="text/javascript">
        /*连接socket*/
        function get() {
            if ($('#appport').val() != ""&&$('#pcport').text() == "") {
                if ($('#pcport').text() == "") {
                    $.ajax({
                        type: "POST",
                        url: "RemotecontrolController/socket",
                        success: function (data) {
                            var dsn = $('#dsn').val();
                            $('#pcport').text(data.data);
                            $('#ele').text("已连接成功");
                            var src = "rtmp://122.114.251.98:1935/mylive/" + dsn;
                            $("#move_div2").hide();
                            play(src);//拉流
                            setTimeout("Reconnect()", 2000); //延时2s让设备重新推流

                        },
                        error: function () {
                            alert("连接方法执行不成功!");
                        }
                    }, "JSON");
                } else {
                    alert("当前已有连接请断开!");
                    $('#ele').text("当前已有连接请断开!");
                }
            } else {
                $('#ele').text("appport不能为空!");
            }
        }
    </script>

    <script type="text/javascript">
        /*让设备重新推流*/
        function Reconnect() {
            $.ajax({
                type: "POST",
                url: "RemotecontrolController/load",
                data: {
                    type: 1,
                    bx: $('#coordx').text(),
                    by: $('#coordy').text(),
                    ex: $('#xx').text(),
                    ey: $('#yy').text(),
                    pcport: $('#pcport').text(),
                    appport: $('#appport').val()
                },
                success: function (data) {
                    alert("设备重新");
                    //$("#move_div2").show();
                },
                error: function () {
                    alert("断开方法执行不成功!");
                }
            }, "JSON");
        }
    </script>
    <script type="text/javascript">
        /*断开socket*/
        function del() {
            if ($('#appport').val() != "" && $('#pcport').text() != "") {
                $.ajax({
                    type: "POST",
                    url: "RemotecontrolController/load",
                    data: {
                        type: 3,
                        bx: $('#coordx').text(),
                        by: $('#coordy').text(),
                        ex: $('#xx').text(),
                        ey: $('#yy').text(),
                        pcport: $('#pcport').text(),
                        appport: $('#appport').val()
                    },
                    success: function (data) {
                        //$("#move_div2").show();
                        $('#pcport').text("");
                        $('#appport').val("");
                        $('#dsn').val("");
                        $('#ele').text("断开执行成功已断开连接");
                    },
                    error: function () {
                        alert("断开方法执行不成功!");
                    }
                }, "JSON");
            } else {
                $('#ele').text("当前没有连接");
            }
        }
    </script>
    <script type="text/javascript">
        /*发送坐标*/
        function end(a) {
            if ($('#appport').val() != "" && $('#pcport').text() != "") {
                $.ajax({
                    type: "POST",
                    url: "RemotecontrolController/load",
                    data: {
                        type: a,
                        bx: $('#coordx').text(),
                        by: $('#coordy').text(),
                        ex: $('#xx').text(),
                        ey: $('#yy').text(),
                        pcport: $('#pcport').text(),
                        appport: $('#appport').val()
                    },
                    success: function (data) {
                        $('#ele').text(data.data);
                        if (data.data == false) {
                            $('#pcport').text("");
                            $('#appport').val("");
                            $('#dsn').val("");
                            $('#ele').text("pc断开请重新连接");
                            // get();
                        }
                    },
                    error: function () {
                        alert("方法执行不成功!");
                    }
                }, "JSON");
            } else {
                $('#ele').text("appport不能为空!");
            }
        }

    </script>


    <script type="text/javascript">
        /*动态下拉框*/
        function IninDepart() {
            if ($('#pcport').text() == "") {
                $('#mySelect').empty();//清空select列表数据
                $.ajax({
                    type: "POST",
                    url: "RemotecontrolController/getRemotecontrol",
                    dataType: "JSON",
                    data: {},
                    success: function (msg) {
                        $('#mySelect').append("<option value=''>请选择设备</option>");//添加第一个option值
                        for (var i = 0; i < msg.length; i++) {

                            //如果在select中传递其他参数，可以在option 的value属性中添加参数
                            //$("#selectSM").append("<option value='"+msg.rows[i].id+"'>"+msg.rows[i]+"</option>");

                            $('#mySelect').append("<option value='" + msg[i].appport + "' >" + msg[i].serialnumber + "</option>");
                        }

                    }, error: function () {
                        alertLayer("获取数据失败", "error");
                    }, async: false             //false表示同步
                });
            } else {
                $('#ele').text("当前正在连接!");
            }
        }

        function gradeChange() {
            if ($('#pcport').text() == "") {
                var appprot = $("#mySelect").val();//获取选中项的value
                var dsn = $("#mySelect").find("option:selected").text();
                $('#appport').val(appprot);
                $('#dsn').val(dsn);
            } else {
                $('#ele').text("当前正在连接!");
            }
        }

    </script>

</head>
<body onbeforeunload="checkLeave()" onload="IninDepart()">
<div id="pcport" style="width:200px;height:20px;border:5px solid #336699;"></div>
<input type="text" name="appport" id="appport" readonly="readonly">
<input type="text" name="dsn" id="dsn" readonly="readonly">
<br>
<br>
<div class="form">
    <select id="mySelect" class="select" onchange="gradeChange()" onclick="IninDepart()">
    </select>

</div>
<br>
<br>
<button onclick="get()">连接</button>
<br>
<button onclick="del()">断开</button>
<br>
<button onclick="end(5)">获取连接列表</button>

<div id="ele" style="width:300px;height:20px;border:2px solid #336699;">当前未连接，请链接</div>
<div id="coordx" style="width:300px;border:2px solid #336699;">x</div>
<div id="coordy" style="width:300px;border:2px solid #336699;">y</div>

<div id="xx" style="width:300px;border:2px solid #336699;">x x</div>
<div id="yy" style="width:300px;border:2px solid #336699;">y y</div>

<p id="p1"></p>
<p id="p2"></p>
<div id="move_div" onmousedown="down()" onmouseup="up()" onmousemove="move()" onselectstart="return false">
    <%-- data-setup="{}"页面准备完成即自动加载      autoplay自动播放muted静音loop环路  controls浏览器自带的控制条--%>
    <video id="videoid" muted loop  <%--poster="OUT1/sn-1.jpg"--%> >
        <source src="" type="rtmp/flv">
    </video>
    <div id="move_div2">

    </div>
</div>

<%--
<img id="banner" src="OUT1/sn-0.jpg" alt="banner" height="520px" width="340px" ondragstart="return false">
--%>

</body>
<script type="text/javascript">
    function play(src) {
        videojs("videoid").ready(function () {
            var myPlayer = this;
            myPlayer.src(src);
            myPlayer.load();//重新加载
            myPlayer.play();//开始播放
        });
    }
</script>
</html>
