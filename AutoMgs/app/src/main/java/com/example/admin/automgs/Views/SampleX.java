package com.example.admin.automgs.Views;

import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.text.Layout;
import android.text.StaticLayout;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ScrollView;

/**
 * Created by Admin on 10/17/2017.
 */

public class SampleX extends View {

    private int width = 0;
    private int height = 0;
    private float yCurrent = 5;
    private DisplayMetrics displayMetrics;
    private Context context;

    public SampleX(Context context) {
        super(context);
        this.context = context;
        setVerticalScrollBarEnabled(true);
    }

    public SampleX(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        setVerticalScrollBarEnabled(true);
    }

    private void setupValues() {
        width = getWidth();
        height = getHeight();
        displayMetrics = getResources().getDisplayMetrics();
    }

    private float getPixel(float unit) {
        return unit * (displayMetrics.densityDpi / 160f);
    }

    private float getTextHeight(String text, Paint paint) {
        Rect rect = new Rect();
        paint.getTextBounds(text, 0, text.length(), rect);
        return rect.height();
    }

    private void drawText(Canvas canvas, Rect bounds, String text, float textSize, String background, String textColor) {
        if (background == null)
            background = "#FFFFFF";
        else if (background.equals(""))
            background = "#FFFFFF";
        if (textColor == null)
            textColor = "#000000";
        else if (textColor.equals(""))
            textColor = "#000000";

        TextPaint textPaint = new TextPaint();
        textPaint.setColor(Color.parseColor(background));
        textPaint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(textPaint);

        textPaint.setColor(Color.parseColor(textColor));
        textPaint.setTextSize(getPixel(textSize));
        StaticLayout sl = new StaticLayout(text, textPaint, canvas.getWidth(), Layout.Alignment.ALIGN_CENTER, 1, 1, false);
        canvas.save();

        float textHeight = getTextHeight(text, textPaint);
        int numberOfTextLines = sl.getLineCount();
        float textYcoordinate = bounds.exactCenterY() - ((numberOfTextLines * textHeight) / 2);

        float textXCoordinate = bounds.left;

        yCurrent += textYcoordinate;
        canvas.translate(textXCoordinate, yCurrent);
        sl.draw(canvas);
        yCurrent += sl.getHeight();
        canvas.restore();
    }


    private void showTextOnCanvas(String stringContent, Canvas canvas, String background, String textColor, float textSize) {
        float stepUnit = 35;
        if (background == null)
            background = "#FFFFFF";
        else if (background.equals(""))
            background = "#FFFFFF";
        if (textColor == null)
            textColor = "#000000";
        else if (textColor.equals(""))
            textColor = "#000000";

        Paint paint = new Paint();
        paint.setColor(Color.parseColor(background));
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPaint(paint);

        float textSizeX = getPixel(textSize);
        paint.setColor(Color.parseColor(textColor));
        paint.setTextSize(textSizeX);

//        float allSizeText = stringContent.length() * textSizeX;
        Rect bounds = new Rect();
        String unit = "a";
        paint.getTextBounds(unit, 0, unit.length(), bounds);
        float textWidthx = bounds.width() + (int) (bounds.width() * 0.15);
        int charactersOnLine = (int) (canvas.getWidth() / textWidthx);
        int endIndex = charactersOnLine >= stringContent.length() ? stringContent.length() : charactersOnLine;
        String drawTexts = stringContent.substring(0, endIndex);
        while (!stringContent.equals(drawTexts)) {
            int step = 0;
            if (stringContent.charAt(endIndex) != ' ') {
                for (int j = 1; j < 3; j++) {
                    if (stringContent.charAt(endIndex + j) == ' ') {
                        drawTexts = stringContent.substring(0, endIndex + j);
                        step = j;
                        break;
                    }
                }

                if (step == 0) {
                    int j = 1;
                    while (stringContent.charAt(endIndex - j) != ' ') {
                        j += 1;
                    }
                    if (stringContent.charAt(endIndex - j) == ' ') {
                        drawTexts = stringContent.substring(0, endIndex - j);
                        step = j * (-1);
                    }
                }
            }
            canvas.drawText(drawTexts, 10, yCurrent + stepUnit, paint);
            stepUnit += 35;
            stringContent = stringContent.substring(charactersOnLine + step, stringContent.length());
            drawTexts = stringContent.substring(0, charactersOnLine >= stringContent.length() ? stringContent.length() : charactersOnLine);
        }
        canvas.drawText(drawTexts, 10, yCurrent + stepUnit, paint);
//        stepUnit += 35;
    }

    private void drawImageBitmap(Canvas canvas, Bitmap bitmap) {
        float bitmapWidth = bitmap.getWidth();
        float bitmapHeight = bitmap.getHeight();

        float pxWidth = width * 0.8f;
        float marginLeft = (width - pxWidth) / 2;

        float dtSize = pxWidth / bitmapWidth;
        bitmapHeight = bitmapHeight * dtSize;

        bitmap = Bitmap.createScaledBitmap(bitmap, (int) pxWidth, (int) bitmapHeight, true);

        canvas.drawBitmap(bitmap, marginLeft, yCurrent, null);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        setupValues();
        drawContent(canvas);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int parentWidth = MeasureSpec.getSize(widthMeasureSpec);
        int parentHeight;

        if (isLandscape()) {
            Rect r = new Rect();
            ((ScrollView) getParent()).getGlobalVisibleRect(r);
            parentHeight = r.bottom - r.top;
        } else {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            Display display = wm.getDefaultDisplay();
            parentHeight = display.getHeight();
        }

        this.setMeasuredDimension(parentWidth, parentHeight);
    }

    private boolean isLandscape() {
        if (getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE)
            return true;
        return false;
    }

    private void drawContent(Canvas canvas) {
//        String text = "It is a long established fact that a reader will be distracted by the readable content of a page when looking at its layout. The point of using Lorem Ipsum is that it has a more-or-less normal distribution of letters, as opposed to using 'Content here, content here', making it look like readable English.";
//        String text = "Simple Text xSample Sample Lorem Ipsum có ưu điểm hơn so với đoạn văn bản chỉ gồm nội dung kiểu";
        String text = "Lorem Ipsum có ưu điểm hơn so với đoạn văn bản chỉ gồm nội dung kiểu \"Nội dung, nội dung, nội dung\" là nó khiến văn bản giống thật hơn, bình thường hơn. Nhiều phần mềm thiết kế giao diện web và dàn trang ngày nay đã sử dụng Lorem Ipsum làm đoạn văn bản giả, và nếu bạn thử tìm các đoạn \"Lorem ipsum\" trên mạng thì sẽ khám phá ra nhiều trang web hiện vẫn đang trong quá trình xây dựng. Có nhiều phiên bản khác nhau đã xuất hiện, đôi khi do vô tình, nhiều khi do cố ý (xen thêm vào những câu hài hước hay thông tục).";
        showTextOnCanvas(text, canvas, "#FFFFFF", "#000000", 15);
//        drawText(canvas, bound, text, 15, "#e01825", null);
//        yCurrent += 15;
//        drawText(canvas, bound, text, 15, null, null);
//        yCurrent += 15;
        //Bitmap btmap = BitmapFactory.decodeResource(getResources(), R.drawable.nature);
        //drawImageBitmap(canvas, btmap);
//        Paint px = new Paint();
//        px.setTextSize(getPixel(15));
//        canvas.drawText("xxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxxx", 10, 10, px);
//        int yPosition = 25;
//        canvas.drawText(text, 10, yPosition, px);
//        yPosition += 35;
//        canvas.drawText(text, 10, yPosition, px);
//        yPosition += 35;
    }
}
