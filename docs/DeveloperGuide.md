---
layout: page
title: Developer Guide
---
* Table of Contents
{:toc}

---

## **Acknowledgements**

- Adapted from [AddressBook-Level3 Developer guide](https://se-education.org/addressbook-level3/DeveloperGuide.html)

---

## **Setting Up, Getting Started**

Refer to the guide [_Setting up and getting started_](SettingUp.md).

---

## **Design**

<div markdown="span" class="alert alert-primary">

:bulb: **Tip:** The `.puml` files used to create diagrams in this document can be found in the [diagrams](https://github.com/AY2122S1-CS2103T-T13-1/tp/tree/master/docs/diagrams) folder. Refer to the [_PlantUML Tutorial_ at se-edu/guides](https://se-education.org/guides/tutorials/plantUml.html) to learn how to create and edit diagrams.

</div>

### Architecture

<img src="images/ArchitectureDiagram.png" width="280" />

The **_Architecture Diagram_** given above explains the high-level design of the App.

Given below is a quick overview of main components and how they interact with each other.

**Main components of the architecture**

**`Main`** has two classes called [`Main`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/Main.java) and [`MainApp`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/MainApp.java). It is responsible for,

- At app launch: Initializes the components in the correct sequence, and connects them up with each other.
- At shut down: Shuts down the components and invokes cleanup methods where necessary.

[**`Commons`**](#common-classes) represents a collection of classes used by multiple other components.

The rest of the App consists of four components.

- [**`UI`**](#ui-component): The UI of the App.
- [**`Logic`**](#logic-component): The command executor.
- [**`Model`**](#model-component): Holds the data of the App in memory.
- [**`Storage`**](#storage-component): Reads data from, and writes data to, the hard disk.

**How the architecture components interact with each other**

The _Sequence Diagram_ below shows how the components interact with each other for the scenario where the user issues the command `delete mod 1`.

<img src="images/ArchitectureSequenceDiagram.png" width="574" />

Each of the four main components (also shown in the diagram above),

- defines its _API_ in an `interface` with the same name as the Component.
- implements its functionality using a concrete `{Component Name}Manager` class (which follows the corresponding API `interface` mentioned in the previous point.

For example, the `Logic` component defines its API in the `Logic.java` interface and implements its functionality using the `LogicManager.java` class which follows the `Logic` interface. Other components interact with a given component through its interface rather than the concrete class (reason: to prevent outside component's being coupled to the implementation of a component), as illustrated in the (partial) class diagram below.

<img src="images/ComponentManagers.png" width="300" />

The sections below give more details of each component.

### UI Component

The **API** of this component is specified in [`Ui.java`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/ui/Ui.java)

![Structure of the UI Component](images/UiClassDiagram.png)

The UI consists of a `MainWindow` that is made up of parts e.g.`CommandBox`, `ResultDisplay`, `ModuleListPanel`, `StatusBarFooter` etc. All these, including the `MainWindow`, inherit from the abstract `UiPart` class which captures the commonalities between classes that represent parts of the visible GUI.

The `UI` component uses the JavaFx UI framework. The layout of these UI parts are defined in matching `.fxml` files that are in the `src/main/resources/view` folder. For example, the layout of the [`MainWindow`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/ui/MainWindow.java) is specified in [`MainWindow.fxml`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/resources/view/MainWindow.fxml)

The `UI` component,

- executes user commands using the `Logic` component.
- listens for changes to `Model` data so that the UI can be updated with the modified data.
- keeps a reference to the `Logic` component, because the `UI` relies on the `Logic` to execute commands.
- depends on some classes in the `Model` component, as it displays `Module` objects residing in the `Model`.
- keeps a reference to the current screen state as a `GuiState` in order to restrict some commands to certain screens (more on this under `Restricting Commands based on GUI state` in the `Implementation` section below).
- changes the screen based on the return value of CommandResult.

#### Design Considerations

**Aspect: How to change UI screen based on the command**

- **Alternative 1 (current choice)**: Create card classes for each type of screen and change rendered card based on return value of CommandResult.
    - Pros: Readability is enhanced, easily extendable.
    - Cons: Coupling of UI component is reduced; logic needs to be added to CommandResult to change the current screen.
- **Alternative 2**: Have a single rendered ModuleCard that changes text based on the type of the object in the ObservableList.
    - Pros: Easier to implement.
    - Cons: Harder to extend; can lead to a monolithic ModuleCard that handles all views.

### Logic Component

**API** : [`Logic.java`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/logic/Logic.java)

Here's a (partial) class diagram of the `Logic` component:

<img src="images/LogicClassDiagram.png" width="550"/>

How the `Logic` component works:

1. When `Logic` is called upon to execute a command, it uses the `ModBookParser` class to parse the user command together with the current state of the GUI (`GuiState`).
2. This results in a `Command` object (more precisely, an object of one of its subclasses e.g., `AddModCommand`) which is executed by the `LogicManager`.
3. The command can communicate with the `Model` when it is executed (e.g. to add a module).
4. The result of the command execution is encapsulated as a `CommandResult` object which is returned back from `Logic`.

The Sequence Diagram below illustrates the interactions within the `Logic` component for the `execute("delete mod 1")` API call.

![Interactions Inside the Logic Component for the `delete mod 1` Command](images/DeleteSequenceDiagram.png)

<div markdown="span" class="alert alert-info">:information_source: **Note:** The lifeline for `DeleteCommandParser` should end at the destroy marker (X) but due to a limitation of PlantUML, the lifeline reaches the end of diagram.
</div>

Here are the other classes in `Logic` (omitted from the class diagram above) that are used for parsing a user command:

<img src="images/ParserClasses.png" width="600"/>

How the parsing works:

- When called upon to parse a user command, the `ModBookParser` class creates a `XYZCommandParser` (`XYZ` is a placeholder for the specific command name e.g., `DeleteCommandParser`) which uses the other classes shown above to parse the user command and create a `XYZCommand` object (e.g., `DetailCommand`) which the `ModBookParser` returns back as a `Command` object.
- The name of the `Command` Object may also include the type of object the command is acting on. For example, `delete mod` command returns a `deleteModCommand` object, `delete lesson` command returns a `deleteLessonCommand` object and `delete exam` command returns a `deleteExamCommand` object.
- All `XYZCommandParser` classes (e.g., `AddCommandParser`, `DeleteCommandParser`, ...) inherit from the `Parser` interface so that they can be treated similarly where possible e.g, during testing.

### Model Component

**API** : [`Model.java`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/model/Model.java)

<img src="images/ModelClassDiagram.png" width="450" />

The `Model` component,

- stores the ModBook data i.e., all `Module` objects (which are contained in a `UniqueModuleList` object).
- stores the currently 'selected' `Module` objects (e.g., results of a search query) as a separate _filtered_ list which is exposed to outsiders as an unmodifiable `ObservableList<Module>` that can be 'observed' e.g. the UI can be bound to this list so that the UI automatically updates when the data in the list change.
- stores a `UserPref` object that represents the user’s preferences. This is exposed to the outside as a `ReadOnlyUserPref` objects.
- does not depend on any of the other three components (as the `Model` represents data entities of the domain, they should make sense on their own without depending on other components)

### Storage Component

**API** : [`Storage.java`](https://github.com/AY2122S1-CS2103T-T13-1/tp/blob/master/src/main/java/seedu/address/storage/Storage.java)

![StorageClassDiagram](images/StorageClassDiagram.png)

The `Storage` component,

- can save both module data and user preference data in `JSON` format, and read them back into corresponding objects.
- inherits from both `ModBookStorage` and `UserPrefStorage`, which means it can be treated as either one (if only the functionality of only one is needed).
- depends on some classes in the `Model` component (because the `Storage` component's job is to save/retrieve objects that belong to the `Model`)

The sequence diagram of how this works for an `add lesson` command is found below. The objects in `Model` and `UI`, as well as time-related objects, are omitted for simplicity.

![StorageSequenceDiagram](images/StorageSequenceDiagram.png)

When storing the `ModBook`, the respective `JsonAdaptedObject` classes will create adaptations of objects in the `Model`, allowing `JsonUtil` to store the objects and save them to the `filePath` specified in `UserPrefs`.

#### Design Considerations

**Aspect: How to save components of Modules (Lesson / Exam / Timeslot / ModBookTime / ModBookDate):**

- **Alternative 1 (current choice)**: Create respective `JsonAdaptedObject` classes for each type of object.
    - Pros: Greatly increases the readability of the JSON storage file
    - Cons: Many objects must be created when a Lesson or Exam is being stored.
- **Alternative 2**: Encode `Lesson`, `Exam` and other components into `String` objects to be stored as properties of a `JsonAdaptedModule`.
    - Pros: Easier to implement the writing of JSON files.
    - Cons: Difficult to parse JSON output - have to figure out how to decode `String` objects to `Lesson`, `Exam` and other objects in the `Model`.

### Common Classes

Classes used by multiple components are in the `seedu.address.commons` package.

---

## **Implementation**

This section describes some noteworthy details on how certain features are implemented.

### Restricting Commands based on GUI state

#### Rationale

Some commands, e.g. `delete lesson` and `edit exam` should not be used in the view where the `Modules` are listed.
This is because the `delete` and `edit` commands are indexed based, and in the `Modules` view, there are multiple `Lessons` or `Exams` displayed with the same index.
Hence, there is a need to restrict these commands to the Module Details view, so that there is no ambiguity about which `Lesson` or `Exam` to delete.

Thus, before commands are executed, the GUI state of the application needs to be checked to see if it is valid.

#### Implementation

To implement this feature, the GUI needs to keep track of which state it is in, so a field in `MainWindow` was created to store the `GuiState`.
In addition, the `Logic#execute()` method has an additional `GuiState` parameter, which will be the current `GuiState` of the `MainWindow`, and the `Parser` interface was modified so that `parse` also takes in a `GuiState` parameter.
After the command is executed, the `GuiState` of the `MainWindow` is updated with the `GuiState` of the returned `CommandResult`.

The class diagram of how UI and Logic interact with each other is shown below.

![LogicGuiStateClassDigram](images/LogicGuiStateClassDiagram.png)

The sequence diagram of how this works for a `delete` command is found below. The objects in `Model` and `Storage` are not shown for simplicity.

![GuiStateSequenceDiagram](images/GuiStateSequenceDiagram.png)

When parsing, the respective `Parser` will check the current `GuiState` with the allowed `GuiState`s. If the `GuiState` is valid, it will proceed with parsing the command.
Otherwise, it will throw a `GuiStateException`.

#### Design Considerations

**Aspect: How to implement edit/delete lessons/exams:**

- **Alternative 1 (current choice)**: Limits these two commands to the Module Details view.
    - Pros: Easier to implement.
    - Cons: Requires passing `GuiState` from the `UI` component to the `Logic` component, reducing cohesion.
- **Alternative 2**: Combine the lists of lessons and exams into one central list in the List Lessons or Exams view respectively.
    - Pros: Does not require `MainWindow` to keep track of its current `GuiState`.
    - Cons: Difficult to implement - have to figure out how to map the `Lesson` or `Exam` from the central list to its original module.

---

## **Documentation, Logging, Testing, Configuration, DevOps**

- [Documentation Guide](Documentation.md)
- [Testing Guide](Testing.md)
- [Logging Guide](Logging.md)
- [Configuration Guide](Configuration.md)
- [DevOps Guide](DevOps.md)

---

## **Appendix: Requirements**

### Product Scope

**Target user profile:**

- has a need to manage modules and lessons related to the modules
- prefer desktop apps over other types
- can type fast
- prefers typing to mouse interactions
- is reasonably comfortable using CLI apps

**Value proposition**: manage modules faster NUSMods

### User Stories

Priorities: High (must have) - `***`, Medium (nice to have) - `**`, Low (unlikely to have) - `*`

| Priority | As a...     | I want to...                           | So that I can                                          |
| -------- | ----------- | -------------------------------------- | ------------------------------------------------------ |
| `***`    | new user    | see usage instructions                 | refer to instructions when I forget how to use the App |
| `***`    | NUS student | add modules to a module list           | keep track of my daily schedule                        |
| `***`    | NUS student | edit my modules                        | update changes in my curriculum plan                   |
| `***`    | NUS student | delete modules                         | update any withdrawal from modules                     |
| `***`    | NUS student | see a list of my modules               | have an overview of all my schoolwork                  |
| `***`    | NUS student | add lessons to a lesson list           | keep track of my lesson schedule                       |
| `***`    | NUS student | edit my lessons                        | update changes in the lesson schedule                  |
| `***`    | NUS student | delete my lessons                      | update if any lessons are cancelled                    |
| `***`    | NUS student | see a list of upcoming lessons         | plan my week ahead                                     |
| `***`    | NUS student | add examinations to a examination list | keep track of my examination schedule                  |
| `***`    | NUS student | edit my examinations                   | update any changes in the examinations                 |
| `***`    | NUS student | delete my examinations                 | update if any examinations are cancelled               |
| `***`    | NUS student | see a list of upcoming examinations    | plan my revision nearing the examination period        |

### Use Cases

(For all use cases below, the **System** is the `ModBook` and the **Actor** is the `user`, unless specified otherwise)

**Use case: Add a module**

**MSS**

1. User types in code and name of module
2. ModBook adds that module into the list
3. ModBook displays the added module

Use case ends.

**Extensions**
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: Add a lesson**

**MSS**

1. User types in module code, day of week, start time, end time, and optionally link and venue details
2. ModBook adds the lesson to the specified module
3. ModBook displays the added lesson

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: Add an exam**

**MSS**

1. User types in exam code, name, time and optionally link and venue
2. ModBook adds the exam to the specified module
3. ModBook displays the added exam

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: List all modules**

**MSS**

1. User requests to see a list of all modules
2. ModBook displays a list of all modules

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: List all lessons across modules**

**MSS**

1. User requests to see a list of all lessons
2. ModBook displays a list of all lessons, sorted by Day and Time

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: List all exams across modules**

**MSS**

1. User requests to see a list of all exams
2. ModBook displays a list of all exams, sorted by Date and Time

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: List lessons and exams for one module**

**MSS**

1. User requests to see list of all lessons and exams of the specified module
2. ModBook displays a list of all Exams and Lessons of the specified module

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: Delete a module**

**MSS**

1. User requests to delete a module
2. ModBook deletes the module and returns success

Use case ends.

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

**Use case: Delete a lesson**

**MSS**

1. User requests to delete a lesson
2. ModBook deletes the module and returns success

Use case ends.

**Use case: Delete an exam**

**MSS**

1. User requests to delete an exam associated with a module
2. ModBook deletes the module and returns success

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

**Use case: Edit a module**

**MSS**

1. User requests to edit a module
2. ModBook edits the module at the specified index

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

**Use case: Edit a lesson**

**MSS**

1. User requests to edit a lesson
2. Modbook edits the lesson and returns success message

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

**Use case: Edit an exam**

**MSS**

1. User requests to edit an exam
2. ModBook edits the exam and returns success message

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

**Use case: Display help**

**MSS**

1. User requests for help
2. ModBook presents a link to a help page

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

**Use case: Clear all modules**

**MSS**

1. User requests to clear all modules
2. ModBook clears all modules

**Extensions**  
1a. User types in a wrong parameter  
&nbsp;&nbsp;&nbsp; 1a1. ModBook outputs error message indicating parameter which has an error  
&nbsp;&nbsp;&nbsp; 1a2. User enters new data  
&nbsp;&nbsp;&nbsp; 1a3. Use case resumes from step 1

Use case ends.

### Non-Functional Requirements

1. Should work on any _mainstream OS_ as long as it has **`Java 11`** or above installed.
2. Should be able to hold up to 1000 modules without a noticeable sluggishness in performance for typical usage.
3. A user with above average typing speed for regular English text (i.e. not code, not system admin commands) should be able to accomplish most of the tasks faster using commands than using the mouse.

### Glossary

- **Mainstream OS**: Windows, Linux, Unix, macOS

---

## **Appendix: Instructions for Manual Testing**

Given below are instructions to test the app manually.

<div markdown="span" class="alert alert-info">:information_source: **Note:** These instructions only provide a starting point for testers to work on;
testers are expected to do more *exploratory* testing.

</div>

### Launch and Shutdown

1. Initial launch
    1. Download the jar file and copy into an empty folder
    2. Double-click the jar file <br>
       Expected: Shows the GUI with a set of sample modules. The window size may not be optimum.
2. Saving window preferences
    1. Resize the window to an optimum size. Move the window to a different location. Close the window.
    2. Re-launch the app by double-clicking the jar file. <br>
       Expected: The most recent window size and location is retained.
3. Shutdown
    1. Either use the `exit` command or close the window to execute a shutdown.

## Commands
### 1. `Add` Command
#### 1.1 Adding a module

1. Prerequisites:
   - ModBook should be in the Modules view. If ModBook is not in the Modules view, use the `list mod` command.
2. Test case: `add mod c/CS1231` <br>
   - Expected: First module is added to the list. Details of the added module are shown in the status message.
3. Test case: `add mod m/CS1231` <br>
   - Expected: No module is added. Error details shown in the status message, with correct command format provided.
4. Other incorrect `add` commands to try:
    - `add CS1231` <br>
        - Expected: No module is added as code prefix is not included.
    - `add`<br>
        - Expected: No module is added as `mod` is not specified after command.

#### 1.2 Adding a lesson

1. Prerequisites:
    - You must have at least one module in ModBook.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
2. Test case: `add lesson n/Tutorial d/Monday s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/COM1 ` <br>
    - Expected: First lesson is added to the list. Details of the added lesson are shown in the status message.
3. Test case: `add lesson d/Monday s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/COM1 ` <br>
    - Expected: No lesson is added. Error details shown in the status message, with correct command format provided.
4. Other incorrect `add` commands to try:
    - `add lesson n/Tutorial d/Monday s/10:00 e/11:00 l/ v/COM1 `<br>
        - Expected: No lesson is added as link field is empty.
    - `add lesson n/Tutorial s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/COM1 ` <br>
        - Expected: No lesson is added as day is not specified.
    - `add n/Tutorial d/Monday s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/COM1 ` <br>
        - Expected: No lesson is added as `lesson` is not specified after add command

#### 1.3 Adding an exam

1. Prerequisites:
    - You must have at least one module in ModBook.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
    - List the details of added module using the `detail c/CODE` command.
2. Test case: `add exam n/Final d/02/02/1999 s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/Field` <br>
    - Expected: First exam is added to the list. Details of the added exam are shown in the status message.
3. Test case: `add exam d/02/02/1999 s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/Field` <br>
    - Expected: No exam is added. Error details shown in the status message, with correct command format provided.
4. Other incorrect `add` commands to try:
    - `add exam n/Final d/02/02/1999 s/10:00 e/11:00 l/ v/Field`<br>
       - Expected: No exam is added as link field is empty.
    -  `add exam n/Final s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/Field` <br>
        - Expected: No exam is added as date is not specified.
    3. `add n/Final d/02/02/1999 s/10:00 e/11:00 l/https://www.youtube.com/watch?v=8mL3L9hN2l4 v/Field` <br>
        - Expected: No exam is added as `exam` is not specified after add command.

### 2. `List` command
#### 2.1 Listing all modules

1. Prerequisites:
    - Can be executed from any view.
2. Test case: `list mod` <br>
    - Expected: All modules are displayed, with their next lessons and next exams (if available)
3. Test case: `list` <br>
    - Expected: Missing parameters so nothing happens. Error details shown in the status message, with correct command format provided.

#### 2.2 Listing all lessons

1. Prerequisites:
    - Can be executed from any view.
2. Test case: `list lesson` <br>
    - Expected: Each module's lesson lists are displayed.
3. Test case: `list` <br>
    - Expected: Missing parameters so nothing happens. Error details shown in the status message, with correct command format provided.

#### 2.3 Listing all exams

1. Prerequisites:
    - Can be executed from any view.
2. Test case: `list exam` <br>
    - Expected: Each module's exam lists are displayed.
3. Test case: `list` <br>
    - Expected: Missing parameters so nothing happens. Error details shown in the status message, with correct command format provided.

### 3. `Details` Command
#### 3.1 Showing details of a module

1. Prerequisites:
    - Can be executed from any view.
    - For testing purposes, assume there is a module with code `CS2040S`.
2. Test case: `detail c\CS2040S`
    - Expected: A list of all Lessons and Exams of the specified Module is printed.
3. Test case: `detail`
    - Expected: Missing parameters so nothing happens. Error details shown in the status message, with correct command format provided.

### 4. `Delete` Command
#### 4.1 Deleting a module

1. Prerequisites:
    - You must have at least one module in ModBook.
    - ModBook should be in the Modules view. If ModBook is not in the Modules view, use the `list mod` command.
2. Test case: `delete mod 1` <br>
    - Expected: First module is deleted from the list. Details of the deleted module are shown in the status message.
3. Test case: `delete mod 0` <br>
    - Expected: No module is deleted. Error details shown in the status message, with correct command format provided.
4. Other incorrect `delete` commands to try:
    - `delete`, `delete 1` <br>
        - Expected: Similar to previous.
    - `delete mod x` (where x is greater than the list size) <br>
        - Expected: No module is found at the provided index.

#### 4.2 Deleting a Lesson

1. Prerequisites:
    - You must have at least one module in ModBook, which contains at least one lesson.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
2. Test case: `delete lesson 1` <br>
    - Expected: First lesson is deleted from the list. Details of the deleted lesson are shown in the status message.
3. Test case: `delete lesson 0` <br>
    - Expected: No lesson is deleted. Error details shown in the status message, with correct command format provided.
4. Other incorrect `delete` commands to try:
    - `delete`, `delete 1` <br>
        - Expected: Similar to previous.
    - `delete lesson x` (where x is greater than the list size) <br>
        - Expected: No lesson is found at the provided index.

#### 4.3 Deleting an Exam

1. Prerequisites:
    - You must have at least one module in ModBook, which contains at least one exam.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
2. Test case: `delete exam 1` <br>
    - Expected: First exam is deleted from the list. Details of the deleted exam are shown in the status message.
3. Test case: `delete exam 0` <br>
    - Expected: No exam is deleted. Error details shown in the status message, with correct command format provided.
4. Other incorrect `delete` commands to try:
    - `delete`, `delete 1` <br>
        - Expected: Similar to previous.
    - `delete exam x` (where x is greater than the list size) <br>
        - Expected: No exam is found at the provided index.

### 5. `Edit` Command
#### 5.1 Editing a Module

1. Prerequisites:
    - You must have at least one module in ModBook.
    - ModBook should be in the Modules view. If ModBook is not in the Modules view, use the `list mod` command.
    - For testing purposes, we assume the first module in the list does not have code **CS1010S** or name **Programming Methodology**.
2. Test case: `edit mod 1 c/CS1010S n/Programming Methodology` <br>
    - Expected: First module in the list is edited to have code **CS10101X** and name **Programming Methodology**. Details of the edited module are shown in the status message.
3. Test case: `edit mod 1` <br>
    - Expected: No module is edited. Error details shown in the status message, with correct command format provided.
4. Other incorrect `edit` commands to try:
    - `edit`, `edit 1` <br>
        - Expected: Similar to previous.
    - `edit mod X c/CS1010S n/Programming Methodology` (where X is greater than the list size) <br>
        - Expected: No module is found at the provided index.

#### 5.2 Editing a Lesson

1. Prerequisites:
    - You must have at least one module in ModBook, which contains at least one lesson.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
    - For testing purposes, we assume the first lesson in the list does not have the name **Weekly Tutorial**.
2. Test case: `edit lesson 1 n/Weekly Tutorial` <br>
    - Expected: First lesson in the module is edited to have name **Weekly Tutorial**. Details of the edited lesson are shown in the status message.
3. Test case: `edit lesson 1` <br>
    - Expected: No lesson is edited. Error details shown in the status message, with correct command format provided.
4. Other incorrect `edit` commands to try:
    - `edit`, `edit 1` <br>
        - Expected: Similar to previous.
    - `edit lesson x n/Weekly Tutorial` (where x is greater than the list size) <br>
        - Expected: No lesson is found at the provided index.

#### 5.3 Editing an Exam

1. Prerequisites:
    - You must have at least one module in ModBook, which contains at least one exam.
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the detail command with one of the modules already in ModBook (`detail c/CODE`).
    - For testing purposes, we assume the first exam in the list does not have venue **MPSH2**.
2. Test case: `edit exam 1 v/MPSH2` <br>
    - Expected: First exam in the module is edited to have venue MPSH2. Details of the edited exam are shown in the status message.
3. Test case: `edit exam 1` <br>
    - Expected: No exam is edited. Error details shown in the status message, with correct command format provided.
4. Other incorrect `edit` commands to try:
    - `edit`, `edit 1` <br>
        - Expected: Similar to previous.
    - `edit exam x v/MPSH2` (where x is greater than the list size) <br>
        - Expected: No exam is found at the provided index.

### 6. Testing Commands based on GUI view

Testing the validity of commands based on the GUI view of ModBook

#### 6.1 Modules View
1. Prerequisite:
    - ModBook should be in the Modules view. If ModBook is not in the Modules view, use the `list mod` command.
2. For testing purposes, we assume there is no module in the list with code **CS1010S** or name **Programming Methodology**. This should be the case with the default data loaded into the application.
3. Test case: `add mod c/CS1010S n/Programming Methodology` <br>
    - Expected: A module with code `CS1010S` and name `Programming Methodology` is added to the list.
4. Test case: `list lesson`<br>
    - Expected: The Lesson view is shown.
5. Test case: `add lesson n/test d/monday s/10am e/11am`<br>
    - Expected: No lesson added. Error details shown in the status message, prompting users to use the `detail` command.

#### 6.2 Lessons View
1. Prerequisite:
    - ModBook should be in the Lessons view. If ModBook is not in the Lessons view, use the `list lesson` command
2. Test case: `list mod`<br>
    - Expected: The Modules view is shown.
3. Test case: `edit mod 1 n/test`<br>
    - Expected: No module edited. Error details shown in the status message, prompting users to use the `list mod` command.
4. Test case: `add lesson n/test d/monday s/10am e/11am`<br>
    - Expected: No lesson added. Error details shown in the status message, prompting users to use the `detail` command.

#### 6.3 Exams View
1. Prerequisite:
    - ModBook should be in the Exams view. If ModBook is not in the Lessons view, use the `list exam` command
2. Test case: `list mod`<br>
    - Expected: The Modules view is shown.
3. Test case: `edit mod 1 n/test`<br>
    - Expected: No module edited. Error details shown in the status message, prompting users to use the `list mod` command.
4. Test case: `add lesson n/test d/monday s/10am e/11am`<br>
    - Expected: No lesson added. Error details shown in the status message, prompting users to use the `detail` command.

#### 6.4 Details View
1. Prerequisites:
    - You must have at least one module in ModBook
    - ModBook should be in the Details view. If ModBook is not in the Details view, use the `detail` command with one of the modules already in ModBook
2. Test case: `add lesson n/test d/monday s/10am e/11am`<br>
    - Expected: A lesson with name "test", day "Monday" and timeslot "10:00 - 11:00" is added to the module
3. Test case: `add exam n/test d/01/01/2022 s/10am e/11am`<br>
    - Expected: An exam with name "test", date "01/01/2022" and timeslot "10:00 - 11:00" is added to the module
4. Test case: `list lesson`<br>
    - Expected: The Lessons view is shown.
5. Test case: `add mod c/AB1234C n/test`<br>
    - Expected: No module edited. Error details shown in the status message, prompting users to use the `list mod` command.

## **Appendix: Effort**

### Rewriting entity types
As ModBook uses completely different entity types than AB3 (e.g. Module, Lesson), we had to write our entity classes from scratch, and implement custom methods that were required for the classes to function well. In addition to rewriting classes in `Model`, we also had to rewrite the classes in `Storage` to ensure that our entities could be written to and read in JSON.

### Implementing showing next Lesson
ModBook displays the next lesson of a module based on the system’s day and time. We implemented the lesson `Day` as an enumeration so that we did not have to worry about wrong inputs, but then we realised that Java enumerations do not allow the `compareTo` method to be overridden. Hence, we had to implement a custom `Comparator` for `Day` which offset the `Day` ordinal value by the current system day, and performed modulo arithmetic to make sure everything worked properly.

### Implementing `Edit` command
Edit was certainly the most time consuming feature to do, and also the feature that is most vulnerable to bugs. It effectively has the complexity of both deleting and adding a new object, while considering some additional complications from having optional fields such as requiring both start and end timings in order to modify a timeslot. As such, we had to ensure our test cases were rigorous.

### Graphical User Interface (GUI)
For the GUI, we made three additional GUI screens to display different information, and we also had to work out how to get the view to change based on the command that was executed. This was a challenge as we only allowed for certain commands to be executed in certain views. Weighing the pros and cons of different implementations, we decided to go with updating the `CommandResult` to return a value which changes the current screen view.
