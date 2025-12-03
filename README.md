# Golf Handicap Calculator With Spring Boot


## Introduction

This application allows golfers to keep track of their handicap and past rounds played. After a round of golf they can
upload their scores after each round. If they have 20 rounds or more then we can calculate their true handicap,
otherwise they view their rough handicap. Users can interact with GolfHandicapCalculator using either
a set of RESTful service endpoints, or a simple UI, or both.

## Storyboard

The following images for the storyboard can be changed over time to satisfy the class diagram image. 

![Golf Handicap Calculator Storyboard part 1](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/9354acc4740dc20fa74c7d079e18cb5a8033e7f8/Group1_DesignDocu_Storyboard_part1.JPG)

![Golf Handicap Calculator Storyboard part 2](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/3e504da65ca6e4d235b4112bca8a1f8016d3ffc8/Group1_DesignDocu_Storyboard_part2.JPG)

![Golf Handicap Calculator Storyboard part 3](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/3e504da65ca6e4d235b4112bca8a1f8016d3ffc8/Group1_DesignDocu_Storyboard_part3.JPG)

![Golf Handicap Calculator Storyboard part 4](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/3e504da65ca6e4d235b4112bca8a1f8016d3ffc8/Group1_DesignDocu_Storyboard_part4.JPG)

![Golf Handicap Calculator Storyboard part 5](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/3e504da65ca6e4d235b4112bca8a1f8016d3ffc8/Group1_DesignDocu_Storyboard_part5.JPG)

## Requirements

1. As a golfer, I want to be able to track my round scores, so I can see what my current handicap is.

### Example

**Given**: The option to input the par score and slope for the round

**When**: The user/service inputs 72 for the course par

**When**: The user/service inputs 89 for their score

**WHNE**: The user/service inputs 121 for the slope

**Then**: The user's/service's score of 89 over the par of 72 and slope of 121 will be saved.

### Example

**Given**: The user/service has 20 rounds previously scored

**When**: The user/service requests their handicap

**Then**: The user's/service's handicap will be displayed

### Example

**Given**: The user/service has 2 previous rounds registered

**When**: The user/service requests their handicap

**Then**: The user's/service's handicap will display how many more rounds need to be registered to calculate the handicap (1)

### Example

***Given***: The user/service has 3 previous rounds played

***When***: The user/service request their handicap

***Then***: The user's/service's handicap will be displayed

## Buisness Logic

![Golf Handicap Calculator USGA](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/main/github_files/image.png)

This table provides the logic behind calculating handicap based on the USGA Rules.

To calculate the score differentials we need three things from the user to input for each round tracked.

- strokes (number of swings): int (ex: 89)
- par (number of strokes to achieve par for the course): int (ex: 72)
- course_slope (number representation of course relitive difficulty; range 55-155; 113 baseline): int (ex: 121)

Formula to calculate the differential:

Score Differential = ((strokes - par) * 113)/course_slope

### Example:

((89 - 72) * 113)/121 = +15.87

### Notes
- The course slope can be left empty, while this can lead to an inaccurate handicap, it will default to 113 since that is the baseline slope of golf courses.
- Handicaps can be negitive (strokes < par).

## Class Diagram

![Golf Handicap Calculator Class Diagram](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/main/github_files/plant_uml.png)

### Class Diagram Description
- **EnterpriseApplication**  
  Spring Boot entry point to launch the application.  

- **GolfHandicapController**  
  Exposes REST endpoints for managing players, scores, and handicap calculations. Uses the service layer.  

- **IPlayerServices (interface)**  
  Defines methods for saving players/scores and calculating handicaps.  

- **PlayerService (class)**  
  Implements `IPlayerServices` with real business logic. Uses DAO layer.  

- **PlayerServiceStub (class)**  
  Test/mock implementation of `IPlayerServices` for development or unit testing.  

- **IPlayerDAO (interface)**  
  Defines data access methods for player persistence.  

- **IScoreDAO (interface)**  
  Defines data access methods for score persistence.  

