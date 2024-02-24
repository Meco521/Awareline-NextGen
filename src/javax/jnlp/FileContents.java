package javax.jnlp;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface FileContents {
  String getName() throws IOException;
  
  InputStream getInputStream() throws IOException;
  
  OutputStream getOutputStream(boolean paramBoolean) throws IOException;
  
  long getLength() throws IOException;
  
  boolean canRead() throws IOException;
}


/* Location:              C:\Users\Administrator\Downloads\Awareline - NextGen7 release ‎2023‎-12-25-‏11-09-05.jar!\javax\jnlp\FileContents.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */