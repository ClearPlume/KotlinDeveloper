class Event(val id: Int, val x: Int, val y: Int, val isLongClick: Boolean) {
    operator fun component1() = id
    operator fun component2() = x
    operator fun component3() = y
    operator fun component4() = isLongClick
}

fun isEventLongClicked(events: Array<Event>, eventId: Int): Boolean? {
    for ((id, x, y, isLongClick) in events) {
        if (id == eventId) return isLongClick
    }
    return null
}