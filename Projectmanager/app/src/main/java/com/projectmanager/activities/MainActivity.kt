package com.projectmanager.activities

import android.os.Bundle
import com.projectmanager.R

class MainActivity : BaseActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        //This call the parent constructor
        super.onCreate(savedInstanceState)

        // This is used to align the xml view to this class
        setContentView(R.layout.activity_main)
    }
}
