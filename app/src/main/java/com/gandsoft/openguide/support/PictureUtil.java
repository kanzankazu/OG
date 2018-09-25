package com.gandsoft.openguide.support;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.util.Base64;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class PictureUtil {

    public static Intent getCropperIntent(Context mContext, Uri imageUri, @Nullable Integer sizex, @Nullable Integer sizey) {
        Intent CropIntent = null;

        if (sizex == null) {
            sizex = 800;
        }
        if (sizey == null) {
            sizey = 900;
        }
        if (sizex > 1500) {
            sizex = 1500;
        }
        if (sizey > 1500) {
            sizey = 1500;
        }

        try {
            CropIntent = new Intent("com.android.camera.action.CROP");
            CropIntent.setDataAndType(imageUri, "image/*");
            CropIntent.putExtra("crop", "true");
            CropIntent.putExtra("outputX", sizex);
            CropIntent.putExtra("outputY", sizey);
            CropIntent.putExtra("aspectX", 4);
            CropIntent.putExtra("aspectY", 4);
            CropIntent.putExtra("scaleUpIfNeeded", true);
            CropIntent.putExtra("return-data", true);
            return CropIntent;
        } catch (ActivityNotFoundException e) {
            return CropIntent;
        }
    }

    public static Bitmap resizeImageBitmap(Bitmap bitmap, int newSize) {
        int width = bitmap.getWidth();
        int height = bitmap.getHeight();

        int newWidth = 0;
        int newHeight = 0;

        if (width > height) {
            newWidth = newSize;
            newHeight = (newSize * height) / width;
        } else if (width < height) {
            newHeight = newSize;
            newWidth = (newSize * width) / height;
        } else if (width == height) {
            newHeight = newSize;
            newWidth = newSize;
        }

        float scaleWidth = ((float) newWidth) / width;
        float scaleHeight = ((float) newHeight) / height;

        Matrix matrix = new Matrix();
        matrix.postScale(scaleWidth, scaleHeight);

        return Bitmap.createBitmap(bitmap, 0, 0, width, height, matrix, true);
    }

    @TargetApi(Build.VERSION_CODES.N)
    public static Uri getUriFromResult(Context context, int resultCode, Uri jink) {
        InputStream in = null;
        Uri selectedImage = null;
        if (resultCode == Activity.RESULT_OK) {

            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), jink);
                in = context.getContentResolver().openInputStream(jink);
                ExifInterface exifInterface = new ExifInterface(in);

                int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                        ExifInterface.ORIENTATION_UNDEFINED);

                switch (orientation) {

                    case ExifInterface.ORIENTATION_ROTATE_90:
                        selectedImage = rotateImage(bitmap, 90, context);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_180:
                        selectedImage = rotateImage(bitmap, 180, context);
                        break;

                    case ExifInterface.ORIENTATION_ROTATE_270:
                        selectedImage = rotateImage(bitmap, 270, context);
                        break;

                    case ExifInterface.ORIENTATION_NORMAL:
                    default:
                        selectedImage = jink;
                        break;
                }

            } catch (IOException e) {
                // Handle any errors
                return null;
            } finally {
                if (in != null) {
                    try {
                        in.close();
                    } catch (IOException ignored) {
                    }
                }
                return selectedImage;
            }
        } else {
            return selectedImage;
        }
    }

    private static File getTempFile(Context context) {
        File imageFile = new File(context.getExternalCacheDir(), "Tempe");
        imageFile.getParentFile().mkdirs();
        return imageFile;
    }

    public static String bitmapToBase64(Bitmap bitmap, Bitmap.CompressFormat format, int compression) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(format, compression, stream);
        byte[] byteArray = stream.toByteArray();
        try {
            stream.close();
            stream = null;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return "data:image/jpeg;base64," + Base64.encodeToString(byteArray, Base64.NO_WRAP);
        }
    }

    public static Bitmap base64ToBitmap(String base64) {
        byte[] decodedString = Base64.decode(base64.replace("data:image/jpeg;base64,", ""), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        return decodedByte;
    }

    public static Uri rotateImage(Bitmap source, float angle, Context mContext) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap temp = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        temp.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(mContext.getContentResolver(), temp, "Title", null);
        return Uri.parse(path);

    }

    public static Bitmap rotateImage(Bitmap source, float angle, Context mContext, Bitmap.CompressFormat format, int compressionLevel) {
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap temp = Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(),
                matrix, true);

        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        temp.compress(format, compressionLevel, bytes);
        return temp;
    }

    private static void createDirectoryAndSaveFile(Bitmap imageToSave, String fileName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES) + "/.Gandsoft/" + "/" + fileName);
        if (file.exists()) {
            file.delete();
        }
        try {
            FileOutputStream out = new FileOutputStream(file);
            imageToSave.compress(Bitmap.CompressFormat.PNG, 100, out);
            out.flush();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String getPathFromUri(Uri uri, Activity activity) {
        String[] projection = {MediaStore.Images.Media.DATA};

        Cursor cursor = activity.managedQuery(uri, projection, null, null, null);
        activity.startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    public static Bitmap getBitmapFromPathFile(String imagePath) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        Bitmap bitmap = BitmapFactory.decodeFile(imagePath, bmOptions);
        return bitmap;
    }

    public static boolean removeImageFromPathFile(String source_) {
        File fdelete = new File(source_);
        if (fdelete.exists()) {
            if (fdelete.delete()) {
                System.out.println("file Deleted :" + source_);
                return true;
            } else {
                System.out.println("file not Deleted :" + source_);
                return false;
            }
        }
        return false;
    }

    public static Bitmap flipHorizontalImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();

        matrix.preScale(-1.0f, 1.0f);

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static Bitmap flipVerticalImage(Bitmap src) {
        // create new matrix for transformation
        Matrix matrix = new Matrix();

        matrix.preScale(1.0f, -1.0f);

        // return transformed image
        return Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
    }

    public static Bitmap flipHorizontally(Bitmap originalImage) {
        // The gap we want between the flipped image and the original image
        final int flipGap = 4;

        int width = originalImage.getWidth();
        int height = originalImage.getHeight();

        // This will not scale but will flip on the Y axis
        Matrix matrix = new Matrix();
        matrix.preScale(-1, 1);

        // Create a Bitmap with the flip matrix applied to it.
        // We only want the bottom half of the image
        Bitmap flipImage = Bitmap.createBitmap(originalImage, 0, 0, width, height, matrix, true);

        // Create a new bitmap with same width but taller to fit reflection
        Bitmap bitmapWithFlip = Bitmap.createBitmap((width + width + flipGap), height, Bitmap.Config.ARGB_8888);

        /*// Create a new Canvas with the bitmap that's big enough for
        Canvas canvas = new Canvas(bitmapWithFlip);

        //Draw original image
        canvas.drawBitmap(originalImage, 0, 0, null);

        //Draw the Flipped Image
        canvas.drawBitmap(flipImage, width + flipGap, 0, null);*/


        return flipImage;
    }
}
