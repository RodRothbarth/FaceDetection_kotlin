package com.example.test_run

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import java.io.InputStream

class MainActivity : AppCompatActivity() {
    private lateinit var mFaceoverlayView: FaceoverlayView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mFaceoverlayView = findViewById(R.id.face_view)

        val stream : InputStream = resources.openRawResource(R.raw.test)
        val bitmap : Bitmap = BitmapFactory.decodeStream(stream)

        mFaceoverlayView.setBitmap(bitmap)
    }
}