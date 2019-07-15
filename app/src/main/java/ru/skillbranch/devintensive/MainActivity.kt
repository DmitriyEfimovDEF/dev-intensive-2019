package ru.skillbranch.devintensive

import android.annotation.SuppressLint
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var benderObj:Bender

    @SuppressLint("LongLogTag")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("~~~~~~~~~~~~~MainActivity", "onCreate")

       val status = savedInstanceState?.getString("STATUS") ?: Bender.Status.NORMAL.name
        var question = savedInstanceState?.getString("  QUESTION") ?: Bender.Question.NAME.name
        benderObj = Bender(Bender.Status.valueOf(status),Bender.Question.valueOf(question))
        tv_question.text = benderObj.askQuestion()

        val (r,g,b)=benderObj.status.color
        iv_bender.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)

        iv_send.setOnClickListener {
            val (phrase,color) = benderObj.listenAnswer(et_message.text.toString().toLowerCase())
            et_message.setText("")
            val (r, g, b) = color
            iv_bender.setColorFilter(Color.rgb(r,g,b),PorterDuff.Mode.MULTIPLY)
            tv_question.text= phrase

            Log.d("M_~~~MainActivity","onCreate $status $question")
        }

    }

    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString("STATUS", benderObj.status.name)
            outState?.putString("QUESTION", benderObj.question.name)

        Log.d("M_~~~~MainActivity","onSaveInstanceState \nstatus: ${benderObj.status.name} \nquestion${benderObj.status.name}")
    }

    @SuppressLint("LongLogTag")
    override fun onRestart() {
        super.onRestart()
        Log.d("M_~~~~~~~~~~~~~MainActivity", "onRestart")
        
    }

    @SuppressLint("LongLogTag")
    override fun onStart() {
        super.onStart()
        Log.d("M_~~~~~~~~~~~~~MainActivity","onStart")
    }

    @SuppressLint("LongLogTag")
    override fun onResume() {
        super.onResume()
        Log.d("M_~~~~~~~~~~~~~MainActivity", "onResume")

    }

    @SuppressLint("LongLogTag")
    override fun onPause() {
        super.onPause()
        Log.d("M_~~~~~~~~~~~~~MainActivity","onPause")
    }

    @SuppressLint("LongLogTag")
    override fun onStop() {
        super.onStop()
        Log.d("M_~~~~~~~~~~~~~MainActivity","onSto")
    }

    @SuppressLint("LongLogTag")
    override fun onDestroy() {
        super.onDestroy()
        Log.d("M_~~~~~~~~~~~~~MainActivity" ,"Destroy")
    }
}
