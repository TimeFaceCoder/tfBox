package cn.timeface.tfbox.utils;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;

/**
 * Created by rayboot on 15/5/26.
 */
public class BitmapUtils {
    public static Bitmap createReflectedBitmap(Bitmap srcBitmap,
                                               float reflectHeight) {
        if (null == srcBitmap) {
            return null;
        }

        int srcWidth = srcBitmap.getWidth();
        int srcHeight = srcBitmap.getHeight();
        int reflectionWidth = srcBitmap.getWidth();
        int reflectionHeight = reflectHeight == 0 ? srcHeight / 3
                : (int) (reflectHeight * srcHeight);

        if (0 == srcWidth || srcHeight == 0) {
            return null;
        }

        // The matrix
        Matrix matrix = new Matrix();
        matrix.preScale(1, -1);

        try {
            // The reflection bitmap, width is same with original's
            Bitmap reflectionBitmap = Bitmap.createBitmap(srcBitmap, 0,
                    srcHeight - reflectionHeight, reflectionWidth,
                    reflectionHeight, matrix, false);

            if (null == reflectionBitmap) {
                return null;
            }

            Canvas canvas = new Canvas(reflectionBitmap);

            Paint paint = new Paint();
            paint.setAntiAlias(true);

            LinearGradient shader = new LinearGradient(0, 0, 0,
                    reflectionBitmap.getHeight(), 0x70FFFFFF, 0x00FFFFFF,
                    Shader.TileMode.MIRROR);
            paint.setShader(shader);

            paint.setXfermode(new PorterDuffXfermode(
                    android.graphics.PorterDuff.Mode.DST_IN));

            // Draw the linear shader.
            canvas.drawRect(0, 0, reflectionBitmap.getWidth(),
                    reflectionBitmap.getHeight(), paint);

            return reflectionBitmap;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }
}
