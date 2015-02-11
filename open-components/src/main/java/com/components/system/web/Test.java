package com.it.web;
import java.util.HashMap;

/**
 * 用作汉字转换成数字的类
 * 
 * @author andre1989@sina.com
 * @version 1.1
 */
public class Test {
    private static HashMap<Long, String> theMap = new HashMap<Long, String>() {
 
        private static final long serialVersionUID = 1L;
 
        {
            put(0L, "零");
            put(1L, "一");
            put(1L, "壹");
            put(2L, "二");
            put(2L, "贰");
            put(3L, "三");
            put(3L, "叁");
            put(4L, "四");
            put(4L, "肆");
            put(5L, "五");
            put(5L, "伍");
            put(6L, "六");
            put(6L, "陆");
            put(7L, "七");
            put(7L, "柒");
            put(8L, "八");
            put(8L, "捌");
            put(9L, "九");
            put(9L, "玖");
            put(10L, "十");
//            put("拾", 10L);
//            put("百", 100L);
//            put("佰", 100L);
//            put("千", 1000L);
//            put("仟", 1000L);
//            put("万", 10000L);
//            put("亿", 100000000L);
        }
    };
 
//    public static long translateToNum(String s) {
//        // 中间及最终结果
//        long result = 0;
// 
//        // 当前汉字代表的数值
//        long num = 1;
// 
//        // 前一汉字的数值
//        long flag = 1;
// 
//        // 当数值过亿时一亿以上的数值
//        long k = 0;
// 
//        for (int i = 0; i < s.length(); i++) {
//            String slice = s.substring(i, i + 1);
//            if (theMap.get(slice) == null) {
//                return 0;
//            }
// 
//            if (theMap.get(slice) == 0) {
//                continue;
//            }
// 
//            if (theMap.get(slice) < 10) {
//                num = theMap.get(slice);
// 
//                if (i == s.length() - 1) {
//                    result += num;
//                    return result + k;
//                }
//            } else if (theMap.get(slice) >= 10000) {
//                // 当中间结果不为0并且前一数值是十的倍数时，可直接相乘
//                if (result != 0 && flag % 10 == 0) {
//                    result *= theMap.get(slice);
//                } else {
//                    result = (result + num) * theMap.get(slice);
//                }
//                 
//                if (theMap.get(slice) == 100000000) {
//                    k = result;
//                    result = 0;
//                }
//            } else {
//                result += num * theMap.get(slice);
//            }
// 
//            flag = theMap.get(slice);
//        }
//        return result + k;
//    }
    
    public static String getUnit(int templen){
    	if(templen == 4){
			return "千";
		}else if(templen == 3){
			return "百";
		}else if(templen == 2){
			return "十";
		}else if(templen == 1){
			return "";
		}else{
			return "";
		}
    }
    
    public static void main(String[] args) {
//    	System.out.println(translateToNum("一百六十四"));
    	int i= 1299;
    	String str = String.valueOf(i);
    	int len = str.length();
		ss(str);
    		
    	
    	System.out.println(getwq);
	}
    
    static String getwq= "";
    public static String ss(String str){
    	int len = str.length();
    	String temp = null;
    	if(len>0){
    		for (int j = 0; j< 1; j++) {
    			Long s = Long.parseLong(str.substring(0, 1));
    			getwq = getwq + theMap.get(s);
    			getwq = getwq + getUnit(str.length());
    			str = str.substring(j, len-1);
    			ss(str);
    		}
    	}
    	
    	return str;
    }
 
}