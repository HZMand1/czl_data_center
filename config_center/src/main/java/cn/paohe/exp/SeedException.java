package cn.paohe.exp;

/**
 * 默认的异常处理类
 */
public class SeedException extends RuntimeException {

	private static final long serialVersionUID = 6517393358388543635L;

	public SeedException() {
		super();
	}

	public SeedException(String message) {
		super(message);
	}

	public SeedException(String message, Throwable cause) {
		super(message, cause);
	}

	public SeedException(Throwable cause) {
		super(cause);
	}
}
