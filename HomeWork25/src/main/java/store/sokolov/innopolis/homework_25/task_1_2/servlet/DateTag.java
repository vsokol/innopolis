package store.sokolov.innopolis.homework_25.task_1_2.servlet;

import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.time.LocalDate;

public final class DateTag extends TagSupport {
    private String plus = "0";

    @Override
    public int doStartTag() throws JspTagException {
        try {
            pageContext.getOut().write(String.valueOf(LocalDate.now().plusDays(Integer.parseInt(plus))));
        } catch (IOException e) {
            throw new JspTagException(e.getMessage());
        }
        return SKIP_BODY;
    }

    public String getPlus() {
        return plus;
    }

    public void setPlus(String plus) {
        this.plus = plus;
    }
}
