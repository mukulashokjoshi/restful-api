/* ****************************************************************************
Copyright 2013 Ellucian Company L.P. and its affiliates.
******************************************************************************/
package net.hedtech.restfulapi.config

import java.text.SimpleDateFormat

import net.hedtech.restfulapi.extractors.json.*

class JSONExtractorDelegate {

    JSONExtractorConfig config = new JSONExtractorConfig()

    PropertyOptions property(String name) {
        config.dottedRenamedPaths.remove(name)
        config.dottedShortObjectPaths.remove(name)
        config.dottedFlattenedPaths.remove(name)
        config.dottedValuePaths.remove(name)
        config.dottedDatePaths.remove(name)
        new PropertyOptions(name)
    }

    JSONExtractorDelegate setInherits(Collection c) {
        config.inherits = c
        this
    }

    JSONExtractorDelegate setShortObjectClosure(Closure c) {
        config.shortObjectClosure = c
        this
    }

    JSONExtractorDelegate shortObject(Closure c) {
        setShortObjectClosure( c )
        this
    }

    JSONExtractorDelegate setDateFormats(Collection formats) {
        formats.each {
            try {
                new SimpleDateFormat(it)
            } catch (IllegalArgumentException) {
                throw new RuntimeException("Invalid date format $it")
            }
        }
        config.dateFormats = formats
        this
    }

    class PropertyOptions {
        String propertyName
        PropertyOptions(String propertyName) {
            this.propertyName = propertyName
        }

        PropertyOptions name(String name) {
            config.dottedRenamedPaths.put(propertyName,name)
            this
        }

        PropertyOptions shortObject() {
            config.dottedShortObjectPaths.add propertyName
            this
        }

        PropertyOptions shortObject(boolean b) {
            if (b) {
                config.dottedShortObjectPaths.add propertyName
            } else {
                config.dottedShortObjectPaths.remove propertyName
            }
            this
        }

        PropertyOptions date(boolean b) {
            if (b) {
                config.dottedDatePaths.add propertyName
            } else {
                config.dottedDatePats.remove propertyName
            }
            this
        }

        PropertyOptions flatObject() {
            config.dottedFlattenedPaths.add propertyName
            this
        }

        PropertyOptions flatObject(boolean b) {
            if (b) {
                config.dottedFlattenedPaths.add propertyName
            } else {
                config.dottedFlattenedPaths.remove propertyName
            }
            this
        }

        PropertyOptions defaultValue(Object val) {
            config.dottedValuePaths.put(propertyName,val)
            this
        }
    }
}
