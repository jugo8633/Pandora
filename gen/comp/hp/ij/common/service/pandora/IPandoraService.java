/*
 * This file is auto-generated.  DO NOT MODIFY.
 * Original file: C:\\Users\\Jugo\\我的開發\\Pandora\\src\\comp\\hp\\ij\\common\\service\\pandora\\IPandoraService.aidl
 */
package comp.hp.ij.common.service.pandora;
public interface IPandoraService extends android.os.IInterface
{
/** Local-side IPC implementation stub class. */
public static abstract class Stub extends android.os.Binder implements comp.hp.ij.common.service.pandora.IPandoraService
{
private static final java.lang.String DESCRIPTOR = "comp.hp.ij.common.service.pandora.IPandoraService";
/** Construct the stub at attach it to the interface. */
public Stub()
{
this.attachInterface(this, DESCRIPTOR);
}
/**
 * Cast an IBinder object into an comp.hp.ij.common.service.pandora.IPandoraService interface,
 * generating a proxy if needed.
 */
public static comp.hp.ij.common.service.pandora.IPandoraService asInterface(android.os.IBinder obj)
{
if ((obj==null)) {
return null;
}
android.os.IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
if (((iin!=null)&&(iin instanceof comp.hp.ij.common.service.pandora.IPandoraService))) {
return ((comp.hp.ij.common.service.pandora.IPandoraService)iin);
}
return new comp.hp.ij.common.service.pandora.IPandoraService.Stub.Proxy(obj);
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
case TRANSACTION_registerClient:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.registerClient(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_unregisterClient:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
this.unregisterClient(_arg0);
reply.writeNoException();
return true;
}
case TRANSACTION_resetIdleAlarmTimer:
{
data.enforceInterface(DESCRIPTOR);
this.resetIdleAlarmTimer();
reply.writeNoException();
return true;
}
case TRANSACTION_isAssociated:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.isAssociated(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_doLogout:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.doLogout(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_generateActivitionCode:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.generateActivitionCode(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_doDeviceLogin:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.doDeviceLogin(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_setLoginAuth:
{
data.enforceInterface(DESCRIPTOR);
java.lang.String _arg0;
_arg0 = data.readString();
java.lang.String _arg1;
_arg1 = data.readString();
this.setLoginAuth(_arg0, _arg1);
reply.writeNoException();
return true;
}
case TRANSACTION_doLogin:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.doLogin(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getNowPlayStationData:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.getNowPlayStationData(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getStationList:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.getStationList(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_deleteStation:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.deleteStation(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_getPlaylist:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.getPlaylist(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_updatePlaylist:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.updatePlaylist(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_downloadImage:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.downloadImage(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_pauseMusic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.pauseMusic(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_resumeMusic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.resumeMusic(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_skipSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.skipSong(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_stopMusic:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
int _result = this.stopMusic(_arg0, _arg1);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addArtistBookmark:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.addArtistBookmark(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addSongBookmark:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.addSongBookmark(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addPostiveFeedback:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.addPostiveFeedback(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_addNegativeFeedback:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.addNegativeFeedback(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_sleepSong:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.sleepSong(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_explainTrack:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.explainTrack(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_musicSearch:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.musicSearch(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_musicSearchAutoComplete:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.musicSearchAutoComplete(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
case TRANSACTION_createStation:
{
data.enforceInterface(DESCRIPTOR);
int _arg0;
_arg0 = data.readInt();
java.lang.String _arg1;
_arg1 = data.readString();
java.lang.String _arg2;
_arg2 = data.readString();
int _result = this.createStation(_arg0, _arg1, _arg2);
reply.writeNoException();
reply.writeInt(_result);
return true;
}
}
return super.onTransact(code, data, reply, flags);
}
private static class Proxy implements comp.hp.ij.common.service.pandora.IPandoraService
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
@Override public void registerClient(int iActivityHashCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
mRemote.transact(Stub.TRANSACTION_registerClient, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void unregisterClient(int iActivityHashCode) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
mRemote.transact(Stub.TRANSACTION_unregisterClient, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public void resetIdleAlarmTimer() throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
mRemote.transact(Stub.TRANSACTION_resetIdleAlarmTimer, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int isAssociated(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_isAssociated, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int doLogout(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_doLogout, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int generateActivitionCode(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_generateActivitionCode, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int doDeviceLogin(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_doDeviceLogin, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public void setLoginAuth(java.lang.String sUsername, java.lang.String sPassword) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeString(sUsername);
_data.writeString(sPassword);
mRemote.transact(Stub.TRANSACTION_setLoginAuth, _data, _reply, 0);
_reply.readException();
}
finally {
_reply.recycle();
_data.recycle();
}
}
@Override public int doLogin(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_doLogin, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getNowPlayStationData(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_getNowPlayStationData, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getStationList(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_getStationList, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int deleteStation(int iActivityHashCode, java.lang.String sUUID, java.lang.String sStationToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sStationToken);
mRemote.transact(Stub.TRANSACTION_deleteStation, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int getPlaylist(int iActivityHashCode, java.lang.String sUUID, java.lang.String sStationToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sStationToken);
mRemote.transact(Stub.TRANSACTION_getPlaylist, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int updatePlaylist(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_updatePlaylist, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int downloadImage(int iActivityHashCode, java.lang.String sUUID, java.lang.String sAlbumArtUrl) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sAlbumArtUrl);
mRemote.transact(Stub.TRANSACTION_downloadImage, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int pauseMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_pauseMusic, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int resumeMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_resumeMusic, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int skipSong(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_skipSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int stopMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
mRemote.transact(Stub.TRANSACTION_stopMusic, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addArtistBookmark(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_addArtistBookmark, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addSongBookmark(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_addSongBookmark, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addPostiveFeedback(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_addPostiveFeedback, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int addNegativeFeedback(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_addNegativeFeedback, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int sleepSong(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_sleepSong, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int explainTrack(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sTrackToken);
mRemote.transact(Stub.TRANSACTION_explainTrack, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int musicSearch(int iActivityHashCode, java.lang.String sUUID, java.lang.String sSearchText) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sSearchText);
mRemote.transact(Stub.TRANSACTION_musicSearch, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int musicSearchAutoComplete(int iActivityHashCode, java.lang.String sUUID, java.lang.String sSearchText) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sSearchText);
mRemote.transact(Stub.TRANSACTION_musicSearchAutoComplete, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
@Override public int createStation(int iActivityHashCode, java.lang.String sUUID, java.lang.String sMusicToken) throws android.os.RemoteException
{
android.os.Parcel _data = android.os.Parcel.obtain();
android.os.Parcel _reply = android.os.Parcel.obtain();
int _result;
try {
_data.writeInterfaceToken(DESCRIPTOR);
_data.writeInt(iActivityHashCode);
_data.writeString(sUUID);
_data.writeString(sMusicToken);
mRemote.transact(Stub.TRANSACTION_createStation, _data, _reply, 0);
_reply.readException();
_result = _reply.readInt();
}
finally {
_reply.recycle();
_data.recycle();
}
return _result;
}
}
static final int TRANSACTION_registerClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 0);
static final int TRANSACTION_unregisterClient = (android.os.IBinder.FIRST_CALL_TRANSACTION + 1);
static final int TRANSACTION_resetIdleAlarmTimer = (android.os.IBinder.FIRST_CALL_TRANSACTION + 2);
static final int TRANSACTION_isAssociated = (android.os.IBinder.FIRST_CALL_TRANSACTION + 3);
static final int TRANSACTION_doLogout = (android.os.IBinder.FIRST_CALL_TRANSACTION + 4);
static final int TRANSACTION_generateActivitionCode = (android.os.IBinder.FIRST_CALL_TRANSACTION + 5);
static final int TRANSACTION_doDeviceLogin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 6);
static final int TRANSACTION_setLoginAuth = (android.os.IBinder.FIRST_CALL_TRANSACTION + 7);
static final int TRANSACTION_doLogin = (android.os.IBinder.FIRST_CALL_TRANSACTION + 8);
static final int TRANSACTION_getNowPlayStationData = (android.os.IBinder.FIRST_CALL_TRANSACTION + 9);
static final int TRANSACTION_getStationList = (android.os.IBinder.FIRST_CALL_TRANSACTION + 10);
static final int TRANSACTION_deleteStation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 11);
static final int TRANSACTION_getPlaylist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 12);
static final int TRANSACTION_updatePlaylist = (android.os.IBinder.FIRST_CALL_TRANSACTION + 13);
static final int TRANSACTION_downloadImage = (android.os.IBinder.FIRST_CALL_TRANSACTION + 14);
static final int TRANSACTION_pauseMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 15);
static final int TRANSACTION_resumeMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 16);
static final int TRANSACTION_skipSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 17);
static final int TRANSACTION_stopMusic = (android.os.IBinder.FIRST_CALL_TRANSACTION + 18);
static final int TRANSACTION_addArtistBookmark = (android.os.IBinder.FIRST_CALL_TRANSACTION + 19);
static final int TRANSACTION_addSongBookmark = (android.os.IBinder.FIRST_CALL_TRANSACTION + 20);
static final int TRANSACTION_addPostiveFeedback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 21);
static final int TRANSACTION_addNegativeFeedback = (android.os.IBinder.FIRST_CALL_TRANSACTION + 22);
static final int TRANSACTION_sleepSong = (android.os.IBinder.FIRST_CALL_TRANSACTION + 23);
static final int TRANSACTION_explainTrack = (android.os.IBinder.FIRST_CALL_TRANSACTION + 24);
static final int TRANSACTION_musicSearch = (android.os.IBinder.FIRST_CALL_TRANSACTION + 25);
static final int TRANSACTION_musicSearchAutoComplete = (android.os.IBinder.FIRST_CALL_TRANSACTION + 26);
static final int TRANSACTION_createStation = (android.os.IBinder.FIRST_CALL_TRANSACTION + 27);
}
public void registerClient(int iActivityHashCode) throws android.os.RemoteException;
public void unregisterClient(int iActivityHashCode) throws android.os.RemoteException;
public void resetIdleAlarmTimer() throws android.os.RemoteException;
public int isAssociated(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int doLogout(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int generateActivitionCode(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int doDeviceLogin(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public void setLoginAuth(java.lang.String sUsername, java.lang.String sPassword) throws android.os.RemoteException;
public int doLogin(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int getNowPlayStationData(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int getStationList(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int deleteStation(int iActivityHashCode, java.lang.String sUUID, java.lang.String sStationToken) throws android.os.RemoteException;
public int getPlaylist(int iActivityHashCode, java.lang.String sUUID, java.lang.String sStationToken) throws android.os.RemoteException;
public int updatePlaylist(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int downloadImage(int iActivityHashCode, java.lang.String sUUID, java.lang.String sAlbumArtUrl) throws android.os.RemoteException;
public int pauseMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int resumeMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int skipSong(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int stopMusic(int iActivityHashCode, java.lang.String sUUID) throws android.os.RemoteException;
public int addArtistBookmark(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int addSongBookmark(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int addPostiveFeedback(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int addNegativeFeedback(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int sleepSong(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int explainTrack(int iActivityHashCode, java.lang.String sUUID, java.lang.String sTrackToken) throws android.os.RemoteException;
public int musicSearch(int iActivityHashCode, java.lang.String sUUID, java.lang.String sSearchText) throws android.os.RemoteException;
public int musicSearchAutoComplete(int iActivityHashCode, java.lang.String sUUID, java.lang.String sSearchText) throws android.os.RemoteException;
public int createStation(int iActivityHashCode, java.lang.String sUUID, java.lang.String sMusicToken) throws android.os.RemoteException;
}
