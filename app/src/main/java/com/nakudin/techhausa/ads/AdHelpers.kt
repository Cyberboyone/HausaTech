package com.nakudin.techhausa.ads

import android.app.Activity
import android.content.Context
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.FullScreenContentCallback
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.google.android.gms.ads.rewarded.RewardedAd
import com.google.android.gms.ads.rewarded.RewardedAdLoadCallback

/**
 * Holds a preloaded interstitial ad and reloads automatically after it is
 * shown or fails to show, so callers can simply call [showIfReady].
 */
class InterstitialManager(private val context: Context) {
    private var interstitialAd: InterstitialAd? = null

    fun load() {
        InterstitialAd.load(
            context,
            AdUnits.INTERSTITIAL,
            AdRequest.Builder().build(),
            object : InterstitialAdLoadCallback() {
                override fun onAdLoaded(ad: InterstitialAd) {
                    interstitialAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    interstitialAd = null
                }
            }
        )
    }

    /**
     * Shows the ad if one is ready. Returns true when an ad was shown.
     * Always preloads the next ad afterwards.
     */
    fun showIfReady(activity: Activity): Boolean {
        val ad = interstitialAd ?: run {
            load()
            return false
        }
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                interstitialAd = null
                load()
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                interstitialAd = null
                load()
            }
        }
        ad.show(activity)
        return true
    }
}

/**
 * Holds a preloaded rewarded ad. Call [showIfReady] with a callback that
 * runs when the user earns the reward.
 */
class RewardedManager(private val context: Context) {
    private var rewardedAd: RewardedAd? = null
    val isReady: Boolean get() = rewardedAd != null

    fun load() {
        RewardedAd.load(
            context,
            AdUnits.REWARDED,
            AdRequest.Builder().build(),
            object : RewardedAdLoadCallback() {
                override fun onAdLoaded(ad: RewardedAd) {
                    rewardedAd = ad
                }

                override fun onAdFailedToLoad(error: LoadAdError) {
                    rewardedAd = null
                }
            }
        )
    }

    /**
     * Shows the ad if ready. [onRewarded] runs when the user earns the
     * reward; [onClosed] runs in every other dismissal/failure case so the
     * caller can fall back gracefully. Returns true when an ad was shown.
     */
    fun showIfReady(
        activity: Activity,
        onRewarded: () -> Unit,
        onClosed: () -> Unit = {}
    ): Boolean {
        val ad = rewardedAd ?: return false
        var rewarded = false
        ad.fullScreenContentCallback = object : FullScreenContentCallback() {
            override fun onAdDismissedFullScreenContent() {
                rewardedAd = null
                load()
                if (!rewarded) onClosed()
            }

            override fun onAdFailedToShowFullScreenContent(error: com.google.android.gms.ads.AdError) {
                rewardedAd = null
                load()
                onClosed()
            }
        }
        ad.show(activity) {
            rewarded = true
            onRewarded()
        }
        return true
    }
}
