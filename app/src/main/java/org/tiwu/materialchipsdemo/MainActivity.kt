package org.tiwu.materialchipsdemo

import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import org.tiwu.materialchips.ChipsView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        chips_view.isDeletable = true
        chips_view.onDeleteListener = object : ChipsView.OnDeleteListener() {
            override fun onDelete(item: Any, position: Int): Boolean {
                Toast.makeText(applicationContext, getString(R.string.delete_item, position),
                        Toast.LENGTH_SHORT).show()
                return true
            }
        }

        chips_view.onClickListener = object : ChipsView.OnClickListener() {
            override fun onClick(item: Any, position: Int) {
                Toast.makeText(applicationContext, getString(R.string.select_item, item.toString()),
                        Toast.LENGTH_SHORT).show()
            }
        }

        button.setOnClickListener {

            val text = text_input.text.toString()
            if (text.isNotEmpty()) {

                val bitmap = if (checkbox.isChecked) {
                    BitmapFactory.decodeStream(resources.openRawResource(R.raw.cat))
                } else {
                    null
                }

                chips_view.addItem(Item(text, bitmap))
                text_input.text.clear()
            }
        }
    }
}
