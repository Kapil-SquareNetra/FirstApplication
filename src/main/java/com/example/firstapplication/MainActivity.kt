package com.example.firstapplication

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.StringBuilder
import java.util.*


class MainActivity : AppCompatActivity() {


    var operator =Stack<Char>()
    var operand = Stack<Double>()

    var num1: Double=0.0
    var num2: Double=0.0

    var numStr= StringBuilder()

    var pointFlag: Boolean= false
    var brackFlag: Boolean= false


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        operator.push('$')


        btnAC.setOnClickListener {
            txtInput.text=""
            txtOutput.text=""
            operand.clear()
            operator.clear()
            operator.push('$')
            pointFlag=false
        }


        btnBkSpc.setOnClickListener {
            var txt= txtInput.text
            if(txt.length>1) {
                txt=txt.substring(0, txt.length-1)
                txtInput.text=txt
            }
            else if(txt.length<=1) {
                txtInput.text=""
                txtOutput.text=""
            }

        }

        btnDiv.setOnClickListener { replaceLastChar('/') }
        btnMul.setOnClickListener { replaceLastChar('*') }
        btnMinus.setOnClickListener { replaceLastChar('-') }
        btnPlus.setOnClickListener { replaceLastChar('+') }
        btnPercent.setOnClickListener { replaceLastChar('%') }


        btnBracks.setOnClickListener {
            if (brackFlag==false){
                addElement('(')
                brackFlag=true

            }
            else {
                addElement(')')
                brackFlag=false
            }

        }
        btnEqual.setOnClickListener {
            try {
                println(txtInput.text.toString())
                pushtoStack()
                evaluateStacks()
            }

            catch (e: Exception){
                Log.i("Exception",e.message)
            }
            finally {
                num1=0.0
                num2=0.0
                operand.clear()
                operator.clear()
                operator.push('$')

            }

        }

        btnZero.setOnClickListener { addElement('0') }
        btnOne.setOnClickListener { addElement('1') }
        btnTwo.setOnClickListener { addElement('2') }
        btnThree.setOnClickListener { addElement('3') }
        btnFour.setOnClickListener { addElement('4') }
        btnFive.setOnClickListener { addElement('5') }
        btnSix.setOnClickListener { addElement('6') }
        btnSeven.setOnClickListener { addElement('7') }
        btnEight.setOnClickListener { addElement('8') }
        btnNine.setOnClickListener { addElement('9') }

        btnPoint.setOnClickListener { addElement('.')  }
    }

    fun addElement(chr: Char){
        if (chr!='.'){
        txtInput.append(chr.toString())
        }
        else
        {
            if (pointFlag==false) {
                txtInput.append(chr.toString())
                pointFlag=true
            }
        }

        var charRange  = listOf('+','-','*','/')
        for (i in charRange){
            if (txtInput.text.contains(i)){
                btnEqual.performClick()
            }
        }


    }

    fun replaceLastChar(chr: Char){
        var txt= txtInput.text
        var charRange  = listOf('+','-','*','/','%')
            if(txt.get(txt.length-1) in charRange){
                txt=txt.substring(0, txt.length-1)
                txtInput.text=txt
                txtInput.append(chr.toString())
                pointFlag=false

            }
            else {
                txtInput.append(chr.toString())
                pointFlag=false
            }


    }

    fun pushtoStack(){
        var charRange  = listOf('+','-','*','/','%')



        for (i in 0..(txtInput.length()-1)) {
            var character : Char = txtInput.text.toString().get(i)
            if (character in '0'..'9'){
                numStr.append(character)
                //var value: Int= character.toString().toInt()
                //num1 = (num1*10) + value
            }
            else if(character=='.') numStr.append(character)
            else if(character=='('|| character==')') operator.push(character)
            else if (character in charRange ) {
                var value: Double= numStr.toString().toDouble()
                operand.push(value)
                numStr= StringBuilder()
                operator.push(character)
            }

        }
        var value:Double= numStr.toString().toDouble()
        operand.push(value)
        numStr= StringBuilder()

    }

    fun performOperation(op:Char){
        when(op){

            '+'-> {
                var value1= operand.pop()
                var value2= operand.pop()
                num2=value2 + value1
                operand.push(num2)
                num2=0.0
            }
            '-'-> {
                var value1= operand.pop()
                var value2= operand.pop()
                num2=value2 - value1
                operand.push(num2)
                num2=0.0
            }
            '*'-> {
                var value1= operand.pop()
                var value2= operand.pop()
                num2=value2 * value1
                operand.push(num2)
                num2=0.0
            }
            '/'-> {
                var value1= operand.pop()
                var value2= operand.pop()
                num2=value2 / value1
                operand.push(num2)
                num2=0.0
            }
            '%'->{
                var value1= operand.pop()
                var value2= operand.pop()
                num2=value2 % value1
                operand.push(num2)
                num2=0.0
            }
            '('->{ }


        }
    }

    fun evaluateStacks(){

        try {
            while(operator.peek()!='$'){
                var op=operator.pop()
                if (op==')')
                {
                    do {
                        op= operator.pop()
                        performOperation(op)
                    }while (op!='(')
                }
                else performOperation(op)


            }
        }
        catch (e: Exception){
            println("error at evaluateStacks ${e.message}")

        }
        finally {
            if (operator.pop()=='$') {
                var finalValue = operand.pop()
                txtOutput.text = finalValue.toString()
               // Toast.makeText(this, "Evaluated!",Toast.LENGTH_SHORT).show()
            }
        }



    }


}
