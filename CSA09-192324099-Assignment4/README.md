# Programming in Java – Assignment 4 (CO4)
**Name:** D Guru Prasanth Babu  
**Register Number:** 192324099  
**Course:** CSA0902 – Programming in Java

## Requirements
- JDK 11+
- Tasks 1–5 use standard AWT/Swing.
- Task 6 needs MySQL Server and MySQL Connector/J 8.x.

## Folders
Task01 Fee payment design; Task02 hospital appointment desk; Task03 library layout; Task04 drawing pad; Task05 four listener styles; Task06 layered Swing/JDBC event registration.

## Compile and run
Open PowerShell in this folder:
```powershell
cd Task01; javac *.java; java FeeWindow
cd ..\Task02; javac *.java; java AppointmentDesk
cd ..\Task03; javac *.java; java LibrarySearch
cd ..\Task04; javac DrawPad.java; java DrawPad
cd ..\Task05; javac *.java; java Converter
```
Task06: run `schema.sql` in MySQL, add the Connector/J jar to classpath, set `DB_URL`, `DB_USER`, `DB_PASSWORD`, then:
```powershell
cd ..\Task06
javac -cp ".;mysql-connector-j-8.4.0.jar" *.java
java -cp ".;mysql-connector-j-8.4.0.jar" EventRegistrationApp
```
Default URL: `jdbc:mysql://localhost:3306/campus_events`.

## Results
1. FeeWindow separates presentation, validation/service and JDBC repository; prepared statements are used.
2. AppointmentDesk refreshes doctors when the department changes and validates booking details.
3. LibrarySearch uses nested BorderLayout/FlowLayout containers; the centre results region expands.
4. DrawPad stores click points and redraws them in paint(), preserving dots after repaint.
5. Four event-handling styles are provided; the lambda version shares one listener for button and Enter.
6. EventRegistrationApp separates UI/service/repository, uses SwingWorker and a UNIQUE constraint to prevent duplicates; table data is paged.

## Evidence
Capture your own screenshots after running each program. Add them to each task's `screenshots` folder. Include valid/invalid cases and resizing/minimise-restore evidence where required. Do not claim screenshots or measurements that you did not personally capture.
