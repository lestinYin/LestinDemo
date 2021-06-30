package com.lestin.yin.utils.image;


import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/** 
 * autour: lestin.yin
 * email:lestin.yin@gmail.com
 * date: 2017/6/23 16:27
 * version: 
 * description: 
*/
public class FileLoadUtil {
        public static MultipartBody.Part toRequestBodyOfImage(String keyStr, File pFile){
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), pFile);
            MultipartBody.Part filedata = MultipartBody.Part.createFormData(keyStr, pFile.getName(), requestBody);
            return filedata;
        }
}
