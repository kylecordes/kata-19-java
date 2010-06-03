PragProg Code Kata 19

Kyle Cordes's solution

Copyright 2003 Kyle Cordes

More info online:

http://kylecordes.com/2003/pragdave-kata-19

I've been watching PragDave's Code Katas with interest, since I am also in the habit of occassionally solve a small, standalone programming problem just for the learning experience. Today I worked out a solution for Kata 19, Word Chains. I did this Kata the "pure" way, i.e. without looking at any of the discussion of solution approaches before coding. Here are the results. I wrote the code this time in Java.

Unlike Dave's published solution, I didn't precalculate adjacency lists; rather I took the more brute-force approach of scanning the word list at each step looking for neighbor words. In spite of that non-optimization, this still calculates the word chains for a bunch of examples in a few seconds (total).

I was curious how much of the program's time is spent checking for neighbor words; this provided a good reason to try out a profiler plug for Eclipse, available on SourceForge. The profiler worked fine, and told me, as I would have guessed, that the program spends approximately 100% of it's time looking at words to decide if they are neighbors (in the word-chains sense). Of course the usual optimization advice applies: it would have been actively stupid of me to optimize without measuring where the slow parts are.

