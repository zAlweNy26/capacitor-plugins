package com.danyalwe.capacitor.connection

import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.ConnectivityManager.NetworkCallback
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import android.net.wifi.WifiManager
import android.os.Build
import android.telephony.TelephonyManager
import android.util.Log
import com.getcapacitor.JSArray
import com.getcapacitor.JSObject

class Connection(
    private val plugin: ConnectionPlugin,
    private val context: Context
) : NetworkCallback() {
    private val intentFilter = IntentFilter()
    private var connectivityManager: ConnectivityManager? = null
    private var networkRequest: NetworkRequest? = null

    init {
        intentFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION)
        intentFilter.addAction(Intent.ACTION_AIRPLANE_MODE_CHANGED)
        connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            networkRequest = NetworkRequest.Builder()
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_BLUETOOTH)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .addTransportType(NetworkCapabilities.TRANSPORT_ETHERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_VPN)
            .build()
    }

    fun start() {
        networkRequest?.let { connectivityManager?.registerNetworkCallback(it, this) }
        Log.d("Start", "Connection Plugin")
    }

    fun stop() {
        connectivityManager?.unregisterNetworkCallback(this)
        Log.d("Stop", "Connection Plugin")
    }

    fun getInfos(): JSObject {
        val obj = JSObject()
        return obj
    }

    private fun getSimData(): JSArray {
        val telephonyManager = context.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager

        val totSim = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            telephonyManager.activeModemCount
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            telephonyManager.phoneCount
        } else 1

        val sims = JSArray()

        if (totSim > 1 && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (i in 0 until totSim) {
                val sim = JSObject()
                sim.put("state", telephonyManager.getSimState(i))
                sims.put(sim)
            }
        } else {
            val sim = JSObject()
            sim.put("state", telephonyManager.simState)
            sims.put(sim)
        }

        return sims
    }

    override fun onUnavailable() {
        val content = JSObject()
        content.put("sims", getSimData())
        this.plugin.notify("connectionChange", content, true)
    }

    override fun onAvailable(network: Network) {
        val content = JSObject()
        content.put("sims", getSimData())
        this.plugin.notify("connectionChange", content, true)
    }

    override fun onLost(network: Network) {
        val content = JSObject()
        content.put("sims", getSimData())
        this.plugin.notify("connectionChange", content, true)
    }
}