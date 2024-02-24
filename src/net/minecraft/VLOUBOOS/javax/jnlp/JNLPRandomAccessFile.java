package net.minecraft.VLOUBOOS.javax.jnlp;

public interface JNLPRandomAccessFile {
  void close();
  
  long length();
  
  long getFilePointer();
  
  int read();
  
  int read(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  int read(byte[] paramArrayOfbyte);
  
  void readFully(byte[] paramArrayOfbyte);
  
  void readFully(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  int skipBytes(int paramInt);
  
  boolean readBoolean();
  
  byte readByte();
  
  int readUnsignedByte();
  
  short readShort();
  
  int readUnsignedShort();
  
  char readChar();
  
  int readInt();
  
  long readLong();
  
  float readFloat();
  
  double readDouble();
  
  String readLine();
  
  String readUTF();
  
  void seek(long paramLong);
  
  void setLength(long paramLong);
  
  void write(int paramInt);
  
  void write(byte[] paramArrayOfbyte);
  
  void write(byte[] paramArrayOfbyte, int paramInt1, int paramInt2);
  
  void writeBoolean(boolean paramBoolean);
  
  void writeByte(int paramInt);
  
  void writeShort(int paramInt);
  
  void writeChar(int paramInt);
  
  void writeInt(int paramInt);
  
  void writeLong(long paramLong);
  
  void writeFloat(float paramFloat);
  
  void writeDouble(double paramDouble);
  
  void writeBytes(String paramString);
  
  void writeChars(String paramString);
  
  void writeUTF(String paramString);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\VLOUBOOS\javax\jnlp\JNLPRandomAccessFile.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */