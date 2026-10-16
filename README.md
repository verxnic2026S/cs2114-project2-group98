# SongLink
CS 2114 — Project 2, Group 98

## Overview

SongLink is a Java console application for managing playlists. Its MVP
allows users to create playlists, add and remove songs, display playlist
contents, and simulate playing the next song. Playback means selecting
a song and updating its play count; the application does not play audio.

## Running the Application

1. Open the project in IntelliJ or Eclipse.
2. Configure the project's Java SDK and JUnit 4 dependency.
3. Compile the application classes.
4. Run `SongLinkApp.main()`.
5. Follow the console menu.

### MVP Demonstration

1. Create a playlist.
2. Add two songs.
3. Display the playlist.
4. Play the next song.
5. Remove a song and display the playlist again.
6. Enter invalid input and verify the application responds appropriately.
7. Exit.

## Application Structure

| Class | Responsibility |
|---|---|
| Song | Stores song information and tracks play count. |
| Playlist | Manages songs and playback position. |
| PlaylistManager | Manages the collection of playlists. |
| UserInput | Handles console interaction and input validation. |
| SongLinkApp | Creates application dependencies and starts the program. |
| LinkedChain<T> | Provides reusable generic linked storage. |

## Generic LinkedChain Reuse

The design uses the same generic `LinkedChain<T>` implementation for:

- `LinkedChain<Song>`: songs within a playlist.
- `LinkedChain<Playlist>`: playlists managed by `PlaylistManager`.

Application classes access `LinkedChain` through its public methods,
such as `addToEnd()`, `remove()`, `get()`, `size()`, and `toArray()`.
They do not access or modify its internal nodes directly.

Playlist management, playback, validation, and song ranking belong in
application classes. `LinkedChain` remains a general-purpose collection
with no song-specific or playlist-specific logic.

## Time Complexity

Let n be the number of elements in the collection being processed,
or the number of characters when validating input.

### LinkedChain Operations

| Operation | Time |
|---|---|
| `add()` at the front | O(1) |
| `addToEnd()` | O(n) |
| `remove()` from the front | O(1) |
| `remove(value)` | O(n) |
| `get(index)` | O(n) |
| `contains(value)` | O(n) |
| `size()`, `isEmpty()` | O(1) |
| `toArray()` | O(n) |
| `clear()` | O(1) |

### Application Operations

Application estimates depend on the traversal used.

| Operation | Expected time and condition |
|---|---|
| Find a playlist | O(n) with one linear traversal |
| Add a song to the end | O(n), plus any playlist lookup |
| Find or remove a song | O(n) with one linear traversal |
| Display all songs | O(n) with one traversal or `toArray()`; O(n²) if repeatedly calling `get(i)` |
| Play next song | O(n) worst case if accessing it through `get(index)` |
| Validate a name | O(n) |

## Input Validation and Error Handling

The `UserInput` design places console-input validation in:

- `validateName(String)`: rejects null or blank names and trims whitespace.
- `readChoice()`: rejects nonnumeric or unsupported menu choices.
- `readString(String)`: collects and validates a name.

| Case | Required response |
|---|---|
| Null or blank name | `validateName` throws an exception; the console caller handles it and re-prompts |
| Nonnumeric menu input | Display an error and re-prompt |
| Unsupported menu number | Display an error and re-prompt |

Expected user errors should be handled so the console application
can continue running.

## JUnit Tests

Run the test classes using the IDE's JUnit 4 runner.

For each public application method, the test plan includes a normal
case and an applicable bad-input or boundary case. Methods with no
input should be tested in relevant states, such as an empty collection.

| Area | Normal case | Bad-input or boundary case |
|---|---|---|
| Song construction/accessors | Correct title and artist returned | Invalid names follow the specified policy |
| Song playback | Play count increases | Initial count is zero |
| Playlist creation | Valid playlist created | Invalid name rejected |
| Adding songs | Added song can be retrieved | Invalid song handled |
| Removing songs | Existing song removed | Missing song and empty playlist |
| Displaying songs | Correct songs and order | Empty playlist |
| Playing next | Expected next song selected | Empty playlist and end-of-playlist behavior |
| Playlist management | Playlist stored and retrieved | Missing playlist and duplicate-name policy |
| UserInput validation | Valid choice/name accepted | Blank, null, nonnumeric, and out-of-range input |
| Generic reuse | Song and Playlist stored/retrieved | Empty chains and missing-item removal |

## Reflection

### 1. What changed from the specification, and why?

The project scope focuses on playlist management and simulated playback.
Real audio playback, shuffle, and an LRU system are outside the current
plan so the team can focus on the linked-data-structure requirements.

### 2. What changed in LinkedChain?

The intended boundary is that `LinkedChain` supplies generic collection
operations while application classes implement playlist behavior.

### 3. What questions or unknowns remain?

- Whether duplicate songs and playlist names are allowed.
- Whether playback wraps to the first song after the last song.
- How removing a song affects the current playback position.
- Whether application method complexities match the original estimates.
- Whether every public method has the required test coverage.

### GenAI Assistance

ChatGPT helped organize the documentation around the rubric.

## Scope

| Feature | Status |
|---|---|
| Shuffle | Dropped from the current plan |
| LRU system | Dropped from the current plan |
| Real audio playback | Outside this class-project scope |
