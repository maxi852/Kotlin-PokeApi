package com.example.pokedexapi

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.provider.CalendarContract
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.marginEnd
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import kotlinx.coroutines.launch

class DetailActivity : AppCompatActivity() {
    private lateinit var linearLayout: LinearLayout
    private lateinit var linearLayoutImgs: LinearLayout
    private lateinit var constraintLayout: ConstraintLayout
    private lateinit var typeDao: TypesDao
    private lateinit var evoDao: EvoDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        constraintLayout = findViewById(R.id.detail_act)
        linearLayout = findViewById(R.id.layout_type)
        linearLayoutImgs = findViewById(R.id.layout_evo)

        val database = AppDatabase.getDatabase(this)
        typeDao = database.typesDao()
        evoDao = database.evoDao()

        val name = intent.getStringExtra("name")
        val idPokemon = intent.getStringExtra("id")
        val img = intent.getStringExtra("img")

        val nameTextView: TextView = findViewById(R.id.detail_name)
        val imgView: ImageView = findViewById(R.id.detail_img)
        nameTextView.text = name

        Glide.with(this).load(img).into(imgView)

        lifecycleScope.launch {
            val types = typeDao.getTypesByName(name)

            for (typePok in types) {
                createEditText(typePok.type)
            }

            val evolutions = evoDao.getEvoChain(name)
            for(ev in evolutions){
                Log.i("MainActivity","${ev.name}")
                createImageView(ev.img)
            }
            /*
          var previousViewId = R.id.detail_type // Primer elemento será debajo de detail_type
          for ((index, typePk) in types.withIndex()) {
              previousViewId = createEditTextNextTo(typePk.type, previousViewId)
          }
          */

        }
    }

    private fun createImageView(text: String){
        val imageView = ImageView(this).apply {
            id = View.generateViewId()
            layoutParams = LinearLayout.LayoutParams(
                300, // Ancho definido en píxeles
                300  // Altura definida en píxeles
            )
            setPadding(8, 8, 8, 8)
            setBackgroundColor(Color.TRANSPARENT) // Si quieres asegurar que el fondo sea transparente
        }
        Glide.with(this)
            .load(text)
            .into(imageView)
        linearLayoutImgs.addView(imageView)
    }
    private fun createEditText(text: String) {
        val colorText = when (text) {
            "grass" -> Color.parseColor("#07530A")
            "poison" -> Color.parseColor("#532F95")
            "fire" -> Color.parseColor("#D51010")
            "flying" -> Color.parseColor("#FFFFFFFF")
            "water" -> Color.parseColor("#2196F3")
            else -> Color.BLACK
        }
        val editText = EditText(this).apply {
            setText(text)
            textSize = 18f
            isEnabled = false
            setTextColor(colorText)
            paintFlags = paintFlags and Paint.UNDERLINE_TEXT_FLAG.inv()
            setPadding(8, 8, 8, 8)
        }
        linearLayout.addView(editText)
    }


}