package com.fev.docxtohtml.docxtohtmlservice.utils;
import com.fev.docxtohtml.docxtohtmlservice.exceptions.GenericException;
import com.fev.docxtohtml.docxtohtmlservice.exceptions.OperationaFailedException;
import com.fev.docxtohtml.docxtohtmlservice.parser.ParagraphParser;
import com.fev.docxtohtml.docxtohtmlservice.resource.Operations;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.util.ZipSecureFile;
import org.apache.poi.xwpf.usermodel.*;
import org.apache.xmlbeans.XmlCursor;
import org.jsoup.Jsoup;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTMarkupRange;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STNumberFormat;
import org.w3c.dom.Attr;
import org.w3c.dom.Node;

public class WordToHtmlConverter {
    static Logger logger = Logger.getLogger(WordToHtmlConverter.class.getName());

    private boolean hasParagraphStarted = false;
    private boolean isParagraphListOrdered = false;
    private final List<String> modalList = new ArrayList<>();
    private final List<String> htmlElements = new ArrayList<>();

    private int majorVersion = 0;
    private int minorVersion = 0;
    private int alphaVersion = 0;
    private boolean convertNumberTolist;
    private final ArrayList<String> fileChangesList = new ArrayList<>();

    public void setConvertNumberTolist(boolean convertNumberTolist) {
        this.convertNumberTolist = convertNumberTolist;
    }

    public List<String> getFileChangesList() {
        return fileChangesList;
    }

    public XWPFDocument openDocument(String msWordPath) throws GenericException, OperationaFailedException {
        if (StringUtils.isBlank(msWordPath)) {
            throw new GenericException("Path to file not specified - %s".formatted(msWordPath));
        }
        try (XWPFDocument document = new XWPFDocument(Files.newInputStream(Path.of(msWordPath)))) {
            ZipSecureFile.setMinInflateRatio(-1.0d);
            majorVersion = 0;
            minorVersion = 0;
            alphaVersion = 0;
            return document;
        } catch (Exception ex) {
            logger.log(Level.WARNING, "Exception occurred while opening document {0}",ex.getMessage());
            throw new OperationaFailedException(Operations.FILE_OPEN.getType(), ex);
        }

    }


    public List<XWPFParagraph> getAllParagraphs(XWPFDocument msDocument) throws OperationaFailedException {
        if (msDocument == null) {
            throw new OperationaFailedException(Operations.FILE_READ.getType(), " XWPFDocument is null");
        }
        return msDocument.getParagraphs();
    }


    public List<String> convertParagraphToHtml(XWPFParagraph docParagraph) throws OperationaFailedException {
        if (docParagraph == null) {
            throw new OperationaFailedException(Operations.FILE_READ.getType(), " XWPFParagraph is null");
        }
        return Collections.singletonList(docParagraph.getText());
    }

    public String convertDocBodyToHtml(List<IBodyElement> bodyElements, String language) throws Exception {
        if (bodyElements == null) {
            throw new OperationaFailedException(Operations.FILE_READ.getType(), " bodyElements is null");
        }
        traverseBodyElements(bodyElements);
        htmlElements.addAll(modalList);
        //Todo enable this if you want to handle brand key words
//        if (!convertNumberTolist) {
//            handleBrandKeyword(language);
//        }
        return convertListTohtml(htmlElements, "<div>", "</div>");
    }

//    private void handleBrandKeyword(String language) {
//        var brandKeyWordsConverter = new BrandNameToPlaceHolderConverter();
//        var keywords = brandKeyWordsConverter.getBrandSearchString(language);
//        String[] brandReplacePlaceholders = brandKeyWordsConverter.getSearchStringReplacePlaceHolder();
//        for (int i = 0; i < htmlElements.size(); i++) {
//            var paragraph = htmlElements.get(i);
//            var replacedString = StringUtils.replaceEachRepeatedly(paragraph, keywords, brandReplacePlaceholders);
//            if (!StringUtils.equals(replacedString, paragraph)) {
//                htmlElements.remove(i);
//                htmlElements.add(i, replacedString);
//                fileChangesList.add("\nChanged %s --> %s \nkeywords used %s\n"
//                        .formatted(paragraph, replacedString, Arrays.toString(keywords)));
//            }
//        }
//    }

