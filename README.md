# DashO-GameOfLife

A sample Android app that demonstrates using [PreEmptive Protection - DashO](https://www.preemptive.com/products/dasho/overview) with libraries and product flavors.

This sample is a [Game of Life](https://en.wikipedia.org/wiki/Conway%27s_Game_of_Life) simulation application that uses a library, both built in the same project.
The application has two flavor dimensions (`view` and `interactive`) which [combine](https://developer.android.com/studio/build/build-variants.html#flavor-dimensions) four product flavors (`menu`, `single`, `withInteraction`, and `withoutInteraction`).

The `view` dimension determines if the application launches a `menu` with a list of three views or just launches a `single` view.

The `interaction` dimension determines if the user can give life to a cell.

This ultimately creates four applications:

|             |                   `withInteraction`                              |                         `withoutInteraction`                            |
|-------------|------------------------------------------------------------------|-------------------------------------------------------------------------|
|**`menu`**   | `menuWithInteraction` - Shows a menu of three interactive views. | `menuWithoutInteraction` - Shows a menu of three non-interactive views. |
|**`single`** | `singleWithInteraction` - Shows a single interactive view.       | `singleWithoutInteraction` - Shows a single non-interactive view.       |

The library is used by all four applications.
The `menu` flavors use the library's code directly and reference the library's `GameOfLifeView` class via XML.
The `single` flavors only use the library's code directly.

There are different ways DashO could be set up to handle this project:

* Protect the library and the application independently.
* Protect both together using a single DashO configuration.
* Protect both together but with variant-specific DashO configurations.

The instructions on the `master` branch demonstrate the last scenario, which provides the strongest protection.

## Prerequisites

* [Java 8](http://www.oracle.com/technetwork/java/index.html)
* [PreEmptive Protection - DashO v9.3.0](https://www.preemptive.com/products/dasho/downloads) (or later)
* [Android Build Environment](https://developer.android.com/studio/index.html)
  * Platform v28

>**Note:** The Android-specific requirements can be changed by editing the `build.gradle` files

## Code Layout

This sample uses a standard layout for an Android project.

* `library` - The library project.
* `app` - The application project.
* `app/src/main` - The majority of the application source.
* `app/src/menu` - The `AndroidManifest.xml` for the `main` flavor.
* `app/src/menuWithInteraction` -  Resources for this flavor's application name.
* `app/src/menuWithoutInteraction` - Resources for this flavor's application name.
* `app/src/single` -  The `AndroidManifest.xml` for the `single` flavor.
* `app/src/singleWithInteraction` - Resources for this flavor's application name.
* `app/src/singleWithoutInteraction` - Resources for this flavor's application name.
* `app/src/withInteraction` - Code which sets up user interaction with the `GameOfLifeView`.
* `app/src/withoutInteraction` - Code which does not allow user interaction with the `GameOfLifeView`.


## Setting up Protection

This branch already contains the DashO configuration.
When following the steps on the `master` branch, the following files were created and/or modified:

* `build.gradle` - The DashO Gradle plugin was added to the build script.
* `app/build.gradle` - Applied `dasho.gradle` to the build script.
* `app/dasho.gradle` - Created via the wizard to hold the DashO Gradle plugin's configuration.
* `app/project.dox` - Created via the wizard as the default project.
* `app/singleWithInteraction.dox` - Created via the wizard for the `singleWithInteraction` product flavor.
* `app/singleWithoutInteraction.dox` - Created via the wizard for the `singleWithoutInteraction` product flavor.

Some of these files were further modified, on this branch, to make this sample more usable:

* The default value for `sdk.dir` in the DashO configuration (`.dox`) files was replaced with `${ANDROID_SDK_ROOT}`.
* The path to the DashO installation in `build.gradle` and `app\dasho.gradle` was replaced with a `${DASHO_HOME}` property reference.

>**Note:**
>`DASHO_HOME` must be configured with location of the DashO installation so the script can find the plugin.
>Edit `gradle.properties` and set it with the path to your [DashO installation](https://www.preemptive.com/dasho/pro/userguide/en/getting_started_first.html#install_dir).

## Running the Application

### Building

Run `gradlew clean build` to compile and protect all the variants.

### Installing

The four different variants can be simultaneously installed:

* `gradlew installMenuWithInteractionDebug` - Installs _DashO MI Game Of Life_.
* `gradlew installMenuWithoutInteractionDebug` - Installs _DashO MN Game Of Life_.
* `gradlew installSingleWithInteractionDebug` - Installs _DashO SI Game Of Life_.
* `gradlew installSingleWithoutInteractionDebug` - Installs _DashO SN Game Of Life_.

### Uninstalling

You can uninstall all the variants by running `gradlew uninstallAll`.

### Everything at Once

Run a custom task to just do all of it: `gradlew doAllTheThings`.

## Verifying Protection

You can validate the build is using the appropriate configurations and that it is protecting the application.

### Reviewing DashO's Protection

The output from DashO will show how classes were renamed and what was removed.
Review DashO's report files  which will be in the `app/build/dasho-results` directory, under directories specific to the product flavor and build type (e.g. `app/build/dasho-results/menuWithInteraction/debug`).

### Verify the Flavor-specific Configuration

If you add `-DSHOW_DASHO_CMD` when building (e.g. `gradlew doAllTheThings -DSHOW_DASHO_CMD`), the information being passed to DashO will be printed to the console as the `transformClassesAndResourcesWithDashOFor...` tasks are run.
This will include a `Running:` line where you can see the full arguments used to run DashO including which `.dox` configuration file is passed.

### Verbose Output

If you want to see more information on what DashO is doing, you can add `verbose = true` to the `dashOConfig` section of `app/dasho.gradle`.
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
