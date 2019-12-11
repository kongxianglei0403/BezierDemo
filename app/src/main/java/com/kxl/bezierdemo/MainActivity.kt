package com.kxl.bezierdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.tool,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.basic -> {
                basicView.visibility = View.VISIBLE
                advanceView.visibility = View.GONE
                moreView.visibility = View.GONE
            }
            R.id.advance ->{
                basicView.visibility = View.GONE
                advanceView.visibility = View.VISIBLE
                moreView.visibility = View.GONE
            }
            R.id.high -> {
                basicView.visibility = View.GONE
                advanceView.visibility = View.GONE
                moreView.visibility = View.VISIBLE
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
