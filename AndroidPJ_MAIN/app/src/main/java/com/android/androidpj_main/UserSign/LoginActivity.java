package com.android.androidpj_main.UserSign;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.androidpj_main.Activity.PreferenceManager;
import com.android.androidpj_main.Main.MainActivity;
import com.android.androidpj_main.NetworkTask.NetworkTask_LogIn;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.GoogleAuthProvider;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.ApiErrorCode;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.OptionalBoolean;
import com.kakao.util.exception.KakaoException;

//-----카카오 로그인 ----------------------------
//import android.support.v7.app.AppCompatActivity;
//구글로그인.

//--------------------------------------------


public class LoginActivity extends Activity {

                                //로그인 - 이강후.

    //카카오 로그인 콜백 선언
    private SessionCallback sessionCallback;

    //구글 로그인
    private FirebaseAuth auth; //파이어 베이스 인증 객체
    private GoogleApiClient googleApiClient; //구글 API클라이언트 객체.
    private static final int REQ_SIGN_GOOGLE = 100; //구글 로그인 결과 코드.(100아니고 어떤값이든 된다)

    //Network
    String macIP = ShareVar.macIP;
    final static String TAG = "LoginActivity";
    String urlAddr3 = null;
    String urlAddr4 = null;

    String sid, spw, sname, sphone;
    EditText Eid, Epw;
    TextView tvFindPw;
    TextView tvGoogle, tvKakao;
    TextView tvErrorId, tvErrorPw;
    Button btnLogin, btnSignup;

    //자동로그인 field
   // Boolean loginChecked; //입력값과 저장값 비교시 사용.
    Boolean saveLoginData;
    //SharedPreferences pref;
    SharedPreferences appData;
    SharedPreferences.Editor editor; //@@@@@@@@@@
    CheckBox cbAutoLogin; //체크박스.
    //오토로그인에 사용하는 변수.
    String id;
    String pwd;

    //아이디 이메일 양싱.
    private String emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ///////////////////////////////////////////////////////////////////////////
        //
        // 21.01.11 지은
        //
        ///////////////////////////////////////////////////////////////////////////
        Button gomain = findViewById(R.id.gomain);
        gomain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                startActivity(intent);

