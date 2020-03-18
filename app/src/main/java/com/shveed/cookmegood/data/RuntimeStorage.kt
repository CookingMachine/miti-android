package com.shveed.cookmegood.data

class RuntimeStorage {
    var categories = arrayListOf<String>()

    companion object {
        private var instance: RuntimeStorage? = null
        fun newInstance(): RuntimeStorage? {
            if (instance == null) {
                instance = RuntimeStorage()
            }
            return instance
        }
    }
}