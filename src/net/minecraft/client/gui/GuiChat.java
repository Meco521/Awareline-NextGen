/*     */ package net.minecraft.client.gui;
/*     */ import awareline.main.Client;
/*     */ import awareline.main.utility.animations.Animation;
/*     */ import awareline.main.utility.animations.Direction;
/*     */ import awareline.main.utility.animations.impl.DecelerateAnimation;
/*     */ import com.google.common.collect.Lists;
/*     */ import java.io.IOException;
/*     */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.ChatComponentText;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import org.apache.commons.lang3.StringUtils;
/*     */ import org.lwjgl.input.Keyboard;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ public class GuiChat extends GuiScreen {
/*  18 */   private final List<String> foundPlayerNames = Lists.newArrayList();
/*     */ 
/*     */   
/*     */   protected GuiTextField inputField;
/*     */   
/*  23 */   private String historyBuffer = "";
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  28 */   private int sentHistoryCursor = -1;
/*     */   
/*     */   private boolean playerNamesFound;
/*     */   
/*     */   private boolean waitingOnAutocomplete;
/*     */   
/*     */   private int autocompleteIndex;
/*  35 */   private String defaultInputFieldText = "";
/*  36 */   public static Animation openingAnimation = (Animation)new DecelerateAnimation(175, 1.0D, Direction.BACKWARDS);
/*     */ 
/*     */   
/*     */   public GuiChat() {}
/*     */   
/*     */   public GuiChat(String defaultText) {
/*  42 */     this.defaultInputFieldText = defaultText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  50 */     openingAnimation = (Animation)new DecelerateAnimation(175, 1.0D);
/*  51 */     Keyboard.enableRepeatEvents(true);
/*  52 */     this.sentHistoryCursor = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/*  53 */     this.inputField = new GuiTextField(0, this.fontRendererObj, 4, this.height - 12, this.width - 4, 12);
/*  54 */     this.inputField.setMaxStringLength(100);
/*  55 */     this.inputField.setEnableBackgroundDrawing(false);
/*  56 */     this.inputField.setFocused(true);
/*  57 */     this.inputField.setText(this.defaultInputFieldText);
/*  58 */     this.inputField.setCanLoseFocus(false);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void onGuiClosed() {
/*  65 */     openingAnimation.setDirection(Direction.BACKWARDS);
/*  66 */     Keyboard.enableRepeatEvents(false);
/*  67 */     this.mc.ingameGUI.getChatGUI().resetScroll();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateScreen() {
/*  74 */     this.inputField.updateCursorCounter();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void mouseReleased(int mouseX, int mouseY, int state) {
/*  79 */     Client.instance.draggable.getScreen().release();
/*     */     
/*  81 */     super.mouseReleased(mouseX, mouseY, state);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void keyTyped(char typedChar, int keyCode) throws IOException {
/*  88 */     this.waitingOnAutocomplete = false;
/*     */     
/*  90 */     if (keyCode == 15) {
/*  91 */       autocompletePlayerNames();
/*     */     } else {
/*  93 */       this.playerNamesFound = false;
/*     */     } 
/*     */     
/*  96 */     if (keyCode == 1) {
/*  97 */       openingAnimation.setDirection(Direction.BACKWARDS);
/*     */     }
/*  99 */     else if (keyCode != 28 && keyCode != 156) {
/* 100 */       if (keyCode == 200) {
/* 101 */         getSentHistory(-1);
/* 102 */       } else if (keyCode == 208) {
/* 103 */         getSentHistory(1);
/* 104 */       } else if (keyCode == 201) {
/* 105 */         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
/* 106 */       } else if (keyCode == 209) {
/* 107 */         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
/*     */       } else {
/* 109 */         this.inputField.textboxKeyTyped(typedChar, keyCode);
/*     */       } 
/*     */     } else {
/* 112 */       String s = this.inputField.getText().trim();
/*     */       
/* 114 */       if (!s.isEmpty()) {
/* 115 */         sendChatMessage(s);
/*     */       }
/* 117 */       openingAnimation.setDirection(Direction.BACKWARDS);
/*     */     } 
/*     */   }
/*     */   protected void keyTyped2(char typedChar, int keyCode) throws IOException {
/* 121 */     this.waitingOnAutocomplete = false;
/*     */     
/* 123 */     if (keyCode == 15) {
/* 124 */       autocompletePlayerNames();
/*     */     } else {
/* 126 */       this.playerNamesFound = false;
/*     */     } 
/*     */     
/* 129 */     if (keyCode == 1) {
/* 130 */       this.mc.displayGuiScreen((GuiScreen)null);
/* 131 */     } else if (keyCode != 28 && keyCode != 156) {
/* 132 */       if (keyCode == 200) {
/* 133 */         getSentHistory(-1);
/* 134 */       } else if (keyCode == 208) {
/* 135 */         getSentHistory(1);
/* 136 */       } else if (keyCode == 201) {
/* 137 */         this.mc.ingameGUI.getChatGUI().scroll(this.mc.ingameGUI.getChatGUI().getLineCount() - 1);
/* 138 */       } else if (keyCode == 209) {
/* 139 */         this.mc.ingameGUI.getChatGUI().scroll(-this.mc.ingameGUI.getChatGUI().getLineCount() + 1);
/*     */       } else {
/* 141 */         this.inputField.textboxKeyTyped(typedChar, keyCode);
/*     */       } 
/*     */     } else {
/* 144 */       String s = this.inputField.getText().trim();
/*     */       
/* 146 */       if (!s.isEmpty()) {
/* 147 */         sendChatMessage(s);
/*     */       }
/*     */       
/* 150 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void handleMouseInput() throws IOException {
/* 158 */     super.handleMouseInput();
/* 159 */     int i = Mouse.getEventDWheel();
/*     */     
/* 161 */     if (i != 0) {
/* 162 */       if (i > 1) {
/* 163 */         i = 1;
/*     */       }
/*     */       
/* 166 */       if (i < -1) {
/* 167 */         i = -1;
/*     */       }
/*     */       
/* 170 */       if (!isShiftKeyDown()) {
/* 171 */         i *= 7;
/*     */       }
/*     */       
/* 174 */       this.mc.ingameGUI.getChatGUI().scroll(i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
/* 182 */     Client.instance.draggable.getScreen().click(mouseX, mouseY);
/* 183 */     if (mouseButton == 0) {
/* 184 */       IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */       
/* 186 */       if (handleComponentClick(ichatcomponent)) {
/*     */         return;
/*     */       }
/*     */     } 
/*     */     
/* 191 */     this.inputField.mouseClicked(mouseX, mouseY, mouseButton);
/* 192 */     super.mouseClicked(mouseX, mouseY, mouseButton);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setText(String newChatText, boolean shouldOverwrite) {
/* 199 */     if (shouldOverwrite) {
/* 200 */       this.inputField.setText(newChatText);
/*     */     } else {
/* 202 */       this.inputField.writeText(newChatText);
/*     */     } 
/*     */   }
/*     */   
/*     */   public void autocompletePlayerNames() {
/* 207 */     if (this.playerNamesFound) {
/* 208 */       this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/*     */       
/* 210 */       if (this.autocompleteIndex >= this.foundPlayerNames.size()) {
/* 211 */         this.autocompleteIndex = 0;
/*     */       }
/*     */     } else {
/* 214 */       int i = this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false);
/* 215 */       this.foundPlayerNames.clear();
/* 216 */       this.autocompleteIndex = 0;
/* 217 */       String s = this.inputField.getText().substring(i).toLowerCase();
/* 218 */       String s1 = this.inputField.getText().substring(0, this.inputField.getCursorPosition());
/* 219 */       sendAutocompleteRequest(s1, s);
/*     */       
/* 221 */       if (this.foundPlayerNames.isEmpty()) {
/*     */         return;
/*     */       }
/*     */       
/* 225 */       this.playerNamesFound = true;
/* 226 */       this.inputField.deleteFromCursor(i - this.inputField.getCursorPosition());
/*     */     } 
/*     */     
/* 229 */     if (this.foundPlayerNames.size() > 1) {
/* 230 */       StringBuilder stringbuilder = new StringBuilder();
/*     */       
/* 232 */       for (String s2 : this.foundPlayerNames) {
/* 233 */         if (stringbuilder.length() > 0) {
/* 234 */           stringbuilder.append(", ");
/*     */         }
/*     */         
/* 237 */         stringbuilder.append(s2);
/*     */       } 
/*     */       
/* 240 */       this.mc.ingameGUI.getChatGUI().printChatMessageWithOptionalDeletion((IChatComponent)new ChatComponentText(stringbuilder.toString()), 1);
/*     */     } 
/*     */     
/* 243 */     this.inputField.writeText(this.foundPlayerNames.get(this.autocompleteIndex++));
/*     */   }
/*     */   
/*     */   private void sendAutocompleteRequest(String p_146405_1_, String p_146405_2_) {
/* 247 */     if (p_146405_1_.length() >= 1) {
/* 248 */       BlockPos blockpos = null;
/*     */       
/* 250 */       if (this.mc.objectMouseOver != null && this.mc.objectMouseOver.typeOfHit == MovingObjectPosition.MovingObjectType.BLOCK) {
/* 251 */         blockpos = this.mc.objectMouseOver.getBlockPos();
/*     */       }
/*     */       
/* 254 */       this.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C14PacketTabComplete(p_146405_1_, blockpos));
/* 255 */       this.waitingOnAutocomplete = true;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void getSentHistory(int msgPos) {
/* 264 */     int i = this.sentHistoryCursor + msgPos;
/* 265 */     int j = this.mc.ingameGUI.getChatGUI().getSentMessages().size();
/* 266 */     i = MathHelper.clamp_int(i, 0, j);
/*     */     
/* 268 */     if (i != this.sentHistoryCursor) {
/* 269 */       if (i == j) {
/* 270 */         this.sentHistoryCursor = j;
/* 271 */         this.inputField.setText(this.historyBuffer);
/*     */       } else {
/* 273 */         if (this.sentHistoryCursor == j) {
/* 274 */           this.historyBuffer = this.inputField.getText();
/*     */         }
/*     */         
/* 277 */         this.inputField.setText(this.mc.ingameGUI.getChatGUI().getSentMessages().get(i));
/* 278 */         this.sentHistoryCursor = i;
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 287 */     if (openingAnimation.finished(Direction.BACKWARDS)) {
/* 288 */       this.mc.displayGuiScreen((GuiScreen)null);
/*     */       return;
/*     */     } 
/* 291 */     Client.instance.draggable.getScreen().draw(mouseX, mouseY);
/* 292 */     int i = 320;
/* 293 */     int j = 40;
/* 294 */     int k = MathHelper.floor_float(this.mc.gameSettings.chatWidth * 280.0F + 40.0F);
/*     */     
/* 296 */     drawRect2(2.0D, (this.height - 14.0F * openingAnimation.getOutput().floatValue()), (k + 6), 12.0D, -2147483648);
/*     */ 
/*     */ 
/*     */     
/* 300 */     this.inputField.yPosition = this.height - 12.0F * openingAnimation.getOutput().floatValue();
/* 301 */     this.inputField.drawTextBox();
/* 302 */     IChatComponent ichatcomponent = this.mc.ingameGUI.getChatGUI().getChatComponent(Mouse.getX(), Mouse.getY());
/*     */     
/* 304 */     if (ichatcomponent != null && ichatcomponent.getChatStyle().getChatHoverEvent() != null) {
/* 305 */       handleComponentHover(ichatcomponent, mouseX, mouseY);
/*     */     }
/*     */     
/* 308 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */   
/*     */   public void onAutocompleteResponse(String[] p_146406_1_) {
/* 312 */     if (this.waitingOnAutocomplete) {
/* 313 */       this.playerNamesFound = false;
/* 314 */       this.foundPlayerNames.clear();
/*     */       
/* 316 */       for (String s : p_146406_1_) {
/* 317 */         if (!s.isEmpty()) {
/* 318 */           this.foundPlayerNames.add(s);
/*     */         }
/*     */       } 
/*     */       
/* 322 */       String s1 = this.inputField.getText().substring(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false));
/* 323 */       String s2 = StringUtils.getCommonPrefix(p_146406_1_);
/*     */       
/* 325 */       if (!s2.isEmpty() && !s1.equalsIgnoreCase(s2)) {
/* 326 */         this.inputField.deleteFromCursor(this.inputField.func_146197_a(-1, this.inputField.getCursorPosition(), false) - this.inputField.getCursorPosition());
/* 327 */         this.inputField.writeText(s2);
/* 328 */       } else if (!this.foundPlayerNames.isEmpty()) {
/* 329 */         this.playerNamesFound = true;
/* 330 */         autocompletePlayerNames();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean doesGuiPauseGame() {
/* 339 */     return false;
/*     */   }
/*     */ }


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\net\minecraft\client\gui\GuiChat.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */