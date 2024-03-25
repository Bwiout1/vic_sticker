package com.example.amusingstickerbox.self_made;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;

import com.example.amusingstickerbox.R;

public class CustomView extends View {
    private Paint paint;
    private Path path;
    private Bitmap bitmap;

    private Button btn_save;

    public CustomView(Context context) {
        super(context);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);

        path = new Path();
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (bitmap != null) {
            canvas.drawBitmap(bitmap, 0, 0, null);
        }

        canvas.drawPath(path, paint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                path.moveTo(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                path.lineTo(x, y);
                break;
            case MotionEvent.ACTION_UP:
                // 截取图片
                performCrop();
                break;
        }

        invalidate();

        return true;
    }

    private void performCrop() {
        if (bitmap != null) {
            Bitmap croppedImage = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(croppedImage);
            canvas.clipPath(path);
            canvas.drawBitmap(bitmap, 0, 0, null);

            // 显示截取后的图片
            ImageView imageView = ((Activity) getContext()).findViewById(R.id.chosed_pic);
            imageView.setImageBitmap(croppedImage);

            CustomView customView = ((Activity) getContext()).findViewById(R.id.manual_view);
            customView.setVisibility(INVISIBLE);

//            btn_save = ((Activity) getContext()).findViewById(R.id.btn_save);
//            btn_save.setVisibility(VISIBLE);

            imageView.setVisibility(VISIBLE);
        }
    }
}



