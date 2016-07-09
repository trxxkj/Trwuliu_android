package cn.trxxkj.trwuliu.driver.ui;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import cn.trxxkj.trwuliu.driver.R;

/**
 * 上传功能
 * @author cyh 2016.4.20 下午16:45
 */

public class UploadActivity extends Activity {

    ImageView imageView;

    public int XIANGJI = 0;
    public int TUKU = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);
//        Button btn = (Button) findViewById(R.id.button);
        Button btn1 = (Button) findViewById(R.id.button1);
        Button btn2 = (Button) findViewById(R.id.button2);
//        imageView = (ImageView) findViewById(R.id.imageView);
//        btn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                loginOfPost();
//            }
//        });
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用相机
                Intent camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                startActivityForResult(camera, XIANGJI);
            }
        });
        btn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //调用图库
                Intent picture = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(picture, TUKU);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String filePath = "";

        if (requestCode == XIANGJI) {
            //如果是图库选择
            if (data == null) {
                return;
            }
            Bundle bundle = data.getExtras();
            filePath = getCameraImage(bundle);
            Bitmap bitmap = bundle.getParcelable("data");
            imageView.setImageBitmap(bitmap);
        }
        if (requestCode == TUKU) {
            Uri uri = data.getData();
            filePath = getPhoneImage(uri.toString());
            ContentResolver cr = this.getContentResolver();
            Bitmap bitmap = null;
            try {
                bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            imageView.setImageBitmap(bitmap);
        }

        //使用Xutils上传图片
        HttpUtils httpUtils = new HttpUtils();
        RequestParams params = new RequestParams();
        params.addBodyParameter("addfile", new File(filePath));
        httpUtils.send(HttpRequest.HttpMethod.POST, "http://file.trxxkj.cn/web/upload", params, new RequestCallBack<String>() {
            @Override
            public void onSuccess(ResponseInfo<String> responseInfo) {
                //上传成功，这里面的返回值，就是服务器返回的数据
                Log.e("返回值", responseInfo.result);
            }

            @Override
            public void onFailure(HttpException e, String s) {
                Log.e("失败:", e.getMessage());
            }
        });
    }

//    public static String loginOfPost(){
//        //使用Xutils上传用户登录信息
//        HttpUtils httpUtils1 = new HttpUtils();
//        final RequestParams params1 = new RequestParams();
//        params1.addBodyParameter("account","admin");
//        params1.addBodyParameter("passWord","666666");
//        params1.addBodyParameter("pass","tmk7758");
//        httpUtils1.send(HttpRequest.HttpMethod.POST, "http://www.trxxkj.cn/user/loginin",params1, new RequestCallBack<String>() {
//            @Override
//            public void onSuccess(ResponseInfo<String> responseInfo) {
//                Log.e("返回值",responseInfo.result);
//            }
//
//            @Override
//            public void onFailure(HttpException e, String s) {
//                Log.e("失败",e.getMessage());
//            }
//        });
//        return null;
//    }

    /**
     * 根据Bundle获取图片在sd卡的路径
     * @param bundle
     * @return
     */
    private String getCameraImage(Bundle bundle) {
        String strState = Environment.getExternalStorageState();
        if (!strState.equals(Environment.MEDIA_MOUNTED)) {
            Log.i("TAG", "SD卡不存在");
        }
        String fileName = "chenxin.jpg"; //此处可以改为时间
        // Bundle bundle = data.getExtras();
        Bitmap bitmap = (Bitmap) bundle.get("data");
        File file = new File("/sdcard/tu/");
        if (!file.exists()) {
            file.mkdirs();
        }
        fileName = "/sdcard/tu/" + fileName;
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream(fileName);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (stream != null) {
                    stream.flush();
                    stream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return fileName;
    }

    /**
     * 获取系统图库图片的SD卡路径
     *
     * @param uriString
     * @return
     */
    private String getPhoneImage(String uriString) {
        Uri selectedImage = Uri.parse(uriString);
        String[] filePathColumns = {MediaStore.Images.Media.DATA};
        Cursor cursor = this.getContentResolver().query(selectedImage, filePathColumns, null, null, null);
        cursor.moveToFirst();
        int columnIndex = cursor.getColumnIndex(filePathColumns[0]);
        String fileName = cursor.getString(columnIndex);
        cursor.close();
        return fileName;
    }

}
