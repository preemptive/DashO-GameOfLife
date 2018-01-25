# DashO-GameOfLife

A sample Android app that demonstrates using DashO with Libraries and Build Flavors.

This sample has four Build Flavors (`menu`, `single`, `withInteraction`, and `withoutInteraction`) and two Flavor Dimensions (`view` and `interactive`) creating four different applications:

* `menuWithInteraction` - Launches with a menu of three interactive views.
* `menuWithoutInteraction` - Launches with a menu of three non-interactive views.
* `singleWithInteraction` - Launches a single interactive view.
* `singleWithoutInteraction` - Launches a single non-interactive view.

## Building

Run `gradlew build` to compile all the versions.

## Installing

The four different versions can be simultaneously installed:

* `gradlew installMenuWithInteractionDebug` - Installs `Game Of Life (Interactive)`
* `gradlew installMenuWithoutInteractionDebug` - Installs `Game Of Life`
* `gradlew installSingleWithInteractionDebug` - Installs `Game Of Life View (Interactive)`
* `gradlew installSingleWithoutInteractionDebug` - Installs `Game Of Life View`

## Uninstalling

You can uninstall all the versions by running `gradlew uninstallAll`

## All of the above

Run the following command to just do all of it:
 `gradlew clean build uA installMenuWithInteractionDebug installMenuWithoutInteractionDebug installSingleWithInteractionDebug installSingleWithoutInteractionDebug`

## Original Source

This sample is based on [GameOfLifeView](https://github.com/thiagokimo/GameOfLifeView) by [Thiago Rocha](http://kimo.io).

## License

    Copyright 2018 PreEmptive Solutions, LLC.

    Copyright 2011, 2012 Thiago Rocha

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
