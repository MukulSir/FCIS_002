package com.example.fcis_002

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TableLayout
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Forgot_Password.newInstance] factory method to
 * create an instance of this fragment.
 */
class Forgot_Password : Fragment() {
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
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_forgot__password, container, false)


        /*(activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)*/



        val txtTitle: TextView = view.findViewById(R.id.txtTitle)
        val txtsid: TextView = view.findViewById(R.id.txtsid)
        val txteid: TextView = view.findViewById(R.id.txteid)
        val tableLayout: TableLayout = view.findViewById(R.id.tableLayout)

        val btnResetPassword: Button = view.findViewById(R.id.btnRestPassword)
        val txtStaffId: EditText = view.findViewById(R.id.txtStaffID)
        val txtEmail: EditText = view.findViewById(R.id.txtEmail)
        val txtMessage: TextView = view.findViewById(R.id.txtMessage)

        var sid: String = ""
        var eid: String = ""

        btnResetPassword.setOnClickListener()
        {
                if (txtTitle.text.toString() == "Forgot Password") {
                    try {
                        val db = requireActivity().openOrCreateDatabase(
                            "FCISData.sqlite",
                            Context.MODE_PRIVATE,
                            null
                        )
                        //db.execSQL("Create Table Users (id integer, name text)")
                        //db.execSQL("insert into users values(1,'name1')")

                        val c = db.rawQuery(
                            "Select * From users where id=" + txtStaffId.text.toString() + " and `email` = '" + txtEmail.text.toString() + "'",
                            null
                        )
                        if (c.count > 0) {
                            txtMessage.text = "Imcorrect Credentials ... "
                            if (c.moveToFirst()) {
                                txtsid.text = "New Password"
                                txteid.text = "Confirm Password"
                                txtTitle.text = "Reset Password"

                                sid = txtStaffId.text.toString()
                                eid = txtEmail.text.toString()

                                txtStaffId.text.clear()
                                txtEmail.text.clear()

                                txtStaffId.hint = "New Password"
                                txtEmail.hint = "Confirm Password"

                                txtMessage.text = "Enter New Password"

                                txtTitle.setBackgroundColor(Color.BLACK)
                                tableLayout.setBackgroundColor(Color.parseColor("#ffc0cb"))

                                /*val transaction = activity?.supportFragmentManager!!.beginTransaction()
                                transaction.replace(R.id.fragment_container_view, ResetPassword())
                                transaction.disallowAddToBackStack()
                                transaction.commit()*/
                            } else {
                                txtMessage.text = "Imcorrect Credentials ... "
                            }

                        }
                        c.close()
                        db.close()
                    } catch (ex: Exception) {
                        txtMessage.text = "Error (102) !!!"
                        txtMessage.text = ex.message.toString()
                    }
                }
                else
                {
                    if (txtStaffId.text.toString() != txtEmail.text.toString())
                        txtMessage.text = "Both passwords must be equal ... "
                    else {
                        try {
                            val db = requireActivity().openOrCreateDatabase(
                                "FCISData.sqlite",
                                Context.MODE_PRIVATE,
                                null
                            )
                            //db.execSQL("Create Table Users (id integer, name text)")
                            //db.execSQL("insert into users values(1,'name1')")

                            db.execSQL("update users set password = '" + txtEmail.text.toString()  + "' where id=" + sid.toString() + " and `email` = '" + eid + "'")
                            db.close()
                            txtMessage.text = "Done"

                            val transaction = activity?.supportFragmentManager!!.beginTransaction()
                            transaction.replace(R.id.fragment_container_view, Login())
                            transaction.disallowAddToBackStack()
                            transaction.commit()

                        } catch (ex: Exception) {
                            txtMessage.text = "Error (102) !!!"
                            txtMessage.text = ex.message.toString()
                        }
                    }
                }
        }


        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Forgot_Password.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Forgot_Password().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}