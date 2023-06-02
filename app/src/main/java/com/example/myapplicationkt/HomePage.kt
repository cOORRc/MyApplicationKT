package com.example.myapplicationkt

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.myapplicationkt.databinding.ActivityHomePageBinding

class HomePage : AppCompatActivity() {
    private lateinit var frag_HomeBinding : ActivityHomePageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        frag_HomeBinding = ActivityHomePageBinding.inflate(layoutInflater)
        setContentView(frag_HomeBinding.root)
        replaceFragment(ScanProductPageFragment())

        frag_HomeBinding.bottomNavigationView.setOnItemSelectedListener {

            when(it.itemId){
                R.id.item_stock -> replaceFragment(StockPageFragment())
                R.id.item_scan_product -> replaceFragment(ScanProductPageFragment())
                R.id.item_all_product -> replaceFragment(ProductPageFragment())
                else ->{

                }
            }
            true
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()
    }

}