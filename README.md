# Golf Handicap Calculator With Spring Boot


## Introduction

This application allows golfers to keep track of their handicap and past rounds played. After a round of golf they can
upload their scores after each round. If they have 20 rounds or more then we can calculate their true handicap,
otherwise they can just view their previous round scores. Users can interact with GolfHandicapCalculator using either
a set of RESTful service endpoints, or a simple UI, or both.

## Storyboard

https://github.com/orgs/Enterprise-Application-Dev-Group-1/projects/1/views/1

## Requirements

1. As a golfer, I want to be able to track my round scores, so I can see what my current handicap is.

### Example

**Given**: The option to input the par and score for the round

**When**: The user/service inputs 72 for the course par

**When**: The user/service inputs 89 for their score

**Then**: The user's/service's score of 89 over the par of 72 will be saved.

### Example

**Given**: The user/service has 20 rounds previously scored

**When**: The user/service requests their handicap

**Then**: The user's/service's handicap will be provided

### Example

**Given**: The user/service has 2 previous rounds registered

**When**: The user/service requests their handicap

**Then**: The user's/service's handicap will display how many more rounds need to be registered to calculate the handicap

## Buisness Logic

![Golf Handicap Calculator USGA](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/main/image.png)

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

![Golf Handicap Calculator Class Diagram](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/blob/main/GolfHandicapClassDiagram.drawio.png)


### Class Diagram Description

## JSON Schema

>{
>   "type":"object",
>   "properties":{
>       "name":{
>           "type":"string"
>       },
>       "handicap":{
>           "type":"integer"
>       }
>   }
>}


## Team Members and Roles

UI Specialist: Nirupama Poojari
Business Logic/Persistence: Nick Turner
DevOps/Product Owner/Scrum Master/Github Admin: Aiden Hartranft

## Milestones

[Milestone 1](https://github.com/Enterprise-Application-Dev-Group-1/SpringBoot-Application/milestone/1)
[Milestone 2](https://google.com)
[Milestone 3](https://google.com)
[Milestone 4](https://google.com)


## Standup

[We meet 5:00 PM Eastern on Thursdays](https://google.com)

