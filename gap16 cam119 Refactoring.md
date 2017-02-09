# Gideon Pfeffer and Christian Martindale

## Changes

### Refactor 1

We noticed that all of the models had height and width instance variables. So we added them to the super and only called them once. 

### Refactor 2

We also considered creating a sueprclass for two classes but decided against it because it was adding a new class for 2 lines. 

### Refactor 3

We added a new tester super to make the scene for the testers so that we wouldnt have to repeat setting the scene. 
