package buffer;

import io.Page;

public class ZeroPageFormatter implements PageFormatter {
	public void format(Page p) {
		int recsize = Page.INT_SIZE;
		for (int i = 0; i + recsize <= Page.BLOCK_SIZE; i += recsize)
			p.setInt(i, 0);
	}
}
