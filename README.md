GoogleProgressBar 
=================

**This library is not maintained anymore and there will be no further releases**

Android library to display different kind of google related animations for the progressBar.

These animations have been finished so far:
  
![FoldingCirclesProgressBar][3] ![GoogleMusicDicesDrawable][4] ![NexusRotationCross.gif][5] ![NexusRotationCross.gif][11]

TODO
----

 * Different colors for the already existing `GoogleMusicDicesDrawable'. Either the whole dice or his faces.

I also would love to receive your pull requests to create any of the following animations or others that you think fit on this library:

 * [NEXUS_CIRCLES](http://ikslawok.free.fr/my_nexus_fr/nexus_5/bootanimation_nexus_5.gif):

    Nexus 5 circles boot progress animation (Just the circles bouncing)

 * [NEXUS_CROSS](http://devfest.gdgthess.org/wp-content/uploads/2013/11/nexus-4-boot-animation.gif):

    Galaxy nexus shinny cross boot animation
 
 

Usage
-----

######Dynamically
Add a ProgressBar to the xml layout:

```xml
     <ProgressBar
            android:id="@+id/google_progress"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_gravity="center"/>
```

Choose from the list of `Drawable`s the one you want to use and place it in your code:

```java
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        mProgressBar.setIndeterminateDrawable(new your_list_option.Builder(this)
                    .build());
        //...
    }
```

`your_list_option` can be replace for:

* FoldingCirclesDrawable
* GoogleMusicDicesDrawable
* NexusRotationCrossDrawable
* ChromeFloatingCircles

The more custom `Drawable`s finished, the more options in this list.

**Attributes depending on the drawable:**

######Color
```java
    mProgressBar.setIndeterminateDrawable(new you_list_option.Builder(this)
                                                             .colors(getResources().getIntArray(R.array.colors) //Array of 4 colors
                                                             .build());
```

* FoldingCirclesDrawable
* NexusRotationCrossDrawable
* ChromeFloatingCirclesDrawable

So far `GoogleMusicDicesDrawable` doesn't have color options.
The animation speed can be modified easily with `android:indeterminateDuration` in the xml.

If not colors are define the 4 default google colors (red,blue, yellow and green) will be used.

######From XML

When you want to use the `GoogleProgresBar` from XML you need to add the following view to your layout:

```java
    <com.jpardogo.android.googleprogressbar.library.GoogleProgressBar
            android:id="@+id/google_progress"
            android:layout_width="50dp"
            android:layout_height="50dp"
            android:layout_gravity="center"
            gpb:type="your_list_option"/>
```
The require attribute `gpb:type` will specify the type of `ProgressBar` to display

`your_list_option` can be replace for:

* folding_circles
* nexus_rotation_cross
* google_music_dices
* chrome_floating_circles

The more custom `Drawable`s finished, the more options in this list.

Each type of `GoogleProgressBar` have different attributes:

**Attributes depending on type:**
######Color
* folding_circles
* nexus_rotation_cross
    * `gpb:colors="@array/colors"`
        * Optional, If not colors are define the 4 default google colors (red,blue, yellow and green) will be used.
        * It needs to be an array of 4 colors

So far `google_music_dices` doesn't have color options.
The animation speed can be modified easily with `android:indeterminateDuration` in the xml.

Including in your project
-------------------------

You can either add the library to your application as a library project or add the following dependency to your build.gradle:

![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.jpardogo.googleprogressbar/library/badge.svg)

```groovy
dependencies {
    compile 'com.jpardogo.googleprogressbar:library:(latest version)'
}
```

Proguard
--------
In order to use this library with proguard you need to add this line to your `proguard.cfg`:

```grovy
-keep class com.jpardogo.android.googleprogressbar.** { *; }
````

How to contribute?
------------------
- Pull request to dev branch NO master.

Acknowledgements
----------------
* Thanks to [RomainPiel][8],[Melnykov Oleksandr][9], [Evelio Tarazona Cáceres][10] and [MewX][12] for his collaboration.
* Thanks to [Gabriele Mariotti][6] for his [colorpickercollection repo][7] that help me to create the example app.

Developed By
------------

Javier Pardo de Santayana Gómez - <jpardogo@gmail.com>

<a href="https://twitter.com/jpardogo">
  <img alt="Follow me on Twitter"
       src="https://raw.github.com/jpardogo/ListBuddies/master/art/ic_twitter.png" />
</a>
<a href="https://plus.google.com/u/0/+JavierPardo/posts">
  <img alt="Follow me on Google+"
       src="https://raw.github.com/jpardogo/ListBuddies/master/art/ic_google+.png" />
</a>
<a href="http://www.linkedin.com/profile/view?id=155395637">
  <img alt="Follow me on LinkedIn"
       src="https://raw.github.com/jpardogo/ListBuddies/master/art/ic_linkedin.png" />

License
-----------

    Copyright 2013 Javier Pardo de Santayana Gómez

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

[1]: https://play.google.com/store/apps/details?id=com.jpardogo.android.googleprogressbar
[2]: https://raw.github.com/jpardogo/ListBuddies/master/art/google_play%20badge.png
[3]: https://raw.githubusercontent.com/jpardogo/GoogleProgressBar/master/art/GoogleProgressBar.gif
[4]: https://raw.githubusercontent.com/jpardogo/GoogleProgressBar/dev/art/GoogleDices.gif
[5]: https://raw.githubusercontent.com/jpardogo/GoogleProgressBar/master/art/NexusRotationCross.gif
[6]: https://github.com/gabrielemariotti
[7]: https://github.com/gabrielemariotti/colorpickercollection
[8]: https://github.com/RomainPiel
[9]: https://github.com/makovkastar
[10]: https://github.com/eveliotc
[11]: https://raw.githubusercontent.com/MewX/google-progress-bar/gpb-chrome/art/ChromeFloatingCircles.gif
[12]: https://github.com/MewX
