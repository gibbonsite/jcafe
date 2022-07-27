package com.poleschuk.cafe.controller.tag;

import jakarta.servlet.jsp.JspTagException;
import jakarta.servlet.jsp.JspWriter;
import jakarta.servlet.jsp.tagext.TagSupport;
import java.io.IOException;

/**
 * FooterTag class for handling <ctg:footertag /> JSP tag.
 */
public class FooterTag extends TagSupport {

    @Override
    public int doStartTag() throws JspTagException{
        try{
            JspWriter out = pageContext.getOut();
            String tagText = "<div style=\"height:40px;\" /><footer><div class=\"footer\">© 2022 Copyright by Maksim Poleschuk</div></footer>";
            out.write(tagText);
        } catch (IOException e) {
            throw new JspTagException("Exception in handling footer JSP tag.", e);
        }
        return SKIP_BODY;
    }

    @Override
    public int doEndTag() {
        return EVAL_PAGE;
    }
}