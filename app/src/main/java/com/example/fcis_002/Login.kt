package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Login.newInstance] factory method to
 * create an instance of this fragment.
 */
class Login : Fragment() {
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        val view: View = inflater.inflate(R.layout.fragment_login, container, false)



        (activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(false)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(false)
        //(activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(false)


        val b8 :Button =  view.findViewById(R.id.button8)
        val e1: EditText = view.findViewById(R.id.txtName)
        val e2: EditText = view.findViewById(R.id.txtPassword)
        val txtMessage: TextView = view.findViewById(R.id.txtMessage)
        val transaction = activity?.supportFragmentManager!!.beginTransaction()

        b8.setOnClickListener()
        {

            transaction.replace(R.id.fragment_container_view, WalkInList())

            //transaction.disallowAddToBackStack()
            //transaction.commit()

            try {
                val db = requireActivity().openOrCreateDatabase("FCISData.sqlite", Context.MODE_PRIVATE, null)
                //db.execSQL("Create Table Users (id integer, name text)")
                //db.execSQL("insert into users values(1,'name1')")
                val c = db.rawQuery("Select * From users where id="+e1.text.toString()+" and `password` = '"+e2.text.toString() + "'" ,null)
                c.moveToFirst()

                //e1.setText(c.getColumnIndex("ID").toString())
                /*val id = arrayOf(c.count)
                var i = 0
                var x: String = ""*/
                if (c.count > 0)
                {
                    transaction.replace(R.id.fragment_container_view, NewDashBoard())
                    //transaction.disallowAddToBackStack()
                    transaction.addToBackStack("FRAGMENT_5")
                    transaction.commit()


                    /*if (c.moveToFirst()) {
                        do {
                            x = c.getString(0)

                        } while (c.moveToNext())
                    }*/
                }
                else
                {
                    txtMessage.setText("Imcorrect Credintials ... !")
                }
                c.close()
            }
            catch (ex:Exception)
            {
                if(e1.text.toString() == "" || e1.text.toString() == "")
                    txtMessage.setText("Name and Password Cannot Be Blank...!")
                else
                    txtMessage.setText("Error...!")
                //txtMessage.setText(ex.message.toString())
            }
        }

        val b9 :Button =  view.findViewById<Button>(R.id.button9)
        b9.setOnClickListener()
        {
            transaction.replace(R.id.fragment_container_view, Forgot_Password())
            transaction.addToBackStack("FRAGMENT_1")
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
            transaction.commit()
        }

        val b10 :Button =  view.findViewById<Button>(R.id.button10)
        b10.setOnClickListener()
        {
            var db = activity?.openOrCreateDatabase("FCISData.sqlite",Context.MODE_PRIVATE,null)
            db?.execSQL("update users set remark = ''")

            transaction.replace(R.id.fragment_container_view, SigningUp())
            transaction.addToBackStack("FRAGMENT_1")
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
         * @return A new instance of fragment BlankFragment1.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Login().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}