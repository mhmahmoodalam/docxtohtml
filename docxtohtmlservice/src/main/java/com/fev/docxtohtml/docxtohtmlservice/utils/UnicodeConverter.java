package com.fev.docxtohtml.docxtohtmlservice.utils;

import org.apache.commons.text.translate.AggregateTranslator;
import org.apache.commons.text.translate.CharSequenceTranslator;
import org.apache.commons.text.translate.EntityArrays;
import org.apache.commons.text.translate.LookupTranslator;

public class UnicodeConverter {


    public static final CharSequenceTranslator ESCAPE_HTML4_CUSTOM =
            new AggregateTranslator(
                    new LookupTranslator(EntityArrays.BASIC_ESCAPE),
                    new LookupTranslator(EntityArrays.ISO8859_1_ESCAPE),
                    new LookupTranslator(EntityArrays.HTML40_EXTENDED_ESCAPE)
            );

}
