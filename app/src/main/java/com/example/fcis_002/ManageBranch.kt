package com.example.fcis_002

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import androidx.fragment.app.Fragment

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [ManageBranch.newInstance] factory method to
 * create an instance of this fragment.
 */
class ManageBranch : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var ll01:LinearLayout

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
        var view:View =  inflater.inflate(R.layout.fragment_manage_branch, container, false)

        ll01 = view.findViewById(R.id.ll01)

        var b:Button = view.findViewById(R.id.btnAdd)

        var list = listOf<String>("Operator", "Clerk", "Admin")
        var adapter = ArrayAdapter<String> (requireContext(),R.layout.fragment_spinner_item,list)
        var sp1:Spinner = view.findViewById(R.id.spDesignation)

        sp1.adapter = adapter

        b.setOnClickListener()
        {
            //ll01.addView(ll01)
            var t1: EditText = view.findViewById(R.id.txtBranchName)

            if(t1.text.toString() != null && t1.text.toString() != "") {
                try {
                    var db = requireActivity().openOrCreateDatabase(
                        "FCISData.sqlite",
                        Context.MODE_PRIVATE,
                        null
                    )
                    db.execSQL("CREATE TABLE IF NOT EXISTS branches (branchname text)")
                    db.execSQL("Insert into branches (branchname) values('" + t1.text.toString() + "')")
                } catch (ex: Exception) {
                    var tv001: TextView = view.findViewById(R.id.tv001)
                    tv001.text = ex.message.toString()
                }

                addview(t1.text.toString())
            }
            else
            {
                var tv001:TextView = view.findViewById(R.id.tv001)
                tv001.text = "Empty ..."
            }
        }

        try{
                var db = requireActivity().openOrCreateDatabase("FCISData.sqlite",Context.MODE_PRIVATE,null)
                db.execSQL("CREATE TABLE IF NOT EXISTS branches (branchname text)")
                val c = db.rawQuery("Select distinct branchname from branches",null)

                if(c.count>0)
                {
                    if(c.moveToFirst()) {
                        do {
                            addview(c.getString(0))
                        } while (c.moveToNext())
                    }
                }
        }
        catch (ex:Exception)
        {
            var tv001:TextView = view.findViewById(R.id.tv001)
            tv001.text = ex.message.toString() + " ..."
        }

        return view
    }

    private fun addview(bn:String) {
        var x:View = layoutInflater.inflate(R.layout.fragment_manage_branches,null,false)
        var b:Button = x.findViewById(R.id.btnDelete)
        var t: EditText = x.findViewById(R.id.txtBranchName)


        t.setText(bn.toString())
        b.setOnClickListener()
        {
            ll01.removeView(x)

            try{
                var db  = requireActivity().openOrCreateDatabase("FCISData.sqlite",Context.MODE_PRIVATE,null)
                db.execSQL("CREATE TABLE IF NOT EXISTS branches (branchname text)")
                db.execSQL("delete from branches where branchname = '" + x.findViewById<EditText>(R.id.txtBranchName).text.toString() +  "'")
            }
            catch (ex:Exception)
            {
                var txtTitle:TextView = view?.findViewById(R.id.txtTitle)!!
                txtTitle.text = ex.message.toString()
            }
        }
        ll01.addView(x)
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment ManageBranch.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            ManageBranch().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}