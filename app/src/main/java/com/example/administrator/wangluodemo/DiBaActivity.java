package com.example.administrator.wangluodemo;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import fragment.MenuLeftFragment;

import static android.R.attr.bitmap;
public class DiBaActivity extends AppCompatActivity implements View.OnClickListener {
        private TextView mDiba_textview;
        private ImageView mDiba_imageview;
        private EditText mDiba_edittext;
        private PopupWindow mPopWindow;
        private Bitmap mBitmap;
        private int theme = 0;
        @Override
        protected void onCreate(Bundle savedInstanceState){
            Night_styleutils.changeStyle(this, theme, savedInstanceState);
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_di_ba);
            mDiba_textview = (TextView) findViewById(R.id.diba_textview);
            mDiba_imageview = (ImageView) findViewById(R.id.diba_imageview);
            mDiba_edittext = (EditText) findViewById(R.id.diba_edittext);
            mDiba_textview.setOnClickListener(this);
            mDiba_imageview.setOnClickListener(this);
        }
    @Override
    public void onClick(View v){
       switch(v.getId()){
           case R.id.diba_textview:
               String trim = mDiba_edittext.getText().toString().trim();
               if(TextUtils.isEmpty(trim)){
                   Toast.makeText(DiBaActivity.this,"请输入昵称",Toast.LENGTH_SHORT).show();
               }else{
                   Intent intent=new Intent(DiBaActivity.this,MainActivity.class);
                   intent.putExtra("bitmap", mBitmap);
                   intent.putExtra("name",trim);
                   intent.putExtra("flag",true);
                   startActivity(intent);
                   finish();
               }
               break;
           case R.id.diba_imageview:
//               showPopupWindow();
               AlertDialog.Builder builder = new AlertDialog.Builder(DiBaActivity.this);
                String[] item=new String[]{"拍照","查相册","取消"};
               builder.setItems(item, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       switch (which){
                           case 0:
                               paizhao();
                               dialog.dismiss();
                               break;
                           case 1:
                               chakan();
                               dialog.dismiss();
                               break;
                           case 2:
                               dialog.dismiss();
                               break;
                       }
                   }
               });
               builder.show();
               break;
//           case R.id.poppai_textview1:
//               Intent intent1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//               intent1.addCategory("android.intent.category.DEFAULT");
//               startActivityForResult(intent1,100);
//               mPopWindow.dismiss();
//               break;
//           case R.id.poppai_textview2:
//               Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);
//               intent2.setType("image/*");
//               startActivityForResult(intent2,200);
//               mPopWindow.dismiss();
//               break;
//           case R.id.poppai_textview3:
//               mPopWindow.dismiss();
//               break;
       }
    }

    private void paizhao() {
        Intent intent1=new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent1.addCategory("android.intent.category.DEFAULT");
        startActivityForResult(intent1,100);
    }
    private void chakan() {
        Intent intent2=new Intent(Intent.ACTION_GET_CONTENT);
        intent2.setType("image/*");
        startActivityForResult(intent2,200);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode==100){
//            Bundle bundle=data.getExtras();
//            Bitmap bitmap=(Bitmap)bundle.get("data");
            Bitmap bitmap = data.getParcelableExtra("data");
             mDiba_imageview.setImageBitmap(bitmap);
        }else if(requestCode==200){
            Uri uri = data.getData();
            crop(uri);
        }else if(requestCode==6666){
            if(data==null){
                return;
            }
            mBitmap = data.getParcelableExtra("data");
            mDiba_imageview.setImageBitmap(mBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void crop(Uri uri){
        Intent intent=new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        intent.putExtra("crop", true);
        intent.putExtra("aspectX",1);
        intent.putExtra("aspectY", 1);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 250);
        intent.putExtra("outputFormat", "JPEG");
        intent.putExtra("noFaceDetection", false);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, 6666);
    }
    private void showPopupWindow(){
        View view=View.inflate(DiBaActivity.this,R.layout.poppai_layout,null);
        mPopWindow = new PopupWindow(view,ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
        mPopWindow.setTouchable(true);
        mPopWindow.setFocusable(true);
        mPopWindow.setOutsideTouchable(true);
        //设置各个控件的点击响应
        TextView poppai_textview1 = (TextView)view.findViewById(R.id.poppai_textview1);
        TextView poppai_textview2 = (TextView)view.findViewById(R.id.poppai_textview2);
        TextView poppai_textview3 = (TextView)view.findViewById(R.id.poppai_textview3);
        poppai_textview1.setOnClickListener(this);
        poppai_textview2.setOnClickListener(this);
        poppai_textview3.setOnClickListener(this);
        mPopWindow.showAsDropDown(mDiba_imageview);
    }
}
