import java.io.File;
import java.sql.Date;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Scanner;

public class Graph {
	public static ArrayList<Integer> checklist;
	static int count = 0;

	// Sorting algorithms
	static void quicksortvertex(ArrayList<Edge> arr, int l, int r) {
		if (l >= r) {
			return;
		}
		int pivot = arr.get(r).src;
		int cnt = l;
		Edge tmp = null;
		for (int i = l; i <= r; i++) {
			count++;
			if (arr.get(i).src <= pivot) {

				tmp = arr.get(cnt);
				arr.set(cnt, arr.get(i));
				arr.set(i, tmp);
				cnt++;
			}
		}
		try {
			quicksortvertex(arr, l, cnt - 2);
		} catch (java.lang.StackOverflowError e) {
			System.out.println(" cnt: " + cnt + " l" + l + "tmp: " + tmp + " arr[cnt] " + arr.get(cnt));
		}

		quicksortvertex(arr, cnt, r);
	}

	// Sorting algorithms
	static void quicksort(ArrayList<Integer> arr, int l, int r) {

		if (l >= r) {
			return;
		}

		int pivot = arr.get(r);

		int cnt = l;

		for (int i = l; i <= r; i++) {

			if (arr.get(i) <= pivot) {

				Integer tmp = arr.get(cnt);
				arr.set(cnt, arr.get(i));
				arr.set(i, tmp);

				cnt++;
			}
		}
		try {
			quicksort(arr, l, cnt - 2);
		} catch (java.lang.StackOverflowError e) {

		}
		try {
			quicksort(arr, cnt, r);
		} catch (java.lang.StackOverflowError e) {

		}

	}

