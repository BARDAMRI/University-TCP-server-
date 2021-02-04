package bgu.spl.net.impl.BGRSServer;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

public class Message {

    private short op;
    private String var1;
    private String var2;

    public Message(short op, String var1, String var2) {

        this.op = op;
        this.var1 = var1;
        this.var2 = var2;
    }
    public Message(){
        this.op = 0;
        this.var1 = null;
        this.var2 = null;
    }
    public short Op() {
        return op;
    }
    public void Op(short op) {
        this.op=op;
    }
    public String VAR1() {
        return var1;
    }
    public void setVar1(String var){this.var1=var;}
    public String VAR2() {
        return var2;
    }
    public void setVar2(String var){this.var2=var;}
    public byte[] makeBytes(){

            byte [] op1=shortToBytes(Op());
            byte[] op2=shortToBytes(Short.parseShort(VAR1()));
            if(VAR2()!=null&&VAR2().length()>0) {
                byte[] ret = (VAR2()).getBytes(StandardCharsets.UTF_8);
                int finalLength=op1.length+op2.length+ret.length+1;
                byte[] outp= Arrays.copyOf(op1, finalLength);
                int destPos = op1.length;
                System.arraycopy(op2, 0, outp, destPos, op2.length);
                destPos += op2.length;
                System.arraycopy(ret, 0, outp, destPos, ret.length);
                destPos += ret.length;
                outp[finalLength-1]='\0';
                return outp;
            }
            int finalLength=op1.length+op2.length;
            byte[] outp=Arrays.copyOf(op1, finalLength);
            int destPos = op1.length;
            System.arraycopy(op2, 0, outp, destPos, op2.length);
            destPos += op2.length;
            return outp;
    }
    public byte[] shortToBytes(short num) {
        byte[] bytesArr = new byte[2];
        bytesArr[0] = (byte) ((num >> 8) & 0xFF);
        bytesArr[1] = (byte) (num & 0xFF);
        return bytesArr;
    }
}