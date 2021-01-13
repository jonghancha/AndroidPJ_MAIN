package com.android.androidpj_main.UserSign;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.androidpj_main.NetworkTask.FindNetworkTask;
import com.android.androidpj_main.R;
import com.android.androidpj_main.Share.ShareVar;

import javax.mail.MessagingException;
import javax.mail.SendFailedException;

public class FindPwActivity extends AppCompatActivity {


                    //이강후

    final static String TAG = "FindPwActivity";
    String urlAddr = null;

    int count3; //인증확인용 @@@@@@@@@@@@@@
    int countAuth=3; //인증횟수 체크용.

    String sid, sauth;

    EditText Eid, Eauth; //인증번호입력@@@@@@@@@@
    TextView tvErrorId, tvErrorEmailAuth; //입력오류 디스플레이.
    Button btn_SendEmail, btn_ChkAuth, btn_FindPw;

    String macIP = ShareVar.macIP;
    String emailCode = createEmailCode(); //생성된 랜덤 이메일 코드 저장.

    //아이디 이메일 양싱.
    private String emailValidation = "^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    //카운트다운 타이머에 관련된 필드.----------------------------------------------
    TextView time_counter; //시간을 보여주는 TextView.
    TextView time_counter_pre; //@@@@@@@@@@@@@@@ 내가 한것.
    CountDownTimer countDownTimer;
    final int MILLISINFUTURE = 180 * 1000;  //총시간 (180초 = 3분)
    final int COUNT_DOWN_INTERVAL = 1000;    //onTick 메소드를 호출할 간격(1초)
    //-----------------------------------------------------------------------


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_findpw);

     //EditText 연결.
     Eid = findViewById(R.id.et_id);
     Eauth = findViewById(R.id.et_Auth);
     //입력시 자릿수 제한.
     Eid.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(26)});
     Eauth.setFilters(new InputFilter[]{ new InputFilter.LengthFilter(8)}); //인증번호 8자리로 제한둠.

     //TextView(입력오류 디스플레이)
     tvErrorId = findViewById(R.id.tv_errorId);
     tvErrorEmailAuth = findViewById(R.id.tv_errorAuth);


     //버튼 연결.
     btn_SendEmail = findViewById(R.id.btn_SendEmail);
     btn_ChkAuth = findViewById(R.id.btn_ChkAuth);
     btn_FindPw = findViewById(R.id.btn_FindPw);

     //ClickListener
     btn_SendEmail.setOnClickListener(mailClickListener); //메일보내기 버튼.
     btn_ChkAuth.setOnClickListener(mailClickListener);   //인증버튼.
     btn_FindPw.setOnClickListener(onClickListener);    //패스워드찾기 버튼.

        //------------------------------------------------------------------------------------------
        //onCreat에 인터넷 사용을 위한 권한을 허용해주는것.
        StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
                .permitDiskReads()
                .permitDiskWrites()
                .permitNetwork().build());

        //여기까지 이메일 사용을 위한 준비 끝남.-------------------------------------------------------------

        //아이디 이메일 형식 체크 ------------------------------------------------------------------------


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


    }// onCreat 끝.==================================================================================

    //---이메일 인증관련   --------------------------------------------------------------------------------------------------------------------------

    View.OnClickListener mailClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            switch(v.getId()) {

                // 이메일로 인증번호 전송 버튼 클릭.
                case R.id.btn_SendEmail:

                    try {
                        GMailSender gMailSender = new GMailSender("imkanghoo@gmail.com", "magneto1203");
                        //GMailSender.sendMail(제목, 본문내용, 받는사람);
                        gMailSender.sendMail("인증번호입니다.", "인증번호 : " + emailCode, Eid.getText().toString()); //@@@@@@@@@@@@
                        Log.v(TAG, "emailCode(send) :" + emailCode);

                        tvErrorId.setText("이메일로 인증번호를 보냈습니다.");//글씨
                        String strColor = "#0000FF";  //색깔.
                        tvErrorId.setTextColor(Color.parseColor(strColor));//글씨색깔.
                        Eid.setBackgroundResource(R.drawable.et_black); //박스.

                        //------------------
                        countDownTimer();       //타이머 실행 @@@@@@@@@@@@@@@@@@@@@@@@@@@@@
                        //------------------

                       /* new AlertDialog.Builder(FindPwActivity.this)
                                .setTitle("[이메일로 인증번호를 보냈습니다!!]")
                                .setMessage("인증번호를 확인하고 인증해주세요.")
                                .setPositiveButton("확인", null)
                                .show();*/

                        // Toast.makeText(getApplicationContext(), "이메일을 성공적으로 보냈습니다.", Toast.LENGTH_SHORT).show();

                    } catch (SendFailedException e) {

                        tvErrorId.setText("잘못된 이메일입니다."); //글씨
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorId.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eid.setBackgroundResource(R.drawable.et_red); //박스


                        /*new AlertDialog.Builder(FindPwActivity.this)
                                .setTitle("[이메일이 잘못되었습니다!!]")
                                .setMessage("이메일 형식을 확인하시고 다시 입력해주세요.")
                                .setPositiveButton("확인", null)
                                .show();*/

                        //Toast.makeText(getApplicationContext(), "이메일 형식이 잘못되었습니다.", Toast.LENGTH_SHORT).show();

                    } catch (MessagingException e) {

                        tvErrorId.setText("e-mail을 입력해주세요.");//글씨.
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorId.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eid.setBackgroundResource(R.drawable.et_red); //박스

                        /*new AlertDialog.Builder(FindPwActivity.this)
                                .setTitle("[인터넷 연결을 확인해주세요!!]")
                                .setMessage("")
                                .setPositiveButton("확인", null)
                                .show();*/

                        //  Toast.makeText(getApplicationContext(), "인터넷 연결을 확인해주십시오", Toast.LENGTH_SHORT).show();

                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    break;


                //인증버튼 클릭---------------------------------------------------------------------------

                case R.id.btn_ChkAuth:

                    String etAuchResult = Eauth.getText().toString().trim();

                    if (etAuchResult.length() == 0) {

                        tvErrorEmailAuth.setText("인증번호 입력해주세요.");//글씨.
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eauth.setBackgroundResource(R.drawable.et_red); //박스

                    }else{
                            if (emailCode.equals(etAuchResult)) {
                                Log.v(TAG, "emailCode(Auth) :" + emailCode);
                                Log.v(TAG, "euAuth(Auth) :" + Eauth);

                                count3 = 1; //@@@@@@@@@@@@@@@@@@@@@@@@@@@@ 인증확인용(1이면 인증됨)
                                Log.v(TAG, "Auth1 :" + count3);

                                tvErrorEmailAuth.setText("인증되었습니다.");//글씨.
                                String strColor = "#0000FF";  //색깔.
                                tvErrorEmailAuth.setTextColor(Color.parseColor(strColor));//글씨색깔.
                                Eauth.setBackgroundResource(R.drawable.et_black); //박스.

                                /*new AlertDialog.Builder(FindPwActivity.this)
                                        .setTitle("[인증되었습니다!!]")
                                        .setMessage("")
         료                                                                 .setPositiveButton("확인", null)
                                        .show();*/

                                //----타이머 종료 -------------------
                                countDownTimer.cancel(); //@@@@@@@@@@@@@@@@내가
                                time_counter_pre.setText("");//@@@@@@@@@@
                                time_counter.setText("");//@@@@@@@@@@@@@
                                emailCode = createEmailCode();
                                //-----------------------


                                break;

                            } else {

                                Log.v(TAG, "emailCode(Autherror) :" + emailCode);
                                Log.v(TAG, "euAuth(Autherror) :" + Eauth);

                                count3 = 0; //@@@@ 인증확인용(0이면 인증안됨)
                                Log.v(TAG, "Auth2 :" + count3);

                                countAuth = countAuth -1; //인증틀릴시 횟수차감용.

                                switch (countAuth){
                                    case 2:
                                        tvErrorEmailAuth.setText("인증번호 불일치 1회.");//글씨.
                                        String strColor2 = "#FF0000"; //색깔.
                                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                                        Eauth.setBackgroundResource(R.drawable.et_red); //박스
                                        break;

                                    case 1:
                                        tvErrorEmailAuth.setText("인증번호가 불일치 2회.");//글씨.
                                        String strColor3 = "#FF0000"; //색깔.
                                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor3)); //글씨색깔.
                                        Eauth.setBackgroundResource(R.drawable.et_red); //박스
                                        break;

                                    case 0: //3회 틀릴시 로그인화면으로 보냄.
                                        Intent intent = new Intent(FindPwActivity.this, LoginActivity.class);
                                        startActivity(intent);
                                        break;



                                }



                                /*new AlertDialog.Builder(FindPwActivity.this)
                                        .setTitle("[인증번호 불일치!!]")
                                        .setMessage("이메일로 받은 인증번호를 다시 확인하고, 재입력해주세요.")
                                        .setPositiveButton("확인", null)
                                        .show();*/

                            }
                            break;
                }
            }
        }};

    //---생성된 이메일 랜덤 인증코드 반환.---------------------------------------------------------------------

    public String getEmailCode() {
        return emailCode;
    } //생성된 이메일 인증코드 반환

    //--- 이메인 랜덤 인증코드 생성.-------------------------------------------------------------------------

    private String createEmailCode() { //이메일 랜덤 인증코드 생성
        String[] str = {"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "l", "m", "n", "o", "p", "q", "r", "s",
                "t", "u", "v", "w", "x", "y", "z", "1", "2", "3", "4", "5", "6", "7", "8", "9"};
        String newCode = new String();

        for (int x = 0; x < 8; x++) {
            int random = (int) (Math.random() * str.length);
            newCode += str[random];
        }

        return newCode;
    }


