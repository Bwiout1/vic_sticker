package com.example.amusingstickerbox.tools;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.widget.ImageView;

public class ImageUtils {

    public static Bitmap adjustSaturation(Bitmap bitmap, float saturation) {
        // 创建一个新的颜色矩阵并设置饱和度
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.setSaturation(saturation);

        // 创建一个颜色过滤器并应用到画笔上
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(colorMatrix);
        Paint paint = new Paint();
        paint.setColorFilter(filter);

        // 创建一个新的位图，并将原始位图绘制到其中
        Bitmap adjustedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(adjustedBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        return adjustedBitmap;
    }

    public  void setContrast(Bitmap bitmap, ImageView imageView, float contrast) {

        // 创建一个调整对比度的颜色矩阵
        ColorMatrix colorMatrix = new ColorMatrix();
        colorMatrix.set(new float[] {
                contrast, 0, 0, 0, 0,
                0, contrast, 0, 0, 0,
                0, 0, contrast, 0, 0,
                0, 0, 0, 1, 0});

        // 创建一个画笔并设置颜色矩阵
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        // 创建一个新的位图并将调整后的图片绘制到位图上
        Bitmap adjustedBitmap = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        Canvas canvas = new Canvas(adjustedBitmap);
        canvas.drawBitmap(bitmap, 0, 0, paint);

        // 在ImageView中显示调整后的图片
        imageView.setImageBitmap(adjustedBitmap);
    }

    public static Bitmap adjustExposure(Bitmap image, float exposure) {
        // 将曝光度值转换为指数形式
        float exposureValue = (float) Math.pow(2, exposure);

        // 创建一个颜色矩阵
        float[] matrix = new float[] {
                exposureValue, 0, 0, 0, 0,
                0, exposureValue, 0, 0, 0,
                0, 0, exposureValue, 0, 0,
                0, 0, 0, 1, 0
        };

        // 创建一个颜色矩阵过滤器并应用于原始图片
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        Paint paint = new Paint();
        paint.setColorFilter(filter);

        // 创建一个新的Bitmap对象，将过滤器应用于原始图片
        Bitmap adjustedImage = Bitmap.createBitmap(image.getWidth(), image.getHeight(), image.getConfig());
        Canvas canvas = new Canvas(adjustedImage);
        canvas.drawBitmap(image, 0, 0, paint);

        return adjustedImage;
    }

    public Bitmap applyColorFilter(Bitmap srcBitmap, float redValue, float greenValue, float blueValue) {
        // 创建一个新的 Bitmap 对象，用于处理后的图像
        Bitmap filteredBitmap = Bitmap.createBitmap(srcBitmap.getWidth(), srcBitmap.getHeight(), Bitmap.Config.ARGB_8888);

        // 创建一个 Canvas 对象，并将其绑定到 filteredBitmap
        Canvas canvas = new Canvas(filteredBitmap);

        // 创建一个颜色矩阵，用于调整颜色补全值
        ColorMatrix colorMatrix = new ColorMatrix(new float[]{
                redValue, 0, 0, 0, 0,
                0, greenValue, 0, 0, 0,
                0, 0, blueValue, 0, 0,
                0, 0, 0, 1, 0
        });

        // 创建一个 Paint 对象，并将颜色矩阵应用于其颜色过滤器
        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(colorMatrix));

        // 在 Canvas 上绘制原始图像，并应用颜色过滤器
        canvas.drawBitmap(srcBitmap, 0, 0, paint);

        return filteredBitmap;
    }

}
