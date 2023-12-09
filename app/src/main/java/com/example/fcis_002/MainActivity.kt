package com.example.fcis_002

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.add
import androidx.fragment.app.commit

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.setTitle("Fast Chek-In System")
        supportActionBar?.setHomeButtonEnabled(true)
        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar.setCustomView(layoutInflater.inflate(R.layout.action_bar_home,null))
        supportActionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayShowHomeEnabled(true)

        //ActionBar.DISPLAY_HOME_AS_UP

        //setSupportActionBar( t1)

        //val transaction = activity?.supportFragmentManager!!.beginTransaction()
        val transaction = supportFragmentManager.beginTransaction()
        val frgLogin = Login.newInstance("1","1")
        val frgForgot_Password = Forgot_Password.newInstance("1","1")
        val frgSigningUp = SigningUp.newInstance("1","1")
        val frgWalkInList = WalkInList.newInstance("1","1")
        val frgWalkin = Walkin.newInstance("1","1")
        val frgDashboard = NewDashBoard.newInstance("1","1")
        val frgCheckin = Checkin.newInstance("1","1")
        val frgBranchMatrics = BranchMatrics.newInstance("1","1")

        transaction.add(R.id.fragment_container_view,frgLogin,"FRAGMENT_1")
        transaction.add(R.id.fragment_container_view,frgForgot_Password,"FRAGMENT_2")
        transaction.add(R.id.fragment_container_view,frgSigningUp,"FRAGMENT_3")
        transaction.add(R.id.fragment_container_view,frgWalkin,"FRAGMENT_4")
        transaction.add(R.id.fragment_container_view,frgDashboard,"FRAGMENT_5")
        transaction.add(R.id.fragment_container_view,frgBranchMatrics,"FRAGMENT_6")
        transaction.add(R.id.fragment_container_view,frgWalkInList,"FRAGMENT_7")
        transaction.add(R.id.fragment_container_view,frgCheckin,"FRAGMENT_8")

        if (savedInstanceState == null) {
            val bundle = bundleOf("some_int" to 0)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<Login>(R.id.fragment_container_view, args = bundle)
            }
        }


    }

}