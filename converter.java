import java.io.File;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class converter {
    
    public static void main(String[] args) {
        try{
            File file = new File("./Asmbly32.asm");
            BufferedReader reader = new BufferedReader(new FileReader(file));
            StringBuffer buffer = new StringBuffer();
            String line;
            while((line = reader.readLine()) != null) {
                buffer.append(line);
                buffer.append("\r\n");
            }
            reader.close();
            
            Map<String, String> ReplacementMap = new HashMap<String, String>();
            ReplacementMap.put("len\\:	equ \\$\\-msg", "fmt\\:    db \"%s\"\\, 10\\, 0");
            ReplacementMap.put("edx\\,len", "push    rbp");
            ReplacementMap.put("main\\:", "main\\:	\n\tpush    rbp");
            ReplacementMap.put("edx\\,len", "rdi\\,fmt");
            ReplacementMap.put("mov	ebx\\,1", "mov	rax\\,0");
            ReplacementMap.put("ecx", "rsi");
            ReplacementMap.put("mov	eax\\,4", "call    printf");
            ReplacementMap.put("int	0x80", "");
            ReplacementMap.put("mov	ebx,0", "pop	rbp");  
            ReplacementMap.put("mov	eax,1", "mov	rax,0"); 
            ReplacementMap.put(",10", ", 0"); 
            
            
            
            String toWrite = buffer.toString();
            for (Map.Entry<String, String> entry : ReplacementMap.entrySet()) {
                toWrite = toWrite.replaceAll(entry.getKey(), entry.getValue());
            }
            
            System.out.println(toWrite);
            toWrite = ("extern	printf\n"+toWrite);
            toWrite = (toWrite+ "\nret");
            
            FileWriter writer = new FileWriter("./converted_sum_assembly_64.asm");
            writer.write(toWrite);
            writer.close();
        }
        catch(IOException e){
            
        }
        
    }
}
