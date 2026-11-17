# Assignment 4 – CO4: Event-Driven GUI Design
**Name:** D Guru Prasanth Babu | **Register No.:** 192324099 | **Course:** CSA0902 – Programming in Java

## Task 1 – GUI Design Review
### 1.1 Deficiencies
1. UI and payment/database logic are mixed, making testing and maintenance difficult.
2. `==` compares String references, not contents; use `.equals()` or dedicated listeners.
3. Empty, non-numeric, zero and negative amounts are not validated.
4. JDBC runs on the event-dispatch thread, freezing the window.
5. `Thread.sleep(3000)` blocks GUI interaction.
6. `System.out.println` is not useful user feedback; show a label/dialog.
7. Receipt button falls into the `else` branch and clears fields.
8. SQL concatenation risks SQL injection; use PreparedStatement.
9. Connection/statement are not closed.
10. Checked exceptions and window closing are not handled safely.

### 1.2 Repair
`FeeWindow` only collects input and displays status. `FeeService` validates and applies payment rules. `FeeRepository` performs JDBC operations with PreparedStatement and try-with-resources. See Task01 source.

### 1.3 Design defence (under 100 words)
The window handles presentation only, while FeeService contains validation and payment rules and FeeRepository contains SQL. Each layer can be tested independently. Screen changes do not require rewriting database code, and database changes do not require rewriting button handlers. This separation reduces duplication and makes debugging and maintenance easier.

**Alternative/complexity:** A shared action-command if/else chain takes up to O(k) comparisons for k buttons and O(1) listener space. Separate listener objects allow direct dispatch in O(1) after source registration, but use O(k) listener objects. The space/time difference is small for normal GUIs; readability is the stronger reason.

## Task 2 – Hospital Appointment Desk
### 2.1 Event table
| Source | Event | Listener | Action |
|---|---|---|---|
| Department combo | ItemEvent | ItemListener | Refresh doctor list |
| Book button | ActionEvent | ActionListener | Validate and book |
| Window close | WindowEvent | WindowAdapter | Confirm if booking is active |
| Slot/doctor combo | ItemEvent | ItemListener | Record current selection |

### 2.2 Component hierarchy
```text
JFrame AppointmentDesk (BorderLayout)
├── JPanel form (GridLayout)
│   ├── JLabel + JTextField patient ID
│   ├── JLabel + JComboBox department
│   ├── JLabel + JComboBox doctor
│   └── JLabel + JComboBox time slot
└── JPanel bottom (FlowLayout): JButton Book + JLabel status
```
### 2.3 Pseudocode
```text
ALGORITHM RefreshDoctors
INPUT selectedDepartment
BEGIN
  Clear doctorCombo
  doctorList <- doctorMap[selectedDepartment]
  FOR EACH doctor IN doctorList
    Add doctor to doctorCombo
  END FOR
END
```
```text
ALGORITHM BookAppointment
BEGIN
  IF patient ID is empty OR doctor/slot is not selected THEN
    Display validation error
    RETURN
  END IF
  Set bookingInProgress <- true
  Save appointment
  Display confirmation or error
  Set bookingInProgress <- false
END
```
### 2.4 Event trace
Construct frame/components → register listeners → setVisible(true) → department selection generates ItemEvent → ItemListener refreshes doctors → Book generates ActionEvent → booking handler runs → close generates WindowEvent → windowClosing asks confirmation → dispose if approved. Applets instead have `init()`, `start()`, `paint()`, `stop()`, `destroy()` lifecycle methods; a JFrame is event-driven and does not use that lifecycle.

**Alternative/complexity:** Fetching on every department change incurs DB/network latency and O(m) work to load m doctors. A preloaded `Map<String,List<Doctor>>` costs O(d+m) memory for d departments and m doctors; lookup is O(1) average plus O(m_department) to populate the combo. Repeated DB fetching reduces cached memory but repeats I/O.

