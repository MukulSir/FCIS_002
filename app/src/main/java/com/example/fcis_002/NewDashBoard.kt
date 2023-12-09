package com.example.fcis_002

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.GridLayout
import android.widget.ImageView
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
 * Use the [NewDashBoard.newInstance] factory method to
 * create an instance of this fragment.
 */
class NewDashBoard : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null
    lateinit var g:GridLayout
    lateinit var tv:TextView

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
        var view:View =  inflater.inflate(R.layout.fragment_new_dash_board, container, false)
        (activity as AppCompatActivity).supportActionBar?.hide()

        g = view.findViewById(R.id.glData)

        tv = view?.findViewById(R.id.txtTitle)!!


        getlist()


        var b1:ImageView = view.findViewById(R.id.imgNew)

        b1.setOnClickListener()
        {
            var transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view,Walkin())
            transaction.addToBackStack("Fragment_5")
            transaction.commit()
        }

        var b2:ImageView = view.findViewById(R.id.imgList)

        b2.setOnClickListener()
        {
            var transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view,TestGV())
            transaction.addToBackStack("Fragment_5")
            transaction.commit()
        }

        var b3:ImageView = view.findViewById(R.id.imgAppointment)

        b3.setOnClickListener()
        {
            var transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view,Checkin())
            transaction.addToBackStack("Fragment_5")
            transaction.commit()
        }

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

            val sdf = SimpleDateFormat("dd/M/yyyy")
            val stf = SimpleDateFormat("hh:mm:ss")

            val currentdate = sdf.format(Date())
            val currenttime = sdf.format(Date())

            val c = db.rawQuery(
                "Select id, FirstName, LastName, note, dt, tm \n" +
                        "From Appointments where remark != 'Checked-in' and remark != 'Checked-out' and dt >= '"+ currentdate + "' order by substr(dt,7)||substr(dt,4,2)||substr(dt,1,2) desc, id desc",
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

        }
    }

    private fun addviews(data:String,ID1:String) {
        var x:View = layoutInflater.inflate(R.layout.fragment_check_in_data,null,false)
        var b: Button = x.findViewById(R.id.btnNew)
        var t2: TextView = x.findViewById(R.id.txtWalkInID)
        var t1: TextView = x.findViewById(R.id.tv01)

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

                db.execSQL("update appointments set remark = '-'  where (deleted != 'Yes' or deleted is null) and id = " + ID2.toString())
            }
            catch (ex:Exception)
            {

            }
            removeView(x)
        }

        var b1: Button = x.findViewById(R.id.btnSubmit)
        b1.setText("Check-in")
        x.setBackgroundColor(Color.parseColor("#1155ff"))
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

                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("update appointments set remark = 'Checked-in' where (deleted != 'Yes' or deleted is null) and id = " + ID3.toString() + " ")
            }
            catch (ex:Exception)
            {

            }
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view, NewDashBoard())
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
         * @return A new instance of fragment NewDashBoard.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            NewDashBoard().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}