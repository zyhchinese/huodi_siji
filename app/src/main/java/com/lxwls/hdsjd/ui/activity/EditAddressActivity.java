package com.lxwls.hdsjd.ui.activity;

import android.content.Intent;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lxwls.hdsjd.R;
import com.lxwls.hdsjd.adapter.SpinnerAdapter;
import com.lxwls.hdsjd.api.PileApi;
import com.lxwls.hdsjd.base.activity.BackCommonActivity;
import com.lxwls.hdsjd.bean.ProvinceEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;

public class EditAddressActivity extends BackCommonActivity implements View.OnClickListener {
    private Spinner spinner_city, spinner_province, spinner_county;
    private List<ProvinceEntity> provinceEntityList;
    private List<ProvinceEntity> cityList;
    private List<ProvinceEntity> countyList;
    private EditText ed_shouhuoren, ed_phone_number, ed_guding, ed_address, ed_youbian, ed_biaoqian;
    private Button btn_save;
    private boolean isOk;
    private int i;
    private int n = 1;

    private ProvinceEntity provinceEntity;
    private ProvinceEntity provinceEntity1;
    private ProvinceEntity provinceEntity2;


    private int provincePosition;
    private int cityPosition;
    private int countyPosition;



    @Override
    protected int getContentView() {
        return R.layout.activity_edit_address;
    }

    @Override
    protected void initWidget() {
        super.initWidget();
        setTitleText("编辑地址");


        initView();

        initSpinner();

        //请求省份
        PileApi.instance.loadProvince()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {
                        try {
                            String body = responseBody.string();
                            System.out.println(body);

                            if (body.indexOf("false") != -1 || body.length() < 3) {
                                showToast("获取信息失败，请重试");
                            } else {
                                Gson gson = new Gson();
                                provinceEntityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                provinceEntityList.add(0, new ProvinceEntity("请选择省份"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(EditAddressActivity.this, provinceEntityList);
                                spinner_province.setAdapter(adapter2);



                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        System.out.println("2222");
                    }

                    @Override
                    public void onComplete() {
                        System.out.println();
                    }
                });

    }

    //城市查询
    private void initCityHttp(int areaid) {

        HashMap<String, String> map = new HashMap<>();
        map.put("provinceid", areaid + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCity(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                cityList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                cityList.add(0, new ProvinceEntity("请选择城市"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(EditAddressActivity.this, cityList);
                                spinner_city.setAdapter(adapter2);

                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void initView() {
        spinner_city = (Spinner) findViewById(R.id.spinner_city);
        spinner_province = (Spinner) findViewById(R.id.spinner_province);
        spinner_county = (Spinner) findViewById(R.id.spinner_county);
        ed_shouhuoren = (EditText) findViewById(R.id.ed_shouhuoren);
        ed_phone_number = (EditText) findViewById(R.id.ed_phone_number);
        ed_guding = (EditText) findViewById(R.id.ed_guding);
        ed_address = (EditText) findViewById(R.id.ed_address);
        ed_youbian = (EditText) findViewById(R.id.ed_youbian);
        ed_biaoqian = (EditText) findViewById(R.id.ed_biaoqian);

        btn_save = (Button) findViewById(R.id.btn_save);

        btn_save.setOnClickListener(this);
        initSpinnertext();
        initEdittextListener();


    }

    private void initSpinnertext() {

    }

    private void initEdittextListener() {
        i = 1;

        ed_shouhuoren.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                i = 1;
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                i = 2;
            }

            @Override
            public void afterTextChanged(Editable s) {

                if (s.toString().indexOf(" ") != -1) {
                    i = 1;

                    isOk = false;
                } else {
                    ed_phone_number.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                            i = 2;
                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {

                            i = 3;
                        }

                        @Override
                        public void afterTextChanged(Editable s) {
                            boolean phone = phone(s.toString());

                            if (s.toString().indexOf(" ") != -1||!phone) {
                                i = 2;


                                isOk = false;
                            } else {
                                ed_address.addTextChangedListener(new TextWatcher() {
                                    @Override
                                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                                        i = 3;
                                    }

                                    @Override
                                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                                    }

                                    @Override
                                    public void afterTextChanged(Editable s) {

                                        if (s.toString().indexOf(" ") != -1) {

                                            i = 3;
                                            isOk = false;
                                        } else {

                                            i = 4;

                                        }
                                    }
                                });
                            }
                        }
                    });
                }
            }
        });


    }

    private boolean phone(String mobiles){
        Pattern p = Pattern.compile("^(13[0-9]|14[57]|15[0-35-9]|17[6-8]|18[0-9])[0-9]{8}$");
        Matcher m = p.matcher(mobiles);
        return m.matches();
    }

