# DashO-GameOfLife

A sample Android™ app that demonstrates using [PreEmptive Protection™ DashO™](https://www.preemptive.com/products/dasho/overview) with the [DashO Gradle Plugin for Android](https://www.preemptive.com/dasho/pro/userguide/en/ref_dagp_index.html) on an application with libraries and product flavors.

This sample is a [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) simulation application.
This project consists of the app itself and a library that it uses.

The application has two flavor dimensions: `view` and `monetization`.
The `view` dimension determines if the application launches a `menu` with a list of two views or just launches a `single` view.
The `monetization` dimension determines whether the application is interactive (`paid`) or non-interactive (`free`).
This interaction allows the user to give life to a cell by tapping.

These dimensions are ultimately [combined](https://developer.android.com/studio/build/build-variants.html#flavor-dimensions) to create four applications:

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
>`Debug` builds can be protected by [configuring](https://www.preemptive.com/dasho/pro/userguide/en/ref_dagp_config.html#dasho) `enabledBuildVariants`.

## Prerequisites

* [Java™ 8](http://www.oracle.com/technetwork/java/index.html)
* [PreEmptive Protection DashO v10.2.0](https://www.preemptive.com/products/dasho/downloads) (or later)
* [Android Build Environment](https://developer.android.com/studio/index.html)
  * Platform v30

>**Note:** The Android-specific settings can be changed by editing the `build.gradle` files.

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


## Set Up Protection

Setting up protection involves the following steps:

1. Run the DashO [New Project Wizard](https://www.preemptive.com/dasho/pro/userguide/en/getting_started_android.html#gradle-wizard).
2. Configure [DashO Home](https://www.preemptive.com/dasho/pro/userguide/en/ref_dagp_dasho_home.html) (if needed).
3. Customize the configuration.

>**Note:** You can preview the final configuration by switching to the `obfuscated` branch.

### Run the DashO New Project Wizard

The wizard will guide you through the process of integrating the *DashO Gradle Plugin for Android* into your build.

1. Launch the DashO GUI.
2. Launch the New Project Wizard by going to `File > Project Wizard...`, if it is not started automatically.
3. Select `Next`, select `Android (Most projects)`, and select `Next` again.
4. To enter the project directory, select `DashO-GameOfLife/app` (or `DashO-GameOfLife/app/build.gradle`), and select `Next`.
5. After reading the Summary, select `Finish` to have the wizard do the integration.

At this point the initial integration is complete.

### Configure DashO Home (If Needed)

The *DashO Gradle Plugin for Android* will automatically find DashO if it is installed in the default location.
Otherwise, the plugin needs to know where [DashO Home](https://www.preemptive.com/dasho/pro/userguide/en/install_installation.html#dasho-home) is so it can run DashO to protect the code.
There are multiple ways to [configure DashO Home](https://www.preemptive.com/dasho/pro/userguide/en/ref_dagp_dasho_home.html).

The wizard will have already added a `dasho { }` closure in `build.gradle` with a `home` line commented out.
Uncommenting this line will set DashO Home to the location of the current DashO installation.

### Customize the Configuration

Since this sample demonstrates product flavor support, we need two different configuration files.
One to handle the `free` variants and one for the `paid` variants.

These two configurations will start with the same information.
The `paid` variants will use `app/project.dox`.

#### Configure the 'free' Variants

To further encourage users to "buy" the `paid` version, an Emulator Check will be added to the `free` variants.
If the application is run on an emulator, the entire view will be covered by oscillating [blinkers](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life#Examples_of_patterns).

1. Copy `app/project.dox` to `app/free.dox`.
   This file will be used by the `free` variants.
2. Launch the DashO GUI (if not already opened).
3. Open `app/free.dox`.
4. If it prompts you to run a build:
    1. Click `OK`.
    2. Run `gradlew clean assembleSingleFreeRelease` from the command line.
    3. Click the refresh button once it is highlighted.
5. Go to the `Checks->Emulator` page in the GUI.
6. Click `Add` and choose `Emulator Check`
7. Under `Locations`, check `checkTheLock()` under `AbstractGameOfLifeActivity`.
   (You will need to scroll down past the `androidx` packages and expand `AbstractGameOfLifeActivity` under `com.preemptive.dasho.example.gameoflife`.)
8. Set the `Action` to `setLocked()` and click `OK`.
9. Save the file.

## Run the Application

Because of the Emulator Check, the application will respond differently based on where it is run.

|             |                   Paid Variants                      |                                 Free Variants                                 |
|-------------|------------------------------------------------------|-------------------------------------------------------------------------------|
|**Device**   | Interactive views. Tapping a cell brings it to life. | Non-interactive views. Tapping tells user to "upgrade".                       |
|**Emulator** | Interactive views. Tapping a cell brings it to life. | Non-interactive views with blinker patterns. Tapping tells user to "upgrade". |


![Screenshot](screenshot.png)

The first two screens are the starting pages for the `menu` and `single` variants.
The last screen shows what to expect when running a `free` variant on an emulator.

### Build

Run `gradlew clean assembleRelease` to compile and protect all the release variants.

### Install

The four different variants can be simultaneously installed:

* `gradlew installMenuFreeRelease` - Installs _DashO MF Game Of Life_.
* `gradlew installMenuPaidRelease` - Installs _DashO MP Game Of Life_.
* `gradlew installSingleFreeRelease` - Installs _DashO SF Game Of Life_.
* `gradlew installSinglePaidRelease` - Installs _DashO SP Game Of Life_.

### Uninstall

You can uninstall all the variants by running `gradlew uninstallAll`.

### Everything at Once

Run a custom task to just do all of it: `gradlew doAllTheThings`.

## Verify Protection

You can validate the build is using the appropriate configurations and that it is protecting the application.

### Review DashO's Protection

The output from the Gradle build will show when DashO is run.

### Verify the Flavor-specific Configuration

If you add `-DSHOW_DASHO_CMD` when building (e.g. `gradlew doAllTheThings -DSHOW_DASHO_CMD`), the information being passed to DashO will be printed to the console as the `transformClassesAndResourcesWithDashOFor...` tasks are run.
This will include a `Running:` line where you can see the full arguments used to run DashO including which `.dox` configuration file is passed.

### Verbose Output

If you want to see more information on what DashO is doing, you can [configure](https://www.preemptive.com/dasho/pro/userguide/en/ref_dagp_config.html#dasho) `verbose true` inside the `dasho` closure in `app/build.gradle`.
This will provide you with the verbose output from the protection process.

### Decompile the APK

To further investigate you can use the following tools to look at the final protected APK:

* [Apktool](https://ibotpeaches.github.io/Apktool/)
* [dex2jar](https://github.com/pxb1988/dex2jar)
* [Bytecode Viewer](https://bytecodeviewer.com/)

## About DashO

[PreEmptive Protection DashO](https://www.preemptive.com/products/dasho/) is an obfuscation and application protection solution from [PreEmptive](https://www.preemptive.com/).
DashO's obfuscation, hardening, and runtime check features make your application much harder to reverse engineer, pirate, or steal data from.
World-class technical support for DashO is available through our [website](https://www.preemptive.com/support/dasho-support) and/or our [support request form](https://www.preemptive.com/contact/supportrequestform).

## Original Source

This sample is based on [GameOfLifeView](https://github.com/thiagokimo/GameOfLifeView) by [Thiago Rocha](http://kimo.io).

## License

    Copyright 2020 PreEmptive Solutions, LLC.

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

Android is a trademark of Google LLC.
GitHub is a registered trademark of GitHub, Inc.
Gradle is a registered trademark of Gradle, Inc.
Oracle and Java are registered trademarks of Oracle and/or its affiliates.
Other names may be trademarks of their respective owners.
