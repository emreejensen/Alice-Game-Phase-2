# Welcome to Alice's Adventures: A Text-Based Adventure Game!

This game immerses you in a whimsical text-based adventure inspired by 
American McGee's Alice Madness Returns, and, more generally, Lewis Carroll's Alice in Wonderland. 

## Key Features
- Dynamic Navigation: Moves Alice through rooms using directional commands (North, East, South, West)
- Room Visitation Tracking: Keeps track of whether a room has been visited
- Error Handling: Prevents invalid moves with feedback
- Puzzle Solving: Engage with puzzles in specific rooms
- Item Collection: Collect and manage items found in various rooms

## How To Play
1. Launch the program.
2. Enter a direction (North, East, South, West) or use abbreviations (N, E, S, W) to navigate.
3. Things will look "awfully familiar" if a room is entered 2+ more times.
4. Read & follow the error messages if Alice wanders to the end of the map (wrong direction).
5. Solve puzzles when prompted.
6. Collect items as you explore.
7. Type `exit` to end the game at any time.
8. Available Commands:
   - `north` or `n`: Move north
   - `east` or `e`: Move east
   - `south` or `s`: Move south
   - `west` or `w`: Move west
   - `pickup <item>`: Pick up an item
   - `drop <item>`: Drop an item
   - `inspect <item>`: Inspect an item in your inventory
   - `explore`: Explore the current room
   - `inventory`: View your inventory
   - `help`: Display available commands
   - `exit`: Exit the game








## How It Works
The program reads in text files "map.txt", "item.txt", and "puzzle.txt" and constructs the game world based on the input.

### Map File (map.txt)
The map file defines the rooms and their connections.

#### Example:
```
1~false~Houndsditch Home for Wayward Youth~In the cold, gloomy halls thick with dust, she serves as a maid for orphans under Dr. Bumby's watch.~Vorpal Blade~0~0,2,3,0
2~false~Hatter's Domain~A fiery factory churning its gears with molten lava, she moves through the searing heat.~Teapot Cannon~0~0,0,4,1
3~false~Deluded Depths~Abandoned ships haunt murky waters, their decaying frames sinking as she navigates the eerie depths.~0~1~1,4,5,0
4~false~Queensland~Card towers rise against the horizon, their fragile walls teetering as she treads carefully through the towering spires.~Roses~0~2,0,6,3
5~false~Dollhouse~She dances carefully through broken dolls and crumbling dollhouses, their twisted remains haunting the forgotten space.~Misstitched Dress~0~3,6,7,0
6~false~Infernal Train~The twisted, fiery train barrels through the wreckage, its wheels screeching as she races to escape its burning grasp.~0~2~4,0,0,5
7~false~Asylum~A dark, oppressive asylum with rusted bars and flickering lights, where echoes of madness haunt every corner.~0~3~5,0,0,0```

### Item File (item.txt)
The item file lists the items that can be found in specific rooms.

#### Example:
```
Vorpal Blade~The Vorpal Blade is similar in appearance to a common household kitchen knife and nothing like a sword as it is called in "Jabberwocky". It has a long silver blade and a short brown wooden handle.
Teapot Cannon~The Teapot Cannon looks like a teapot, or an old metal kettle. A clock face in inserted into the side of the belly of the pot, releases steam, and it shoots pocket watch-like projectiles from its large spout.
Roses~Roses are the representation of Alice Liddell's health. The number of the Roses corresponds to how much health she currently has, and if her health drops to one Rose left, Hysteria becomes available and she is rendered invincible for a short amount of time.
Misstitched Dress~The Misstitched Dress is based on the appearance of the Dollhouse domain, and is one of Alice's most colorful and vibrant dresses. However, it is also the most worn and torn, in accordance with the domain's ragged appearance.
```

### Puzzle File (puzzle.txt)
The puzzle file defines the puzzles in specific rooms.

#### Example:
```
1~Who has a hat and loves to chat?~mad hatter
2~He’s always late, with no time to wait?~white rabbit
3~She yells 'Off with their head!' and paints the roses red?~red queen
```








## Classes & Logic

### MVC Structure
The game is designed following the Model-View-Controller (MVC) pattern:

1. **Model**: Represents the data and the business logic of the application.
2. **View**: Represents the presentation layer and displays the data to the user.
3. **Controller**: Manages the communication between the Model and the View.

