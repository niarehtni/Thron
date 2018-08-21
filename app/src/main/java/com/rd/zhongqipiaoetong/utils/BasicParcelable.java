package com.rd.zhongqipiaoetong.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.rd.zhongqipiaoetong.utils.ref.RefException;
import com.rd.zhongqipiaoetong.utils.ref.RefObject;
import com.rd.zhongqipiaoetong.utils.ref.RefWrapper;

import java.lang.reflect.Array;
import java.util.List;

/**
 * Email: hbh@erongdu.com
 * Created by hebihe on 5/12/16.
 */
public abstract class BasicParcelable implements Parcelable {
    private static final String CREATOR                       = "CREATOR";
    private static final String CONTENTS_FILE_DESCRIPTOR      = "CONTENTS_FILE_DESCRIPTOR";
    private static final String PARCELABLE_WRITE_RETURN_VALUE = "PARCELABLE_WRITE_RETURN_VALUE";

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        try {

            Log.d("writeToParcel", "start");
            final RefObject refObject = RefObject.wrap(this);
            List<RefObject> list      = refObject.getAll();
            for (RefObject refObject1 : list) {
                Log.d("writeToParcel", "for");
                setValueToParcel(dest, refObject1);
            }
            Log.d("writeToParcel", "end");
        } catch (RefException e) {
            e.printStackTrace();
        }
    }

    private static void setValueToParcel(Parcel dest, RefObject refObj) throws RefException {

        String className = refObj.getName();
        Log.d("setValueToParcel", "<<<" + className + ">>" + refObj.getClazz());
        if (className.equals(PARCELABLE_WRITE_RETURN_VALUE) || className.equals(CONTENTS_FILE_DESCRIPTOR) || className.equals(CREATOR))
            return;
        int classType = refObj.getIntClazz();
        Log.d("setValueToParcel", className + ">>>>" + refObj.unwrap().toString() + ">>" + refObj.getClazz() + ">>>" + classType);
        switch (classType) {
            case RefWrapper.CLASS_STRING:
                dest.writeString((String) refObj.unwrap());
                break;
            case RefWrapper.CLASS_INT:
                dest.writeInt((int) refObj.unwrap());
                break;
            case RefWrapper.CLASS_DOUBLE:
                dest.writeDouble((Double) refObj.unwrap());
                break;
            case RefWrapper.CLASS_LONG:
                dest.writeLong((Long) refObj.unwrap());
                break;
            case RefWrapper.CLASS_FLOAT:
                dest.writeFloat((Float) refObj.unwrap());
                break;
            case RefWrapper.CLASS_BYTE:
                dest.writeByte((Byte) refObj.unwrap());
                break;
            case RefWrapper.CLASS_BOOLEN:
                dest.writeByte((Boolean) refObj.unwrap() ? (byte) 1 : (byte) 0);
                break;
            default:
                break;
        }
    }

    //    private static void setValueFromParcel(final Parcel source, final RefObject refObj) throws RefException {
    //        //        Log.d("readString", source.readString() + "<<<" + refObj.getName());
    //        if (refObj.getName().equals("CREATOR")) {
    //            Log.d("CREATOR", source.readString() + ">>" + refObj.getName());
    //        } else if (!refObj.isPrimitive()) {
    //            //            Log.d("isPrimitive", source.readString() + ">>" + refObj.getName());
    //            refObj.set(source.readString());
    //        } else {
    //            Log.d("isNoPrimitive", source.readString() + ">>" + refObj.getName());
    //        }
    //    }

    private static void setValueFromParcel(final Parcel source, final RefObject refObj) throws RefException {
        String className = refObj.getName();
        Log.d("setValueFromParcel", "<<<" + className + ">>" + refObj.getClazz());
        if (className.equals(PARCELABLE_WRITE_RETURN_VALUE) || className.equals(CONTENTS_FILE_DESCRIPTOR) || className.equals(CREATOR))
            return;
        int classType = refObj.getIntClazz();
        Log.d("setValueFromParcel", "<<<" + className + ">>" + refObj.getClazz() + ">>>" + classType);
        switch (classType) {
            case RefWrapper.CLASS_STRING:
                refObj.set(source.readString());
                break;
            case RefWrapper.CLASS_INT:
                refObj.set(source.readInt());
                break;
            case RefWrapper.CLASS_DOUBLE:
                refObj.set(source.readDouble());
                break;
            case RefWrapper.CLASS_LONG:
                refObj.set(source.readLong());
                break;
            case RefWrapper.CLASS_FLOAT:
                refObj.set(source.readFloat());
                break;
            case RefWrapper.CLASS_BYTE:
                refObj.set(source.readByte());
                break;
            case RefWrapper.CLASS_BOOLEN:
                refObj.set(source.readByte() != 0);
                break;
            default:
                break;
        }
    }

    public static final <T> Creator<T> creator(final Class<T> clazz) {
        final Parcelable.Creator<T> pc = new Parcelable.Creator<T>() {
            @Override
            public T createFromParcel(Parcel source) {

                try {
                    final T t = clazz.newInstance();

                    try {
                        final RefObject refObject = RefObject.wrap(t);
                        List<RefObject> list      = refObject.getAll();
                        Log.d("createFromParcel", "start");
                        for (RefObject refObject1 : list) {
                            Log.d("createFromParcel", "for");
                            setValueFromParcel(source, refObject1);
                        }
                        Log.d("createFromParcel", "end");
                    } catch (RefException e) {
                        e.printStackTrace();
                    }
                    return t;
                } catch (InstantiationException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @SuppressWarnings("unchecked")
            @Override
            public T[] newArray(int size) {
                return (T[]) Array.newInstance(clazz, size);
            }
        };
        return pc;
    }
}
