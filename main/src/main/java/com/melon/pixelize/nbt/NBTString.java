package com.melon.pixelize.nbt;

import java.util.ArrayList;

/**
 * Use Big Endian byte order and modified UTF-8 encoding (same as DataOutputStream.writeUTF)
 * for strings.
 */
public class NBTString extends NBTElement<String> {

    public NBTString(String keyName, String value) {
        if(keyName == null)
            keyName = "";
        this.keyName = keyName;
        this.keyNameLength = (short) keyName.length();
        this.payLoad = value;
    }

    public NBTString(String value) {
        this(null, value);
    }

    

    @Override
    public byte[] toBytes() {
        byte[] strBytes = payLoad.getBytes();
        int strLength = strBytes.length;
        byte[] result = new byte[3 + keyNameLength + 2 + strLength];
        result[0] = NBTElement.Type.STRING;
        result[1] = (byte) ((0xFF) & keyNameLength >> 8);
        result[2] = (byte) ((0xFF) & keyNameLength);
        System.arraycopy(keyName.getBytes(), 0, result, 3, keyNameLength);
        result[3 + keyNameLength] = (byte) ((0xFF) & strLength >> 8);
        result[4 + keyNameLength] = (byte) ((0xFF) & strLength);
        System.arraycopy(strBytes, 0, result, 5 + keyNameLength, strLength);
        return result;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(payLoad);
        
        ArrayList<Integer> quoteIndex = new ArrayList<>();

        char[] payloadArr= payLoad.toCharArray();
        for(int i = 0;i<payloadArr.length;i++){
            if(payloadArr[i]=='"'&&(i==0||payloadArr[i-1]!='\\'))
                quoteIndex.add(i);
        }

        int QuoteCounter = quoteIndex.size();
        if(QuoteCounter%2!=0)
            throw new IllegalStateException("Quote in the string is unclosed!");
        QuoteCounter/=2;

        for(int i=0;i<QuoteCounter;i++){
            int neededRSlash;
            switch (i) {
                case 0:
                    neededRSlash = 0;
                    break;
                case 1:
                    neededRSlash = 1;
                    break;
                default:
                    neededRSlash = 1+(int)Math.pow(2,i-1);
                    break;
            }
            for(int j=0;j<neededRSlash;j++){
                sb.insert(quoteIndex.get(i), "\\");
                sb.insert(quoteIndex.get(quoteIndex.size()-i), "\\");
            }
        }

        if(QuoteCounter==0)
            return keyName.isEmpty()?"\""+sb+"\"":keyName+":"+"\""+sb+"\"";
        else return keyName.isEmpty()?"\'"+sb+"\'":keyName+":"+"\'"+sb+"\'";
    }

    
}
