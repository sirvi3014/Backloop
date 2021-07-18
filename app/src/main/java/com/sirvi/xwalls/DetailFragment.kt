package com.sirvi.xwalls

import android.app.WallpaperManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.graphics.drawable.toBitmap
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target
//import com.google.android.gms.ads.InterstitialAd
//import com.google.android.gms.ads.AdRequest
//import com.google.android.gms.ads.InterstitialAd
import kotlinx.android.synthetic.main.fragment_detail.*

class DetailFragment : Fragment(), View.OnClickListener {
 //   private lateinit var mInterstitialAd: InterstitialAd

    private var image: String? =null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        image = DetailFragmentArgs.fromBundle(arguments!!).wallpaperImage
        //mInterstitialAd = InterstitialAd(context)
        //mInterstitialAd.adUnitId = "cca-app-pub-3940256099942544/1033173712"
        //mInterstitialAd.loadAd(AdRequest.Builder().build())
        //set wallpaper button click
        detail_app_btn.setOnClickListener(this)



    }

    override fun onClick(v: View?) {
       // if (mInterstitialAd.isLoaded) {
        //    mInterstitialAd.show()
       // } else {
       //     Log.d("TAG", "The interstitial wasn't loaded yet.")

       // }
        when(v!!.id){
            R.id.detail_app_btn -> setWallpaper()

        }
    }

    private fun setWallpaper() {


        //change text and disable button
        detail_app_btn.isEnabled = false
        detail_app_btn.text="Done"
        detail_app_btn.setTextColor(resources.getColor(R.color.colorPrimaryDark,null))



        val bitmap: Bitmap =detail_image.drawable.toBitmap()
        val task: SetWallpaperTask =SetWallpaperTask(context!!,bitmap)
        task.execute(true)

    }
    companion object{
        class SetWallpaperTask internal constructor(private val context: Context, private val bitmap: Bitmap) :
            AsyncTask<Boolean, String, String>()
        {
            override fun doInBackground(vararg params: Boolean?): String {
                val wallpaperManager: WallpaperManager = WallpaperManager.getInstance(context)
                wallpaperManager.setBitmap(bitmap)
                return "Wallpaper Set"
            }


        }


    }

    override fun onStart() {
        super.onStart()
        if (image !=null)
        {
            //set image
            Glide.with(context!!).load(image).listener(
                object :RequestListener<Drawable>{
                    override fun onLoadFailed(
                        e: GlideException?,
                        model: Any?,
                        target: Target<Drawable>?,
                        isFirstResource: Boolean
                    ): Boolean {
                        return false
                    }

                    override fun onResourceReady(
                        resource: Drawable?,
                        model: Any?,
                        target: Target<Drawable>?,
                        dataSource: DataSource?,
                        isFirstResource: Boolean
                    ): Boolean {
                        //image loaded,Show set wallpaper Button
                        //detail_app_btn.visibility =View.VISIBLE
                        detail_app_btn.text="Wallpaper Set"
                        detail_app_btn.isVisible

                        //Hide progressbar
                        detail_wallpaper_progress.visibility=View.INVISIBLE
                        return false
                    }

                }

            ).into(detail_image)

        }
    }

    override fun onStop() {
        super.onStop()
        Glide.with(context!!).clear(detail_image)

    }


}