    private void traverseBodyElements(List<IBodyElement> bodyElements) {
        for (IBodyElement bodyElement : bodyElements) {
            if (bodyElement instanceof XWPFParagraph paragraph) {
                parseParagraphForDocument(paragraph);
            } else if (bodyElement instanceof XWPFTable table) {
                parseTableForDocument(table);
            }
        }
    }

    private void parseTableForDocument(XWPFTable table) {
        if (table.getRows().isEmpty()) {
            return;
        }
        htmlElements.add("<table><tbody>");
        for (var row : table.getRows()
        ) {
            htmlElements.add("<tr>");
            for (var col : row.getTableCells()
            ) {
                htmlElements.add("<td>");
                for (var paragraph : col.getParagraphs()) {
                    parseParagraphForDocument(paragraph);
                }
                htmlElements.add("</td>");
            }
            htmlElements.add("</tr>");
        }

        htmlElements.add("</tbody></table>");
    }

    private void parseParagraphForDocument(XWPFParagraph paragraph) {
        var text = paragraph.getText();
        var paragraphStyle = paragraph.getStyle();
        if (StringUtils.isBlank(text)) {
            htmlElements.add("<br>");
            return;
        }
        if (shouldSkipTextFromConversion(text)) {
            fileChangesList.add("skipping text from html %s".formatted(text));
            return;
        }
        var modalId = handleCommentForParagraph(paragraph);
        StringBuilder str = new StringBuilder(text.length());
        handleStyle(paragraph, str, modalId);

        if (shouldParseNumbers(paragraph)) {
            parseDecimalNumbering(paragraph, str);
        } else if (shouldParseBullet(paragraph)) {
            parseBulletNumbering(str);
        } else if (shouldParseCustomNumbering(paragraph)) {
            parseCustomNumbering(str);

        } else {
            handleClosingNumbering();
            parseParagraphText(paragraphStyle, str);
        }
    }

    private void parseParagraphText(String paragraphStyle, StringBuilder str) {
        var textStr = str.toString();
        if (str.length() > 0) {
            htmlElements.add(paragraphStyle != null && paragraphStyle.equals("BBHeading0") ? "<h3>" : "<p>");
            htmlElements.add(parseEmbeddedNumbering(textStr));
            htmlElements.add(paragraphStyle != null && paragraphStyle.equals("BBHeading0") ? "</h3>" : "</p>");
        }
    }

    private void handleClosingNumbering() {
        if (hasParagraphStarted) {
            hasParagraphStarted = false;

            htmlElements.add(
                    isParagraphListOrdered
                            ? "</ol></p><br>"
                            : "</ul></p><br>");
            isParagraphListOrdered = false;
        }
    }

    private void parseCustomNumbering(StringBuilder str) {
        if (!hasParagraphStarted) {
            hasParagraphStarted = true;
            isParagraphListOrdered = true;
            htmlElements.add("<p><ol>");
        }
        insertTag(str, "<li>", "</li>");
        htmlElements.add(str.toString());
    }

    private void parseBulletNumbering(StringBuilder str) {
        if (!hasParagraphStarted) {
            hasParagraphStarted = true;
            htmlElements.add("<ul>");
        }
        insertTag(str, "<li>", "</li>");
        htmlElements.add(str.toString());
    }

    private void parseDecimalNumbering(XWPFParagraph paragraph, StringBuilder str) {
        if (str.length() > 0) {
            String number = parseNumberingInfo(paragraph);
            htmlElements.add("<p><strong>%s</strong>".formatted(number));
            htmlElements.add(str.toString());
            htmlElements.add("</p>");
        }
    }

