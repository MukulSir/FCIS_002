package com.example.fcis_002

import android.content.ClipData.Item
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.GridView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
/*private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"*/

/**
 * A simple [Fragment] subclass.
 * Use the [WalkInList.newInstance] factory method to
 * create an instance of this fragment.
 */
class WalkInList : Fragment() {
    // TODO: Rename and change types of parameters
    //private var param1: String? = null
    //private var param2: String? = null



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            //param1 = it.getString(ARG_PARAM1)
            //param2 = it.getString(ARG_PARAM2)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        container?.removeAllViews()
        // Inflate the layout for this fragment
        var view:View =  inflater.inflate(R.layout.fragment_walk_in_list, container, false)

        (activity as AppCompatActivity).supportActionBar?.setTitle("Walk-in List")

        var gv01: GridView = view.findViewById(R.id.gv01)
        var tv001: TextView = view.findViewById(R.id.tv001)
        var al01:ArrayList<String> = ArrayList<String>()
        var al02:ArrayList<String> = ArrayList<String>()
        var al03:ArrayList<String> = ArrayList<String>()

        var varFirstName: String = ""
        var varID:String = "0"
        var varLastName: String = ""
        var varNote: String = ""
        var varDt:String = ""
        var varTm:String = ""

        val transaction = activity?.supportFragmentManager!!.beginTransaction()
        var adapter = ArrayAdapter(requireContext(), R.layout.fragment_walk_in_data, R.id.tv01, al01)
        var adapter1 = ArrayAdapter(requireContext(), R.layout.fragment_walk_in_data, R.id.txtWalkInID, al02)


        gv01.adapter = adapter
        try {
            val db = requireActivity().openOrCreateDatabase(
                "FCISData.sqlite",
                Context.MODE_ENABLE_WRITE_AHEAD_LOGGING,
                null
            )

            db.execSQL("Create Table if not exists branches(branchname text)")
            //db.execSQL("Drop Table Users")
            db.execSQL("CREATE TABLE IF NOT EXISTS Users (" +
                                " id integer PRIMARY KEY AUTOINCREMENT, " +
                                " firstname text, lastname text, roletype text, " +
                                " branchname text, email text, password text, " +
                                " remark text, deleted text)")

            var c = db.rawQuery(
                "Select id, FirstName, LastName, note, dt, tm \n" +
                        "From Appointments order by substr(dt,7)||substr(dt,4,2)||substr(dt,1,2) desc, id desc",
                null
            )
            c.moveToFirst()
            val id = arrayOf(c.count)
            var i = "0"
            var x: String = ""
            var count = 0

            var item:Item

            var xx = 1
            if (c.count > 0) {
                c.moveToFirst()
                do
                {
                    varID = c.getInt(0).toString()
                    varFirstName = c.getString(1).toString()
                    varLastName = c.getString(2).toString()
                    varNote = c.getString(3).toString()
                    varDt = c.getString(4).toString()
                    varTm = c.getString(5).toString()

                    al01.add(varFirstName + " " + varLastName + "\n" + varDt + " " + varTm + "\n(" + varID + ")" + c.count.toString())
                    al02.add(varFirstName)
                    al03.add(varLastName)
                }while(c.moveToNext())
            }

            c.close()


        }
        catch (ex:Exception)
        {
            tv001.text = "Er#101"
            tv001.text = ex.message.toString()
        }

        //var tv001:TextView = view.findViewById(R.id.tv001)
        /*gv01.onItemClickListener = AdapterView.OnItemClickListener { _, _, position, _ ->
            // inside on click method we are simply displaying
            // a toast message with course name.
            tv001.text = position.toString()
            //tv001.text = "update appointments set remark = 'selected' where deleted != 'Yes' and id = " + varID.toString() + " "
            try
            {
                val db = requireActivity().openOrCreateDatabase(
                    "FCISData.sqlite",
                    Context.MODE_PRIVATE,
                    null
                )
                var item:TextView = gv01.getChildAt(position).findViewById<TextView?>(R.id.tv01)
                varID = item.text.toString()


*//*
                var item1:TextView = gv01.getChildAt(position).findViewById<TextView?>(R.id.txtWalkInID)
                varID = item1.text.toString()
*//*
                var item1:TextView = gv01.getChildAt(position).findViewById<TextView?>(R.id.txtWalkInID)
                item1.text = "ram"

                //var gtv001:TextView = gv01.findViewById(R.id.txtWalkInID)
                //gtv001.text = "ram"

                tv001.text = "update appointments set remark = 'selected' where deleted != 'Yes' and id = " + varID.toString().substring(varID.toString().indexOf("(")+1, varID.indexOf(")") ) + " " //item.text.toString()

                db.execSQL("Create Table if not exists appointments (id integer PRIMARY KEY AUTOINCREMENT, firstname text, lastname, phonenumber, queryfor text, staff text, note text, dt date, tm time, remark, deleted)")
                db.execSQL("update appointments set remark = '' where (deleted != 'Yes' or deleted is null) ")
                db.execSQL("update appointments set remark = 'selected' where (deleted != 'Yes' or deleted is null) and id = " + varID.toString().substring(varID.toString().indexOf("(")+1, varID.indexOf(")") ) + " ")
            }
            catch (ex:Exception)
            {
                tv001.text = ex.message.toString()
            }
            val transaction = activity?.supportFragmentManager!!.beginTransaction()
            transaction.replace(R.id.fragment_container_view, Reschedule())
            //transaction.disallowAddToBackStack()
            transaction.addToBackStack("FRAGMENT_7")
            transaction.commit()
        }*/
        return view
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment WalkInList.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            WalkInList().apply {
                arguments = Bundle().apply {
                    //putString(ARG_PARAM1, param1)
                    //putString(ARG_PARAM2, param2)
                }
            }
    }
}