# Capacitor Plugin - Sensors

Get access to every sensor present in the device!

Supported Android version: `24+`\
Supported iOS version: `Not supported`\
Supported Browsers: `Chromium-based`

## Install

```bash
npm install @danyalwe/capacitor-sensors
npx cap sync
```

If you want to use the **`HEART_BEAT`** or **`HEART_RATE`** sensor, you need to request the `BODY_SENSORS` permission in your app:

```xml
<uses-permission android:name="android.permission.BODY_SENSORS" />
```

If you want to use the **`STEP_COUNTER`** or **`STEP_DETECTOR`** sensor, you need to request the `ACTIVITY_RECOGNITION` permission in your app:

```xml
<uses-permission android:name="android.permission.ACTIVITY_RECOGNITION" />
```

## Example

The following example demonstrates how to use the Sensors plugin to access the device's accelerometer:

```ts
import { Sensors } from '@danyalwe/capacitor-sensors'

// Initialize the specific sensor and obtain infos about it
const sensor = await Sensors.init({ type: 'ACCELEROMETER' })

// Start the sensor to begin receiving data
await Sensors.start({ type: 'ACCELEROMETER' })

// Add a listener to receive accelerometer data
Sensors.addListener('ACCELEROMETER', (data) => {
  console.log('Accelerometer data:', data)
})

// Stop the sensor
await Sensors.stop({ type: 'ACCELEROMETER' })
```

## Todos

- [ ] Add support for iOS

## Supported methods

| Name                | Android | iOS | Web |
| :------------------ | :------ | :-- | :-- |
| init                | ✅      | ❌  | ✅  |
| getAvailableSensors | ✅      | ❌  | ✅  |
| start               | ✅      | ❌  | ✅  |
| stop                | ✅      | ❌  | ✅  |
| addListener         | ✅      | ❌  | ✅  |
| removeAllListeners  | ✅      | ❌  | ✅  |
| requestPermissions  | ✅      | ❌  | ✅  |

## Supported sensors

| Sensors                           | Android | iOS | Web |
| :-------------------------------- | :------ | :-- | :-- |
| **`MOTION_DETECTOR`**             | ✅      | ❌  | ✅  |
| **`LINEAR_ACCELERATION`**         | ✅      | ❌  | ✅  |
| **`MAGNETOMETER`**                | ✅      | ❌  | ✅  |
| **`GRAVITY`**                     | ✅      | ❌  | ✅  |
| **`GYROSCOPE`**                   | ✅      | ❌  | ✅  |
| **`AMBIENT_LIGHT`**               | ✅      | ❌  | ✅  |
| **`ACCELEROMETER`**               | ✅      | ❌  | ✅  |
| **`ABSOLUTE_ORIENTATION`**        | ✅      | ❌  | ✅  |
| **`RELATIVE_ORIENTATION`**        | ✅      | ❌  | ✅  |
| **`TEMPERATURE`**                 | ✅      | ❌  | ❌  |
| **`GAME_ROTATION_VECTOR`**        | ✅      | ❌  | ❌  |
| **`GEOMAGNETIC_ROTATION_VECTOR`** | ✅      | ❌  | ❌  |
| **`HEART_BEAT`**                  | ✅      | ❌  | ❌  |
| **`HEART_RATE`**                  | ✅      | ❌  | ❌  |
| **`POSE_6DOF`**                   | ✅      | ❌  | ❌  |
| **`PRESSURE`**                    | ✅      | ❌  | ❌  |
| **`PROXIMITY`**                   | ✅      | ❌  | ❌  |
| **`RELATIVE_HUMIDITY`**           | ✅      | ❌  | ❌  |
| **`ROTATION_VECTOR`**             | ✅      | ❌  | ❌  |
| **`SIGNIFICANT_MOTION`**          | ✅      | ❌  | ❌  |
| **`STATIONARY_DETECTOR`**         | ✅      | ❌  | ❌  |
| **`STEP_COUNTER`**                | ✅      | ❌  | ❌  |
| **`STEP_DETECTOR`**               | ✅      | ❌  | ❌  |

## API

<docgen-index>

