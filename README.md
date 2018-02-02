# DashO-GameOfLife

>A sample Android app that demonstrates using _PreEmptive Protection - DashO_ with libraries and product flavors

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

* Obfuscate the library and the application independently.
* Obfuscate both together using a single DashO configuration.
* Ofuscate both together but with variant-specific DashO configurations.

These instructions will demonstrate the last scenario, which provides the strongest protection.

>**Note:** DashO can obfuscate both `debug` and `release` builds, but this sample will be using `debug` builds so a signing key does not have to be generated and configured.

## Prerequisites

* [PreEmptive Protection - DashO v8.4.0](https://www.preemptive.com/products/dasho/downloads) (or later)
* [Android Build Environment](https://developer.android.com/studio/index.html)
  * Platform v27
  * Build Tools v27.0.3

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


## Setting up Obfuscation

DashO's [New Project Wizard](https://www.preemptive.com/dasho/pro/userguide/en/getting_started_wizard.html#android) makes this project easy to set up.
In this case, you will be setting up a single default project and two projects for specific product flavors.
The library will be configured to be obfuscated as part of the main application.

>**Note:** You can preview the final DashO configuration by switching to the `obfuscated` branch. 

### Initial Compile

Compile the application by running `gradlew clean assembleDebug`.  This will compile all the debug Build Variants.

### The Default Project

This will set up the main DashO configuration, which will be used by any flavors that don't have a custom DashO configuration of their own.
Ultimately, it will only be used by the `menu` flavor builds.

<a name="wizard" />

1. Launch _PreEmptive Protection - DashO_. (Register if necessary)
2. If it is not already showing, start the New Project Wizard (**File > New Project > Wizard**).
3. Click `Next`.
4. Select `An Android application or library`.
5. Click `Next`.
6. Keep `Gradle` as the build environment.
7. Browse to this project and select the `app` directory or `app/build.gradle` file. <a name="selectVariant"/>
8. Select the `menuWithInteractionDebug` Build Variant.  The `menu` flavor builds use all the Actvities and Views which the `single` flavor builds use, so it can be used to create a default project.
9. Click `Next`.
10. Verify the `Android SDK Home` was found.
11. Verify `android-27` (or an appropriate value) is selected as the platform.
12. Click `Next`. It will analyze the files and resources.
13. This screen shows what classes were evaluated.
14. Click `Next`.
15. Four entry points will be selected.
16. Click `Next`.
17. Since this configuration won't be flavor-specific, leave the `Build Variant` boxes unchecked.
18. Click `Finish`.

The Gradle build environment has been configured to use the DashO Gradle Plugin and a DashO project has been created which will work to obfuscate all four variants of this project.

If you just re-build now, you would get an error on the library project because it does not have the configuration required by DashO.

Edit `library/build.gradle` and uncomment the `dashOConfig` line.
This will [configure](https://www.preemptive.com/dasho/pro/userguide/en/gradle/androidConf.html) DashO to not obfuscate the library.
We will configure obfuscation for this library [below](#library).

### The `singleWithInteraction` and `singleWithoutInteraction` Flavor Projects.

The `single` flavor projects do not use all the Activities which are used by the `menu` flavors.
If these builds used the default `project.dox`, created in the previous section, those Activities would remain in the application because they are referenced by that configuration.

This will set up configurations to better handle the `singleWithInteraction` and `singleWithoutInteraction` flavors, by only referencing the one Activity used by those flavors.

1. Run the wizard with the same steps [2-14](#wizard) used when creating the default project, but select `singleWithInteractionDebug` in step [8](#selectVariant).
2. You should now see one entry point selected.
3. Click `Next`.
4. Check the `Create a flavor-specific project...` box.
5. Click `Finish`

Repeat the above, but select `singleWithoutIteractionDebug` in step [8](#selectVariant). 

You now have two flavor-specific projects and one default project.
The flavor-specific projects reference the one Activity used by those flavors, while the default project references all four Activities used by the `menu` flavors.

Since this sample works with `debug` builds, edit `app/dasho.gradle` and remove the line about `disabledForBuildVariants` so DashO will obfuscate the `debug` builds.

When you build (`gradlew clean build`), the `single` flavors will not retain those extra classes, while the `menu` flavors still have all four.

However, the library is still not being obfuscated.
The next section will walk through obfuscating the library.

<a name="library"/>

### The `library` Project

DashO can obfuscate libraries as standalone projects creating an obfuscated `.AAR` file.
That approach, however, would require not renaming any class, method, or field used by any application that depends on the library.
In this sample, the library will be obfuscated as part of main application, which allows DashO to rename much more of the library's code.

In an earlier step, we turned off obfuscation of this library by uncommenting the `disabledForBuildVariants` line in `library/build.gradle`.
To include the library in DashO's obfuscation:

1.  Edit `app/dasho.gradle` and add `includeAsInputs = [':library']` inside the `dashOConfig` section.

Since the application's layout files reference the `GameOfLifeView` view defined in the library, that view needs to be added as an entry point, so that DashO will not remove the methods used by the reference in the XML resources.

1. Open `project.dox` in the DashO UI.
2. Navigate to `Entry Points` and click `New Class`.
3. Enter `io.kimo.gameoflifeview.view.GameOfLifeView` for the name of the class.
4. Click `New Method`
5. Enter `<init>` as the name of the method.
6. Enter `**` as the signature for the method.
7. Save the project.

>**Note:** The `single` flavor projects do not need this change because those variants use the view directly and do not reference it via an XML layout, so DashO will discover that code automatically.

When you build (`gradlew clean build`), the library is now obfuscated as part of each build variant of the application.

## Running the Application

### Building

Run `gradlew clean build` to compile and obfuscate all the variants.

### Installing

The four different variants can be simultaneously installed:

* `gradlew installMenuWithInteractionDebug` - Installs `Game Of Life (Interactive)`
* `gradlew installMenuWithoutInteractionDebug` - Installs `Game Of Life`
* `gradlew installSingleWithInteractionDebug` - Installs `Game Of Life View (Interactive)`
* `gradlew installSingleWithoutInteractionDebug` - Installs `Game Of Life View`

### Uninstalling

You can uninstall all the variants by running `gradlew uninstallAll`

### Everything at Once

Run a custom task to just do all of it: `gradlew doAllTheThings`

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
