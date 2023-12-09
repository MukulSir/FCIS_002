package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.GridLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"


/**
 * A simple [Fragment] subclass.
 * Use the [UserManagement.newInstance] factory method to
 * create an instance of this fragment.
 */
class UserManagement : Fragment() {
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

    lateinit var g:GridLayout
    lateinit var tv01:TextView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        var view:View =  inflater.inflate(R.layout.fragment_user_management, container, false)

        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)

        (activity as AppCompatActivity).supportActionBar?.setTitle("Fast Chek-In System")

        //supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //actionBar.setCustomView(layoutInflater.inflate(R.layout.action_bar_home,null))


        g = view.findViewById(R.id.gl1)
        tv01 = view.findViewById(R.id.tv01)

        getlist()

        return view
    }

    private fun getlist() {
        try {
            val db = requireActivity().openOrCreateDatabase(
                "FCISData.sqlite",
                Context.MODE_PRIVATE,
                null
            )
            //db.execSQL("Drop Table Users")
            db.execSQL(
                "CREATE TABLE IF NOT EXISTS Users (" +
                        " id integer PRIMARY KEY AUTOINCREMENT, " +
                        " firstname text, lastname text, roletype text, " +
                        " branchname text, email text, password text, " +
                        " remark text, deleted text)"
            )

            val c = db.rawQuery(
                "Select distinct FirstName from users",
                null
            )

            tv01.text = "Users"

            var x1:String
            if(c.count>0) {
                c.moveToFirst()
                do {
                    x1 = c.getString(0)
                    addviews(x1)


                }while (c.moveToNext())
            }
        }
        catch (ex:Exception)
        {
                tv01.text = ex.message.toString()
        }
    }

    private fun addviews(u: String) {
        var x:View = layoutInflater.inflate(R.layout.fragment_user,null, false)

        var txtUserName:TextView =  x.findViewById(R.id.txtUserName)
        var btnEdit:ImageView = x.findViewById(R.id.imgEdit)
        var btnDelete:ImageView = x.findViewById(R.id.imgDelete)

        txtUserName.text = u.toString()
        //tv01.text = tv01.text.toString() + " " + u.toString()

        btnEdit.setOnClickListener()
        {

            try {
                var db = requireActivity().openOrCreateDatabase("FCISData.sqlite",Context.MODE_PRIVATE,null)
                db.execSQL("update users set remark = ''")
                db.execSQL("update users set remark = 'selected' where firstname = '" + u.toString() + "' ")
            }
            catch (ex:Exception)
            {

            }
            var transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.addToBackStack("Fragment_5")
            transaction.replace(R.id.fragment_container_view,SigningUp())
            transaction.commit()
        }

        btnDelete.setOnClickListener()
        {
            g.removeView(x)
        }
        g.addView(x)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment UserManagement.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            UserManagement().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}