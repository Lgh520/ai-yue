(function () {

    // 添加页面水印
    function addWaterMark({ id, name }) {
        const styleStr = `
            position:fixed;
            left:0;
            top:0;
            right:0;
            bottom:0;
            pointer-events:none;
            z-index:3000;
            `
        // const styleStr = `
        //     position: absolute;
        //     top: 0;
        //     left: 0;
        //     height: 100%;
        //     width: 100%;
        //     pointer-events:none;
        //     z-index:3000;
        //     border: 1px solid red;
        //     `
        const can = document.createElement('canvas')
        const cans = can.getContext('2d')
        const container = createContainer()
        const body = document.querySelector('#content')
        body.appendChild(container)

        // container.appendChild(can)
        can.width = 520
        can.height = 140
        can.style.display = 'none'
        cans.rotate(-10 * Math.PI / 180)
        cans.font = '16px 微软雅黑'
        cans.fillStyle = 'rgba(180,180,180,0.6)'
        cans.textAlign = 'left'
        cans.textBaseline = 'Middle'
        cans.fillText(`${id} ${name} ` + new Date().toLocaleDateString(), can.width / 14, can.height)
        container.style.backgroundImage = `url(${can.toDataURL('image/png')})`

        const MutationObserver = window.MutationObserver || window.WebKitMutationObserver
        if (MutationObserver) {
            let mo = new MutationObserver(function () {
                const _wm = document.querySelector('._wm')
                // 只在_wm变动才重新调��?
                if ((_wm && _wm.getAttribute('style') !== styleStr) || !_wm) {
                    mo.disconnect()
                    mo = null
                    getUserInfoByUserId()
                }
            })
            mo.observe(container, {
                attributes: true,
                subtree: true,
                childList: true
            })
            mo.observe(document.body, {
                childList: true
            })
        }

        // 创建水印容器
        function createContainer() {
            const _wm = document.querySelector('._wm')
            const div = _wm || document.createElement('div')
            div.classList.add('_wm')
            div.setAttribute('style', styleStr)
            return div
        }
    }



    // 获取用户信息
    function getUserInfoByUserId() {
        let xmlhttp;
        if (window.XMLHttpRequest) {
            // code for IE7+,Firefox,Chrome,Opera,Safari
            xmlhttp = new XMLHttpRequest()
        } else {
            // code for IE6,IE5
            xmlhttp = new ActiveXObject('Microsoft.XMLHTTP')
        }

        xmlhttp.onreadystatechange = function () {
            if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
                const { result } = JSON.parse(xmlhttp.responseText)
                if (xmlhttp.responseText && result) {
                    addWaterMark(result)
                } else {
                    console.error('水印信息获取失败')
                }
            }
        }

        xmlhttp.open('POST', '/employee-salary-ui/web/employee/getLoginUser', true)
        xmlhttp.send()
    }

    getUserInfoByUserId()
})()