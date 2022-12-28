package io.nayuki.png.chunk;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.Objects;
import io.nayuki.png.Chunk;


public record Srgb(RenderingIntent renderingIntent) implements Chunk {
	
	public static final String TYPE = "sRGB";
	
	
	/*---- Constructor ----*/
	
	public Srgb {
		Objects.requireNonNull(renderingIntent);
	}
	
	
	public static Srgb read(DataInput in) throws IOException {
		RenderingIntent renderingIntent = Util.indexInto(RenderingIntent.values(), in.readUnsignedByte());
		return new Srgb(renderingIntent);
	}
	
	
	/*---- Methods ----*/
	
	@Override public String getType() {
		return TYPE;
	}
	
	
	@Override public int getDataLength() {
		return 1;
	}
	
	
	@Override public void writeData(DataOutput out) throws IOException {
		out.writeByte(renderingIntent.ordinal());
	}
	
	
	
	/*---- Enumeration ----*/
	
	public enum RenderingIntent {
		PERCEPTUAL,
		RELITAVIE_COLORIMETRIC,
		SATURATION,
		ABSOLUTE_COLORIMETRIC,
	}
	
}
