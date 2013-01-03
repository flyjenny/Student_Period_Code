package buffer;

import java.util.Comparator;
import java.util.TreeMap;
import java.util.TreeSet;
import io.Block;

public class BufferPool {

	private Buffer pool[];
	private int numAvailable;
	// ReadWriteLock rwl;
	// private final Lock rl;
	// private final Lock wl;
	private final TreeSet<LRUItem> unpinedBuffer;
	private final TreeMap<Block, Buffer> bufferIndex;

	public class LRUItem {
		public int id;
		public long Time;

		public LRUItem(int id, long currentTimeMillis) {
			this.id = id;
			this.Time = currentTimeMillis;
		}
	}

	public BufferPool(int numbuffs) {
		pool = new Buffer[numbuffs];
		// rwl = new ReentrantReadWriteLock();
		// rl = rwl.readLock();
		// wl = rwl.writeLock();
		numAvailable = numbuffs;

		bufferIndex = new TreeMap<Block, Buffer>(new Comparator<Block>() {
			@Override
			public int compare(Block o1, Block o2) {
				if (o1.fileName().equals(o2.fileName()))
					return o1.number() - o2.number();
				else
					return o1.fileName().compareTo(o2.fileName());

			}

		});

		// Imp LRU
		unpinedBuffer = new TreeSet<LRUItem>(new Comparator<LRUItem>() {

			@Override
			public int compare(LRUItem o1, LRUItem o2) {
				return (int) (o2.Time - o1.Time);
			}

		});

		for (int i = 0; i < numbuffs; i++) {
			pool[i] = new Buffer();
			pool[i].buffId = i;

			unpinedBuffer.add(new LRUItem(i, System.currentTimeMillis()));
		}
	}

	public synchronized Buffer pin(Block blk) {
		Buffer buff = findCached(blk);
		if (buff == null) {
			buff = selectUnpinned();
			if (buff == null)
				return null;
			buff.assignToBlock(blk);
			bufferIndex.put(blk, buff);
		}
		if (!buff.isPinned())
			numAvailable--;
		buff.pin();
		return buff;
	}

	private Buffer selectUnpinned() {
		LRUItem i = unpinedBuffer.pollFirst();
		if (null == i)
			return null;
		else {
			Buffer b = pool[i.id];
			Block k = b.block();
			if (null != k)
				bufferIndex.remove(b.block());
			i.Time = System.currentTimeMillis();
			return b;
		}

	}

	private Buffer findCached(Block blk) {
		Buffer buf = bufferIndex.get(blk);
		if (buf == null || !blk.equals(buf.block()))
			return null;
		else
			return buf;

	}

	public synchronized Buffer pinNew(String filename, PageFormatter fmtr) {
		Buffer buff = selectUnpinned();
		if (buff == null)
			return null;
		buff.assignToNew(filename, fmtr);
		numAvailable--;
		buff.pin();
		bufferIndex.put(buff.block(), buff);
		return buff;
	}

	public synchronized void unpin(Buffer buff) {
		if (buff != null) {
			buff.unpin();
			if (!buff.isPinned()) {
				numAvailable++;
				LRUItem i = new LRUItem(buff.buffId, System.currentTimeMillis());
				unpinedBuffer.add(i);
			}
		}
	}

	public void flushAll(int txnum) {
		// wl.lock();
		//
		// try {
		for (Buffer b : pool)
			if (b.isModifiedBy(txnum))
				b.flush();
		// } finally {
		// wl.unlock();
		// }

	}

	public int available() {
		return available();
	}

}
