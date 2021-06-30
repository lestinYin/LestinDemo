package com.lestin.yin.activity;

import android.Manifest;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;


import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import com.lestin.yin.R;
import com.lestin.yin.base.ABase;
import com.lestin.yin.utils.ToastUtils;
import com.lestin.yin.widget.dialog.BottomListMenuDialog;


import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import static com.lestin.yin.Constants.Companion;


public class ACutImg extends ABase {
    private Bitmap mPhoto;
    private boolean hasPhoto;
    private BottomListMenuDialog bdialog;

    boolean isCancel = true;

    private boolean isNormalPic;//是普通的获取一张图片返回file 还是修改头像
    //保存图片名字
    private String savePic;


    @Override
    public void initView() {
        
        isNormalPic = this.getIntent().getBooleanExtra(Companion.getIS_NORMAL_PIC(), false);
        hasPhoto = false;
        changeHeadImg();
    }

    @Override
    public void initData() {
    }

    @Override
    protected void onResume() {
        super.onResume();
        isCancel = true;
        if (bdialog != null) bdialog.show();
    }


    private void changeHeadImg() {
        bdialog = new BottomListMenuDialog(this, "拍照", "相册", "取消");
        bdialog.setOnItemClick(position -> {
            switch (position) {
                case 0:
                    isCancel = false;
                    checkCameraPermission();
                    checkRedExtenalStorygyPermission();
                    break;
                case 1:
                    isCancel = false;
                    showPhotoPicker();
                    break;
            }
        });
        bdialog.show();
        bdialog.setOnDismissListener(dialog -> {
            if (isCancel)
                finish();
        });
    }

    /**
     * 跳转选择相册
     */
    private void showPhotoPicker() {
        Intent intent = new Intent(Intent.ACTION_PICK, null);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        startActivityForResult(intent, Companion.getINTENT_TAKE_PIC_DICM());
    }

