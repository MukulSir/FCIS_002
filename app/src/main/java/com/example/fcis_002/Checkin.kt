package com.example.fcis_002

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import java.text.SimpleDateFormat
import java.util.Date

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"
lateinit var tv2:TextView

/**
 * A simple [Fragment] subclass.
 * Use the [Checkin.newInstance] factory method to
 * create an instance of this fragment.
 */
class Checkin : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var g: GridLayout

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
        var view:View = inflater.inflate(R.layout.fragment_checkin, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(" Walk-in List")
        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.edit)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ff3459")))

        tv2 = view.findViewById(R.id.textView2)

        //tv2.text = "sdf"
        g = view.findViewById(R.id.gl1)
        //g.addView(x)
        getlist()
        //tv2.text = "300"
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
            db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                    " id integer PRIMARY KEY AUTOINCREMENT, " +
                    " firstname text, lastname text, roletype text, " +
                    " branchname text, email text, password text, " +
                    " remark text, deleted text)")

            val c = db.rawQuery(
                "Select id, FirstName, LastName, note, dt, tm \n" +
                        "From Appointments where remark == 'Checked-in' order by substr(dt,7)||substr(dt,4,2)||substr(dt,1,2)  desc, id desc",
                null)

            var i = "0"
            if (c.count > 0) {
                c.moveToFirst()
                do
                {
                    var ID0 = c.getInt(0).toString()
                    var FirstName = c.getString(1).toString()
                    var LastName = c.getString(2).toString()
                    var Note = c.getString(3).toString()
                    var Dt = c.getString(4).toString()
                    var Tm = c.getString(5).toString()

                    /*al01.add(
                        varFirstName + " " + varLastName + "\n" + varDt + " " + varTm + "\n(" + varID + ")" + c.count.toString() )
                    al02.add(varFirstName)
                    al03.add(varLastName)*/

                    addviews(FirstName + " " + LastName + "\n" + Dt + " " + Tm + "\n(" + ID0 + ")",ID0)
                }while(c.moveToNext())
                c.close()
            }
        }
        catch (ex:Exception)
        {
            tv2.text = "301"
        }
    }

    private fun addviews(data:String,ID1:String) {
        var x:View = layoutInflater.inflate(R.layout.fragment_check_in_data,null,false)
        var b: Button = x.findViewById(R.id.btnNew)
        var t2: TextView = x.findViewById(R.id.txtWalkInID)
        var t1:TextView = x.findViewById(R.id.tv01)

        b.visibility = View.VISIBLE

        t1.setText(data)
        t2.setText(ID1.toString())

        b.setOnClickListener()
        {
            try
            {
                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )
                var item: EditText = x.findViewById(R.id.txtWalkInID)
                var ID2 = item.text.toString()

                db.execSQL("update appointments set remark='-'  where (deleted != 'Yes' or deleted is null) and id = " + ID2.toString())
            }
            catch (ex:Exception)
            {
                tv2.text = "302"
            }
            removeView(x)
        }

        var b1:Button = x.findViewById(R.id.btnSubmit)
        b1.setOnClickListener()
        {
            try
            {
                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )
                var item: EditText = x.findViewById(R.id.txtWalkInID)
                var ID3 = item.text.toString()

                val sdf = SimpleDateFormat("dd/M/yyyy")
                val stf = SimpleDateFormat("hh:mm:ss")

                val currentDate = sdf.format(Date())
                val currentTime = stf.format(Date())

                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("update appointments set remark = 'Checked-out', dt = '" + currentTime + "', tm = '" + currentTime.toString() + "' where (deleted != 'Yes' or deleted is null) and id = " + ID3.toString() + " ")
            }
            catch (ex:Exception)
            {
                tv2.text = "303"
            }
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view, Checkin())
            transaction.disallowAddToBackStack()
            //transaction.addToBackStack("FRAGMENT_7")
            transaction.commit()
        }
        g.addView(x)
    }

    private fun removeView(x: View) {
        g.removeView(x)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Checkin.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Checkin().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}