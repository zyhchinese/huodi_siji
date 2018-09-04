package com.lxwls.hdsjd.base;

public class Constant {

    //  public static final String BASE_URL ="http://192.168.1.231:8080/showtime/";
  public static final String BASE_URL ="http://192.168.1.236:8280/hdsy/";
//    public static final String BASE_URL ="http://192.168.1.57:7099/lxnew/";
//  public static final String BASE_URL ="http://118.190.47.231:8004/hdsy/";
//  public static final String BASE_URL ="http://www.huodiwulian.com/";
//  public static final String BASE_URL ="http://192.168.1.55/hdsy/";
//  public static final String BASE_URL ="http://192.168.1.45:8080/maibate2/";
//    public static final String BASE_URL = "http://www.maibat.com/maibate/";
    public static final String PAY_URL = "http://www.maibat.com/";
    public static final String BASE_WEBSOCKET_URL = "ws://www.maibat.com/maibate/pcwebsocket";
    //   public static final String BASE_URL ="http://47.93.28.241/maibate/";
//    public static final String BASE_URL = "http://192.168.1.45:8080/maibate2/";
    //    public static final String BASE_URL ="http://192.168.1.55:80/maibate/";
    public static final String HOLD_ACCOUNT = "hold_account";
    public static final int REQUEST_CODE_CAMERA = 1023;//调用相机
    public static final int REQUEST_CODE = 1024;//获取相册
    public static final int COMPRESS_REQUEST_CODE = 2048;
    public static String city = "";
    public static String wl_jgmxcity = "";//物流价格明细地址
    public static boolean cityflag = false;
    public static String WXPAY_STARTNAME = "";//调用微信支付的调用方名字，用于区分广播
    public static String KUAIYUN_SHENGFEN="";

    public static String HUOYUAN_SHENG="";
    public static String HUOYUAN_SHI="";
    /**
     * 支付宝支付业务：入参app_id
     */
//    public static final String APPID = "2017090508564772";
    public static final String APPID = "2018050402631871";

