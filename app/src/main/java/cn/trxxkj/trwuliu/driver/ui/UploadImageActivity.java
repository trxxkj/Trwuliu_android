package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

import cn.trxxkj.trwuliu.driver.R;

public class UploadImageActivity extends Activity {

    private static final int PHOTO_REQUEST_CAMERA = 1;// 拍照
    private static final int PHOTO_REQUEST_GALLERY = 2;// 从相册中选择
    private static final int PHOTO_REQUEST_CUT = 3;// 结果

    private ImageView imageView;
    private Bitmap bitmap;

    /* 头像名称 */
    private static final String PHOTO_FILE_NAME = "temp_photo.jpg";
    private File tempFile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_upload);
        initView();
    }

    private void initView(){
        imageView = (ImageView) findViewById(R.id.imageView);
    }

    /*
     * 从相册获取
     */
    public void gallery(View view) {
        // 激活系统图库，选择一张图片
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, PHOTO_REQUEST_GALLERY);
    }

    /*
     * 从相机获取
     */
    public void camera(View view) {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        // 判断存储卡是否可以用，可用进行存储
        if (hasSdcard()) {
            intent.putExtra(MediaStore.EXTRA_OUTPUT,
                    Uri.fromFile(new File(Environment
                            .getExternalStorageDirectory(), PHOTO_FILE_NAME)));
        }
        startActivityForResult(intent, PHOTO_REQUEST_CAMERA);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PHOTO_REQUEST_GALLERY) {
            if (data != null) {
                // 得到图片的全路径
                Uri uri = data.getData();
                crop(uri);
            }

        } else if (requestCode == PHOTO_REQUEST_CAMERA) {
            if (hasSdcard()) {
                tempFile = new File(Environment.getExternalStorageDirectory(),
                        PHOTO_FILE_NAME);
                crop(Uri.fromFile(tempFile));
            } else {
                Toast.makeText(UploadImageActivity.this, "未找到存储卡，无法存储照片！", 0).show();
            }

        } else if (requestCode == PHOTO_REQUEST_CUT) {
            try {
                bitmap = data.getParcelableExtra("data");
                this.imageView.setImageBitmap(bitmap);
                boolean delete = tempFile.delete();
                System.out.println("delete = " + delete);

            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * 剪切图片
     * @author:cyh
     * @date:2016-5-15
     * @param uri
     */
    private void crop(Uri uri) {
        // 裁剪图片意图
        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", "true");
        // 裁剪框的比例，1：1
        intent.putExtra("aspectX", 1.6);
        intent.putExtra("aspectY", 1);
        // 裁剪后输出图片的尺寸大小
        intent.putExtra("outputX", 300);
        intent.putExtra("outputY", 230);
        // 图片格式
        intent.putExtra("outputFormat", "PNG");
        intent.putExtra("noFaceDetection", true);// 取消人脸识别
        intent.putExtra("return-data", true);// true:不返回uri，false：返回uri
        startActivityForResult(intent, PHOTO_REQUEST_CUT);
    }

    private boolean hasSdcard() {
        if (Environment.getExternalStorageState().equals(
                Environment.MEDIA_MOUNTED)) {
            return true;
        } else {
            return false;
        }
    }

//    /*
//     * 上传图片
//     */
//    public void upload(View view) {
//        try {
//            ByteArrayOutputStream out = new ByteArrayOutputStream();
//            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
//            out.flush();
//            out.close();
//            byte[] buffer = out.toByteArray();
//
//            byte[] encode = android.util.Base64.encode(buffer, android.util.Base64.DEFAULT);
//            String photo = new String(encode);
//
//            RequestParams params = new RequestParams();
//            params.put("photo", photo);
//            String url = "http://172.20.10.123:8080/imageAction/upload";
//
//            AsyncHttpClient client = new AsyncHttpClient();
//            client.post(url, params, new AsyncHttpResponseHandler() {
//                @Override
//                public void onSuccess(int statusCode, Header[] headers,
//                                      byte[] responseBody) {
//                    try {
//                        if (statusCode == 200) {
//
//                            Toast.makeText(UploadImageActivity.this, "头像上传成功!", 0)
//                                    .show();
//                        } else {
//                            Toast.makeText(UploadImageActivity.this,
//                                    "网络访问异常，错误码：" + statusCode, 0).show();
//
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//                }
//
//                @Override
//                public void onFailure(int statusCode, Header[] headers,
//                                      byte[] responseBody, Throwable error) {
//                    Toast.makeText(UploadImageActivity.this,
//                            "网络访问异常，错误码  > " + statusCode, 0).show();
//
//                }
//            });
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

}
