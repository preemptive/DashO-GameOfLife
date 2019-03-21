# DashO-GameOfLife

A sample Android app that demonstrates using [PreEmptive Protection - DashO](https://www.preemptive.com/products/dasho/overview) with libraries and product flavors.

This sample is a [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) simulation application that uses a library, both built in the same project.
The application has two flavor dimensions (`view` and `monetization`) which [combine](https://developer.android.com/studio/build/build-variants.html#flavor-dimensions) four product flavors (`menu`, `single`, `free`, and `paid`).

The `view` dimension determines if the application launches a `menu` with a list of two views or just launches a `single` view.

The `monetization` dimension determines if the user can give life to a cell.

This ultimately creates four applications:

|             |                      `paid`                         |                         `free`                          |
|-------------|-----------------------------------------------------|---------------------------------------------------------|
|**`menu`**   | `menuPaid` - Shows a menu of two interactive views. | `menuFree` - Shows a menu of two non-interactive views. |
|**`single`** | `singlePaid` - Shows a single interactive view.     | `singleFree` - Shows a single non-interactive view.     |

The library is used by all four applications.
The `menu` flavors use the library's code directly and reference the library's `GameOfLifeView` class via XML.
The `single` flavors only use the library's code directly.

These instructions will demonstrate protecting the application with variant-specific configurations.

>**Note:** DashO can protect both `debug` and `release` builds.
>This sample will be protecting `release` builds.
>`Debug` builds can be protected by [configuring](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_config.html) `enabledBuildVariants`.

## Prerequisites

* [Java 8](http://www.oracle.com/technetwork/java/index.html)
* [PreEmptive Protection - DashO v10.0.0](https://www.preemptive.com/products/dasho/downloads) (or later)
* [Android Build Environment](https://developer.android.com/studio/index.html)
  * Platform v28
  * Android Gradle Plugin v3.1.0 (or later)

>**Note:** The Android-specific requirements can be changed by editing the `build.gradle` files.

## Code Layout

This sample uses a standard layout for an Android project.

* `library` - The library project.
* `app` - The application project.
* `app/src/main` - The majority of the application source.
* `app/src/free` - Code which does not allow user interaction with the `GameOfLifeView`.
* `app/src/paid` - Code which allows user interaction with the `GameOfLifeView`.
* `app/src/menu` - The `AndroidManifest.xml` for all `menu` flavors.
* `app/src/single` - The `AndroidManifest.xml` for all `single` flavors.
* `app/src/menuFree` - Resources for the `menuFree` flavor.
* `app/src/menuPaid` - Resources for the `menuPaid` flavor.
* `app/src/singleFree` - Resources for the `singleFree` flavor.
* `app/src/singlePaid` - Resources for the `singlePaid` flavor.


## Setting up Protection

Setting up protection involves five steps:

1. Configuring the plugin's location.
2. Applying the [DashO Android Gradle Plugin](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_index.html).
3. Configuring [DashO Home](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_dasho_home.html).
4. Generating a configuration file.
5. Customizing the configuration.

### Configuring the Plugin's Location

The Gradle build script needs to be able to find the [DashO Android Gradle Plugin](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_index.html).

In `build.gradle` add the following to the `repositories` section which is inside `buildscript`:

`maven { url 'https://maven.preemptive.com' }`

In `build.gradle` add the following to the `dependencies` section which is inside `buildscript`:

`classpath 'com.preemptive.dasho:dasho-android:0.9.+'`

### Applying the DashO Android Gradle Plugin

Applying the plugin will incorporate DashO into the build process.

In `app/build.gradle` add the following after `apply plugin: 'com.android.application'`:

`apply plugin: 'com.preemptive.dasho.android'`

### Configuring DashO Home

The plugin needs to know where DashO is located so it can protect the code.

Edit `gradle.properties` and set `DASHO_HOME` to the [appropriate location](https://www.preemptive.com/dasho/pro/10.0/userguide/en/install_installation.html#dasho-home).

>**Note:**<br/>
>There are [other ways](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_dasho_home.html) to configure `DASHO_HOME`.

### Generating a Configuration File

The basic configuration can be generated during the build.

Run the following command:

`gradlew clean assembleRelease -DGENERATE_DASHO_CONFIG`

That will generate an [Android Mode](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_android_mode.html) `project.dox` file in the `app` directory.

### Customizing the Configuration

Since this demo will a different configuration for the `free` variants we need two different configuration files.

These two configurations will start with the same information.
We are excluding Android's classes as they are not relevant to this sample.

1. Launch the DashO GUI (if not already opened).
2. Open `app/project.dox`.
3. If it prompts you to run a build:
    1. Click `OK`.
    2. Run `gradlew clean assembleSinglePaidRelease` from the command line.
    3. Refresh when prompted.
4. Go to `Global Exclude` in the GUI.
5. Click `New Class`
6. Type `android*.**` for the `name` and click OK.
7. Save the file.

#### Configuring the 'free' Variants.

To further encourage users to "buy" the `paid` version, an Emulator Check will be added to the `free` variants.
If the application is run on an emulator, the view will only show blinkers.

1. Copy `app/project.dox` to `app/free.dox`.
   This file will be used by the `free` variants.
2. Launch the DashO GUI (if not already opened).
3. Open `app/free.dox`.
4. If it prompts you to run a build:
    1. Click `OK`.
    2. Run `gradlew clean assembleSingleFreeRelease` from the command line.
    3. Refresh when prompted.
5. Go to `Checks->Emulator` in the GUI.
6. Click `Add` and choose `Emulator Check`
7. In the locations, check `checkTheLock()` under `AbstractGameOfLifeActivity`.
8. Set the `Action` to `setLocked()` and click `OK`.
9. Save the file.

## Running the Application

Because of the Emulator Check, the application will respond differently based on where it is run.

|             |                   Paid Variants                       |                                  Free Variants                                 |
|-------------|-------------------------------------------------------|--------------------------------------------------------------------------------|
|**Device**   | Interactive views. Clicking a cell brings it to life. | Non-interactive views. Clicking tells user to "upgrade".                       |
|**Emulator** | Interactive views. Clicking a cell brings it to life. | Non-interactive views with blinker patterns. Clicking tells user to "upgrade". |


![Screenshot](screenshot.png)

The first two screens are the starting pages for the `menu` and `single` variants.
The last screen shows what to expect when running a `free` variant on an emulator.

### Building

Run `gradlew clean assembleRelease` to compile and protect all the release variants.

### Installing

The four different variants can be simultaneously installed:

* `gradlew installMenuFreeRelease` - Installs _DashO MF Game Of Life_.
* `gradlew installMenuPaidRelease` - Installs _DashO MP Game Of Life_.
* `gradlew installSingleFreeRelease` - Installs _DashO SF Game Of Life_.
* `gradlew installSinglePaidRelease` - Installs _DashO SP Game Of Life_.

### Uninstalling

You can uninstall all the variants by running `gradlew uninstallAll`.

### Everything at Once

Run a custom task to just do all of it: `gradlew doAllTheThings`.

## Verifying Protection

You can validate the build is using the appropriate configurations and that it is protecting the application.

### Reviewing DashO's Protection

The output from the Gradle build will show when DashO is run.

### Verify the Flavor-specific Configuration

If you add `-DSHOW_DASHO_CMD` when building (e.g. `gradlew doAllTheThings -DSHOW_DASHO_CMD`), the information being passed to DashO will be printed to the console as the `transformClassesAndResourcesWithDashOFor...` tasks are run.
This will include a `Running:` line where you can see the full arguments used to run DashO including which `.dox` configuration file is passed.

### Verbose Output

If you want to see more information on what DashO is doing, you can add a `dasho` closure to `app/build.gradle` and [configure](https://www.preemptive.com/dasho/pro/10.0/userguide/en/ref_dagp_config.html#dasho_closure_properties) `verbose true`.
This will provide you with the verbose output from the protection process.

### Decompiling the APK

To further investigate you can use the following tools to look at the final protected APK:

* [Apktool](https://ibotpeaches.github.io/Apktool/)
* [dex2jar](https://github.com/pxb1988/dex2jar)
* [Bytecode Viewer](https://bytecodeviewer.com/)

## About DashO

_PreEmptive Protection - DashO_ is a [Java and Android obfuscator](https://www.preemptive.com/products/dasho/) and application hardening solution from [PreEmptive Solutions](https://www.preemptive.com/).
DashO's obfuscation, hardening, and runtime check features make your application much harder to reverse engineer, pirate, or steal data from.
World-class technical support for DashO is available through our [website](https://www.preemptive.com/support/dasho-support) and/or our [support request form](https://www.preemptive.com/contact/supportrequestform).

## Original Source

This sample is based on [GameOfLifeView](https://github.com/thiagokimo/GameOfLifeView) by [Thiago Rocha](http://kimo.io).

## License

    Copyright 2019 PreEmptive Solutions, LLC.

    Copyright 2011, 2012 Thiago Rocha

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       <http://www.apache.org/licenses/LICENSE-2.0>

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
