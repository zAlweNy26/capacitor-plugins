import type { ConnectionPlugin } from './definitions'

import { registerPlugin } from '@capacitor/core'

const Connection = registerPlugin<ConnectionPlugin>('Connection', {
  web: () => import('./web').then(m => new m.ConnectionWeb()),
})

export * from './definitions'
export { Connection }
