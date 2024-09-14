package org.example.btns;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

import java.util.List;

public interface InterfaceBtns {
    public ReplyKeyboardMarkup btnKeyboardBTN(List<String>list);
    public InlineKeyboardMarkup btnInlineKeyboardBTN(List<String>key,List<String>value);

}
