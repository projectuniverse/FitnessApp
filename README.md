# Fitness App

## An app that provides support for multiple workout types and allows users to track indoor and outdoor activities 
This project was my first experience developing a larger-scale Android app. It encourages users to stay healthy by allowing them to track multiple workout types, including both indoor and outdoor workouts. Supported outdoor workout types include running, hiking, and biking. For indoor workouts, the app supports squats, push-ups, and sit-ups.

When a user starts an outdoor workout, the app displays the traveled distance, elapsed workout time, the time needed for the last kilometer, and the average time per kilometer. Additionally, the app uses Google Maps to draw a black path that follows the traveled route, helping users track their movements during and after their workout. For greater user-friendliness, the app even tracks outdoor workouts in the background. Thus, users are not required to explicitly start an outdoor workout. This is done using the device's activity recognition.

When a user starts an indoor workout, the app displays the repetitions, elapsed workout time, and burned calories. The user needs to keep their device close to their body (i.e., attached to their arm for sit-ups and in their pocket for squats and push-ups), because repetitions are automatically calculated using the user's movement. This is done using the device's gyroscope data.

Users are also able to track their progress and view their workout results. The app's welcome screen lists all recorded workouts, and workout results can be viewed either directly after the workout or by selecting a previously recorded workout. For outdoor workouts, the app displays the traveled distance, elapsed workout time, pace, steps, and burned calories. Additionally, users can view the elevation graph for their traveled route, and they can also see a black path on Google Maps that shows their route. For indoor workouts, users can view their repetitions, elapsed time, and burned calories.

Burned calories are calculated using the data users must enter when first opening the app. The app requires age, height, and weight. In the settings, users can change this data at any time. Furthermore, users can choose to sync all their workout data with Google Health Connect.

## Screenshots
<picture>
  <img alt="The home screen." src="https://github.com/user-attachments/assets/d686779f-b787-4762-8823-67df3d8544d6" width="250">
</picture>
&nbsp;
<picture>
  <img alt="The screen to start a workout for running." src="https://github.com/user-attachments/assets/9dd0bc17-4556-4470-885d-ca77e4c3f6ac" width="250">
</picture>
&nbsp;
<picture>
  <img alt="The screen which lists the outdoor workout's stats." src="https://github.com/user-attachments/assets/fa83da07-5589-43d4-89e0-c252156e1019" width="250">
</picture>

<br>

<picture>
  <img alt="The screen to start a workout for squats." src="https://github.com/user-attachments/assets/4a7eeebd-0f46-4835-8a87-0b24d87bc329" width="250">
</picture>
&nbsp;
<picture>
  <img alt="The screen which lists the indoor workout's stats." src="https://github.com/user-attachments/assets/16e321e5-b2d0-45fa-9b89-c4de9f2c70d6" width="250">
</picture>
&nbsp;
<picture>
  <img alt="The settings screen." src="https://github.com/user-attachments/assets/28ed670c-7524-4fc1-9045-1f4be5712706" width="250">
</picture>

## How to install and run this app on your Android device
1. Install Android Studio and `clone` this project
2. Generate a Google Maps API key and an OpenWeather API key for current weather and forecast
3. In the project's root directory that also contains `local.defaults.properties`, create a file called `secrets.properties`
4. Paste the following lines of code into `secrets.properties` and replace `YOUR_API_KEY` with your own respective API keys:
    ```
   OPEN_WEATHER_API_KEY=YOUR_API_KEY
   MAPS_API_KEY=YOUR_API_KEY
   ```
6. Build the APK
7. Install the APK on your Android device
