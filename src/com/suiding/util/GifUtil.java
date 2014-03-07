package com.suiding.util;

import java.io.InputStream;

import com.suiding.util.GifHelper.GifFrame;

public class GifUtil
{
    /**
     * ����GIFͼƬ
     * 
     * @param is
     * @return
     */
    public static GifFrame[] getGif(InputStream is) {
        GifHelper gifHelper = new GifHelper();
        if (GifHelper.STATUS_OK == gifHelper.read(is)) {
            return gifHelper.getFrames();
        }
        return null;
    }
    /**
     * �ж�ͼƬ�Ƿ�ΪGIF��ʽ
     * @param is
     * @return
     */
    public static boolean isGif(InputStream is) {
        GifHelper gifHelper = new GifHelper();
        return gifHelper.isGif(is);
    }

}
