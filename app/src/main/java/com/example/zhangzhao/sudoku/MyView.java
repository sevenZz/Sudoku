package com.example.zhangzhao.sudoku;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by zhangzhao on 2015/3/30.
 */
public class MyView extends View{

    //用于记录单元格的宽度和高度
    private float width;
    private float height;

    private Game game = new Game();
    public MyView(Context context) {
        super(context);
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        //计算当前单元格的宽度和高度
        this.width = w / 9f;
        this.height = h / 9f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint backgroundPaint = new Paint();
        backgroundPaint.setColor(getResources().getColor(R.color.sudoku_background));
        canvas.drawRect(0, 0, getWidth(), getHeight(), backgroundPaint);

        Paint darkPaint = new Paint();
        darkPaint.setColor(getResources().getColor(R.color.sudoku_dark));

        Paint hilitePaint = new Paint();
        hilitePaint.setColor(getResources().getColor(R.color.sudoku_hilite));

        Paint lightPaint = new Paint();
        lightPaint.setColor(getResources().getColor(R.color.sudoku_light));
        for(int i = 0; i < 9; i++){
            //以下四行，画横纵各8条线。先画浅色，再挨着画深一点，达到立体效果。
            canvas.drawLine(0, i*height, getWidth(), i*height, lightPaint);
            canvas.drawLine(0, i*height+1, getWidth(), i*height+1,hilitePaint);
            canvas.drawLine(i*width, 0, i*width, getHeight(), lightPaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),hilitePaint);
            if (i % 3 == 0){
                canvas.drawLine(0, i*height, getWidth(), i*height, lightPaint);
                canvas.drawLine(0, i*height + 1, getWidth(), i*height + 1, darkPaint);
                canvas.drawLine(i*width, 0, i*width, getHeight(),lightPaint);
                canvas.drawLine(i*width + 1, 0, i*width + 1, getHeight(),darkPaint);
            }
        }


        //画数字
        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.STROKE);
        numberPaint.setTextSize(height*0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);
        Paint.FontMetrics fm = numberPaint.getFontMetrics();
        float x = width/2;

        float y = height/2 - (fm.ascent + fm.descent)/2;

        for (int i = 0; i < 9; i++){
            for(int j = 0; j < 9; j++){
                canvas.drawText(game.getTileString(i, j), i*width + x, j*height + y, numberPaint);
            }
        }
    }


    public boolean onTouchEvent(MotionEvent event){
        if (event.getAction() != MotionEvent.ACTION_DOWN){
            return super.onTouchEvent(event);
        }

        int selectedX = (int)(event.getX() / width);
        int selectedY = (int)(event.getY() / height);

        int used[] = game.getUsedTilesByCoor(selectedX, selectedY);
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < used.length; i++){
            sb.append(used[i]);
        }

//        LayoutInflater layoutInflater = LayoutInflater.from(this.getContext());
//        View layoutView = layoutInflater.inflate(R.layout.dialog, null);
//        TextView textView = (TextView)layoutView.findViewById(R.id.usedTextId);
//        textView.setText(sb.toString());
//        AlertDialog.Builder builder = new AlertDialog.Builder(this.getContext());
//        builder.setView(layoutView);
////        AlertDialog dialog = builder.create();
////        dialog.show();
//        builder.create().show();
        KeyDialog keyDialog = new KeyDialog(this.getContext(), used);
        keyDialog.show();

        return true;
    }



}
