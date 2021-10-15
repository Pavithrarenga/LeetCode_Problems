/* Question: Given an n x n binary matrix grid, return the length of the shortest clear path in the matrix.
If there is no clear path, return -1.

A clear path in a binary matrix is a path from the top-left cell (i.e., (0, 0)) to the bottom-right
cell (i.e., (n - 1, n - 1)) such that:

All the visited cells of the path are 0.
All the adjacent cells of the path are 8-directionally connected (i.e.,
they are different and they share an edge or a corner).
The length of a clear path is the number of visited cells of this path.

*/

/* Solution Approach: Solving using BFS
 1. Given an N*N matrix, the value 0 means that you can walk, and the walking direction of the path can
 be any adjacent position, that is, a total of eight directions.
 2. Check whether there is a path from the upper left corner to the lower right corner,
 if there is the shortest path length, then it is the output.
 3. And to check whether the path exists or not in the current direction.
 */

import java.util.LinkedList;
import java.util.Queue;

public class ShortestPathInBinaryMatrix {
    public static int shortestPathBinaryMatrix(int[][] grid) {
        if (grid == null || grid.length == 0 || grid[0][0] == 1) {
            return -1;
        }

        int n = grid.length;
        Queue<int[]> queue = new LinkedList<>();
        int[][] paths = {{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}};
        queue.offer(new int[]{0, 0});
        int step = 1;   // Starting point is also counted as one step, so it is initialized to 1
        int[] curr = {0, 0};

        while (!queue.isEmpty()) {
            int size = queue.size();
            for (int i = 0; i < size; i++) {
                curr = queue.poll();
                if (curr[0] == n - 1 && curr[1] == n - 1) {
                    return step;
                }

                for (int[] dir : paths) {
                    int nextR = curr[0] + dir[0], nextC = curr[1] + dir[1];
                    if (nextR < 0 || nextR >= n || nextC < 0 || nextC >= n || grid[nextR][nextC] != 0) {
                        continue;
                    }
                    queue.offer(new int[]{nextR, nextC});
                    grid[nextR][nextC] = 2;
                }
            }
            step++;
        }
        // If I reach here, it means that there's no path exists
        return -1;
    }

    public static void main(String[] args) {
        System.out.println(shortestPathBinaryMatrix(new int[][]{{0, 0, 0}, {1, 1, 0}, {1, 1, 0}}));
    }
}