* [Interfaces](#interfaces)
* [Type Aliases](#type-aliases)

</docgen-index>

<docgen-api>
<!--Update the source file JSDoc comments and rerun docgen to update the docs below-->

The Sensors Plugin interface.

### Interfaces


#### SensorOptions

Represents the options for a sensor.

| Prop        | Type                                                | Description                        |
| ----------- | --------------------------------------------------- | ---------------------------------- |
| **`type`**  | <code><a href="#sensortype">SensorType</a></code>   | The type of sensor to use.         |
| **`delay`** | <code><a href="#sensordelay">SensorDelay</a></code> | The delay between sensor readings. |


#### SensorData

Represents the data returned by a sensor, including any additional information about the sensor.

| Prop        | Type                                                |
| ----------- | --------------------------------------------------- |
| **`infos`** | <code><a href="#sensorinfos">SensorInfos</a></code> |


#### SensorInfos

Interface representing sensor information.

| Prop             | Type                | Description                                                |
| ---------------- | ------------------- | ---------------------------------------------------------- |
| **`name`**       | <code>string</code> | The name of the sensor.                                    |
| **`vendor`**     | <code>string</code> | The vendor of the sensor.                                  |
| **`version`**    | <code>number</code> | The version of the sensor.                                 |
| **`type`**       | <code>number</code> | The type of the sensor.                                    |
| **`maxRange`**   | <code>number</code> | The maximum range of the sensor in sensor units.           |
| **`resolution`** | <code>number</code> | The resolution of the sensor in sensor units.              |
| **`power`**      | <code>number</code> | The power consumption of the sensor in milliamperes.       |
| **`minDelay`**   | <code>number</code> | The minimum delay between sensor readings in microseconds. |
| **`maxDelay`**   | <code>number</code> | The maximum delay between sensor readings in microseconds. |


#### PermissionStatus

| Prop           | Type                                                                                                            |
| -------------- | --------------------------------------------------------------------------------------------------------------- |
| **`onchange`** | <code>(this: <a href="#permissionstatus">PermissionStatus</a>, ev: <a href="#event">Event</a>) =&gt; any</code> |
| **`state`**    | <code><a href="#permissionstate">PermissionState</a></code>                                                     |

| Method                  | Signature                                                                                                                                                                                                                                          | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----------------------- | -------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **addEventListener**    | &lt;K extends "change"&gt;(type: K, listener: (this: <a href="#permissionstatus">PermissionStatus</a>, ev: PermissionStatusEventMap[K]) =&gt; any, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a>) =&gt; void | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **addEventListener**    | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a>, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a>) =&gt; void                                     | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **removeEventListener** | &lt;K extends "change"&gt;(type: K, listener: (this: <a href="#permissionstatus">PermissionStatus</a>, ev: PermissionStatusEventMap[K]) =&gt; any, options?: boolean \| <a href="#eventlisteneroptions">EventListenerOptions</a>) =&gt; void       | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |
| **removeEventListener** | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a>, options?: boolean \| <a href="#eventlisteneroptions">EventListenerOptions</a>) =&gt; void                                           | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |


#### PermissionStatusEventMap

| Prop           | Type                                    |
| -------------- | --------------------------------------- |
| **`"change"`** | <code><a href="#event">Event</a></code> |


#### Event

An event which takes place in the DOM.

| Prop                   | Type                                                | Description                                                                                                                                                                                                                                                |
| ---------------------- | --------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **`bubbles`**          | <code>boolean</code>                                | Returns true or false depending on how event was initialized. True if event goes through its target's ancestors in reverse tree order, and false otherwise.                                                                                                |
| **`cancelBubble`**     | <code>boolean</code>                                |                                                                                                                                                                                                                                                            |
| **`cancelable`**       | <code>boolean</code>                                | Returns true or false depending on how event was initialized. Its return value does not always carry meaning, but true can indicate that part of the operation during which event was dispatched, can be canceled by invoking the preventDefault() method. |
| **`composed`**         | <code>boolean</code>                                | Returns true or false depending on how event was initialized. True if event invokes listeners past a ShadowRoot node that is the root of its target, and false otherwise.                                                                                  |
| **`currentTarget`**    | <code><a href="#eventtarget">EventTarget</a></code> | Returns the object whose event listener's callback is currently being invoked.                                                                                                                                                                             |
| **`defaultPrevented`** | <code>boolean</code>                                | Returns true if preventDefault() was invoked successfully to indicate cancelation, and false otherwise.                                                                                                                                                    |
| **`eventPhase`**       | <code>number</code>                                 | Returns the event's phase, which is one of NONE, CAPTURING_PHASE, AT_TARGET, and BUBBLING_PHASE.                                                                                                                                                           |
| **`isTrusted`**        | <code>boolean</code>                                | Returns true if event was dispatched by the user agent, and false otherwise.                                                                                                                                                                               |
| **`returnValue`**      | <code>boolean</code>                                |                                                                                                                                                                                                                                                            |
| **`srcElement`**       | <code><a href="#eventtarget">EventTarget</a></code> |                                                                                                                                                                                                                                                            |
| **`target`**           | <code><a href="#eventtarget">EventTarget</a></code> | Returns the object to which event is dispatched (its target).                                                                                                                                                                                              |
| **`timeStamp`**        | <code>number</code>                                 | Returns the event's timestamp as the number of milliseconds measured relative to the time origin.                                                                                                                                                          |
| **`type`**             | <code>string</code>                                 | Returns the type of event, e.g. "click", "hashchange", or "submit".                                                                                                                                                                                        |
| **`AT_TARGET`**        | <code>number</code>                                 |                                                                                                                                                                                                                                                            |
| **`BUBBLING_PHASE`**   | <code>number</code>                                 |                                                                                                                                                                                                                                                            |
| **`CAPTURING_PHASE`**  | <code>number</code>                                 |                                                                                                                                                                                                                                                            |
| **`NONE`**             | <code>number</code>                                 |                                                                                                                                                                                                                                                            |

