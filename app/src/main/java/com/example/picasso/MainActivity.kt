package com.example.picasso

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import okhttp3.*
import org.json.JSONObject
import java.io.IOException



class MainActivity : AppCompatActivity() {

    var imageList : List<ItemOfList> = listOf<ItemOfList>()
    var test : MutableList<ItemOfList> = mutableListOf<ItemOfList>()
    private val client = OkHttpClient()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        test.clear()

        for(count in 1..5)
        {
            Log.d("Debug_Count", count.toString())
            run("https://aws.random.cat/meow")
        }

        while (true)
        {
            if(test.size == 5)
            {
                Thread.sleep(1000)
                break
            }
        }

        var adapter = Adapter(test.toList())

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(applicationContext)
        recyclerView.setHasFixedSize(true)

        recyclerView.adapter = adapter

        Log.d("Debug_test_size", test.size.toString())

    }


    private fun run(url:String){
        val request = Request.Builder().url(url).build()
        client.newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
                val json = response.body()?.string()
                val jsonImage = (JSONObject(json).get("file")).toString()
                this@MainActivity.runOnUiThread {
                    if (response.isSuccessful) {
                        Log.d("Debug_Url_Signal", jsonImage)
                    }
                }
                test.add(ItemOfList(jsonImage))
                Log.d("Debug_test_size", test.size.toString())

            }

        })
    }
}
