package com.mudassir.customkeyboard.keyboard


interface NumberKeyboardListener {

    /**
     * Invoked when a number key is clicked.
     */
    fun onNumberClicked(number: Int)

    /**
     * Invoked when the left auxiliary button is clicked.
     */
    fun onLeftAuxButtonClicked()

    /**
     * Invoked when the right auxiliary button is clicked.
     */
    fun onRightAuxButtonClicked()
}
