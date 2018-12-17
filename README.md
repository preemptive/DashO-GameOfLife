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

These instructions will demonstrate the last scenario, which provides the strongest protection.

>**Note:** DashO can protect both `debug` and `release` builds, but this sample will be using `debug` builds so a signing key does not have to be generated and configured.

## Prerequisites

* [Java 8](http://www.oracle.com/technetwork/java/index.html)
* [PreEmptive Protection - DashO v9.2.0](https://www.preemptive.com/products/dasho/downloads) (or later)
* [Android Build Environment](https://developer.android.com/studio/index.html)
  * Platform v28

>**Note:** The Android-specific requirements can be changed by editing the `build.gradle` files.

## Code Layout

This sample uses a standard layout for an Android project.

* `library` - The library project.
* `app` - The application project.
* `app/src/main` - The majority of the application source.
* `app/src/menu` - The `AndroidManifest.xml` for the `main` flavor.
* `app/src/menuWithInteraction` - Resources for this flavor's application name.
* `app/src/menuWithoutInteraction` - Resources for this flavor's application name.
* `app/src/single` - The `AndroidManifest.xml` for the `single` flavor.
* `app/src/singleWithInteraction` - Resources for this flavor's application name.
* `app/src/singleWithoutInteraction` - Resources for this flavor's application name.
* `app/src/withInteraction` - Code which sets up user interaction with the `GameOfLifeView`.
* `app/src/withoutInteraction` - Code which does not allow user interaction with the `GameOfLifeView`.


## Setting up Protection

DashO's [New Project Wizard](https://www.preemptive.com/dasho/pro/userguide/en/getting_started_wizard.html#android) makes this project easy to set up.
In this case, you will be setting up a single default project and two projects for specific product flavors.
The library will be configured to be protected as part of the main application.

>**Note:** You can preview the final DashO configuration by switching to the `obfuscated` branch.

### Initial Compile

Compile the application by running `gradlew clean assembleDebug`.
This will compile all the debug Build Variants.

### The Default Project

This section shows how to set up the main DashO configuration, which will be used by any flavors that don't have a custom DashO configuration of their own.
Ultimately, it will only be used by the `menu` flavor builds.

<a name="wizard" />

1. Launch _PreEmptive Protection - DashO_. (Register if necessary)
2. If it is not already showing, start the New Project Wizard (**File > Project Wizard**).
3. Click `Next`.
4. Select `An Android application or library`.
5. Click `Next`.
6. Keep `Gradle` as the build environment.
7. Browse to this project and select the `app` directory or `app/build.gradle` file. <a name="selectVariant"/>
8. Select the `menuWithInteractionDebug` Build Variant. The `menu` flavor builds use all the Activities and Views which the `single` flavor builds use, so it can be used to create a default project.
9. Click `Next`.
10. Verify the `Android SDK Home` was found.
11. Verify `android-28` (or an appropriate value) is selected as the platform.
12. Click `Next`. It will analyze the files and resources.
13. This screen would allow you to add local `.jar` files to the project; not necessary in this case.
14. Click `Next`.
15. Four entry points will be selected.
16. Click `Next`.
17. Since this configuration won't be flavor-specific, leave the `Build Variant` boxes unchecked.
18. Click `Finish`. <a name="set_remove"/>
19. Go to the [`Removal` section](https://www.preemptive.com/dasho/pro/userguide/en/ui_removal.html#options) in the UI.
20. Set `Unused Classes:` to `Remove` allowing DashO to remove more unused classes.
21. Set `Unused Methods:` to `Remove` allowing DashO to remove more unused methods.
22. Go to the [`Entry Points -> Special Classes` section](https://www.preemptive.com/dasho/pro/userguide/en/ui_entry.html#special_classes) in the UI.
23. Open the properties of `CustomParamsActivity`, `ThroughCodeActivity`, and `ThroughXMLActivity` and check `Rename Class`.
    This improves the strength of protection as it will rename these classes and their entries in the manifest.
24. Save the project.

The Gradle build environment has now been configured to use the DashO Gradle Plugin and a DashO project has been created which will work to protect all four variants of this project.

>**Note:** The wizard creates a backup of any file it modifies.

### The `singleWithInteraction` and `singleWithoutInteraction` Flavor Projects.

The `single` flavor projects do not use all the Activities which are used by the `menu` flavors.
If these builds used the default `project.dox`, created in the previous section, those Activities would remain in the application because they are referenced by that configuration.

This section shows how to set up configurations to better handle the `singleWithInteraction` and `singleWithoutInteraction` flavors, by only referencing the one Activity used by those flavors.

1. Run the wizard with the same steps [2-14](#wizard) used when creating the default project, but select `singleWithInteractionDebug` in step [8](#selectVariant).
2. You should now see one entry point selected.
3. Click `Next`.
4. Check the `Create a flavor-specific project...` box to name the `.dox` file for this flavor specifically.
5. Click `Finish`
6. Repeat the `Removal` steps [19-21](#set_remove).
7. Save the project.

Repeat the above steps, but select `singleWithoutIteractionDebug` in step [8](#selectVariant).

You now have two flavor-specific projects and one default project.
The flavor-specific projects reference the one Activity used by those flavors, while the default project references all four Activities used by the `menu` flavors.

Since this sample works with `debug` builds, edit `app/dasho.gradle` and add `android{buildTypes{debug{minifyEnabled true}}}` at the bottom so DashO will protect the `debug` builds.

When you build (`gradlew clean build`), the `single` flavors will not retain those extra classes, while the `menu` flavors still have all four.

However, the library is still not being protected.
The next section will walk through protecting the library.

<a name="library"/>

### The `library` Project

DashO can protect libraries as standalone projects creating a protected `.AAR` file.
That approach, however, would require not renaming any class, method, or field used by any application that depends on the library.
In this sample, the library will be protected as part of main application, which allows DashO to rename and remove many more of the library's classes, methods, and fields.

To include the library when DashO protects the application:

1. Edit `app/dasho.gradle` and add `includeAsInputs = [':library']` inside the `dashOConfig` section.

Since the application's layout files reference the `GameOfLifeView` view defined in the library, that view needs to be added as an entry point, so that DashO will not remove or rename the methods used by the reference in the XML resources.

1. Open `app/project.dox` in the DashO UI.
2. Navigate to `Entry Points` and click `New Class`.
3. Enter `io.kimo.gameoflifeview.view.GameOfLifeView` for the name of the class.
4. Click `OK`.
5. Click `New Method`.
6. Enter `<init>` as the name of the method.
7. Enter `**` as the signature for the method.
8. Click `OK`.
9. Save the project.

>**Note:** The `single` flavor projects do not need this change because those variants use the view directly and do not reference it via an XML layout, so DashO will discover that code automatically.

When you build (`gradlew clean build`), the library is now protected as part of each build variant of the application.

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
Review DashO's report files which will be in the `app/build/dasho-results` directory, under directories specific to the product flavor and build type (e.g. `app/build/dasho-results/menuWithInteraction/debug`).

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

    Copyright 2018 PreEmptive Solutions, LLC.

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
