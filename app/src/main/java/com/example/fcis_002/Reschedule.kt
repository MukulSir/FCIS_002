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
 * Use the [Reschedule.newInstance] factory method to
 * create an instance of this fragment.
 */
class Reschedule : Fragment() {
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
        var view:View = inflater.inflate(R.layout.fragment_reschedule, container, false)

        (activity as AppCompatActivity).supportActionBar?.setTitle("Reschedule - Walk-in")
        (activity as AppCompatActivity).actionBar?.setDisplayShowHomeEnabled(true)
        (activity as AppCompatActivity).actionBar?.setIcon(R.drawable.edit)

        var btnSave:Button =  view.findViewById(R.id.btnSave)
        var btnDelete:Button = view.findViewById(R.id.btnDelete)

        val spBranchName: Spinner = view.findViewById(R.id.spBranchName)

        var varID:String = ""
        var txtFirstName: EditText = view.findViewById(R.id.txtFirstName)
        var txtLastName: EditText = view.findViewById((R.id.txtLastName))
        var txtPhoneNumber: EditText = view.findViewById((R.id.txtPhoneNumber))
        var spQueryFor: Spinner = view.findViewById((R.id.spBranchName))
        var spStaff : Spinner = view.findViewById((R.id.spStaff))
        var txtNote: EditText = view.findViewById((R.id.txtNote))
        var txtDate: EditText = view.findViewById((R.id.txtDate))
        var txtTime: EditText = view.findViewById((R.id.txtTime))

        var sdf = SimpleDateFormat("dd/M/yyyy")
        var stf = SimpleDateFormat("hh:mm:ss")

        var currentDate = sdf.format(Date())
        var currentTime = stf.format(Date())

        spStaff.adapter = null

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

            var aa: ArrayAdapter<String>
            aa = ArrayAdapter<String>(requireActivity(),R.layout.fragment_spinner_item,arrayList)
            spBranchName.adapter = aa
        }

        c.close()

        fun getStaff(branch:String) : ArrayList<String>
        {
            var a:Int = 0
            var sa02  = ArrayList<String>()

            try {

                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null)
                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                var c = db.rawQuery("select distinct firstname from users where branchname = '" + branch + "'",null)

                //txtFirstName.setText(c.count.toString())

                if (c.count > 0)
                {
                    c.moveToFirst()
                    do
                    {
                        sa02.add(c.getString(0))
                        //sa01[a] = c.getString(0)
                        a++
                    }while(c.moveToNext())
                }
                //txtFirstName.setText(a.toString())
            }
            catch (ex:Exception)
            {
                txtLastName.setText("Er#200")
                //txtLastName.setText("ttt" + ex.message.toString() )
            }
            return sa02
        }

        try {

            val db = requireActivity().openOrCreateDatabase(
                "FCISData.sqlite",
                Context.MODE_PRIVATE,
                null
            )
            db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
            var c = db.rawQuery("select id, firstname, lastname, phonenumber, queryfor, staff, note, dt, tm from appointments where remark = 'selected'" ,null)

            txtNote.setText(c.count.toString())
            if(c.count>0)
            {
                c.moveToFirst()
                do
                {
                    varID = c.getInt(0).toString()
                    txtFirstName.setText(c.getString(1))
                    txtLastName.setText(c.getString(2))
                    txtPhoneNumber.setText(c.getString(3))
                    spQueryFor.setSelection(resources.getStringArray(R.array.branches).indexOf(c.getString(4)))

                    //txtFirstName.setText(c.getString(4))
                    var sa01 = ArrayList<String>()
                    sa01 = getStaff(c.getString(4))

                    //txtFirstName.setText(sa01.size.toString())
                    var aa : ArrayAdapter<String>
                    aa = ArrayAdapter<String>(requireActivity(), R.layout.fragment_spinner_item,sa01)
                    spStaff.adapter = aa
                    spStaff.setSelection(sa01.indexOf(c.getString(5)))

                    txtNote.setText(c.getString(6))
                    txtDate.setText(c.getString(7))
                    txtTime.setText(c.getString(8))
                }while(c.moveToNext())
            }

            c.close()
        }
        catch (ex:Exception)
        {
            txtFirstName.setText("Er#201")
            //txtFirstName.setText(ex.message.toString())
        }

        btnSave.setOnClickListener()
        {
            var txtMessage:TextView = view.findViewById(R.id.txtMessage)

            try {

                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )

            /*    db.beginTransaction()
                var ih: DatabaseUtils.InsertHelper = DatabaseUtils.InsertHelper(db,"appointments")
                ih.prepareForReplace()
                ih.bind (1,txtFirstName.text.toString())
                ih.bind (2,txtLastName.text.toString())
                ih.bind (3,txtPhoneNumber.text.toString())
                ih.bind (4,spQueryFor.selectedItem.toString())
                ih.bind (5,spStaff.selectedItem.toString())
                ih.bind (6,txtNote.text.toString())
                ih.bind (7,txtDate.text.toString())
                ih.bind (8,txtTime.text.toString())
                ih.execute()
                ih.close()
                db.setTransactionSuccessful()
                db.endTransaction()*/

                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("update appointments set " +
                        "FirstName = '" + txtFirstName.text.toString() + "', " +
                        "LastName = '" + txtLastName.text.toString() + "', " +
                        "PhoneNumber = '" + txtPhoneNumber.text.toString() + "', " +
                        "queryfor = '" + spBranchName.selectedItem.toString() + "', " +
                        "Staff = '" + spStaff.selectedItem.toString() + "', " +
                        "Note = '" + txtNote.text.toString() + "', " +
                        "Dt = '" + txtDate.text.toString() + "', " +
                        "tm = '" + txtTime.text.toString() + "' " +
                        " where remark = 'selected'")

                db.execSQL("update appointments set remark = '' where  (deleted != 'Yes' or deleted = null)")
                txtMessage.text = spStaff.selectedItem.toString()

                val transaction = activity?.supportFragmentManager!!.beginTransaction()
                transaction.replace(R.id.fragment_container_view, TestGV ())
                transaction.commit()
            }
            catch (ex:Exception)
            {
                txtMessage.setText("Er#203")
            //txtMessage.setText(ex.message.toString())
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
         * @return A new instance of fragment Reschedule.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Reschedule().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}