    /**
     * 跳转拍照界面
     */
    private void showCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT,
                Uri.fromFile(getMyCachePicFile()));
        //判断是否是AndroidN以及更高的版本
        Uri uri;
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
            uri = Uri.fromFile(getMyCachePicFile());
        } else {
            /**
             * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
             * 并且这样可以解决MIUI系统上拍照返回size为0的情况
             */
            uri = FileProvider.getUriForFile(getApplicationContext(), getFileProviderName(this), getMyCachePicFile());
        }

        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, Companion.getINTENT_TAKE_PIC_CAMEAR());


    }

    public static String getFileProviderName(Context context) {
        return context.getPackageName() + ".fileProvider";
    }

    /**
     * 保存裁剪之后的图片数据
     */
    private void setPicToView(Intent data) {
        Bundle extras = data.getExtras();
        // 裁剪完成后立即进行上传操作
        if (extras != null) {
            mPhoto = extras.getParcelable("data");
            checkWriteSDPermission();
        } else {
        }
    }

    /**
     * 保存图片到本地缓存
     *
     * @param bitmap 图片对象
     */
    private void savePic(Bitmap bitmap) {
        File file = getMyCachePicFile();
        if (file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
        }
        try {
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            OutputStream outStream = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 15, outStream);
            outStream.flush();
            outStream.close();
            hasPhoto = true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        uploadImage();
    }

    Uri uri;

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            //拍照返回
            case 2:
                if (isNormalPic) {
//                    File temp = new File(getExternalCacheDir().toString(),savePic);
//                    Uri uri = Uri.fromFile(temp);
//                    startPhotoZoom(uri);
                    //普通图片不用裁剪
                    savePic(BitmapFactory.decodeFile(new File(getExternalCacheDir(), savePic).getAbsolutePath()));
//                    this.setResult(RESULT_CODE_GET_PIC, new Intent().putExtra("picName", savePic));
//                    finish();

                } else {
                    File temp = new File(getMyCachePicFile().toString());


                    if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.M) {
                        uri = Uri.fromFile(temp);
                    } else {
                        /**
                         * 7.0 调用系统相机拍照不再允许使用Uri方式，应该替换为FileProvider
                         * 并且这样可以解决MIUI系统上拍照返回size为0的情况
                         */
                        uri = getImageContentUri(getApplicationContext(), temp);
//                        uri = FileProvider.getUriForFile(getApplicationContext(), getFileProviderName(this), temp);
                    }
                    startPhotoZoom(uri);
                }
                break;
            //选择相册返回
            case 1:
                if (data == null) return;
                Uri uri_pic_dicm = data.getData();
                if (isNormalPic) {
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri_pic_dicm);
                        savePic(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else {
                    startPhotoZoom(uri_pic_dicm);
                }
                break;
            case 3:
                if (data != null) {
                    setPicToView(data);
                }
                break;
        }
    }

    public void startPhotoZoom(Uri uri) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 下面这个crop=true是设置在开启的Intent中设置显示的VIEW可裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 300);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, Companion.getINTENT_TAKE_PIC_SCALE());
    }

    //文件
    // 头像文件路径
    public File getMyCachePicFile() {
        // 目录不存在就创建
        File dirPath = new File(getBaseCachePath());
        if (!dirPath.exists()) {
            //noinspection ResultOfMethodCallIgnored
            dirPath.mkdirs();
        }
        return new File(getBaseCachePath(), getMyPicName());
    }

    public String getBaseCachePath() {
        File cacheDir = getExternalCacheDir();
        return cacheDir != null ? cacheDir.getAbsolutePath() : null;
    }

    public String getMyPicName() {
        String phone = "9989898";
//        String phone = eCutomer.getId();
//        if (TextUtils.isEmpty(phone)) {
        if (isNormalPic) {
            phone = phone + "" + System.currentTimeMillis();
            savePic = phone + ".jpg";
        } else {
            phone = "temp";
        }
//        }
        return phone + ".jpg";
    }

    private void uploadImage() {

        if (!hasPhoto) {
            ToastUtils.showShort("读取图片失败，请重新选择");
            return;
        }
//         上传图片
        /**
         * isNormal返回File 反之修改头像
         */
        if (isNormalPic) {
            this.setResult(1, new Intent().putExtra("picName", savePic));
            finish();
        } else {
//            if (bdialog != null && bdialog.isShowing()) {
//                isCancel = false;
//                bdialog.dismiss();
//            }
//            MultipartBody.Part img = FileLoadUtil.toRequestBodyOfImage("files", getMyCachePicFile());
//            exec(baseApi().uploadImage(user.getData().getUserApp().getUserId(), 2, img), result -> {
//                EBaseString responseData = new Gson().fromJson(result.string(), EBaseString.class);
//                if (responseData.getState() == 1) {
//                    bdialog.dismiss();
//                    ToastUtils.showShort("上传成功");
//                    user.getData().getUserApp().setHeadUrl(responseData.getData().getMessage());
//                    spManager.save(GlobalConfig.USER_INFO, user);
//                    EventBus.getDefault().post(new Event.EChangeHead(true));
//                    finish();
//                } else {
//                    ToastUtils.showShort("上传失败,请重试");
//                    bdialog.show();
//                }
//            }, throwable -> {
//                ToastUtils.showShort("上传失败,请重试");
//            });

        }

        //upload img
    }

    int PERMISSION_REQUEST_WRITE_SD = 103;
    int PERMISSION_READ_EXTERNAL_STORAGE = 104;

    protected void checkWriteSDPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                        PERMISSION_REQUEST_WRITE_SD);
            } else {
                onGetWriteSDPermission();
            }
        } else {
            onGetWriteSDPermission();
        }
    }

    protected void checkRedExtenalStorygyPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        PERMISSION_READ_EXTERNAL_STORAGE);
            } else {
            }
        } else {
        }
    }

    int PERMISSION_REQUEST_CAMERA = 100;

    protected void checkCameraPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
                    != PackageManager.PERMISSION_GRANTED) {
                requestPermissions(new String[]{Manifest.permission.CAMERA},
                        PERMISSION_REQUEST_CAMERA);
            } else {
                onGetCameraPermission();
            }
        } else {
            onGetCameraPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case 100:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {//
                            checkCameraPermission();//重新申请权限
                            return;
                        }
                    }
                }
                showCamera();
                break;
            case 104:
                for (int i = 0; i < grantResults.length; i++) {
                    if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                        //判断是否勾选禁止后不再询问
                        boolean showRequestPermission = ActivityCompat.shouldShowRequestPermissionRationale(this, permissions[i]);
                        if (showRequestPermission) {//
                            checkRedExtenalStorygyPermission();//重新申请权限
                            return;
                        }
                    }
                }
//                showCamera();
                break;
            default:
                break;
        }
    }

    protected void onGetWriteSDPermission() {
        savePic(mPhoto);
    }


    protected void onGetCameraPermission() {
        showCamera();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bdialog != null && bdialog.isShowing()) {
            bdialog.dismiss();
        }
    }

    public static Uri getImageContentUri(Context context, File imageFile) {
        String filePath = imageFile.getAbsolutePath();
        Cursor cursor = context.getContentResolver().query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                new String[]{MediaStore.Images.Media._ID},
                MediaStore.Images.Media.DATA + "=? ",
                new String[]{filePath}, null);

        if (cursor != null && cursor.moveToFirst()) {
            int id = cursor.getInt(cursor
                    .getColumnIndex(MediaStore.MediaColumns._ID));
            Uri baseUri = Uri.parse("content://media/external/images/media");
            return Uri.withAppendedPath(baseUri, "" + id);
        } else {
            if (imageFile.exists()) {
                ContentValues values = new ContentValues();
                values.put(MediaStore.Images.Media.DATA, filePath);
                return context.getContentResolver().insert(
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
            } else {
                return null;
            }
        }
    }


    @Override
    public int layoutId() {
        return R.layout.item_tabview;
    }

    @Override
    public void start() {

    }
}
