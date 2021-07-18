package com.sirvi.xwalls

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.DocumentSnapshot

class WallpapersViewModel: ViewModel() {

private val firebaseRepository:FirebaseRepository = FirebaseRepository()
    private val wallpaperList: MutableLiveData<List<WallpapersModel>> by lazy {
        MutableLiveData<List<WallpapersModel>>().also {
            loadWallpapersData()
        }
    }
    fun getWallpapersList(): LiveData<List<WallpapersModel>>
    {
        return wallpaperList
    }
    fun loadWallpapersData()
    {
        //Query data from repo
        firebaseRepository.queryWallpapers().addOnCompleteListener {
            if (it.isSuccessful)
            {
                val result =it.result
                if (result!!.isEmpty)
                {
                    //no more result to load,Reached at bottom of page
                }
                else
                {
                    //Result are ready to load
                 if (wallpaperList.value ==null)
                 {
                     //Loading first page
                     wallpaperList.value = result.toObjects(WallpapersModel::class.java)
                 }
                    else
                 {
                     //loading nest page
                     wallpaperList.value=wallpaperList.value!!.plus(result.toObjects(WallpapersModel::class.java))

                 }


                    //Get the last document
                    val lastItem:DocumentSnapshot=result.documents[result.size()-1]
                    firebaseRepository.lastVisible =lastItem
                }
            }
            else
            {
                //Error
                Log.d("VIEW_MODEL_LOG","Error : ${it.exception!!.message}")
            }
        }
    }
}