	// prints BFS traversal from a given source s
	public static String isReachable(int s, int d, boolean visited[], LinkedList<Integer> adj[]) {
		LinkedList<Integer> temp;

		// Mark all the vertices as not visited(By default set
		// as false)

		// Create a queue for BFS
		LinkedList<Integer> queue = new LinkedList<Integer>();

		// Mark the current node as visited and enqueue it
		visited[s] = true;
		queue.add(s);

		// 'i' will be used to get all adjacent vertices of a vertex
		Iterator<Integer> i;
		int lengthofpath = 0;
		long timeMilliprev = System.currentTimeMillis();
		while (queue.size() != 0) {
			// Dequeue a vertex from queue and print it
			s = queue.poll();
			System.out.print(s + "=>");
			lengthofpath++;
			int n;
			i = adj[s].listIterator();

			// Get all adjacent vertices of the dequeued vertex s
			// If a adjacent has not been visited, then mark it
			// visited and enqueue it
			while (i.hasNext()) {
				n = i.next();

				// If this adjacent node is the destination node,
				// then return true
				if (n == d) {
					long timetobefound = System.currentTimeMillis() - timeMilliprev;
					System.out.println();
					return "Was found with path length " + lengthofpath + " in " + timetobefound + " milliseconds";
				}

				// Else, continue to do BFS
				if (!visited[n]) {
					visited[n] = true;
					queue.add(n);
				}
			}
		}

		// If BFS is complete without visited d
		return "Was not found";
	}

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ArrayList<Edge> edges = new ArrayList<>();
		ArrayList<Integer> sources = new ArrayList<>();
		ArrayList<Integer> targets = new ArrayList<>();
		/*
		 * Reading File
		 * 
		 * 
		 */
		System.out.println("Reading file");
		try {
			System.out.print("Enter the file name with extension : ");

			Scanner input = new Scanner(System.in);

			File file = new File(input.nextLine());

			input = new Scanner(file);
			int i = 0;
			int linenumber = 0;
			while (input.hasNextLine()) {
				linenumber++;
				String line = input.nextLine();
				String[] lines;
				// check if the scanner's next token is an int
				if (line.charAt(0) != '#') {
					lines = line.split("\t");
					if (lines.length > 2) {
						System.out.println("Wrong input form!");
						break;
					} else {
						Integer target = null;
						Integer source = null;
						for (int ln = 0; ln < lines.length; ln++) {
							if (ln == 0) {
								try {
									source = Integer.parseInt(lines[ln]);
									sources.add(source);
								} catch (NumberFormatException e) {
									System.out.println("In line: " + linenumber + " the source should be a number");
									System.out.println("See here. Source =>" + lines[0]);
									System.exit(0);
								}
							}
							if (ln == 1) {
								try {
									target = Integer.parseInt(lines[ln]);
									targets.add(target);
								} catch (NumberFormatException e) {
									System.out.println("In line: " + linenumber + " the target should be a number");
									System.out.println("See here. Target =>" + lines[1]);
									System.exit(0);
								}
							}

						}
						edges.add(new Edge(source, target));

					}
				} else {
					System.out.println("Not vital information");
				}
			}
			input.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		/*
		 * Reading file
		 * 
		 * 
		 * 
		 */
		System.out.println("Sorting edges!");
		quicksortvertex(edges, 0, edges.size() - 1);
		int firstsrc = edges.get(0).src;
		// System.out.println(firstsrc);
		int counter = 0;
		checklist = new ArrayList<>();
		ArrayList<Integer> nnz = new ArrayList<>();//degrees of every node,or their non zero value count for every row
		ArrayList<Integer> nonzero = new ArrayList<>();//sources of non zero values
		nnz.add(0);
		// nonzero.add(firstsrc);
		int tot = 0;
		int last = 0;
		for (int i = 0; i < edges.size(); i++) {
			// System.out.println(edges.get(i).src+"=>"+edges.get(i).target);
			if (firstsrc != edges.get(i).src) {
				// System.out.println(firstsrc);
				nnz.add(counter);
				firstsrc = edges.get(i).src;
				nonzero.add(edges.get(i).src);
				tot += counter;
				counter = 1;
			} else if (i == edges.size() - 1) {
				last = i;
				tot += counter;
			} else {
				counter++;
			}

			if (!checklist.contains(targets.get(i))) {
				checklist.add(targets.get(i));
			}

			if (!checklist.contains(sources.get(i))) {
				checklist.add(sources.get(i));
			}

		}
		nonzero.add(edges.get(last).src);
		nnz.add(counter);
		System.out.println("Sorting values!");
		quicksort(checklist, 0, checklist.size() - 1);
		ArrayList<Integer> values = new ArrayList<Integer>();
		for (int distng = 0; distng <= checklist.get(checklist.size() - 1); distng++) {
			if (checklist.contains(distng)) {
				values.add(distng);
			} else {
				values.add(-1); // non existent node
			}
		}
		System.out.println("Iterating graph");
		int iteration = 0;
		LinkedList<Integer> adj[];
		adj = new LinkedList[values.size()];
		for (int i = 0; i < values.size(); ++i) {
			adj[i] = new LinkedList();
		}
		for (int i = 0; i < nonzero.size(); i++) {
			//System.out.println("number of degress for src" + edges.get(iteration).src + ":" + nnz.get(i));
			iteration += nnz.get(i+1);
			// System.out.println("For row"+nonzero.get(i));
			for (int row = iteration - nnz.get(i+1); row < iteration; row++) {
				adj[nonzero.get(i)].add(edges.get(row).target);
			}
		}
		Iterator<Integer> i;
		boolean visited[] = new boolean[values.size()];
		for (int in = 0; in < values.size(); in++) {
			visited[in] = false;
		}
		int no1 = 0, no2 = 0;
		while (true) {
			Scanner userinput = new Scanner(System.in);
			System.out.println("Give us two nodes!(Positive numbers only)");
			System.out.println(
					"In case you want to exit,Press a negative number in the first or the second node selection");
			System.out.println("Node no:1");
			no1 = userinput.nextInt();
			if (no1 < 0) {
				break;
			}
			System.out.println("Node no:2");
			no2 = userinput.nextInt();
			if (no2 < 0) {
				break;
			}
			System.out.println(isReachable(no1, no2, visited, adj));
		}

	}
}
