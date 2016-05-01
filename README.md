# Networks Final Project


### Running the Application 
### SMS Scheduler (tabbed activity)
* Build Android Application 
* Upon Application Start
	* Select the middle button "SMS AUTOMATION"

* Run Node.js server
* Go to http://localhost:8080/showContacts
	* A list of your contacts will be displayed


### SMS Scheduler (tabbed activity)
* Select Date and Time
* Run in bg
* Specify phone number and message with character counter

* Prompts user on SMS send, a way to bypass?
* Time / Date pickers for SMS tool
* Jhipster webapp to connect with the android app
* clean up ui
* Validate / format phone number field

## 
## Contact JSON
* Need to define on both android and web
*

* JSON serializer (Only numbers, strings, arrays [primatives])
** Converts a java object to JSON
** GENSON with Gradle

## Web Application
* phone will ask server (Prompt for an action)
* ALWAYS Easier for phone to initialize communication with the server
* Server initializing to phone = Push notifications are more difficult
* Phone will prompt every _ seconds for a command from server

## Connecting Android to Web
* URL Connection
* POST contact data
* Get the data (message and phone number)


## Weekly Goals
* Send a text via Web App


## SMS Scheduling
* Added permission for SMS sending in manifest
* SMS successfully sends from app
* (Android Service) AlarmManager - triggers events based on the phones current time

* AlarmManager - prioritizes tasks
* Pass time that I want to use
* Calendar
* Change style of date on XML file


## 
* Sent my first text from the web server.
* Building a basic form that allows user to specify recipient information with web server

## 
* Bootstrap form
* Form validation to send properly formatted data



## 04/16/16
* Sending JSON of phone numbers to server
* Displaying the data sent from mobile on website

## 04/22/2016
* Using netcat to debug
* nc -l -k 8080
* nc localhost 8080
* GET /show
* or test with curl
curl -G -v http://localhost:8080/show
* store json object in browser session (when does session time-out)
* Use redis
* Use database (store JSON in mongo?)

* use android monitor to see if anything is happening when those intents are passed.

* SUGGESTION: create a separate android studio project to experiment with the alarm manager
* do a toast / log in the callback

## 04/24/2016
* Fixed AlarmManager by adding the full path in the Android Manifest
* Android Monitor wasn't debugging application anymore, solution found:
	* Gradle Projects -> Mobile -> Mobile (root) -> Tasks -> build -> build (double click build)
* added "lintOptions {
          abortOnError false
      }" To build.gradle under "android {}"
1. To send data BETWEEN fragments, must first pass to the Activity that displays fragments
2. Call a method in main activity that passes data to third fragment(review fragment). Then advances to second fragment
3. Second fragment sends date / time to main activity like the first fragment, where the main activity sends date/time to the third fragment.
4. Then when the third fragment loads, it will receive the phone number, message, and date / time of scheduled text.
5. Pressing SEND on the third fragment then calls a method in the main activity that schedules via the method in the first fragment.

## 04/29/2016
* Adding sendSMS method that requires 2 parameters (String message, String number)
* Used JADE (pug) template to dynamically display list of contacts

# Future Goals
* Pull Contact photo from contacts as well
* Choose a contact to send to from populated contact list

