/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Jugo\\我的開發\\Pandora\\src\\comp\\hp\\ij\\common\\service\\mplayer\\IMediaPlaybackServiceCallback.aidl
 */
package comp.hp.ij.common.service.mplayer;
public interface IMediaPlaybackServiceCallback extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback
{
private static final java.lang.String DESCRIPTOR = "comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback interface,
 * generating a proxy if needed.
 */
public static comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback))) {
return ((comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback)iin);
}
return new comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback.Stub.Proxy(obj);
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
case TRANSACTION_onComplete:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onComplete(_arg0);
return true;
}
case TRANSACTION_onErrorReport:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.onErrorReport(_arg0);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback
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
/**
     * Called when the service has a new value for you.
     */// call back when one song complete

@Override public void onComplete(int complete_status) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(complete_status);
mRemote.transact(Stub.TRANSACTION_onComplete, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
// call back when error occure

@Override public void onErrorReport(int error_code) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(error_code);
mRemote.transact(Stub.TRANSACTION_onErrorReport, _data, null, android.os.IBinder.FLAG_ONEWAY);
}
finally {
_data.recycle();
}
}
}
static final int TRANSACTION_onComplete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_onErrorReport = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
}
/**
     * Called when the service has a new value for you.
     */// call back when one song complete

public void onComplete(int complete_status) throws android.os.RemoteException;
// call back when error occure

public void onErrorReport(int error_code) throws android.os.RemoteException;
}