### Classes

#### 1. Main
- Entry Point
- Initializes the game and starts the game loop

#### 2. Player (Model)
- Alice's Current Location, Movement, and Rooms Visited
- Methods:
  - `move(String direction)`: Alice's Movement Logic
  - `getCurrentRoomName()` and `getCurrentRoomDescription()`: Get Room Details for Display

#### 3. GameMap (Model)
- Map Structure and All Rooms + Relationships
- Methods:
  - `readRooms(String fileName)`: Loads and parses room data from a text file
  - `getRoom(String roomID)`: Retrieves room by ID and throws an exception if needed

#### 4. Room (Model)
- Individual Rooms + Attributes and Room Connections
- Attributes:
  - `roomID`: Unique identifier for each room
  - `description`: Descriptive context for the room
  - `northRoomID`, `eastRoomID`, `southRoomID`, `westRoomID`: Connections to adjacent rooms
  - `isVisited`: Tracks room visitation history
- Methods:
  - `getRoomID()`, `getDescription()`, `isVisited()`: Retrieve room details
  - `setVisited(boolean isVisited)`: Updates the visited status

#### 5. Item (Model)
- Represents items that can be found and collected in rooms
- Attributes:
  - `itemID`: Unique identifier for each item
  - `name`: Name of the item
  - `description`: Description of the item
- Methods:
  - `getItemID()`, `getName()`, `getDescription()`: Retrieve item details

#### 6. Puzzle (Model)
- Represents puzzles that can be found in rooms
- Attributes:
  - `puzzleID`: Unique identifier for each puzzle
  - `description`: Description of the puzzle
  - `answer`: Correct answer for the puzzle
- Methods:
  - `getPuzzleID()`, `getDescription()`, `getAnswer()`: Retrieve puzzle details
  - `solve(String attempt)`: Check if the provided attempt is correct

#### 7. InvalidRoomException
- Custom exception thrown with error message when attempting to access an invalid room.

#### 8. GameView (View)
- Handles the display of messages and information to the player
- Methods:
  - `displayMessage(String message)`: Displays a message to the player
  - `displayInventory(Player player)`: Displays the player's inventory
  - `getUserInput()`: Gets input from the player
  - `displayHelp()`: Displays help information

#### 9. GameController (Controller)
- Manages the game logic and flow
- Methods:
  - `startGame()`: Starts the game loop and handles player commands











## Wonderland Default Map

### Version 1 (roomID) for map.txt
```
 _ _ _ _
|   |   |
|_1_|_2_|
|   |   |
|_3_|_4_|
|   |   |
|_5_|_6_|
|   |
|_7_|
```

### Version 2 (name) for map.txt
```
 _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ 
|   								 |				   |
|_Houndsditch_Home_for_Wayward_Youth_|_Hatter's_Domain_|
|   								 |   			   |
|_ _ _ _ _ _Deluded_Depths_ _ _ _ _ _|_ _Queensland_ _ |
|   								 |   			   |
|_ _ _ _ _ _ _Dollhouse_ _ _ _ _ _ _ | _Infernal_Train_|
|   								 |
|_ _ _ _ _ _ _ _Asylum_ _ _ _ _ _ _ _|
```

## Items Map
```
 _ _ _ _
|        |
|_VB_ _TC|
|        |
|_0_ __R_|
|        |
|_MD_ _0_|
|        |
|_0_|
```

### Legend:
- VB: Vorpal Blade (Houndsditch Home for Wayward Youth)
- TC: Teapot Cannon (Hatter's Domain)
- 0: No item (Deluded Depths, Infernal Train, Asylum)
- R: Roses (Queensland)
- MD: Misstitched Dress (Dollhouse)

## Puzzles Map
```
 _ _ _ _
|       |
|_0_ _0_|
|       |
|_P1_ 0_|
|       |
|_0_ P2_|
|       |
|_P3_|
```

### Legend:
- P1: Who has a hat and loves to chat? (Deluded Depths)
- P2: He’s always late, with no time to wait? (Infernal Train)
- P3: She yells 'Off with their head!' and paints the roses red? (Asylum)
- 0: No puzzle (Houndsditch Home for Wayward Youth, Hatter's Domain, Queensland, Dollhouse)










## Thank You & Enjoy Playing Alice's Adventures!
Emree Jensen
