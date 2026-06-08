package com.ad.admob

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdValue
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object AdUtils {

    // key为:adPlacementId，value:广告对象
    val adCaches = hashMapOf<String, Any>()

    fun isAdCached(adPlacementId: String): Boolean {
        val adItem = adCaches.get(adPlacementId)
        return adItem != null
    }

    suspend fun loadAppOpenAd(adPlacementId: String, onAdPaid: (value: AdValue, adSourceName: String) -> Unit) = suspendCancellableCoroutine { continuation ->
        AppOpenAd.load(app, adPlacementId, AdRequest.Builder().build(), object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                ad.setOnPaidEventListener { adValue ->
                    onAdPaid.invoke(adValue, ad.responseInfo.loadedAdapterResponseInfo?.adSourceName ?: "admob")
                }
                adCaches[adPlacementId] = ad
                continuation.resume(true)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                continuation.resume(false)
            }
        })
    }

    suspend fun loadInterstitialAd(adPlacementId: String, onAdPaid: (value: AdValue, adSourceName: String) -> Unit) = suspendCancellableCoroutine { continuation ->
        InterstitialAd.load(app, adPlacementId, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                ad.setOnPaidEventListener { adValue ->
                    onAdPaid.invoke(adValue, ad.responseInfo.loadedAdapterResponseInfo?.adSourceName ?: "admob")
                }
                adCaches[adPlacementId] = ad
                continuation.resume(true)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                continuation.resume(false)
            }
        })
    }

    fun showFullscreenAd(adPlacementId: String, activity: Activity, onAdClosed: () -> Unit) {
        val adItem = adCaches.get(adPlacementId)
        if (null == adItem) {
            onAdClosed()
            return
        }
        val callback by lazy {
            object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) {
                    onAdClosed()
                }

                override fun onAdShowedFullScreenContent() {

                }

                override fun onAdDismissedFullScreenContent() {
                    onAdClosed()
                }
            }
        }
        when (adItem) {
            is AppOpenAd -> {
                adItem.fullScreenContentCallback = callback
                adItem.setImmersiveMode(true)
                adItem.show(activity)
            }

            is InterstitialAd -> {
                adItem.fullScreenContentCallback = callback
                adItem.setImmersiveMode(true)
                adItem.show(activity)
            }

            else -> onAdClosed()
        }
    }


}