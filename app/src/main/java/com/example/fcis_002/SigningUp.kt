package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SigningUp.newInstance] factory method to
 * create an instance of this fragment.
 */
class SigningUp : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_signing_up, container, false)

        /*(activity as AppCompatActivity).supportActionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setDisplayHomeAsUpEnabled(true)
        //(activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).supportActionBar?.setHomeButtonEnabled(true)*/

        val txtFirstName: EditText = view.findViewById(R.id.txtFirstName)
        val txtLastName : EditText = view.findViewById(R.id.txtLastName)
        val txtEmail: EditText = view.findViewById(R.id.txtEmail)
        val spRole: Spinner = view.findViewById(R.id.spRole)
        val spBranchName: Spinner = view.findViewById(R.id.spBranchName)
        val txtPassword: EditText = view.findViewById(R.id.txtPassword)
        val txtConfirmPassword: EditText = view.findViewById(R.id.txtConfirmPassword)
        val txtMessage: TextView = view.findViewById(R.id.txtMessage)
        val b8: Button = view.findViewById(R.id.btnSignup)
        var txtTitle:TextView = view.findViewById(R.id.txtTitle)

        txtMessage.text = "Welcome .."


        val db = requireActivity().openOrCreateDatabase(
            "FCISData.sqlite",
            Context.MODE_PRIVATE,
            null
        )

        var aa: ArrayAdapter<String>

        var c = db.rawQuery("Select distinct branchname from branches",null)
        if(c.count>0)
        {
            val arrayList: ArrayList<String> = ArrayList()
            c.moveToFirst()
            do {
                arrayList.add((c.getString(0)))
            }while(c.moveToNext())

            aa = ArrayAdapter<String>(requireActivity(),android.R.layout.simple_spinner_item,arrayList)
            spBranchName.adapter = aa
        }

        c.close()

        c = db.rawQuery("select firstname, lastname, roletype, branchname, email, password, remark, deleted from users where remark = 'selected'",null)
        if (c.count>0)
        {
            c.moveToFirst()
            var txtFN1:String = ""
            var txtLN:String = ""
            var txtBN:String = ""
            var txtEM:String = ""
            var txtPD:String = ""
            var txtRM:String = ""
            var txtDT:String = ""
            var txtRT:String = ""
            var sa01 = ArrayList<String>()
            var aa = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item,sa01)

            do {
                txtFN1 = c.getString(0).toString()
                txtLN = c.getString(1)
                txtBN = c.getString(2)
                txtEM = c.getString(3)
                txtPD = c.getString(4)
                txtRM = c.getString(5)
                txtDT = c.getString(6)
                txtRT = c.getString(7)

                sa01 = getBranches()

            }while (c.moveToNext())

            txtFirstName.setText(txtFN1)
            txtLastName.setText(txtLN)
            txtEmail.setText(txtEM)
            txtPassword.setText(txtPD)
            txtConfirmPassword.setText(txtPD)
            spBranchName.setSelection(sa01.indexOf(txtBN))

            txtTitle.text = "Update User Data"

            var btnSignup:Button = view.findViewById(R.id.btnSignup)
            btnSignup.setText("Update")
        }

        b8.setOnClickListener()
        {
            if(txtPassword.text.toString() != txtConfirmPassword.text.toString())
                txtMessage.setText ("Password x Confirm Password")
            else
            {
                try {
                    val db = requireActivity().openOrCreateDatabase(
                        "FCISData.sqlite",
                        Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,
                        null
                    )
                    //db.execSQL("Drop Table Users")
                    db.execSQL("CREATE TABLE IF NOT EXISTS Users (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname text, roletype text, branchname text, email text, password text, remark text, deleted text)")
                    //db.execSQL("insert into users values(1,'name1')")
                    val c = db.rawQuery(
                        "Select * From users where email = '" + txtEmail.text.toString() + "'",
                        null
                    )
                    c.moveToFirst()
                    //e1.setText(c.getColumnIndex("ID").toString())
                    val id = arrayOf(c.count)
                    var i = 0
                    var x: String = ""
                    if (c.count > 0) {
                        /*val transaction = activity?.supportFragmentManager!!.beginTransaction()
                    transaction.replace(R.id.fragment_container_view, Dashboard())
                    transaction.disallowAddToBackStack()
                    transaction.commit()*/
                            if(txtTitle.text == "Update User Data")
                            {
                                db.execSQL(
                                    "update users set " +
                                            "firstname = '" + txtFirstName.text.toString() + "', " +
                                            "lastname = '" + txtLastName.text.toString() + "', " +
                                            "roletype = '" + spRole.selectedItem.toString() + "', " +
                                            "branchname = '" + spBranchName.selectedItem.toString() + "', " +
                                            "email = '" + txtEmail.text.toString() + "', " +
                                            "password = '" + txtPassword.text.toString() + "', " +
                                            "remark = '-', deleted = '0' where remark = 'selected'")

                                var transaction = activity?.supportFragmentManager!!.beginTransaction()
                                transaction.replace(R.id.fragment_container_view,UserManagement())
                                transaction.commit()
                            }
                            else {
                                txtMessage.text = "You are already registered ... "
                            }
                                /*if (c.moveToFirst()) {
                                do {
                                    x = c.getString(0)
                                } while (c.moveToNext())
                            }*/

                    } else {

                        db.execSQL(
                            "insert into users (firstname, lastname, roletype, branchname, email, password, remark, deleted) values( " +
                                    "'" + txtFirstName.text.toString() + "', " +
                                    "'" + txtLastName.text.toString() + "', " +
                                    "'" + spRole.selectedItem.toString() + "', " +
                                    "'" + spBranchName.selectedItem.toString() + "', " +
                                    "'" + txtEmail.text.toString() + "', " +
                                    "'" + txtPassword.text.toString() + "', " +
                                    "'-', '0')"
                        )

                        val c = db.rawQuery(
                            "Select * From users where email = '" + txtEmail.text.toString() + "'",
                            null
                        )
                        txtMessage.text = "Kindly Contact Us for UserID "
                        if(c.count>0)
                        {
                            if(c.moveToFirst())
                            {
                                x = c.getString(0)
                                txtMessage.text = "Registered ... \n Your ID is ... " + x.toString()
                            }
                        }

                       /* val transaction = activity?.supportFragmentManager!!.beginTransaction()
                        transaction.replace(R.id.fragment_container_view, SingedUp())
                        transaction.disallowAddToBackStack()
                        transaction.commit()*/
                    }
                    c.close()
                } catch (ex: Exception) {
                    /*txtFirstName.setText("update into users set( " +
                            "firstname = '" + txtFirstName.text.toString() + "', " +
                            "lastname = '" + txtLastName.text.toString() + "', " +
                            "roletype = '" + spRole.selectedItem.toString() + "', " +
                            "branchname = '" + spBranchName.selectedItem.toString() + "', " +
                            "email = '" + txtEmail.text.toString() + "', " +
                            "password = '" + txtPassword.text.toString() + "', " +
                            "remark = '-', deleted = '0') where remark = 'selected'")*/
                    txtFirstName.setText(ex.message.toString())
                }
            }
        }
        return view
    }

    private fun getBranches(): ArrayList<String> {
        var sa02 = ArrayList<String>()
        var db = requireActivity().openOrCreateDatabase("FCISData.sqlite",Context.MODE_PRIVATE,null)
        var c = db.rawQuery("Select * from Branches",null)

        if(c.count>0)
        {
            c.moveToFirst()
            do {
                sa02.add(c.getString(0))
            }while(c.moveToNext())
        }

        return sa02
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment SigningUp.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SigningUp().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}