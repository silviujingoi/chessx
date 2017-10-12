package com.atlas.chessx.ui;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import com.atlas.chessx.R;

/**
 * TODO: document your custom view class.
 */
public class BoardView extends View {
    private Paint paintWhite, paintBlack, paintPieces;
    private int contentWidth, contentHeight, squareSize;

    public BoardView(Context context) {
        super(context);
        init(null, 0);
    }

    public BoardView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public BoardView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.BoardView, defStyle, 0);



        paintWhite = new Paint();
        paintWhite.setColor(Color.WHITE);

        paintBlack = new Paint();
        paintBlack.setColor(Color.BLACK);

        paintPieces = new TextPaint();
        paintPieces.setTextSize(100);
        paintPieces.setColor(Color.BLUE);

        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // Try for a width based on our minimum
        int minw = getPaddingLeft() + getPaddingRight() + getSuggestedMinimumWidth();
        int width = resolveSizeAndState(minw, widthMeasureSpec, 1);

        // Whatever the width ends up being, ask for a height that would let the pie
        // get as big as it can
        int height = resolveSizeAndState(MeasureSpec.getSize(width * 12 / 16), heightMeasureSpec, 0);

        setMeasuredDimension(width, height);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        contentWidth = getWidth() - paddingLeft - paddingRight;
        contentHeight = getHeight() - paddingTop - paddingBottom;

        drawBoard(canvas);
        drawPieces(canvas);
    }

    private void drawBoard(Canvas canvas) {
        float maxSize = Math.min(contentHeight, contentWidth);

        squareSize = (int) maxSize / 8;

        for (int x = 0; x < 8; x++) {
            for (int y = 0; y < 8; y++) {
                Paint paint = (x+y) % 2 == 0 ? paintBlack : paintWhite;

                canvas.drawRect(x*squareSize, y*squareSize, (x+1)*squareSize, (y+1)*squareSize, paint);
            }
        }
    }

    private void drawPieces(Canvas canvas) {
        String[][] blackPieces = new String[][] {{"R", "K", "B", "Q", "W", "B", "K", "R"},
                                            {"P", "P", "P", "P", "P", "P", "P", "P"}};

        for (int row = 0; row < blackPieces.length; row++) {
            for (int col = 0; col < 8; col++) {
                String piece = blackPieces[row][col];

                float textWidth = paintPieces.measureText(piece);
                float textHeight = paintPieces.getTextSize();

                float textX = squareSize * col + squareSize / 2 - textWidth / 2;
                float textY = squareSize * row + squareSize / 2 + textHeight / 2;
                // TODO: fix textY

                canvas.drawText(piece, textX, textY, paintPieces);
            }
        }

        //TODO: add white pieces
    }
}
