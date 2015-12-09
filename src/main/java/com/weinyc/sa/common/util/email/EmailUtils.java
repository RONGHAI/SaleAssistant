 

package com.weinyc.sa.common.util.email;

import javax.mail.internet.MimeMessage;
import javax.mail.MessagingException;
import java.util.Random;
import java.io.FileOutputStream;
import java.io.File;
import java.io.IOException;
 
final class EmailUtils
{
    
    private static final Random RANDOM = new Random();

    /**
     * Constructs a new <code>EmailException</code> with no detail message.
     */
    private EmailUtils()
    {
        super();
    }

    
    static boolean isEmpty(String str)
    {
        return (str == null) || (str.length() == 0);
    }

   
    static boolean isNotEmpty(String str)
    {
        return (str != null) && (str.length() > 0);
    }

    
    static void notNull(Object object, String message)
    {
        if (object == null)
        {
            throw new IllegalArgumentException(message);
        }
    }

     
    static String randomAlphabetic(int count)
    {
        return random(count, 0, 0, true, false, null, RANDOM);
    }

   
    private static String random(
        int count,
        int start,
        int end,
        boolean letters,
        boolean numbers,
        char [] chars,
        Random random)
    {
        if (count == 0)
        {
            return "";
        }
        else if (count < 0)
        {
            throw new IllegalArgumentException("Requested random string length " + count + " is less than 0.");
        }

        if ((start == 0) && (end == 0))
        {
            end = 'z' + 1;
            start = ' ';

            if (!letters && !numbers)
            {
                start = 0;
                end = Integer.MAX_VALUE;
            }
        }

        StringBuffer buffer = new StringBuffer();
        int gap = end - start;

        while (count-- != 0)
        {
            char ch;

            if (chars == null)
            {
                ch = (char) (random.nextInt(gap) + start);
            }
            else
            {
                ch = chars[random.nextInt(gap) + start];
            }

            if ((letters && numbers && Character.isLetterOrDigit(ch)) || (letters && Character.isLetter(ch))
                            || (numbers && Character.isDigit(ch)) || (!letters && !numbers))
            {
                buffer.append(ch);
            }
            else
            {
                count++;
            }
        }

        return buffer.toString();
    }

 
    static void writeMimeMessage(File resultFile, MimeMessage mimeMessage) throws IOException, MessagingException
    {
        FileOutputStream fos = null;

        if (mimeMessage == null)
        {
            throw new IllegalArgumentException("mimeMessage is null");
        }

        if (resultFile == null)
        {
            throw new IllegalArgumentException("resulFile is null");
        }

        if (resultFile.getParentFile() != null)
        {
            resultFile.getParentFile().mkdirs();
        }

        try
        {
            fos = new FileOutputStream(resultFile);
            mimeMessage.writeTo(fos);
            fos.flush();
            fos.close();
            fos = null;
        }
        finally
        {
            if (fos != null)
            {
                try
                {
                    fos.close();
                    fos = null;
                }
                catch (Exception e)
                {
                    // ignore
                }
            }
        }
    }
}
