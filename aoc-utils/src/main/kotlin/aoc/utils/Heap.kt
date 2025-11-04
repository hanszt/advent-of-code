package aoc.utils

/**
 * A map implementation with the ability to efficiently extract the minimum value according to the [comparator]
 */
class Heap<K, V>(private val comparator: Comparator<V>) : AbstractMutableMap<K, V>() {

    private data class Data<V>(var v: V, var i: Int)

    override var size = 0
        private set
    private var keyArray = arrayOfNulls<Any>(INITIAL_CAPACITY)
    private val map = HashMap<K, Data<V>>()

    override fun isEmpty() = size == 0
    override val entries: MutableSet<MutableMap.MutableEntry<K, V>>
        get() = HashMap<K, V>(map.size).apply {
            for ((k, v) in map) {
                put(k, v.v)
            }
        }.entries

    /**
     * @return a pair containing the key and the minimum value.
     */
    @Suppress("UNCHECKED_CAST")
    fun removeMin(): Pair<K, V> {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        val resK = keyArray[0] as K
        val resV = map[resK]!!.v
        size--
        var i = 0
        val ik = keyArray[size] as K
        val id = map[ik]!!
        keyArray[0] = ik
        id.i = 0
        keyArray[size] = null
        while (true) {
            var t = 2 * i + 1
            var j = i
            var jd = id
            if (t >= size) break
            var td = map[keyArray[t]]!!
            if (comparator.compare(td.v, jd.v) < 0) {
                j = t
                jd = td
            }
            t++
            if (t < size) {
                td = map[keyArray[t]]!!
                if (comparator.compare(td.v, jd.v) < 0) {
                    j = t
                    jd = td
                }
            }
            if (j == i) break
            val jk = keyArray[j] as K
            keyArray[i] = jk
            jd.i = i
            keyArray[j] = ik
            id.i = j
            i = j
        }
        return resK to resV
    }

    fun getMin(): Pair<K, V> {
        if (isEmpty()) {
            throw NoSuchElementException()
        }
        @Suppress("UNCHECKED_CAST") val resK = keyArray[0] as K
        val resV = map[resK]!!.v
        return resK to resV
    }

    override fun get(key: K): V? = map[key]?.v

    override fun put(key: K, value: V): V? {
        val get = get(key)
        putBetter(key, value)
        return get
    }

    /**
     * @return the value if a new value was added or the value of an existing key was updated else null.
     */
    @Suppress("UNCHECKED_CAST")
    fun putBetter(k: K, v: V): V? {
        val kd0 = map[k]
        var i: Int
        val id: Data<V>
        if (kd0 == null) {
            i = size++
            id = Data(v, i)
            if (i >= keyArray.size) keyArray = keyArray.copyOf(keyArray.size * 2)
            keyArray[i] = k
            map[k] = id
        } else {
            if (comparator.compare(kd0.v, v) <= 0) return null
            i = kd0.i
            id = kd0
            id.v = v
        }
        while (i > 0) {
            val j = (i - 1) / 2
            val jd = map[keyArray[j]]!!
            if (comparator.compare(jd.v, id.v) <= 0) break
            val jk = keyArray[j] as K
            keyArray[i] = jk
            jd.i = i
            keyArray[j] = k
            id.i = j
            i = j
        }
        return v
    }

    private companion object {
        private const val INITIAL_CAPACITY = 1024
    }
}

fun <K, V : Comparable<V>> Heap(): Heap<K, V> = Heap(naturalOrder())
