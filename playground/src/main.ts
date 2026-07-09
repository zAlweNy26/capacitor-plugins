import type { BatteryData } from '@danyalwe/capacitor-battery'
import type { SensorResult, SensorType } from '@danyalwe/capacitor-sensors'
import type { RuntimeInfo } from '@danyalwe/capacitor-systeminfo'
import { Battery } from '@danyalwe/capacitor-battery'
import { Sensors } from '@danyalwe/capacitor-sensors'
import { SystemInfo } from '@danyalwe/capacitor-systeminfo'

function methodBlock(sectionId: string, name: string, loading = true): HTMLElement {
  const section = document.getElementById(sectionId)!
  const div = document.createElement('div')
  div.className = 'method'
  div.innerHTML = `<div class="method-name">${name}</div>
    <div class="method-body"><span class="${loading ? 'loading' : ''}">${loading ? 'Running...' : ''}</span></div>`
  section.querySelector('.results')!.appendChild(div)
  return div.querySelector('.method-body')!
}

function render(container: HTMLElement, value: unknown, error = false): void {
  const cls = error ? 'error' : 'success'
  const text = error
    ? String(value)
    : JSON.stringify(value, null, 2) || 'void'
  container.innerHTML = `<pre class="${cls}">${text}</pre>`
}

function renderError(container: HTMLElement, err: unknown): void {
  render(container, err instanceof Error ? err.message : String(err), true)
}

// ── Sensors ──────────────────────────────────────────────────

async function testSensors(): Promise<void> {
  const m = (name: string): HTMLElement => methodBlock('sensors', name)

  try {
    const available = await Sensors.getAvailableSensors()
    render(m('getAvailableSensors()'), available)
  }
  catch (e) { renderError(m('getAvailableSensors()'), e) }

  try {
    const perms = await Sensors.checkPermissions()
    render(m('checkPermissions()'), perms)
  }
  catch (e) { renderError(m('checkPermissions()'), e) }

  const testSensor: SensorType = 'ACCELEROMETER'

  try {
    const result = await Sensors.init({ type: testSensor })
    render(m(`init({ type: '${testSensor}' })`), result)
  }
  catch (e) { renderError(m(`init({ type: '${testSensor}' })`), e) }

  try {
    await Sensors.start({ type: testSensor })
    const startEl = m(`start({ type: '${testSensor}' })`)
    render(startEl, 'Started — waiting for reading...')

    const reading = await new Promise<SensorResult>((resolve) => {
      Sensors.addListener(testSensor, resolve)
    })
    render(m('addListener reading'), reading)

    await Sensors.stop({ type: testSensor })
    render(m(`stop({ type: '${testSensor}' })`), 'Stopped')
    await Sensors.removeAllListeners()
    render(m('removeAllListeners()'), 'All listeners removed')
  }
  catch (e) { renderError(m('start/listen/stop'), e) }

  try {
    await Sensors.requestPermissions({ type: testSensor })
    render(m(`requestPermissions({ type: '${testSensor}' })`), 'Done')
  }
  catch (e) { renderError(m(`requestPermissions({ type: '${testSensor}' })`), e) }
}

// ── Battery ──────────────────────────────────────────────────

async function testBattery(): Promise<void> {
  const m = (name: string): HTMLElement => methodBlock('battery', name)

  try {
    const info = await Battery.getInfos()
    render(m('getInfos()'), info)
  }
  catch (e) { renderError(m('getInfos()'), e) }

  try {
    await Battery.start()
    const el = m('start()')
    render(el, 'Started — waiting for event...')

    const data = await new Promise<BatteryData>((resolve) => {
      Battery.addListener('batteryChange', resolve)
    })
    render(m('batteryChange event'), data)

    await Battery.stop()
    render(m('stop()'), 'Stopped')
    await Battery.removeAllListeners()
    render(m('removeAllListeners()'), 'All listeners removed')
  }
  catch (e) { renderError(m('start/listen/stop'), e) }
}

// ── SystemInfo ───────────────────────────────────────────────

async function testSystemInfo(): Promise<void> {
  const m = (name: string): HTMLElement => methodBlock('systeminfo', name)

  try {
    const info = await SystemInfo.getInfos()
    render(m('getInfos()'), info)
  }
  catch (e) { renderError(m('getInfos()'), e) }

  try {
    await SystemInfo.start()
    const el = m('start()')
    render(el, 'Started — waiting for event...')

    const data = await new Promise<RuntimeInfo>((resolve) => {
      SystemInfo.addListener('runtimeChange', resolve)
    })
    render(m('runtimeChange event'), data)

    await SystemInfo.stop()
    render(m('stop()'), 'Stopped')
    await SystemInfo.removeAllListeners()
    render(m('removeAllListeners()'), 'All listeners removed')
  }
  catch (e) {
    render(m('start/listen/stop'), 'Live monitoring not available in browser — static info shown above')
  }
}

// ── Run all ──────────────────────────────────────────────────

Promise.all([testSensors(), testBattery(), testSystemInfo()])