                // PreferenceManager 이메일 보내주기
                PreferenceManager.setString(LoginActivity.this, "email", "qkrtpa12@naver.com");
                finish();
            }
        });
        ///----------------------------------------------------------------------------------------------------


        //[자동로그인] 설정값 불러오기  ---------------------------------------------------------------------
        appData = getSharedPreferences("appData",MODE_PRIVATE);
                //--------
                  load(); //@@@@@@@@@
                //--------
        //------------------------------------------------------------------------------------------d

        tvFindPw = findViewById(R.id.tv_findPw);
        btnLogin = findViewById(R.id.btn_Login);
        btnSignup = findViewById(R.id.btn_Signup);
        //체크박스
        cbAutoLogin = findViewById(R.id.cb_autoLogin);

        //EditText연결
        Eid = findViewById(R.id.et_id);
        Epw = findViewById(R.id.et_pw);

        //EditText에 입력시 자릿수 제한.
        Eid.setFilters(new InputFilter[]{new InputFilter.LengthFilter(25)});
        Epw.setFilters(new InputFilter[]{new InputFilter.LengthFilter(15)});

        //[자동로그인] 이전에 로그인 정보를 저장시킨 기록이 있다면 -----------------------------------------------
        if(saveLoginData){

            Eid.setText(id);
            Epw.setText(pwd);
            cbAutoLogin.setChecked(saveLoginData);
        }
        //------------------------------------------------------------------------------------------

       // cbAutoLogin.setOnCheckedChangeListener(mClickListener);
        tvFindPw.setOnClickListener(onClickListener);
        btnLogin.setOnClickListener(onClickListener);
        btnSignup.setOnClickListener(onClickListener);


        //아이디 이메일 형식 체크 ------------------------------------------------------------------------

        tvErrorId = findViewById(R.id.tv_errorId);
        Eid = findViewById(R.id.et_id);
        Eid.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {



                String sid = Eid.getText().toString().trim();

                if(sid.length() == 0){   //null값일때, 메세지 표시안하도록

                    tvErrorId.setText("");

                }else {


                    if (sid.matches(emailValidation) && s.length() > 0) {

                        tvErrorId.setText("올바른 이메일 형식입니다.");//글씨.

                        Eid.setBackgroundResource(R.drawable.et_black); //박스.


                        String strColor = "#0000FF";

                        tvErrorId.setTextColor(Color.parseColor(strColor));

                        //     inputEmail.setBackgroundResource(R.drawable.edt_bg_selelctor);

                        //     btnReset.setBackgroundColor(Color.parseColor("#007ed6"));

                    } else {

                        tvErrorId.setText("올바른 이메일 형식이 아닙니다."); //글씨.

                        String strColor2 = "#FF0000"; //색깔.

                        tvErrorId.setTextColor(Color.parseColor(strColor2)); //글씨색깔.

                        Eid.setBackgroundResource(R.drawable.et_red); //박스


                        //      inputEmail.setBackgroundResource(R.drawable.edt_bg_wrong_validate);

                        //     btnReset.setBackgroundColor(Color.parseColor("#c0c0c0"));
                    }

                }
            }
        });



    //[카카오로그인]-----------------------------------------------------------------------------------

    //onCreate 시 SessionCallback을 초기화하고, 현재 세션에 콜백을 붙임.

        sessionCallback = new SessionCallback(); //SessionCallback 초기화.
        Session.getCurrentSession().addCallback(sessionCallback); //현재 세션에 콜백 붙임.

   //     Session.getCurrentSession().checkAndImplicitOpen();  //자동로그인 (앱 실행 시 로그인 토큰이 있으면 자동으로 로그인 수행)@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 카카오 자동로그인.
        //checkAndImplicitOpen()함수는 현재 앱에 유효한 카카오로그인 토큰이 있다면 바로 로그인을 시켜주는 함수임.
        //즉, 이전에 로그인한 기록이 있다면, 다음번에 앱을 켰을때 자동으로 로그인시켜줌.
        //checkAndImplicitOpen() 함수는 앱이 실행될 때 가장 먼저 켜지는 Activity의 onCreate 안에 있어야 정상적으로 작동합니다.
        // 여기서는 LoginActivity가 가장 먼저 실행되기 때문에 LoginActivity의 onCreate 안에 checkAndImplicitOpen() 함수가 있지만,
        // 만약 앱의 시작점이 로그인 Activity가 아닌 다른 Activity(예를 들어 로딩 화면 Activity 등)라면, 로그인 Activity가 아닌 시작점인
        // Activity의 onCreate 안에 checkAndImplicitOpen()를 넣어주어야함.


        //커스텀 카카오 로그인 버튼
        tvKakao = findViewById(R.id.tv_kakao);
        tvKakao.setOnClickListener(kakaoClickListener);

    //--------------------------------------------
    //    getAppKeyHash(); //해시 키.
    //--------------------------------------------

    //[구글로그인]-------------------------------------------------------------------------------------

        GoogleSignInOptions googleSignInOptions = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        Log.v(TAG,"googleSignInOptions = " + googleSignInOptions);

        googleApiClient = new GoogleApiClient.Builder(this)
