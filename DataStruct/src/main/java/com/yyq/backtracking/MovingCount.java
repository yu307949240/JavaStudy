package com.yyq.backtracking;

/**
 * 机器人运动范围
 *
 * @author yyq
 * @since 2018/11/28
 */
public class MovingCount {
    private static final int[][] next = {{0,-1},{0,1},{-1,0},{1,0}};
    private int cnt = 0;
    private int threshold;
    private int rows;
    private int cols;
    private int[][] digitSum;

    private void initDigitSum(){
        int[] digit = new int[Math.max(rows,cols)];
        for(int i=0;i<digit.length;i++){
            int n = i;
            while(n > 0){
                digit[i] += n%10;
                n /= 10;
            }
        }

        for(int i = 0;i<rows;i++){
            for(int j=0;j<cols;j++){
                digitSum[i][j] = digit[i]+digit[j];
            }
        }
    }

    public  int movingCount(int threshold,int rows,int cols){
        this.threshold = threshold;
        this.rows = rows;
        this.cols = cols;
        initDigitSum();
        boolean[][] marked = new boolean[rows][cols];
        backtracking(marked,0,0);
        return cnt;
    }

    private void backtracking(boolean[][] marked, int r, int c) {
        if(marked[r][c] || r<0||r>rows||c<0||c>cols){
            return;
        }
        marked[r][c] = true;
        if(threshold < digitSum[r][c])
            return;
        cnt++;
        for(int[] n : next){
            backtracking(marked,r+n[0],c+n[1]);
        }
    }
}
