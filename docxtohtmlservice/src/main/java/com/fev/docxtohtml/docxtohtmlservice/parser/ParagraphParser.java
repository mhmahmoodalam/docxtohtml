package com.fev.docxtohtml.docxtohtmlservice.parser;

import com.fev.docxtohtml.docxtohtmlservice.utils.UnicodeConverter;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.xwpf.usermodel.XWPFHyperlinkRun;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.STUnderline;

public class ParagraphParser {

    private final boolean hasEmbeddedPopup;
    private final XWPFParagraph xwpfParagraph;
    private boolean isBold = false;
    private boolean isUnderlined = false;
    private boolean isItalic = false;
    private List<String> elements = new ArrayList<>();
    private String modalId;
    private String direction;
    private String language;

    public ParagraphParser(XWPFParagraph xwpfParagraph, String modalId) {
        this.xwpfParagraph = xwpfParagraph;
        this.hasEmbeddedPopup = StringUtils.isNotBlank(modalId);
        this.modalId = modalId;
        this.direction = xwpfParagraph.getCTP().getPPr().isSetBidi()? "rtl" : "ltr";

    }

    public boolean isBold() {
        return isBold;
    }

    public boolean isUnderlined() {
        return isUnderlined;
    }

    public boolean isItalic() {
        return isItalic;
    }

    public List<String> getElements() {
        return elements;
    }

    public String getDirection() {
        return direction;
    }

    public String getLanguage() {
        return language;
    }

    public void ParseRunElement(XWPFRun run) {
        var runText = UnicodeConverter.ESCAPE_HTML4_CUSTOM.translate(run.text());
        if (runText.equals("\t")) {
            return;
        }
        var textUnderlined = isTextUnderlined(run);
        var textBold = run.isBold();
        var textItalic = run.isItalic();
        String underlineElement = null;
        if (textUnderlined && !isUnderlined) {

            var underLineTags = getOpeningUnderLinedElement();
            if (hasEmbeddedPopup && underLineTags.contains("class='popup-link'")
                    && elements.contains(underLineTags)) {
                var exitingPopupLinkTagIndex = elements.indexOf(underLineTags);
                var popupText = elements.get(exitingPopupLinkTagIndex + 1);
                elements.remove(exitingPopupLinkTagIndex + 1);

                var newPopupText = popupText + " " + runText;
                elements.add(exitingPopupLinkTagIndex + 1, newPopupText);
                return;
            } else {
                isUnderlined = true;
                underlineElement = underLineTags;
            }
        }
        if (textBold && !isBold) {
            isBold = true;
            elements.add("<strong>");
        }
        if (!textBold && isBold) {
            isBold = false;
            elements.add("</strong>");
        }
        if (textItalic && !isItalic) {
            isItalic = true;
            elements.add("<em>");
        }
        if (!textItalic && isItalic) {
            isItalic = false;
            elements.add("</em>");
        }
        if (null != underlineElement) {
            elements.add(underlineElement);
        }
        if (!textUnderlined && isUnderlined) {
            isUnderlined = false;
            elements.add(getClosingUnderLinedElement());
        }
        elements.add(runText);
    }


    public void ParseRunElement(XWPFHyperlinkRun run) {
        var runText = parseHyperlink(run);
        var textUnderlined = isTextUnderlined(run);
        var textBold = run.isBold();
        var textItalic = run.isItalic();
        if (textBold && !isBold) {
            isBold = true;
            elements.add("<strong>");
        }
        if (!textBold && isBold) {
            isBold = false;
            elements.add("</strong>");
        }
        if (textItalic && !isItalic) {
            isItalic = true;
            elements.add("<em>");
        }
        if (!textItalic && isItalic) {
            isItalic = false;
            elements.add("</em>");
        }
        if (textUnderlined && !isUnderlined) {
            isUnderlined = true;
            elements.add(getOpeningUnderLinedElement());
        }
        if (!textUnderlined && isUnderlined) {
            isUnderlined = false;
            elements.add(getClosingUnderLinedElement());
        }
        elements.add(runText);
    }

    private String parseHyperlink(XWPFHyperlinkRun run) {
        var hyperLink = run.getHyperlink(xwpfParagraph.getDocument());
        var runText = UnicodeConverter.ESCAPE_HTML4_CUSTOM.translate(run.text());
        return "<a href=\"%s\" target=\"_blank\">%s</a>"
                .formatted(hyperLink.getURL(), runText);
    }

    public boolean isTextUnderlined(XWPFRun run) {
        return run.getUnderline().getValue() == STUnderline.SINGLE.intValue();
    }

    public String getOpeningUnderLinedElement() {
        if (!hasEmbeddedPopup || modalId == null) {
            return "<u>";
        } else {
            return "<u><a href='#%s' class='popup-link'>".formatted(modalId);
        }
    }

    public String getClosingUnderLinedElement() {
        if (!hasEmbeddedPopup) {
            return "</u>";
        } else {
            return "</a></u>";
        }
    }
}