- **Player (DTO)**  
  Data object representing a golfer, including ID, name, and handicap.  

- **Score (DTO)**  
  Data object representing a single round, including score, par, slope, and associated player.  

## JSON Schema

Player
```json
{
  "type": "object",
  "properties": {
    "name": { "type": "string" },
    "handicap": { "type": "number" }
  },
  "required": ["name"]
}
```
Score
```json
{
  "type": "object",
  "properties": {
    "strokes": { "type": "integer" },
    "par": { "type": "integer" },
    "course_slope": { "type": "integer" }
  },
  "required": ["strokes", "par"]
}
```
## REST API

Base path: `/api` (controller: `GolfHandicapController`)

Players:
- `GET /api/players` — list all players
- `GET /api/players/{playerId}` — get player by id
- `POST /api/players` — create player (JSON)
- `PUT /api/players/{playerId}` — update player (JSON)
- `DELETE /api/players/{playerId}` — delete player

Scores:
- `GET /api/players/{playerId}/scores` — list a player's scores
- `POST /api/players/{playerId}/scores` — add score (JSON)
- `PUT /api/players/{playerId}/scores/{scoreId}` — update score (JSON)

Weather:
- `GET /api/weather?lat={lat}&lon={lon}` — returns human readable weather string for UI

## Team Members and Roles

- UI Specialist: Craig Blanding
- Software Developer: Nirupama Poojari
- Business Logic/Persistence: Nick Turner
- DevOps/Product Owner/Scrum Master/Github Admin: Aiden Hartranft
- Backend/Persistence: Nick Turner/Aiden Hartranft

## Github Project link

Project link: https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application

## Github Projects Kanban Board

https://github.com/orgs/Enterprise-Application-Dev-Group-1/projects/1/views/1

## Milestones

- [Milestone 1](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/milestone/1)
- [Milestone 2](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/milestone/3)
- [Milestone 3](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/milestone/4)
- [Milestone 4](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/milestone/5)

## How To Run Application
- Copy down the repo and open it in IntelliJ IDEA
- Install docker desktop on windows/macos machine
- Use Java 11 or higher, most of this was built with Java 11 so that is recommended
- Make sure you have Maven installed and setup in IntelliJ
- Run the docker-compose.yml file to start the database with ```docker compose up -d``` with the terminal at the root of the project directory
- Run the EnterpriseApplication file in InteliJ
- Access the web GUI from http://localhost:8080/

## Standup

[We meet 8:00 PM Eastern on Sundays](https://teams.microsoft.com/dl/launcher/launcher.html?url=%2F_%23%2Fl%2Fmeetup-join%2F19%3Ameeting_YTE0YTVmNTAtODVkYS00MDBmLTk5YWMtMjUxNTllMDRiY2Zh%40thread.v2%2F0%3Fcontext%3D%257b%2522Tid%2522%253a%2522f5222e6c-5fc6-48eb-8f03-73db18203b63%2522%252c%2522Oid%2522%253a%2522e3dd5340-4b1b-4344-8d9c-b206108a8a64%2522%257d%26anon%3Dtrue&type=meetup-join&deeplinkId=0be347a4-9a73-4f0f-956f-6f5c9cfd85e1&directDl=true&msLaunch=true&enableMobilePage=true&suppressPrompt=true)
  - Meeting 1 September 18, 5:00pm.
  - Meeting 2 September 28, 8:00pm.
  - Meeting 3 October 5, 8:00pm.
  - Meeting 4 October 12, 8:00pm.
  - Meeting 5 October 19, 8:00pm.
  - Meeting 6 October 26, 8:00pm.
  - Meeting 7 November 2, 8:00pm.
  - Meeting 8 November 9, 8:00pm.
  - Meeting 9 November 16, 8:00pm.
  - Meeting 10 November 23, 8:00pm.
  - Meeting 11 November 30, 8:00pm.
  - Meeting 12 December 7, 8:00pm.
