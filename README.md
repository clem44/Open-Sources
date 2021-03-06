# Open-Sources
This library allows for quick dynamic generation of Open source Activity/Fragment for your application. 
It renders a list of open source information that you will provide. See the example app for a demonstration of how it works.
I’m trying to make it as customizable as possible, so in the near future I'll add more styling options. But for the time being it meets the basic needs.

### Style One
<img src="http://clemgumbs.com/img/apps/Screenshot_1487865229.png" width="30%" style="padding:10px">
<img src="http://clemgumbs.com/img/apps/Screenshot_1487865242.png" width="30%" style="padding:10px">

### Style Two
<img src="http://clemgumbs.com/img/apps/Screenshot_1487865415.png" width="30%" style="padding:10px">
<img src="http://clemgumbs.com/img/apps/Screenshot_1487865271.png" width="30%" style="padding:10px">



## Sample
<a href="https://play.google.com/store/apps/details?id=com.codeogenic.opensource_libraries"><img src="https://upload.wikimedia.org/wikipedia/commons/thumb/c/cd/Get_it_on_Google_play.svg/2000px-Get_it_on_Google_play.svg.png" width="25%"></a>

download sample from repository
<a href="https://github.com/clem44/Open-Sources/tree/master/app/build/outputs/apk"> Sample App download</a>

## Present & Futrue Features
* Activity Builder that initialises an Open sources Activity
* Fragment Builder that initialises an Open sources Fragment 
* Choose between two different default styles. (more styling options to come)
* Array style of appending your library information.
* Theme styling


### Installation

[for now] clone the repository, and add the opensources library to your project
(in Android Studio as a module) (in Eclipse import the project and add as a dependency)

### Usage

If your going to use the <br>Activity Builder</b>:
    ```OpenSources.Builder(MainActivity.this).start(options);```
    
    then you need to include the activity name in the manifest
     <activity
            android:name="com.codeogenic.opensources.OpenSources"/>
            

### Base setup:
  Activity Builder
  ```
  OSOptions options = new OSOptions();
  options.setTitle("Open Sources");
  options.setTypefaceBold("fonts/ClanPro-Medium.otf");//OR WHAT ever what you'll like. You can leave blank
  options.setTypefaceRegular("fonts/ClanPro-Book.otf");
  options.setLogoResource(R.mipmap.ic_launcher);//your personal logo
  options.setSummary("The listed following are external libraries we have included in this application."+ 
    "We thank the open source community for all of their contributions.");
  options.setStyle(OSOptions.STYLE_2);
  options.addItem(new ListItem("Android Iconics",getString(R.string.mike_penz),""));
  options.addItem(new ListItem("Butterknife",getString(R.string.fancy_button),""));
  options.addItem(new ListItem("CircleImageView", getString(R.string.MIT_Licence),
  "https://github.com/hdodenhof/CircleImageView"));
  options.addItem(new ListItem("Fancy Button",getString(R.string.fancy_button),"https://github.com/medyo/Fancybuttons"));
  options.addItem(new ListItem("Material Drawer",getString(R.string.mike_penz),""));
  OpenSources.Builder(Settings.this).start(options);
  ```
  
  Fragment Builder
    same as above just use  ``` OpenSources.FragmentBuilder( layoutId , getSupportFragmentManager())
    .start(options);```
    see below
    
  ```
    options.setTypefaceBold("fonts/ClanPro-Medium.otf");
    options.setTypefaceRegular("fonts/ClanPro-Book.otf");
    options.setLogoResource(R.mipmap.ic_launcher);
    options.setSummary("The listed following are external libraries we have"+
    " included in this application. We thank the open source community for all of their contributions.");
    options.setTheme(OSOptions.DARK_THEME);
    OpenSources.FragmentBuilder(R.id.activity_main,MainActivity.this.getSupportFragmentManager())
    .start(options);
  ```
        
### TODO
* make available through jcenter ..someday
* Get fragment working
* Maybe add a style builder or allow for themes to be overridden
* set custom views for adapters
* fix all the bugs lol
        
### Developed By

Clemaurde Gumbs

Connect [@Clem](https://www.linkedin.com/in/clemaurdegumbs25/) on LinkedIn for new releases.

  
