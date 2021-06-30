package com.lestin.yin.net;

import android.content.Context;
import android.os.AsyncTask;




public class HttpHandler extends AsyncTask<String, Integer, String[]> {
    private static String TAG = "HttpHandler";

    private String tag;
    private Context ctx;

    public HttpHandler(Context ctx, String tag) {
        this.ctx = ctx;
        this.tag = tag;
    }

    @Override
    protected String[] doInBackground(String... params) {
        String[] result = new String[]{tag, "-1", ""};

        String param = params[0];
//        try {
//            PALog.d(TAG, "start load ad.");
//            String urlName = BusinessConstants.HOST_AD() + "?" + param;
//            URL requestUrl = new URL(urlName);
//            HttpURLConnection conn = (HttpURLConnection) requestUrl.openConnection();
//            conn.setRequestMethod("GET");
//            conn.setRequestProperty("user_agent", SystemUtils.getUserAgent(ctx));
//            conn.setReadTimeout(BusinessConstants.CONNECTION_OUT_TIME * 1000);
//            conn.setConnectTimeout(BusinessConstants.CONNECTION_OUT_TIME * 1000);
//            conn.connect();
//            final int code = conn.getResponseCode();
//            result[1] = String.valueOf(code);
//            PALog.d("HttpHandler", "request payload, http status code: " + code);
//            ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//            InputStream is = conn.getInputStream();
//            int len;
//            byte[] buffer = new byte[1024];
//            while ((len = is.read(buffer, 0, 1024)) != -1) {
//                outStream.write(buffer, 0, len);
//            }
//            result[2] = XXTEA.decryptToString(outStream.toByteArray(), "cHUgQenkAmS5169iZcRABw8n");
//        } catch (Exception e) {
//            PALog.e(TAG, "payload failed");
//            return result;
//        }
        return result;
    }

    @Override
    protected void onPostExecute(String[] result) {
        super.onPostExecute(result);
        String tag = result[0];
        int code = Integer.valueOf(result[1]);
        String data = result[2];
//        if (code == 200 && data != null) {
//            // 有广告，且解析成功
//            AdsHandler.getInstance().getTracker(tag).setPayloadText(data);
//            EventsCenter.getInstance().sendPreloadingEvent(tag, PRELOAD_PAYLOAD_SUCCESS);
//            Log.d(TAG, "payload success");
//            return;
//        }
//
//        if (code == 404) {
//            // 代表无广告
//            EventsCenter.getInstance().setRetryDelayed(ADConfig.getInstance(null).getConfigNoAdRetryTime());
//            EventsCenter.getInstance().sendPreloadingEvent(tag, PRELOAD_NO_AD);
//            Log.d(TAG, "no ZPLAYAds's ad");
//            return;
//        }
//
//        // 这个重试时间最好写在发送sendXxxEvent之前，否则可能会使用旧的时间重试
//        EventsCenter.getInstance().setRetryDelayed(ADConfig.getInstance(null).getConfigDefaultRetryTime());
//        if (code == 400) {
//            // 参数错误，如广告位id输入错误
//            Log.d(TAG, "request parameters error.");
//            EventsCenter.getInstance().sendPreloadingEvent(tag, REQUEST_PARAMS_ERROR);
//        } else {
//            PALog.e(TAG, "request payload has unknown error.");
//            EventsCenter.getInstance().sendPreloadingEvent(tag, UNKNOWN);
//        }
    }
}
