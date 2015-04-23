package tw.supra.lib.supower.model;

import java.io.IOException;

import tw.supra.lib.supower.model.ModelObj;
import tw.supra.lib.supower.model.Persistable;

/**
 * 受保护、可持久化的数据模型对象
 * 
 * @author supra
 *
 */
public abstract class PersistableObj extends ModelObj implements Persistable {
	private State mStatus = State.INIT;

	protected PersistableObj() {
	}

	abstract protected void onReset();

	abstract protected void onFlush();

	@Override
	public void reset() throws IOException {
		onReset();
		touch(State.CLEAN);
	}

	@Override
	public void flush() throws IOException {
		onFlush();
		touch(State.CLEAN);
	}

	@Override
	public boolean isClean() {
		return State.CLEAN == mStatus;
	}

	@Override
	public void touch(State status) {
		mStatus = status;
	}

	@Override
	public boolean isEmpty() {
		return true;
	}
	
	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public State getStatus() {
		return mStatus;
	}
}
