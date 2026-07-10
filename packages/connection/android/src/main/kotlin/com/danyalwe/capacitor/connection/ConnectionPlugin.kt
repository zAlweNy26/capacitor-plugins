package com.danyalwe.capacitor.connection

import android.Manifest
import android.content.Intent
import com.getcapacitor.PermissionState
import com.getcapacitor.JSObject
import com.getcapacitor.Plugin
import com.getcapacitor.PluginCall
import com.getcapacitor.PluginMethod
import com.getcapacitor.annotation.CapacitorPlugin
import com.getcapacitor.annotation.Permission
import com.getcapacitor.annotation.PermissionCallback

@CapacitorPlugin(
    name = "Connection",
    permissions = [
        Permission(
            alias = "connectionAboveOreo",
            strings = arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.READ_PHONE_STATE
            )
        ),
        Permission(
            alias = "connectionUnderOreo",
            strings = arrayOf(
                Manifest.permission.READ_PHONE_STATE
            )
        )
    ]
)
class ConnectionPlugin : Plugin() {
    private lateinit var implementation: Connection

    override fun load() {
        super.load()
        implementation = Connection(this, context)
    }

    fun notify(eventName: String, data: JSObject, retainUntilConsumed: Boolean): Unit {
        return notifyListeners(eventName, data, retainUntilConsumed)
    }

    @PluginMethod
    fun getInfos(call: PluginCall) {
        call.resolve(implementation.getInfos())
    }

    private fun hasPermissions(call: PluginCall): Boolean {
        if (getPermissionState("connectionAboveOreo") != PermissionState.GRANTED
            && Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            requestPermissionForAlias("connectionAboveOreo", call, "permissionsCallback")
            return false
        } else if (getPermissionState("connectionUnderOreo") != PermissionState.GRANTED
            && Build.VERSION.SDK_INT < Build.VERSION_CODES.O) {
            requestPermissionForAlias("connectionUnderOreo", call, "permissionsCallback")
            return false
        }
        return true
    }

    @PluginMethod
    override fun requestPermissions(call: PluginCall) {
        hasPermissions(call)
    }

    @PermissionCallback
    private fun permissionsCallback(call: PluginCall) {
        if (hasPermissions(call)) implementation.start()
        else call.reject("Permission is required to access connection information.")
    }
}