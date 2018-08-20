<%--
  Created by IntelliJ IDEA.
  User: Administrator
  Date: 2018/7/30
  Time: 18:39
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/static/common/tagLibs.jsp" %>
<html>
<head>
    <title>登陆页面</title>
</head>
<body>


<div class=wrap id=wrap>
    <div class=wrapper>
        <div class=header>
            <div class="head clearfix">
                <div class=logo_box>
                    <h1 class=hidden>yueylong专属</h1>
                    <a href='javascript:;' class=logo_link><img src="static/picture/logo.png" alt=xxx></a>
                </div>
                <div class=nav_box id=nav_box>
                    <ul>
                        <li>
                            <a href="javascript:void(0)" onclick="_loginModal()">登录</a>
                        </li>
                        <li>
                            <a href="javascript:void(0)">注册</a>
                        </li>
                    </ul>
                    <span class=ic_line></span>
                </div>
            </div>
        </div>
        <div class=page_wp id=page_wp>
            <div class="page page_1"><span class="page_bg scale_box"></span>
                <div class=img_box><img src="" alt=""></div>
                <div class="txt_box scale_box">
                    <h2>yueylong专属</h2>
                    <p class=txt_brief>生命的路上，耐心使你获得力量，耐心使你认清方向;耐心使你坦途疾进，耐心使你少遭波浪。寻着古往今来的路，在耐心的帮助下看生活。让我们手握耐心给我们的耐得寂寞的意志和品质，脚踩耐心给我们的超出凡俗的雄心壮志，载着耐心给我们的永不服输的信念，向生活中的成功，出发吧!</div>
            </div>
            <div class="page page_2"><span class="page_bg scale_box"></span>
                <div class=img_box><img src="" alt=""></div>
                <div class="txt_box scale_box">
                    <h2>yueylong专属</h2>
                    <p class=txt_brief>不要将过去看成是寂寞的，因为这是再也不会回头的。应想办法改善现在，因为那就是你，毫不畏惧地鼓起勇气向着未来前进。</div>
            </div>
            <div class="page page_3"><span class="page_bg scale_box"></span>
                <div class=img_box><img src="" alt=""></div>
                <div class="txt_box scale_box">
                    <h2>yueylong专属</h2>
                    <p class=txt_brief>
                        机会对于任何人都是公平的，它在我们身边的时候，不是打扮的花枝招展，而是普普通通的，根本就不起眼。看起来耀眼的机会不是机会，是陷阱;真正的机会最初都是朴素的，只有经过主动与勤奋，它才变得格外绚烂。
                </div>
            </div>
            <div class="page page_4"><span class="page_bg scale_box"></span>
                <div class=img_box><img src="" alt=""></div>
                <div class="light_wp scale_box"><span class=light_box><i class=light_1></i> <i class=light_2></i> <i
                        class=light_3></i> <i class=light_4></i> <i class=light_5></i></span></div>
                <span class="meteor_box scale_box"></span>
                <div class="txt_box scale_box">
                    <h2>yueylong专属</h2>
                    <p class=txt_brief>
                        人生就是一出戏，怎么演都是练好滴，导演就是咱自己，想怎么演都是可以滴，节拍快慢是咱可以拿捏滴，不要以为自己有问题，不努力是不行滴，遇到坎坷莫消极，需要耐心对待滴。
                </div>
            </div>
        </div>
        <div class="star_wp scale_box" id=star_wp><span class=star_bg></span> <span class=star_box></span></div>
        <canvas id=canvas></canvas>

        <div class=btn_control id=btn_control>
            <a href=javascript:; class=cur></a>
            <a href=javascript:;></a>
            <a href=javascript:;></a>
            <a href=javascript:;></a>
        </div>
        <div class=footer>
            <div class=foot>
                <p>© 2014-2017 yueylong 版权所有</div>
        </div>
    </div>
</div>

<div class="modal fade" id="myModal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog" style="width:550px;">
        <div class="modal-content" style="background-color:#000;background:rgba(0, 0, 0, 0.5);">
            <div class="modal-body">
                <button class="close" data-dismiss="modal">
                    <span>&times;</span>
                </button>
                <div class="modal-title">
                    <h1 class="text-center" style="color:white;">登录</h1>
                </div>
                <div class="modal-body">
                    <form class="form-group" action="">
                        <div class="form-group">
                            <label for="" style="color:white;">用户名</label>
                            <input class="form-control" type="text" placeholder="" style="background-color:#000;background:rgba(0, 0, 0, 0.5);">
                        </div>
                        <div class="form-group">
                            <label for="" style="color:white;">密码</label>
                            <input class="form-control" type="password" placeholder=""
                                   style="color:white;background-color:#000;background:rgba(0, 0, 0, 0.5);">
                        </div>
                        <div class="text-right">
                            <button class="btn btn-primary" type="submit">登录</button>
                            <button class="btn btn-danger" data-dismiss="modal">取消</button>
                        </div>
                        <a href="" data-toggle="modal" data-dismiss="modal" data-target="#register">还没有账号？点我注册</a>
                    </form>
                </div>
            </div>
        </div><!-- /.modal-content -->
    </div><!-- /.modal-dialog -->
</div><!-- /.modal -->


<script type="text/javascript">
    var links = document.getElementsByTagName("a");
    for (var i = 0, l = links.length; i < l; i++) {
        links[i].setAttribute("hideFocus", true);
    }
</script>


<audio autoplay>
    <!--<source src="http://pan.aaaxt.cc/data/f_9618.mp3" loop="-1" type="audio/mpeg"> -->
    <source src="http://cgtblog.oss-cn-shanghai.aliyuncs.com/mp3/Victory.mp3" loop="-1" type="audio/mpeg">
</audio>

<script type="text/javascript">
    $(function () {
        $('#myModal').modal('hide')
        $('#myModal').on('show.bs.modal', centerModals);
        //禁用空白处点击关闭
        $('#myModal').modal({
            backdrop: 'static',
            keyboard: false,//禁止键盘
            show:false
        });
        //页面大小变化是仍然保证模态框水平垂直居中
        $(window).on('resize', centerModals);
    });
    function centerModals() {
        $('#myModal').each(function(i) {
            var $clone = $(this).clone().css('display','block').appendTo('body');
            var top = Math.round(($clone.height() - $clone.find('.modal-content').height()) / 2);
            top = top > 0 ? top : 0;
            $clone.remove();
            $(this).find('.modal-content').css("margin-top", top);
        });
    }

    function _loginModal(){
        $('#myModal').modal('show');
    }
</script>

</body>
</html>
