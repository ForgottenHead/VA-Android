package cz.mendelu.tododolist.extensions

fun Double.roundTwoDecimal(): String {
    return String.format("%.2f", this)
}