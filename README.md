# KillGoal

This spigot plugin is being developed for SootMC's 2023 Christmas event, but designed to be reused for similar events in the future.

# TO-DO
- Implement save/load system for spawner locations to persist between restarts
- Implement kill tracking
  - Server-wide goal
  - Player leaderboard
- Make spawners spawn mobs in 10x10 range instead of 1x1
- Rewards? Maybe separate from this plugin.

# DONE
- Set server-wide goal with command (/setgoal <number>)
- Set spawner locations with command (/setspawner)
- "Angry" snowmen
  - Without NMS, you can't really make a snowman target players, so they're technically just wandering naturally and forced to shoot snowballs towards random nearby players. The SnowballListener listens for hits on players and applies damage at that point.
- Start command (/start)
