package com.android.androidpj_main.Main;


import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.drawable.RoundedBitmapDrawable;
import androidx.core.graphics.drawable.RoundedBitmapDrawableFactory;
import androidx.fragment.app.Fragment;

import com.android.androidpj_main.Activity.MyViewActivity;
import com.android.androidpj_main.Activity.OrderdetailActivity;
import com.android.androidpj_main.Bean.User;
import com.android.androidpj_main.NetworkTask.ImageUploadNetworkTask;
import com.android.androidpj_main.NetworkTask.UserNetworkTask;
import com.android.androidpj_main.NetworkTask.ViewImageNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class Frmt_Mypage extends Fragment {

    View v;
    final static String TAG = "Frmt_Mypage";

    // 21.01.10 지은 추가 ********************************
    LinearLayout LH_call, LH_email, LH_often;
    LinearLayout myOrder, myReview;
    ImageView mySetting;

    TextView MyMainName, MyColor;
    ArrayList<User> users_mypage;
    String urlAddr_My = null; // 로그인한 아이디에 대한 정보 띄움 ( 이름과 퍼스널 컬러 )
    //****************************************************************


    // 이미지 업로드
    String imgAddr;
//    ImageView imageView = null;

    private final int REQ_CODE_SELECT_IMAGE = 300; // Gallery Return Code
    private String img_path = null; // 최종 file name
    private String f_ext = null;    // 최종 file extension
    File tempSelectFile;

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //          Tomcat Server의 IP Address와 Package이름은 수정 하여야 함
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    String devicePath = Environment.getDataDirectory().getAbsolutePath() + "/data/com.android.androidpj_main/";
    String urlAddr = "http://" + ShareVar.macIP + ":8080/JSP/insertMultipart.jsp?userEmail=";
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    ImageView insertImage;
    String userEmail;

    public Frmt_Mypage() {

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.frmt_mypage,container,false);

        // 지은 21/01/10 정보 띄우기****************************
        // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
        String email = PreferenceManager.getString(getActivity(), "email");
//        Toast.makeText(getContext(), "email값:::::::;"+ email, Toast.LENGTH_SHORT).show();



        // 로그인 한 id에 대한 이름 과 연락처를 띄우는 jsp
        urlAddr_My = "http://" + ShareVar.macIP + ":8080/JSP/mySelect.jsp?userEmail=" + email;
        getUserDate();  // 띄우기 위한 메소드
        MyMainName = v.findViewById(R.id.MyMainName);
        MyColor = v.findViewById(R.id.MyColor);

        MyMainName.setText(users_mypage.get(0).getUserName());
        MyColor.setText(users_mypage.get(0).getUserColor());
        //*******************************************


        // 지은 21/01/10 정보 & 조회 관련****************************
        mySetting = v.findViewById(R.id.mySetting);

        myOrder = v.findViewById(R.id.myOrder);
        myReview = v.findViewById(R.id.myReview);

        mySetting.setOnClickListener(settingListener);
        myOrder.setOnClickListener(settingListener);
        myReview.setOnClickListener(settingListener);
        //*******************************************

        // 지은 21/01/10 문의 관련****************************
        LH_call = v.findViewById(R.id.LH_call);
        LH_email = v.findViewById(R.id.LH_email);
        LH_often = v.findViewById(R.id.LH_often);

        LH_call.setOnClickListener(questionListener);
        LH_email.setOnClickListener(questionListener);
        LH_often.setOnClickListener(questionListener);
        //*******************************************

        insertImage = v.findViewById(R.id.insert_image);
        insertImage.setOnClickListener(mClickListener);


        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        //
        //          사용자에게 사진(Media) 사용 권한 받기
        //
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, getContext().MODE_PRIVATE);
        /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

