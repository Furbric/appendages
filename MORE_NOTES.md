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

* Models are a collection of:
  * a model reference (item model, GLTF model, something else?)
  * a collection of textures for how to texture it
* Model presets define the default options for a model.
* Textures are:
  * an Identifier for which texture to use
  * a color palette for how to tint it

Current problems:

* ModelIdentifiers, ironically, do not make good model identifiers
  * A ModelIdentifier is not enough information to render an item model, for example
* wrt. rendering item models on yourself, Mojang has a different idea of models
  * Textures and models are not different things.
  * This makes sense, you can't really retexture an item sensibly
* I haven't even thought about how gltf models play in to the system

It probably doesn't make sense to keep thinking about "how can I make the system flexible enough to render gltf models and also item models", when I a) don't have gltf support in, b) don't know what it looks like, and c) don't think people will actually render item models on themself very much.

Putting item models as an appendage is kind of out-of-scope, right? It's supposed to be a mod for furries, most people don't have a breadsona. The only purposes I can think of are novelty haha there's a compass on my head now, and that's not a great reason to complicate the entire dang system.

TODO: take a look at Tails's GLTF loader.