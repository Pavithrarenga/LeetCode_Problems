/* Question:
You are given an array books where books[i] = [thickness, height] indicates the thickness
and height of the ith book. You are also given an integer shelfWidth.

We want to place these books in order onto bookcase shelves that have a total width shelfWidth.

We choose some of the books to place on this shelf such that the sum of their thickness is less than or
equal to shelfWidth, then build another level of the shelf of the bookcase so that the total height of
the bookcase has increased by the maximum height of the books we just put down. We repeat this process until
there are no more books to place.

Note that at each step of the above process, the order of the books we place is the same order
as the given sequence of books.

For example, if we have an ordered list of 5 books, we might place the first and second book onto the first
shelf, the third book on the second shelf, and the fourth and fifth book on the last shelf.
Return the minimum possible height that the total bookshelf can be after placing shelves in this manner.

Input: books = [[1,1],[2,3],[2,3],[1,1],[1,1],[1,1],[1,2]], shelf_width = 4
Output: 6
Explanation:
The sum of the heights of the 3 shelves is 1 + 3 + 2 = 6.
Notice that book number 2 does not have to be on the first shelf.

 */

/* Approach to solve this problem:DFS + Memory Search
 1. Given several books, these books have thickness and height information respectively.
 2. These books are required to be arranged in layers, so that the sum of the
 widths of the books on the same layer shall not exceed the given limit shelf_width,
 3. The height of each floor of the bookshelf is the maximum height of all books on
 the current floor. What is the minimum total height of the bookshelf.

Therefore, to find the minimum total height:-
 1. Given a two-dimensional array, divide it into different SubArrays so that the sum of the widths
 of each SubArray does not exceed shelf_width,
 2. Find the minimum value of the sum of the highest height values of each SubArray.
 3. Considering that the position of each element cannot be changed, so the greedy + sorting approach cannot be used.
 4. Therefore, I thought of using DFS + Memory Search to solve it.

 * There are only two ways to put a book books[i]:
  1. Place it on the same layer as other books before
 (but it needs to meet the requirement that the sum of width does not exceed shelf_width)
  2. Put the current book on a new layer.
  3. That is, in DFS, there are two different options above.
  For this, we can choose the solution that makes the height of the bookshelf lower.
  4. In the DFS process, the information we need to maintain is: the index position of the current book,
  the highest height of the book on the current shelf of the bookshelf, and the remaining
  width of the current shelf of the bookshelf.
  5. When the position of the book and the remaining width are determined in the recursive process,
  the answer is certain (it is a question of no aftereffect)
  6. The termination condition is: traverse all books (index == books.length)

 */

public class FillingBookcaseShelves {
    static int[][] mem;

    public static int minHeightShelves(int[][] books, int shelf_width) {
        mem = new int[books.length][shelf_width + 1];
        return dfs(books, 0, 0, shelf_width, shelf_width);
    }

    private static int dfs(int[][] books, int index, int currentHeight, int leftWidth, int shelf_width) {
        if (index == books.length) {
            return currentHeight;
        }
        if (mem[index][leftWidth] != 0) {
            return mem[index][leftWidth];
        }

        // Place the current book on a new layer below
        mem[index][leftWidth] = currentHeight + dfs(books, index + 1, books[index][1],
                shelf_width - books[index][0], shelf_width);
        // If the remaining width of the current layer can fit the current book,
        // you can choose to put it on the same layer as the previous book
        if (leftWidth >= books[index][0]) {
            mem[index][leftWidth] = Math.min(mem[index][leftWidth], dfs(books, index + 1,
                    Math.max(currentHeight, books[index][1]), leftWidth - books[index][0], shelf_width));
        }
        return mem[index][leftWidth];
    }

    public static void main(String[] args) {
        System.out.println(minHeightShelves(new int[][]{{1, 1}, {2, 3}, {2, 3}, {1, 1}, {1, 1}, {1, 1}, {1, 2}}, 4));
    }
}
