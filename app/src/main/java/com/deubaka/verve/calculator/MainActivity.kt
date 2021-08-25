package com.deubaka.verve.calculator

import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import net.pubnative.lite.sdk.HyBid
import net.pubnative.lite.sdk.interstitial.HyBidInterstitialAd
import net.pubnative.lite.sdk.views.HyBidAdView
import net.pubnative.lite.sdk.views.PNAdView


class MainActivity : AppCompatActivity() {
    val TAG = MainActivity::class.java.simpleName

    // Got from the PubNative demo app
    private val hybidAppToken = "dde3c298b47648459f8ada4a982fa92d"

    // Banner
    private lateinit var hybidBanner: HyBidAdView

    // Interstitial
    private var interstitial: HyBidInterstitialAd? = null

    private var isNewOperation = true
    private var period = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        hybidBanner = findViewById(R.id.hybid_banner)

        HyBid.initialize(hybidAppToken, application)
        HyBid.setTestMode(true); // Just so it won't count
    }

    override fun onStart() {
        super.onStart()
        loadBanner()
        loadInterstitial()
    }

    override fun onDestroy() {
        interstitial?.destroy()
        hybidBanner?.destroy()
        super.onDestroy()
    }

    fun onEntryFieldInput(view: View) {
        if (isNewOperation) {
            etEntryField.setText("")
        }

        isNewOperation = false
        val btnSelected = view as Button
        var entryFieldValue: String = etEntryField.text.toString()
        when (btnSelected.id) {
            btnZero.id -> {
                entryFieldValue += "0"
            }
            btnOne.id -> {
                entryFieldValue += "1"
            }
            btnTwo.id -> {
                entryFieldValue += "2"
            }
            btnThree.id -> {
                entryFieldValue += "3"
            }
            btnFour.id -> {
                entryFieldValue += "4"
            }
            btnFive.id -> {
                entryFieldValue += "5"
            }
            btnSix.id -> {
                entryFieldValue += "6"
            }
            btnSeven.id -> {
                entryFieldValue += "7"
            }
            btnEight.id -> {
                entryFieldValue += "8"
            }
            btnNine.id -> {
                entryFieldValue += "9"
            }
            btnPeriod.id -> {
                if (!period) {
                    entryFieldValue += "."
                }
                period = true
            }

            btnPlusMinus.id -> {
                entryFieldValue = "-$entryFieldValue"
            }
        }
        etEntryField.setText(entryFieldValue)
    }

    var operation = "X"
    var oldNumber = ""

    fun onOperationEvent(view: View) {
        val btnSelected = view as Button
        when (btnSelected.id) {
            btnMultiply.id -> {
                operation = "X"
            }
            btnDivision.id -> {
                operation = "÷"
            }
            btnMinus.id -> {
                operation = "-"
            }
            btnPlus.id -> {
                operation = "+"
            }
        }
        oldNumber = etEntryField.text.toString()
        isNewOperation = true
        period = false
    }

    fun onComputeEvent(view: View) {
        val newNumber = etEntryField.text.toString()
        var finalNumber: Double? = null
        when (operation) {
            "X" -> {
                finalNumber = oldNumber.toDouble() * newNumber.toDouble()
            }
            "÷" -> {
                finalNumber = oldNumber.toDouble() / newNumber.toDouble()
            }
            "-" -> {
                finalNumber = oldNumber.toDouble() - newNumber.toDouble()
            }
            "+" -> {
                finalNumber = oldNumber.toDouble() + newNumber.toDouble()
            }
        }

        etEntryField.setText(finalNumber.toString())
        isNewOperation = true

        interstitial?.show()
    }

    fun onPercentEvent(view: View) {
        val number = (etEntryField.text.toString().toDouble()) / 100
        etEntryField.setText(number.toString())
        isNewOperation = true
    }

    fun onClearEvent(view: View) {
        etEntryField.setText("")
        isNewOperation = true
        period = false
    }

    private fun loadBanner() {
        hybidBanner.load("2", object : PNAdView.Listener {

            override fun onAdLoaded() {
                Log.d(TAG, "onAdLoaded")
            }

            override fun onAdLoadFailed(error: Throwable) {
                Log.d(TAG, "onAdLoadFailed")
            }

            override fun onAdImpression() {
                Log.d(TAG, "onAdImpression")
            }

            override fun onAdClick() {
                Log.d(TAG, "onAdClick")
            }
        })
    }

    private fun loadInterstitial() {
        interstitial = HyBidInterstitialAd(this, "3", object : HyBidInterstitialAd.Listener {
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