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

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER


/**
 * A simple [Fragment] subclass.
 * Use the [TestGV.newInstance] factory method to
 * create an instance of this fragment.
 */
class TestGV : Fragment() {

    override fun onDestroy() {
        super.onDestroy()
        (activity as AppCompatActivity).supportActionBar?.setTitle(" Fast Check-in System")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }
    lateinit var g:GridLayout

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        var view:View= inflater.inflate(R.layout.fragment_test_g_v, container, false)
        (activity as AppCompatActivity).supportActionBar?.setTitle(" Walk-in List")
        (activity as AppCompatActivity).supportActionBar?.setIcon(R.drawable.edit)
        (activity as AppCompatActivity).supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#ff3459")))


        g = view.findViewById(R.id.gl1)
        //g.addView(x)
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
                db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                        " id integer PRIMARY KEY AUTOINCREMENT, " +
                        " firstname text, lastname text, roletype text, " +
                        " branchname text, email text, password text, " +
                        " remark text, deleted text)")

                val c = db.rawQuery(
                "Select id, FirstName, LastName, note, dt, tm \n" +
                        "From Appointments order by substr(dt,7)||substr(dt,4,2)||substr(dt,1,2) desc, id desc",
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
        var x:View = layoutInflater.inflate(R.layout.fragment_walk_in_data,null,false)
        var b:Button = x.findViewById(R.id.btnNew)
        var t2:TextView = x.findViewById(R.id.txtWalkInID)
        var t1:TextView = x.findViewById(R.id.tv01)

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

                db.execSQL("delete from appointments  where (deleted != 'Yes' or deleted is null) and id = " + ID2.toString())
            }
            catch (ex:Exception)
            {

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

                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("update appointments set remark = '' where (deleted != 'Yes' or deleted is null) ")
                db.execSQL("update appointments set remark = 'selected' where (deleted != 'Yes' or deleted is null) and id = " + ID3.toString() + " ")
            }
            catch (ex:Exception)
            {

            }
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view, Reschedule())
            transaction.disallowAddToBackStack()
            //transaction.addToBackStack("FRAGMENT_7")
            transaction.commit()
        }
        g.addView(x)
    }

    private fun removeView(x: View) {
        g.removeView(x)
    }


}