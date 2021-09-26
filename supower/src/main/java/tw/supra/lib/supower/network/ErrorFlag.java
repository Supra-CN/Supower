package tw.supra.lib.supower.network;

import com.android.volley.VolleyError;

public class ErrorFlag {
    public static final int OK = 0;
    public static final int FLAG_UNKNOW = 1 << 0;
    public static final int FLAG_FRIENDLY = 1 << 1;
    public static final int FLAG_RETURN_BY_API = 1 << 2;
    public static final int FLAG_PARSE = 1 << 3;
    private int mFlag;

    private String mDescription;
    private ErrorFlag mCauseBy;
    private VolleyError mVolleyError;
    private Throwable mThrowableError;
    private String mDyingMsg = "";

    public ErrorFlag() {
        this(FLAG_UNKNOW);
    }

    public ErrorFlag(int status) {
        setStatus(status);
    }

    public void setFlag(int flag) {
        mFlag = flag;
    }
    public void addFlag(int flag) {
        mFlag |= flag;
    }

    public int getFlag() {
        return mFlag;
    }

    public void clearFlag(int flag) {
        mFlag &= ~flag;
    }


    public void toggleFlag(int flag) {
        mFlag ^= flag;
    }


    public boolean checkFlag(int flag) {
        return (mFlag & flag) != 0;
    }


    public void setStatus(int status) {
        mFlag = status;
    }

    int getStatus() {
        return mFlag;
    }

    public void setCauseBy(ErrorFlag causeBy) {
        if (causeBy == this) {
            throw new IllegalArgumentException("throwable == this");
        }
        mCauseBy = causeBy;
    }

    public ErrorFlag getCauseBy() {
        if (mCauseBy == this) {
            return null;
        }
        return mCauseBy;
    }

    public void setVollyError(VolleyError volleyError) {
        mVolleyError = volleyError;
    }

    public VolleyError getVolleyError() {
        return mVolleyError;
    }

    public void setThrowableError(Throwable throwableError) {
        mThrowableError = throwableError;
    }

    public Throwable getThrowableError() {
        return mThrowableError;
    }

    public void setDescription(String desc) {
        mDescription = desc;
    }

    public String getDescription() {
        return mDescription;
    }

    public void addDyingMsg(String msg) {
        mDyingMsg += msg;
        mDyingMsg += "\n";
    }

    public String getDyingMsg() {
        return mDyingMsg;
    }

    public boolean isOK() {
        return mFlag == 0;
    }

    public boolean isReturnByApiError() {
        return checkFlag(FLAG_RETURN_BY_API);
    }

    public boolean isFriendlyInfo() {
        return checkFlag(FLAG_FRIENDLY);
    }

    @Override
    public String toString() {
        return "ErrorFlag = " + getFlag() + " isOk = " + isOK() + " Desc = " + getDescription() + "\nDyingMsg = " + getDyingMsg();
    }
}
