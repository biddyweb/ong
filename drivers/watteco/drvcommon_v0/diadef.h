
/*
* Copyright (C) Actility, SA. All Rights Reserved.
* DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER
*
* This program is free software; you can redistribute it and/or
* modify it under the terms of the GNU General Public License version
* 2 only, as published by the Free Software Foundation.
*
* This program is distributed in the hope that it will be useful, but
* WITHOUT ANY WARRANTY; without even the implied warranty of
* MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
* General Public License version 2 for more details (a copy is
* included at /legal/license.txt).
*
* You should have received a copy of the GNU General Public License
* version 2 along with this work; if not, write to the Free Software
* Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
* 02110-1301 USA
*
* Please contact Actility, SA.,  4, rue Ampere 22300 LANNION FRANCE
* or visit www.actility.com if you need additional
* information or have any questions.
*/

#ifndef _DIADEF_H_ 
#define _DIADEF_H_ 

int	WDiaUCBPreLoadTemplate(char *reqname,t_dia_req *preq,char *target,
		t_cmn_sensor *cmn,void *xo);
int	WDiaUCBPostLoadTemplate(char *reqname,t_dia_req *preq,char *target,
		t_cmn_sensor *cmn,void *xo);
int	WDiaUCBRequestOk(char *reqname,t_dia_req *preq,t_cmn_sensor *cmn);
int	WDiaUCBRequestTimeout(char *reqname,t_dia_req *preq,t_cmn_sensor *cmn);
int	WDiaUCBRequestError(char *reqname,t_dia_req *preq,t_cmn_sensor *cmn);

#define	DIA_FUNC	(char *)__func__

/***********************************************************************/
/***********************************************************************/
#define	DIA_LOCAL_VAR()							\
	int	ret;							\
	int	tid;							\
	void	*xo		= NULL;					\
	char	*buf;							\
	char	*ctype;							\
	int	parseflags	= 0;					\
	int	serialtype	= XO_FMT_STD_XML;			\
	int	sz;							\
	char	target[256];						\
	char	*reqname	= (char *)__func__;			\
	char	*scode;							\
/***********************************************************************/
#define	DIA_DEVICE_CONTEXT()						\
	t_cmn_sensor	*cmn	= NULL;					\
	cmn	= CmnSensorNumSerial(preq->rq_dev,preq->rq_serial);	\
	if	(!cmn)							\
	{								\
	RTL_TRDBG(0,"ERROR CTXT dia request '%s' tid=%d\n",		\
				preq->rq_name,preq->rq_tid);		\
	return;								\
	}								\
	RTL_TRDBG(5,"DIA CTX DEV dev=%d app=%d\n",			\
				preq->rq_dev,preq->rq_app);
/***********************************************************************/
/***********************************************************************/
#define	DIA_CREATE_REQUEST(_cbfct)					\
if	(!DiaCtxt)							\
	return;								\
buf	= WXoSerializeFree(xo,serialtype,parseflags,&sz,&ctype);	\
if	(!buf)								\
	return;								\
ret	= WDiaCreateRequest(reqname,GetReqId(),target,			\
			buf,sz,ctype,					\
			NULL,NULL,&tid);				\
free(buf);								\
if	(ret < 0)							\
{									\
	RTL_TRDBG(0,"ERROR dia create '%s' ret=%d tid=%d\n",		\
						reqname,ret,tid);	\
	return;								\
}									\
DiaSendCount++;								\
RTL_TRDBG(2,"send dia requestC '%s' ret=%d tid=%d\n",reqname,ret,tid);	\
									\
t_dia_req	req;							\
memcpy	(&req,preq,sizeof(t_dia_req));					\
req.rq_tid	= tid;							\
req.rq_name	= reqname;						\
req.rq_cb	= _cbfct;						\
WDiaAddRequest(&req);

