package com.antonromanov.arnote.model.investing.response.serializers;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import java.util.List;

public class ListSerializer extends StdSerializer<List<String>> {
//public class ListSerializer extends StdSerializer{
//public class ListSerializer extends JsonSerializer<List<String>> {

    public ListSerializer(JavaType type) {
        super(type);
    }




    /* @Override
    public void serialize(Object o, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) throws IOException {

    }*/
/*
    @Override
    public void serialize(List<String> listWrapper, JsonGenerator jgen, SerializerProvider serializerProvider) throws IOException {
        jgen.writeStartArray();

       *//* for(Object o : value.list()){
            jgen.writeStartObject();
            if(o instanceof Integer){
                jgen.writeNumberField("java.lang.Integer", (int) o);
            }else if(o instanceof Double){ //by default, floated values are represented by double
                jgen.writeNumberField("java.lang.Float", (double) o);
            }else if(o instanceof Color){
                jgen.writeStringField("myPackage.Color", ((Color)o).toString());
            }else if(o instanceof Date){
                jgen.writeStringField("java.util.Date", o.toString());
            }else{ //default to String
                jgen.writeStringField("java.lang.String", o.toString());
            }
            jgen.writeEndObject();
        }*//*

        jgen.writeStringField("tt", "1");
        jgen.writeEndArray();
    }*/





    @Override
    public void serialize(List<String> stringList, JsonGenerator gen, SerializerProvider serializerProvider) throws IOException {
        gen.writeStartObject();
        /*for (Property p : value) {
            gen.writeStringField(p.getName(), p.getValue());
        }*/
        gen.writeEndObject();
    }
}
