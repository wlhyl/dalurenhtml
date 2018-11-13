//import kotlin.browser.document
import js.externals.jquery.JQueryXHR
import js.externals.jquery.jQuery
import org.w3c.dom.HTMLInputElement
import org.w3c.dom.HTMLOptionElement
import kotlin.browser.document
import kotlin.browser.window
import kotlin.js.Date

val diZhi = arrayOf("子", "丑", "寅", "卯", "辰", "巳", "午", "未", "申", "酉", "戌", "亥")
fun main(args: Array<String>) {
//    setDateTime()
//    val yearOfBirthInput = document.getElementById("yearOfBirth") as HTMLInputElement
//    yearOfBirthInput.value = Date().getFullYear().toString()
//    val divinationTimeOption = document.getElementById("divinationTime${(0..11).random()}") as HTMLOptionElement
//    divinationTimeOption.selected=true
//    setSun()

}

fun setDateTime() {
    val t = Date()
    val year = t.getFullYear()
    val month = t.getMonth() + 1
    val day = t.getDate()
    val hour = t.getHours()
    val minutes = t.getMinutes()
    val seconds = t.getSeconds()

    val yearInput = document.getElementById("year") as HTMLInputElement
    val monthInput = document.getElementById("month") as HTMLInputElement
    val dayInput = document.getElementById("day") as HTMLInputElement
    val hourInput = document.getElementById("hour") as HTMLInputElement
    val minutesInput = document.getElementById("minutes") as HTMLInputElement
    val secondsInput = document.getElementById("seconds") as HTMLInputElement

    yearInput.value = year.toString()
    monthInput.value = month.toString()
    dayInput.value = day.toString()
    hourInput.value = hour.toString()
    minutesInput.value = minutes.toString()
    secondsInput.value = seconds.toString()

    val divinationTimeRequest = js("{}")
    divinationTimeRequest.year = year
    divinationTimeRequest.month = month
    divinationTimeRequest.day = day
    divinationTimeRequest.hour = hour
    divinationTimeRequest.minutes = minutes
    divinationTimeRequest.seconds = seconds

    val dtSettings = js("{}")
//    val a: JQueryAjaxSettings = js("{}")

//    println(js("typeof a"))
    dtSettings.type = "PUT"
    dtSettings.data = JSON.stringify(divinationTimeRequest)
    dtSettings.dataType = "json"
    dtSettings.headers = js("{}")
    dtSettings.headers["Content-Type"] = "application/json"
    dtSettings.success = fun(data: Any) {//, textStatus: String, jqXHR: JQueryXHR) {
//        val k = JSON.parse<Map<*,*>>(data.toString())
//        val d: dynamic = data

        val dt = diZhi.indexOf(data.asDynamic().hour[1])
        val tSelect = document.getElementById("divinationTime${dt}") as HTMLOptionElement
        tSelect.selected = true
    }
    dtSettings.error = fun(data: JQueryXHR) {
//        val k = JSON.parse<Map<*,*>>(data.toString())
//        val d: dynamic = data
        val info0 = jQuery("""
<div class="alert alert-danger  fade show navbar-fixed-top "   style="z-index:100000">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>${data.responseJSON.asDynamic().message}</strong>
</div>
""")
        jQuery("#alter").append(info0)
        window.setTimeout(fun() {
            info0.asDynamic().alert("close")
        }, 5000)


    }
    jQuery.ajax("/sizhu", dtSettings)// as JQueryAjaxSettings)// settings)
}

