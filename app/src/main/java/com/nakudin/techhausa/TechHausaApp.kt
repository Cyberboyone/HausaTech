package com.nakudin.techhausa

import android.app.Application
import com.google.android.gms.ads.MobileAds

class TechHausaApp : Application() {
    override fun onCreate() {
        super.onCreate()
        MobileAds.initialize(this)
    }
}
