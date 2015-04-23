package tw.supra.lib.supower.util;

import java.util.List;

/**
 * List相关的工具类
 * 
 * @author supra
 *
 */
public class ListUtil {

	/**
	 * 不可构造
	 */
	private ListUtil() {
	}

	/**
	 * 测试List是否为空
	 * 
	 * @param dataArray
	 *            目标list
	 * @return 目标List为Null或size小于1则为真
	 */
	public static <T> boolean isEmpty(List<T> dataArray) {
		return null == dataArray || dataArray.size() < 1;
	}

	/**
	 * List安全取值，数组越界时返回null，而不报异常
	 * 
	 * @param dataArray
	 *            目标list
	 * @param index
	 *            目标索引
	 * @return
	 */
	public static <T> T getSafely(List<T> dataArray, int index) {
		return index < dataArray.size() ? dataArray.get(index) : null;
	}
}
