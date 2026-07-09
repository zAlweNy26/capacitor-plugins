import type { BatteryData, BatteryInfo, BatteryPlugin } from './definitions'

import { WebPlugin } from '@capacitor/core'

export class BatteryWeb extends WebPlugin implements BatteryPlugin {
  private battery!: BatteryManager
  private abortController: AbortController = new AbortController()

  private async checkBatteryApi(): Promise<boolean> {
    const check = typeof navigator !== 'undefined' && 'getBattery' in navigator
    if (!check) throw this.unavailable('Battery Status API is not available in this browser.')

    this.battery = await navigator.getBattery()

    return !!this.battery
  }

  private getBatteryData(notify = true): {
    level: number
    isCharging: boolean
    chargingTime: number
    dischargingTime: number
  } {
    const result = {
      level: this.battery.level,
      isCharging: this.battery.charging,
      chargingTime: this.battery.chargingTime,
      dischargingTime: this.battery.dischargingTime,
    } satisfies BatteryData

    if (notify) this.notifyListeners('batteryChange', result)

    return result
  }

  async getInfos(): Promise<BatteryInfo> {
    throw this.unavailable('Battery information is not available in the browser.')
  }

  async start(): Promise<void> {
    if (!await this.checkBatteryApi()) throw this.unavailable('Use of this feature was blocked by a Permissions Policy.')

    this.abortController = new AbortController()

    this.battery.addEventListener('chargingchange', () => this.getBatteryData(), {
      signal: this.abortController.signal,
    })

    this.battery.addEventListener('chargingtimechange', () => this.getBatteryData(), {
      signal: this.abortController.signal,
    })

    this.battery.addEventListener('dischargingtimechange', () => this.getBatteryData(), {
      signal: this.abortController.signal,
    })

    this.battery.addEventListener('levelchange', () => this.getBatteryData(), {
      signal: this.abortController.signal,
    })
  }

  async stop(): Promise<void> {
    this.abortController.abort('stop')
  }

  async removeAllListeners(): Promise<void> {
    this.abortController.abort('remove')
    return super.removeAllListeners()
  }
}
