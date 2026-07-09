import type { PluginListenerHandle } from '@capacitor/core'

/** Array of possible charging modes of a battery. */
export const BatteryChargeModes = ['UNKNOWN', 'NONE', 'AC', 'USB', 'DOCK', 'WIRELESS'] as const

export type BatteryChargeMode = (typeof BatteryChargeModes)[number]

/**
 * Array of possible health statuses of a battery.
 */
export const BatteryHealths = ['UNKNOWN', 'COLD', 'DEAD', 'GOOD', 'OVERHEAT', 'OVER_VOLTAGE', 'FAILURE'] as const

export type BatteryHealth = (typeof BatteryHealths)[number]

/**
 * Array of possible battery statuses.
 */
export const BatteryStatuses = ['UNKNOWN', 'NOT_CHARGING', 'DISCHARGING', 'CHARGING', 'FULL'] as const

export type BatteryStatus = (typeof BatteryStatuses)[number]

/** Represents the battery information of the device. */
export interface BatteryInfo {
  /** The total battery capacity (in mAh). */
  totalCapacity: number
  /** The technology used in the battery. */
  technology: string
  /** Whether the device has a battery or not. */
  hasBattery: boolean
}

/** Represents the battery data that can change over time. */
export interface BatteryData {
  /** The current battery level as a percentage (0-100). */
  level: number
  /** Whether the device is currently charging or not. */
  isCharging: boolean
  /** The estimated time remaining until the battery is fully charged (in minutes). */
  chargingTime: number
  /** The estimated time remaining until the battery is fully discharged (in minutes). */
  dischargingTime: number
  /** The current battery capacity (in mAh). */
  currentCapacity?: number
  /** The real battery percentage, which may differ from the reported percentage. */
  realPercentage?: number
  /** The current temperature of the battery (in Celsius). */
  temperature?: number
  /** The current voltage of the battery (in volts). */
  voltage?: number
  /** The current amperage of the battery (in amperes). */
  amperage?: number
  /** The current wattage of the battery (in watts). */
  wattage?: number
  /** The health status of the battery. */
  health?: BatteryHealth
  /** The status of the battery. */
  status?: BatteryStatus
  /** The charging mode of the battery. */
  chargeMode?: BatteryChargeMode
}

/** Represents the BatteryPlugin interface. */
export interface BatteryPlugin {
  /**
   * Returns a Promise that resolves with an object containing battery information.
   * @returns SystemInformations
   */
  getInfos: () => Promise<BatteryInfo>
  /**
   * Starts listening for battery information changes.
   */
  start: () => Promise<void>
  /**
   * Stops listening for battery information changes.
   */
  stop: () => Promise<void>
  /**
   * Adds a listener for the 'batteryChange' event.
   * @param eventName The name of the event.
   * @param listenerFunc The listener function to be called when the event is triggered.
   * @returns A promise that resolves to a PluginListenerHandle.
   */
  addListener: (eventName: 'batteryChange', listenerFunc: (event: BatteryData) => void) => Promise<PluginListenerHandle>
  /**
   * Removes all listeners for the 'batteryChange' event.
   * @returns A promise that resolves when all listeners are removed.
   */
  removeAllListeners: () => Promise<void>
}
