### LabVIEW User Interface Overview

#### General Description
The LabVIEW user interface for the water quality supervision system is designed to provide a user-friendly and secure platform for accessing real-time sensor data. Additionally, it generates water quality reports based on a Random Forest classification algorithm. 

#### Key Components

1. **Login Interface**:
    - **User Authentication**: Users must enter their username and password to gain access.
    - **Secure Access**: The system verifies the credentials before granting access to the main dashboard.

2. **Main Dashboard**:
    - **Real-Time Data Display**: 
        - Displays real-time sensor readings for pH, chlorine, conductivity, turbidity, and TDS.
        - Continuous updates ensure accurate monitoring.
    - **Water Quality Classification**:
        - Utilizes a Random Forest algorithm to classify the water as potable or non-potable based on sensor data.
        - The classification result is clearly displayed on the interface.

3. **Data History Interface**:
    - **Data Logging**: 
        - Historical data is logged and can be accessed for trend analysis.
        - The interface allows users to view past sensor readings and classifications.
    - **Visualization**:
        - Graphical representations of historical data aid in identifying patterns and anomalies.

#### Security and User Management
- The interface ensures that user data and access are secure.
- Historical data logging and visualization provide a comprehensive overview of water quality over time.

This LabVIEW interface plays a crucial role in managing and analyzing water quality, offering a robust tool for real-time supervision and historical data analysis.
