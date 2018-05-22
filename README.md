# Fusion Engine
3D LWJGL Game Engine Based on Optimization and Customization.

Fusion Engine is currently developed and maintained by StepperDox, and
was variantly based on ThinMatrix's YouTube tutorials. However it is now
being developed and furthered by only one person. 

# LOD System
Fusion Engine uses a memory based LOD system. It is very efficient, but
memory expensive. Every object supports up to five LODs, and each LOD
is managed by a main LOD thread.

# Detail Mesh System
Normal Maps are only active within 30 units of the main camera. Grass
and other detail meshes are only rendered within 100 units of the main
camera. All detail meshes, particles, and alike objects use instanced
rendering for optimization.

# World / Asset Editor
A world/asset editor is currently being developed for easy customization
of in-game worlds, assets, particles, characters, textures, models, etc.
