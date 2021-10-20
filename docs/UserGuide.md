# User Guide

NUS ModBook is a **desktop app for managing modules, optimized for use via a Command Line Interface** (CLI) while still
having the benefits of a Graphical User Interface (GUI). If you can type fast, ModBook can help you organise modules
faster than traditional GUI apps.

## Table of Contents

- [User Guide](#user-guide)
  - [Table of Contents](#table-of-contents)
  - [Quick Start](#quick-start)
  - [Features](#features)
    - [Notes about commands](#notes-about-commands)
    - [Modules](#modules)
      - [Adding a module: `add mod`](#adding-a-module-add-mod)
      - [Listing all modules : `list mod`](#listing-all-modules--list-mod)
      - [Show details of a module: `detail`](#show-details-of-a-module-detail)
      - [Editing a module: `edit mod`](#editing-a-module-edit-mod)
      - [Delete a module: `delete mod`](#delete-a-module-delete-mod)
    - [Lessons](#lessons)
      - [Adding a lesson: `add lesson`](#adding-a-lesson-add-lesson)
      - [Listing all lessons : `list lesson`](#listing-all-lessons--list-lesson)
      - [Editing a lesson: `edit lesson`](#editing-a-lesson-edit-lesson)
      - [Deleting a lesson: `delete lesson`](#deleting-a-lesson-delete-lesson)
    - [Exams](#exams)
      - [Adding an exam: `add exam`](#adding-an-exam-add-exam)
      - [Listing all Exams : `list exam`](#listing-all-exams--list-exam)
      - [Editing an Exam: `edit exam`](#editing-an-exam-edit-exam)
      - [Deleting an Exam : `delete exam`](#deleting-an-exam--delete-exam)
    - [Miscellaneous](#miscellaneous)
      - [Viewing help : `help`](#viewing-help--help)
      - [Clearing all entries : `clear`](#clearing-all-entries--clear)
      - [Exiting the program : `exit`](#exiting-the-program--exit)
      - [Saving the data](#saving-the-data)
      - [Editing the data file](#editing-the-data-file)
  - [FAQ](#faq)
  - [Date and time formats](#date-and-time-formats)
  - [Command Summary](#command-summary)
    - [Command Summary by Action](#command-summary-by-action)
    - [Command Summary by Object](#command-summary-by-object)
    - [Command Summary by Screen](#command-summary-by-screen)

## Quick Start

1. Ensure you have Java 11 or above installed in your Computer.
2. Download the latest modbook.jar from here.
3. Copy the file to the folder you want to use as the home folder for your ModBook.
4. Double-click the file to start the app. The GUI similar to the below should appear in a few seconds. Note how the app
   contains some sample data
5. Try out some example commands:

- `list mod`: Lists all modules.
- `add mod c/CS2103T n/Software Engineering`: Adds a module with code CS2103T, named Software Engineering.
- `delete mod 1`: Deletes the 1st module shown in the module list.
- `clear`: Deletes all modules.
- `exit`: Exits the app.

6. Refer to the Features below for details of each command.

## Features

### Notes about commands

- Words in UPPER_CASE are the parameters to be supplied by the user. e.g. in add `c/CODE`, `CODE` is a parameter which
  can be used as `add mod c/CS2103T`.
- Items in square brackets are optional. e.g `c/CODE [n/NAME]` can be used as `c/CS2103T` or
  as `c/CS2103T n/Software Engineering`.
- Parameters can be in any order. e.g. if the command specifies `c/CODE n/NAME`, `n/NAME c/CODE` is also acceptable.
- If a parameter is expected only once in the command but you specified it multiple times, only the last occurrence of
  the parameter will be taken. e.g. if you specify `p/12341234 p/56785678`, only `p/56785678` will be taken.
- Extraneous parameters for commands that do not take in parameters (such as `help`, `exit` and `clear`) will be
  ignored. e.g. if the command specifies `help 123`, it will be interpreted as `help`.
- Some commands such as `delete` and `edit` can only be executed from certain screens. These requirements will be 
  further explained in the descriptions of the commands below. A summary of requirements is also provided in
  the `Command Summary by Screen` table under `Command Summary` below.

### Modules

#### Adding a module: `add mod`

Adds a module to the ModBook. This command can be executed in any screen.

Format: `add mod c/CODE [n/NAME]`

- Example: `add mod c/CS2103T n/Software Engineering` : Adds a module with code CS2103T, named Software Engineering.

#### Listing all modules : `list mod`

Shows a list of all modules in the ModBook. This command can be executed in any screen.

Format: `list mod`

#### Show details of a module: `detail`

Show details of an existing module in the ModBook. A list of all Lessons and Exams of the specified Module is printed.
This command can be executed in any screen.

Format: `detail c/CODE`

- Example: `detail c/CS2103T` : Shows details for the CS2103T module.

#### Editing a module: `edit mod`

Edits an existing module in the ModBook. This command can only be executed in the modules screen.

Format: `edit mod INDEX [c/NEW_CODE] [n/NEW_NAME]`

- Edits the module at the specified `INDEX`. The index refers to the index number shown in the displayed module list.
  The index must be a positive integer 1, 2, 3, …​
- At least one of the optional fields must be provided.
- Existing values will be updated to the input values.
- Examples:
  `edit mod 1 c/CS2105 n/Computer Networks` : Edits the name of the CS2105 module to Computer Networks

#### Delete a module: `delete mod`

Deletes a module from the ModBook.  This command can only be executed in the modules screen.

Format: `delete mod INDEX`

- Deletes the person at the specified INDEX.
- The index refers to the index number shown in the displayed person list.
- The index must be a positive integer 1, 2, 3, …​ Examples: `delete mod 2` : deletes the 2nd module in the ModBook.

### Lessons

#### Adding a lesson: `add lesson`

Adds a lesson to the ModBook. This command can be executed in any screen.

Format: `add lesson c/CODE n/NAME d/DAY_OF_WEEK s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`

- Examples:
  `add lesson c/CS2103T n/Weekly Tutorial d/Monday s/1000 e/1100 l/https://www.latlmes.com/breaking/click-this-link-for-exam-link-1`:
  Adds a lesson to the CS2103T module.

#### Listing all lessons : `list lesson`

Shows a list of all lessons in the ModBook. This command can be executed in any screen.

Format: `list lesson`

#### Editing a lesson: `edit lesson`

Edits an existing lesson in the ModBook. This command can only be executed in the details screen.

Format: `edit lesson INDEX [n/NEW_NAME] [d/NEW_DAY_OF_WEEK] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]`

Examples:

- `edit lesson 2 c/CS2103T n/Weekly Tutorial d/Monday s/1000 e/1100 l/https://www.latlmes.com/breaking/click-this-link-for-exam-link-1` :
  Edits the second lesson of the CS2103T module.

#### Deleting a lesson: `delete lesson`

Deletes a lesson from the ModBook. This command can only be executed in the details screen.

Format: `delete lesson`

- Deletes the lesson at the specified INDEX.
- The index refers to the index number shown in the displayed module detail list.
- The index must be a positive integer 1, 2, 3, …​

Examples:

- `delete lesson 2`: Deletes the 2nd lesson of the module that is currently displayed.

### Exams

#### Adding an exam: `add exam`

Adds an exam to the ModBook. This command can be executed in any screen.

Format: `add exam c/CODE n/NAME d/DATE s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`

- Examples:
  `add exam c/CS2103T n/Final Exam d/31/12/2022 s/2100 e/2200 l/https://www.latlmes.com/breaking/click-this-link-for-exam-link-1`:
  Adds an exam to the CS2103T module.

#### Listing all Exams : `list exam`

Shows a list of all lessons in the ModBook. This command can be executed in any screen.

Format: `list exam`

#### Editing an Exam: `edit exam`

Edits an existing exam in the ModBook. This command can only be executed in the details screen.

Format: `edit exam 3 [n/NEW_NAME] [d/NEW_DATE] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]`

- Examples:
  `edit exam 3 c/CS2103T n/Final Exam d/31/12/2022 s/2100 e/2200 l/https://www.latlmes.com/breaking/click-this-link-for-exam-link-1` :
  Edits the 3rd exam of the CS2103T module.

#### Deleting an Exam : `delete exam`

Deletes the specified Exam from the ModBook. This command can only be executed in the details screen.

Format: `delete exam INDEX`

- Deletes the Exam at the specified INDEX.
- The index refers to the index number shown in the displayed Module details list.
- The index must be a positive integer 1, 2, 3, …​
- Examples:
  `delete exam 3` : Deletes the 3rd exam of the module that is currently displayed.

### Miscellaneous

#### Viewing help : `help`

Shows a message explaining how to access the help page. Format: `help`

#### Clearing all entries : `clear`

Clears all entries from the ModBook. Format: `clear`

#### Exiting the program : `exit`

Exits the program. Format: `exit`

#### Saving the data

ModBook data is saved in the hard disk automatically after any command that changes the data. There is no need to save
manually.

#### Editing the data file

ModBook data is saved as a JSON file `[JAR file location]/data/modbook.json`. Advanced users are welcome to update data
directly by editing that data file. Caution: If your changes to the data file makes its format invalid, ModBook will
discard all data and start with an empty data file at the next run.

## FAQ

**Q**: How do I transfer my data to another Computer?
**A**: Install the app in the other computer and overwrite the empty data file it creates with the file that contains
the data of your previous ModBook home folder.

## Date and time formats

The following formats are valid for entering time values:

| Format   | Examples             |
| -------- | -------------------- |
| `HH:mm`  | `09:00`, `14:30`     |
| `HH.mm`  | `09.00`, `14.30`     |
| `HHmm`   | `0900`, `1430`       |
| `ha`     | `9AM`, `2pm`         |
| `hha`    | `09AM`, `11pm`       |
| `h:mma`  | `9:00AM`, `2:30pm`   |
| `hh:mma` | `09:00AM`, `11:30pm` |
| `h.mma`  | `9.00AM`, `2.30pm`   |
| `hh.mma` | `09.00AM`, `11.30pm` |

The meaning of the symbols in the formats specified are in the table below:

| Symbol | Meaning                                 |
| ------ | --------------------------------------- |
| H      | Hour of 24-hour time. Goes from 0 to 23 |
| h      | Hour of 12-hour time. Goes from 1 to 12 |
| m      | Minute                                  |
| a      | AM/PM indicator                         |

## Command Summary

### Command Summary by Action

| Action | Object | Format, Examples                                                                                                    |
| ------ | ------ | ------------------------------------------------------------------------------------------------------------------- |
| Add    | Module | `add mod c/CODE [n/NAME]`                                                                                           |
|        | Lesson | `add lesson c/CODE n/NAME d/DAY_OF_WEEK s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`                                 |
|        | Exam   | `add exam c/CODE n/NAME d/DATE s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`                                          |
| List   | Module | `list mod`                                                                                                          |
|        | Lesson | `list lesson`                                                                                                       |
|        | Exam   | `list exam`                                                                                                         |
| Detail | Module | `detail c/CODE`                                                                                                     |
| Delete | Module | `delete mod INDEX`                                                                                                  |
|        | Lesson | `delete lesson INDEX`                                                                                               |
|        | Exam   | `delete exam INDEX`                                                                                                 |
| Edit   | Module | `edit mod INDEX [c/NEW_CODE] [n/NEW_NAME]`                                                                          |
|        | Lesson | `edit lesson INDEX [n/NEW_NAME] [d/NEW_DAY_OF_WEEK] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]` |
|        | Exam   | `edit exam INDEX [n/NEW_NAME] [d/NEW_DATE] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]`          |
| Help   |        | `help`                                                                                                              |
| Clear  |        | `clear`                                                                                                             |
| Exit   |        | `exit`                                                                                                              |

### Command Summary by Object

| Object | Action | Format, Examples                                                                                                    |
| ------ | ------ | ------------------------------------------------------------------------------------------------------------------- |
| Module | Add    | `add mod c/CODE [n/NAME]`                                                                                           |
|        | Detail | `detail c/CODE`                                                                                                     |
|        | List   | `list mod`                                                                                                          |
|        | Edit   | `edit mod INDEX [c/NEW_CODE] [n/NEW_NAME]`                                                                          |
|        | Delete | `delete mod INDEX`                                                                                                  |
| Lesson | Add    | `add lesson c/CODE n/NAME d/DAY_OF_WEEK s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`                                 |
|        | List   | `list lesson`                                                                                                       |
|        | Edit   | `edit lesson INDEX [n/NEW_NAME] [d/NEW_DAY_OF_WEEK] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]` |
|        | Delete | `delete lesson INDEX`                                                                                               |
| Exam   | Add    | `add exam c/CODE n/NAME d/DATE s/START_TIME e/END_TIME [l/LINK] [v/VENUE]`                                          |
|        | List   | `list exam`                                                                                                         |
|        | Edit   | `edit exam INDEX [n/NEW_NAME] [d/NEW_DATE] [s/NEW_START_TIME] [e/NEW_END_TIME] [l/NEW_LINK] [v/NEW_VENUE]`          |
|        | Delete | `delete exam INDEX`                                                                                                 |
| Help   |        | `help`                                                                                                              |
| Clear  |        | `clear`                                                                                                             |
| Exit   |        | `exit`                                                                                                              |

### Command Summary by Screen

| Screen  | Commands                                                                                                                                     |
|---------|----------------------------------------------------------------------------------------------------------------------------------------------|
| Modules | `add mod`<br>`add lesson`<br>`add exam`<br>`edit mod`<br>`delete mod`<br>`clear`<br>`list`<br>`detail`                                       |
| Lessons | `add mod`<br>`add lesson`<br>`add exam`<br>`clear`<br>`list`<br>`detail`                                                                     |
| Exams   | `add mod`<br>`add lesson`<br>`add exam`<br>`clear`<br>`list`<br>`detail`                                                                     |
| Details | `add mod`<br>`add lesson`<br>`add exam`<br>`edit lesson`<br>`edit exam`<br>`delete lesson`<br>`delete exam`<br>`clear`<br>`list`<br>`detail` |