/***********************************************************************/
#define	DIA_CREATE_RESPONSE(_fcterr,_fctnok,_fctok)			\
tid	= preq->rq_tid;							\
ret	= par->pr_error;						\
scode	= par->pr_scode;						\
if	(ret >= 1 || par->pr_timeout)					\
{									\
	if	(par->pr_timeout && (preq->rq_retry)++ < 3)		\
	{								\
		DiaRtryCount++;						\
		RTL_TRDBG(0,"RETRY dia create '%s' ret=%d tid=%d %d\n",	\
				reqname,ret,tid,preq->rq_retry);	\
		goto	retry;						\
	}								\
	RTL_TRDBG(2,"ERROR dia create '%s' ret=%d tid=%d code='%s'\n",\
						reqname,ret,tid,scode);	\
	_fcterr(preq,par);						\
	return;								\
}									\
preq->rq_scode	= scode;						\
if	(WDiaCodeOk(scode) == NOK_DIA)					\
{									\
	RTL_TRDBG(2,"NOK dia response '%s' ret=%d tid=%d code='%s'\n",	\
						reqname,ret,tid,scode);	\
	_fctnok(preq,NULL);						\
	return;								\
}									\
RTL_TRDBG(2,"recv dia responseC '%s' ret=%d tid=%d code='%s'\n",	\
					reqname,ret,tid,scode);		\
_fctok(preq,NULL);

/***********************************************************************/
/***********************************************************************/
#define	DIA_RETRIEVE_REQUEST(_cbfct)					\
if	(!DiaCtxt)							\
	return;								\
ret	= WDiaRetrieveRequest(reqname,GetReqId(),target,		\
						NULL,NULL,&tid);	\
if	(ret < 0)							\
{									\
	RTL_TRDBG(0,"ERROR dia retrieve '%s' ret=%d tid=%d\n",		\
						reqname,ret,tid);	\
	return;								\
}									\
DiaSendCount++;								\
RTL_TRDBG(2,"send dia requestR '%s' ret=%d tid=%d\n",reqname,ret,tid);	\
									\
t_dia_req	req;							\
memcpy	(&req,preq,sizeof(t_dia_req));					\
req.rq_tid	= tid;							\
req.rq_name	= reqname;						\
req.rq_cb	= _cbfct;						\
WDiaAddRequest(&req);


/***********************************************************************/
#define	DIA_RETRIEVE_RESPONSE(_fcterr,_fctnok,_fctok)			\
tid	= preq->rq_tid;							\
ret	= par->pr_error;						\
scode	= par->pr_scode;						\
sz	= par->pr_len;							\
buf	= par->pr_ctt;							\
ctype	= par->pr_cttType;						\
xo	= NULL;								\
if	(ret >= 1 || par->pr_timeout)					\
{									\
	if	(par->pr_timeout && (preq->rq_retry)++ < 3)		\
	{								\
		DiaRtryCount++;						\
		RTL_TRDBG(0,"RETRY dia retrieve '%s' ret=%d tid=%d %d\n",\
				reqname,ret,tid,preq->rq_retry);	\
		goto	retry;						\
	}								\
	RTL_TRDBG(2,"ERROR dia retrieve '%s' ret=%d tid=%d code='%s'\n",\
						reqname,ret,tid,scode);	\
	_fcterr(preq,par);						\
	return;								\
}									\
preq->rq_scode	= scode;						\
if	(sz <= 0 || !buf || !*buf || WDiaCodeOk(scode) == NOK_DIA)	\
{									\
RTL_TRDBG(2,"NOK dia response '%s' ret=%d tid=%d code='%s' sz=%d\n",	\
					reqname,ret,tid,scode,sz);	\
	_fctnok(preq,NULL);						\
	return;								\
}									\
RTL_TRDBG(2,"recv dia responseR '%s' ret=%d tid=%d code='%s' sz=%d\n",	\
				reqname,ret,tid,scode,sz);		\
if	(preq->rq_cli && serialtype == XO_FMT_STD_XML)			\
	WAdmDumpRequest(preq->rq_cli,reqname,GetReqId(),target,buf,sz);	\
_fctok(preq,NULL);
/***********************************************************************/
/***********************************************************************/


#endif
