package org.example;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;

public class Record {   //класс для хранения строкии доступа к полям
    long id;
    String name;

    public Boolean r(String csvLine) {
        try {
            String[] parts = csvLine.split(",");
            this.id = Integer.parseInt(parts[0]);
            this.name = parts[1];
        } catch (Exception e) {
            System.out.println("ошибка в заполнении Record");
            return FALSE;
        }

        return TRUE;
    }
    Record(long id,String name ) {
        this.id = id;
        this.name = name;
        return;
    }
    Record() {
        return;
    }

    public String getString (){
//      FIXME  STRINGBUILDER
        String str = id + "," + name;
        return str;
    }
}
