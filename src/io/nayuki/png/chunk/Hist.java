/* 
 * PNG library (Java)
 * 
 * Copyright (c) Project Nayuki
 * MIT License. See readme file.
 * https://www.nayuki.io/page/png-library
 */

package io.nayuki.png.chunk;

import java.io.IOException;
import java.util.Objects;


/**
 * An image histogram (hIST) chunk. This gives the approximate
 * usage frequency of each color in the palette. Instances should
 * be treated as immutable, but arrays are not copied defensively.
 * @see https://www.w3.org/TR/2003/REC-PNG-20031110/#11hIST
 */
public record Hist(short[] frequencies) implements SmallDataChunk {
	
	static final String TYPE = "hIST";
	
	
	/*---- Constructor and factory ----*/
	
	public Hist {
		Objects.requireNonNull(frequencies);
		if (!(1 <= frequencies.length && frequencies.length <= 256))
			throw new IllegalArgumentException("Data length out of range");
	}
	
	
	/**
	 * Reads from the specified chunk reader, parses the
	 * fields, and returns a new chunk object of this type.
	 * @param in the chunk reader to read the chunk's data from (not {@code null})
	 * @return a new chunk object of this type (not {@code null})
	 * @throws NullPointerException if the input stream is {@code null}
	 * @throws IllegalArgumentException if the read data is invalid for this chunk type
	 * @throws IOException if an I/O exception occurs
	 */
	public static Hist read(ChunkReader in) throws IOException {
		Objects.requireNonNull(in);
		
		var freqs = new short[in.getRemainingCount() / Short.BYTES];
		for (int i = 0; i < freqs.length; i++)
			freqs[i] = (short)in.readUnsignedShort();
		return new Hist(freqs);
	}
	
	
	/*---- Methods ----*/
	
	@Override public String getType() {
		return TYPE;
	}
	
	
	@Override public void writeData(ChunkWriter out) throws IOException {
		for (short freq : frequencies)
			out.writeUint16(freq & 0xFFFF);
	}
	
}
