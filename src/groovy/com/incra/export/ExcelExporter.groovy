package com.incra.export

import java.io.OutputStream
import java.io.Writer
import java.util.List
import java.util.Map

import org.apache.commons.beanutils.PropertyUtils

/**
 * @author Jeff Risberg (with portions from the Export plugin by Andreas Schmitt)
 * @since November 2011
 */
class ExcelExporter {

    Map labels = [:]
    Map formatters = [:]
    Map parameters = [:]

    public void exportData(OutputStream outputStream, List data, List fields) {
        try {
            def builder = new ExcelBuilder()

            builder {
                workbook(outputStream: outputStream) {
                    int numFields = fields.size()

                    sheet(name: parameters.get("sheetName") ?: "Export", widths: parameters.get("column.widths")) {
                        // Default format
                        format(name: "header") {
                            font(name: "arial", bold: true)
                        }

                        // Create titles
                        List<String> titles = parameters.get("titles") ?: []
                        titles.eachWithIndex { title, index ->
                            cell(row: index, column: 0, value: title, format: "header")
                        }

                        // Create header
                        fields.eachWithIndex { field, index ->
                            String value = getLabel(field)
                            cell(row: titles.size(), column: index, value: value, format: "header")
                        }

                        //Rows
                        data.eachWithIndex { object, k ->
                            fields.eachWithIndex { field, i ->
                                Object value = getValue(object, field)
                                cell(row: k + titles.size() + 1, column: i, value: value)
                            }
                        }
                    }
                }
            }

            builder.write()
        }
        catch(Exception e) {
            throw new RuntimeException("Error during export", e)
        }
    }

    protected String getLabel(String field) {
        if (labels.containsKey(field)) {
            return labels[field]
        }

        return field
    }

    protected Object getValue(Object domain, String field) {
        return formatValue(domain, getNestedValue(domain, field), field)
    }

    protected Object formatValue(Object domain, Object value, String field) {
        if (formatters?.containsKey(field)) {
            return formatters[field].call(domain, value)
        }

        return value
    }

    protected Writer getOutputStreamWriter(OutputStream outputStream) {
        Writer outputStreamWriter

        if (parameters?.containsKey("encoding")) {
            outputStreamWriter = new OutputStreamWriter(outputStream, parameters["encoding"])
        }
        else {
            outputStreamWriter = new OutputStreamWriter(outputStream)
        }

        return outputStreamWriter
    }

    /**
     * This is a generalization of PropertyUtils    
     */
    protected Object getNestedValue(Object domain, String field) {
        try {
            // Doesn't work for dynamic properties such as tags used in taggable
            return PropertyUtils.getProperty(domain, field)
        }
        catch(Exception ex){
            // Alternative method for dynamic properties such as tags used in taggable
            def subProps = field.split("\\.")

            int i = 0
            def lastProp
            for(prop in subProps){
                if(i == 0){
                    try {
                        lastProp = domain?."$prop"
                    }
                    catch(Exception e){
                        log.info("Couldn't retrieve property ${prop}", e)
                    }
                }
                else {
                    try {
                        lastProp = lastProp?."$prop"
                    }
                    catch(Exception e){
                        log.info("Couldn't retrieve property ${prop}", e)
                    }
                }
                i += 1
            }

            return lastProp
        }
    }
}