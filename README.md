# CanchaLlenaGol
CanchaLlenaGol app for LaNacion Hackaton Challenge

This application was entirely designed for the Hackaton Smartphone Challenge by LaNacion in only 24 hours of competition.

The purpose was to show goals videos for a provided soccer match in a mobile app. 

Our infrastructure
-----------------
A backend made in GoogleAppEngine to support push-notifications and provide content to the mobile application  
An Android native mobile application  
A Jenkins on a remote linux server with Auto deploy to GoogleAppEngine on master commits

Android
-----------------
The app was fully developed using Android SDK and Android Studio with the best libraries in the jCenter repository.

* Google Play Services - to use Google Cloud Messaging (for Push Notifications) and other services.
* Google Support Library v4 & v7 - for compatibility to old Android versions.
* OkHttp - for making http requests.
* Google GSON - to work with JSON (is better work with Jackson, but it's faster to implement GSON)
* Universal Image Loader - to loading & cache images.

We delivered the app in 2 environments, one for development and another for production (minified and different signing configuration)
Also the app every startup verifies if it registered in our backend to receive the push notificacions when a goal or every event was raised.

You can get the apk using the following steps: 
* Download the source code
* Execute "gradlew assembleDebug"
* Locate the apk in the bin directory
* Distribute the apk to different devices and install it.

Enjoy watching soccer!

AppEngine
-----------------
The backend was fully designed using Google infrastructure and best libraries for AppEngine.  
Basically we work with:
* Objectify 5+ - to support persistance
* Google Guava Libraries - To handle common java tasks  
* Google Guice - to support dependency injection
* Google Api Endpoints - to publish APIs to the frontend
* AppEngine Task Queues - to support heavy task like parsing xml, and detect Amazon Hidden URLS
* Java Servlets - to publish APIs to mobile application
* Google GSON - to work with JSON 
* Dom4J - to parse the XML with the match data
* Twitter Bootstrap - To design quickly the frontend
* AngularJS - to add rich content and interact with backend 

AppEngine Backend
------------------------
The app is hosted by Google in: https://marroccl-909.appspot.com/  
  
Provide the game data, with correct video url for each event to the Android cellphone, was our main focus so we    
built a backend designed to be capable of the following:
* Configure the video of the game url, also the start time and end time of the soccer 1st and 2nd time without
the half time media stop, to show match events.
* Parse the xml from a custom URL (Could be configured). Detect main actions, and actors from a current event
in the unparsed xml, like players, plays, time, teams involved in the play and so on. Configure the url 
to support a location change
* We only get the first Amazon video URL, and Amazon is not able to show a discovery of all the others video urls,
so we created a task using the TaskQueues, to get in less than a minute all the match urls using heuristyc alghoritms,
hitting all possible urls in each second until we found the video for each minute. 
* Publish a fully usable frontend to be able to launch this task manually, and also interact with cellphone manually
for demo purposes. 
* Support more than 1.000.000 push-notification to devices at the same time, using paginated TaskQueues, keeping in
mind that Google GCM supports only 1000 pushes in a row. 


AppEngine Mobile APIs
-----------------
  
Note: GET & POST are supported for easy manual testing  
  
### Device Registration  
Used to register devices in our backend to push notifications
* https://marroccl-909.appspot.com/devices/register
* deviceId - String parameter returned by the GCM to the android device that we will use to post notifications

### Device Unregistration  
Used to unregister devices in our backend to avoid sending push notifications
* https://marroccl-909.appspot.com/devices/unregister
* deviceId - String parameter returned by the GCM to the android device that we will remove of our push notifications

### Game List
Used to get a list of games (Actually one is the real used in the challenge, 
the other ones will be dummys to complete the screen)
* https://marroccl-909.appspot.com/game/list

### Game Get
Get game by minute and second. Do not return the history of the events in the match before that time. 
Shows the score in that time.
* https://marroccl-909.appspot.com/game/get
* gameId - String id of the game
* minute - Integer minutes of the game to show an event
* second - Integer seconds of the game to show an event

### Game Get Full 
Get game by minute and second. Return the complete history of the events in the match. Match could be a running one, 
so only events before current match time will be returned to simulate progress.
Shows the current score.
* https://marroccl-909.appspot.com/game/getFull
* gameId - String id of the game

Artwork
-----------------
There is an extra folder called Artwork, that contains a psd used to design the application,
and also the application logo