    // 商户PID
    public static final String PARTNER = "2088821027321059";
    // 商户收款账号
    public static final String SELLER = "dingyuru@maibut.com";
    // 商户私钥，pkcs8格式
//    public static final String RSA_PRIVATE = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCWV51I3JmT70UsQzW3ramMgeeJdhGlhAKeZjtTu4pJtzxb4mT41XIvppKHEeYS6N50RRAfT57GbD9zzpV1RCkSGgefPwLcslUiyFAykdA8bk8XT2awGUJZZ5kgCxqLDY0nxd+QESIhLaWTjIqheNf/AP9AMHauAFYna5MWfbWsPGWLDz77hN8tlHBu6C1IGVgFPunCcXypk40nCy2TYbSiPrsvx/e6Ui8T9ylR/NOZS55kI8IsEabl4kcm7lS4mclrSVMBFr+6l8E6P0el6H3SgVO59QeCgyphksS178ngi5umA6h2n+h+xQugIzPbfWlkn1yqiJKQeaiF+JR/ZorfAgMBAAECggEABoPVdQraPObpgHmJImSMLGKUvgg3y4xk8KhNedtuqrMeEn1FEuNtB1OYlfHYnoko2rEDedfhcYfPWB1jrKk/fmFSYzw/y4CO3+r+TrKy62t8Ue5G7OqrTWSH5jOU+uGjdE3G2l9jszxfKH22pDiwY4SPiyCOhAvPr/WhciAQd141oq/tpplbQSw+cSjzogrvojKQHeL7KVSNqSu3Fki0Hs2fk2SV/KLdY/BUUUoBK09l6T/eYuWCtH/usCGDwy7tEvZXgLfaM4VnrkZ9z7SdPkHCbCnbejppR9KmJPIh02AOSUpnTOqPY+1G1IZivgbRdouwG+kX9vZOPc22DOtHgQKBgQDNnH5SOm5BMD4wo75E49R9F4sZbku1yeEgNVPb0CYh4PjwozzGn8RJWcYv29g16DdRHKLt1BNEB9+iAHSiZyewitD0FXQ+KFa0Kat9B275cblYVLunKjX+ShWrv8FgijOpkah+g/Ou30ox6WmDRxzJWYoLvWmEkkubp5nnrE7meQKBgQC7L6/nHjQEKV7v+4iqGtfQXrinLxdChNxXg/sVAf1W8ibtyugXwQ7L3+U7qMXtQfmClZXGZJ9p5bR54IH/xr88CrVxwSV88MHYFztyFT3wScuGwpW2gwnKicsplYZ/6rnefNt4nLDigMWZXv+0gjVnT9jc3G1O46dzno3sC/EGFwKBgFQ3I/pUGnKy6tYLS1R2KMNv0DaDWZlE7eO+U/G1qNi5h90wTyVfrQsKLUXO+xjhWz7qxsU41wdHXk7BdwRJ0hTaVsmSvAD1jsXOR4I3eDnNXcgTNKU8gc780zF8oh0DnjN3CJeBPl9C7+XPn7r4do72ELfMRhrZvQQtOrmMduipAoGAOgg4wrBsc/XXhxM2dXZI/kK/gKVq0qaaIU+7ofGx9yivxP0pI2QpEC/jw7E5W6sejcuOWamMeqpKX5ao9wFI/HZddlzpIGkLz9C0D1RilYJrZOYiwCiz+mTp5YSD5FiDM2UGLch2VmKR4FDBedb8c4EfvKliAhk2KvQ3D71MBhUCgYEAh4q9gD+Z13Udn6esNH9Ns43xRlHZalyb/HzbL0scLCqbz0ksNMZjWtSQ1WwJ9zK5etKZKNqUuCOlYgyQ9I6/QrBbIXRT9DB+TmR0kCy9V/MnE9ynJRFQ+lE5fbYAJmP6Tr70E0SLhSmFh1Ys01HHNEVF1NsedavrZVp4HUmaImY=";
    public static final String RSA_PRIVATE = "MIIEvwIBADANBgkqhkiG9w0BAQEFAASCBKkwggSlAgEAAoIBAQCgl3WH1391d0qNK3H1YYLaByI5Cu9xip7CaGSU/AUvu3jC5JaMVk8f6KvuOF3y9vaJZ/eJBBYIQEN+gNy56pemqYbz4rRDgM80dEBk7EvXWc9FjCcdw1iOpC7liISxc+kjrHffs4bJaDIzxbeg+NWtj9djIxDhhj+FID+jm+6zfsqmiWoKiB29E8WEy9MYjBO5YxxQyjo6E4RIvHl2f/sZck8sCwLu1zNT0mHeBzbCglv5YH5sv7HPau58Zg6CcpVqfHgvBMZWl47Bb17MUONfZEST4pu2s1YsyuCyYGZGXkb0H2+Bgz5A8AAai9fBMaGXeHAEJtCNeel3zjST1WXnAgMBAAECggEBAIlT28q1aqadw1/jNglbQJWHoilbg5T5sHeY4+JPfXPrAgLPjTonGHS1+Ei3ZQe/TuYDxTcjCmA17EHCmn5V8ScxM1ldlT9gaFB31RU4yEY447mad1zJTvJzzjK0wpwo/cZm1v+yOuICu2NwgW4jPD62kb6SHv5sWedSw3j6gG529DxEVVj/sjQLa1za5kJVwQZRBf98pIDMqGXpGWUS24XkbtwpOg3taQxPcH+Ig6PtlprSJekOIyXBXDSa8si00V4Lh/qHEvtpNESr47XWuDJTKvIInQw2avLZeVEPC7wLJZzNwnAOND/PSi3X2Xi0bArZfTtha2wtCK6GGydldhkCgYEA1bnN8ZIwIWt40RCayDtyiMYKNIilM1GANvrrHPYDwCN5dDPu6R32LWjmaPDCoHLgatbgbj6ERx10fWP8WUAYcCb8J+WRAzMMmR53aY+MTouW3LcYlTCcNqERqBLju1QRBPcJT4MDqm/FuIMhpie0lElG6ITtBRTi/OuMRPxaUFUCgYEAwFsowVq08ksaMBrn4dC0+nUwsnZ0HbNNtp6O4N/40VCp7pOYylewnx0BvUiae0atrKs8zm3Tk+9SO/0mnyzEFZ6CJS8KTOMIpbeBV0xVWjF9H45QdYLor1xqjBASJRCcSMyziNCoBThezA5WAImdjcuVxZ3h9rZ8w6FmHFm5aUsCgYEAqwLkJZI6jtMmteoaX3MpvwTvb3+S6BAjjlqRu/ehoqD/8poHZWJC86zHCFr08Fta9EIBDFVblGeeKSLS7TsbEsou42GN+chkITS+iamBs+t/iBW9RsrBg/i3XwvWSrMGdzmPqp6DVWHgDDZou0Fz/V7wrOaHnJRM1+FFuIFPZo0CgYAC9uSv5if3u/SRXTjJoB/J5Fh1x6GOe57s+5CUIp+BCk80IA4wwB02tom6Uw4ebD5s9M6hF8w08XFA/2vC9DupYaH71LfB65XMiAF0SPbfxvd4rwbKaNQRL8lskyrUa2cdMBpsN1r/iZjcaDQnGffVoZeTThWXacSOd0RNl4xl9QKBgQCPZwxmAYkw789CE/jgvEh0Az6ODSkpzkjaRjPOFCqt1wAWfg0UIYf6BYCJMApEZTROqEffQwrTleXLRNyBvbMjlZlw6+gp1hpzvgiiu9/mExcT74VQ6BEesuV5dlbCyl3RfQyUmICEhy27b/Ul21zHE4rPcM1BugE2ZqYmYQg5NA==";
    // 支付宝公钥
    public static final String RSA_PUBLIC = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQDBjLZJco3R1qjPehkzJOWAcExynm6oOe2mNSmscciXSMnwYOBCCdJXF/FAvyfzi8dcbxkbW8EF6ygAFnUAMjEw+wSQ2Wh/8kw7x9KM81CpFgWyjGSX6ZyHw+y6oiQDL4zrlTkIeecVkpJUmZbX0a7DT9wsZCov19qNhfoP4ywnEwIDAQAB";


    // APP_ID 替换为你的应用从官方网站申请到的合法appId
//    public static final String APP_ID = "wx9d147fb6eec962ec";
    public static final String APP_ID = "wx8d987bda68c1c298";
    //  API密钥，在商户平台设置
//    public static final String API_KEY = "Mwe3Bg0To2mYYo00o1fe31y4IDy8em13";
    public static final String API_KEY = "vkRUyk8tZT4PGxc8ilebbm1UguVcbzmA";
    //商户号 微信分配的公众账号ID
//    public static final String MCH_ID = "1488915852";
    public static final String MCH_ID = "1503289611";
}
