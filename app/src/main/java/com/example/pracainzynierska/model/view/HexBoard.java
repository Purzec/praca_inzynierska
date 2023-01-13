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
import com.example.pracainzynierska.model.enums.Directions;

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
        hex2.setId(1);
        hex3.setId(2);
        hex10.setId(9);
        hex9.setId(8);
        hex8.setId(7);
        hex6.setId(5);
        hex5.setId(4);
        hex4.setId(3);
        hex19.setId(18);
        hex18.setId(17);
        hex17.setId(16);
        hex16.setId(15);
        hex15.setId(14);
        hex14.setId(13);
        hex13.setId(12);
        hex12.setId(11);
        hex11.setId(10);
        hex7.setId(6);

        hex1.addNeighbours(hex2, Directions.BACK_LEFT);
        hex1.addNeighbours(hex3, Directions.BACK);
        hex1.addNeighbours(hex4, Directions.BACK_RIGHT);
        lista.add(hex1);
        hex2.addNeighbours(hex5,Directions.BACK_LEFT);
        hex2.addNeighbours(hex6,Directions.BACK);
        hex2.addNeighbours(hex3,Directions.BACK_RIGHT);
        hex2.addNeighbours(hex1,Directions.FORWARD_RIGHT);
        lista.add(hex2);
        hex3.addNeighbours(hex1,Directions.FORWARD);
        hex3.addNeighbours(hex4,Directions.FORWARD_RIGHT);
        hex3.addNeighbours(hex8,Directions.BACK_RIGHT);
        hex3.addNeighbours(hex7,Directions.BACK);
        hex3.addNeighbours(hex6,Directions.BACK_LEFT);
        hex3.addNeighbours(hex2,Directions.FORWARD_LEFT);
        lista.add(hex3);

        hex4.addNeighbours(hex1,Directions.FORWARD_LEFT);
        hex4.addNeighbours(hex9,Directions.BACK_RIGHT);
        hex4.addNeighbours(hex8,Directions.BACK);
        hex4.addNeighbours(hex3,Directions.BACK_LEFT);
        lista.add(hex4);

        hex5.addNeighbours(hex2,Directions.FORWARD_RIGHT);
        hex5.addNeighbours(hex6,Directions.BACK_RIGHT);
        hex5.addNeighbours(hex10,Directions.BACK);
        lista.add(hex5);

        hex6.addNeighbours(hex2,Directions.FORWARD);
        hex6.addNeighbours(hex3,Directions.FORWARD_RIGHT);
        hex6.addNeighbours(hex7,Directions.BACK_RIGHT);
        hex6.addNeighbours(hex11,Directions.BACK);
        hex6.addNeighbours(hex10,Directions.BACK_LEFT);
        hex6.addNeighbours(hex5,Directions.FORWARD_LEFT);
        lista.add(hex6);

        hex7.addNeighbours(hex3,Directions.FORWARD);
        hex7.addNeighbours(hex8,Directions.FORWARD_RIGHT);
        hex7.addNeighbours(hex13,Directions.BACK_RIGHT);
        hex7.addNeighbours(hex12,Directions.BACK);
        hex7.addNeighbours(hex11,Directions.BACK_LEFT);
        hex7.addNeighbours(hex6,Directions.FORWARD_LEFT);
        lista.add(hex7);

        hex8.addNeighbours(hex4,Directions.FORWARD);
        hex8.addNeighbours(hex9,Directions.FORWARD_RIGHT);
        hex8.addNeighbours(hex14,Directions.BACK_RIGHT);
        hex8.addNeighbours(hex13,Directions.BACK);
        hex8.addNeighbours(hex7,Directions.BACK_LEFT);
        hex8.addNeighbours(hex3,Directions.FORWARD_LEFT);
        lista.add(hex8);

        hex9.addNeighbours(hex4,Directions.FORWARD_LEFT);
        hex9.addNeighbours(hex8,Directions.BACK_LEFT);
        hex9.addNeighbours(hex14,Directions.BACK);
        lista.add(hex9);

        hex10.addNeighbours(hex5,Directions.FORWARD);
        hex10.addNeighbours(hex6,Directions.FORWARD_RIGHT);
        hex10.addNeighbours(hex11,Directions.BACK_RIGHT);
        hex10.addNeighbours(hex15,Directions.BACK);
        lista.add(hex10);

        hex11.addNeighbours(hex6,Directions.FORWARD);
        hex11.addNeighbours(hex7,Directions.FORWARD_RIGHT);
        hex11.addNeighbours(hex12,Directions.BACK_RIGHT);
        hex11.addNeighbours(hex16,Directions.BACK);
        hex11.addNeighbours(hex15,Directions.BACK_LEFT);
        hex11.addNeighbours(hex10,Directions.FORWARD_LEFT);
        lista.add(hex11);

        hex12.addNeighbours(hex7,Directions.FORWARD);
        hex12.addNeighbours(hex13,Directions.FORWARD_RIGHT);
        hex12.addNeighbours(hex18,Directions.BACK_RIGHT);
        hex12.addNeighbours(hex17,Directions.BACK);
        hex12.addNeighbours(hex16,Directions.BACK_LEFT);
        hex12.addNeighbours(hex11,Directions.FORWARD_LEFT);
        lista.add(hex12);

        hex13.addNeighbours(hex8,Directions.FORWARD);
        hex13.addNeighbours(hex14,Directions.FORWARD_RIGHT);
        hex13.addNeighbours(hex19,Directions.BACK_RIGHT);
        hex13.addNeighbours(hex18,Directions.BACK);
        hex13.addNeighbours(hex12,Directions.BACK_LEFT);
        hex13.addNeighbours(hex7,Directions.FORWARD_LEFT);
        lista.add(hex13);

        hex14.addNeighbours(hex9,Directions.FORWARD);
        hex14.addNeighbours(hex8,Directions.FORWARD_LEFT);
        hex14.addNeighbours(hex13,Directions.BACK_LEFT);
        hex14.addNeighbours(hex19,Directions.BACK);
        lista.add(hex14);

        hex15.addNeighbours(hex10,Directions.FORWARD);
        hex15.addNeighbours(hex11,Directions.FORWARD_RIGHT);
        hex15.addNeighbours(hex16,Directions.BACK_RIGHT);
        lista.add(hex15);

        hex16.addNeighbours(hex15,Directions.FORWARD_LEFT);
        hex16.addNeighbours(hex11,Directions.FORWARD);
        hex16.addNeighbours(hex12,Directions.FORWARD_RIGHT);
        hex16.addNeighbours(hex17,Directions.BACK_RIGHT);
        lista.add(hex16);

        hex17.addNeighbours(hex16,Directions.FORWARD_LEFT);
        hex17.addNeighbours(hex12,Directions.FORWARD);
        hex17.addNeighbours(hex18,Directions.FORWARD_RIGHT);
        lista.add(hex17);

        hex18.addNeighbours(hex13,Directions.FORWARD);
        hex18.addNeighbours(hex19,Directions.FORWARD_RIGHT);
        hex18.addNeighbours(hex12,Directions.FORWARD_LEFT);
        hex18.addNeighbours(hex17,Directions.BACK_LEFT);
        lista.add(hex18);
        hex19.addNeighbours(hex14,Directions.FORWARD);
        hex19.addNeighbours(hex13,Directions.FORWARD_LEFT);
        hex19.addNeighbours(hex18,Directions.BACK_LEFT);

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
