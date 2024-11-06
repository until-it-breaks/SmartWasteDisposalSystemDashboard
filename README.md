# SWDS_Dashboard
A dashboard that displays sensors readings and an activity log coming from an Arduino.
To be used in conjunction with an Arduino Uno R3 loaded with the SmartWasteDisposalSystem program.

When launched it will attempt to detect the serial port on which Arduino is connected, assuming that a message containing "ArduinoUno" is sent from the MCU during the setup.
There is also an option to provide the port via args with either {./gradlew run --args="portname"} or {java -jar ./dashboard-all.jar "portname"}.