## Task 3 – Campus Library Search Layout
### 3.1 Layout choices
Main frame: BorderLayout. Search row: FlowLayout(LEFT), keeping preferred control sizes. Results: JScrollPane in CENTER, receiving extra space. Bottom controls: FlowLayout(CENTER); status at SOUTH. GridLayout is suitable for equal-sized forms; CardLayout is for switching screens, not this single screen.
### 3.2 Nested layout
```text
JFrame LibrarySearch (BorderLayout)
├── NORTH JPanel (FlowLayout): JTextField, JComboBox, Search
├── CENTER JScrollPane -> JList results
└── SOUTH JPanel (BorderLayout)
    ├── CENTER JPanel (FlowLayout): Prev, Next, Reserve
    └── SOUTH JLabel status
```
### 3.3–3.4
Run Task03 and capture small, medium and large window screenshots. The results area should grow/shrink while search controls and buttons retain preferred sizes.
**Alternative/complexity:** GridBagLayout offers flexible constraints but needs more setup. Absolute positioning (`null` layout and `setBounds`) can use fewer containers but does not adapt well to resizing. Layout computation is generally O(c) in component count c; GridBagLayout has a larger constant cost. Nested design uses O(p) space for p panels.

## Task 4 – Event-Handling Forensics
### 4.1 Defect
`mouseclicked` has incorrect capitalization; Java requires `mouseClicked`. The handler is therefore not overridden. The original code also never updates the label.
### 4.2 Failing interaction
Click to draw, minimise the window, then restore it. A dot drawn only with `getGraphics()` may disappear because repainting does not know about it.
### 4.3 Repair
Store each click as a Point, update the label, call repaint(), and redraw stored points inside paint(). See Task04.
### 4.4 Prevention
`@Override` makes the compiler verify the override. Misspelling `mouseClicked` while using `@Override` produces a compile-time error.
**Alternative/complexity:** Repainting n stored points costs O(n) time and O(n) point memory. Drawing into an off-screen BufferedImage once means repaint copies the image (O(width × height) pixel work) and uses O(width × height) pixel memory.

## Task 5 – Event Handling Styles
### 5.1 Implementations
Task05 includes frame implements ActionListener, named inner class, anonymous inner class and lambda versions. Each uses WindowAdapter for closing. `Converter.java` completes both TODOs: one shared lambda for button and Enter, parse/format to two decimals, and show invalid amount in a dialog.
### 5.2 Change requirement
The amount field's action listener also invokes conversion on Enter. Result is shown in a dialog. The same handler can be registered with both controls.
### 5.3 Comparison
| Style | Readability | Field access | Reuse |
|---|---|---|---|
| Frame listener | Easy for tiny GUI; frame gets crowded | Direct | Low |
| Named inner | Clear named unit | Direct | Good |
| Anonymous inner | Local but verbose | Direct | Limited |
| Lambda | Concise | Direct | Good when shared |
For a large app, prefer a named handler or Swing Action for reuse across buttons, menus and shortcuts.
**Alternative/complexity:** `this::convert` is a method reference. A shared Swing Action can be attached to button/menu/keyboard mapping. One shared action object uses O(1) handler space rather than O(t) independent objects for t triggers; adding a trigger is one registration.

## Task 6 – Campus Event Registration
### Stage 1
Users: students and committee staff. Screens: registration form and registrations table. Data: student ID/name, event and timestamp. Rules: required fields, one registration per student/event, clear errors, responsive loading.
Testable requirements: reject blanks; insert valid registration; reject duplicate; filter table; load bounded pages; perform JDBC off the EDT.
### Stage 2 – Screen flow
```text
JFrame + JMenuBar (File: Refresh/Exit; Help: About)
├── Register tab: Student ID, Name, Event, Register
└── View tab: Event filter, JTable, Previous/Next
Flow: open -> register/view -> validate -> SwingWorker JDBC -> show success/error -> refresh table.
```
### Stage 3
Service layer validates. Database UNIQUE(student_id,event_name) is the final duplicate guard. JDBC runs in SwingWorker; table uses LIMIT/OFFSET pagination.
### Stage 4 – Layers and pseudocode
```text
EventRegistrationApp (Swing UI)
          |
RegistrationService (validation/rules)
          |
RegistrationRepository (JDBC)
          |
MySQL registrations table
```
Register: read form; reject blanks; disable button; start SwingWorker; call service/repository; show success or duplicate/database error; re-enable button; refresh page.
Filter: read event; set page=0; SwingWorker calls repository.findPage(event, pageSize, offset); replace JTable model in done().
### Stage 5
See Task06 Java sources and `schema.sql`. Create the database, add Connector/J and set DB environment variables. Demonstrate normal, invalid and duplicate registration plus event filter.
**Alternative/complexity (100,000 rows):** Loading all rows transfers and retains O(N) data and can use substantial heap and freeze the EDT if done synchronously. Paging with page size p transfers/stores O(p) rows per page and SwingWorker keeps the EDT responsive. SQL cost depends on indexes and OFFSET depth; keyset pagination can be better for very deep pages.
