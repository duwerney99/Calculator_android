package com.example.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var operation: Operation


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        operation = Operation(this)
        val seven = findViewById<Button>(R.id.seven)
        val eight = findViewById<Button>(R.id.eight)
        seven.setOnClickListener (this)
        eight.setOnClickListener (this)

    }

    fun numberAction(view: View) {

       operation.numberAction(view)
    }

    fun operatorAction(view: View) {
        operation.operatorAction(view)
    }


    fun allClearAction(view: View)
    {
        findViewById<TextView>(R.id.workingsTV).text = ""
        findViewById<TextView>(R.id.resultsTV).text = ""
    }

    fun backSpaceAction(view: View)
    {
        val length = findViewById<TextView>(R.id.workingsTV).length()
        if (length > 0)
            findViewById<TextView>(R.id.workingsTV).text = findViewById<TextView>(R.id.workingsTV).text.subSequence(0, length - 1)
    }

    fun equalsAction(view: View)
    {
        findViewById<TextView>(R.id.resultsTV).text = calculateResults()
    }

    private fun calculateResults(): String
    {
        val digitOperators = digitsOperators()
        if (digitOperators.isEmpty()) return ""

        val timesDivision = timesDivisionCalculate(digitOperators)
        if (timesDivision.isEmpty()) return ""

        val result = addSubtractCalculate(timesDivision)
        if (result.toString().contains(".0"))
        {
            return result.toInt().toString()
        }
        else {

            return result.toString()
        }
    }

    private fun addSubtractCalculate(passedList: MutableList<Any>): Float
    {
        var result = passedList[0] as Float

        for (i in passedList.indices)
        {
            if (passedList[i] is Char && i != passedList.lastIndex)
            {
                val operator = passedList[i]
                val nextDigit = passedList[i + 1] as Float
                if (operator == '+')
                    result += nextDigit
                if (operator == '-')
                    result -= nextDigit
            }
        }
        return result
    }

    private fun timesDivisionCalculate(passedList: MutableList<Any>): MutableList<Any>
    {
        var list = passedList
        while (list.contains('x') || list.contains('/'))
        {
            list = calcTimesDiv(list)
        }
        return list

    }

    private fun calcTimesDiv(passedList: MutableList<Any>): MutableList<Any>
    {
        val newList = mutableListOf<Any>()
        var restarIndex = passedList.size

        for (i in passedList.indices)
        {
            if (passedList[i] is Char && i != passedList.lastIndex && i < restarIndex)
            {
                val operator = passedList[i]
                val prevDigit = passedList[i - 1] as Float
                val nextDigit = passedList[i + 1] as Float
                when (operator)
                {
                    'x' ->
                    {
                        newList.add(prevDigit * nextDigit)
                        restarIndex = i + 1
                    }
                    '/' ->
                    {
                        newList.add(prevDigit / nextDigit)
                        restarIndex = i + 1
                    }
                    else ->
                    {
                        newList.add(prevDigit)
                        newList.add(operator)
                    }
                }
            }
            if (i > restarIndex)
                newList.add(passedList[i])
        }
        return  newList
    }

    private fun digitsOperators(): MutableList<Any>
    {
        val list = mutableListOf<Any>()
        var currentDigit = ""
        for (character in findViewById<TextView>(R.id.workingsTV).text)
        {
            if (character.isDigit() || character == '.')
                currentDigit += character
            else
            {
                list.add(currentDigit.toFloat())
                currentDigit = ""
                list.add(character)
            }
        }
        if (currentDigit != "")
            list.add(currentDigit.toFloat())

        return list
    }

    override fun onClick(p0: View?) {
        when (p0?.id) {
            R.id.seven, R.id.eight -> { operation.numberAction(p0) }
//            R.id.eight -> { numberAction(p0) }
        }
    }
}
