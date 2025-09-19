# Golf-Handicap-Calculator-With-Spring-Boot

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

**Given**: The user/service has 15 previous rounds registered

**When**: The user/service requests their handicap

**Then**: The user's/service's handicap will display how many more rounds need to be registered to calculate the handicap

## Class Diagram

![Golf Handicap Calculator Class Diagram](https://google.com)

### Class Diagram Description

## JSON Schema

>{
>   "type":"object",
>   "properties":{
>       "name":{
>           "type":"string"
>       },
>       "pars": {
>           "type":"integer array"
>       }
>       "scores": {
>           "type":"integer array"
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

[Milestone 1](https://google.com)
[Milestone 2](https://google.com)
[Milestone 3](https://google.com)
[Milestone 4](https://google.com)

## Standup

[We meet 5:00 PM Eastern on Thursdays](https://google.com)

