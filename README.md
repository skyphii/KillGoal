# KillGoal

This spigot plugin is being developed for SootMC's 2023 Christmas event, but designed to be reused for similar events in the future.

# TO-DO
- Add player leaderboard
  - Hologram.
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

- ~~Set spawner locations with command (/setspawner)~~ Replaced with real spawners
- ~~Start command (/start)~~
