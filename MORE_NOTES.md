* Changes since last time:
    * AppendageTypes are gone, replaced with AppendagePresets.
    * Shuffled a few internal JSON things around, it actually uses gson now.

## Random notes on mount points and species assumptions

The mount point system assumes you are a player-shaped biped. This isn't great.

* I don't know if a "more player models" thing is out-of-scope but, you know, might be nice?
* Maybe at the very least, some way of indexing cuboids by an integer ID, so you can glue things to arbitrary cuboids.
* Also - appendage models providing their own mount point? Could be a thing? E.g. stick something on the end of your tail.

## On models

The divisions:

* Model Types define:
  * a method of loading a model file
  * how many texture slots they have
* Texture Types define:
  * a texture path
  * how many tint slots the texture has.
* Models are a collection of:
  * one model type
  * a list of texture types, equal in size to the texture slots
* Textures are a collection of:
  * one texture type
  * a color palette, equal in size to the tint slots of the texture

Thinking about changing it to:

* Models are a collection of:
  * a model (item model or GLTF model)
  * a collection of textures
  * a collection of color palettes, one for each texture
* Model presets simply define the default options for a model.
* Ditch special data structures for textures, define them by their identifier.

Why the complexity?

* The idea behind divorcing textures from the model is that, as the player, you could apply literally any texture in the game to yourself. 
* For example: stone wings, using the vanilla stone texture on the membrane part.
* To make this look good, there has to be some kind of overlay texture (the wing's bones, in this case), so models have to support more than one texture slot at a time.
* Each texture needs its own palette, so you can tint the bones without tinting the membrane between them.
* Additionally there are different methods of interpreting a texture:
  * most vanilla textures have one channel
  * textures in Tails have three channels (kinda like a normal map, it uses r/g/b channels to mean "strength of tint 1/2/3")
  * textures in appendages may have an unbounded amount of channels!
  * either way, the UI should show the appropriate number of channels for each texture
  
The metatexture problem: I was also thinking textures could sometimes be more complicated than a single .png file. Like if you have some kind of five-channel texture, it would make sense to author those as five separate B&W png files, but it also makes sense to treat them as a single unit in-game and in-json.

However I do think the current modelling of this system is too complex. If the number of color channels per texture is limited to three so the r/g/b channel trick from Tails works, and if one texture always maps to one .png file on-disk, the simplified version will work just fine.

There are some "type safety" concerns, though, like defining too many (or not enough) textures for a given model. Idk. It's a tricky problem.