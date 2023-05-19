package eu.tutorials.calculator

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var tvinput:TextView?=null
    private var lastnumeric:Boolean=true
    private var lastdot:Boolean=false
    private var flag:Int=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
      tvinput=findViewById(R.id.tvinput)
    }
    fun btnclick(view:View)
    {
        tvinput?.append((view as Button).text) //view is basically the exact button which is clicked
        // but since view does't have text method so we typecasted it as button and then used its text method
        lastnumeric=true
        lastdot=false
    }
    fun onclr(view:View)
    {
        tvinput?.text=""
        lastnumeric=true
        lastdot=false
        flag=0
    }
    fun ondel(view:View)
    {
        val value:String=tvinput?.text.toString()
        if(value.isNotEmpty()) {
            tvinput?.text = value.substring(0, value.length - 1)
            lastnumeric=true
            lastdot=false
            flag=0
        }

    }
    fun ondecimal(view: View)
    {
        if(lastnumeric && !lastdot && flag==0)
        {
            tvinput?.append(".")
            lastnumeric=false
            lastdot=true
            flag=1

        }
    }
    fun onOperator(view:View)
    {
        tvinput?.text?.let {
            if(lastnumeric && !isoperatorAdded(it.toString()))
            {
                tvinput?.append((view as Button).text)
                lastnumeric=false
                lastdot=false

            }
        }
    }
    @SuppressLint("SetTextI18n")
    fun onEqual(view: View)
    {
        tvinput?.text.let {
            var prefix=""
            if(lastnumeric)
            {
                 var tvValue=tvinput?.text.toString()
                try {
                    if(tvValue.startsWith("-"))
                    {
                        prefix="-"
                        tvValue=tvValue.substring(1)
                    }
                    if(tvValue.contains("-")) {
                        val splitValue = tvValue.split("-")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvinput?.text =RemoveZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                    }
                    else if(tvValue.contains("+")) {
                        val splitValue = tvValue.split("+")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }

                        tvinput?.text =RemoveZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                    }
                    else if(tvValue.contains("*")) {
                        val splitValue = tvValue.split("*")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvinput?.text=RemoveZeroAfterDot((one.toDouble() * two.toDouble()).toString())

                    }
                    else if(tvValue.contains("/")) {
                        val splitValue = tvValue.split("/")
                        var one = splitValue[0]
                        val two = splitValue[1]
                        if (prefix.isNotEmpty()) {
                            one = prefix + one
                        }
                        tvinput?.text =RemoveZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                    }


                }catch (e:java.lang.ArithmeticException)
                {
                    e.printStackTrace()
                }
            }
        }

    }
    private fun RemoveZeroAfterDot(result:String):String
    {
       var name=result
       if(result.contains(".0") &&result.endsWith("0"))
       {
           name=result.substring(0,result.length-2)
       }
        return name
    }

    private fun isoperatorAdded(value:String):Boolean
    {
        return if(value.startsWith("-")) {
            false
        }
        else {
            (value.contains("/")
                    || value.contains("*")
                    || value.contains("+")
                    || value.contains("-")) // this will return true if these conditions are satisfied
        }
    //if both blocks if and else are not true then it will return false
    }

}