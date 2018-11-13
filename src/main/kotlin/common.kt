import kotlin.js.Date

fun Date.format():String{
   return "${this.getFullYear()}-${this.getMonth()+1}-${this.getDate()} ${this.getHours()}:${this.getMinutes()}:${this.getSeconds()}"
}