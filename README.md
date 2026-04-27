# HotStone: A Card Game Architecture Project

## Overview

HotStone is a turn-based card game based on HeartStone built as a software architecture learning project for the Software Engineering and Architecture course at Aarhus University. The project applies design patterns and architectural principles to develop a codebase from a simple starting point into a flexible, extensible system supporting multiple game variants.

The project was built through test-driven development with focus on compositional design, clean architecture and design patterns in Java. It was developed collaborately togehter with another classmate, where we physically shared a screen and evenly switched between coding and assisting.

## Tech Stack

**Language & Build**
- Built in `Java 11+`
- `Maven` for build management
- `JUnit 5` as testing framework

**Core Libraries**
- `MiniDraw`: GUI framework for rendering game figures and handling user interactions
- `FRDS.Broker`: Distributed communication library for gameplay over network
- `WizardHub`: Course specific effect engine for card effects (adapter pattern integration)

**Design Patterns**
- Strategy Pattern (variant behavior) `src/main/java/hotstone/variants`
- Factory Pattern (object creation with pluggability) `src/main/java/hotstone/factories`
- Decorator Pattern (adding behavior without modification) `src/main/java/hotstone/decorator/TranscriptedDecoratorHotStoneGame.java`
- Adapter Pattern (using effect library) `src/main/java/hotstone/adapter/EffectWizardAdapter.java`
- Observer Pattern (event-driven UI updates) `src/main/java/hotstone/observer`
- Broker Pattern (distributed communication) `src/main/java/hotstone/broker`
- Model-View-Controller (implementing a GUI) `src/main/java/hotstone/view`
- Test doubles (automated tests of code) `src/main/java/hotstone/doubles`

## Technical Highlights

### 1. **Test-Driven Development**
The codebase evolved incrementally through systematic use of the TDD rythm. The test suite drives implementation and continuously verifies the system. Test doubles like stubs and spies are utilized to automate test cases.

### 2. **Strategy-Based Variant System**
Rather than using inheritance hierarchies, variants are compositionally build with strategies:
- **ManaStrategy**: How mana regenerates each turn
- **HeroStrategy**: Hero abilities and special powers
- **DeckStrategy**: Card pool and deck composition
- **WinnerStrategy**: Victory conditions

This design makes it easy to create new variants by combining existing strategies without modifying core game logic (StandardHotStoneGame.java). The frameworks for each variabilty point in the code also makes it easier and faster to implement new game elements, like cards, heroes or effects.

### 3. **Separation of Concerns**
The architecture maintains clean boundaries:
- **Domain Layer** (`hotstone.framework`, `hotstone.standard`): Pure game logic with no UI dependencies
- **View Layer** (`hotstone.view`): MiniDraw figures, UI tools, and graphical rendering
- **Broker Layer** (`hotstone.broker`): Network communication (client/server) using the Broker pattern
- **Variants Layer** (`hotstone.variants`): Strategy implementations for different game rules

### 4. **Effect Resolution via Adapter**
The project integrates an external effect engine (WizardHub provided by the course) using the Adapter pattern. The `EffectWizardAdapter` bridges between HotStone's game model and the external library's effect implementations.

### 5. **Observer-Driven UI Updates**
The UI reacts to game changes through an Observer pattern. The `GameObserver` interface allows the drawing engine to subscribe to domain events (card played, turn changed, hero attacked), ensuring UI stays synchronized with game state.

## Reflections

This was a project developed as part of a university course and has some limitations:

1. **MiniDraw Coupling**: The view layer is tightly coupled to MiniDraw. Introducing a proper abstraction layer would make the UI swappable (e.g., for Swing, JavaFX, or web-based rendering).

2. **Card Effect Complexity**: The external effect engine is powerful but the integration could be cleaner. Currently, effect classes manually instantiate adapters rather than receiving them via dependency injection.

3. **General Clean Up**: As clean code principles first were introduced later in the course, a general refactoring for better maintainability and analyzability could better some of the code.

4. **Visual Test Coverage Gaps**: While core game logic is well-tested, the view layer (`hotstone.view`) lacks comprehensive test coverage. Visual components were primarily tested manually through figure test cases.

Despite these limitations, the project gave me hands-on experience with multiple concepts like pair programming, compositional design, clean code principles, TDD, systematic black-box testing and multiple design patterns.