fun setSun() {
    val year = jQuery("#year").`val`()//此值为字符串
    val month = jQuery("#month").`val`()
    val day = jQuery("#day").`val`()
    val hour = jQuery("#hour").`val`()
    val minutes = jQuery("#minutes").`val`()
    val seconds = jQuery("#seconds").`val`()

    val sunRequest = js("{}")
    sunRequest.year = year
    sunRequest.month = month
    sunRequest.day = day
    sunRequest.hour = hour
    sunRequest.minutes = minutes
    sunRequest.seconds = seconds

    val sunSettings = js("{}")
    sunSettings.type = "PUT"
    sunSettings.data = JSON.stringify(sunRequest)
    sunSettings.dataType = "json"
    sunSettings.headers = js("{}")
    sunSettings.headers["Content-Type"] = "application/json"
    sunSettings.success = fun(data: Any) {//, textStatus: String, jqXHR: JQueryXHR) {
        val dt = data.asDynamic().SunMansion.toString()
        jQuery("#sun").`val`(dt)
//        val tSelect = document.getElementById("sun{dt}") as HTMLOptionElement
//        tSelect.selected = true
    }
    sunSettings.error = fun(data: JQueryXHR) {
//        val k = JSON.parse<Map<*,*>>(data.toString())
//        val d: dynamic = data
        val message = data.responseJSON.asDynamic().message
        val info0 = jQuery("""
<div class="alert alert-danger  fade show navbar-fixed-top "   style="z-index:100000">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>${message}</strong>
</div>
""")
        jQuery("#alert").append(info0)
        window.setTimeout(fun() {
            info0.asDynamic().alert("close")
        }, 5000)


    }
    jQuery.ajax("/sunmansion", sunSettings)
}

