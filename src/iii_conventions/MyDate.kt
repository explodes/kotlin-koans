package iii_conventions

data class MyDate(val year: Int, val month: Int, val dayOfMonth: Int) : Comparable<MyDate> {

    override operator fun compareTo(other: MyDate) = magnitude() - other.magnitude()

    private fun magnitude() = year * 366 + month * 31 + dayOfMonth

    operator fun rangeTo(other: MyDate): DateRange = DateRange(this, other)

    operator fun plus(interval: TimeInterval): MyDate {
        return addTimeIntervals(interval, 1)
    }

    operator fun plus(interval: RepeatedTimeInterval): MyDate {
        return addTimeIntervals(interval.interval, interval.amount)
    }

}

enum class TimeInterval {
    DAY,
    WEEK,
    YEAR;

    operator fun times(amount: Int): RepeatedTimeInterval {
        return RepeatedTimeInterval(this, amount)
    }
}

class RepeatedTimeInterval(val interval: TimeInterval, val amount: Int)

class DateRange(override val start: MyDate, override val endInclusive: MyDate) : ClosedRange<MyDate>, Iterable<MyDate> {

    override fun iterator(): Iterator<MyDate> {
        return object : Iterator<MyDate> {
            var current = start.copy()

            override fun hasNext(): Boolean {
                return current <= endInclusive
            }

            override fun next(): MyDate {
                val next = current
                current = current.nextDay()
                return next
            }

        }
    }

}
