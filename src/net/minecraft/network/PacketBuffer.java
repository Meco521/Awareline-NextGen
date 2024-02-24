/*      */ package net.minecraft.network;
/*      */ import com.google.common.base.Charsets;
/*      */ import io.netty.buffer.ByteBuf;
/*      */ import io.netty.buffer.ByteBufAllocator;
/*      */ import io.netty.buffer.ByteBufInputStream;
/*      */ import io.netty.buffer.ByteBufProcessor;
/*      */ import io.netty.handler.codec.DecoderException;
/*      */ import io.netty.handler.codec.EncoderException;
/*      */ import io.netty.util.ReferenceCounted;
/*      */ import java.io.DataInput;
/*      */ import java.io.DataOutput;
/*      */ import java.io.IOException;
/*      */ import java.io.InputStream;
/*      */ import java.io.OutputStream;
/*      */ import java.nio.ByteBuffer;
/*      */ import java.nio.ByteOrder;
/*      */ import java.nio.channels.GatheringByteChannel;
/*      */ import java.nio.channels.ScatteringByteChannel;
/*      */ import java.nio.charset.Charset;
/*      */ import java.util.UUID;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.nbt.CompressedStreamTools;
/*      */ import net.minecraft.nbt.NBTSizeTracker;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ 
/*      */ public class PacketBuffer extends ByteBuf {
/*      */   public PacketBuffer(ByteBuf wrapped) {
/*   31 */     this.buf = wrapped;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private final ByteBuf buf;
/*      */ 
/*      */   
/*      */   public static int getVarIntSize(int input) {
/*   40 */     for (int i = 1; i < 5; i++) {
/*      */       
/*   42 */       if ((input & -1 << i * 7) == 0)
/*      */       {
/*   44 */         return i;
/*      */       }
/*      */     } 
/*      */     
/*   48 */     return 5;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeByteArray(byte[] array) {
/*   53 */     writeVarIntToBuffer(array.length);
/*   54 */     writeBytes(array);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] readByteArray() {
/*   59 */     byte[] abyte = new byte[readVarIntFromBuffer()];
/*   60 */     readBytes(abyte);
/*   61 */     return abyte;
/*      */   }
/*      */ 
/*      */   
/*      */   public BlockPos readBlockPos() {
/*   66 */     return BlockPos.fromLong(readLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeBlockPos(BlockPos pos) {
/*   71 */     writeLong(pos.toLong());
/*      */   }
/*      */ 
/*      */   
/*      */   public IChatComponent readChatComponent() throws IOException {
/*   76 */     String str = readStringFromBuffer(32767);
/*   77 */     int exploitIndex = str.indexOf("${");
/*   78 */     if (exploitIndex != -1 && str.lastIndexOf("}") > exploitIndex) {
/*   79 */       str = str.replaceAll("\\$\\{", "\\$\000{");
/*      */     }
/*      */ 
/*      */ 
/*      */     
/*   84 */     return IChatComponent.Serializer.jsonToComponent(str);
/*      */   }
/*      */   
/*      */   public void writeChatComponent(IChatComponent component) {
/*   88 */     writeString(IChatComponent.Serializer.componentToJson(component));
/*      */   }
/*      */ 
/*      */   
/*      */   public <T extends Enum<T>> T readEnumValue(Class<T> enumClass) {
/*   93 */     return (T)((Enum[])enumClass.getEnumConstants())[readVarIntFromBuffer()];
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeEnumValue(Enum<?> value) {
/*   98 */     writeVarIntToBuffer(value.ordinal());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public int readVarIntFromBuffer() {
/*      */     byte b0;
/*  107 */     int i = 0;
/*  108 */     int j = 0;
/*      */ 
/*      */     
/*      */     do {
/*  112 */       b0 = readByte();
/*  113 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*      */       
/*  115 */       if (j > 5)
/*      */       {
/*  117 */         throw new RuntimeException("VarInt too big");
/*      */       }
/*      */     }
/*  120 */     while ((b0 & 0x80) == 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  126 */     return i;
/*      */   }
/*      */   
/*      */   public long readVarLong() {
/*      */     byte b0;
/*  131 */     long i = 0L;
/*  132 */     int j = 0;
/*      */ 
/*      */     
/*      */     do {
/*  136 */       b0 = readByte();
/*  137 */       i |= (b0 & Byte.MAX_VALUE) << j++ * 7;
/*      */       
/*  139 */       if (j > 10)
/*      */       {
/*  141 */         throw new RuntimeException("VarLong too big");
/*      */       }
/*      */     }
/*  144 */     while ((b0 & 0x80) == 128);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  150 */     return i;
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeUuid(UUID uuid) {
/*  155 */     writeLong(uuid.getMostSignificantBits());
/*  156 */     writeLong(uuid.getLeastSignificantBits());
/*      */   }
/*      */ 
/*      */   
/*      */   public UUID readUuid() {
/*  161 */     return new UUID(readLong(), readLong());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeVarIntToBuffer(int input) {
/*  172 */     while ((input & 0xFFFFFF80) != 0) {
/*      */       
/*  174 */       writeByte(input & 0x7F | 0x80);
/*  175 */       input >>>= 7;
/*      */     } 
/*      */     
/*  178 */     writeByte(input);
/*      */   }
/*      */ 
/*      */   
/*      */   public void writeVarLong(long value) {
/*  183 */     while ((value & 0xFFFFFFFFFFFFFF80L) != 0L) {
/*      */       
/*  185 */       writeByte((int)(value & 0x7FL) | 0x80);
/*  186 */       value >>>= 7L;
/*      */     } 
/*      */     
/*  189 */     writeByte((int)value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeNBTTagCompoundToBuffer(NBTTagCompound nbt) {
/*  197 */     if (nbt == null) {
/*      */       
/*  199 */       writeByte(0);
/*      */     } else {
/*      */ 
/*      */       
/*      */       try {
/*      */         
/*  205 */         CompressedStreamTools.write(nbt, (DataOutput)new ByteBufOutputStream(this));
/*      */       }
/*  207 */       catch (IOException ioexception) {
/*      */         
/*  209 */         throw new EncoderException(ioexception);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NBTTagCompound readNBTTagCompoundFromBuffer() throws IOException {
/*  219 */     int i = readerIndex();
/*  220 */     byte b0 = readByte();
/*      */     
/*  222 */     if (b0 == 0)
/*      */     {
/*  224 */       return null;
/*      */     }
/*      */ 
/*      */     
/*  228 */     readerIndex(i);
/*  229 */     return CompressedStreamTools.read((DataInput)new ByteBufInputStream(this), new NBTSizeTracker(2097152L));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void writeItemStackToBuffer(ItemStack stack) {
/*  238 */     if (stack == null) {
/*      */       
/*  240 */       writeShort(-1);
/*      */     }
/*      */     else {
/*      */       
/*  244 */       writeShort(Item.getIdFromItem(stack.getItem()));
/*  245 */       writeByte(stack.stackSize);
/*  246 */       writeShort(stack.getMetadata());
/*  247 */       NBTTagCompound nbttagcompound = null;
/*      */       
/*  249 */       if (stack.getItem().isDamageable() || stack.getItem().getShareTag())
/*      */       {
/*  251 */         nbttagcompound = stack.getTagCompound();
/*      */       }
/*      */       
/*  254 */       writeNBTTagCompoundToBuffer(nbttagcompound);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public ItemStack readItemStackFromBuffer() throws IOException {
/*  263 */     ItemStack itemstack = null;
/*  264 */     int i = readShort();
/*      */     
/*  266 */     if (i >= 0) {
/*      */       
/*  268 */       int j = readByte();
/*  269 */       int k = readShort();
/*  270 */       itemstack = new ItemStack(Item.getItemById(i), j, k);
/*  271 */       itemstack.setTagCompound(readNBTTagCompoundFromBuffer());
/*      */     } 
/*      */     
/*  274 */     return itemstack;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public String readStringFromBuffer(int maxLength) {
/*  283 */     int i = readVarIntFromBuffer();
/*      */     
/*  285 */     if (i > maxLength << 2)
/*      */     {
/*  287 */       throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + i + " > " + (maxLength << 2) + ")");
/*      */     }
/*  289 */     if (i < 0)
/*      */     {
/*  291 */       throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
/*      */     }
/*      */ 
/*      */     
/*  295 */     String s = new String(readBytes(i).array(), Charsets.UTF_8);
/*      */     
/*  297 */     if (s.length() > maxLength)
/*      */     {
/*  299 */       throw new DecoderException("The received string length is longer than maximum allowed (" + i + " > " + maxLength + ")");
/*      */     }
/*      */ 
/*      */     
/*  303 */     return s;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public PacketBuffer writeString(String string) {
/*  310 */     byte[] abyte = string.getBytes(Charsets.UTF_8);
/*      */     
/*  312 */     if (abyte.length > 32767)
/*      */     {
/*  314 */       throw new EncoderException("String too big (was " + string.length() + " bytes encoded, max " + '翿' + ")");
/*      */     }
/*      */ 
/*      */     
/*  318 */     writeVarIntToBuffer(abyte.length);
/*  319 */     writeBytes(abyte);
/*  320 */     return this;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public int capacity() {
/*  326 */     return this.buf.capacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf capacity(int p_capacity_1_) {
/*  331 */     return this.buf.capacity(p_capacity_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxCapacity() {
/*  336 */     return this.buf.maxCapacity();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBufAllocator alloc() {
/*  341 */     return this.buf.alloc();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteOrder order() {
/*  346 */     return this.buf.order();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf order(ByteOrder p_order_1_) {
/*  351 */     return this.buf.order(p_order_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf unwrap() {
/*  356 */     return this.buf.unwrap();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isDirect() {
/*  361 */     return this.buf.isDirect();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readerIndex() {
/*  366 */     return this.buf.readerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readerIndex(int p_readerIndex_1_) {
/*  371 */     return this.buf.readerIndex(p_readerIndex_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writerIndex() {
/*  376 */     return this.buf.writerIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writerIndex(int p_writerIndex_1_) {
/*  381 */     return this.buf.writerIndex(p_writerIndex_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setIndex(int p_setIndex_1_, int p_setIndex_2_) {
/*  386 */     return this.buf.setIndex(p_setIndex_1_, p_setIndex_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readableBytes() {
/*  391 */     return this.buf.readableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int writableBytes() {
/*  396 */     return this.buf.writableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public int maxWritableBytes() {
/*  401 */     return this.buf.maxWritableBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable() {
/*  406 */     return this.buf.isReadable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isReadable(int p_isReadable_1_) {
/*  411 */     return this.buf.isReadable(p_isReadable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable() {
/*  416 */     return this.buf.isWritable();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean isWritable(int p_isWritable_1_) {
/*  421 */     return this.buf.isWritable(p_isWritable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf clear() {
/*  426 */     return this.buf.clear();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markReaderIndex() {
/*  431 */     return this.buf.markReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetReaderIndex() {
/*  436 */     return this.buf.resetReaderIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf markWriterIndex() {
/*  441 */     return this.buf.markWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf resetWriterIndex() {
/*  446 */     return this.buf.resetWriterIndex();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardReadBytes() {
/*  451 */     return this.buf.discardReadBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf discardSomeReadBytes() {
/*  456 */     return this.buf.discardSomeReadBytes();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf ensureWritable(int p_ensureWritable_1_) {
/*  461 */     return this.buf.ensureWritable(p_ensureWritable_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int ensureWritable(int p_ensureWritable_1_, boolean p_ensureWritable_2_) {
/*  466 */     return this.buf.ensureWritable(p_ensureWritable_1_, p_ensureWritable_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean getBoolean(int p_getBoolean_1_) {
/*  471 */     return this.buf.getBoolean(p_getBoolean_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public byte getByte(int p_getByte_1_) {
/*  476 */     return this.buf.getByte(p_getByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getUnsignedByte(int p_getUnsignedByte_1_) {
/*  481 */     return this.buf.getUnsignedByte(p_getUnsignedByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public short getShort(int p_getShort_1_) {
/*  486 */     return this.buf.getShort(p_getShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedShort(int p_getUnsignedShort_1_) {
/*  491 */     return this.buf.getUnsignedShort(p_getUnsignedShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getMedium(int p_getMedium_1_) {
/*  496 */     return this.buf.getMedium(p_getMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getUnsignedMedium(int p_getUnsignedMedium_1_) {
/*  501 */     return this.buf.getUnsignedMedium(p_getUnsignedMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getInt(int p_getInt_1_) {
/*  506 */     return this.buf.getInt(p_getInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getUnsignedInt(int p_getUnsignedInt_1_) {
/*  511 */     return this.buf.getUnsignedInt(p_getUnsignedInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public long getLong(int p_getLong_1_) {
/*  516 */     return this.buf.getLong(p_getLong_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public char getChar(int p_getChar_1_) {
/*  521 */     return this.buf.getChar(p_getChar_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public float getFloat(int p_getFloat_1_) {
/*  526 */     return this.buf.getFloat(p_getFloat_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public double getDouble(int p_getDouble_1_) {
/*  531 */     return this.buf.getDouble(p_getDouble_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_) {
/*  536 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_) {
/*  541 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuf p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/*  546 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_) {
/*  551 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, byte[] p_getBytes_2_, int p_getBytes_3_, int p_getBytes_4_) {
/*  556 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_, p_getBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, ByteBuffer p_getBytes_2_) {
/*  561 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf getBytes(int p_getBytes_1_, OutputStream p_getBytes_2_, int p_getBytes_3_) throws IOException {
/*  566 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int getBytes(int p_getBytes_1_, GatheringByteChannel p_getBytes_2_, int p_getBytes_3_) throws IOException {
/*  571 */     return this.buf.getBytes(p_getBytes_1_, p_getBytes_2_, p_getBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBoolean(int p_setBoolean_1_, boolean p_setBoolean_2_) {
/*  576 */     return this.buf.setBoolean(p_setBoolean_1_, p_setBoolean_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setByte(int p_setByte_1_, int p_setByte_2_) {
/*  581 */     return this.buf.setByte(p_setByte_1_, p_setByte_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setShort(int p_setShort_1_, int p_setShort_2_) {
/*  586 */     return this.buf.setShort(p_setShort_1_, p_setShort_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setMedium(int p_setMedium_1_, int p_setMedium_2_) {
/*  591 */     return this.buf.setMedium(p_setMedium_1_, p_setMedium_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setInt(int p_setInt_1_, int p_setInt_2_) {
/*  596 */     return this.buf.setInt(p_setInt_1_, p_setInt_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setLong(int p_setLong_1_, long p_setLong_2_) {
/*  601 */     return this.buf.setLong(p_setLong_1_, p_setLong_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setChar(int p_setChar_1_, int p_setChar_2_) {
/*  606 */     return this.buf.setChar(p_setChar_1_, p_setChar_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setFloat(int p_setFloat_1_, float p_setFloat_2_) {
/*  611 */     return this.buf.setFloat(p_setFloat_1_, p_setFloat_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setDouble(int p_setDouble_1_, double p_setDouble_2_) {
/*  616 */     return this.buf.setDouble(p_setDouble_1_, p_setDouble_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_) {
/*  621 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_) {
/*  626 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuf p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/*  631 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_) {
/*  636 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, byte[] p_setBytes_2_, int p_setBytes_3_, int p_setBytes_4_) {
/*  641 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_, p_setBytes_4_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setBytes(int p_setBytes_1_, ByteBuffer p_setBytes_2_) {
/*  646 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int p_setBytes_1_, InputStream p_setBytes_2_, int p_setBytes_3_) throws IOException {
/*  651 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int setBytes(int p_setBytes_1_, ScatteringByteChannel p_setBytes_2_, int p_setBytes_3_) throws IOException {
/*  656 */     return this.buf.setBytes(p_setBytes_1_, p_setBytes_2_, p_setBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf setZero(int p_setZero_1_, int p_setZero_2_) {
/*  661 */     return this.buf.setZero(p_setZero_1_, p_setZero_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean readBoolean() {
/*  666 */     return this.buf.readBoolean();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte readByte() {
/*  671 */     return this.buf.readByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readUnsignedByte() {
/*  676 */     return this.buf.readUnsignedByte();
/*      */   }
/*      */ 
/*      */   
/*      */   public short readShort() {
/*  681 */     return this.buf.readShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedShort() {
/*  686 */     return this.buf.readUnsignedShort();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readMedium() {
/*  691 */     return this.buf.readMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readUnsignedMedium() {
/*  696 */     return this.buf.readUnsignedMedium();
/*      */   }
/*      */ 
/*      */   
/*      */   public int readInt() {
/*  701 */     return this.buf.readInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readUnsignedInt() {
/*  706 */     return this.buf.readUnsignedInt();
/*      */   }
/*      */ 
/*      */   
/*      */   public long readLong() {
/*  711 */     return this.buf.readLong();
/*      */   }
/*      */ 
/*      */   
/*      */   public char readChar() {
/*  716 */     return this.buf.readChar();
/*      */   }
/*      */ 
/*      */   
/*      */   public float readFloat() {
/*  721 */     return this.buf.readFloat();
/*      */   }
/*      */ 
/*      */   
/*      */   public double readDouble() {
/*  726 */     return this.buf.readDouble();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(int p_readBytes_1_) {
/*  731 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readSlice(int p_readSlice_1_) {
/*  736 */     return this.buf.readSlice(p_readSlice_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_) {
/*  741 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_) {
/*  746 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuf p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/*  751 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] p_readBytes_1_) {
/*  756 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(byte[] p_readBytes_1_, int p_readBytes_2_, int p_readBytes_3_) {
/*  761 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_, p_readBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(ByteBuffer p_readBytes_1_) {
/*  766 */     return this.buf.readBytes(p_readBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf readBytes(OutputStream p_readBytes_1_, int p_readBytes_2_) throws IOException {
/*  771 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int readBytes(GatheringByteChannel p_readBytes_1_, int p_readBytes_2_) throws IOException {
/*  776 */     return this.buf.readBytes(p_readBytes_1_, p_readBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf skipBytes(int p_skipBytes_1_) {
/*  781 */     return this.buf.skipBytes(p_skipBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBoolean(boolean p_writeBoolean_1_) {
/*  786 */     return this.buf.writeBoolean(p_writeBoolean_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeByte(int p_writeByte_1_) {
/*  791 */     return this.buf.writeByte(p_writeByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeShort(int p_writeShort_1_) {
/*  796 */     return this.buf.writeShort(p_writeShort_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeMedium(int p_writeMedium_1_) {
/*  801 */     return this.buf.writeMedium(p_writeMedium_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeInt(int p_writeInt_1_) {
/*  806 */     return this.buf.writeInt(p_writeInt_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeLong(long p_writeLong_1_) {
/*  811 */     return this.buf.writeLong(p_writeLong_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeChar(int p_writeChar_1_) {
/*  816 */     return this.buf.writeChar(p_writeChar_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeFloat(float p_writeFloat_1_) {
/*  821 */     return this.buf.writeFloat(p_writeFloat_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeDouble(double p_writeDouble_1_) {
/*  826 */     return this.buf.writeDouble(p_writeDouble_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_) {
/*  831 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_) {
/*  836 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuf p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/*  841 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] p_writeBytes_1_) {
/*  846 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(byte[] p_writeBytes_1_, int p_writeBytes_2_, int p_writeBytes_3_) {
/*  851 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_, p_writeBytes_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeBytes(ByteBuffer p_writeBytes_1_) {
/*  856 */     return this.buf.writeBytes(p_writeBytes_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(InputStream p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/*  861 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int writeBytes(ScatteringByteChannel p_writeBytes_1_, int p_writeBytes_2_) throws IOException {
/*  866 */     return this.buf.writeBytes(p_writeBytes_1_, p_writeBytes_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf writeZero(int p_writeZero_1_) {
/*  871 */     return this.buf.writeZero(p_writeZero_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int indexOf(int p_indexOf_1_, int p_indexOf_2_, byte p_indexOf_3_) {
/*  876 */     return this.buf.indexOf(p_indexOf_1_, p_indexOf_2_, p_indexOf_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(byte p_bytesBefore_1_) {
/*  881 */     return this.buf.bytesBefore(p_bytesBefore_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int p_bytesBefore_1_, byte p_bytesBefore_2_) {
/*  886 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int bytesBefore(int p_bytesBefore_1_, int p_bytesBefore_2_, byte p_bytesBefore_3_) {
/*  891 */     return this.buf.bytesBefore(p_bytesBefore_1_, p_bytesBefore_2_, p_bytesBefore_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(ByteBufProcessor p_forEachByte_1_) {
/*  896 */     return this.buf.forEachByte(p_forEachByte_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByte(int p_forEachByte_1_, int p_forEachByte_2_, ByteBufProcessor p_forEachByte_3_) {
/*  901 */     return this.buf.forEachByte(p_forEachByte_1_, p_forEachByte_2_, p_forEachByte_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(ByteBufProcessor p_forEachByteDesc_1_) {
/*  906 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int forEachByteDesc(int p_forEachByteDesc_1_, int p_forEachByteDesc_2_, ByteBufProcessor p_forEachByteDesc_3_) {
/*  911 */     return this.buf.forEachByteDesc(p_forEachByteDesc_1_, p_forEachByteDesc_2_, p_forEachByteDesc_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy() {
/*  916 */     return this.buf.copy();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf copy(int p_copy_1_, int p_copy_2_) {
/*  921 */     return this.buf.copy(p_copy_1_, p_copy_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice() {
/*  926 */     return this.buf.slice();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf slice(int p_slice_1_, int p_slice_2_) {
/*  931 */     return this.buf.slice(p_slice_1_, p_slice_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf duplicate() {
/*  936 */     return this.buf.duplicate();
/*      */   }
/*      */ 
/*      */   
/*      */   public int nioBufferCount() {
/*  941 */     return this.buf.nioBufferCount();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer() {
/*  946 */     return this.buf.nioBuffer();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer nioBuffer(int p_nioBuffer_1_, int p_nioBuffer_2_) {
/*  951 */     return this.buf.nioBuffer(p_nioBuffer_1_, p_nioBuffer_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer internalNioBuffer(int p_internalNioBuffer_1_, int p_internalNioBuffer_2_) {
/*  956 */     return this.buf.internalNioBuffer(p_internalNioBuffer_1_, p_internalNioBuffer_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers() {
/*  961 */     return this.buf.nioBuffers();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuffer[] nioBuffers(int p_nioBuffers_1_, int p_nioBuffers_2_) {
/*  966 */     return this.buf.nioBuffers(p_nioBuffers_1_, p_nioBuffers_2_);
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasArray() {
/*  971 */     return this.buf.hasArray();
/*      */   }
/*      */ 
/*      */   
/*      */   public byte[] array() {
/*  976 */     return this.buf.array();
/*      */   }
/*      */ 
/*      */   
/*      */   public int arrayOffset() {
/*  981 */     return this.buf.arrayOffset();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean hasMemoryAddress() {
/*  986 */     return this.buf.hasMemoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public long memoryAddress() {
/*  991 */     return this.buf.memoryAddress();
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(Charset p_toString_1_) {
/*  996 */     return this.buf.toString(p_toString_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString(int p_toString_1_, int p_toString_2_, Charset p_toString_3_) {
/* 1001 */     return this.buf.toString(p_toString_1_, p_toString_2_, p_toString_3_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int hashCode() {
/* 1006 */     return this.buf.hashCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean equals(Object p_equals_1_) {
/* 1011 */     return this.buf.equals(p_equals_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public int compareTo(ByteBuf p_compareTo_1_) {
/* 1016 */     return this.buf.compareTo(p_compareTo_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public String toString() {
/* 1021 */     return this.buf.toString();
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain(int p_retain_1_) {
/* 1026 */     return this.buf.retain(p_retain_1_);
/*      */   }
/*      */ 
/*      */   
/*      */   public ByteBuf retain() {
/* 1031 */     return this.buf.retain();
/*      */   }
/*      */ 
/*      */   
/*      */   public int refCnt() {
/* 1036 */     return this.buf.refCnt();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release() {
/* 1041 */     return this.buf.release();
/*      */   }
/*      */ 
/*      */   
/*      */   public boolean release(int p_release_1_) {
/* 1046 */     return this.buf.release(p_release_1_);
/*      */   }
/*      */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\network\PacketBuffer.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */