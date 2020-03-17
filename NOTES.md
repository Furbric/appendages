# Structure

* Different approach to appendages.
* Original mod:
  * 4 appendage slots (tail, wings, ears, snout)
  * Each appendage has one fixed location, size, and texture (okay, sometimes two), and a pool of models to choose from
  * You either have an appendage of a given slot or you don't (imagine Map<BodyPart, Appendage>)
* Proposal:
  * Appendage types (analogous to `Item`) and appendages (analogous to `ItemStack`)
  * You make an appendage of a given type, then attach it to a specific part of your body, give it an offset, scale, and texture etc.
  * Players hold an unstructured, unsized list of these appendages (or, "configured appendage types")

Downsides:

* harder to serialize
* backwards-incompatible
* lets you do cursed things like make floating body parts or put wings on your head
* harder to design UI around so you *don't* end up making cursed shit on accident

Upsides:

* It's just more flexible in general: it doesn't care about ears and snouts being different, for example, because they're both things that attach to your head.
* That makes it easier to generalize to other things like horns and talons.
* I've also seen a few sonas with wings in slightly "non-standard" locations as well, which this should be able to accommodate.
* And finally, helps fix the issue where you want to use a snout but your player skin has its nose in a slightly different spot, so it looks weird.

# Symmetry

Breaking up appendages into more granular pieces (sorry, wording) naturally begs questions like... should you be able to add wings individually and have control over the position/color/scale of each one? I don't see any reason not to, except symmetrical ones are by far the most popular type, and the UI seems like a nightmare

# UI

Here are my Tails UI whines I'd like to address:

* It's hard to stick with a color scheme. If you want to create a character with multiple appendages with the same color set, you have to copy and paste the RGB hex codes one at a time. A "color palette" system would be nice here.
* The color picker isn't great. The HSV picker especially seems to... round back and forth with the RGB sliders, or something like that (dragging the saturation/value sliders bump the hue around a bit)? It's not super nice to use.
* It would be nice to have everything within reach on the same side of the screen.
* You don't have much camera control and it would be nice to ogle yourself from a few more angles - zoom, pan (oh man, is this gonna be a can of worms or what)

I'm changing the appendages model anyways, so the UI will have to change with it. Probably have an accordion on the left for configuring each appendage, have some kind of "library" to add new ones from.

# Mount Points and Body Parts

Appendages are attached to mount points, mount points are attached to body parts. 

There are six body parts (head, torso, L/R arm, L/R leg) corresponding to the six cuboids on a player model.

Each body part has at least seven mount points: center/top/bottom/left/right/front/back. Extra points as needed for UI convenience (probably a snout point for the head, bum point for tails)

How to translate to draw an appendage:

* First, translate to the center of the body part. Rotate to align with it.
* Then, translate and rotate according to the directions given by the mount point (hardcoded into the mod)
* Then, translate, rotate, and scale with the user-defined offsets from that mount point.

Note from trying to implement this: it's not possible to hardcode offsets from cuboid origin -> mount point, mainly because player models have two possibilities for arm sizes, totally forgot about that. More likely I'll need to write a method on the mount point that takes in a cuboid and does the transformations for you.

# Notes.md "At Work With Nothing Better To Do" edition

ok so basically i am derg

## Symmetry, again

* Models only include one wing, one ear, one horn etc
* There's a "symmetry" option, checked by default on items where it makes sense, that adds a mirrored duplicate version
* Changes made to the original model reflect across the vertical axis
* UI option to enable symmetry when it's not
* UI option to disable symmetry when it is - two choices, remove the mirrored element or make it an actual duplicate object (flattening)

After trying to implement this: Symmetry should probably remain a UI concept and not an appendage concept (apart from something basic like apps having a logical "parent"). Things get hairy when trying to make getters and setters transparently update the mirror image, for example

## Textures

How many color channels are available for tinting is a property of the texture

Should you be able to choose textures not intended for use on an appendage, like the stone texture? Probably not. if you can that's low priority af

Think about this more.

## Models

There's the crazy model format that Tails uses, that appears to have animation support etc.

Would also be nice to support block and item models. Probably not too difficult to, useful for testing as well.

## All the configurable options

For each outfit:

* the color palette (just a list of rgbas)
* MAYYYYYBE an alternate skin (so you can use a different skin designed with the apps in mind)

For each appendage:

* position
    * mount point (body part + point)
    * positional offset
    * rotational offset
    * scale (x/y/z? or just a scalar)
    * bilateral symmetry checkbox
* texture
    * texture
    * colors (either adhoc rgbas, or references into the color palette)
* extras
  * animation speed?
  * hide under armor?
  * for wing-like things, hide when using elytra (ooo, or hide the elytra instead)
  * show when invisible?

App types come with default options specified in json, when you add an app it loads those options.
