package com.example.toba.customime

class Keymap {
    private val keymap: MutableMap<Int, KeymapElement> = mutableMapOf()

    private val primaryKeyInfo: keyInfo.KeyInfo = keyInfo.KeyInfo.NULL

    fun put(index: Int, direction: Flick.Direction, key: keyInfo.KeyInfo) {
        if (keymap.uncontainsKey(index)) {
            keymap[index] = KeymapElement()
        }
        keymap[index]?.put(direction, key)
    }

    fun getKey(index: Int, flick: Flick): keyInfo.KeyInfo {
        return if (keymap.uncontainsKey(index))
            primaryKeyInfo
        else
            keymap[index]?.getKey(flick) ?: primaryKeyInfo
    }

    fun clear(index: Int) {
        keymap[index]?.clear()
    }

    fun getMaxDistance(index: Int, direction: Flick.Direction): Int {
        return keymap[index]?.getMaxDistance(direction) ?: 0
    }

    private fun <K, V> MutableMap<K, V>.uncontainsKey(key: K): Boolean {
        return isEmpty() || !containsKey(key)
    }
    private inner class KeymapElement {
        /**
         * Entity of keymap.
         * Key of map is Flick.Direction, Key of list is Flick.direction.
         */
        private val listMap: MutableMap<Flick.Direction, MutableList<keyInfo.KeyInfo>> = mutableMapOf()
        /**
         * Primary KeyInfo.
         * Its value equals to [Keymap.primaryKeyInfo].
         */
        private val primaryKeyInfo: keyInfo.KeyInfo = this@Keymap.primaryKeyInfo
        /**
         * KeyInfo when Flick.Direction is NONE, or Flick.direction is 0.
         */
        private var onTapKeyInfo: keyInfo.KeyInfo = primaryKeyInfo

        /**
         * Sets KeyInfo to listMap.
         * @param direction index direction
         * @param key key that you want to store
         */
        fun put(direction: Flick.Direction, key: keyInfo.KeyInfo) {
            if (direction == Flick.Direction.NONE) {
                if (onTapKeyInfo == primaryKeyInfo) {
                    onTapKeyInfo = key
                }
            } else {
                if (listMap.uncontainsKey(direction)) {
                    listMap[direction] = mutableListOf()
                }
                listMap[direction]?.add(key)
            }
        }

        /**
         * Finds stored KeyInfo with index and Flick.
         * When keymap have not defined or can't find matching KeyInfo,
         * this method returns [onTapKeyInfo].
         * @param flick flick object that has current flick information
         * @return KeyInfo matching [flick], or [onTapKeyInfo]
         */
        fun getKey(flick: Flick): keyInfo.KeyInfo {
            return (listMap[flick.direction] ?: return onTapKeyInfo)
                    .filterIndexed { i, _ -> i < flick.distance }
                    .lastOrNull()
                    ?: onTapKeyInfo
        }

        /**
         * Clears [listMap] and reset [onTapKeyInfo].
         */
        fun clear() {
            onTapKeyInfo = primaryKeyInfo
            listMap.clear()
        }

        /**
         * Returns max distance from listMap[[direction]].
         * @param direction flick direction
         * @returns max distance
         */
        fun getMaxDistance(direction: Flick.Direction): Int {
            return listMap[direction]?.size ?: 0
        }

        /**
         * Returns max distance from listMap.
         * @returns max distance
         */
        fun getMaxDistance(): Int {
            return listMap.keys.map { getMaxDistance(it) }.max() ?: 0
        }
    }
}