package com.example.adaptation

import android.content.SharedPreferences
import android.content.res.Configuration
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class MainActivity : AppCompatActivity() {

    private lateinit var imageView: ImageView
    private lateinit var changeButton: Button
    private lateinit var sharedPreferences: SharedPreferences
    private var currentImageIndex = 0

    private val images = listOf(
        R.drawable.car1,
        R.drawable.car2,
        R.drawable.car3
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        imageView = findViewById(R.id.picture)
        changeButton = findViewById(R.id.btn_change_picture)

        sharedPreferences = getSharedPreferences("AppPreferences", MODE_PRIVATE)

        currentImageIndex = sharedPreferences.getInt("saved_image_index", 0)
        imageView.setImageResource(images[currentImageIndex])

        changeButton.setOnClickListener { onChangePictureClick() }

        adjustLayoutForOrientation(resources.configuration.orientation)
    }

    private fun onChangePictureClick() {
        currentImageIndex = (currentImageIndex + 1) % images.size
        imageView.setImageResource(images[currentImageIndex])

        sharedPreferences.edit().putInt("saved_image_index", currentImageIndex).apply()
    }

    private fun adjustLayoutForOrientation(orientation: Int) {
        val layout = findViewById<ConstraintLayout>(R.id.main_layout)
        val constraintSet = ConstraintSet()
        constraintSet.clone(layout)

        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            constraintSet.connect(R.id.picture, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            constraintSet.connect(R.id.picture, ConstraintSet.END, R.id.btn_change_picture, ConstraintSet.START, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.START, R.id.picture, ConstraintSet.END, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
            constraintSet.connect(R.id.picture, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
        } else {
            constraintSet.connect(R.id.picture, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, 16)
            constraintSet.connect(R.id.picture, ConstraintSet.BOTTOM, R.id.btn_change_picture, ConstraintSet.TOP, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.TOP, R.id.picture, ConstraintSet.BOTTOM, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM, 16)
            constraintSet.connect(R.id.picture, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            constraintSet.connect(R.id.picture, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, 16)
            constraintSet.connect(R.id.btn_change_picture, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END, 16)
        }

        constraintSet.applyTo(layout)
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
        adjustLayoutForOrientation(newConfig.orientation)
    }
}
