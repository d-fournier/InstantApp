package me.dfournier.instantapp

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        account_button.setOnClickListener {
            startActivity(
                Intent(Intent.ACTION_VIEW)
                    .setClassName(this, "me.dfournier.instantapp.account_management.AccountListActivity")
            )
        }

        stock_button.setOnClickListener {
/*
            try {
                startActivity(
                    Intent(Intent.ACTION_VIEW)
                        .setClassName(this, "me.dfournier.instantapp.stock.StockActivity")
                )
            } catch (e: ActivityNotFoundException) {
                Toast.makeText(this, "Activity not found", Toast.LENGTH_SHORT).show()
            }
*/
        }
    }
}
