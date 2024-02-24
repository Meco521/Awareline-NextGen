/*     */ package net.minecraft.tileentity;
/*     */ import net.minecraft.command.CommandException;
/*     */ import net.minecraft.command.CommandResultStats;
/*     */ import net.minecraft.command.ICommandSender;
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.event.ClickEvent;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.network.Packet;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentProcessor;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.ChatStyle;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class TileEntitySign extends TileEntity {
/*  19 */   public final IChatComponent[] signText = new IChatComponent[] { (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText(""), (IChatComponent)new ChatComponentText("") };
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  25 */   public int lineBeingEdited = -1;
/*     */   private boolean isEditable = true;
/*     */   private EntityPlayer player;
/*  28 */   final CommandResultStats stats = new CommandResultStats();
/*     */ 
/*     */   
/*     */   public void writeToNBT(NBTTagCompound compound) {
/*  32 */     super.writeToNBT(compound);
/*     */     
/*  34 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  36 */       String s = IChatComponent.Serializer.componentToJson(this.signText[i]);
/*  37 */       compound.setString("Text" + (i + 1), s);
/*     */     } 
/*     */     
/*  40 */     this.stats.writeStatsToNBT(compound);
/*     */   }
/*     */ 
/*     */   
/*     */   public void readFromNBT(NBTTagCompound compound) {
/*  45 */     this.isEditable = false;
/*  46 */     super.readFromNBT(compound);
/*  47 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getName()
/*     */         {
/*  51 */           return "Sign";
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/*  55 */           return (IChatComponent)new ChatComponentText(getName());
/*     */         }
/*     */ 
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {}
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  62 */           return true;
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/*  66 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/*  70 */           return new Vec3(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/*  74 */           return TileEntitySign.this.worldObj;
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/*  78 */           return null;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/*  82 */           return false;
/*     */         }
/*     */ 
/*     */ 
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {}
/*     */       };
/*  89 */     for (int i = 0; i < 4; i++) {
/*     */       
/*  91 */       String s = compound.getString("Text" + (i + 1));
/*     */ 
/*     */       
/*     */       try {
/*  95 */         IChatComponent ichatcomponent = IChatComponent.Serializer.jsonToComponent(s);
/*     */ 
/*     */         
/*     */         try {
/*  99 */           this.signText[i] = ChatComponentProcessor.processComponent(icommandsender, ichatcomponent, (Entity)null);
/*     */         }
/* 101 */         catch (CommandException var7) {
/*     */           
/* 103 */           this.signText[i] = ichatcomponent;
/*     */         }
/*     */       
/* 106 */       } catch (JsonParseException var8) {
/*     */         
/* 108 */         this.signText[i] = (IChatComponent)new ChatComponentText(s);
/*     */       } 
/*     */     } 
/*     */     
/* 112 */     this.stats.readStatsFromNBT(compound);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public Packet getDescriptionPacket() {
/* 121 */     IChatComponent[] aichatcomponent = new IChatComponent[4];
/* 122 */     System.arraycopy(this.signText, 0, aichatcomponent, 0, 4);
/* 123 */     return (Packet)new S33PacketUpdateSign(this.worldObj, this.pos, aichatcomponent);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_183000_F() {
/* 128 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getIsEditable() {
/* 133 */     return this.isEditable;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEditable(boolean isEditableIn) {
/* 141 */     this.isEditable = isEditableIn;
/*     */     
/* 143 */     if (!isEditableIn)
/*     */     {
/* 145 */       this.player = null;
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void setPlayer(EntityPlayer playerIn) {
/* 151 */     this.player = playerIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public EntityPlayer getPlayer() {
/* 156 */     return this.player;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean executeCommand(final EntityPlayer playerIn) {
/* 161 */     ICommandSender icommandsender = new ICommandSender()
/*     */       {
/*     */         public String getName()
/*     */         {
/* 165 */           return playerIn.getName();
/*     */         }
/*     */         
/*     */         public IChatComponent getDisplayName() {
/* 169 */           return playerIn.getDisplayName();
/*     */         }
/*     */ 
/*     */         
/*     */         public void addChatMessage(IChatComponent component) {}
/*     */         
/*     */         public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/* 176 */           return (permLevel <= 2);
/*     */         }
/*     */         
/*     */         public BlockPos getPosition() {
/* 180 */           return TileEntitySign.this.pos;
/*     */         }
/*     */         
/*     */         public Vec3 getPositionVector() {
/* 184 */           return new Vec3(TileEntitySign.this.pos.getX() + 0.5D, TileEntitySign.this.pos.getY() + 0.5D, TileEntitySign.this.pos.getZ() + 0.5D);
/*     */         }
/*     */         
/*     */         public World getEntityWorld() {
/* 188 */           return playerIn.getEntityWorld();
/*     */         }
/*     */         
/*     */         public Entity getCommandSenderEntity() {
/* 192 */           return (Entity)playerIn;
/*     */         }
/*     */         
/*     */         public boolean sendCommandFeedback() {
/* 196 */           return false;
/*     */         }
/*     */         
/*     */         public void setCommandStat(CommandResultStats.Type type, int amount) {
/* 200 */           TileEntitySign.this.stats.setCommandStatScore(this, type, amount);
/*     */         }
/*     */       };
/*     */     
/* 204 */     for (int i = 0; i < this.signText.length; i++) {
/*     */       
/* 206 */       ChatStyle chatstyle = (this.signText[i] == null) ? null : this.signText[i].getChatStyle();
/*     */       
/* 208 */       if (chatstyle != null && chatstyle.getChatClickEvent() != null) {
/*     */         
/* 210 */         ClickEvent clickevent = chatstyle.getChatClickEvent();
/*     */         
/* 212 */         if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND)
/*     */         {
/* 214 */           MinecraftServer.getServer().getCommandManager().executeCommand(icommandsender, clickevent.getValue());
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 219 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public CommandResultStats getStats() {
/* 224 */     return this.stats;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\tileentity\TileEntitySign.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */