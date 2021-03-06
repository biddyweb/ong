
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

#ifndef _SPECCEXTERN_H_ 
#define _SPECCEXTERN_H_ 

/*
 * these variables must be declared in the specific part of the driver
 */

extern	int	DiaTraceRequest;
extern	void	*MainTbPoll;
extern	int	PseudoOng;
extern	int	Operating;

extern	int	DiaNbReqInuse;
extern	int	DiaMaxReqInuse;
extern	int	DiaNbReqPending;
extern	int	DiaMaxReqPending;
extern	int	DiaConsecutiveTimeout;
extern	int	DiaUp;

extern	unsigned	int	DiaSendCount;
extern	unsigned	int		DiaCreaCount;
extern	unsigned	int		DiaRetrCount;
extern	unsigned	int		DiaUpdaCount;
extern	unsigned	int		DiaDeleCount;
extern	unsigned	int	DiaRtryCount;
extern	unsigned	int	DiaRecvCount;
extern	unsigned	int	DiaTimeCount;

#endif
