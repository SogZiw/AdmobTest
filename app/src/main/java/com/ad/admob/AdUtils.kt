package com.ad.admob

import android.app.Activity
import com.google.android.gms.ads.AdError
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.appopen.AppOpenAd
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.read.pdf.check.MainContainer
import com.tradplus.ads.mgr.TPOutcome
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlin.coroutines.resume

object AdUtils {

    // key为:adPlacementId，value:广告对象
    val adCaches = hashMapOf<String, Any>()

    fun isAdCached(adPlacementId: String): Boolean {
        val adItem = adCaches.get(adPlacementId)
        return adItem != null
    }

    fun fetchPrice(adPlacementId: String, path: String): Long {
        val adItem = adCaches.get(adPlacementId) ?: return -1L
        return runCatching {
            return@runCatching MainContainer.compact(app, adItem, path)
        }.getOrNull() ?: -1L
    }

    fun isTradPlusWin(admobRevenue: Double, tpAdId: String): Boolean {
        return TPOutcome().isTPW(admobRevenue, tpAdId)
    }

    suspend fun loadAppOpenAd(adPlacementId: String, onAdPaid: (map: HashMap<String, Any>) -> Unit) = suspendCancellableCoroutine { continuation ->
        AppOpenAd.load(app, adPlacementId, AdRequest.Builder().build(), object : AppOpenAd.AppOpenAdLoadCallback() {
            override fun onAdLoaded(ad: AppOpenAd) {
                ad.setOnPaidEventListener { adValue ->
                    onAdPaid.invoke(
                        hashMapOf(
                            "adSourceName" to (ad.responseInfo.loadedAdapterResponseInfo?.adSourceName ?: "admob"),
                            "valueMicros" to adValue.valueMicros,
                            "currencyCode" to adValue.currencyCode,
                            "adPlacementId" to adPlacementId,
                        )
                    )
                }
                adCaches[adPlacementId] = ad
                continuation.resume(true)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                continuation.resume(false)
            }
        })
    }

    suspend fun loadInterstitialAd(adPlacementId: String, onAdPaid: (map: HashMap<String, Any>) -> Unit) = suspendCancellableCoroutine { continuation ->
        InterstitialAd.load(app, adPlacementId, AdRequest.Builder().build(), object : InterstitialAdLoadCallback() {
            override fun onAdLoaded(ad: InterstitialAd) {
                ad.setOnPaidEventListener { adValue ->
                    onAdPaid.invoke(
                        hashMapOf(
                            "adSourceName" to (ad.responseInfo.loadedAdapterResponseInfo?.adSourceName ?: "admob"),
                            "valueMicros" to adValue.valueMicros,
                            "currencyCode" to adValue.currencyCode,
                            "adPlacementId" to adPlacementId,
                        )
                    )
                }
                adCaches[adPlacementId] = ad
                continuation.resume(true)
            }

            override fun onAdFailedToLoad(adError: LoadAdError) {
                continuation.resume(false)
            }
        })
    }

    fun showFullscreenAd(adPlacementId: String, activity: Activity, block: SimpleFullScreenCallback) {
        val adItem = adCaches.remove(adPlacementId)
        if (null == adItem) {
            block.onAdDismissed()
            return
        }
        val callback by lazy {
            object : FullScreenContentCallback() {
                override fun onAdFailedToShowFullScreenContent(e: AdError) = block.onAdFailedToShow(e.message)
                override fun onAdShowedFullScreenContent() = block.onAdShowed()
                override fun onAdClicked() = block.onAdClicked()
                override fun onAdImpression() = block.onAdImpression()
                override fun onAdDismissedFullScreenContent() = block.onAdDismissed()
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

            else -> block.onAdDismissed()
        }
    }

}

abstract class SimpleFullScreenCallback {
    open fun onAdClicked() = Unit
    open fun onAdImpression() = Unit
    open fun onAdDismissed() = Unit
    open fun onAdFailedToShow(msg: String) = Unit
    open fun onAdShowed() = Unit
}