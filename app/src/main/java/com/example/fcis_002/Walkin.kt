package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Walkin.newInstance] factory method to
 * create an instance of this fragment.
 */
class Walkin : Fragment() {
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
    ): View? {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_walkin, container, false)

        (activity as AppCompatActivity).supportActionBar?.setTitle(" Walk-in")

        val spBranchName: Spinner = view.findViewById(R.id.spBranchName)

        val txtFirstName: EditText = view.findViewById(R.id.txtFirstName)
        val txtLastName: EditText = view.findViewById((R.id.txtLastName))
        val txtPhoneNumber: EditText = view.findViewById((R.id.txtPhoneNumber))
        val spQueryFor: Spinner = view.findViewById((R.id.spBranchName))
        val spStaff : Spinner = view.findViewById((R.id.spStaff))
        val txtNote: EditText = view.findViewById((R.id.txtNote))
        val txtDate: EditText = view.findViewById((R.id.txtDate))
        val txtTime: EditText = view.findViewById((R.id.txtTime))

        val sdf = SimpleDateFormat("dd/M/yyyy")
        val stf = SimpleDateFormat("hh:mm:ss")

        val currentDate = sdf.format(Date())
        val currentTime = stf.format(Date())

        txtDate.setText(currentDate.toString())
        txtTime.setText(currentTime.toString())
        val db = requireActivity().openOrCreateDatabase(
            "FCISData.sqlite",
            Context.MODE_PRIVATE,
            null
        )

        var c = db.rawQuery("Select distinct branchname from branches",null)
        if(c.count>0)
        {
            val arrayList: ArrayList<String> = ArrayList()
            c.moveToFirst()
            do {
                arrayList.add((c.getString(0)))
            }while(c.moveToNext())

            var aa:ArrayAdapter<String>
            aa = ArrayAdapter<String>(requireActivity(),R.layout.fragment_spinner_item,arrayList)
            spBranchName.adapter = aa
        }
        c.close()

        val btnSignUp:Button = view.findViewById(R.id.btnSubmit)

        btnSignUp.setOnClickListener()
        {
            try {

                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )

                //db.execSQL("Create Table Users (id integer, name text)")
                //db.execSQL("insert into users values(1,'name1')")
                //db.execSQL("drop table appointments")
                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("insert into appointments (firstname, lastname, phonenumber, queryfor, staff, note, dt, tm) " +
                        "values('" + txtFirstName.text.toString() + "', " +
                        "'" + txtLastName.text.toString() + "', " +
                        "'" + txtPhoneNumber.text.toString() + "', " +
                        "'" + spQueryFor.selectedItem.toString() + "', " +
                        "'" + spStaff.selectedItem.toString() + "', " +
                        "'" + txtNote.text.toString() + "', " +
                        "'" + txtDate.text.toString() + "', " +
                        "'" + txtTime.text.toString() + "' )")
            }
            catch (ex:Exception)
            {
                txtFirstName.setText(ex.message.toString())
            }
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view,NewDashBoard())
            transaction.commit()
        }


        spBranchName?.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
                    txtFirstName.setText("New")
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                try {
                    val db = requireActivity().openOrCreateDatabase(
                        "FCISData.sqlite",
                        Context.MODE_PRIVATE,
                        null)
                    //db.execSQL("Create Table Users (id integer, name text)")
                    //db.execSQL("insert into users values(1,'name1')")
                    var c = db.rawQuery("Select distinct firstname, " +
                            "lastname From users where branchname = '" +
                            spBranchName.selectedItem.toString() + "'",
                        null)

                    if(c.count > 0 ) {
                        if (c.moveToFirst()) {
                            val arrayList: ArrayList<String> = ArrayList<String>()
                            do {
                                arrayList.add(c.getString(0))
                            }while(c.moveToNext())
                            var aa : ArrayAdapter<String>
                            aa = ArrayAdapter<String>(requireActivity(), R.layout.fragment_spinner_item,arrayList)
                            spStaff.adapter = aa
                            //txtFirstName.setText(arrayList[0].toString())
                        }
                    }
                    c.close()
                }
                catch (ex:Exception)
                {
                    txtFirstName.setText(ex.message.toString())
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
         * @return A new instance of fragment Walkin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Walkin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}