# Employee Leave Management System

A command-line based Employee Leave Management System built with Java, demonstrating core Object-Oriented Programming principles and design patterns. This system provides comprehensive functionality for managing employees, teams, and various types of leave applications with robust validation and error handling.

## 📋 Table of Contents
- [Features](#features)
- [System Architecture](#system-architecture)
- [Design Patterns](#design-patterns)
- [Leave Types](#leave-types)
- [Technologies Used](#technologies-used)
- [Installation & Setup](#installation--setup)
- [Usage](#usage)
- [Available Commands](#available-commands)
- [Exception Handling](#exception-handling)
- [Project Structure](#project-structure)

## ✨ Features

### Core Functionality
- **Employee Management**: Hire employees with customizable annual leave entitlements
- **Team Management**: Create teams with designated leaders and add team members
- **Leave Application System**: Support for multiple leave types (Annual, Block, Sick, No-pay)
- **Acting Head Assignment**: Automatic handling of acting heads when team leaders take leave
- **Leave Balance Tracking**: Real-time tracking of remaining annual and sick leave days
- **Date Management**: System date tracking for chronological operation validation

### Advanced Features
- **Undo/Redo Functionality**: Reversible operations for all major commands
- **Leave Overlap Detection**: Prevents conflicting leave periods
- **Acting Head Conflict Resolution**: Ensures acting heads are available during assignment periods
- **Block Leave Reservation**: Intelligent system reserves 7 days for mandatory block leave
- **Comprehensive Validation**: Multiple layers of business rule validation
- **Sorted Data Management**: Automatic alphabetical sorting of employees and teams

## 🏗️ System Architecture

The system follows a layered architecture with clear separation of concerns:

```
┌─────────────────────────────────────┐
│         Main (Entry Point)          │
│      - File-based command input     │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│       Command Layer (Pattern)       │
│  - Command Interface                │
│  - RecordedCommand (Undo/Redo)     │
│  - Concrete Command Classes         │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│        Business Logic Layer         │
│  - Company (Singleton)              │
│  - Employee Management              │
│  - Team Management                  │
│  - Leave Processing                 │
└──────────────┬──────────────────────┘
               │
┌──────────────▼──────────────────────┐
│          Domain Models              │
│  - Employee, Team, LeaveRecord      │
│  - ActingHeadRecord, Day            │
└─────────────────────────────────────┘
```

## 🎯 Design Patterns

### 1. **Command Pattern**
All user operations are encapsulated as command objects implementing the `Command` interface, enabling:
- Decoupling of command invocation from execution
- Easy addition of new commands without modifying existing code
- Support for undo/redo operations

### 2. **Singleton Pattern**
- `Company` class ensures single instance of company data
- `SystemDate` provides centralized date management

### 3. **Template Method Pattern**
- `RecordedCommand` abstract class defines the framework for undoable commands
- Subclasses implement specific `undoMe()` and `redoMe()` methods

### 4. **Strategy Pattern (Polymorphism)**
- Different leave types (`LeaveRecordAL`, `LeaveRecordBL`, `LeaveRecordSL`, `LeaveRecordNL`) extend `LeaveRecord`
- Each leave type can have specific validation rules

## 📅 Leave Types

### 1. Annual Leave (AL)
- Deducted from employee's annual leave balance
- Must be less than 7 consecutive days (otherwise use Block Leave)
- System reserves 7 days for block leave if not yet taken
- **Validation**: Sufficient balance, no overlap, not acting head during period

### 2. Block Leave (BL)
- Must be at least 7 consecutive days
- Mandatory leave type for extended breaks
- Deducted from annual leave balance
- **Validation**: At least 7 days, sufficient balance, no overlap

### 3. Sick Leave (SL)
- Maximum 135 days per employee
- Separate balance from annual leave
- **Validation**: Sufficient sick leave balance, no overlap

### 4. No-Pay Leave (NL)
- Does not deduct from any balance
- Used when leave balances are exhausted
- **Validation**: No overlap with existing leaves

## 🛠️ Technologies Used

- **Language**: Java
- **Programming Paradigm**: Object-Oriented Programming (OOP)
- **Data Structures**: ArrayList, Collections Framework
- **I/O**: File-based input processing
- **Design Principles**: SOLID principles, Encapsulation, Polymorphism, Abstraction

## 💻 Installation & Setup

### Prerequisites
- Java Development Kit (JDK) 8 or higher
- Command-line interface (Terminal, Command Prompt, or PowerShell)

### Steps
1. Clone the repository:
   ```bash
   git clone https://github.com/yourusername/employee-leave-management.git
   cd employee-leave-management
   ```

2. Compile the Java files:
   ```bash
   javac *.java
   ```

3. Prepare an input file with commands (see [Usage](#usage) section)

4. Run the program:
   ```bash
   java Main
   ```

5. When prompted, enter the path to your input file:
   ```
   Please input the file pathname: commands.txt
   ```

## 📖 Usage

### Input File Format
Commands are read from a text file with pipe-delimited format:

```
startNewDay|01-Jan-2025
hire|John|15
hire|Alice|20
setupTeam|Development|John
addTeamMember|Development|Alice
applyLeave|John|AL|05-Jan-2025|07-Jan-2025|Development|Alice
listEmployees
listLeaves|John
undo
redo
```

### Example Session
```
Please input the file pathname: test_commands.txt

> startNewDay|01-Jan-2025

> hire|John|15
Done.

> setupTeam|Development|John
Done.

> applyLeave|John|AL|05-Jan-2025|07-Jan-2025|Development|Alice
Done.

> listLeaves|John
05-Jan-2025 to 07-Jan-2025 [AL] (3 days)
```

## 📝 Available Commands

| Command | Format | Description |
|---------|--------|-------------|
| **startNewDay** | `startNewDay\|DD-MMM-YYYY` | Set/advance system date |
| **hire** | `hire\|Name\|AnnualLeaves` | Hire new employee |
| **listEmployees** | `listEmployees` | Display all employees |
| **setupTeam** | `setupTeam\|TeamName\|LeaderName` | Create new team |
| **listTeams** | `listTeams` | Display all teams |
| **addTeamMember** | `addTeamMember\|TeamName\|EmployeeName` | Add member to team |
| **listTeamMembers** | `listTeamMembers\|TeamName` | List team members |
| **listAllRoles** | `listAllRoles` | Show all employees and their roles |
| **applyLeave** | `applyLeave\|Name\|Type\|Start\|End[optional acting heads]` | Apply for leave |
| **listLeaves** | `listLeaves\|EmployeeName` | List employee's leaves |
| **undo** | `undo` | Undo last recorded command |
| **redo** | `redo` | Redo last undone command |

### Leave Application with Acting Heads
When a team leader applies for leave, acting heads must be specified:
```
applyLeave|John|AL|05-Jan-2025|10-Jan-2025|TeamA|Alice|TeamB|Bob
```
- Format: `applyLeave|LeaderName|LeaveType|Start|End|Team1|ActingHead1|Team2|ActingHead2|...`

## ⚠️ Exception Handling

The system includes comprehensive exception handling for business rule validation:

### Employee-Related Exceptions
- `ExEmployeeAlreadyExists`: Duplicate employee name
- `ExEmployeeNotFound`: Employee doesn't exist
- `ExEmployeeNotFoundForTeam`: Acting head not in specified team
- `ExEmployeeAlreadyInTeam`: Duplicate team membership

### Leave-Related Exceptions
- `ExLeaveOverlap`: Overlapping leave periods
- `ExInsufficientAnnualLeavesBalance`: Not enough annual leave days
- `ExInsufficientSickDayLeaves`: Not enough sick leave days
- `ExInvalidLeaveType`: Invalid leave type specified
- `ExBlockLeaveReservation`: Violation of block leave reservation rule
- `ExUseBlockLeave`: Annual leave ≥7 days should be block leave
- `ExUseAnnualLeave`: Block leave <7 days should be annual leave
- `ExActingHeadLeaveOverlap`: Acting head unavailable during period

### Team-Related Exceptions
- `ExTeamAlreadyExists`: Duplicate team name
- `ExTeamNotFound`: Team doesn't exist
- `ExMissingActingHead`: Acting head not specified for leader's team

### System Exceptions
- `ExWrongDate`: Invalid date (before system date)
- `ExInsufficientArguments`: Missing command parameters
- `ExAnnualLeavesOutOfRange`: Invalid annual leave entitlement

## 📂 Project Structure

```
├── Main.java                          # Entry point, file processing
├── Command.java                       # Command interface
├── RecordedCommand.java              # Abstract class for undoable commands
│
├── Command Implementations
│   ├── CmdHire.java                  # Hire employee command
│   ├── CmdSetupTeam.java            # Create team command
│   ├── CmdAddTeamMember.java        # Add team member command
│   ├── CmdApplyLeave.java           # Apply leave command
│   ├── CmdStartNewDay.java          # Advance date command
│   ├── CmdListEmployees.java        # List employees command
│   ├── CmdListTeams.java            # List teams command
│   ├── CmdListTeamMembers.java      # List team members command
│   ├── CmdListAllRoles.java         # List roles command
│   └── CmdListLeaves.java           # List leaves command
│
├── Domain Models
│   ├── Company.java                  # Singleton company management
│   ├── Employee.java                 # Employee entity
│   ├── Team.java                     # Team entity
│   ├── LeaveRecord.java             # Abstract leave record
│   ├── LeaveRecordAL.java           # Annual leave
│   ├── LeaveRecordBL.java           # Block leave
│   ├── LeaveRecordSL.java           # Sick leave
│   ├── LeaveRecordNL.java           # No-pay leave
│   ├── ActingHeadRecord.java        # Acting head assignment
│   ├── Day.java                      # Date handling
│   └── SystemDate.java              # Singleton system date
│
└── Custom Exceptions
    ├── ExEmployeeAlreadyExists.java
    ├── ExEmployeeNotFound.java
    ├── ExLeaveOverlap.java
    ├── ExInsufficientAnnualLeavesBalance.java
    └── ... (16+ custom exceptions)
```

## 🧪 Key Business Rules

1. **Leave Balance Management**
   - Annual leave: Employee-specific entitlement
   - Sick leave: 135 days maximum per employee
   - Block leave reservation: 7 days reserved until block leave taken

2. **Leave Overlap Prevention**
   - No overlapping leave periods for same employee
   - Acting heads cannot take leave during assignment period
   - Employee cannot be acting head if already on leave

3. **Date Validation**
   - Leave start date cannot be before system date
   - System date can only move forward

4. **Team Leader Requirements**
   - Team leaders must assign acting heads when taking leave
   - Acting heads must be team members
   - One acting head per team during leader's absence

## 🎓 Learning Outcomes

This project demonstrates proficiency in:
- **Object-Oriented Design**: Proper use of classes, inheritance, polymorphism, and encapsulation
- **Design Patterns**: Implementation of Command, Singleton, and Template Method patterns
- **Data Structures**: Effective use of Java Collections Framework
- **Exception Handling**: Custom exception hierarchy for business logic validation
- **Software Architecture**: Layered architecture with separation of concerns
- **Code Organization**: Clean, maintainable, and extensible codebase
- **Business Logic Implementation**: Complex rule validation and constraint handling

## 📄 License

This project was developed as part of academic coursework for CS2312.

## 👤 Author

[Your Name]  
[Your Email]  
[LinkedIn Profile]

---

**Note**: This system was developed as a coursework project to demonstrate OOP principles and software design patterns in Java.

