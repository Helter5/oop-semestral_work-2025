# Insurance Management System

## Overview
A simplified information system for managing scientific projects implemented in Java. This is a semester project for Object-Oriented Programming (B-OOP) course.

**Tests were not included (only RequiredTests.java was).**
 
## Project Structure
The system manages the following core entities:
- **Insurance Company** - Main management entity
- **Contracts** - Various types of insurance contracts (Travel, Vehicle contracts)
- **Persons** - Individual and legal entities
- **Vehicles** - Vehicle information for insurance
- **Payment System** - Handles contract payments and premium frequencies

## Key Features
- **Contract Management**: Support for travel contracts and vehicle contracts (single and master)
- **Payment Processing**: Automated payment handling with different premium frequencies
- **Person Management**: Support for both individual persons and legal entities
- **Vehicle Insurance**: Comprehensive vehicle contract management
- **Payment Validation**: Built-in payment validation and error handling

## Technical Requirements
- **Language**: Java 21
- **Dependencies**: Only standard Java library and JUnit 5 for testing
- **No external libraries** allowed (including Lombok)

## Project Components

### Core Packages
- `company/` - Insurance company management
- `contracts/` - Contract implementations and abstractions
- `objects/` - Person, Vehicle, and LegalForm entities
- `payment/` - Payment processing and frequency management

### Testing
- `junit_tests_01/` - Primary test suite
- `junit_tests_02/` - Secondary test suite

## Implementation Notes
- All interfaces and enums are provided and **must not be modified**
- Only the `DataFactory` class requires implementation completion
- Maintains strict package structure as provided in template
- Implements comprehensive unit testing coverage

## Building and Running
The project is designed to be validated through unit tests. No GUI or console interface is required.

## Academic Information
- **Course**: B-OOP (Object-Oriented Programming)
- **Year**: 2024
