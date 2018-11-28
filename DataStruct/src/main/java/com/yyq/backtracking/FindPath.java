package com.yyq.backtracking;

/**
 * 矩阵寻路算法；回溯法
 *
 * @author yyq
 * @since 2018/11/28
 */
public class FindPath {
    final int[][] next = {{0, 1}, {0, -1}, {1, 0}, {-1, 0}};
    int rows;
    int cols;

    public boolean hasPath(char[][] matrix, String str) {
        rows = matrix.length;
        cols = matrix[0].length;
        boolean[][] marked = new boolean[rows][cols];
        for (int i = 0; i < rows; i++) {
            for (int j = 0; i < cols; j++) {
                if (backtracking(matrix, marked, str, 0, i, j)) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean backtracking(char[][] matrix, boolean[][] marked, String str, int pathLen, int r, int c) {
        if (str.length() == pathLen) {
            return true;
        }
        if (r < 0 || r > rows || c < 0 || c > cols || str.charAt(pathLen) != matrix[r][c] || marked[r][c])
            return false;
        marked[r][c] = true;
        for (int[] n : next) {
            if (backtracking(matrix, marked, str, pathLen++, r + n[0], c + n[1])) {
                return true;
            }
        }
        marked[r][c] = false;
        return false;
    }


}
