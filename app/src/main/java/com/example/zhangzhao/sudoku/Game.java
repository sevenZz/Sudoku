package com.example.zhangzhao.sudoku;

import android.util.Log;

/**
 * Created by zhangzhao on 2015/3/31.
 */
public class Game {
    private final String str = "360000000004230800000004200"
            + "070460003820000014500013020"
            + "001900000007048300000000045";

    private int[] sudoku = new int[9*9];

    //用于存储每个单元格已经不可用的数字
    private int used[][][] = new int[9][9][];

    public Game(){
        sudoku = fromPuzzleString(str);
        calculateAllUsedTiles();
    }

    //根据九宫格当中的坐标，返回该坐标里应该填写的数字
    private int getTile(int x, int y){
        return sudoku[y*9 + x];
    }

    public String getTileString(int x, int y){
        int v = getTile(x,y);
        if(v == 0){
            return "";
        }else{
            return String.valueOf(v);
        }
    }

    protected int[] fromPuzzleString(String str){
        int[] temp = new int[str.length()];
        for(int i = 0; i < temp.length; i++){
            temp[i] = str.charAt(i) - '0';
        }
        return temp;
    }

    //计算每一个格子中不能填写的数字
    public int[] calculateUsedTiles(int x, int y){
        int c[] = new int[9];
        //循环y轴，从上到下判断每个格子中的数字，不等于零就存下来
        for (int i = 0; i < 9; i++){
            if(y == i)
                continue;
            int t = getTile(x, i);
            if(t != 0){
                c[t-1] = t;
            }
        }
        //循环x轴，从左到右判断格子中数字
        for (int i = 0; i < 9; i++){
            if (x == i)
                continue;
            int t = getTile(i, y);
            if (t != 0){
                c[t-1] = t;
            }
        }

        int startX = (x/3)*3;
        int startY = (y/3)*3;
        for (int i = 0; i < 3; i++){
            for (int j = 0; j < 3; j++){
                if ((startX + i == x) && (startY + j == y))
                    continue;
                int t = getTile(startX + i, startY + j);
                if (t != 0){
                    c[t-1] = t;
                }
            }
        }
        //conpress
        int nused = 0;
        for (int t:c){
            if (t != 0)
                nused++;
        }
        int c1[] = new int[nused];
        nused = 0;
        for (int t:c){
            if(t != 0)
                c1[nused++] = t;
        }

        return c1;
    }

    //计算所有格子各自不能填写的数字
    public void calculateAllUsedTiles(){
       for (int x = 0; x < 9; x++){
           for (int y = 0; y < 9; y++){
               used[x][y] = calculateUsedTiles(x, y);
           }
       }
    }

    public int[] getUsedTilesByCoor(int x, int y){
        return used[x][y];
    }
}
