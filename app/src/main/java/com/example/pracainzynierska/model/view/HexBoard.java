package com.example.pracainzynierska.model.view;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.example.pracainzynierska.model.Hex;

import java.util.ArrayList;
import java.util.List;

public class HexBoard extends View {
    public HexBoard(Context context) {
        super(context);
    }

    public HexBoard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HexBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public HexBoard(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    float a = (float) (2 * Math.PI / 6);
    float r = (float) getScreenHeight() / 10;

    float sW = (float) getScreenWidth() / 2;
    float sH = (float) getScreenHeight() / 10;

    float[] rzad = {
            (float) getScreenHeight() / 10,
            sH + r * (float) Math.sin(a),
            sH + 2 * r * (float) Math.sin(a),
            sH + 3 * r * (float) Math.sin(a),
            sH + 4 * r * (float) Math.sin(a),
            sH + 5 * r * (float) Math.sin(a),
            sH + 6 * r * (float) Math.sin(a),
            sH + 7 * r * (float) Math.sin(a),
            sH + 8 * r * (float) Math.sin(a),
            sH + 9 * r * (float) Math.sin(a)
    };
    float[] kolumna = {
            sW - 6 * r * (float) Math.cos(a),
            sW - r - r * (float) Math.cos(a),
            (float) getScreenWidth() / 2,
            sW + r + r * (float) Math.cos(a),
            sW + 6 * r * (float) Math.cos(a)};


    Hex hex1 = new Hex(kolumna[2], rzad[0]);
    Hex hex2 = new Hex(kolumna[1], rzad[1]);
    Hex hex3 = new Hex(kolumna[2], rzad[2]);
    Hex hex4 = new Hex(kolumna[3], rzad[1]);
    Hex hex5 = new Hex(kolumna[0], rzad[2]);
    Hex hex6 = new Hex(kolumna[1], rzad[3]);
    Hex hex7 = new Hex(kolumna[2], rzad[4]);
    Hex hex8 = new Hex(kolumna[3], rzad[3]);
    Hex hex9 = new Hex(kolumna[4], rzad[2]);
    Hex hex10 = new Hex(kolumna[0], rzad[4]);
    Hex hex11 = new Hex(kolumna[1], rzad[5]);
    Hex hex12 = new Hex(kolumna[2], rzad[6]);
    Hex hex13 = new Hex(kolumna[3], rzad[5]);
    Hex hex14 = new Hex(kolumna[4], rzad[4]);
    Hex hex15 = new Hex(kolumna[0], rzad[6]);
    Hex hex16 = new Hex(kolumna[1], rzad[7]);
    Hex hex17 = new Hex(kolumna[2], rzad[8]);
    Hex hex18 = new Hex(kolumna[3], rzad[7]);
    Hex hex19 = new Hex(kolumna[4], rzad[6]);
    ArrayList<Hex> lista = new ArrayList<>(19);


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawHexagon(kolumna[2], rzad[0], canvas);
        drawHexagon(kolumna[1], rzad[1], canvas);
        drawHexagon(kolumna[3], rzad[1], canvas);
        drawHexagon(kolumna[0], rzad[2], canvas);
        drawHexagon(kolumna[2], rzad[2], canvas);
        drawHexagon(kolumna[4], rzad[2], canvas);
        drawHexagon(kolumna[1], rzad[3], canvas);
        drawHexagon(kolumna[3], rzad[3], canvas);
        drawHexagon(kolumna[0], rzad[4], canvas);
        drawHexagon(kolumna[2], rzad[4], canvas);
        drawHexagon(kolumna[4], rzad[4], canvas);
        drawHexagon(kolumna[1], rzad[5], canvas);
        drawHexagon(kolumna[3], rzad[5], canvas);
        drawHexagon(kolumna[0], rzad[6], canvas);
        drawHexagon(kolumna[2], rzad[6], canvas);
        drawHexagon(kolumna[4], rzad[6], canvas);
        drawHexagon(kolumna[1], rzad[7], canvas);
        drawHexagon(kolumna[3], rzad[7], canvas);
        drawHexagon(kolumna[2], rzad[8], canvas);

    }


    public void drawHexagon(float sW, float y, Canvas canvas) {
        Path path = new Path();
        path.moveTo(sW, y);
        for (int i = 0; i <= 6; i++) {
            path.lineTo(sW + r * (float) Math.cos(a * i), y + r * (float) Math.sin(a * i));
        }
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        canvas.drawPath(path, paint);
        drawsmallHexagon(sW, y, canvas);

    }

    public void drawsmallHexagon(float sW, float y, Canvas canvas) {
        Path path = new Path();
        path.moveTo(sW, y);
        for (int i = 0; i <= 6; i++) {
            path.lineTo(sW + r * 0.9f * (float) Math.cos(a * i), y + r * 0.8f * (float) Math.sin(a * i));
        }
        Paint paint = new Paint();
        paint.setColor(Color.rgb(217,135,92));
        canvas.drawPath(path, paint);

    }

    public int getScreenWidth() {
        return Resources.getSystem().getDisplayMetrics().widthPixels;
    }

    public int getScreenHeight() {
        return Resources.getSystem().getDisplayMetrics().heightPixels;
    }

    public List<Hex> pobierzKordy() {

        hex1.setId(0);
        lista.add(hex1);
        hex2.setId(1);
        lista.add(hex2);
        hex3.setId(2);
        lista.add(hex3);
        hex4.setId(3);
        lista.add(hex4);
        hex5.setId(4);
        lista.add(hex5);
        hex6.setId(5);
        lista.add(hex6);
        hex7.setId(6);
        lista.add(hex7);
        hex8.setId(7);
        lista.add(hex8);
        hex9.setId(8);
        lista.add(hex9);
        hex10.setId(9);
        lista.add(hex10);
        hex11.setId(10);
        lista.add(hex11);
        hex12.setId(11);
        lista.add(hex12);
        hex13.setId(12);
        lista.add(hex13);
        hex14.setId(13);
        lista.add(hex14);
        hex15.setId(14);
        lista.add(hex15);
        hex16.setId(15);
        lista.add(hex16);
        hex17.setId(16);
        lista.add(hex17);
        hex18.setId(17);
        lista.add(hex18);
        hex19.setId(18);
        lista.add(hex19);


        return lista;
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(width, height);

    }

}
