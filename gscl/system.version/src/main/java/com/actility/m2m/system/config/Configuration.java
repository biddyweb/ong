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
 * Please contact Actility, SA.,  4, rue Ampère 22300 LANNION FRANCE
 * or visit www.actility.com if you need additional
 * information or have any questions.
 *
 * id $Id: $
 * author $Author: $
 * version $Revision: $
 * lastrevision $Date: $
 * modifiedby $LastChangedBy: $
 * lastmodified $LastChangedDate: $
 */

package com.actility.m2m.system.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.osgi.service.cm.ConfigurationAdmin;

import com.actility.m2m.system.log.BundleLogger;
import com.actility.m2m.util.log.OSGiLogger;
import com.actility.m2m.xo.XoObject;

public class Configuration {
    private static final Logger LOG = OSGiLogger.getLogger(Configuration.class, BundleLogger.LOG);
    private ConfigurationAdmin configAdmin;
    private String bundleName;
    private Map config;
    private org.osgi.service.cm.Configuration osgiConfig;
    /**
     * Constructor
     * @param bundleName the name of the current bundle
     */
    public Configuration(String bundleName) {
        this.config = new HashMap();
        this.configAdmin = null;
        this.bundleName = bundleName;
        LOG.info("Bundle symbolic name: "+bundleName);
        putDefaultValues();
    }
    
    /**
     * Sets the config admin service used to modify configuration
     * @param configAdmin The configuration admin service
     */
    public void setConfigAdminService(ConfigurationAdmin configAdmin) {
        this.configAdmin = configAdmin;
        if(this.configAdmin!=null){
            try {
                this.osgiConfig = this.configAdmin.getConfiguration(bundleName+".config");
            } catch (IOException e) {
                LOG.error("Impossible to get the bundle configuration",e);
            }
        }
    }
    /**
     * This method was called when a new configuration was loaded
     * @param config
     */
    public void update(Dictionary config){
        LOG.info("The config has changed");
        if(config!=null){
            Enumeration e = config.keys();
            while (e.hasMoreElements()) {
                String key = (String) e.nextElement();
                Object value = config.get(key);
                if(value !=null){
                    this.config.put(key,value);
                }
            }
        }
    }
    /**
     * Puts the default values in the configuration
     */
    private void putDefaultValues(){
        putDefaultValue(Name.HIGH_RATE_LOGGING_SIZE,new Integer(60));
        putDefaultValue(Name.MEDIUM_RATE_LOGGING_SIZE,new Integer(96));
        putDefaultValue(Name.LOW_RATE_LOGGING_SIZE,new Integer(112));
        putDefaultValue(Name.ALARMS_LOGGING_SIZE,new Integer(30));
        putDefaultValue(Name.ALARM_LOAD_AVG_THRESHOLD_MAJ,new Float(0.8f));
        putDefaultValue(Name.ALARM_LOAD_AVG_THRESHOLD_CRIT,new Float(0.9f));
        putDefaultValue(Name.ALARM_RAM_THRESHOLD_MAJ,new Float(0.2f));
        putDefaultValue(Name.ALARM_RAM_THRESHOLD_CRIT,new Float(0.1f));
        putDefaultValue(Name.ALARM_DISK_THRESHOLD_MAJ,new Float(0.2f));
        putDefaultValue(Name.ALARM_DISK_THRESHOLD_CRIT,new Float(0.1f));
        putDefaultValue(Name.MAX_RATE_DISK_IO,null);
        putDefaultValue(Name.ALARM_DISK_IO_THRESHOLD_MAJ,null);
        putDefaultValue(Name.ALARM_DISK_IO_THRESHOLD_CRIT,null);
        putDefaultValue(Name.DISK_MONITORING_EXCLUSIONS, new ArrayList());
        putDefaultValue(Name.DISK_PART_MONITORING_EXCLUSIONS, new ArrayList());
        putDefaultValue(Name.MAX_RATE_NET_IO,null);
        putDefaultValue(Name.ALARM_NET_IO_THRESHOLD_MAJ,null);
        putDefaultValue(Name.ALARM_NET_IO_THRESHOLD_CRIT,null);
        putDefaultValue(Name.NET_MONITORING_EXCLUSIONS,new ArrayList());
        putDefaultValue(Name.ALARM_TEMP_THRESHOLD_MAJ,null);
        putDefaultValue(Name.ALARM_TEMP_THRESHOLD_CRIT,null);

        putDefaultValue(Name.TIME_BETWEEN_INIT,new Integer(5000));

    }
    /**
     * Adds a new configuration value from an xoObject
     * @param xoObject The xoObject to add in configuration
     */
    public void updateValue(XoObject xoObject){
        String xoType = xoObject.getType();
        String xoName = xoObject.getStringAttribute("name");
        String xoVal = xoObject.getStringAttribute("val");
        if(xoType==null || xoName==null || xoVal==null){
            return;
        }
        if(Configuration.Name.isKeyValid(xoName)){
            Class valid = Configuration.Name.getTypeForKey(xoName);
            if(valid!=null){
                if(valid.equals(Integer.class)){
                    if(xoType.equals("o:int")){
                        try{
                            Integer val = new Integer(xoVal);
                            config.put(xoName, val);
                        }catch(NumberFormatException e){
                            LOG.error("",e);
                        }
                    }
                }
                if(valid.equals(Float.class)){
                    if(xoType.equals("o:real")){
                        try{
                            Float val = new Float(xoVal);
                            config.put(xoName, val);
                        }catch(NumberFormatException e){
                            LOG.error("",e);
                        }
                    }
                }
                if(valid.equals(List.class)){
                    if(xoType.equals("o:str")){
                        int space;
                        List val = new ArrayList();
                        while((space = xoVal.indexOf(' '))>0){
                            String subVal = xoVal.substring(0,space);
                            val.add(subVal);
                            xoVal = xoVal.substring(space+1);
                        }
                        val.add(xoVal);
                        config.put(xoName, val);
                    }
                }
            }
        }else{
            LOG.info("The config key "+xoName+" isn't valid");
        }
    }
    /**
     * Saves the configuration into the disk
     */
    public void saveConfig(){
        if(osgiConfig!=null){
            Dictionary dict = osgiConfig.getProperties();
            Set keys = config.keySet();
            Iterator iterator = keys.iterator();
            while(iterator.hasNext()){
                Object key = iterator.next();
                if(key!=null){
                    Object value = config.get(key);
                    if(value!=null){
                        dict.put(key, value);
                    }
                }
            }
            try {
                osgiConfig.update(dict);
            } catch (IOException e) {
                LOG.error("Impossible to update the bundle configuration",e);
            }
        }
    }
    
