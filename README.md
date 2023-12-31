# Hydration Tracker Android App
---

<u>Table of contents</u>
=======

<!--ts-->
  - [Objectives](#objectives)
  - [Development Tools](#devtools)
  - [User Interface](#ui)
<!--te-->

---



## Objectives<a id="objectives"></a>

This mobile application addresses the challenge of inadequate water intake by motivating users to drink enough water and monitor their daily consumption. The app calculates recommended water goals based on the Total Daily Energy Expenditure (TDEE) formula, allowing users to set personalized targets or use the app's recommendation. Timed notifications remind users to drink and log their water intake, and they can track their progress toward daily goals. The app also offers biometric data collection, notification customization, and visual progress tracking to support healthier hydration habits while respecting user privacy.


<!-- ## Analysis <a id="analysis"></a>

These are the key functional requirements for Hydration  Tracker App

| Req ID | Description | Primary Stakeholders | Category | Priority | Success Criteria |
|----------|----------|----------|----------|----------|----------|
| Row 1, Col 1 | Row 1, Col 2 | Row 1, Col 3 | Row 1, Col 4 | Row 1, Col 5 | Row 1, Col 6 |
| Row 2, Col 1 | Row 2, Col 2 | Row 2, Col 3 | Row 2, Col 4 | Row 2, Col 5 | Row 2, Col 6 |
| Row 3, Col 1 | Row 3, Col 2 | Row 3, Col 3 | Row 3, Col 4 | Row 3, Col 5 | Row 3, Col 6 |
| Row 4, Col 1 | Row 4, Col 2 | Row 4, Col 3 | Row 4, Col 4 | Row 4, Col 5 | Row 4, Col 6 |
| Row 5, Col 1 | Row 5, Col 2 | Row 5, Col 3 | Row 5, Col 4 | Row 5, Col 5 | Row 5, Col 6 |
| Row 6, Col 1 | Row 6, Col 2 | Row 6, Col 3 | Row 6, Col 4 | Row 6, Col 5 | Row 6, Col 6 |
| Row 7, Col 1 | Row 7, Col 2 | Row 7, Col 3 | Row 7, Col 4 | Row 7, Col 5 | Row 7, Col 6 |
| Row 8, Col 1 | Row 8, Col 2 | Row 8, Col 3 | Row 8, Col 4 | Row 8, Col 5 | Row 8, Col 6 |
| Row 9, Col 1 | Row 9, Col 2 | Row 9, Col 3 | Row 9, Col 4 | Row 9, Col 5 | Row 9, Col 6 |
| Row 10, Col 1 | Row 10, Col 2 | Row 10, Col 3 | Row 10, Col 4 | Row 10, Col 5 | Row 10, Col 6 |
| Row 11, Col 1 | Row 11, Col 2 | Row 11, Col 3 | Row 11, Col 4 | Row 11, Col 5 | Row 11, Col 6 |
| Row 12, Col 1 | Row 12, Col 2 | Row 12, Col 3 | Row 12, Col 4 | Row 12, Col 5 | Row 12, Col 6 | -->







## Development Tools <a id="devtools"></a>
Below, you'll find a list of tools and components used to enhance the application's user experience and functionality.
- __Java__ as the programming language of this project.
- __MongoDB__ as database usage and requirement for the application. We gathered users’ personalized information and stored it in __JSON__ format.
- Used Intent for connecting through the application and transferring from one activity to another and redirecting users between various sections of the app.
- Implemented save state mechanism for the obtained information while using the application.
- Implemented alarm manager and notification builder for notification settings. 
- Formatted the application by using Scroll layout and relative layout.
- Various widgets to display data.



## User Interface <a id="ui"></a>

The primary focus of the User Interface design is centered on simplicity and ease of use, ensuring an intuitive experience for application users. The UI has been precisely designed by integrating multiple sample pages and providing clear guidance on utilizing various widgets. Upon launching the application, users are greeted with a registration page for their initial setup, where they are prompted to provide the necessary information for account creation. For returning users, a straightforward 'Sign In' option is available to access their existing accounts within the application.

<div align="center">
  <img src="./Images/1.png" alt="Image 1" width="300" style="margin: 0 10px;">&nbsp;&nbsp;&nbsp;
  <img src="./Images/2.png" alt="Image 2" width="318" style="margin: 0 10px;">
</div>

<br>

The next page in the application is the home page of it. As we can see here it contains different information and widgets.
The first one is the Goal of intake water for the user. The user can also modify this goal amount by using the Set Goal option.
The next part is showing the total progression of water consumption for the user. In the Add Water section, the user can input the amount of consumed water, so the total progress will be updated based on it in the Home Page.
The last three options are profile, reminders, and logout for adjusting user’s information, modify notifications, and log out from account respectively.

<div align="center">
  <img src="./Images/3.png" alt="Image 3" width=240 style="display:inline-block; margin:0 10px;">
</div>

<br>

In the Set Goal page, users can set a custom amount of intake water goal or use the Calculate Goal to do the calculation based on Total Daily Energy Expenditure formula.
The goal amount will be updated as well in the home page.

<div align="center">
  <img src="./Images/4.png" alt="Image 4" width=240 style="display:inline-block; margin:0 10px;">
</div>

<br>

In the Reminder page, we can set wake up and sleep time for the application, so mute the notification during that time. We also have the option to set preferred time intervals to get notifications throughout the day.

<div align="center">
  <img src="./Images/5.png" alt="Image 5" width="200" style="margin: 0 10px;">&nbsp;&nbsp;&nbsp;
  <img src="./Images/6.png" alt="Image 6" width="215" style="margin: 0 10px;">&nbsp;&nbsp;&nbsp;
  <img src="./Images/7.png" alt="Image 7" width="260" style="margin: 0 10px;">
</div>

<br>

The last section is the 'Profile,' where users can update their personal details. All user information is stored and retrieved using MongoDB for reuse.

<div align="center">
  <img src="./Images/8.png" alt="Image 8" width="200" style="margin: 0 10px;">&nbsp;&nbsp;&nbsp;
  <img src="./Images/9.png" alt="Image 9" width="163" style="margin: 0 10px;">
</div>
