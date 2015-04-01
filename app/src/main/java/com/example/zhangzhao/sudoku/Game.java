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

    public Game(){
        sudoku = fromPuzzleString(str);
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
}