    private static boolean shouldSkipTextFromConversion(String text) {
        return text.strip().startsWith("[") && text.strip().endsWith("]");
    }

    private static boolean shouldParseCustomNumbering(XWPFParagraph paragraph) {
        return paragraph.getNumFmt() != null
                && (!(paragraph.getNumFmt().equals(STNumberFormat.BULLET.toString())
                || paragraph.getNumFmt().equals(STNumberFormat.CUSTOM.toString())));
    }

    private static boolean shouldParseBullet(XWPFParagraph paragraph) {
        return paragraph.getNumFmt() != null && paragraph.getNumFmt().equals("bullet");
    }

    private boolean shouldParseNumbers(XWPFParagraph paragraph) {
        return paragraph.getNumFmt() != null && paragraph.getNumFmt().contains("decimal") && this.convertNumberTolist;
    }

    private String parseEmbeddedNumbering(String runText) {

        var pattern = Pattern.compile("\\d{1,3}\\.\\d{0,3}[.]?\\d{0,3}", Pattern.DOTALL);
        Matcher matcher = pattern.matcher(runText);
        if (matcher.find()) {
            var matchedText = matcher.group();
            return matcher.replaceFirst("<strong>%s&ensp;</strong>".formatted(matchedText));
        }
        return runText;
    }

    private String parseNumberingInfo(XWPFParagraph paragraph) {
        var id = paragraph.getNumIlvl();
        if (id.intValue() == 0) {
            majorVersion++;
            minorVersion = 0;
            alphaVersion = 0;
            return "%d.&ensp;".formatted(majorVersion);
        }
        if (id.intValue() == 1) {
            minorVersion++;
            alphaVersion = 0;
            return "%d.%d&ensp;".formatted(majorVersion, minorVersion);
        }
        if (id.intValue() == 2) {
            alphaVersion++;
            return "%d.%d.%d&ensp;".formatted(majorVersion, minorVersion, alphaVersion);
        }
        return paragraph.getNumLevelText();
    }


    private List<String> parseParagraphForComments(XWPFComment comment) {
        boolean hasListStarted = false;
        boolean isOrderedList = false;
        var commentsHtmlElements = new ArrayList<String>();
        var docParagraph = comment.getParagraphs();
        var infoParagraphs = docParagraph.stream()
                .filter(p -> p.getText().contains("Info to")
                        || p.getText().contains("info to")
                        || p.getText().contains("Lotte:")
                        || p.getText().contains("Información para el traducto")
                        || p.getText().contains("Please either keep")).toList();

        if (docParagraph.isEmpty() || !infoParagraphs.isEmpty()) {
            fileChangesList.add("Ignored comment %s".formatted(comment.getText()));
            return commentsHtmlElements;
        }
        for (var paragraph : docParagraph
        ) {
            var text = paragraph.getText();
            if (StringUtils.isBlank(text)) {
                commentsHtmlElements.add("<br>");
                continue;
            }
            StringBuilder str = new StringBuilder(text.length());
            handleStyle(paragraph, str, null);
            if (shouldParseBullet(paragraph)) {
                if (!hasListStarted) {
                    hasListStarted = true;
                    commentsHtmlElements.add("<p><ul>");
                }
                insertTag(str, "<li>", "</li>");
                commentsHtmlElements.add(str.toString());
            } else if (shouldParseCustomNumbering(paragraph)) {
                if (!hasListStarted) {
                    hasListStarted = true;
                    isOrderedList = true;
                    commentsHtmlElements.add("<p><ol>");
                }
                insertTag(str, "<li>", "</li>");
                commentsHtmlElements.add(str.toString());

            } else {
                if (hasListStarted) {
                    hasListStarted = false;
                    commentsHtmlElements.add(
                            isOrderedList
                                    ? "</ol></p><br>"
                                    : "</ul></p><br>");
                    isOrderedList = false;
                }
                if (str.length() > 0) {
                    commentsHtmlElements.add("<p>");
                    commentsHtmlElements.add(str.toString());
                    commentsHtmlElements.add("</p>");
                }
            }
        }

        return commentsHtmlElements;
    }

