package com.junka.form.main

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat.startActivity
import com.junka.form.databinding.ActivityMainBinding

class MainActivity: AppCompatActivity() {

    companion object{
        fun create(context : Context){
            Intent(context,MainActivity::class.java).run{
                startActivity(context,this,null)
            }
        }
    }

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}