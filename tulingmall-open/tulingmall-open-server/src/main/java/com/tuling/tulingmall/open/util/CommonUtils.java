package com.tuling.tulingmall.open.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.http.HttpStatus;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

public class CommonUtils {

    private static final Logger logger = Logger.getLogger(CommonUtils.class);
    private static final AtomicInteger sequenceNo = new AtomicInteger(10000);

    public static void randomTimerSleep(long minMillis, long maxMillis) {
        long value;
        Random objRandom;
        Calendar nowCalendar;

        try {
            // 工作时间进行休眠
            nowCalendar = Calendar.getInstance();
            if (nowCalendar.get(Calendar.HOUR_OF_DAY) > 7 && nowCalendar.get(Calendar.HOUR_OF_DAY) < 20) {
                objRandom = new Random();
                value = objRandom.nextLong();
                value = value % maxMillis;
                if (value < minMillis)
                    value = minMillis;
            } else {
                value = 50;
            }
            TimeUnit.MILLISECONDS.sleep(value);
        } catch (InterruptedException ignore) {
        }
    }

    public static String formatDate() {
        String dateString;
        Date nowDate;
        SimpleDateFormat sdfDate;

        sdfDate = new SimpleDateFormat("yyyyMMdd");
        nowDate = new Date();
        dateString = sdfDate.format(nowDate);

        return dateString;
    }

    /**
     * 输出指定格式的当前时间的字符串表示形式
     */
    public static String formatDateNow(String formatPattern) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(formatPattern);
        return simpleDateFormat.format(new Date());
    }

    public static String formatYYMMDDHHMMSSDate() {
        String dateString;
        Date nowDate;
        SimpleDateFormat sdfDate;

        sdfDate = new SimpleDateFormat("yyMMddHHmmss");
        nowDate = new Date();
        dateString = sdfDate.format(nowDate);

        return dateString;
    }

    public static String generateSequence() {
        String seqNo;
        Date nowDate;
        SimpleDateFormat sdfDate;

        sdfDate = new SimpleDateFormat("yyMMddHHmmss");
        nowDate = new Date();
        seqNo = sdfDate.format(nowDate) + sequenceNo.incrementAndGet();
        seqNo = Long.toHexString(Long.parseLong(seqNo));

        return seqNo;
    }

    public static void inputstreamToFile(InputStream ins, File file) {
        try {
            OutputStream os = new FileOutputStream(file);
            int bytesRead;
            byte[] buffer = new byte[8192];
            while ((bytesRead = ins.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();
            ins.close();
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        }
    }

    public String inputStreamToString(InputStream is) {
        String result = null;
        try {
            ByteArrayOutputStream outputstream = new ByteArrayOutputStream();
            byte[] str_b = new byte[1024];
            int i;
            while ((i = is.read(str_b)) > 0) {
                outputstream.write(str_b, 0, i);
            }
            result = outputstream.toString();

        } catch (IOException e) {
            logger.warn(e.getMessage(), e);
        }
        return result;
    }

    /**
     * 取得当月天数
     */
    public static int getCurrentMonthLastDay() {
        Calendar a = Calendar.getInstance();
        return getMonthLastDay(a);
    }

    /**
     * 取得某个时间的当月的天数
     */
    public static int getMonthLastDay(Calendar a) {
        a.add(Calendar.MONTH, 1);// 时间流逝一个月
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.add(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 得到指定月的天数
     */
    public static int getMonthLastDay(int year, int month) {
        Calendar a = Calendar.getInstance();
        a.set(Calendar.YEAR, year);
        a.set(Calendar.MONTH, month - 1);
        a.set(Calendar.DATE, 1);// 把日期设置为当月第一天
        a.add(Calendar.DATE, -1);// 日期回滚一天，也就是最后一天
        return a.get(Calendar.DATE);
    }

    /**
     * 判断是否是字母和数字
     */
    public static boolean isLetter(String str) {
        if (StringUtils.isBlank(str)) {
            return false;
        }
        // 判断是否是数字类型
        String strpat = "[0-9a-zA-Z]+";
        Pattern pattern = Pattern.compile(strpat);
        return pattern.matcher(str).matches();
    }

    public static String sendHttpBodyRequest(String requestUrl, String requestJson) {
        int statusCode;
        HttpPost httpPost;
        StringEntity reqEntity;
        HttpEntity responseEntity;
        CloseableHttpClient httpclient = null;
        CloseableHttpResponse httpResponse;
        String resultData = "";
        logger.info("---requestUrl---" + requestUrl);
        logger.info("---requestJson---" + requestJson);
        try {
            httpclient = HttpClients.createDefault();
            reqEntity = new StringEntity(requestJson, Consts.UTF_8);
            reqEntity.setContentType("application/json");
            httpPost = new HttpPost(requestUrl);
            httpPost.setEntity(reqEntity);

            long lStartTime, lCostTime;
            lStartTime = System.currentTimeMillis();
            httpResponse = httpclient.execute(httpPost);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            lCostTime = System.currentTimeMillis() - lStartTime;
            logger.info("sendHttpBodyRequest - requestTime[" + lCostTime + "] statusCode = " + statusCode);

            if (statusCode == HttpStatus.OK.value()) {
                responseEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(responseEntity, "utf-8");
                logger.info(resultData);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (httpclient != null)
                    httpclient.close();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
            httpclient = null;
        }

        return resultData;
    }

    public static String sendHttpParameterRequest(String requestUrl, String reqParameter, String requestJson) {
        int statusCode;
        HttpPost httpPost;
        HttpEntity responseEntity;
        CloseableHttpClient httpclient = null;
        List<NameValuePair> formParams;
        UrlEncodedFormEntity formEntity;
        CloseableHttpResponse httpResponse;
        String resultData = "";
        logger.info("---requestUrl---" + requestUrl);
        try {
            httpclient = HttpClients.createDefault();

            formParams = new ArrayList<>();
            formParams.add(new BasicNameValuePair(reqParameter, requestJson));
            formEntity = new UrlEncodedFormEntity(formParams, Consts.UTF_8);

            httpPost = new HttpPost(requestUrl);
            httpPost.setEntity(formEntity);
            logger.info("---attribute---" + reqParameter + " => " + requestJson);
            long lStartTime, lCostTime;
            lStartTime = System.currentTimeMillis();
            httpResponse = httpclient.execute(httpPost);
            statusCode = httpResponse.getStatusLine().getStatusCode();
            lCostTime = System.currentTimeMillis() - lStartTime;
            logger.info("requestTime[" + lCostTime + "] statusCode = " + statusCode);

            if (statusCode == HttpStatus.OK.value()) {
                responseEntity = httpResponse.getEntity();
                resultData = EntityUtils.toString(responseEntity, "utf-8");
                logger.info("responseEntity-resultData:" + resultData);
            }
        } catch (Exception e) {
            logger.warn(e.getMessage(), e);
        } finally {
            try {
                if (httpclient != null)
                    httpclient.close();
            } catch (Exception e) {
                logger.warn(e.getMessage(), e);
            }
            httpclient = null;
        }

        return resultData;
    }
}