    private String handleCommentForParagraph(XWPFParagraph paragraph) {
        XWPFComment comment;
        for (CTMarkupRange anchor : paragraph.getCTP().getCommentRangeStartArray()) {
            if ((comment = paragraph.getDocument().getCommentByID(anchor.getId().toString())) != null) {
                var modalId = "modal%s".formatted(comment.getId());
                var parsedParagraphs = parseParagraphForComments(comment);
                if (parsedParagraphs.isEmpty()) {
                    return null;
                }
                var commentHtml = convertListTohtml(parsedParagraphs, getModalParentTag(modalId), "</div>");
                modalList.add(commentHtml);
                return modalId;
            }
        }
        return null;
    }


    private String getModalParentTag(String modalId) {
        return "<div id='%s' class='modal'><span class='close' >&times;</span><br>".formatted(modalId);
    }

    private String convertListTohtml(List<String> list, String parentTag, String closingParentTag) {
        StringBuilder sb = new StringBuilder(64);
        list.forEach(sb::append);
        if (StringUtils.isNotEmpty(parentTag) && StringUtils.isNotEmpty(closingParentTag)) {
            insertTag(sb, parentTag, closingParentTag);
        }
        var doc = Jsoup.parseBodyFragment(sb.toString());
        doc.outputSettings().prettyPrint(true);
        var body = doc.body();
        return body.html();
    }

    private void insertTag(StringBuilder str, String tag, String closingTag) {
        str.insert(0, tag);
        str.insert(str.length(), closingTag);
    }

    private ParagraphParser handleStyle(XWPFParagraph paragraph, StringBuilder s1, String modalId) {

        var paragraphParser = new ParagraphParser(paragraph, modalId);
        for (var runElement : paragraph.getIRuns()) {
            if (runElement instanceof XWPFHyperlinkRun hyperLink) {
                paragraphParser.ParseRunElement(hyperLink);
            } else if (runElement instanceof XWPFRun text) {
                paragraphParser.ParseRunElement(text);
            }

        }
        var elements = paragraphParser.getElements();
        if (paragraphParser.isBold()) {
            elements.add("</strong>");
        }
        if (paragraphParser.isItalic()) {
            elements.add("</em>");
        }
        if (paragraphParser.isUnderlined()) {
            elements.add(paragraphParser.getClosingUnderLinedElement());
        }

        elements.forEach(s1::append);
        return paragraphParser;
    }


    private String getCommentHighlightedText(XWPFParagraph paragraph) {
        boolean hasComment = false;
        String commentReferenceText = "";
        String startId = "";
        String endId = "";
        for (CTMarkupRange anchor : paragraph.getCTP().getCommentRangeStartArray()) {
            XmlCursor xmlCursor = anchor.newCursor();
            while (xmlCursor.hasNextToken()) {

                Node node = xmlCursor.getDomNode();

                if (hasComment) {
                    if (node.getNodeName().contains("#text")) {
                        commentReferenceText += node.getNodeValue();
                    }

                }
                if (node.getNodeName().contains("w:commentRangeStart")) {
                    Attr attr = (Attr) node.getAttributes().getNamedItem("w:id");
                    if (attr != null) {
                        startId = attr.getValue();
                    }

                    if (anchor.getId().toString().equals(startId)) {
                        hasComment = true;
                    }
                } else if (node.getNodeName().contains("w:commentRangeEnd")) {
                    Attr attr = (Attr) node.getAttributes().getNamedItem("w:id");
                    if (attr != null) {
                        endId = attr.getValue();
                        //System.out.println("endId: " + endId);
                    }

                    if (anchor.getId().toString().equals(endId)) {
                        return commentReferenceText;
                    }
                }
                xmlCursor.toNextToken();
            }
        }
        return commentReferenceText;
    }
}
