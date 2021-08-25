package com.deubaka.verve.calculator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import net.pubnative.lite.sdk.interstitial.HyBidInterstitialAd

class AdsActivity : AppCompatActivity() {
    val TAG = AdsActivity::class.java.simpleName


    // Interstitial
    private var interstitial: HyBidInterstitialAd? = null
    private val interstitialZoneId: String = "2"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ads)
    }

    override fun onStart() {
        super.onStart()

        loadInterstitial()
    }

    override fun onResume() {
        super.onResume()

        if (interstitial?.isReady!!) {
            interstitial?.load()
        }
    }

    override fun onDestroy() {
        interstitial?.destroy()
        super.onDestroy()
    }

    private fun loadInterstitial() {
        interstitial = HyBidInterstitialAd(this, interstitialZoneId, object : HyBidInterstitialAd.Listener {
            override fun onInterstitialLoaded() {
                Log.d(TAG,"onInterstitialLoaded")
            }

            override fun onInterstitialLoadFailed(error: Throwable) {
                Log.d(TAG,"onInterstitialLoadFailed")
            }

            override fun onInterstitialImpression() {
                Log.d(TAG,"onInterstitialImpression")
            }

            override fun onInterstitialDismissed() {
                Log.d(TAG,"onInterstitialDismissed")
            }

            override fun onInterstitialClick() {
                Log.d(TAG,"onInterstitialClick")
            }
        })
        interstitial?.setSkipOffset(3)
        interstitial!!.load()
    }
}