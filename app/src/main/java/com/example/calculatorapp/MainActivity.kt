package com.example.calculatorapp

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

private const val STATE_OPERANT1 = "Operant1"
private const val STATE_PENDING_OPERATOR = "Pending_Operator"
private const val STATE_OPERANT_STORES = "Operant1Stored"

class MainActivity : AppCompatActivity() {

//    private lateinit var result: EditText
//    private lateinit var newNumber: EditText
//    private val operator by lazy(LazyThreadSafetyMode.NONE) { findViewById<TextView>(R.id.operator) }

    //operantes and operator
    private var oparent1: Double? = null
    private var pendingOperator: String = "="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        result = findViewById(R.id.result)
//        newNumber = findViewById(R.id.newNumber)
//
//        //Digit and Dot Button
//        val button0: Button = findViewById(R.id.button0)
//        val button1: Button = findViewById(R.id.button1)
//        val button2: Button = findViewById(R.id.button2)
//        val button3: Button = findViewById(R.id.button3)
//        val button4: Button = findViewById(R.id.button4)
//        val button5: Button = findViewById(R.id.button5)
//        val button6: Button = findViewById(R.id.button6)
//        val button7: Button = findViewById(R.id.button7)
//        val button8: Button = findViewById(R.id.button8)
//        val button9: Button = findViewById(R.id.button9)
//        val buttonDot: Button = findViewById(R.id.buttonDot)
//
//        //operators button and equal buttons
//
//        val buttonEquals = findViewById<Button>(R.id.buttonEquals)
//        val buttonDivide = findViewById<Button>(R.id.buttonDivide)
//        val buttonMultiply = findViewById<Button>(R.id.buttonMultiply)
//        val buttonMinus = findViewById<Button>(R.id.buttonMinus)
//        val buttonPlus = findViewById<Button>(R.id.buttonPlus)

        val listener = View.OnClickListener { v ->
            val b = v as Button
            newNumber.append(b.text)
        }

        button0.setOnClickListener(listener)
        button1.setOnClickListener(listener)
        button2.setOnClickListener(listener)
        button3.setOnClickListener(listener)
        button4.setOnClickListener(listener)
        button5.setOnClickListener(listener)
        button6.setOnClickListener(listener)
        button7.setOnClickListener(listener)
        button8.setOnClickListener(listener)
        button9.setOnClickListener(listener)
        buttonDot.setOnClickListener(listener)

        val opListener = View.OnClickListener { v ->
            val op = (v as Button).text.toString()
//            var newNumber = newNumber.text.toString()
            try {
                operationPerformed(newNumber.text.toString().toDouble())
            } catch (e: NumberFormatException) {
                newNumber.setText("")
            }
            pendingOperator = op
            operator.text = pendingOperator
        }

        buttonEquals.setOnClickListener(opListener)
        buttonDivide.setOnClickListener(opListener)
        buttonMultiply.setOnClickListener(opListener)
        buttonMinus.setOnClickListener(opListener)
        buttonPlus.setOnClickListener(opListener)


        buttonNeg.setOnClickListener{
            val value = newNumber.text.toString()
            if(value.isEmpty()){
                newNumber.setText("-")
            }else{
                try{
                    var valueDouble = value.toDouble()
                    valueDouble *= -1
                    newNumber.setText(valueDouble.toString())
                }catch (e: Exception){
                    newNumber.setText("")
                }
            }
        }
        buttonClear.setOnClickListener{
            result.setText("")
            newNumber.setText("")
            oparent1 = null
            pendingOperator = "="
        }
    }

    private fun operationPerformed(value: Double) {
        if (oparent1 == null) {
            oparent1 = value
        } else {
            when (pendingOperator) {
                "=" -> oparent1 = value
                "/" -> oparent1 = if (value == 0.0) {
                    Double.NaN
                } else {
                    oparent1!! / value
                }
                "*" -> oparent1 = oparent1!! * value
                "-" -> oparent1 = oparent1!! - value
                "+" -> oparent1 = oparent1!! + value
            }
        }
        result.setText(oparent1.toString())
        newNumber.setText("")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        if (oparent1 != null) {
            outState.putDouble(STATE_OPERANT1, oparent1!!)
            outState.putBoolean(STATE_OPERANT_STORES, true)
        }
        outState.putString(STATE_PENDING_OPERATOR, pendingOperator)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)

        oparent1 = if (savedInstanceState.getBoolean(STATE_OPERANT_STORES, false)) {
            savedInstanceState.getDouble(STATE_OPERANT1)
        } else {
            null
        }
        pendingOperator = savedInstanceState.getString(STATE_PENDING_OPERATOR).toString()
        operator.text = pendingOperator

    }
}
