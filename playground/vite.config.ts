import { defineConfig } from 'vite'

export default defineConfig({
  ssr: {
    noExternal: ['@danyalwe/capacitor-battery', '@danyalwe/capacitor-sensors', '@danyalwe/capacitor-systeminfo', '@danyalwe/capacitor-connection'],
  },
})
