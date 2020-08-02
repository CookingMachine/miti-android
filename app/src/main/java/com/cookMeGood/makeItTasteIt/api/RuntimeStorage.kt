package com.cookMeGood.makeItTasteIt.api

class RuntimeStorage {

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