package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ResetPassword.newInstance] factory method to
 * create an instance of this fragment.
 */
class ResetPassword : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_reset_password, container, false)

        val btnResetPassword:Button = view.findViewById(R.id.btnRestPassword)
        val txtPassword: EditText = view.findViewById(R.id.txtPassword)
        val txtConfirmPassword: EditText = view.findViewById(R.id.txtConfirmPassword)
        val txtMessage: TextView = view.findViewById(R.id.txtMessage)
        val txtStaffId: EditText = view.findViewById(R.id.txtStaffID)
        val txtEmail: EditText = view.findViewById(R.id.txtEmail)

        btnResetPassword.setOnClickListener()
        {
            try
            {
                val db = requireActivity().openOrCreateDatabase("FCISData.sqlite", Context.MODE_PRIVATE, null)
                //db.execSQL("Create Table Users (id integer, name text)")
                //db.execSQL("insert into users values(1,'name1')")
                val c = db.rawQuery("Select * From users where id="+txtStaffId.text.toString()+" and `email` = '"+txtEmail.text.toString() + "'" ,null)
                if(c.count>0) {
                    if (c.moveToFirst())
                    {

                        val transaction = activity?.supportFragmentManager!!.beginTransaction()
                        transaction.replace(R.id.fragment_container_view, ResetPassword())
                        transaction.disallowAddToBackStack()
                        transaction.commit()
                    } else {
                        txtMessage.text = "Imcorrect Credentials ... "
                    }
                    txtMessage.text = "Imcorrect Credentials ... "
                }
                c.close()
            }
            catch (ex:Exception)
            {
                txtMessage.text = "Error (102) !!!"
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
         * @return A new instance of fragment ResetPassword.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ResetPassword().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}