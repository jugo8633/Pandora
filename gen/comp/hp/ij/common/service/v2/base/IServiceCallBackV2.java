/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Jugo\\我的開發\\Pandora\\src\\comp\\hp\\ij\\common\\service\\v2\\base\\IServiceCallBackV2.aidl
 */
package comp.hp.ij.common.service.v2.base;
public interface IServiceCallBackV2 extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements comp.hp.ij.common.service.v2.base.IServiceCallBackV2
{
private static final java.lang.String DESCRIPTOR = "comp.hp.ij.common.service.v2.base.IServiceCallBackV2";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an comp.hp.ij.common.service.v2.base.IServiceCallBackV2 interface,
 * generating a proxy if needed.
 */
public static comp.hp.ij.common.service.v2.base.IServiceCallBackV2 asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof comp.hp.ij.common.service.v2.base.IServiceCallBackV2))) {
return ((comp.hp.ij.common.service.v2.base.IServiceCallBackV2)iin);
}
return new comp.hp.ij.common.service.v2.base.IServiceCallBackV2.Stub.Proxy(obj);
}
@Override public android.os.IBinder asBinder()
{
return this;
}
@Override public boolean onTransact(int code, android.os.Parcel data, android.os.Parcel reply, int flags) throws android.os.RemoteException
{
switch (code)
{
case INTERFACE_TRANSACTION:
{
reply.writeString(DESCRIPTOR);
return true;
}
case TRANSACTION_ResultCallBack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
int _arg1;
_arg1 = data.readInt();
int _arg2;
_arg2 = data.readInt();
comp.hp.ij.common.service.v2.base.PResultV2 _arg3;
if ((0!=data.readInt())) {
_arg3 = comp.hp.ij.common.service.v2.base.PResultV2.CREATOR.createFromParcel(data);
}
else {
_arg3 = null;
}
this.ResultCallBack(_arg0, _arg1, _arg2, _arg3);
reply.writeNoException();
if ((_arg3!=null)) {
reply.writeInt(1);
_arg3.writeToParcel(reply, android.os.Parcelable.PARCELABLE_WRITE_RETURN_VALUE);
}
else {
reply.writeInt(0);
}
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements comp.hp.ij.common.service.v2.base.IServiceCallBackV2
{
private android.os.IBinder mRemote;
Proxy(android.os.IBinder remote)
{
mRemote = remote;
}
@Override public android.os.IBinder asBinder()
{
return mRemote;
}
public java.lang.String getInterfaceDescriptor()
{
return DESCRIPTOR;
}
@Override public void ResultCallBack(int what, int arg1, int arg2, comp.hp.ij.common.service.v2.base.PResultV2 pRes) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(what);
_data.writeInt(arg1);
_data.writeInt(arg2);
if ((pRes!=null)) {
_data.writeInt(1);
pRes.writeToParcel(_data, 0);
}
else {
_data.writeInt(0);
}
mRemote.transact(Stub.TRANSACTION_ResultCallBack, _data, _reply, 0);
_reply.readException();
if ((0!=_reply.readInt())) {
pRes.readFromParcel(_reply);
}
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_ResultCallBack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
}
public void ResultCallBack(int what, int arg1, int arg2, comp.hp.ij.common.service.v2.base.PResultV2 pRes) throws android.os.RemoteException;
}
