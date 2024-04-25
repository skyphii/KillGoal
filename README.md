# KillGoal

This Minecraft/Spigot plugin was developed for a community server's Christmas event. It was a rushed development and focused on the specific requirements provided, so it may not perfectly fit the use-cases for similar events. The server has since shut down and this repo is not being maintained.

# TO-DO
- Ice zombies?

# DONE
- Set server-wide goal with command (/setgoal <number>)
- "Angry" snowmen
  - Without NMS, you can't really make a snowman target players, so they're technically just wandering naturally and forced to shoot snowballs towards random nearby players. The SnowballListener listens for hits on players and applies damage at that point.
- Place spawners and right click them with a snow golem egg to spawn angry snowmen (rates/range automatically get adjusted)
- Basic kill tracking
- Save/load server-wide kill goal progress
- Finish implementing kill tracking
  - Implement save/load methods in PlayerData.java
- Add player leaderboard
  - Hologram.
- Add permissions for commands

- ~~Set spawner locations with command (/setspawner)~~ Replaced with real spawners
- ~~Start command (/start)~~
