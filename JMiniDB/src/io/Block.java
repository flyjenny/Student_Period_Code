package io;

public class Block {
	private String filename;
	private int blknum;

	public Block(String filename, int blknum) {
		this.filename = filename;
		this.blknum = blknum;
	}

	public String fileName() {
		return filename;
	}

	public int number() {
		return blknum;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + blknum;
		result = prime * result
				+ ((filename == null) ? 0 : filename.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Block other = (Block) obj;
		if (blknum != other.blknum)
			return false;
		if (filename == null) {
			if (other.filename != null)
				return false;
		} else if (!filename.equals(other.filename))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "[file " + filename + ", block " + blknum + "]";
	}

}