    /**
     * Puts the value in the configuration after verification of datas
     * @param name The configuration name
     * @param value The configuration value
     */
    private void putDefaultValue(String name, Object value){
        if(name == null || value == null){
            return;
        }
        if(config.containsKey(name)==false){
            config.put(name, value);
        }
    }
    /**
     * Gives the configuration from its name
     * @param name
     * @return
     */
    public Object getValue(String name){
        return this.getValue(name, Configuration.Name.getTypeForKey(name), null);
    }
    
    /**
     * Gives the configuration from its name
     * @param name
     * @param defaultValue
     * @return
     */
    public Object getValue(String name, Object defaultValue){
        return this.getValue(name, Configuration.Name.getTypeForKey(name), defaultValue);
    }
//    public Object getValue(String name, Class type){
//        return this.getValue(name, type, null);
//    }
    
    /**
     * Gives the configuration from its name and verifies its type
     * @param name
     * @param type
     * @param defaultValue
     * @return
     */
    private Object getValue(String name, Class type, Object defaultValue){
        Object result = defaultValue;
        Object value = config.get(name);
        if (value == null) {
            if (LOG.isEnabledFor(Level.WARN)) {
                LOG.warn("Configuration property " + name + " is null -> use default value instead " + defaultValue);
            }
        } else if (!type.isAssignableFrom(value.getClass())) {
            LOG.error("Configuration property " + name + " must be of type " + type.toString() + " while it is of type "
                    + value.getClass() + " -> use default value instead " + defaultValue);
        } else if (type != String.class || ((String) value).length() != 0) {
            result = value;
        }
        return result;
    }
    

    /**
     * This class gives all configuration names allowed
     * @author qdesrame
     *
     */
    public static class Name {
        public static final String HIGH_RATE_LOGGING_SIZE = "highRateLoggingSize";
        public static final String MEDIUM_RATE_LOGGING_SIZE = "mediumRateLoggingSize";
        public static final String LOW_RATE_LOGGING_SIZE = "lowRateLoggingSize";
        public static final String ALARMS_LOGGING_SIZE = "alarmsLoggingSize";

        public static final String ALARM_LOAD_AVG_THRESHOLD_MAJ = "alarmLoadAvgThresholdMaj";
        public static final String ALARM_LOAD_AVG_THRESHOLD_CRIT = "alarmLoadAvgThresholdCrit";
        public static final String ALARM_RAM_THRESHOLD_MAJ = "alarmRamThresholdMaj";
        public static final String ALARM_RAM_THRESHOLD_CRIT = "alarmRamThresholdCrit";
        public static final String ALARM_DISK_THRESHOLD_MAJ = "alarmDiskThresholdMaj";
        public static final String ALARM_DISK_THRESHOLD_CRIT = "alarmDiskThresholdCrit";
        public static final String MAX_RATE_DISK_IO = "maxRateDiskIo";
        public static final String ALARM_DISK_IO_THRESHOLD_MAJ = "alarmDiskIoThresholdMaj";
        public static final String ALARM_DISK_IO_THRESHOLD_CRIT = "alarmDiskIoThresholdCrit";
        public static final String DISK_MONITORING_EXCLUSIONS = "diskMonitoringExclusions";
        public static final String DISK_PART_MONITORING_EXCLUSIONS = "diskPartMonitoringExclusions";
        public static final String MAX_RATE_NET_IO = "maxRateNetIo";
        public static final String ALARM_NET_IO_THRESHOLD_MAJ = "alarmNetIoThresholdMaj";
        public static final String ALARM_NET_IO_THRESHOLD_CRIT = "alarmNetIoThresholdCrit";
        public static final String NET_MONITORING_EXCLUSIONS = "netMonitoringExclusions";
        public static final String ALARM_TEMP_THRESHOLD_MAJ = "alarmTempThresholdMaj";
        public static final String ALARM_TEMP_THRESHOLD_CRIT = "alarmTempThresholdCrit";

        public static final String TIME_BETWEEN_INIT = "timeBetweenInit";
        /**
         * Verifies if the configration key is allowed
         * @param key
         * @return
         */
        public static boolean isKeyValid(String key){
            if(key==null){
                return false;
            }
            return key.equals(HIGH_RATE_LOGGING_SIZE)||key.equals(MEDIUM_RATE_LOGGING_SIZE)||key.equals(LOW_RATE_LOGGING_SIZE)||key.equals(ALARMS_LOGGING_SIZE)
                    ||key.equals(ALARM_LOAD_AVG_THRESHOLD_MAJ)||key.equals(ALARM_LOAD_AVG_THRESHOLD_CRIT)||key.equals(ALARM_RAM_THRESHOLD_MAJ)||key.equals(ALARM_RAM_THRESHOLD_CRIT)
                    ||key.equals(ALARM_DISK_THRESHOLD_MAJ)||key.equals(ALARM_DISK_THRESHOLD_CRIT)||key.equals(MAX_RATE_DISK_IO)||key.equals(ALARM_DISK_IO_THRESHOLD_MAJ)
                    ||key.equals(ALARM_DISK_IO_THRESHOLD_CRIT)||key.equals(DISK_MONITORING_EXCLUSIONS)||key.equals(DISK_PART_MONITORING_EXCLUSIONS)||key.equals(MAX_RATE_NET_IO)
                    ||key.equals(ALARM_NET_IO_THRESHOLD_MAJ)||key.equals(ALARM_NET_IO_THRESHOLD_CRIT)||key.equals(NET_MONITORING_EXCLUSIONS)||key.equals(ALARM_TEMP_THRESHOLD_MAJ)
                    ||key.equals(ALARM_TEMP_THRESHOLD_CRIT)||key.equals(TIME_BETWEEN_INIT);
        }
        /**
         * Gives the type of configuration from the key
         * @param key
         * @return
         */
        public static Class getTypeForKey(String key){
            if(key==null){
                return null;
            }
            if(key.equals(HIGH_RATE_LOGGING_SIZE)){
                return Integer.class;
            }
            if(key.equals(MEDIUM_RATE_LOGGING_SIZE)){
                return Integer.class;
            }
            if(key.equals(LOW_RATE_LOGGING_SIZE)){
                return Integer.class;
            }
            if(key.equals(ALARMS_LOGGING_SIZE)){
                return Integer.class;
            }
            if(key.equals(ALARM_LOAD_AVG_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_LOAD_AVG_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(ALARM_RAM_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_RAM_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(ALARM_DISK_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_DISK_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(MAX_RATE_DISK_IO)){
                return Float.class;
            }
            if(key.equals(ALARM_DISK_IO_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_DISK_IO_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(DISK_MONITORING_EXCLUSIONS)){
                return List.class;
            }
            if(key.equals(DISK_PART_MONITORING_EXCLUSIONS)){
                return List.class;
            }
            if(key.equals(MAX_RATE_NET_IO)){
                return Float.class;
            }
            if(key.equals(ALARM_NET_IO_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_NET_IO_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(NET_MONITORING_EXCLUSIONS)){
                return List.class;
            }
            if(key.equals(ALARM_TEMP_THRESHOLD_MAJ)){
                return Float.class;
            }
            if(key.equals(ALARM_TEMP_THRESHOLD_CRIT)){
                return Float.class;
            }
            if(key.equals(TIME_BETWEEN_INIT)){
                return Integer.class;
            }
            return null;
        }
    }

}
