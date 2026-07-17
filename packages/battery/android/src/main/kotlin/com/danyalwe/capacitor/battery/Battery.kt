package com.danyalwe.capacitor.battery

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager
import android.util.Log
import com.getcapacitor.JSObject
import java.util.Timer
import java.util.TimerTask
import kotlin.math.abs

class Battery(
    private val plugin: BatteryPlugin,
    private val context: Context
) {
    private val intentFilter = IntentFilter()
    private var timer: Timer? = null
    private val maxHistorySize = 10
    private var batteryLevelHistory = mutableListOf<BatteryReading>()

    data class BatteryReading(
        val level: Int,
        val capacityMah: Int,
        val voltageMv: Int,
        val amperageMa: Float,
        val timestamp: Long,
        val isCharging: Boolean
    ){
        val wattageW: Float
            get() = (voltageMv.toFloat() * amperageMa) / (1000f * 1000f)
        val energyMwh: Double
            get() = capacityMah.toDouble() * (voltageMv.toDouble() / 1000.0)
    }

    init {
        intentFilter.addAction(Intent.ACTION_BATTERY_CHANGED)
        intentFilter.addAction(Intent.ACTION_POWER_CONNECTED)
        intentFilter.addAction(Intent.ACTION_POWER_DISCONNECTED)
        intentFilter.addAction(Intent.ACTION_BATTERY_LOW)
        intentFilter.addAction(Intent.ACTION_BATTERY_OKAY)
    }

    fun getInfos(): JSObject {
        val content = JSObject()

        val batteryIntent = context.registerReceiver(null, intentFilter)
        val totalCapacity = getBatteryCapacity(context)
        val technology = batteryIntent?.getStringExtra(BatteryManager.EXTRA_TECHNOLOGY)
        val hasBattery = batteryIntent?.getBooleanExtra(BatteryManager.EXTRA_PRESENT, false)

        content.put("totalCapacity", totalCapacity)
        content.put("technology", technology ?: "Unknown")
        content.put("hasBattery", hasBattery)

        return content
    }

    fun start() {
        timer?.cancel()
        timer?.purge()
        timer = Timer("CheckUsage")
        timer?.schedule(object : TimerTask() {
            override fun run() {
                plugin.activity.runOnUiThread {
                    val batteryIntent = context.registerReceiver(null, intentFilter)
                    if (batteryIntent != null) {
                        getValues(context, batteryIntent)
                    }
                }
            }
        }, 100, 1000) // Start after 100ms, repeat every second
    }

    fun stop() {
        batteryLevelHistory.clear()
        timer?.cancel()
        timer?.purge()
    }

    fun getValues(context: Context, intent: Intent): JSObject {
        val content = JSObject()

        val batteryManager = context.getSystemService(Context.BATTERY_SERVICE) as BatteryManager

        val totalCapacity = getBatteryCapacity(context) // mAh
        val level = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CAPACITY)
        val currentCapacity = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CHARGE_COUNTER) / 1000
        val temperature = intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0).toFloat() / 10 // °C
        val voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0) // mV
        val amperage = batteryManager.getIntProperty(BatteryManager.BATTERY_PROPERTY_CURRENT_NOW).toFloat() / 1000 // mA
        val wattage = (voltage * amperage) / (1000 * 1000) // W
        val chargeMode = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, -1)
        val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 1)
        val status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 1)
        val realPercentage = ((currentCapacity * 100) / totalCapacity)

        val isCharging = status == BatteryManager.BATTERY_STATUS_CHARGING

        // Calculate charging/discharging times
        val currentTime = System.currentTimeMillis()
        updateBatteryHistory(level, currentCapacity, voltage, wattage, currentTime, isCharging)

        val chargingTime = if (isCharging) calculateChargingTime(totalCapacity) else 0
        val dischargingTime = if (!isCharging && status != BatteryManager.BATTERY_STATUS_FULL) calculateDischargingTime() else 0

        content.put("level", level)
        content.put("currentCapacity", currentCapacity)
        content.put("realPercentage", realPercentage)
        content.put("isCharging", isCharging)
        content.put("temperature", temperature)
        content.put("voltage", voltage)
        content.put("amperage", amperage)
        content.put("wattage", wattage)
        content.put("health", BatteryHealth.fromInt(health)?.name ?: BatteryHealth.UNKNOWN)
        content.put("status", BatteryStatus.fromInt(status)?.name ?: BatteryStatus.UNKNOWN)
        content.put("chargeMode", BatteryChargeMode.fromInt(chargeMode)?.name ?: BatteryChargeMode.UNKNOWN)
        content.put("chargingTime", chargingTime ?: 0)
        content.put("dischargingTime", dischargingTime ?: 0)
        content.put("remainingTime", getRemainingBatteryTime())

        this.plugin.notify("batteryChange", content, true)

        return content
    }

    @SuppressLint("PrivateApi")
    private fun getBatteryCapacity(context: Context?): Double {
        val mPowerProfile: Any
        val batteryCapacity: Double
        val powerProfileClass = "com.android.internal.os.PowerProfile"
        try {
            mPowerProfile = Class.forName(powerProfileClass)
                .getConstructor(Context::class.java)
                .newInstance(context)
            batteryCapacity = Class
                .forName(powerProfileClass)
                .getMethod("getBatteryCapacity")
                .invoke(mPowerProfile) as Double
        } catch (e: Exception) {
            return 0.0
        }
        return batteryCapacity
    }

    private fun updateBatteryHistory(
        level: Int,
        capacityMah: Int,
        voltageMv: Int,
        amperageMa: Float,
        timestamp: Long,
        isCharging: Boolean
    ) {
        val reading = BatteryReading(level, capacityMah, voltageMv, amperageMa, timestamp, isCharging)
        batteryLevelHistory.add(reading)
        if (batteryLevelHistory.size > maxHistorySize) {
            batteryLevelHistory.removeAt(0)
        }
        val tenMinutesAgo = timestamp - (10 * 60 * 1000) // 10 minutes
        batteryLevelHistory.removeAll { it.timestamp < tenMinutesAgo }
    }


    fun getRemainingBatteryTime(): Int {
        if (batteryLevelHistory.size < 2) {
            Log.d("Battery", "Not enough history to calculate remaining time.")
            return 0 // Not enough data
        }

        val latestReading = batteryLevelHistory.lastOrNull() ?: return 0
        // If the latest reading indicates charging, this function (for discharging time) isn't applicable.
        if (latestReading.isCharging) {
            Log.d("Battery", "Device is charging, remaining battery time calculation is for discharging state.")
            return 0
        }

        val currentEnergyStoredMwh = latestReading.energyMwh
        if (currentEnergyStoredMwh <= 0) {
            return 0 // Already effectively empty
        }

        // Filter relevant discharging readings from history for rate calculation.
        // Wattage must be positive, indicating power flowing out.
        val dischargingRateReadings = batteryLevelHistory.filter {
            !it.isCharging && it.wattageW > 0.01 // Use a small threshold to ensure actual discharge
        }

        if (dischargingRateReadings.size < 2) {
            Log.d("Battery", "Not enough discharging readings in history to calculate rate.")
            return 0 // Not enough data points to calculate a reliable rate
        }

        // Calculate average discharging power in milliwatts (mW)
        val dischargingWattageMw = dischargingRateReadings.map { it.wattageW * 1000f } // Convert W to mW
        if (dischargingWattageMw.isEmpty()) {
            Log.d("Battery", "No valid discharging wattage values found in history.")
            return 0
        }

        val averageDischargingPowerMw = dischargingWattageMw.average().toFloat()
        if (averageDischargingPowerMw <= 0) {
            Log.d("Battery", "Average discharging power is zero or negative, cannot estimate time.")
            return 0 // Not actually discharging at a measurable rate
        }

        val timeToEmptyHours = currentEnergyStoredMwh / averageDischargingPowerMw
        return (timeToEmptyHours * 60).toInt()
    }

    private fun calculateChargingTime(totalCapacity: Double): Int? {
        if (batteryLevelHistory.size < 2 || totalCapacity <= 0) return null

        val latestReading = batteryLevelHistory.lastOrNull { it.isCharging } ?: return null // Need a recent charging reading
        if (latestReading.energyMwh >= totalCapacity) return 0 // Already full or exceeding

        // Filter relevant charging readings from history for rate calculation
        // Ensure wattage is negative (power flowing in) and amperage indicates charging
        val chargingRateReadings = batteryLevelHistory.filter {
            it.isCharging && it.wattageW < -0.01 // Small threshold to avoid noise if wattage is near zero
        }
        if (chargingRateReadings.size < 2) return null // Not enough data points for rate

        val chargingWattageMw = chargingRateReadings.map { abs(it.wattageW * 1000f) } // Use absolute mW
        if (chargingWattageMw.isEmpty()) return null

        val averageChargingPowerMw = chargingWattageMw.average().toFloat()
        if (averageChargingPowerMw <= 0) return null

        // Use the most recent reading's energy to determine energy needed
        val currentEnergyStoredMwh = latestReading.energyMwh
        val energyNeededMwh = totalCapacity - currentEnergyStoredMwh
        if (energyNeededMwh <= 0) return 0 // Effectively full

        val timeToFullHours = energyNeededMwh / averageChargingPowerMw
        return (timeToFullHours * 60).toInt()
    }

    private fun calculateDischargingTime(): Int? {
        if (batteryLevelHistory.size < 2) return null

        val latestReading = batteryLevelHistory.lastOrNull { !it.isCharging } ?: return null
        val currentEnergyStoredMwh = latestReading.energyMwh
        if (currentEnergyStoredMwh <= 0) return 0

        val dischargingRateReadings = batteryLevelHistory.filter { !it.isCharging && it.wattageW > 0.01 }
        if (dischargingRateReadings.size < 2) return null

        val dischargingWattageMw = dischargingRateReadings.map { it.wattageW * 1000f }
        if (dischargingWattageMw.isEmpty()) return null

        val averageDischargingPowerMw = dischargingWattageMw.average().toFloat()
        if (averageDischargingPowerMw <= 0) return null

        val timeToEmptyHours = currentEnergyStoredMwh / averageDischargingPowerMw
        return (timeToEmptyHours * 60).toInt()
    }
}