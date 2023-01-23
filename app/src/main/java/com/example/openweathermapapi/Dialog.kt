package com.example.openweathermapapi

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.view.Window
import android.widget.*

class Dialog(context: Context,) {
    private val dialog = Dialog(context)
    private val context = context
    private var coldList = arrayListOf(0,1)
    private var cold = 0
    private var hotList = arrayListOf(0,1)
    private var hot = 0
    fun UserDialog() {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_user)
        dialog.setCancelable(true) // 바깥 화면 누르면 닫힘.
        dialog.findViewById<Button>(R.id.SaveBtn).setOnClickListener {
            val intent = Intent(this.context, MainActivity::class.java)
            intent.putExtra("cold", cold)
            intent.putExtra("hot", hot)
            dialog.dismiss()
            context.startActivity(intent)
        }
        dialog.findViewById<Button>(R.id.CancelBtn).setOnClickListener {
            dialog.dismiss()
        }
        dialog.findViewById<RadioGroup>(R.id.cold).setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.cold1 -> cold = coldList[0]
                R.id.cold2 -> cold = coldList[1]
            }
            dialog.show()
        }
        dialog.findViewById<RadioGroup>(R.id.hot).setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.hot1 -> hot = hotList[0]
                R.id.hot2 -> hot = hotList[1]
            }
            dialog.show()
        }

        dialog.show()
    }

}