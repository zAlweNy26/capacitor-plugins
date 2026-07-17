import type { PluginListenerHandle } from '@capacitor/core'

/**
 * Array of possible SIM states.
 */
export const SimStates = ['UNKNOWN', 'ABSENT', 'PIN_REQUIRED', 'PUK_REQUIRED', 'NETWORK_LOCKED', 'READY', 'NOT_READY', 'PERMANENTLY_DISABLED', 'IO_ERROR', 'RESTRICTED'] as const

export type SimState = (typeof SimStates)[number]

export interface SimData {
  state: SimState
}

export interface ConnectionListenerResult {
  sims: SimData[]
}

/** Represents the ConnectionPlugin interface. */
export interface ConnectionPlugin {
  /**
   * Returns a Promise that resolves with an object containing connection information.
   * @returns ConnectionInfo
   */
  getInfos: () => Promise<SimData>
  /**
   * Checks the permissions for the connection data.
   * @returns A Promise that resolves to the permission status.
   */
  checkPermissions: () => Promise<PermissionStatus>
  /**
   * Requests permission for the connection data.
   * @returns A Promise that resolves to the permission status.
   */
  requestPermissions: () => Promise<PermissionStatus>
  /**
   * Adds a listener for the given connection event.
   * @param eventName The name of the event to listen for.
   * @param listenerFunc The function to call when the event is triggered.
   * @returns A Promise that resolves to a handle for the listener.
   */
  addListener: (eventName: 'connectionChange', listenerFunc: (event: ConnectionListenerResult) => void) => Promise<PluginListenerHandle>
  /**
   * Removes all listeners for the connection plugin.
   * @returns A Promise that resolves when all listeners have been removed.
   */
  removeAllListeners: () => Promise<void>
}
