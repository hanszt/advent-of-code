package aoc.utils

inline fun <K, V, G> Map<K, V>.groupingByKey(crossinline keySelector: (K) -> G): Grouping<Pair<K, V>, G> =
    object : Grouping<Pair<K, V>, G> {
        override fun sourceIterator() = asSequence().map { (k, v) -> k to v }.iterator()
        override fun keyOf(element: Pair<K, V>): G = keySelector(element.first)
    }
