package com.example.test_run

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.SparseArray
import android.view.View
import androidx.core.util.valueIterator
import com.google.android.gms.vision.Frame
import com.google.android.gms.vision.face.Face
import com.google.android.gms.vision.face.FaceDetector

public class FaceoverlayView (context: Context, attrs: AttributeSet? = null, defStyleAttr:Int) : View(context, attrs, defStyleAttr) {
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, defStyleAttr = 0)

    private lateinit var mBitmap: Bitmap
    private var mFaces: SparseArray<Face> = SparseArray<Face>()

    fun setBitmap(bitmap: Bitmap){
        mBitmap = bitmap

        val faceDetector : FaceDetector = FaceDetector.Builder(context)
            .setTrackingEnabled(true)
            .setLandmarkType(FaceDetector.ALL_LANDMARKS)
            .setMode(FaceDetector.ACCURATE_MODE)
            .build()
        if(!faceDetector.isOperational()){
            //TODO verificar como implementar o facedetection se nao estiver no celular
        }else{
            val frame : Frame = Frame.Builder()
                .setBitmap(mBitmap)
                .build()

            mFaces = faceDetector.detect(frame)
            faceDetector.release()
        }

        invalidate()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)

        if(mBitmap != null && mFaces != null){
            var scale : Int? = canvas?.let { drawBitmap(it) }
            if(canvas != null && scale != null){
                drawFaceBox(canvas, scale)
            }
        }
    }
    fun drawBitmap(canvas:Canvas) : Int {
        val viewWidth : Int = canvas?.width ?:0
        val viewHeight: Int = canvas?.height ?:0
        val imageHeight : Int = mBitmap.height
        val imageWidth : Int = mBitmap.width
        val scale : Int = Math.min(viewWidth / imageWidth, viewHeight/ imageHeight)
        val bounds : Rect = Rect(0, 0, imageWidth * scale, imageHeight * scale)
        canvas.drawBitmap(mBitmap, null, bounds, null)
        return scale
    }

    fun drawFaceBox(canvas: Canvas, scale : Int){
        var paint: Paint = Paint()
        var blurRadius : Float = scale * 18F
        var vectorX : Float = 0F
        var vectorY : Float = 0F
        var radius : Float = 0F

        paint.maskFilter = BlurMaskFilter(blurRadius, BlurMaskFilter.Blur.NORMAL)

        for (face in mFaces.valueIterator()){
//
            vectorX = scale * (face.position.x + face.width/2)
            vectorY = scale * (face.position.y + face.height/2)
            radius = scale * (35F)
            canvas.drawCircle(vectorX, vectorY, radius, paint)
        }

    }
}

