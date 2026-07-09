import type { BatteryPlugin } from './definitions'

import { registerPlugin } from '@capacitor/core'

const Battery = registerPlugin<BatteryPlugin>('Battery', {
  web: () => import('./web').then(m => new m.BatteryWeb()),
})

export * from './definitions'
export { Battery }
