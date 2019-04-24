package com.hj.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class ValidateGB2312Utils {
	// 日志信息
    private static final Log log = LogFactory.getLog(ValidateGB2312Utils.class);

    public static boolean vd(String str) {
        if (StringUtils.isEmpty(str)) {
            return false;
        }
        char[] chars = str.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (!checkType(chars[i])) {
                continue;
            }
            int[] ints = null;
            try {
                String word = chars[i] + "";
                ints = toHexString(word.getBytes("GBK"));
                if (chars[i] == ' ') {
                    continue;
                }
                if (ints.length == 2) {
                    if (ints[0] >= 176 && ints[0] <= 247 && ints[1] >= 161 && ints[1] <= 254) {
                        if (ints[0] == 215 && ints[1] > 249) {
                            return false;
                        } else {
                            continue;
                        }
                        // break;
                    } else {
                        return false;
                    }
                }
            } catch (Exception e) {
                log.error("繁体字判断异常", e);
            }
        }
        return true;
    }

    /**
     * 判断输入字符是否为gb2312的编码格式
     * 
     * @param c 输入字符
     * @return 如果是gb2312返回真，否则返回假
     */
    public static String isGB2312(String str) {
        char[] chars = str.toCharArray();
        StringBuffer notIs = new StringBuffer();
        for (int i = 0; i < chars.length; i++) {
            Character ch = new Character(chars[i]);
            String sCh = ch.toString();
            if (!vd(sCh)) {
                notIs.append(chars[i]);
            }
        }
        return notIs.toString();
    }

    public static int[] toHexString(byte[] b) {
        int[] ints = new int[2];

        for (int i = 0; i < b.length; ++i) {
            ints[i] = toHexString(b[i]);
        }
        return ints;
    }

    public static int toHexString(byte b) {
        char[] buffer = new char[2];
        buffer[0] = Character.forDigit((b >>> 4) & 0x0F, 16);
        buffer[1] = Character.forDigit(b & 0x0F, 16);
        int wordInt = Integer.parseInt(String.valueOf(buffer), 16);
        return wordInt;
    }

    private static boolean checkType(char c) {
        String ct = null;
        boolean returnValue = false;

        // 中文，编码区间0x4e00-0x9fbb
        if ((c >= 0x4e00) && (c <= 0x9fbb)) {
            ct = "CharType.CHINESE";
        } else if ((c >= 0xff00) && (c <= 0xffef)) {
            // 2字节英文字
            if (((c >= 0xff21) && (c <= 0xff3a)) || ((c >= 0xff41) && (c <= 0xff5a))) {
                ct = "CharType.LETTER";
            }
            // 2字节数字
            else if ((c >= 0xff10) && (c <= 0xff19)) {
                ct = "CharType.NUM";
            }
            // 其他字符，可以认为是标点符号
            else {
                ct = "CharType.DELIMITER";
            }
        }

        // basic latin，编码区间 0000-007f
        else if ((c >= 0x0021) && (c <= 0x007e)) {
            // 1字节数字
            if ((c >= 0x0030) && (c <= 0x0039)) {
                ct = "CharType.NUM";
            } // 1字节字符
            else if (((c >= 0x0041) && (c <= 0x005a)) || ((c >= 0x0061) && (c <= 0x007a))) {
                ct = "CharType.LETTER";
            }
            // 其他字符，可以认为是标点符号
            else {
                ct = "CharType.DELIMITER";
            }
        }

        // latin-1，编码区间0080-00ff
        else if ((c >= 0x00a1) && (c <= 0x00ff)) {
            if ((c >= 0x00c0) && (c <= 0x00ff)) {
                ct = "CharType.LETTER";
            } else {
                ct = "CharType.DELIMITER";
            }
        } else {
            ct = "CharType.OTHER";
        }
        if ("CharType.CHINESE".equalsIgnoreCase(ct)) {
            returnValue = true;
        }

        return returnValue;
    }

    /**
     * @param pName 姓名数组
     * @return 例：劉鑫陳|劉陳@陳欣然|陳@
     */
    public static String checkPassengerNames(String[] pName) {
        StringBuffer returnJson = new StringBuffer();
        try {
            for (int i = 0; i < pName.length; i++) {
                if (StringUtils.isNotEmpty(pName[i])) {
                    String validName = ValidateGB2312Utils.isGB2312(String.valueOf(pName[i]));
                    if (StringUtils.isNotEmpty(validName)) {
                        returnJson.append(pName[i]);
                        // “|”前面是姓名，“|”后面内容是 姓名中的繁体字
                        returnJson.append("|");
                        returnJson.append(validName);
                        // "@"分隔多个姓名
                        returnJson.append("@");
                    }

                }
            }

        } catch (Exception e) {
            log.error("校验乘客姓名异常", e);
        }

        return returnJson.toString();
    }
}