| Method                       | Signature                                                          | Description                                                                                                                                                                                                                             |
| ---------------------------- | ------------------------------------------------------------------ | --------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **composedPath**             | () =&gt; EventTarget[]                                             | Returns the invocation target objects of event's path (objects on which listeners will be invoked), except for any nodes in shadow trees of which the shadow root's mode is "closed" that are not reachable from event's currentTarget. |
| **initEvent**                | (type: string, bubbles?: boolean, cancelable?: boolean) =&gt; void |                                                                                                                                                                                                                                         |
| **preventDefault**           | () =&gt; void                                                      | If invoked when the cancelable attribute value is true, and while executing a listener for the event with passive set to false, signals to the operation that caused event to be dispatched that it needs to be canceled.               |
| **stopImmediatePropagation** | () =&gt; void                                                      | Invoking this method prevents event from reaching any registered event listeners after the current one finishes running and, when dispatched in a tree, also prevents event from reaching any other objects.                            |
| **stopPropagation**          | () =&gt; void                                                      | When dispatched in a tree, invoking this method prevents event from reaching any objects other than the current object.                                                                                                                 |


#### EventTarget

<a href="#eventtarget">EventTarget</a> is a DOM interface implemented by objects that can receive events and may have listeners for them.

| Method                  | Signature                                                                                                                                                                                                              | Description                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| ----------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- | ---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------- |
| **addEventListener**    | (type: string, listener: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a> \| null, options?: boolean \| <a href="#addeventlisteneroptions">AddEventListenerOptions</a>) =&gt; void | Appends an event listener for events whose type attribute value is type. The callback argument sets the callback that will be invoked when the event is dispatched. The options argument sets listener-specific options. For compatibility this can be a boolean, in which case the method behaves exactly as if the value was specified as options's capture. When set to true, options's capture prevents callback from being invoked when the event's eventPhase attribute value is BUBBLING_PHASE. When false (or not present), callback will not be invoked when event's eventPhase attribute value is CAPTURING_PHASE. Either way, callback will be invoked if event's eventPhase attribute value is AT_TARGET. When set to true, options's passive indicates that the callback will not cancel the event by invoking preventDefault(). This is used to enable performance optimizations described in § 2.8 Observing event listeners. When set to true, options's once indicates that the callback will only be invoked once after which the event listener will be removed. The event listener is appended to target's event listener list and is not appended if it has the same type, callback, and capture. |
| **dispatchEvent**       | (event: <a href="#event">Event</a>) =&gt; boolean                                                                                                                                                                      | Dispatches a synthetic event event to target and returns true if either event's cancelable attribute value is false or its preventDefault() method was not invoked, and false otherwise.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                               |
| **removeEventListener** | (type: string, callback: <a href="#eventlisteneroreventlistenerobject">EventListenerOrEventListenerObject</a> \| null, options?: <a href="#eventlisteneroptions">EventListenerOptions</a> \| boolean) =&gt; void       | Removes the event listener in target's event listener list with the same type, callback, and options.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                  |


#### EventListener


#### EventListenerObject

| Method          | Signature                                    |
| --------------- | -------------------------------------------- |
| **handleEvent** | (evt: <a href="#event">Event</a>) =&gt; void |


#### AddEventListenerOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`once`**    | <code>boolean</code> |
| **`passive`** | <code>boolean</code> |


#### EventListenerOptions

| Prop          | Type                 |
| ------------- | -------------------- |
| **`capture`** | <code>boolean</code> |


#### SensorResult

Represents the result of a sensor reading.

| Prop            | Type                  | Description                                        |
| --------------- | --------------------- | -------------------------------------------------- |
| **`accuracy`**  | <code>number</code>   | The accuracy of the sensor reading, if available.  |
| **`timestamp`** | <code>number</code>   | The timestamp of the sensor reading, if available. |
| **`values`**    | <code>number[]</code> | The values obtained from the sensor reading.       |


#### PluginListenerHandle

| Prop         | Type                                      |
| ------------ | ----------------------------------------- |
| **`remove`** | <code>() =&gt; Promise&lt;void&gt;</code> |


### Type Aliases


#### SensorType

<code>(typeof SensorTypes)[number]</code>


#### SensorDelay

<code>(typeof SensorDelays)[number]</code>


#### EventListenerOrEventListenerObject

<code><a href="#eventlistener">EventListener</a> | <a href="#eventlistenerobject">EventListenerObject</a></code>


#### PermissionState

<code>"denied" | "granted" | "prompt"</code>

</docgen-api>
