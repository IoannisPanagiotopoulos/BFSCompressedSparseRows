# BFSCompressedSparseRows
This is a command line programm where you can insert .txt files with loaded graphs from https://snap.stanford.edu 
and run a simple Breatdh First Search traversal algorithm.


We use the Compressed Sparse Rows(https://en.wikipedia.org/wiki/Sparse_matrix) method to load the graph in our programm 
,which is a type of Sparse Matrix where we can exclude the zero values of an adjacent matrix due to the size of the given files.


As for the BFS traversal algorithm,our goal is to find if there is any path between two nodes in the 
graph(the ones that are given as an  input by the user).The final output is the existence of the path(true/false),the certain
path which was followed from the source node to the destination node and the duration (in milliseconds) of the BFS Traversal 
from source to destination.

Guide to execute this programm:

