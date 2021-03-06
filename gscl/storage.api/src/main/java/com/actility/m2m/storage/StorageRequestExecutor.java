/*
 * Copyright   Actility, SA. All Rights Reserved.
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
 *
 * id $Id: StorageRequestExecutor.java 8030 2014-03-07 17:30:45Z JReich $
 * author $Author: JReich $
 * version $Revision: 8030 $
 * lastrevision $Date: 2014-03-07 18:30:45 +0100 (Fri, 07 Mar 2014) $
 * modifiedby $LastChangedBy: JReich $
 * lastmodified $LastChangedDate: 2014-03-07 18:30:45 +0100 (Fri, 07 Mar 2014) $
 */

package com.actility.m2m.storage;

import java.util.Collection;
import java.util.Map;

/**
 * It provides all the necessary methods to operate on data
 */
public interface StorageRequestExecutor {
    public static final int SCOPE_EXACT = 1;
    public static final int SCOPE_SUBTREE = 2;
    public static final int ORDER_ASC = 3;
    public static final int ORDER_DESC = 4;
    public static final int ORDER_UNKNOWN = 5;
    public static final int CASCADING_ONE_LEVEL = 6;
    public static final int CASCADING_NONE = 7;
    public static final int CASCADING_SUBTREE = 12;
    public static final int TYPE_CREATE = 8;
    public static final int TYPE_UPDATE = 9;
    public static final int TYPE_DELETE = 10;

    /**
     * Retrieves a complete resource (attributes + references to sub resources)
     *
     * @param path the full (absolute) path of the resource (relative to a root)
     * @return Null if the path is not defined for this context in database. Document as a string.
     * @throws StorageException
     */
    public byte[] retrieve(String path) throws StorageException;

    /**
     * Retrieves a complete resource (attributes + references to sub resources)
     *
     * @param config storage configuration to apply
     * @param path the full (absolute) path of the resource (relative to a root)
     * @return Null if the path is not defined for this context in database. Document as a string
     * @throws StorageException
     */
    public byte[] retrieve(Map config, String path) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, Condition condition) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, Condition condition, int order) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @param limit maximum number of results returned
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, Condition condition, int order, int limit) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, int scope, Condition condition) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, int scope, Condition condition, int order) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @param limit maximum number of results returned
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(String basePath, int scope, Condition condition, int order, int limit) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, Condition condition) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, Condition condition, int order) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param condition condition that filters documents
     * @param order ordering of results (unk, asc, desc)
     * @param limit maximum number of results returned
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, Condition condition, int order, int limit) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, int scope, Condition condition) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @param order ordering of the results (unk, asc, desc)
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, int scope, Condition condition, int order) throws StorageException;

    /**
     * Searches for a document in a specified path
     *
     * @param config storage configuration to apply
     * @param basePath node from which search is performed
     * @param scope either one level either full depth
     * @param condition condition that filters documents
     * @param order ordering of the results (unk, asc, desc)
     * @param limit maximum number of results returned
     * @return a map containing the result of the search key=doc path value=document content
     * @throws StorageException
     */
    public SearchResult search(Map config, String basePath, int scope, Condition condition, int order, int limit)
            throws StorageException;

    /**
     * Updates a existing resource. Raises an an exception if the document does not exist
     *
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument new value to set in the resource
     * @throws StorageException if the document does not exist
     */
    public void update(String path, byte[] rawDocument) throws StorageException;

    /**
     * Updates a existing resource. . Raises an an exception if the document does not exist. Replaces all the searchAttributes
     * unless search Attributes is not specified. In that case, searchAttributes are left untouched
     *
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument new value to set in the resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @throws StorageException if the document does not exist
     */
    public void update(String path, byte[] rawDocument, Collection searchAttributes) throws StorageException;

    /**
     * Updates a existing resource. Raises an an exception if the document does not exist.
     *
     * @param config storage configuration to apply
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument new value to set in the resource
     * @throws StorageException if the document does not exist.
     */
    public void update(Map config, String path, byte[] rawDocument) throws StorageException;

    /**
     * Updates a existing resource. Raises an an exception if the document does not exist. Replaces all the searchAttributes
     * unless search Attributes is not specified. In that case, searchAttributes are left untouched
     *
     * @param config storage configuration to apply
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument new value to set in the resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @throws StorageException (if the doc already exists)
     */
    public void update(Map config, String path, byte[] rawDocument, Collection searchAttributes) throws StorageException;

    /**
     * Creates a new resource (raises an error if it already exists). Raises an exception if the document already exists. The
     * created document has path/doc for path.
     *
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument value of the new resource
     * @throws StorageException (if the doc already exists)
     */
    public void create(String path, byte[] rawDocument) throws StorageException;

    /**
     * Creates a new resource (raises an error if it already exists). Raises an exception if the document already exists The
     * created document has path/doc for path.
     *
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument value of the new resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @throws StorageException (if the doc already exists)
     */
    public void create(String path, byte[] rawDocument, Collection searchAttributes) throws StorageException;

    /**
     * Creates a new resource (raises an error if it already exists). Raises an exception if the document already exists. The
     * created document has path/doc for path.
     *
     * @param config storage configuration to apply
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument value of the new resource
     * @throws StorageException (if the doc already exists)
     */
    public void create(Map config, String path, byte[] rawDocument) throws StorageException;

    /**
     * Creates a new resource (raises an error if it already exists). Raises an exception if the document already exists. The
     * created document has path/doc for path.
     *
     * @param config storage configuration to apply
     * @param path the full path of the resource (relative to a root)
     * @param rawDocument value of the new resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @throws StorageException (if the doc already exists)
     */
    public void create(Map config, String path, byte[] rawDocument, Collection searchAttributes) throws StorageException;

    /**
     * Erases a resource. Raises an exception if the document does not exist.
     *
     * @param path the path of the resource (relative to a root)
     * @throws StorageException (if the doc already exists)
     */
    public void delete(String path) throws StorageException;

    /**
     * Erases a resource. If cascading is specified to oneLevel, a resource and all its subresources are deleted. Raises an
     * exception if the document does not exist.
     *
     * @param path the path of the resource (relative to a root)
     * @param cascade a constant ONE_LEVEL, NONE or SUBTREE
     * @throws StorageException (if the doc already exists)
     */
    public void delete(String path, int cascade) throws StorageException;

    /**
     * Erases a resource. Raises an exception if the document does not exist.
     *
     * @param config storage configuration to apply
     * @param path the path of the resource (relative to a root)
     * @throws StorageException
     */
    public void delete(Map config, String path) throws StorageException;

    /**
     * Erases a resource. If cascading is specified to oneLevel, a resource and all its subresources are deleted. Raises an
     * exception if the document does not exist.
     *
     * @param config storage configuration to apply
     * @param path the path of the resource (relative to a root)
     * @param cascade a constant ONE_LEVEL, NONE or SUBTREE
     * @throws StorageException if the document does not exist
     */
    public void delete(Map config, String path, int cascade) throws StorageException;

    /**
     * Performs a batch of modifications to the database in a single transaction
     *
     * @param tabModif array of modifications to perform
     * @throws StorageException
     */
    public void batchModify(Modification[] tabModif) throws StorageException;

    /**
     * Creates a modification object. Does not modify the database.
     *
     * @param type a constant UPDATE, DELETE or CREATE
     * @param fullpath full path of the document impacted by the modification
     * @param rawDocument value of the new resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @return
     */
    public Modification createModification(int type, String fullpath, byte[] rawDocument, Collection searchAttributes);

    /**
     * Creates a modification object. Does not modify the database.
     *
     * @param config storage configuration to apply
     * @param type a constant UPDATE, DELETE or CREATE
     * @param fullpath full path of the document impacted by the modification
     * @param rawDocument value of the new resource
     * @param searchAttributes a collection containing instances of com.actility.util.Pair
     * @return
     */
    public Modification createModification(Map config, int type, String fullpath, byte[] rawDocument,
            Collection searchAttributes);

    /**
     * Reserves space for the creation of future documents
     *
     * @param config StorageConfiguration to apply
     * @param path path under which documents are going to be created
     * @param docNumber maximum number of documents that can be created in reserved space
     * @param docSize maximum size of documents that can be created in reserved space
     * @return A reservation code
     */
    public String reserveSpace(Map config, String path, String docNumber, double docSize);
}
