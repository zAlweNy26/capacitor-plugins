# Capacitor Plugin - Battery

Get access to every info about the device battery!

Supported Android version: `24+`\
Supported iOS version: `Not supported`\
Supported Browsers: `Chromium-based`

## Install

```bash
npm install @danyalwe/capacitor-battery
npx cap sync
```

## Todos

- [ ] Add support for iOS

## Supported methods

| Name               | Android | iOS | Web |
| :----------------- | :------ | :-- | :-- |
| start              | ✅      | ❌  | ✅  |
| stop               | ✅      | ❌  | ✅  |
| addListener        | ✅      | ❌  | ✅  |
| removeAllListeners | ✅      | ❌  | ✅  |

## Supported properties

| Property              | Android | iOS | Web |
| :-------------------- | :------ | :-- | :-- |
| **`currentCapacity`** | ✅      | ❌  | ❌  |
| **`totalCapacity`**   | ✅      | ❌  | ❌  |
| **`realPercentage`**  | ✅      | ❌  | ❌  |
| **`technology`**      | ✅      | ❌  | ❌  |
| **`temperature`**     | ✅      | ❌  | ❌  |
| **`voltage`**         | ✅      | ❌  | ❌  |
| **`amperage`**        | ✅      | ❌  | ❌  |
| **`wattage`**         | ✅      | ❌  | ❌  |
| **`health`**          | ✅      | ❌  | ❌  |
| **`status`**          | ✅      | ❌  | ❌  |
| **`chargeMode`**      | ✅      | ❌  | ❌  |
| **`level`**           | ✅      | ❌  | ✅  |
| **`hasBattery`**      | ✅      | ❌  | ✅  |
| **`isCharging`**      | ✅      | ❌  | ✅  |
| **`chargingTime`**    | ❌      | ❌  | ✅  |
| **`dischargingTime`** | ❌      | ❌  | ✅  |

## API

<docgen-index>

* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

Represents the BatteryPlugin interface.

### Interfaces


#### BatteryInfo

Represents the battery information of the device.

| Prop                | Type                 | Description                              |
| ------------------- | -------------------- | ---------------------------------------- |
| **`totalCapacity`** | <code>number</code>  | The total battery capacity (in mAh).     |
| **`technology`**    | <code>string</code>  | The technology used in the battery.      |
| **`hasBattery`**    | <code>boolean</code> | Whether the device has a battery or not. |


#### BatteryData

Represents the battery data that can change over time.

| Prop                  | Type                                                            | Description                                                                      |
| --------------------- | --------------------------------------------------------------- | -------------------------------------------------------------------------------- |
| **`level`**           | <code>number</code>                                             | The current battery level as a percentage (0-100).                               |
| **`isCharging`**      | <code>boolean</code>                                            | Whether the device is currently charging or not.                                 |
| **`chargingTime`**    | <code>number</code>                                             | The estimated time remaining until the battery is fully charged (in minutes).    |
| **`dischargingTime`** | <code>number</code>                                             | The estimated time remaining until the battery is fully discharged (in minutes). |
| **`currentCapacity`** | <code>number</code>                                             | The current battery capacity (in mAh).                                           |
| **`realPercentage`**  | <code>number</code>                                             | The real battery percentage, which may differ from the reported percentage.      |
| **`temperature`**     | <code>number</code>                                             | The current temperature of the battery (in Celsius).                             |
| **`voltage`**         | <code>number</code>                                             | The current voltage of the battery (in volts).                                   |
| **`amperage`**        | <code>number</code>                                             | The current amperage of the battery (in amperes).                                |
| **`wattage`**         | <code>number</code>                                             | The current wattage of the battery (in watts).                                   |
| **`health`**          | <code><a href="#batteryhealth">BatteryHealth</a></code>         | The health status of the battery.                                                |
| **`status`**          | <code><a href="#batterystatus">BatteryStatus</a></code>         | The status of the battery.                                                       |
| **`chargeMode`**      | <code><a href="#batterychargemode">BatteryChargeMode</a></code> | The charging mode of the battery.                                                |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### BatteryHealth

<code>(typeof BatteryHealths)[number]</code>


#### BatteryStatus

<code>(typeof BatteryStatuses)[number]</code>


#### BatteryChargeMode

<code>(typeof BatteryChargeModes)[number]</code>

</docgen-api>
