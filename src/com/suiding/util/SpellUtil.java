package com.suiding.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import com.suiding.application.AppExceptionHandler;

//import net.sourceforge.pinyin4j.PinyinHelper;
//import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
//import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

public class SpellUtil
{
    /**
     * 汉语拼音转换工具
     * 
     * @param chinese
     * @return
     */
    public static String getSpell(String chinese)
    {
    	return "AAA";
//        String pinyinString = "";
//        char[] charArray = chinese.toCharArray();
//        // 根据需要定制输出格式，我用默认的即可
//        HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
//        try
//        {
//            // 遍历数组，ASC码大于128进行转换
//            for (int i = 0; i < charArray.length; i++)
//            {
//                if (charArray[i] > 128)
//                {
//                    // charAt(0)取出首字母
//                    if (charArray[i] >= 0x4e00 && charArray[i] <= 0x9fa5)
//                    { //判断是否中文
//                        pinyinString += PinyinHelper.toHanyuPinyinStringArray(
//                                charArray[i], defaultFormat)[0];
//                    }
//                    else
//                    { //不是中文的打上未知，所以无法处理韩文日本等等其他文字
//                        pinyinString += "?";
//                    }
//                }
//                else
//                {
//                    pinyinString += charArray[i];
//                }
//            }
//            return pinyinString;
//        }
//        catch (BadHanyuPinyinOutputFormatCombination e)
//        {
//            e.printStackTrace();//handled
//			AppExceptionHandler.handler(e, "SpellUtil，getSpell 抛出异常 被过滤");
//            return "";
//        }
    }

    /**
     * 判读字母
     * @param str
     * @return
     */
    public static boolean isWord(String str)
    {
        Pattern pattern = Pattern.compile("^[A-Za-z]+$");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches())
        {
            return false;
        }
        else
        {
            return true;
        }
    }

    /**
     * 判读字母
     * @param ch
     * @return
     */
    public static boolean isWord(char ch)
    {
        if (ch >= 'a' && ch <= 'z')
            return true;
        else if (ch >= 'A' && ch <= 'Z')
            return true;
        else
            return false;
    }

}
