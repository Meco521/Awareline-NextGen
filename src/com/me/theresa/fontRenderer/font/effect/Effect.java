package com.me.theresa.fontRenderer.font.effect;

import com.me.theresa.fontRenderer.font.Glyph;
import com.me.theresa.fontRenderer.font.UnicodeFont;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

public interface Effect {
  void draw(BufferedImage paramBufferedImage, Graphics2D paramGraphics2D, UnicodeFont paramUnicodeFont, Glyph paramGlyph);
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\com\me\theresa\fontRenderer\font\effect\Effect.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */