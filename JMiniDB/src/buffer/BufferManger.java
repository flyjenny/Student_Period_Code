package buffer;

import io.Block;

public class BufferManger {
	private static final long MAX_WAIT = 10000; // 10 seconds
	private BufferPool pool;

	public BufferManger(int numbuffs) {
		pool = new BufferPool(numbuffs);
	}

	interface pinAction {
		public Buffer doPin();
	}

	private Buffer doPin(pinAction act) {
		try {
			long timestamp = System.currentTimeMillis();
			Buffer buff = act.doPin();
			while (buff == null && !waitingTooLong(timestamp)) {
				wait(MAX_WAIT);
				buff = act.doPin();
			}
			if (buff == null)
				throw new BufferAbortException();
			return buff;
		} catch (InterruptedException e) {
			throw new BufferAbortException();
		}
	}

	public synchronized Buffer allocate(final Block blk) {
		return doPin(new pinAction() {
			@Override
			public buffer.Buffer doPin() {
				return pool.pin(blk);
			}
		});
	}

	private boolean waitingTooLong(long timestamp) {

		return System.currentTimeMillis() - timestamp > MAX_WAIT;
	}

	public synchronized Buffer allocateNew(final String filename,
			final PageFormatter fmtr) {
		return doPin(new pinAction() {
			@Override
			public buffer.Buffer doPin() {
				return pool.pinNew(filename, fmtr);
			}
		});
	}

	public synchronized void free(Buffer buff) {
		pool.unpin(buff);
		if (!buff.isPinned())
			notifyAll();
	}

	public void flushAll(/* int txnum */) {
		pool.flushAll(1/* txnum */);
	}

	public int available() {
		return pool.available();
	}
}
