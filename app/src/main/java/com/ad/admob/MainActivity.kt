package com.ad.admob

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private val adScope by lazy {
        CoroutineScope(Dispatchers.Main + SupervisorJob() + CoroutineExceptionHandler { _, _ -> })
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }


    fun loadAppOpen(adPlacementId: String) {
        adScope.launch {
            val isLoadSucceed = AdUtils.loadAppOpenAd(adPlacementId) { map ->

            }
        }
    }

    fun loadInterstitial(adPlacementId: String) {
        adScope.launch {
            val isLoadSucceed = AdUtils.loadInterstitialAd(adPlacementId) { map ->

            }
        }
    }

    fun showFullScreenAd(adPlacementId: String) {
        AdUtils.showFullscreenAd(adPlacementId, this, object : SimpleFullScreenCallback() {
            override fun onAdClicked() {

            }

            override fun onAdDismissed() {

            }

            override fun onAdFailedToShow(msg: String) {

            }

            override fun onAdImpression() {

            }

            override fun onAdShowed() {

            }
        })
    }

}