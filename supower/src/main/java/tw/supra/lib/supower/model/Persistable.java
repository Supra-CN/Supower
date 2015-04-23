package tw.supra.lib.supower.model;

import java.io.Flushable;
import java.io.IOException;

/**
 * 可持久化的接口
 * @author supra
 *
 */
public interface Persistable extends Flushable {
	enum State {
		INIT,
		CLEAN,
		DIRTY,
		MODIFIED
	}
	/**
	 * 重置为持久化的状态
	 * @throws java.io.IOException
	 */
	public void reset() throws IOException;
	/**
	 * 测试持久化的值是否发生了改变，如果为true则意味着对象可能需要flush()或reset();
	 * @return
	 */
	public boolean isClean();
	/**
	 * 标记当前的Statues状态
	 */
	public void touch(State status);
	
	/**
	 * 测试是否为空;
	 * @return
	 */
	public boolean isEmpty();
	
	/**
	 * 测试是否有效;
	 * @return
	 */
	public boolean isValid();

	/**
	 * 返回当前Status状态
	 * @return
	 */
	public State getStatus();
	
}
