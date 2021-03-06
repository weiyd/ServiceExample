package com.cmtech.android.serviceexample.ecgappendix;


import com.cmtech.android.serviceexample.bmefile.ByteUtil;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class EcgHrAppendix extends EcgAppendix{
    private static final int HR_LIST_LEN_BYTE_NUM = 4;
    private static final int BYTE_NUM_PER_HR_VALUE = 2;

    private List<Short> hrList = new ArrayList<>();

    private EcgHrAppendix() {
    }

    public static EcgHrAppendix create() {
        return new EcgHrAppendix();
    }

    public void setHrList(List<Short> hrList) {
        this.hrList = hrList;
    }
    public List<Short> getHrList() {
        return hrList;
    }

    @Override
    public void readFromStream(DataInput in) throws IOException {
        if(in == null) throw new IllegalArgumentException("The data input is null.");

        int hrLength = ByteUtil.reverseInt(in.readInt());
        for(int i = 0; i < hrLength; i++) {
            hrList.add(ByteUtil.reverseShort(in.readShort()));
        }
    }

    @Override
    public void writeToStream(DataOutput out) throws IOException{
        if(out == null) throw new IllegalArgumentException();

        out.writeInt(ByteUtil.reverseInt(hrList.size()));
        for(short hr : hrList) {
            out.writeShort(ByteUtil.reverseShort(hr));
        }
    }

    @Override
    public int length() {
        return super.length() + HR_LIST_LEN_BYTE_NUM + hrList.size()* BYTE_NUM_PER_HR_VALUE;
    }

    @Override
    public EcgAppendixType getType() {
        return EcgAppendixType.HEART_RATE;
    }

    @Override
    public String toString() {
        return hrList.toString();
    }
}
