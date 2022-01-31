package com.example.content_provider

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.View
import android.widget.*

class MainActivity : AppCompatActivity() {

    lateinit var  Context:Context
    lateinit var autocomplete:AutoCompleteTextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Context =this
        autocomplete=findViewById(R.id.autoComplete)

        var ed =findViewById<EditText>(R.id.ed)
        var btn=findViewById<Button>(R.id.btn)

        btn.setOnClickListener {

            var number=ed.text

            val phone_intent = Intent(Intent.ACTION_CALL)
            phone_intent.data = Uri.parse("tel:$number")
            startActivity(phone_intent)

        }
        getContect()
    }


    @SuppressLint("Range")
    fun getContect(){
        val Contects: MutableList<String> = ArrayList()
        val Cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI,null,null,null,null)
        if(Cursor != null && Cursor.moveToFirst()){
            do {
                val name =Cursor.getString(Cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                val No = Cursor.getInt(Cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))
                Log.d("getContect","name: "+name)
                Contects.add(name)
            }while (Cursor!!.moveToNext())
            Cursor.close()
        }

        autocomplete.setAdapter(ArrayAdapter(Context,android.R.layout.simple_list_item_1,Contects))
        autocomplete.threshold =1
        autocomplete.onItemSelectedListener = object : AdapterView.OnItemClickListener,
            AdapterView.OnItemSelectedListener {
            override fun onItemClick(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                Toast.makeText(applicationContext,"Contect : "+parent,Toast.LENGTH_LONG).show()
            }

            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                TODO("Not yet implemented")
            }
            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }

    }
}