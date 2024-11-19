# Smart Waste Disposal System Dashboard
A dashboard that displays sensor readings and activity logs coming from a primary system.
To be used in conjunction with an Arduino Uno R3 flashed with the SmartWasteDisposalSystem program.

This program is supposed to be launched via terminal using **java -jar ./dashboard-all.jar.**

Upon execution with no arguments the application will report all active serial ports.\
On Windows, the user can identify the correct port by using the Device Manager and searching for the COM port used by Arduino.\
Knowing the correct port the user can then run the application again using **java -jar ./dashboard-all.jar "portname"**.
