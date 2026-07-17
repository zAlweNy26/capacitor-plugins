package com.danyalwe.capacitor.battery

import android.util.Log
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin

@CapacitorPlugin(name = "Battery")
class BatteryPlugin : Plugin() {
    private lateinit var implementation: Battery

    override fun load() {
        super.load()
        implementation = Battery(this, context)
    }

    fun notify(eventName: String, data: JSObject, retainUntilConsumed: Boolean): Unit {
        return notifyListeners(eventName, data, retainUntilConsumed)
    }

    @PluginMethod
    fun getInfos(call: PluginCall) {
        call.resolve(implementation.getInfos())
    }

    @PluginMethod
    fun start(call: PluginCall) {
        Log.d("Start", "Battery Plugin")
        implementation.start()
    }

    @PluginMethod
    fun stop(call: PluginCall) {
        implementation.stop()
        Log.d("Stop", "Battery Plugin")
    }
}