//--- 패스워드 찾기 메인기능. -------------------------------------------------------------------------------------------------------------------

    View.OnClickListener onClickListener = new View.OnClickListener() { //패스워드찾기 버튼 클릭시.
        @Override
        public void onClick(View v) {

                    sid = Eid.getText().toString().trim();
                    sauth = Eauth.getText().toString().trim();
                    if(sid.length()==0) {

                        tvErrorId.setText("아이디를 입력해주세요.");//글씨.
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorId.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eid.setBackgroundResource(R.drawable.et_red); //박스

                        new AlertDialog.Builder(FindPwActivity.this)
                                .setTitle("아이디를 입력해주세요!!")
                                .setMessage("")
                                .setPositiveButton("확인", null)
                                .show();

                    /*}else if(sid.length()!=0){
                        tvErrorId.setText("");//글씨.
                        String strColor2 = "#0000FF"; //색깔.
                        tvErrorId.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eid.setBackgroundResource(R.drawable.et_black); //박스*/


  /*                  }else if(sauth.length()==0) {

                        tvErrorEmailAuth.setText("인증번호를 입력해주세요.");//글씨.
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eauth.setBackgroundResource(R.drawable.et_red); //박스

                    }else if(sauth.length()!=0){
                        tvErrorEmailAuth.setText("");//글씨.
                        String strColor2 = "#0000FF"; //색깔.
                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eauth.setBackgroundResource(R.drawable.et_black); //박스*/



                    }else{

        /*                Log.v(TAG, "countAuth3 : "+ count3);

                    if(sauth.length()==0){
                        tvErrorEmailAuth.setText("인증번호를 입력해주세요.");//글씨.
                        String strColor2 = "#FF0000"; //색깔.
                        tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                        Eauth.setBackgroundResource(R.drawable.et_red); //박스
                    }else{*/
                        switch(count3){

                            case 0: //이메일 인증안도서 패스워드 못알려줌.

                            tvErrorEmailAuth.setText("인증번호가 일치하지 않습니다. 다시 입력해주세요.");//글씨.
                            String strColor2 = "#FF0000"; //색깔.
                            tvErrorEmailAuth.setTextColor(Color.parseColor(strColor2)); //글씨색깔.
                            Eauth.setBackgroundResource(R.drawable.et_red); //박스

                            new AlertDialog.Builder(FindPwActivity.this)
                                    .setTitle("인증번호가 일치하지 않습니다. 다시 입력해주세요.")
                                    .setMessage("")
                                    .setPositiveButton("확인", null)
                                    .show();
                            break;

                            case 1: //이메일 인증되서 패스워드 알려줌.

                                sid = Eid.getText().toString().trim();

                                Intent intent = getIntent();
                                urlAddr = "http://" + macIP + ":8080/JSP/findPw.jsp?";
                                urlAddr = urlAddr + "id=" + sid;

                                //jsp를 실행해서 Json code를 String으로 받음

                                //---------------------------------
                                String Pw = connectFindPwdata();
                                //---------------------------------

                                if(Pw == null) {

                                    new AlertDialog.Builder(FindPwActivity.this)
                                            .setTitle("패스워드를 찾을 수 없습니다.")
                                            .setMessage("입력값을 다시 확인해주세요.")
                                            .setPositiveButton("확인", null)
                                            .show();

                                }else{

                                    new AlertDialog.Builder(FindPwActivity.this)
                                            .setTitle("패스워는 [" + Pw + "] 입니다.")
                                            .setMessage("")
                                            .setPositiveButton("확인", null)
                                            .show();
                                }

                                break;
                        }

                    }
                    }

            };

    private String connectFindPwdata(){
        String result = null;
        Log.v(TAG, urlAddr);

        try {
            ///////////////////////////////////////////////////////////////////////////////////////
            //
            // Description:
            //  - NetworkTask를 한곳에서 관리하기 위해 기존 CUDNetworkTask 삭제
            //  - NetworkTask의 생성자 추가 : where
            //
            ///////////////////////////////////////////////////////////////////////////////////////

            //NetworkTask 생성자.
            FindNetworkTask findNetworkTask = new FindNetworkTask(FindPwActivity.this, urlAddr, "findPw");
            ///////////////////////////////////////////////////////////////////////////////////////

            ///////////////////////////////////////////////////////////////////////////////////////
            // Date : 2020.12.24
            //
            // Description:
            //  - 입력 결과 값을 받기 위해 Object로 return후에 String으로 변환 하여 사용
            //
            ///////////////////////////////////////////////////////////////////////////////////////
            Object obj = findNetworkTask.execute().get();
            result = (String) obj;
            Log.v(TAG," :"+result);
            ///////////////////////////////////////////////////////////////////////////////////////

        }catch (Exception e){
            e.printStackTrace();
        }
        return result;
    }


    //----Timer관련------------------------------------------------------------------------------

    public void countDownTimer() { //카운트 다운 메소드

        time_counter = findViewById(R.id.time_counter);
        time_counter_pre = findViewById(R.id.time_counter_pre);
        //  time_counter = (TextView) dialogLayout.findViewById(R.id.emailAuth_time_counter);
        //줄어드는 시간을 나타내는 TextView
        //   emailAuth_number = (EditText) dialogLayout.findViewById(R.id.emailAuth_number);
        //사용자 인증 번호 입력창
        //  emailAuth_btn = (Button) dialogLayout.findViewById(R.id.emailAuth_btn);
        //인증하기 버튼


        countDownTimer = new CountDownTimer(MILLISINFUTURE, COUNT_DOWN_INTERVAL) {
            @Override
            public void onTick(long millisUntilFinished) { //(300초에서 1초 마다 계속 줄어듬)

                long emailAuthCount = millisUntilFinished / 1000;
                Log.d("Alex", emailAuthCount + "");

                if ((emailAuthCount - ((emailAuthCount / 60) * 60)) >= 10) { //초가 10보다 크면 그냥 출력
                    time_counter_pre.setText("인증번호를 입력하세요.");//@@@@@@@@@@
                    time_counter.setText((emailAuthCount / 60) + " : " + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                    String strColor = "#FF0000";
                    time_counter.setTextColor(Color.parseColor(strColor));

                } else { //초가 10보다 작으면 앞에 '0' 붙여서 같이 출력. ex) 02,03,04...
                    time_counter_pre.setText("인증번호를 입력하세요.");//@@@@@@@@@@@2
                    time_counter.setText((emailAuthCount / 60) + " : 0" + (emailAuthCount - ((emailAuthCount / 60) * 60)));
                }

                //emailAuthCount은 종료까지 남은 시간임. 1분 = 60초 되므로,
                // 분을 나타내기 위해서는 종료까지 남은 총 시간에 60을 나눠주면 그 몫이 분이 된다.
                // 분을 제외하고 남은 초를 나타내기 위해서는, (총 남은 시간 - (분*60) = 남은 초) 로 하면 된다.

            }


            @Override
            public void onFinish() { //시간이 다 되면 다이얼로그 종료

                countDownTimer.cancel(); //@@@@@@@@@@@@@@@@@ 내가 수정.
                time_counter_pre.setText("[인증코드 만료]");//@@@@@@@@@@
                time_counter.setText("새인증코드를 받아주세요.");//@@@@@@@@@@@@@
                emailCode = createEmailCode(); //코드재성생으로 코드변경.

            }
        }.start();


    }


    //editText 외의 화면 클릭시 키보드 숨기기--------------------------------------------------------------
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


}//---------