//package com.limbo.camerademo
//
//import android.content.Context
//import android.view.SurfaceHolder
//import android.view.SurfaceView
//import android.view.ViewGroup
//
//class Preview(context: Context,
//              val surfaceView: SurfaceView = SurfaceView(context)
//              ) : ViewGroup(context), SurfaceHolder.Callback {
//    var mHolder: SurfaceHolder = surfaceView.holder.apply {
//        addCallback(this@Preview)
//        setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS)
//    }
//}