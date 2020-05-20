* Changes since last time:
    * AppendageTypes are gone, replaced with AppendagePresets.
    * Shuffled a few internal JSON things around, it actually uses gson now.

## Random notes on mount points

The mount point system assumes you are a player-shaped biped. This isn't great.

* I don't know if a "more player models" thing is out-of-scope but, you know, might be nice?
* Maybe at the very least, some way of indexing cuboids by an integer ID, so you can glue things to arbitrary cuboids.
* Also - appendage models providing their own mount point? Could be a thing? E.g. stick something on the end of your tail.