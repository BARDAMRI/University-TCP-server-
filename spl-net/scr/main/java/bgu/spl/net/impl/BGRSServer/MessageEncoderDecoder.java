package bgu.spl.net.impl.BGRSServer;
import jdk.nashorn.internal.runtime.regexp.joni.constants.OPCode;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.HashMap;

public class MessageEncoderDecoder<T> implements bgu.spl.net.api.MessageEncoderDecoder {


    private Message mess = new Message();
    private byte[] bytes = new byte[1 << 10];
    private int length = 0;
    private boolean start = true;
    private HashMap<Short, Integer> zeros = new HashMap<>();
    ;
    private int leng = -1;
    private int zeroUse = -1;
    private boolean num = false;

    public MessageEncoderDecoder() {
        initZeros();
    }

    private void initZeros() {

        zeros.put((short) 1, 2);
        zeros.put((short) 2, 2);
        zeros.put((short) 3, 2);
        zeros.put((short) 8, 1);
        zeros.put((short) 4, 0);
        zeros.put((short) 5, 0);
        zeros.put((short) 6, 0);
        zeros.put((short) 7, 0);
        zeros.put((short) 9, 0);
        zeros.put((short) 10, 0);
        zeros.put((short) 11, 0);
    }

    @Override
    public Message decodeNextByte(byte nextByte) {

        //At the satrt of each command, when we can read the opcode ew do this and never return to this again in the current command.
        if (start && length == 1) {
            byte[] op = {bytes[0], nextByte};
            mess.Op(bytesToShort(op));
            pushByte(nextByte);
            start = false;
            if (zeros.get(mess.Op()) == 0) {
                if (mess.Op() == 11 | mess.Op() == 4) {
                    leng = 0;
                } else {
                    leng = 2;
                    num = true;
                }
            } else {
                zeroUse = zeros.get(mess.Op());
            }
            bytes = new byte[1 << 10];
            length = 0;
        }
        //at first check if its 0 byte, means the array holds complete value
        else if (nextByte == '\0' && length > 0) {
            //create the value in the message obj in the right spot
            if (mess.VAR1() == null) {
                mess.setVar1(popString());
            } else {
                mess.setVar2(popString());
            }
            //zeroUse is for commands that holds zero's between variables
            zeroUse--;
            //after reading the array we delete what we entered to the obj
            bytes = new byte[1 << 10];
            length = 0;
        } else {

            //we can add to the array since we didn't finished
            pushByte(nextByte);
            leng--;
        }
        //in case of short number that came to its end we add it as var to the message obj.
        if (num & leng == 0) {
            short courseNum = bytesToShort(bytes);
            mess.setVar1(String.valueOf(courseNum));
        }
        //the part of checking the stop reading statement and resetting the fields for the next command
        if (leng == 0 || (mess.Op() > 0 && zeroUse == 0)) {
            start = true;
            bytes = new byte[1 << 10];
            leng = -1;
            length = 0;
            zeroUse = -1;
            num = false;
            Message ret = new Message(mess.Op(), mess.VAR1(), mess.VAR2());
            mess = new Message();
            return ret;
        }
        return null;
    }

    @Override
    public byte[] encode(Object message) {
        if (message instanceof Message) {
            //we return message object that contains a method to encode itself
            return ((Message) message).makeBytes();
        } else {
            return ("\0").getBytes(StandardCharsets.UTF_8);
        }

    }

    public short bytesToShort(byte[] byteArr) {
        short result = (short) ((byteArr[0] & 0xff) << 8);
        result += (short) (byteArr[1] & 0xff);
        return result;
    }

    private void pushByte(byte nextByte) {
        if (length >= bytes.length) {
            bytes = Arrays.copyOf(bytes, length * 2);
        }
        bytes[length++] = nextByte;
    }

    private String popString() {
       //as learned in class
        String result = new String(bytes, 0, length, StandardCharsets.UTF_8);
        length = 0;
        return result;
    }
}
