package com.sxu.testmodule

import android.widget.TextView
import com.sxu.basecomponentlibrary.activity.BaseActivity

/**
 * Copyright (c) 2017. zhinanmao Inc. All rights reserved.
 *
 *
 * 类或接口的描述信息
 *
 * @author Freeman
 *
 * @date 17/10/31
 */

class KotlinTestActivity : BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.activity_kotlin_test
    }


    override fun getViews() {
        //var text = findViewById(R.id.text) as TextView
        var str = "Hello, World"
        //text.setText("hahahh")
        //text.setText("$str ${sum(5, 10)} max(5, 10) is ${maxOf(5, 10)} ${sringToInt("hello")} ${sringToInt("123")}")

        //text.setText("${getLength("Hello")}")
        //getValue(2, text)
//        var list = listOf("hello", "shanghai","xian", 5);
//        for (item in list) {
//            println(item)
//        }
//        for (index in list.indices) {
//            println(list[index])
//        }
//
//        var index = 0
//        while (index < list.size) {
//            println(list[index])
//        }
//
//        fun getValue(key : Any) =
//                when (key) {
//                    1   ->  "beijing"
//                    2   -> "上海"
//                    "name"  -> "Freeman"
//                    "age"   -> "29"
//                    else -> "unknown"
//                }
//

    }

    fun getValue(key : Any, textView: TextView) =
            when (key) {
                1   ->  "beijing"
                2   -> textView.setText("上海")
                "name"  -> "Freeman"
                "age"   -> "29"
                else -> "unknown"
            }

    fun getLength(obj : Any) : Int? {
        if (obj is String) {
            return obj.length
        }

        return null
    }

    override fun initActivity() {
        sum(5, 10)
    }

    fun sum(a : Int, b : Int) : Int {
        var i : Int = 1
        var j = 3
        var str = "Hello"
        print(" str value is $str, a+b=  ${a+b}")
        return a + b
    }

    fun maxOf(a: Int, b : Int)  = if (a > b)  a else b

    fun sringToInt(str : String) : Int? {
        return str.toIntOrNull()
    }
}