package com.example.fcis_002

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Dashboard.newInstance] factory method to
 * create an instance of this fragment.
 */
class Dashboard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            param1 = it.getString(ARG_PARAM1)
            param2 = it.getString(ARG_PARAM2)
        }

    }
    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.setTitle(" Fast Check-in System")
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?
    {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_dashboard, container, false)


       /* (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)*/

        (activity as AppCompatActivity).supportActionBar?.setTitle(" Dashboard")
        (activity as AppCompatActivity).supportActionBar?.setIcon(null)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#5934ff")))

        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        transaction.addToBackStack("FRAGMENT_5")

        val b1 : Button =  view.findViewById(R.id.b1)

        b1.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, Walkin())
            transaction.commit()
        }

        val b2 : Button =  view.findViewById(R.id.b2)

        b2.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, TestGV())
            transaction.commit()
        }


        val b3 : Button =  view.findViewById(R.id.b3)

        b3.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, BranchMatrics())
            transaction.commit()
        }

        val b4 : Button =  view.findViewById(R.id.b4)

        b4.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, SetLunchTime())
            transaction.commit()
        }

        var btnSetBranch:Button = view.findViewById(R.id.btnSetBranch)
        btnSetBranch.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, ManageBranch())
            transaction.commit()
        }

        val btnManageUsers : Button =  view.findViewById(R.id.btnManageUsers)

        btnManageUsers.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, UserManagement())
            transaction.commit()
        }

        // Return the fragment view/layout
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BlankFragment2.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Dashboard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}