    private void initSpinner() {

        spinner_province.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                provincePosition = spinner_province.getSelectedItemPosition();

                initCityHttp(provinceEntityList.get(position).getAreaid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                System.out.println("1111");

            }
        });

        spinner_city.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                cityPosition = spinner_province.getSelectedItemPosition();
                initCountyHttp(cityList.get(position).getAreaid());

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spinner_county.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                countyPosition = spinner_province.getSelectedItemPosition();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


    }


    //县区查询
    private void initCountyHttp(int areaid) {
        HashMap<String, String> map = new HashMap<>();
        map.put("cityid", areaid + "");
        Gson gson = new Gson();
        String params = gson.toJson(map);
        PileApi.instance.loadCountry(params)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {

                    }

                    @Override
                    public void onNext(@NonNull ResponseBody responseBody) {

                        try {
                            String body = responseBody.string();
                            System.out.println(body);
                            if (body.indexOf("false") != -1 || body.length() < 2) {
                                showToast("获取信息失败，请重试");
                            } else {


                                Gson gson = new Gson();
                                countyList = gson.fromJson(body, new TypeToken<List<ProvinceEntity>>() {
                                }.getType());
                                countyList.add(0, new ProvinceEntity("请选择县区"));

                                SpinnerAdapter adapter2 = new SpinnerAdapter(EditAddressActivity.this, countyList);
                                spinner_county.setAdapter(adapter2);

                            }
                        } catch (IOException e) {
                        }
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_save:


//                int id = spinner_city.getSelectedItemPosition();
                if (spinner_county.getSelectedItemPosition() != 0&&i>3) {
                    isOk = true;
                }
                if (!isOk) {
                    if (i == 1) {
                        Toast.makeText(EditAddressActivity.this, "请输入收货人姓名", Toast.LENGTH_SHORT).show();
                    } else if (i == 2) {
                        Toast.makeText(EditAddressActivity.this, "请输入正确的联系方式", Toast.LENGTH_SHORT).show();
                    } else if (spinner_province.getSelectedItemPosition() == 0) {
                        Toast.makeText(EditAddressActivity.this, "请选择省份", Toast.LENGTH_SHORT).show();
                    } else if (spinner_city.getSelectedItemPosition() == 0) {
                        Toast.makeText(EditAddressActivity.this, "请选择城市", Toast.LENGTH_SHORT).show();
                    } else if (spinner_county.getSelectedItemPosition() == 0) {
                        Toast.makeText(EditAddressActivity.this, "请选择县区", Toast.LENGTH_SHORT).show();
                    } else if (i == 3) {
                        Toast.makeText(EditAddressActivity.this, "请输入详细地址", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    //请求添加地址
                    HashMap<String, String> map = new HashMap<>();

                    provinceEntity = (ProvinceEntity) spinner_province.getSelectedItem();
                    map.put("provinceid", provinceEntity.getAreaid() + "");

                    provinceEntity1 = (ProvinceEntity) spinner_city.getSelectedItem();
                    map.put("cityid", provinceEntity1.getAreaid() + "");

                    provinceEntity2 = (ProvinceEntity) spinner_county.getSelectedItem();
                    map.put("areaid", provinceEntity2.getAreaid() + "");

                    map.put("address", ed_address.getText().toString());
                    map.put("isdefault", "1");
                    map.put("shcustname", ed_shouhuoren.getText().toString());
                    map.put("shphone", ed_phone_number.getText().toString());
                    map.put("taxphone", ed_guding.getText().toString());
                    map.put("postcode", ed_youbian.getText().toString());
                    map.put("addresslabel", ed_biaoqian.getText().toString());
                    Gson gson = new Gson();
                    String data = gson.toJson(map);
                    PileApi.instance.addCustAddress(data)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(new Observer<ResponseBody>() {
                                @Override
                                public void onSubscribe(@NonNull Disposable d) {

                                }

                                @Override
                                public void onNext(@NonNull ResponseBody responseBody) {

                                    try {
                                        String body = responseBody.string();
                                        System.out.println(body);
                                        if (body.indexOf("false") != -1 || body.length() < 6) {
                                            showToast("获取信息失败，请重试");
                                        } else {
                                            Intent intent=new Intent();
                                            intent.putExtra("province",provincePosition);
                                            intent.putExtra("city",cityPosition);
                                            intent.putExtra("county",countyPosition);
                                            setResult(2,intent);
                                            finish();
                                            Toast.makeText(EditAddressActivity.this, "保存成功", Toast.LENGTH_SHORT).show();

                                            sendPayLocalReceiver();


                                        }
                                    } catch (IOException e) {
                                    }
                                }

                                @Override
                                public void onError(@NonNull Throwable e) {

                                }

                                @Override
                                public void onComplete() {

                                }
                            });
                }

                break;
        }
    }
}
