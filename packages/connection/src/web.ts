import type { ConnectionPlugin, SimData } from './definitions'

import { WebPlugin } from '@capacitor/core'

export class ConnectionWeb extends WebPlugin implements ConnectionPlugin {
  async getInfos(): Promise<SimData> {
    throw this.unavailable('Connection information is not available in the browser.')
  }

  async checkPermissions(): Promise<PermissionStatus> {
    throw this.unavailable('Permission information is not available in the browser.')
  }

  async requestPermissions(): Promise<PermissionStatus> {
    throw this.unavailable('Permission information is not available in the browser.')
  }
}
