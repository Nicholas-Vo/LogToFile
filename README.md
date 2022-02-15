![logtofile2](https://user-images.githubusercontent.com/60233722/153992586-c9af19c5-1d09-4f21-8134-ff195b881fe8.png)

This plugin logs the following actions to a text file:

- Shooting item frames and armor stands with a projectile
- Players nearby when a creeper explodes
- Removal of books from lecterns
- Mounting of horses
- Naming of entities via nametag

You can toggle these via the config.yml.

**Note:** This isn't a polished CoreProtect or Prism extension, it's a simple personal plugin I've made public for those who are interested. The plugin does not include a config reload functionality at the moment.


# Obtaining the plugin

You can download the plugin here on GitHub by navigating to "Releases"

# Configuration

You can disable actions which you do not wish to have logged. By default, all actions are enabled!

```
logProjectiles: true
logCreeperProvoke: false
logLecternTake: true
logHorseMounts: true
logNametagUse: true
```

# Example

An example of a creeperProvoke.txt entry:
```
[2022-02-14] Creeper exploded at 42 73 -78 in "world"
[2022-02-14] - Nearby: _NickV
```
