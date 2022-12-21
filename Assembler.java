package assembler;

//"C:\\Users\\prote\\Desktop\\New_Text_Document.txt"
import java.io.*;

public class Assembler {

    public static void main(String args[]) throws IOException {
        String file = new String("C:\\Users\\prote\\Desktop\\New_Text_Document.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        int counter = 0;
        String rows_for_count;
        while ((rows_for_count = reader.readLine()) != null) {
            counter++;
        }
        System.out.println("number of rows is equal = " + counter);

// PASS ONE


        System.out.println("Pass One: ");
        
        
        String[][] operands = new String[counter][4];

        BufferedReader reader2 = new BufferedReader(new FileReader(file));

        String line = null;

        int location_counter =0;
        int i = 0;
        String word_byte =null;
        String operand = null;
        String instruction = null;
        String symbol = null;
        String dex;
        
        
        while ((line = reader2.readLine()) != null) {
            String [] split_string=line.split("\\s+");
            symbol = split_string[0];
            instruction = split_string[1];
            operand = split_string[2];
            if(line.contains("START")){
                location_counter= Integer.parseInt(split_string[2],16);
            }
            
            dex = Integer.toHexString(location_counter);
            dex= dex.toUpperCase();
            operands[i][0] = dex;
            operands[i][1] = symbol;
            operands[i][2] = instruction;
            operands[i][3] = operand;
            if(line.contains("START")){
                
                
            }
            else if(line.contains("RESW")){
            word_byte = split_string[2];
            
            int x=Integer.parseInt(word_byte,16);
            x=x*3;
            location_counter+=x;
            dex = Integer.toHexString(location_counter);
            }
            else if(line.contains("RESB")){
            word_byte = split_string[2];
            int x=Integer.parseInt(word_byte,16);
            location_counter+=x;
            dex = Integer.toHexString(location_counter);
            }
            else if(line.contains("BYTE")){
            word_byte = split_string[2];
            int x=word_byte.length()-3;
            location_counter+=x;
            dex = Integer.toHexString(location_counter);
            }
            else
            {
                location_counter+=3;
                dex= Integer.toHexString(location_counter);
            }
            
            
            i++;
            
            
        }

        /*test print it out*/
        int j = 0;
        BufferedReader reader4 = new BufferedReader(new FileReader(file));

        System.out.println("Location counter  symbol " + "inst_set " + "operand");

        while (j < counter) {
            line = reader4.readLine();
            System.out.print("\t");
            System.out.print(operands[j][0]);
            System.out.print("\t");
            System.out.println(line);
            j++;
        }

// pass two; scan for the opcodes //   
        System.out.println("Symbol table: ");

        /* create opcode table */
        String[][] opcodes_table = new String[][]{
            {"00000000", "LDA"},
            {"00000001", "STA"},
            {"00000010", "ADD"},
            {"00000011", "SUB"},
            {"00000100", "MUL"},
            {"00000101", "DIV"},
            {"00000110", "LDX"},
            {"00000111", "STX"},
            {"00001000", "COMP"},
            {"00001001", "TIX"},
            {"00001010", "JGT"},
            {"00001011", "JLT"},
            {"00001100", "JEQ"},
            {"00001101", "JSUB"},
            {"00001110", "LDCH"},
            {"00001111", "STCH"}
        };

        /* get the Symbol table */
        BufferedReader reader3 = new BufferedReader(new FileReader(file));

        BufferedWriter obj_writer = new BufferedWriter(new FileWriter("output.txt"));
        
        String opcode = null;
        String codeBits = null;
        int loop_varible = 0;
        int varible=0;
        String [] object_code=new String [counter];
        obj_writer.write("location\t"+"symbol\t\t"+"instruction\t"+"operand\t"+"  the object code");
        obj_writer.newLine();
        
        while (loop_varible<counter) {
            //to skip spaces we used this [a-zA-Z]+ to read only chars
            if(operands[loop_varible][1].matches("[a-zA-Z]+"))
            {
            System.out.print(operands[loop_varible][0]);
            System.out.print("\t");
            System.out.print(operands[loop_varible][1]);
            System.out.print("\n");
            
            }
            loop_varible++;
        }
           
        /* get the object code table 
            System.out.print(operands[k][1]);
            System.out.print("\t");*/
             //loop through opcode table
             while(varible<counter){
             
                 
                if(operands[varible][2].equals("RESW")||operands[varible][2].equals("RESB")||operands[varible][2].equals("START")||operands[varible][2].equals("END")){
                
                object_code[varible]="   ";
                
                }
                else if(operands[varible][2].equals("WORD"))
                {
               int x=Integer.parseInt(operands[varible][3],10);
               object_code[varible]=Integer.toHexString(x);
                
                }
                else if(operands[varible][2].equals("BYTE"))
                { 
                    String str="";
                    String char_ascii =operands[varible][3].substring(2,operands[varible][3].length()-1);
                    for (int b = 0; b < char_ascii.length(); b++) {
                    char character = char_ascii.charAt(b);
                    int ascii = (int) character;
                    String n = Integer.toHexString(ascii);
                    str = str + n;
                    
                }
                    object_code[varible]=str;
                    
                }
                else  {
                    for (int h = 0; h < 16; h++) {
                        if(operands[varible][2].equals(opcodes_table[h][1])){
                        opcode=opcodes_table[h][0];
                        int number=Integer.parseInt(opcodes_table[h][0],2);
                        opcode=Integer.toHexString(number);
                        
                        }}
                    for (int n = 0; n < counter; n++){
                        if(operands[varible][3].contains(","))
                        {
                          int index=operands[varible][3].indexOf(",");
                          String opera = operands[varible][3].substring(0, index);
                          if(opera.equals(operands[n][1]))
                          {
                              
                              int decimal=Integer.parseInt(operands[n][0],16);
                              String Binary=Integer.toBinaryString(decimal);
                              String Bin_after;
                              if(Binary.length()==16){
                              Bin_after=Binary;
                              }
                              else if(Binary.length()==15){
                              Bin_after=1+Binary;
                              }
                              else if(Binary.length()==14){
                              Bin_after=10+Binary;
                              
                              }else{
                               Bin_after=100+Binary;
                              }
                              
                              int deciml=Integer.parseInt(Bin_after,2);
                              String hex=Integer.toHexString(deciml);
                              object_code[varible]=0+opcode+hex;
                            
                          }
                        }
                        else if(operands[varible][3].equals(operands[n][1])){
                            String loc=operands[n][0];
                            
                            
                            object_code[varible]=0+opcode+loc;
                            
                    }
                    
            }}  obj_writer.write(operands[varible][0]);
            obj_writer.write("\t\t");
            obj_writer.write(operands[varible][1]);
            obj_writer.write("\t\t");
            obj_writer.write(operands[varible][2]);
            obj_writer.write("\t\t");
            obj_writer.write(operands[varible][3]);
            obj_writer.write("\t\t");
            
                obj_writer.write(object_code[varible]);
                obj_writer.newLine();
            varible++;
            
            
            
            
             }
             
             
            // System.out.println(operands[counter-1][0]);
           
             String head;
             String coulmn1="H";
             String coulmn2=operands[0][1];
             String column3=operands[0][0];
             int deciml=Integer.parseInt(operands[counter-1][0],16);
             int deciml2=Integer.parseInt(operands[0][0],16);
             int decimal3=deciml-deciml2;
             String hex=Integer.toHexString(decimal3);
             hex=hex.toUpperCase();
             System.out.println("\n"+coulmn1+"^"+coulmn2+"  ^00"+column3+"^000"+hex);
            
            
              String strT="T";
              
              int decimlll=Integer.parseInt(operands[10][0],16);
              int decimlll2=Integer.parseInt(operands[1][0],16);
              int decimalll3=decimlll-decimlll2;
              String hexx=Integer.toHexString(decimalll3);
              hexx=hexx.toUpperCase();
              System.out.print(strT+"  ^00"+operands[1][0]+"^"+hexx);
              for(int g=1;g<11;g++){
                  object_code[g]=object_code[g].toUpperCase();
              System.out.print("^0"+object_code[g]);
              }
              
              System.out.print("\n");
              int decimll=Integer.parseInt(operands[11][0],16);
              int decimll2=Integer.parseInt(operands[20][0],16);
              int decimall3=decimll2-decimll;
              String heexx=Integer.toHexString(decimall3);
              heexx=heexx.toUpperCase();
              System.out.print(strT+"  ^00"+operands[11][0]+"^"+heexx);
               
              for(int g=11;g<21;g++){
                  if(object_code[g].matches("[a-zA-Z0-9]+"))
                          {
                              object_code[g]=object_code[g].toUpperCase();
                              System.out.print("^0"+object_code[g]);
                          }
              
              }
              
            
              
              int start,end;
              String END;
             String coul1="E";
             String coulmn22=operands[0][0];
             System.out.println("\n"+coul1+"^00"+coulmn22);
             
              
              
              
             
             //write binary equivalents to new file
            
        
        obj_writer.close();
        reader.close();

    }

}