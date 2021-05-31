package org.ddu.algorithm.amazon;

public class SolutionNumIslands {

  /**
   * 200. 岛屿数量 给定一个由 '1'（陆地）和 '0'（水）组成的的二维网格，计算岛屿的数量。
   * 一个岛被水包围，并且它是通过水平方向或垂直方向上相邻的陆地连接而成的。
   * 你可以假设网格的四个边均被水包围。
   *
   * 输入: 11000 11000 00100 00011
   *
   * 输出: 3
   */
  public int numIslands(char[][] grid) {
    if (grid == null || grid.length == 0) {
      return 0;
    }

    int num = 0;

    int rows = grid.length;
    int cols = grid[0].length;

    for (int i = 0; i < rows; i++) {
      for (int j = 0; j < cols; j++) {
        if (grid[i][j] == '1') {
          num++;
          numIslands(grid, i, j);
        }
      }
    }
    return num;
  }

  private void numIslands(char[][] grid, int row, int col) {
    if (row >= 0 && row <= grid.length - 1 && col >= 0 && col <= grid[0].length - 1) {
      if (grid[row][col] != '0') {
        grid[row][col] = '0';

        numIslands(grid, row + 1, col);
        numIslands(grid, row - 1, col);
        numIslands(grid, row, col + 1);
        numIslands(grid, row, col - 1);
      }
    }
  }


  public static void main(String[] args) {
    SolutionNumIslands solutions = new SolutionNumIslands();

    char[][] grid = {
        {'1', '1', '0', '0', '0'},
        {'1', '1', '0', '0', '0'},
        {'0', '0', '1', '0', '0'},
        {'0', '0', '0', '1', '1'}
    };

    int num = solutions.numIslands(grid);
    System.out.println(num);
  }

}
