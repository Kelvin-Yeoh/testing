package com.example.testing

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.net.toUri
import androidx.drawerlayout.widget.DrawerLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.rv_one.*
import kotlinx.android.synthetic.main.rv_one.view.*
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject


class MainActivity : AppCompatActivity(){

    private var email: String? = null
    private var password: String? = null
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    private val URL :String = "http://10.0.2.2/login/login.php"

    lateinit var sharedPreferences: SharedPreferences
    var PREFS_KEY = "prefs"
    var EMAIL_KEY = "email"




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.login_screen)
        title = "KotlinApp"

        etEmail = findViewById(R.id.txtEmail)
        etPassword = findViewById(R.id.txtPassword)


        sharedPreferences = getSharedPreferences(PREFS_KEY, Context.MODE_PRIVATE)
        email = sharedPreferences.getString(EMAIL_KEY, "").toString()
        // getting the recyclerview by its id
//        recyclerView = findViewById<RecyclerView>(R.id.recycler)
        // this creates a vertical layout Manager
        //recyclerView!!.layoutManager = LinearLayoutManager(this)
        //fetchingJSON()


        //navigation

//        toggle = ActionBarDrawerToggle(this, drawerLayout, R.string.open, R.string.close)
//        drawerLayout.addDrawerListener(toggle)
//        toggle.syncState()
//
//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        nav_view.setNavigationItemSelectedListener {
//
//            when(it.itemId){
//                R.id.nav_profile ->startActivity(Intent(this@MainActivity, ProfileActivity::class.java))
//            }
//            true
//        }

    }

//    private fun fetchingJSON() {
//        val recyclerview = findViewById<RecyclerView>(R.id.recycler)
//        val stringRequest: JsonArrayRequest = object : JsonArrayRequest(
//            Request.Method.GET, URLstring,
//            null, { response ->
//
//                for (i in 0 until response.length()) {
//                    val dataobj = response.getJSONObject(i)
//
//                    //adding the product to product list
//                    data.add(
//                        ItemsViewModel(
//                            dataobj.getString("image"),
//                            dataobj.getString("event_title"),
//                            dataobj.getString("meal_quantity"),
//                            dataobj.getInt("id"),
//                            dataobj.getString("event_description"),
//                            dataobj.getInt("current_meal_quantity")
//                        )
//                    )
//                }
//                val adapter = RvAdapter(data, this)
//                recyclerview.adapter = adapter
//            },
//            Response.ErrorListener { error ->
//                Toast.makeText(
//                    this@MainActivity,
//                    error.toString().trim { it <= ' ' },
//                    Toast.LENGTH_SHORT
//                ).show()
//            }) {
//        }
//        val requestQueue = Volley.newRequestQueue(applicationContext)
//        requestQueue.add(stringRequest)
//    }

//    override fun onItemClick(position: Int) {
//        Toast.makeText(this, "Event " +position+ " Clicked", Toast.LENGTH_SHORT).show()
//        val intent = Intent(this, EventDetail::class.java)
//        intent.putExtra("image", data[position].image)
//        intent.putExtra("textTitle", data[position].textViewTitle)
//        intent.putExtra("textMeal", data[position].textViewMeal)
//        intent.putExtra("id", data[position].id)
//        intent.putExtra("eventDescription", data[position].eventDescription)
//        startActivity(intent)
//    }



    fun login(view: View?) {
        var email = etEmail.getText().toString().trim()
        var password = etPassword.getText().toString().trim()
        if (email != "" && password != "") {
            val stringRequest: StringRequest = object : StringRequest(
                Request.Method.POST, URL,
                Response.Listener { response ->
                    Log.d("res", response)
                    if (response == "success") {
                        val editor: SharedPreferences.Editor=sharedPreferences.edit()
                        editor.putString(EMAIL_KEY, etEmail.text.toString())
                        editor.apply()
                        val intent = Intent(this@MainActivity, ProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else if (response == "failure") {
                        Toast.makeText(
                            this@MainActivity,
                            "Invalid Login Id/Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                },
                Response.ErrorListener { error ->
                    Toast.makeText(
                        this@MainActivity,
                        error.toString().trim { it <= ' ' },
                        Toast.LENGTH_SHORT
                    ).show()
                }) {
                @Throws(AuthFailureError::class)
                override fun getParams(): Map<String, String>? {
                    val data: MutableMap<String, String> = HashMap()
                    data["email"] = email
                    data["password"] = password
                    return data
                }
            }
            val requestQueue = Volley.newRequestQueue(applicationContext)
            requestQueue.add(stringRequest)
        } else {
            Toast.makeText(this, "Fields can not be empty!", Toast.LENGTH_SHORT).show()
        }
    }


    fun register(view: View?) {
        val intent = Intent(this, Register::class.java)
        startActivity(intent)
        finish()
    }

//    fun login(view: View?) {
//        val intent = Intent(this, Login::class.java)
//        startActivity(intent)
//        finish()
//    }
//
//    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        if(toggle.onOptionsItemSelected(item)){
//            return true
//        }
//        return super.onOptionsItemSelected(item)
//    }
}

