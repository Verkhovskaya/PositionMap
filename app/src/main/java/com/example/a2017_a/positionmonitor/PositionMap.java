package com.example.a2017_a.positionmonitor;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


class PositionMap extends View {

    private final int paintColor = Color.BLACK;
    private Paint drawPaint;
    Context context;

    int frameSizeX = 300;
    int frameSizeY = 300;
    int circleSize = 30;

    AFile todayFile;
    JSONObject currentDayJSON;
    double[] todayLon;
    double[] todayLat;
    int[] todayHour;
    int[] todayMin;
    int[] todaySec;
    int x;
    int y;

    public PositionMap(Context context, AttributeSet attrs) {
        super(context, attrs);
        setFocusable(true);
        setFocusableInTouchMode(true);
        setupPaint();
        this.context = context;
    }


    private void setupPaint() {
        drawPaint = new Paint();
        drawPaint.setColor(paintColor);
        drawPaint.setAntiAlias(true);
        drawPaint.setStrokeWidth(5);
        drawPaint.setStyle(Paint.Style.STROKE);
        drawPaint.setStrokeJoin(Paint.Join.ROUND);
        drawPaint.setStrokeCap(Paint.Cap.ROUND);
    }

    int[] toIntArray(JSONArray jsonArray) {
        Log.d("A", jsonArray.toString());
        int[] newArray = new int[jsonArray.length()];
        for (int i = 0; i<jsonArray.length(); i++) {
            try{newArray[i] = jsonArray.getInt(i);}
            catch (Exception e) {
                Log.e("A", "Issue in toIntArray");
            }
        }
        return newArray;
    }

    double[] toDoubleArray(JSONArray jsonArray) {
        Log.d("A", jsonArray.toString());
        double[] newArray = new double[jsonArray.length()];
        for (int i = 0; i<jsonArray.length(); i++) {
            try{newArray[i] = jsonArray.getDouble(i);}
            catch (Exception e) {
                Log.e("A", "Issue in toDoubleArray");
            }
        }
        return newArray;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Log.d("A", "Canvas is drawn");

        PositionWithTime todayTime = new PositionWithTime(1, 1);
        String todayString = String.valueOf(todayTime.year*10000+todayTime.month*100+todayTime.day);
        todayFile = new AFile(this.context, todayString.concat(".txt"));

        try {currentDayJSON = new JSONObject(todayFile.read());}

        catch (JSONException e) {
            Log.e("A", "JSON exception reading file");
            return;
        }

        try {
            todayLon = toDoubleArray(currentDayJSON.optJSONArray(todayString.concat("lon")));
            todayLat = toDoubleArray(currentDayJSON.optJSONArray(todayString.concat("lat")));
            todayHour = toIntArray(currentDayJSON.optJSONArray(todayString.concat("hour")));
            todayMin = toIntArray(currentDayJSON.optJSONArray(todayString.concat("min")));
            todaySec = toIntArray(currentDayJSON.optJSONArray(todayString.concat("sec")));

        } catch (Exception e) {
            Log.e("A", "Can't find arrays ".concat(e.toString()));
            return;
        }

        double minLon = todayLon[0];
        double maxLon = todayLon[0];
        double minLat = todayLat[0];
        double maxLat = todayLat[0];
        for (int j=1; j<todayLon.length; j++) {
            if (todayLon[j]<minLon) {minLon = todayLon[j];}
            if (todayLon[j]>maxLon) {maxLon = todayLon[j];}
            if (todayLon[j]<minLat) {minLat = todayLat[j];}
            if (todayLon[j]>maxLat) {maxLat = todayLat[j];}
        }

        double verticalDistance = (maxLat - minLat + 0.0001)*1.2;
        double horizontalDistance = (maxLon - minLon + 0.0001)*1.2;

        for (int i = 0; i < todayLon.length; i++) {
            x = (int) ((todayLon[i]-minLon)*frameSizeX/horizontalDistance);
            y = (int) ((todayLat[i]-minLat)*frameSizeY/verticalDistance);
            Log.d("A", "Circle at ".concat(String.valueOf(x)).concat(" ").concat(String.valueOf(y)));
            drawPaint.setColor(Color.BLUE);
            canvas.drawCircle(x+50, y+50, circleSize, drawPaint);
        }
    }
}