//                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, googleSignInOptions)
                .build();

        auth = FirebaseAuth.getInstance(); //파이어베이스 인증 객체 초기화.
        Log.v(TAG,"auth = " + auth);

        tvGoogle = findViewById(R.id.tv_google);
        tvGoogle.setOnClickListener(googleClickListener);

    }//@@@@@@@@@@@@@@@@@@@@@ OnCreate 끝. 여기부터 메서드. @@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@



    //[자동로그인]관련 메서드 2개 -------------------------------------------------------------------------

    private void save(){

        // SharedPreferences 객체만으론 저장 불가능 Editor 사용
        SharedPreferences.Editor editor = appData.edit();

        // 에디터객체.put타입( 저장시킬 이름, 저장시킬 값 )
        // 저장시킬 이름이 이미 존재하면 덮어씌움
        editor.putBoolean("SAVE_LOGIN_DATA", cbAutoLogin.isChecked());
        editor.putString("ID", Eid.getText().toString().trim());
        editor.putString("PWD", Epw.getText().toString().trim());

        // apply, commit 을 안하면 변경된 내용이 저장되지 않음
        editor.apply();

    }

    // 설정값을 불러오는 함수
    private void load() {
        // SharedPreferences 객체.get타입( 저장된 이름, 기본값 )
        // 저장된 이름이 존재하지 않을 시 기본값
        saveLoginData = appData.getBoolean("SAVE_LOGIN_DATA", false);
         id = appData.getString("ID", "");
         pwd = appData.getString("PWD", "");
    }


    //올바른 아이디와 비밀번호가 입력되었는지 확인하는것(아이디와 비밀번호가 일치하는지 확인하는 것.)-----------------------
    private boolean loginValidation(String id, String password){

        SharedPreferences loginInfo = getSharedPreferences("setting",0);
        SharedPreferences.Editor editor = loginInfo.edit();

            //Preference에서 받아온 정보아 일치하면 true.
            if(loginInfo.getString("id", "").equals(sid) && loginInfo.getString("pw", "").equals(spw)){ //로그인 성공.
                return true;
            //저장된 정보가 없으면 false.
            }else if(loginInfo.getString("id","").equals(null)){
                return false;
            //저장된 정보와 일치하지 않으면.
            }else{
                return false;
            }
    }



    //=== 버튼 액션 및 기본 로그인 기능 =================================================================================


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            tvErrorId = findViewById(R.id.tv_errorId);
            tvErrorPw = findViewById(R.id.tv_errorPw);



            switch(v.getId()) {

                //패스워드 찾기 클릭시.
                case R.id.tv_findPw:
                    Intent intentPw = new Intent(LoginActivity.this, FindPwActivity.class);
                    startActivity(intentPw);
                    break;

                //회원가입 클릭 시.
                case R.id.btn_Signup:
                    Intent intentSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                    startActivity(intentSignUp);
                    break;

                // 로그인 버튼 클릭 시
                case R.id.btn_Login:
                    sid = Eid.getText().toString().trim();
                    spw = Epw.getText().toString().trim();

                    if (sid.length() == 0) {
                        tvErrorId.setText("아이디를 엽력해주세요.");
                        String strColor = "#FF0000";
                        tvErrorId.setTextColor(Color.parseColor(strColor));
                        //커서이동
                        Eid.requestFocus();
                        Eid.setCursorVisible(true);


                       /* new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("아이디를 입력해주세요!!")
                                .setMessage("")
                                .setPositiveButton("확인", null)
                                .show();*/

                    } else if (spw.length() == 0) {

                        tvErrorPw.setText("패스워드를 입력해주세요.");
                        String strColor = "#FF0000";
                        tvErrorPw.setTextColor(Color.parseColor(strColor));
                        //커서이동.
                        Epw.requestFocus();
                        Epw.setCursorVisible(true);

                        /*new AlertDialog.Builder(LoginActivity.this)
                                .setTitle("패스워드를 입력해주세요!!")
                                .setMessage("")
                                .setPositiveButton("확인", null)
                                .show();*/

                    } else{

                        //----------
                            save();
                        //----------
                        Intent intent = getIntent();

                        //아이디와 비번을 비교해 count값을 리턴받는것.
                        urlAddr3 = "http://" + macIP + ":8080/JSP/loginCheck.jsp?"; //@@@@@@@@@@@@@@@@@@@@@@@@ JSP폴
                        urlAddr3 = urlAddr3 + "id=" + sid + "&pw=" + spw;

                        //다음 페이지로 값을 넘겨주는것.
        /*              urlAddr4 = "http://" + macIP + ":8080/test/loginGetData.jsp?";
                        urlAddr4 = urlAddr4 + "id=" + sid + "&pw=" + spw;*/
                        //이 방식 대신 PreferenceManager.setString방식을 쓰기로 함.

                        int count = connectLoginCheckData();
                        Log.v(TAG, "count =" + count);

                        switch (count) {
                            case 0: //아이디와 패스워드 일치하는 정보 없음.

                                tvErrorPw.setText("아이디와 비밀번호가 맞는지 다시 확인해주세요.");
                                String strColor = "#FF0000";
                                tvErrorPw.setTextColor(Color.parseColor(strColor));

                               /* new AlertDialog.Builder(LoginActivity.this)
                                        .setTitle("[ID와 Password 불일치!!]")
                                        .setMessage("- ID와 Password가 맞는지 다시 확인해주세요. -")
                                        .setPositiveButton("확인", null)
                                        .show();*/
                                break;

                            case 1: //아이디와 패스워드 일치.

                                //SharedPreference에 저장.(아이디와 패스워드값)

                                sid = Eid.getText().toString().trim();
                                spw = Epw.getText().toString().trim();


                                SharedPreferences loginInfo = getSharedPreferences("setting",0);
                                SharedPreferences.Editor editor = loginInfo.edit();

                                editor.putString("id", sid); //id라는 키값으로 저장.
                                editor.putString("pw", spw); //pw라는 키값으로 저장.
                                editor.commit();                                  //빼먹지말것(중요!!!)

                                Intent intentLogIn = new Intent(LoginActivity.this, MainActivity.class);

                                // 저장한 키 값으로 저장된 아이디와 암호를 불러와 String 값에 저장
                                String checkId = loginInfo.getString("id","");
                                String checkPw = loginInfo.getString("pw","");
                                Log.v(TAG, "String checkId.end =" + checkId);
                                Log.v(TAG, "String checkPw.end =" + checkPw);

                                startActivity(intentLogIn);
                                finish();
                                break;

                        }
                    }
                    break;

            }



        }
    };

    private int connectLoginCheckData(){
        int result = 0;

        try{

            /////////////////////////////////////////////////////////////////////////////////////
            // Description:
            //  - NetworkTask를 한곳에서 관리하기 위해 기존 CUDNetworkTask 삭제
            //  - NetworkTask의 생성자 추가 : where
            //
            ///////////////////////////////////////////////////////////////////////////////////////

            //NetworkTask 생성자.
            NetworkTask_LogIn networkTask_LogIn3 = new NetworkTask_LogIn(LoginActivity.this, urlAddr3, "loginCheck");
            ///////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////////////////////////
            // Description:
            //  - 입력 결과 값을 받기 위해 Object로 return
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Object obj = networkTask_LogIn3.execute().get();
            result = (int) obj;
            Log.v(TAG,"connectLoginCheckData :"+result);
            ///////////////////////////////////////////////////////////////////////////////////////

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;

    }

    //[KaKao 로그인관련]================================================================================================================

    View.OnClickListener kakaoClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);

        }
    };


    @Override
    //카카오 로그인 액티비티에서 결과값을 가져오는 부분으로, 그대로 쓰면 된다.
    //구글 로그인 인증을 요청했을때 결과 값을 되돌려받는곳.
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        //@@@@@@@@@@@@@@@@@ 카카오 로그인 화면에서 값이 넘어온 경우 처리@@@@@@@@@@@@@@@@@@@@@@@@@@@
        if(Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }

        //@@@@@@@@@@@@@@@@@ 구글  로그인 화면에서 값이 넘어온 경우 처리 @@@@@@@@@@@@@@@@@@@@@@@@@@@
        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() == true) { //인증결과가 성공적이면.
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 모든 구글로그인 정보가 다 들어가있다.(닉네임, 프로필사진url,이메일, 주소 등)
                resultLogin(account); //로그인 결과값 출력 수행하라는 메서드.
            }
        }
    }


    @Override
    protected void onDestroy() {  //onCreat에서 현재 세션에 콜백을 붙였다면, 여기에서 콜백을 제거해야함. 네이버, 구글과 같든 다름 API와 같이 사용시,콜백제거를 안해주면 로그아웃에 문제가 생김.
        //Activity Destroy 시 카카오 로그인 콜백 제거
        //이 코드가 없으면 타 로그인 플랫폼과 연동 시 오류가 발생할 가능성이 높다.
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallback);
    }

    //카카오 로그인 콜백
    private class SessionCallback implements ISessionCallback {
        @Override

        //onSessionOpened에서는 로그인을 수행하고, 그정보를 받아오는 역할임
        public void onSessionOpened() { //로그인 세션이 성공적으로 열린 경우
            UserManagement.getInstance().me(new MeV2ResponseCallback() { //유저 정보를 가져온다.(me는 유저정보를 가져오는 함수임)

                //밑에 3가지 경우에 대한 처리를 구현함.

                //1.로그인이 실패했을때 호출되는 함수임.
                @Override
                public void onFailure(ErrorResult errorResult) { //유저 정보를 가져오는 데 실패한 경우
                    int result = errorResult.getErrorCode(); //오류 코드를 받아온다.

                    if (result == ApiErrorCode.CLIENT_ERROR_CODE) { //클라이언트 에러인 경우: 네트워크 오류
                        Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    } else { //클라이언트 에러가 아닌 경우: 기타 오류
                        Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                    }
                }

                //2. 로그인 도중 세션이 비정상적인 이유로 닫혔을 때 작동하는 함수임.(이 오류는 거의 없으므로 무시해도됨)
                @Override
                public void onSessionClosed(ErrorResult errorResult) { //세션이 도중에 닫힌 경우
                    Toast.makeText(getApplicationContext(), "세션이 닫혔습니다. 다시 시도해 주세요: " + errorResult.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }

                //3.로그인에 성공하였을 경우 호출됨.
                @Override
                public void onSuccess(MeV2Response result) { //유저 정보를 가져오는데 성공한 경우
                    //MeV2Response는 유저의 정보를 담고있는 아주 중요한 객체임. 보틍은 이개체에서 필요한 정보를 가져온뒤 용도에 맞 처리함.

                    String needsScopeAutority = ""; //이메일, 성별, 연령대, 생일 정보 가져오는 권한 체크용

                    if (result.getKakaoAccount().needsScopeAccountEmail()) { //이메일 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + "이메일";
                    }
                    if (result.getKakaoAccount().needsScopeGender()) { //성별 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 성별";
                    }
                    if (result.getKakaoAccount().needsScopeAgeRange()) { //연령대 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 연령대";
                    }
                    if (result.getKakaoAccount().needsScopeBirthday()) { //생일 정보를 가져오는 데 사용자가 동의하지 않은 경우
                        needsScopeAutority = needsScopeAutority + ", 생일";
                    }

                    if (needsScopeAutority.length() != 0) { //거절된 권한이 있는 경우
                        //거절된 권한을 허용해달라는 Toast 메세지 출력
                        if (needsScopeAutority.charAt(0) == ',') {
                            needsScopeAutority = needsScopeAutority.substring(2);
                        }
                        Toast.makeText(getApplicationContext(), needsScopeAutority + "에 대한 권한이 허용되지 않았습니다. 개인정보 제공에 동의해주세요.", Toast.LENGTH_SHORT).show();

                        //회원탈퇴 수행
                        //회원탈퇴에 대한 자세한 내용은 MainActivity의 회원탈퇴 버튼 참고
                        UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                            @Override
                            public void onFailure(ErrorResult errorResult) {
                                int result = errorResult.getErrorCode();

                                if (result == ApiErrorCode.CLIENT_ERROR_CODE) {
                                    Toast.makeText(getApplicationContext(), "네트워크 연결이 불안정합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(getApplicationContext(), "오류가 발생했습니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onSessionClosed(ErrorResult errorResult) {
                                Toast.makeText(getApplicationContext(), "로그인 세션이 닫혔습니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onNotSignedUp() {
                                Toast.makeText(getApplicationContext(), "가입되지 않은 계정입니다. 다시 로그인해 주세요.", Toast.LENGTH_SHORT).show();
                            }

                            @Override
                            public void onSuccess(Long result) {
                            }
                        });

                    } else { //모든 정보를 가져오도록 허락받았다면
                        //MainActivity로 넘어가면서 유저 정보를 같이 넘겨줌
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        //intent.putExtra("name", result.getNickname()); //유저 이름(String)
                        //intent.putExtra("profile", result.getProfileImagePath()); //유저 프로필 사진 주소(String)

                        if (result.getKakaoAccount().hasEmail() == OptionalBoolean.TRUE) {
                            intent.putExtra("email", result.getKakaoAccount().getEmail()); //이메일이 있다면 -> 이메일 값 넘겨줌(String)

                            SharedPreferences loginInfo = getSharedPreferences("setting", 0);
                            SharedPreferences.Editor editor = loginInfo.edit();

                            String email = result.getKakaoAccount().getEmail();
                            editor.putString("id", email); //SharedPreference에 emil값을 아이디로 저장.
                            editor.commit();

                            String checkId = loginInfo.getString("id", ""); //저장이 잘되었는지 확인.
                            Log.v(TAG, "Kakao email ID :" + checkId);

                        } else
                            intent.putExtra("email", "none"); //이메일이 없다면 -> 이메일 자리에 none 집어넣음.
                        /*if (result.getKakaoAccount().hasAgeRange() == OptionalBoolean.TRUE)
                            intent.putExtra("ageRange", result.getKakaoAccount().getAgeRange().getValue()); //연령대 정보 있다면 -> 연령대 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("ageRange", "none");
                        if (result.getKakaoAccount().hasGender() == OptionalBoolean.TRUE)
                            intent.putExtra("gender", result.getKakaoAccount().getGender().getValue()); //성별 정보가 있다면 -> 성별 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("gender", "none");
                        if (result.getKakaoAccount().hasBirthday() == OptionalBoolean.TRUE)
                            intent.putExtra("birthday", result.getKakaoAccount().getBirthday()); //생일 정보가 있다면 -> 생일 정보를 String으로 변환해서 넘겨줌
                        else
                            intent.putExtra("birthday", "none");*/

                        startActivity(intent);
                        finish();
                    }
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException e) { //로그인 세션을 여는 도중 오류가 발생한 경우 -> Toast 메세지를 띄움.
            //참고로, 인터넷이 아예 연결되어 있지않아도 onSessionOpenFailed가 작동함. 반면 인터넷 연결이 불안정한 경우(와이파이신호가 약하거나 등등) onSessionOpened가 실행됨.
            Toast.makeText(getApplicationContext(), "로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }



 /*       //-------- 해시 키 찾는 코드 -----------------------------------------------------------------
        private void getAppKeyHash() {
            try {
                PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
                for (Signature signature : info.signatures) {
                    MessageDigest md;
                    md = MessageDigest.getInstance("SHA");
                    md.update(signature.toByteArray());
                    String something = new String(Base64.encode(md.digest(), 0));

                    Log.e(TAG,"Hash key : " + something);
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                Log.e(TAG,"name not found" + e.toString());
            }
        }*/

    //[구글로그인관련] ============================================================================================================================


    View.OnClickListener googleClickListener = new View.OnClickListener() { //구글 로그인 버튼을 클릭했을때.
        @Override
        public void onClick(View v) {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, REQ_SIGN_GOOGLE);
        }
    };


/*    @Override @@@@@@@@@@@@@ 카카오 로그인쪽에 합침 @@@@@@@@@@@@@@@@@@@@@@@2
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) { //구글 로그인 인증을 요청했을때 결과 값을 되돌려받는곳.
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQ_SIGN_GOOGLE) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess() == true) { //인증결과가 성공적이면.
                GoogleSignInAccount account = result.getSignInAccount(); // account라는 데이터는 모든 구글로그인 정보가 다 들어가있다.(닉네임, 프로필사진url,이메일, 주소 등)
                resultLogin(account); //로그인 결과값 출력 수행하라는 메서드.
            }
        }
    }*/


    private void resultLogin(final GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        auth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>(){
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){ //로그인이 성공했으면.
                            Toast.makeText(LoginActivity.this, "로그인 성공", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                            intent.putExtra("nickName", account.getDisplayName());
                            intent.putExtra("photoUrl", String.valueOf(account.getPhotoUrl())); //String.valueOf() 특저 자료형을 Stirng 형태로 변환.
                            intent.putExtra("email", account.getEmail());

                            startActivity(intent);
                            finish();

                        }else{ //로그인이 실패했으면.
                            Toast.makeText(LoginActivity.this, "로그인 실패", Toast.LENGTH_SHORT).show();

                        }

                    }
                });
    }

    /*@Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }*/

    //================================================================================================================================================

    //editText 외의 화면 클릭시 키보드 숨기기
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        View focusView = getCurrentFocus();
        if (focusView != null) {
            Rect rect = new Rect();
            focusView.getGlobalVisibleRect(rect);
            int x = (int) ev.getX(), y = (int) ev.getY();
            if (!rect.contains(x, y)) {
                InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                if (imm != null)
                    imm.hideSoftInputFromWindow(focusView.getWindowToken(), 0);
                focusView.clearFocus();
            }
        }
        return super.dispatchTouchEvent(ev);
    }

}//-------------------