package com.example.calculator

import android.view.View
import android.widget.Button
import android.widget.TextView


class Operation(val mainActivity: MainActivity){

    var canAddOperation = false
    var canAddDecimal = true

    fun numberAction(view: View) {

        if (view is Button)
        {
            if (view.text == ".")
            {
                if (canAddDecimal)
                    mainActivity.findViewById<TextView>(R.id.workingsTV).append(view.text)

                canAddDecimal = false
            }
            else
                mainActivity.findViewById<TextView>(R.id.workingsTV).append(view.text)

            canAddOperation = true
        }
    }

    fun operatorAction(view: View) {

        if (view is Button && canAddOperation) {
            mainActivity.findViewById<TextView>(R.id.workingsTV).append(view.text)
            canAddOperation = false
            canAddDecimal = true
        }
    }
}