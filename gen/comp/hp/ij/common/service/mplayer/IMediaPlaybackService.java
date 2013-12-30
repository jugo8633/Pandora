/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Jugo\\我的開發\\Pandora\\src\\comp\\hp\\ij\\common\\service\\mplayer\\IMediaPlaybackService.aidl
 */
package comp.hp.ij.common.service.mplayer;
public interface IMediaPlaybackService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements comp.hp.ij.common.service.mplayer.IMediaPlaybackService
{
private static final java.lang.String DESCRIPTOR = "comp.hp.ij.common.service.mplayer.IMediaPlaybackService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an comp.hp.ij.common.service.mplayer.IMediaPlaybackService interface,
 * generating a proxy if needed.
 */
public static comp.hp.ij.common.service.mplayer.IMediaPlaybackService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof comp.hp.ij.common.service.mplayer.IMediaPlaybackService))) {
return ((comp.hp.ij.common.service.mplayer.IMediaPlaybackService)iin);
}
return new comp.hp.ij.common.service.mplayer.IMediaPlaybackService.Stub.Proxy(obj);
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
case TRANSACTION_isPlaying:
{
data.enforceInterface(DESCRIPTOR);
boolean _result = this.isPlaying();
reply.writeNoException();
reply.writeInt(((_result)?(1):(0)));
return true;
}
case TRANSACTION_openUri:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
this.openUri(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_setNetWorkTimeOut:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.setNetWorkTimeOut(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_playMusic:
{
data.enforceInterface(DESCRIPTOR);
this.playMusic();
reply.writeNoException();
return true;
}
case TRANSACTION_stopMusic:
{
data.enforceInterface(DESCRIPTOR);
this.stopMusic();
reply.writeNoException();
return true;
}
case TRANSACTION_pauseMusic:
{
data.enforceInterface(DESCRIPTOR);
this.pauseMusic();
reply.writeNoException();
return true;
}
case TRANSACTION_getPosition:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getPosition();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setVolume:
{
data.enforceInterface(DESCRIPTOR);
float _arg0;
_arg0 = data.readFloat();
this.setVolume(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_getVolume:
{
data.enforceInterface(DESCRIPTOR);
float _result = this.getVolume();
reply.writeNoException();
reply.writeFloat(_result);
return true;
}
case TRANSACTION_getDuration:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getDuration();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getCurrentState:
{
data.enforceInterface(DESCRIPTOR);
int _result = this.getCurrentState();
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_registerCallback:
{
data.enforceInterface(DESCRIPTOR);
comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback _arg0;
_arg0 = comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback.Stub.asInterface(data.readStrongBinder());
this.registerCallback(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterCallback:
{
data.enforceInterface(DESCRIPTOR);
comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback _arg0;
_arg0 = comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback.Stub.asInterface(data.readStrongBinder());
this.unregisterCallback(_arg0);
reply.writeNoException();
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements comp.hp.ij.common.service.mplayer.IMediaPlaybackService
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
	*	true -> media player is playing music ; false-> media player is not playing.
	*/
@Override public boolean isPlaying() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
boolean _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_isPlaying, _data, _reply, 0);
_reply.readException();
_result = (0!=_reply.readInt());
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	*	give one file URI, it would make media player from IDLE to INITIALED.
	*	then media player would try to prepare , if prepare error, it would call onReportError.
	*	If prepare success , it would make mediap layer from INITIALED TO PREPARED.  
	*/
@Override public void openUri(java.lang.String fileUri) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(fileUri);
mRemote.transact(Stub.TRANSACTION_openUri, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void setNetWorkTimeOut(int sec) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(sec);
mRemote.transact(Stub.TRANSACTION_setNetWorkTimeOut, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*	It would trigger media player start play music and media player would
	*	from PREPARED to PLAY
	*/
@Override public void playMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_playMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*	If media player is play music, it would trigger media player from PLAY to IDLE
	*/
@Override public void stopMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_stopMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	* 
	*/
@Override public void pauseMusic() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_pauseMusic, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*
	*/
@Override public int getPosition() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getPosition, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	*
	*/
@Override public void setVolume(float volume) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeFloat(volume);
mRemote.transact(Stub.TRANSACTION_setVolume, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
	*
	*/
@Override public float getVolume() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
float _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getVolume, _data, _reply, 0);
_reply.readException();
_result = _reply.readFloat();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getDuration() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getDuration, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
	*
	*/
@Override public int getCurrentState() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_getCurrentState, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
/**
    * Register a previously registered callback interface.
    */
@Override public void registerCallback(comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
/**
     * Remove a previously registered callback interface.
     */
@Override public void unregisterCallback(comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback cb) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeStrongBinder((((cb!=null))?(cb.asBinder()):(null)));
mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
}
static final int TRANSACTION_isPlaying = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_openUri = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_setNetWorkTimeOut = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_playMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_stopMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_pauseMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_getPosition = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_getVolume = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getDuration = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getCurrentState = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_registerCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_unregisterCallback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
}
/**
	*	true -> media player is playing music ; false-> media player is not playing.
	*/
public boolean isPlaying() throws android.os.RemoteException;
/**
	*	give one file URI, it would make media player from IDLE to INITIALED.
	*	then media player would try to prepare , if prepare error, it would call onReportError.
	*	If prepare success , it would make mediap layer from INITIALED TO PREPARED.  
	*/
public void openUri(java.lang.String fileUri) throws android.os.RemoteException;
public void setNetWorkTimeOut(int sec) throws android.os.RemoteException;
/**
	*	It would trigger media player start play music and media player would
	*	from PREPARED to PLAY
	*/
public void playMusic() throws android.os.RemoteException;
/**
	*	If media player is play music, it would trigger media player from PLAY to IDLE
	*/
public void stopMusic() throws android.os.RemoteException;
/**
	* 
	*/
public void pauseMusic() throws android.os.RemoteException;
/**
	*
	*/
public int getPosition() throws android.os.RemoteException;
/**
	*
	*/
public void setVolume(float volume) throws android.os.RemoteException;
/**
	*
	*/
public float getVolume() throws android.os.RemoteException;
public int getDuration() throws android.os.RemoteException;
/**
	*
	*/
public int getCurrentState() throws android.os.RemoteException;
/**
    * Register a previously registered callback interface.
    */
public void registerCallback(comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback cb) throws android.os.RemoteException;
/**
     * Remove a previously registered callback interface.
     */
public void unregisterCallback(comp.hp.ij.common.service.mplayer.IMediaPlaybackServiceCallback cb) throws android.os.RemoteException;
}
