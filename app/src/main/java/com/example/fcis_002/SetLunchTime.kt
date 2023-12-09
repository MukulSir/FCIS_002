package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [SetLunchTime.newInstance] factory method to
 * create an instance of this fragment.
 */
class SetLunchTime : Fragment() {
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
        val view: View = inflater.inflate(R.layout.fragment_set_lunch_time, container, false)

        val btnSetLunch: Button = view.findViewById(R.id.btnSetLunch)
        val spStaff: Spinner = view.findViewById(R.id.spStaff)
        val txtLunchTime:TextView = view.findViewById(R.id.txtLunchTime)
        val txtToLunchTime: TextView = view.findViewById(R.id.txtToTime)
        val txtMessage: TextView = view.findViewById(R.id.txtLunchTime)

        try {

            val db = requireActivity().openOrCreateDatabase(
                "FCISData.sqlite",
                Context.MODE_PRIVATE,
                null
            )

            //db.execSQL("Create Table Users (id integer, name text)")
            //db.execSQL("insert into users values(1,'name1')")
            val c = db.rawQuery(
                "Select distinct firstname, lastname From users",null) // where branchname = '" + spBranchName.selectedItem.toString() + "'",

            if(c.count > 0 ) {
                if (c.moveToFirst()) {
                    val arrayList: ArrayList<String> = ArrayList<String>()
                    do {
                        arrayList.add(c.getString(0))
                    }while(c.moveToNext())

                    var aa : ArrayAdapter<String>
                    aa = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item,arrayList)
                    spStaff.adapter = aa

                }
            }
        }
        catch (ex:Exception)
        {
            txtMessage.text = ex.message.toString()
        }

        btnSetLunch.setOnClickListener()
        {
            try {

                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )

                /*db.execSQL("Alter Table users add LunchTime text")
                db.execSQL("Alter Table users add toLunchTime text")*/
                //db.execSQL("insert into users values(1,'name1')")
                db.execSQL(
                    "update table users set LunchTime = '" + txtLunchTime.text.toString() + "', " +
                            " toLunchTime = '" + txtToLunchTime.text.toString() + "' " +
                            " where firstname = '" + spStaff.selectedItem.toString() + "'")

                /*val c = db.rawQuery(
                    "update table users set LunchTime = '" + txtLunchTime.text.toString() + "', " +
                            " toLunchTime = '" + txtToLunchTime.text.toString() + "' " +
                            " where firstname = '" + spStaff.selectedItem.toString() + "'",null)


                if(c.count > 0 ) {
                    if (c.moveToFirst()) {
                        val arrayList: ArrayList<String> = ArrayList<String>88
                        do {
                            arrayList.add(c.getString(0))
                        }while(c.moveToNext())

                        var aa : ArrayAdapter<String>
                        aa = ArrayAdapter<String>(requireActivity(), android.R.layout.simple_spinner_item,arrayList)
                        spStaff.adapter = aa

                    }
                }*/
            }
            catch (ex:Exception)
            {
                //txtMessage.text = ex.message.toString()
            }

           /* val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view, Dashboard())
            transaction.disallowAddToBackStack()
            transaction.commit()*/
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
         * @return A new instance of fragment SetLunchTime.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            SetLunchTime().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}