fun paiPan() {
    val year = jQuery("#year").`val`()//此值为字符串
    val month = jQuery("#month").`val`()
    val day = jQuery("#day").`val`()
    val hour = jQuery("#hour").`val`()
    val minutes = jQuery("#minutes").`val`()
    val seconds = jQuery("#seconds").`val`()

    val sun = jQuery("#sun  option:selected").text()
    val divinationTime = jQuery("#divinationTime  option:selected").text()
    val daytime = jQuery("#daytime  option:selected").text() == "昼"
    val sex = jQuery("#sex  option:selected").text()
    val yearOfBirth = jQuery("#yearOfBirth").`val`()
    val mingJu = jQuery("#mingJu").`val`()
    val desc = jQuery("#desc").`val`()

    val req = js("{}")
    req.year = year
    req.month = month
    req.day = day
    req.hour = hour
    req.minutes = minutes
    req.second = seconds
    req.sunMansion = sun
    req.divinationTime = divinationTime
    req.daytime = daytime
    req.yearOfBirth = yearOfBirth
    req.description = desc
    req.sex = sex

    val settings = js("{}")
    settings.type = "PUT"
    settings.data = JSON.stringify(req)
    settings.dataType = "json"
    settings.headers = js("{}")
    settings.headers["Content-Type"] = "application/json"
    settings.success = fun(data: Any) {//, textStatus: String, jqXHR: JQueryXHR) {
        val res = data.asDynamic()
        val sp = jQuery("#shipan")
        sp.empty()
        sp.append("<div>${res.year}年${res.month}月${res.day}日${res.hour}时${res.minutes}分${res.second}秒</div>")
        sp.append("<div>占测的事：${res.description}</div>")
        sp.append("<div>四柱：${res.siZhu[0]} ${res.siZhu[1]} ${res.siZhu[2]} ${res.siZhu[3]}</div>")
        sp.append("<div>${res.solarTerms[0].name}: ${Date(res.solarTerms[0].t.toString()).format()}</div>")
        sp.append("<div>${res.solarTerms[1].name}: ${Date(res.solarTerms[1].t.toString()).format()}</div>")
        sp.append("""<div>月将：${res.sunMansion}  月宿：${res.moonMansion}
            |占时：${res.divinationTime} ${if (res.daytime) "昼占" else "夜占"}
            （|空亡：${res.kongWang[0]} ${res.kongWang[1]}）</div>""".trimMargin())
        sp.append("""<div>性别：  ${res.sex} 本命：${res.benMing}行年：${res.xingNian}</div>""")

        val table= jQuery("<table></table>")
        sp.append(table)
        for (i in 0..2){
        val tr= jQuery("""
            <tr>
                <td></td>
                <td>${res.sanChuan.liuQing[i]}</td>
                <td>${res.sanChuan.dunGan[i]}</td>
                <td>${res.sanChuan.sanChuan[i]}</td>
                <td>${res.tianJiang[diZhi.indexOf(res.sanChuan.sanChuan[i])]}</td>
                <td></td>
            </tr>""")
            tr.appendTo(table)
        }
        table.append("<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>")
        val siKe="""
            <tr>
                <td></td>
                <td>${res.tianJiang[diZhi.indexOf(res.siKe.zhiYing)]}</td>
                <td>${res.tianJiang[diZhi.indexOf(res.siKe.zhiYang)]}</td>
                <td>${res.tianJiang[diZhi.indexOf(res.siKe.ganYing)]}</td>
                <td>${res.tianJiang[diZhi.indexOf(res.siKe.ganYang)]}</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>${res.siKe.zhiYing}</td>
                <td>${res.siKe.zhiYang}</td>
                <td>${res.siKe.ganYing}</td>
                <td>${res.siKe.ganYang}</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>${res.siKe.zhiYang}</td>
                <td>${res.siKe.zhi}</td>
                <td>${res.siKe.ganYang}</td>
                <td>${res.siKe.gan}</td>
                <td></td>
            </tr>"""
        table.append(siKe)
        table.append("<tr><td></td><td></td><td></td><td></td><td></td><td></td></tr>")
        val tp="""
            <tr>
                <td></td>
                <td>${res.tianJiang[5]}</td>
                <td>${res.tianJiang[6]}</td>
                <td>${res.tianJiang[7]}</td>
                <td>${res.tianJiang[8]}</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>${res.tianPan[5]}</td>
                <td>${res.tianPan[6]}</td>
                <td>${res.tianPan[7]}</td>
                <td>${res.tianPan[8]}</td>
                <td></td>
            </tr>
            <tr>
                <td>${res.tianJiang[4]}</td>
                <td>${res.tianPan[4]}</td>
                <td></td><td></td>
                <td>${res.tianPan[9]}</td>
                <td>${res.tianJiang[9]}</td>
            </tr>
            <tr>
                <td>${res.tianJiang[3]}</td>
                <td>${res.tianPan[3]}</td>
                <td></td><td></td>
                <td>${res.tianPan[10]}</td>
                <td>${res.tianJiang[10]}</td>
            </tr>
            <tr>
                <td></td>
                <td>${res.tianPan[2]}</td>
                <td>${res.tianPan[1]}</td>
                <td>${res.tianPan[0]}</td>
                <td>${res.tianPan[11]}</td>
                <td></td>
            </tr>
            <tr>
                <td></td>
                <td>${res.tianJiang[2]}</td>
                <td>${res.tianJiang[1]}</td>
                <td>${res.tianJiang[0]}</td>
                <td>${res.tianJiang[11]}</td>
                <td></td>
            </tr>
        """
        table.append(tp)
        // join是javascript数组的方法，不是kotlin的方法，
        // 因为是动态类型，所以可以这样写
        sp.append("<div><b>卦体：</b> ${res.guaTi.join("　")}</div>")


    }
    settings.error = fun(data: JQueryXHR) {
//        val k = JSON.parse<Map<*,*>>(data.toString())
//        val d: dynamic = data
        data.responseJSON
        val message = data.responseJSON.asDynamic().message
        val info0 = jQuery("""
<div class="alert alert-danger  fade show navbar-fixed-top "   style="z-index:100000">
<button type="button" class="close" data-dismiss="alert">&times;</button>
<strong>${message}</strong>
</div>
""")
        jQuery("#alert").append(info0)
        window.setTimeout(fun() {
            info0.asDynamic().alert("close")
        }, 5000)


    }
    jQuery.ajax("/dalurenshipanjson", settings)

}