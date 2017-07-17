###Project Introduction

The Flybits Android Vanilla Application is a project template that allows application developers to quickly build a Flybits-enabled Android application with all the basic components such as push notifications, context uploading, and retrieval of Moment data built into the code. This template allows application developers to simply change the look and feel of their application without having to add any of the Flybits functionality themselves.

###Project Setup

Once you clone this application and load it into your Android Studio there are a few things that will stop you from compiling the code into a valid APK file. The first thing you will notice the following error: `File google-services.json is missing. The Google Services Plugin cannot function without it.`. If you would like to compile this code into a valid application you will need register your application through the [Firebase Console](https://console.firebase.google.com/) which will allow you to receive Push Notifications. Once you have registered your application through the Firebase Console you will be able to download the google-services.json and add it to your project.

The second issue that will appear is the `Error:(75, 28) No resource found that matches the given name (at 'value' with value '@string/flybitsProjectID').` issue this is because by default there is no flybits Project ID set to this application. Therefore, you will need to log in to your [Flybits Developer Portal](https://devportal.flybits.com) account and create a project. Once that project has been created you will be able to copy your Flybits Project Id and add it to your strings.xml file under the `flybitsProjectID` attribute.

Once you have set up your Flybits Project ID, you should be ready to start coding!

###Setting Up Push Notifications

Before you have your fully Flybits-enabled application you need to register your Firebase project's Cloud Messaging server token with the Flybits Developer Portal. You can retrieve your' Firebase project's Cloud Messaging key from the [Firebase Console](https://console.firebase.google.com/) as seen in the screenshot below under `YOUR_SERVER_KEY`.

![firebase1](https://github.com/flybits/vanilla-android/screenshots/firebase1.png)

Finally, you can paste `YOUR_SERVER_KEY` within the Flybits Push Setting console as seen in the screenshot below.

![firebase1](https://github.com/flybits/vanilla-android/screenshots/flybits1.png)

Now your set!