//        imageView = v.findViewById(R.id.insert_image);


        insertImage.setOnClickListener(mClickListener);



        userEmail = com.android.androidpj_main.Activity.PreferenceManager.getString(getActivity(), "email");

        if (users_mypage.get(0).getUserFilename().length() == 0){
            insertImage.setImageResource(R.drawable.myaccount);
        }else{
        imgAddr = "http://" + ShareVar.macIP + ":8080/Images/";
        imgAddr = imgAddr + users_mypage.get(0).getUserFilename();

        ViewImageNetworkTask networkTask = new ViewImageNetworkTask(getActivity(), imgAddr, insertImage);
        networkTask.execute(100); // 10초. 이것만 쓰면 pre post do back 등 알아서 실행

        }



        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }



    // 21.01.10 지은 추가 ********************************
    // 내가 로그인한 id값에 대한 이름과 연락처를 불러옴
    private void getUserDate(){
        try {
            UserNetworkTask networkTask = new UserNetworkTask(getActivity(), urlAddr_My);
            Object obj = networkTask.execute().get();
            users_mypage = (ArrayList<User>) obj;
        }catch (Exception e){
            e.printStackTrace();
        }
    }


    // 정보 & 조회 관련 버튼 클릭
    View.OnClickListener settingListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.mySetting:
                    Toast.makeText(getActivity(), "내정보 상세 수정", Toast.LENGTH_SHORT).show();
                    Intent Mintent = new Intent(getActivity(), MyViewActivity.class);
                    startActivity(Mintent);
                    break;
                case R.id.myOrder:
                    //Toast.makeText(getActivity(), "나의 주문/배송 조회", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity(), OrderdetailActivity.class);
                    startActivity(intent);
                    break;
                case R.id.myReview:
                    Toast.makeText(getActivity(), "나의 리뷰 조회", Toast.LENGTH_SHORT).show();
                    break;
            }
        }
    };



    // 문의 관련 버튼 클릭
    View.OnClickListener questionListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.LH_often:
                    Toast.makeText(getActivity(), "자주묻는 질문 클릭", Toast.LENGTH_SHORT).show();
                    break;

                case R.id.LH_call:
                    Intent mIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("tel:012-3456-7890"));
                    startActivity(mIntent);
                    break;

                case R.id.LH_email:
                    Intent email = new Intent(Intent.ACTION_SEND);
                    email.setType("plain/text");
                    String[] address = {"qkrwldms011@naver.com"};
                    email.putExtra(Intent.EXTRA_EMAIL, address);
                    email.putExtra(Intent.EXTRA_SUBJECT, "LIPHAE 에게 문의하실 제목을 적어주세요.");
                    email.putExtra(Intent.EXTRA_TEXT, "LIPHAE 에게 문의 하실 내용을 적어주세요.");
                    startActivity(email);
                    break;
            }
        }
    };


    // ****************************************************************
    // 이미지 업로드 관련.
    View.OnClickListener mClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                //////////////////////////////////////////////////////////////////////////////////////////////
                //
                //          Photo App.으로 이동
                //
                //////////////////////////////////////////////////////////////////////////////////////////////
                case R.id.insert_image:
                    Intent intent = new Intent(Intent.ACTION_PICK);
                    intent.setType(MediaStore.Images.Media.CONTENT_TYPE);
                    intent.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
                    break;


            }
        }
    };

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //                   Photo App.에서 Image 선택후 작업내용
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        Log.v(TAG, "Data :" + String.valueOf(data));

        if (requestCode == REQ_CODE_SELECT_IMAGE && resultCode == Activity.RESULT_OK) {
            try {
                //이미지의 URI를 얻어 경로값으로 반환.
                img_path = getImagePathToUri(data.getData());
                Log.v(TAG, "image path :" + img_path);
                Log.v(TAG, "Data :" +String.valueOf(data.getData()));

                //이미지를 비트맵형식으로 반환
                Bitmap image_bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), data.getData());

                //image_bitmap 으로 받아온 이미지의 사이즈를 임의적으로 조절함. width: 400 , height: 300
                Bitmap image_bitmap_copy = Bitmap.createScaledBitmap(image_bitmap, 400, 300, true);

                RoundedBitmapDrawable roundBitmap = RoundedBitmapDrawableFactory.create(getContext().getResources(), image_bitmap_copy);
                roundBitmap.setCircular(true);
                insertImage.setImageDrawable(roundBitmap);

//                insertImage.setImageBitmap(image_bitmap_copy);

                // 파일 이름 및 경로 바꾸기(임시 저장, 경로는 임의로 지정 가능)
                String date = new SimpleDateFormat("yyyyMMddHmsS").format(new Date());
                String imageName = date + "." + f_ext;
                tempSelectFile = new File(devicePath , imageName);
                OutputStream out = new FileOutputStream(tempSelectFile);
                image_bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);

                // 임시 파일 경로로 위의 img_path 재정의
                img_path = devicePath + imageName;
                Log.v(TAG,"fileName :" + img_path);
            } catch (Exception e) {
                e.printStackTrace();
            }
            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //           Upload
            //
            //////////////////////////////////////////////////////////////////////////////////////////////
            urlAddr = urlAddr + userEmail;
            Log.v(TAG, urlAddr);
            ImageUploadNetworkTask networkTask = new ImageUploadNetworkTask(getActivity(), insertImage, img_path, urlAddr);
            //////////////////////////////////////////////////////////////////////////////////////////////
            //
            //              NetworkTask Class의 doInBackground Method의 결과값을 가져온다.
            //
            //////////////////////////////////////////////////////////////////////////////////////////////
            try {
                Integer result = networkTask.execute(100).get();
                //////////////////////////////////////////////////////////////////////////////////////////////
                //
                //              doInBackground의 결과값으로 Toast생성
                //
                //////////////////////////////////////////////////////////////////////////////////////////////
                switch (result){
                    case 1:
                        Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();

                        //////////////////////////////////////////////////////////////////////////////////////////////
                        //
                        //              Device에 생성한 임시 파일 삭제
                        //
                        //////////////////////////////////////////////////////////////////////////////////////////////
                        File file = new File(img_path);
                        file.delete();
                        break;
                    case 0:
                        Toast.makeText(getActivity(), "Error", Toast.LENGTH_SHORT).show();
                        break;
                }
                //////////////////////////////////////////////////////////////////////////////////////////////
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //
    //              사용자가 선택한 이미지의 정보를 받아옴
    //
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private String getImagePathToUri(Uri data) {

        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContext().getContentResolver().query(data, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();

        //이미지의 경로 값
        String imgPath = cursor.getString(column_index);
        Log.v(TAG, "Image Path :" + imgPath);

        //이미지의 이름 값
        String imgName = imgPath.substring(imgPath.lastIndexOf("/") + 1);

        // 확장자 명 저장
        f_ext = imgPath.substring(imgPath.length()-3, imgPath.length());

        return imgPath;
    }


}//---- 끝 ------
