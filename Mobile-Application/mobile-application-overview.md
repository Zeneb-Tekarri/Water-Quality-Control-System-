### Mobile Application Overview

#### General Description
The mobile application for the water quality supervision system is designed to offer a user-friendly interface, facilitating real-time monitoring and analysis of essential water quality parameters. Developed using Android Studio, the application provides remote access to data and functionalities.

1. **User Authentication**: 
    - **Login**: Users must log in using their email and password. New users can sign up via their Google account or email.
    - **Security**: User accounts are securely stored in a Firebase database, and password reset options are available for forgotten passwords.

2. **Real-Time Data Access**:
    - The application displays current sensor data for pH, conductivity, turbidity, chlorine, and TDS.
    - The interface is designed to be clear and intuitive, allowing easy monitoring of these critical parameters.

3. **User Experience**:
    - Provides a convenient platform for supervisors and operators to access and analyze water quality data.
    - Ensures data accuracy with options to refresh sensor readings as needed.

#### Application Functionality

1. **Login Interface**:
    - The login screen verifies user credentials against the Firebase database.
    - Users can navigate to the sign-up interface to create a new account or use the "Forget password" option to reset their password.
    - This interface ensures a secure and straightforward process for accessing user accounts.

2. **Sign-Up Interface**:
    - **Classic Method**: Users manually input personal information (name, email, password, phone number).
    - **Quick Method**: Allows users to sign up quickly using their Google account, simplifying the registration process.
    - Successful registration redirects users to the main application interface.

3. **Data Display Interface**:
    - Two main interfaces display real-time sensor values for pH, chlorine, conductivity, turbidity, and TDS.
    - Users can refresh the data to ensure they are viewing the most up-to-date readings.

#### Security and User Management
- The application prioritizes the security of user data with robust authentication mechanisms and secure data storage in Firebase.
- Password reset functionality is accessible only to registered users with valid email addresses.

This mobile application is a vital tool for the real-time supervision and analysis of water quality, offering a seamless and secure user experience for monitoring essential parameters.
