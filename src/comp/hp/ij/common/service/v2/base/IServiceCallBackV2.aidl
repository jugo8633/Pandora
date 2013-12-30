package comp.hp.ij.common.service.v2.base;

import comp.hp.ij.common.service.v2.base.PResultV2;
  
interface IServiceCallBackV2 {
	void ResultCallBack(int what,int arg1,int arg2,inout PResultV2 pRes);
}