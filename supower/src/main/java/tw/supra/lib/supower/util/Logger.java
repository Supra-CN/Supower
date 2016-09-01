package tw.supra.lib.supower.util;

import android.text.TextUtils;

/**
 * Created by supra on 16-9-1.
 */
public class Logger {
    /**
     * 获取当前调用栈的标记信息
     * 当前调用栈的标记信息定义“fileName#methodName:lineNum”
     *
     * @param extraTags 附加的标签
     * @return 当前调用栈的标记信息
     */
    public static String getStackTag(Object... extraTags) {
        int stackDepth = 3;  // SUPPRESS CHECKSTYLE
        StackTraceElement[] elements = Thread.currentThread().getStackTrace();

        StringBuilder stackTag = new StringBuilder();

        if (elements.length > stackDepth) {
            StackTraceElement element = elements[stackDepth];
            stackTag.append(element.getFileName()).append("#").append(element.getMethodName()).append(":")
                    .append(element.getLineNumber());
        }

        if (null != extraTags) {
            for (Object tag : extraTags) {
                if (!TextUtils.isEmpty(stackTag)) {
                    stackTag.append(" ");
                }
                stackTag.append(tag);
            }
        }
        if (TextUtils.isEmpty(stackTag)) {
            return "getStackTag";
        }
        return stackTag.toString();
    }


}
