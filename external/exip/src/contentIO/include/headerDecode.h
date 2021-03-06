/*==================================================================*\
|                EXIP - Embeddable EXI Processor in C                |
|--------------------------------------------------------------------|
|          This work is licensed under BSD 3-Clause License          |
|  The full license terms and conditions are located in LICENSE.txt  |
\===================================================================*/

/**
 * @file headerDecode.h
 * @brief Interface for parsing an EXI header
 *
 * @date Aug 23, 2010
 * @author Rumen Kyusakov
 * @version 0.4
 * @par[Revision] $Id: headerDecode.h 242 2013-01-28 15:13:45Z kjussakov $
 */

#ifndef HEADERDECODE_H_
#define HEADERDECODE_H_

#include "errorHandle.h"
#include "procTypes.h"

/**
 * @brief Decode the header of an EXI stream. The current position in the stream is set to
 * the first bit after the header. The EXIStream.header.EXIOptions* are set accordingly
 * @param[in, out] strm EXI stream of bits
 * @param[in] outOfBandOpts TRUE if there are out-of-band options set in parser->strm.header;
 * FALSE otherwise
 * @return Error handling code
 */
errorCode decodeHeader(EXIStream* strm, boolean outOfBandOpts);

#endif /* HEADERDECODE_H_ */
