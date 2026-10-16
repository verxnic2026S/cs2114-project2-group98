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
4. Run SongLinkApp.main().
5. Follow the console menu.

[Confirm the entry-point class and required Java version.]

### MVP Demonstration

1. Create a playlist.
2. Add two songs.
3. Display the playlist.
4. Play the next song.
5. Remove a song and display the playlist again.
6. Enter invalid input and verify the application responds appropriately.
7. Exit.

Current compile/run status: [Record the actual result.]

## Application Structure

| Class | Responsibility |
|---|---|
| Song | Stores song information and tracks play count. |
| Playlist | Manages songs and playback position. |
| PlaylistManager | Manages the collection of playlists. |
| UserInput | Handles console interaction and input validation. |
| SongLinkApp | Creates application dependencies and starts the program. |
| LinkedChain<T> | Provides reusable generic linked storage. |

[Update this table to match the implemented classes.]

## Generic LinkedChain Reuse

The design uses the same generic LinkedChain<T> implementation for:

- LinkedChain<Song>: songs within a playlist.
- LinkedChain<Playlist>: playlists managed by PlaylistManager.

[Confirm that both types are stored and retrieved in the current code.]

Application classes access LinkedChain through its public methods,
such as addToEnd(), remove(), get(), size(), and toArray(). They do not
access or modify its internal nodes directly.

Playlist management, playback, validation, and song ranking belong in
application classes. LinkedChain remains a general-purpose collection
with no song-specific or playlist-specific logic.

A third stored element type is [not implemented / implemented as ___].
Two types satisfy the rubric's Meets requirement; more than two are
needed for Exceeds in generic reuse.

## Time Complexity

Let p be the number of playlists and s the number of songs in a playlist.

The following collection costs come from the LinkedChain design:

| LinkedChain operation | Time |
|---|---|
| add() at the front | O(1) |
| addToEnd() | O(n) |
| remove() from the front | O(1) |
| remove(value) | O(n) |
| get(index) | O(n) |
| contains(value) | O(n) |
| size(), isEmpty() | O(1) |
| toArray() | O(n) |
| clear() | O(1) |

Application estimates depend on the traversal used:

| Application operation | Expected time and condition |
|---|---|
| Find a playlist | O(p) with one linear traversal |
| Add a song to the end | O(s), plus any playlist lookup |
| Find or remove a song | O(s) with one linear traversal |
| Display all songs | O(s) with one traversal or toArray(); O(s²) if repeatedly calling get(i) |
| Play next song | O(s) worst case if accessing it through get(index) |
| Validate a name | O(k), where k is the input length |

Comparison with the specification:
[Identify the actual methods, confirm their complexities, and explain
any differences from the original estimates. Repeated indexed access
on a linked collection can make a loop quadratic.]

## Input Validation and Error Handling

The UserInput design places console-input validation in:

- validateName(String): rejects null or blank names and trims whitespace.
- readChoice(): rejects nonnumeric or unsupported menu choices.
- readString(String): collects and validates a name.

Expected responses must match the approved specification.

| Case | Required response | Current status |
|---|---|---|
| Null or blank name | validateName throws an exception; console caller handles it and re-prompts | [Verify] |
| Nonnumeric menu input | Display an error and re-prompt | [Verify] |
| Unsupported menu number | Display an error and re-prompt | [Verify] |
| Playlist not found | [Insert specified response] | [Verify] |
| Song not found | [Insert specified response] | [Verify] |
| Playback on an empty playlist | [Insert specified response] | [Verify] |
| Duplicate song or playlist name | [Insert the team's duplicate policy] | [Verify] |

Expected user errors should be handled at the layer named in the
specification so the console application can continue running.

## JUnit Tests

Run the test classes using the IDE's JUnit 4 runner.

Latest test result: [Date, number of tests, failures, and errors.]
Do not mark tests as passing until they have been executed.

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

Implemented test classes: [List actual class names.]

Proposed tests to add if absent:

- PlaylistTest.testRemoveFromEmptyPlaylist
- PlaylistTest.testRemoveMissingSong
- PlaylistTest.testPlayNextOnEmptyPlaylist
- PlaylistTest.testPlaybackAtEnd
- PlaylistManagerTest.testMissingPlaylist
- LinkedChainReuseTest.testSongStorageAndRetrieval
- LinkedChainReuseTest.testPlaylistStorageAndRetrieval

These are proposed test names, not claims that the tests already exist.

## Reflection

### 1. What changed from the specification, and why?

The project scope focuses on playlist management and simulated playback.
Real audio playback, shuffle, and an LRU system are outside the current
plan so the team can focus on the linked-data-structure requirements.

[Confirm which of these were actual changes from the submitted
specification rather than decisions made before it was submitted.]

Specific implementation changes:
- [Named class/method, original plan, actual change, and reason.]
- [Origin: group discussion, GenAI suggestion, or individual reflection.]

### 2. What changed in LinkedChain?

[State whether LinkedChain was reused unchanged. If changed, name each
method, explain why it changed, and identify where the change originated.]

The intended boundary is that LinkedChain supplies generic collection
operations while application classes implement playlist behavior.

### 3. What questions or unknowns remain?

Items to confirm against the specification and current implementation:

- Whether duplicate songs and playlist names are allowed.
- Whether playback wraps to the first song after the last song.
- How removing a song affects the current playback position.
- Whether application method complexities match the original estimates.
- Whether every public method has the required test coverage.

[Replace resolved items with the team's actual decisions.]

### 4. What does not work yet?

[Name unfinished classes, methods, failing tests, and integration issues.
If the MVP works completely, record the verified demonstration and test
results rather than leaving this section blank.]

GenAI assistance for this README:
ChatGPT helped organize the documentation around the rubric. The team
must verify implementation claims, results, decisions, and ownership
before submission.

## Next Steps

Complete remaining work in this order. Owners below are proposed role
assignments; replace them with team members' names.

| Order | Remaining task | Owner | Completion evidence |
|---|---|---|---|
| 1 | Confirm scope decisions and document deviations | Scope/documentation owner: [Name] | Reflection updated with specific decisions |
| 2 | Compile all classes and resolve integration issues | Integration owner: [Name] | Successful compilation and application launch |
| 3 | Complete any missing playlist management and playback methods | Playlist owner: [Name] | MVP demonstration succeeds |
| 4 | Verify both generic LinkedChain element types | Collection owner: [Name] | Storage/retrieval tests pass for Song and Playlist |
| 5 | Match invalid-input behavior to the specification | Input owner: [Name] | Bad-input tests and console checks pass |
| 6 | Complete public-method and boundary-case tests | Testing owner: [Name] | Recorded JUnit results |
| 7 | Verify Big-O estimates against actual loops and collection calls | Review owner: [Name] | Complexity table corrected |
| 8 | Finalize documentation and repeat the MVP demonstration | Documentation owner: [Name] | README matches the submitted application |

### Stretch Goals

Reconcile this list with the submitted scope before submission.

| Feature | Decision/status | Tests if committed |
|---|---|---|
| Rank songs by play count | [Committed or dropped] | Ordering, ties, empty playlist |
| Search songs | [Committed or dropped] | Existing song, missing song, empty playlist |
| Additional sorting | [Committed or dropped] | Order, duplicates, empty playlist |
| Shuffle | Dropped from the current plan | Not applicable |
| LRU system | Dropped from the current plan | Not applicable |
| Real audio playback | Outside this class-project scope | Not applicable |

A stretch goal counts as working only after implementation and testing.
Documentation alone does not establish completion.
