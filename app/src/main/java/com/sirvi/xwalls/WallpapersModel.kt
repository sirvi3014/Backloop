package com.sirvi.xwalls

import com.google.firebase.Timestamp

data class WallpapersModel( val name:String="",
                            val image:String="",
                            val thumbnail: String?=null,
                            val date: Timestamp?=null
)


