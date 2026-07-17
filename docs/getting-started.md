# Getting Started

Install and use all three plugins in your Capacitor app.

## Battery

Get access to every info about the device battery.

```bash
npm install @danyalwe/capacitor-battery
```

```ts
import { Battery } from '@danyalwe/capacitor-battery'

await Battery.start()

Battery.addListener('batteryChange', (data) => {
  console.log('Level:', data.level, 'Charging:', data.isCharging)
})
```

[Full API →](/plugins/battery)

## Sensors

Get access to every sensor present in the device.

```bash
npm install @danyalwe/capacitor-sensors
```

```ts
import { Sensors } from '@danyalwe/capacitor-sensors'

const sensor = await Sensors.init({ type: 'ACCELEROMETER' })
await Sensors.start({ type: 'ACCELEROMETER' })

Sensors.addListener('ACCELEROMETER', (data) => {
  console.log('Accelerometer:', data.values)
})
```

[Full API →](/plugins/sensors)

## System Info

Get access to every info about the device software and hardware.

```bash
npm install @danyalwe/capacitor-systeminfo
```

```ts
import { SystemInfo } from '@danyalwe/capacitor-systeminfo'

const info = await SystemInfo.getInfos()

SystemInfo.addListener('runtimeChange', (data) => {
  console.log('Used RAM:', data.usedRAM)
})
await SystemInfo.start()
```

[Full API →](/plugins/systeminfo)
